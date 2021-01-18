package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRequisition;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CDocumentType;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.domain.CWarehouse;
import com.bhp.opusb.domain.CCostCenter;
import com.bhp.opusb.repository.MRequisitionRepository;
import com.bhp.opusb.service.MRequisitionService;
import com.bhp.opusb.service.dto.MRequisitionDTO;
import com.bhp.opusb.service.mapper.MRequisitionMapper;
import com.bhp.opusb.service.dto.MRequisitionCriteria;
import com.bhp.opusb.service.MRequisitionQueryService;

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
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MRequisitionResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRequisitionResourceIT {

    private static final LocalDate DEFAULT_DATE_TRX = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRX = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TRX = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_APPROVED = false;
    private static final Boolean UPDATED_APPROVED = true;

    private static final Boolean DEFAULT_PROCESSED = false;
    private static final Boolean UPDATED_PROCESSED = true;

    private static final LocalDate DEFAULT_DATE_PROMISED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PROMISED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_PROMISED = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MRequisitionRepository mRequisitionRepository;

    @Autowired
    private MRequisitionMapper mRequisitionMapper;

    @Autowired
    private MRequisitionService mRequisitionService;

    @Autowired
    private MRequisitionQueryService mRequisitionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRequisitionMockMvc;

    private MRequisition mRequisition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRequisition createEntity(EntityManager em) {
        MRequisition mRequisition = new MRequisition()
            .dateTrx(DEFAULT_DATE_TRX)
            .documentNo(DEFAULT_DOCUMENT_NO)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
            .approved(DEFAULT_APPROVED)
            .processed(DEFAULT_PROCESSED)
            .datePromised(DEFAULT_DATE_PROMISED)
            .description(DEFAULT_DESCRIPTION)
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
        mRequisition.setAdOrganization(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mRequisition.setDocumentType(cDocumentType);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mRequisition.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mRequisition.setWarehouse(cWarehouse);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mRequisition.setCostCenter(cCostCenter);
        return mRequisition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRequisition createUpdatedEntity(EntityManager em) {
        MRequisition mRequisition = new MRequisition()
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .datePromised(UPDATED_DATE_PROMISED)
            .description(UPDATED_DESCRIPTION)
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
        mRequisition.setAdOrganization(aDOrganization);
        // Add required entity
        CDocumentType cDocumentType;
        if (TestUtil.findAll(em, CDocumentType.class).isEmpty()) {
            cDocumentType = CDocumentTypeResourceIT.createUpdatedEntity(em);
            em.persist(cDocumentType);
            em.flush();
        } else {
            cDocumentType = TestUtil.findAll(em, CDocumentType.class).get(0);
        }
        mRequisition.setDocumentType(cDocumentType);
        // Add required entity
        CCurrency cCurrency;
        if (TestUtil.findAll(em, CCurrency.class).isEmpty()) {
            cCurrency = CCurrencyResourceIT.createUpdatedEntity(em);
            em.persist(cCurrency);
            em.flush();
        } else {
            cCurrency = TestUtil.findAll(em, CCurrency.class).get(0);
        }
        mRequisition.setCurrency(cCurrency);
        // Add required entity
        CWarehouse cWarehouse;
        if (TestUtil.findAll(em, CWarehouse.class).isEmpty()) {
            cWarehouse = CWarehouseResourceIT.createUpdatedEntity(em);
            em.persist(cWarehouse);
            em.flush();
        } else {
            cWarehouse = TestUtil.findAll(em, CWarehouse.class).get(0);
        }
        mRequisition.setWarehouse(cWarehouse);
        // Add required entity
        CCostCenter cCostCenter;
        if (TestUtil.findAll(em, CCostCenter.class).isEmpty()) {
            cCostCenter = CCostCenterResourceIT.createUpdatedEntity(em);
            em.persist(cCostCenter);
            em.flush();
        } else {
            cCostCenter = TestUtil.findAll(em, CCostCenter.class).get(0);
        }
        mRequisition.setCostCenter(cCostCenter);
        return mRequisition;
    }

    @BeforeEach
    public void initTest() {
        mRequisition = createEntity(em);
    }

    @Test
    @Transactional
    public void createMRequisition() throws Exception {
        int databaseSizeBeforeCreate = mRequisitionRepository.findAll().size();

        // Create the MRequisition
        MRequisitionDTO mRequisitionDTO = mRequisitionMapper.toDto(mRequisition);
        restMRequisitionMockMvc.perform(post("/api/m-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionDTO)))
            .andExpect(status().isCreated());

        // Validate the MRequisition in the database
        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeCreate + 1);
        MRequisition testMRequisition = mRequisitionList.get(mRequisitionList.size() - 1);
        assertThat(testMRequisition.getDateTrx()).isEqualTo(DEFAULT_DATE_TRX);
        assertThat(testMRequisition.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testMRequisition.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMRequisition.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMRequisition.isApproved()).isEqualTo(DEFAULT_APPROVED);
        assertThat(testMRequisition.isProcessed()).isEqualTo(DEFAULT_PROCESSED);
        assertThat(testMRequisition.getDatePromised()).isEqualTo(DEFAULT_DATE_PROMISED);
        assertThat(testMRequisition.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMRequisition.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMRequisition.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMRequisitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mRequisitionRepository.findAll().size();

        // Create the MRequisition with an existing ID
        mRequisition.setId(1L);
        MRequisitionDTO mRequisitionDTO = mRequisitionMapper.toDto(mRequisition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMRequisitionMockMvc.perform(post("/api/m-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRequisition in the database
        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionRepository.findAll().size();
        // set the field null
        mRequisition.setDocumentAction(null);

        // Create the MRequisition, which fails.
        MRequisitionDTO mRequisitionDTO = mRequisitionMapper.toDto(mRequisition);

        restMRequisitionMockMvc.perform(post("/api/m-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mRequisitionRepository.findAll().size();
        // set the field null
        mRequisition.setDocumentStatus(null);

        // Create the MRequisition, which fails.
        MRequisitionDTO mRequisitionDTO = mRequisitionMapper.toDto(mRequisition);

        restMRequisitionMockMvc.perform(post("/api/m-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionDTO)))
            .andExpect(status().isBadRequest());

        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMRequisitions() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList
        restMRequisitionMockMvc.perform(get("/api/m-requisitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRequisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMRequisition() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get the mRequisition
        restMRequisitionMockMvc.perform(get("/api/m-requisitions/{id}", mRequisition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRequisition.getId().intValue()))
            .andExpect(jsonPath("$.dateTrx").value(DEFAULT_DATE_TRX.toString()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.approved").value(DEFAULT_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.processed").value(DEFAULT_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.datePromised").value(DEFAULT_DATE_PROMISED.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMRequisitionsByIdFiltering() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        Long id = mRequisition.getId();

        defaultMRequisitionShouldBeFound("id.equals=" + id);
        defaultMRequisitionShouldNotBeFound("id.notEquals=" + id);

        defaultMRequisitionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRequisitionShouldNotBeFound("id.greaterThan=" + id);

        defaultMRequisitionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRequisitionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx equals to DEFAULT_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.equals=" + DEFAULT_DATE_TRX);

        // Get all the mRequisitionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.equals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx not equals to DEFAULT_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.notEquals=" + DEFAULT_DATE_TRX);

        // Get all the mRequisitionList where dateTrx not equals to UPDATED_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.notEquals=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx in DEFAULT_DATE_TRX or UPDATED_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.in=" + DEFAULT_DATE_TRX + "," + UPDATED_DATE_TRX);

        // Get all the mRequisitionList where dateTrx equals to UPDATED_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.in=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx is not null
        defaultMRequisitionShouldBeFound("dateTrx.specified=true");

        // Get all the mRequisitionList where dateTrx is null
        defaultMRequisitionShouldNotBeFound("dateTrx.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx is greater than or equal to DEFAULT_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.greaterThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRequisitionList where dateTrx is greater than or equal to UPDATED_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.greaterThanOrEqual=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx is less than or equal to DEFAULT_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.lessThanOrEqual=" + DEFAULT_DATE_TRX);

        // Get all the mRequisitionList where dateTrx is less than or equal to SMALLER_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.lessThanOrEqual=" + SMALLER_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx is less than DEFAULT_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.lessThan=" + DEFAULT_DATE_TRX);

        // Get all the mRequisitionList where dateTrx is less than UPDATED_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.lessThan=" + UPDATED_DATE_TRX);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDateTrxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where dateTrx is greater than DEFAULT_DATE_TRX
        defaultMRequisitionShouldNotBeFound("dateTrx.greaterThan=" + DEFAULT_DATE_TRX);

        // Get all the mRequisitionList where dateTrx is greater than SMALLER_DATE_TRX
        defaultMRequisitionShouldBeFound("dateTrx.greaterThan=" + SMALLER_DATE_TRX);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMRequisitionShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRequisitionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMRequisitionShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMRequisitionShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRequisitionList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMRequisitionShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMRequisitionShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mRequisitionList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMRequisitionShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentNo is not null
        defaultMRequisitionShouldBeFound("documentNo.specified=true");

        // Get all the mRequisitionList where documentNo is null
        defaultMRequisitionShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRequisitionsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMRequisitionShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRequisitionList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMRequisitionShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMRequisitionShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRequisitionList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMRequisitionShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMRequisitionShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRequisitionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRequisitionShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMRequisitionShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRequisitionList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMRequisitionShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMRequisitionShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mRequisitionList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMRequisitionShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentAction is not null
        defaultMRequisitionShouldBeFound("documentAction.specified=true");

        // Get all the mRequisitionList where documentAction is null
        defaultMRequisitionShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRequisitionsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMRequisitionShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRequisitionList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMRequisitionShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMRequisitionShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mRequisitionList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMRequisitionShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMRequisitionShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRequisitionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRequisitionShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMRequisitionShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRequisitionList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMRequisitionShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMRequisitionShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mRequisitionList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMRequisitionShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentStatus is not null
        defaultMRequisitionShouldBeFound("documentStatus.specified=true");

        // Get all the mRequisitionList where documentStatus is null
        defaultMRequisitionShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRequisitionsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMRequisitionShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRequisitionList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMRequisitionShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMRequisitionShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mRequisitionList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMRequisitionShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where approved equals to DEFAULT_APPROVED
        defaultMRequisitionShouldBeFound("approved.equals=" + DEFAULT_APPROVED);

        // Get all the mRequisitionList where approved equals to UPDATED_APPROVED
        defaultMRequisitionShouldNotBeFound("approved.equals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where approved not equals to DEFAULT_APPROVED
        defaultMRequisitionShouldNotBeFound("approved.notEquals=" + DEFAULT_APPROVED);

        // Get all the mRequisitionList where approved not equals to UPDATED_APPROVED
        defaultMRequisitionShouldBeFound("approved.notEquals=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where approved in DEFAULT_APPROVED or UPDATED_APPROVED
        defaultMRequisitionShouldBeFound("approved.in=" + DEFAULT_APPROVED + "," + UPDATED_APPROVED);

        // Get all the mRequisitionList where approved equals to UPDATED_APPROVED
        defaultMRequisitionShouldNotBeFound("approved.in=" + UPDATED_APPROVED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where approved is not null
        defaultMRequisitionShouldBeFound("approved.specified=true");

        // Get all the mRequisitionList where approved is null
        defaultMRequisitionShouldNotBeFound("approved.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where processed equals to DEFAULT_PROCESSED
        defaultMRequisitionShouldBeFound("processed.equals=" + DEFAULT_PROCESSED);

        // Get all the mRequisitionList where processed equals to UPDATED_PROCESSED
        defaultMRequisitionShouldNotBeFound("processed.equals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByProcessedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where processed not equals to DEFAULT_PROCESSED
        defaultMRequisitionShouldNotBeFound("processed.notEquals=" + DEFAULT_PROCESSED);

        // Get all the mRequisitionList where processed not equals to UPDATED_PROCESSED
        defaultMRequisitionShouldBeFound("processed.notEquals=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where processed in DEFAULT_PROCESSED or UPDATED_PROCESSED
        defaultMRequisitionShouldBeFound("processed.in=" + DEFAULT_PROCESSED + "," + UPDATED_PROCESSED);

        // Get all the mRequisitionList where processed equals to UPDATED_PROCESSED
        defaultMRequisitionShouldNotBeFound("processed.in=" + UPDATED_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where processed is not null
        defaultMRequisitionShouldBeFound("processed.specified=true");

        // Get all the mRequisitionList where processed is null
        defaultMRequisitionShouldNotBeFound("processed.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised equals to DEFAULT_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.equals=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.equals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised not equals to DEFAULT_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.notEquals=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised not equals to UPDATED_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.notEquals=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised in DEFAULT_DATE_PROMISED or UPDATED_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.in=" + DEFAULT_DATE_PROMISED + "," + UPDATED_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised equals to UPDATED_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.in=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised is not null
        defaultMRequisitionShouldBeFound("datePromised.specified=true");

        // Get all the mRequisitionList where datePromised is null
        defaultMRequisitionShouldNotBeFound("datePromised.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised is greater than or equal to DEFAULT_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.greaterThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised is greater than or equal to UPDATED_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.greaterThanOrEqual=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised is less than or equal to DEFAULT_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.lessThanOrEqual=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised is less than or equal to SMALLER_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.lessThanOrEqual=" + SMALLER_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsLessThanSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised is less than DEFAULT_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.lessThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised is less than UPDATED_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.lessThan=" + UPDATED_DATE_PROMISED);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDatePromisedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where datePromised is greater than DEFAULT_DATE_PROMISED
        defaultMRequisitionShouldNotBeFound("datePromised.greaterThan=" + DEFAULT_DATE_PROMISED);

        // Get all the mRequisitionList where datePromised is greater than SMALLER_DATE_PROMISED
        defaultMRequisitionShouldBeFound("datePromised.greaterThan=" + SMALLER_DATE_PROMISED);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where description equals to DEFAULT_DESCRIPTION
        defaultMRequisitionShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the mRequisitionList where description equals to UPDATED_DESCRIPTION
        defaultMRequisitionShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where description not equals to DEFAULT_DESCRIPTION
        defaultMRequisitionShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the mRequisitionList where description not equals to UPDATED_DESCRIPTION
        defaultMRequisitionShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultMRequisitionShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the mRequisitionList where description equals to UPDATED_DESCRIPTION
        defaultMRequisitionShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where description is not null
        defaultMRequisitionShouldBeFound("description.specified=true");

        // Get all the mRequisitionList where description is null
        defaultMRequisitionShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRequisitionsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where description contains DEFAULT_DESCRIPTION
        defaultMRequisitionShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the mRequisitionList where description contains UPDATED_DESCRIPTION
        defaultMRequisitionShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where description does not contain DEFAULT_DESCRIPTION
        defaultMRequisitionShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the mRequisitionList where description does not contain UPDATED_DESCRIPTION
        defaultMRequisitionShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where uid equals to DEFAULT_UID
        defaultMRequisitionShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mRequisitionList where uid equals to UPDATED_UID
        defaultMRequisitionShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where uid not equals to DEFAULT_UID
        defaultMRequisitionShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mRequisitionList where uid not equals to UPDATED_UID
        defaultMRequisitionShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where uid in DEFAULT_UID or UPDATED_UID
        defaultMRequisitionShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mRequisitionList where uid equals to UPDATED_UID
        defaultMRequisitionShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where uid is not null
        defaultMRequisitionShouldBeFound("uid.specified=true");

        // Get all the mRequisitionList where uid is null
        defaultMRequisitionShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where active equals to DEFAULT_ACTIVE
        defaultMRequisitionShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mRequisitionList where active equals to UPDATED_ACTIVE
        defaultMRequisitionShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where active not equals to DEFAULT_ACTIVE
        defaultMRequisitionShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mRequisitionList where active not equals to UPDATED_ACTIVE
        defaultMRequisitionShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMRequisitionShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mRequisitionList where active equals to UPDATED_ACTIVE
        defaultMRequisitionShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        // Get all the mRequisitionList where active is not null
        defaultMRequisitionShouldBeFound("active.specified=true");

        // Get all the mRequisitionList where active is null
        defaultMRequisitionShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRequisitionsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mRequisition.getAdOrganization();
        mRequisitionRepository.saveAndFlush(mRequisition);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mRequisitionList where adOrganization equals to adOrganizationId
        defaultMRequisitionShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mRequisitionList where adOrganization equals to adOrganizationId + 1
        defaultMRequisitionShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByDocumentTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CDocumentType documentType = mRequisition.getDocumentType();
        mRequisitionRepository.saveAndFlush(mRequisition);
        Long documentTypeId = documentType.getId();

        // Get all the mRequisitionList where documentType equals to documentTypeId
        defaultMRequisitionShouldBeFound("documentTypeId.equals=" + documentTypeId);

        // Get all the mRequisitionList where documentType equals to documentTypeId + 1
        defaultMRequisitionShouldNotBeFound("documentTypeId.equals=" + (documentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByCurrencyIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCurrency currency = mRequisition.getCurrency();
        mRequisitionRepository.saveAndFlush(mRequisition);
        Long currencyId = currency.getId();

        // Get all the mRequisitionList where currency equals to currencyId
        defaultMRequisitionShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the mRequisitionList where currency equals to currencyId + 1
        defaultMRequisitionShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByWarehouseIsEqualToSomething() throws Exception {
        // Get already existing entity
        CWarehouse warehouse = mRequisition.getWarehouse();
        mRequisitionRepository.saveAndFlush(mRequisition);
        Long warehouseId = warehouse.getId();

        // Get all the mRequisitionList where warehouse equals to warehouseId
        defaultMRequisitionShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the mRequisitionList where warehouse equals to warehouseId + 1
        defaultMRequisitionShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }


    @Test
    @Transactional
    public void getAllMRequisitionsByCostCenterIsEqualToSomething() throws Exception {
        // Get already existing entity
        CCostCenter costCenter = mRequisition.getCostCenter();
        mRequisitionRepository.saveAndFlush(mRequisition);
        Long costCenterId = costCenter.getId();

        // Get all the mRequisitionList where costCenter equals to costCenterId
        defaultMRequisitionShouldBeFound("costCenterId.equals=" + costCenterId);

        // Get all the mRequisitionList where costCenter equals to costCenterId + 1
        defaultMRequisitionShouldNotBeFound("costCenterId.equals=" + (costCenterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRequisitionShouldBeFound(String filter) throws Exception {
        restMRequisitionMockMvc.perform(get("/api/m-requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRequisition.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTrx").value(hasItem(DEFAULT_DATE_TRX.toString())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].approved").value(hasItem(DEFAULT_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].processed").value(hasItem(DEFAULT_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].datePromised").value(hasItem(DEFAULT_DATE_PROMISED.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMRequisitionMockMvc.perform(get("/api/m-requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRequisitionShouldNotBeFound(String filter) throws Exception {
        restMRequisitionMockMvc.perform(get("/api/m-requisitions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRequisitionMockMvc.perform(get("/api/m-requisitions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRequisition() throws Exception {
        // Get the mRequisition
        restMRequisitionMockMvc.perform(get("/api/m-requisitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMRequisition() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        int databaseSizeBeforeUpdate = mRequisitionRepository.findAll().size();

        // Update the mRequisition
        MRequisition updatedMRequisition = mRequisitionRepository.findById(mRequisition.getId()).get();
        // Disconnect from session so that the updates on updatedMRequisition are not directly saved in db
        em.detach(updatedMRequisition);
        updatedMRequisition
            .dateTrx(UPDATED_DATE_TRX)
            .documentNo(UPDATED_DOCUMENT_NO)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .approved(UPDATED_APPROVED)
            .processed(UPDATED_PROCESSED)
            .datePromised(UPDATED_DATE_PROMISED)
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MRequisitionDTO mRequisitionDTO = mRequisitionMapper.toDto(updatedMRequisition);

        restMRequisitionMockMvc.perform(put("/api/m-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionDTO)))
            .andExpect(status().isOk());

        // Validate the MRequisition in the database
        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeUpdate);
        MRequisition testMRequisition = mRequisitionList.get(mRequisitionList.size() - 1);
        assertThat(testMRequisition.getDateTrx()).isEqualTo(UPDATED_DATE_TRX);
        assertThat(testMRequisition.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testMRequisition.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMRequisition.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMRequisition.isApproved()).isEqualTo(UPDATED_APPROVED);
        assertThat(testMRequisition.isProcessed()).isEqualTo(UPDATED_PROCESSED);
        assertThat(testMRequisition.getDatePromised()).isEqualTo(UPDATED_DATE_PROMISED);
        assertThat(testMRequisition.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMRequisition.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMRequisition.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMRequisition() throws Exception {
        int databaseSizeBeforeUpdate = mRequisitionRepository.findAll().size();

        // Create the MRequisition
        MRequisitionDTO mRequisitionDTO = mRequisitionMapper.toDto(mRequisition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMRequisitionMockMvc.perform(put("/api/m-requisitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mRequisitionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MRequisition in the database
        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMRequisition() throws Exception {
        // Initialize the database
        mRequisitionRepository.saveAndFlush(mRequisition);

        int databaseSizeBeforeDelete = mRequisitionRepository.findAll().size();

        // Delete the mRequisition
        restMRequisitionMockMvc.perform(delete("/api/m-requisitions/{id}", mRequisition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MRequisition> mRequisitionList = mRequisitionRepository.findAll();
        assertThat(mRequisitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
