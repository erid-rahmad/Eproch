package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.PaDashboardPreference;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.PaDashboardItem;
import com.bhp.opusb.repository.PaDashboardPreferenceRepository;
import com.bhp.opusb.service.PaDashboardPreferenceService;
import com.bhp.opusb.service.dto.PaDashboardPreferenceDTO;
import com.bhp.opusb.service.mapper.PaDashboardPreferenceMapper;
import com.bhp.opusb.service.dto.PaDashboardPreferenceCriteria;
import com.bhp.opusb.service.PaDashboardPreferenceQueryService;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaDashboardPreferenceResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PaDashboardPreferenceResourceIT {

    private static final Integer DEFAULT_COLUMN_NO = 1;
    private static final Integer UPDATED_COLUMN_NO = 2;
    private static final Integer SMALLER_COLUMN_NO = 1 - 1;

    private static final Integer DEFAULT_ROW_NO = 1;
    private static final Integer UPDATED_ROW_NO = 2;
    private static final Integer SMALLER_ROW_NO = 1 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PaDashboardPreferenceRepository paDashboardPreferenceRepository;

    @Autowired
    private PaDashboardPreferenceMapper paDashboardPreferenceMapper;

    @Autowired
    private PaDashboardPreferenceService paDashboardPreferenceService;

    @Autowired
    private PaDashboardPreferenceQueryService paDashboardPreferenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaDashboardPreferenceMockMvc;

    private PaDashboardPreference paDashboardPreference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaDashboardPreference createEntity(EntityManager em) {
        PaDashboardPreference paDashboardPreference = new PaDashboardPreference()
            .columnNo(DEFAULT_COLUMN_NO)
            .rowNo(DEFAULT_ROW_NO)
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
        paDashboardPreference.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        paDashboardPreference.setAdUser(adUser);
        // Add required entity
        PaDashboardItem paDashboardItem;
        if (TestUtil.findAll(em, PaDashboardItem.class).isEmpty()) {
            paDashboardItem = PaDashboardItemResourceIT.createEntity(em);
            em.persist(paDashboardItem);
            em.flush();
        } else {
            paDashboardItem = TestUtil.findAll(em, PaDashboardItem.class).get(0);
        }
        paDashboardPreference.setPaDashboardItem(paDashboardItem);
        return paDashboardPreference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaDashboardPreference createUpdatedEntity(EntityManager em) {
        PaDashboardPreference paDashboardPreference = new PaDashboardPreference()
            .columnNo(UPDATED_COLUMN_NO)
            .rowNo(UPDATED_ROW_NO)
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
        paDashboardPreference.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        paDashboardPreference.setAdUser(adUser);
        // Add required entity
        PaDashboardItem paDashboardItem;
        if (TestUtil.findAll(em, PaDashboardItem.class).isEmpty()) {
            paDashboardItem = PaDashboardItemResourceIT.createUpdatedEntity(em);
            em.persist(paDashboardItem);
            em.flush();
        } else {
            paDashboardItem = TestUtil.findAll(em, PaDashboardItem.class).get(0);
        }
        paDashboardPreference.setPaDashboardItem(paDashboardItem);
        return paDashboardPreference;
    }

    @BeforeEach
    public void initTest() {
        paDashboardPreference = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaDashboardPreference() throws Exception {
        int databaseSizeBeforeCreate = paDashboardPreferenceRepository.findAll().size();

        // Create the PaDashboardPreference
        PaDashboardPreferenceDTO paDashboardPreferenceDTO = paDashboardPreferenceMapper.toDto(paDashboardPreference);
        restPaDashboardPreferenceMockMvc.perform(post("/api/pa-dashboard-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardPreferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the PaDashboardPreference in the database
        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        PaDashboardPreference testPaDashboardPreference = paDashboardPreferenceList.get(paDashboardPreferenceList.size() - 1);
        assertThat(testPaDashboardPreference.getColumnNo()).isEqualTo(DEFAULT_COLUMN_NO);
        assertThat(testPaDashboardPreference.getRowNo()).isEqualTo(DEFAULT_ROW_NO);
        assertThat(testPaDashboardPreference.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testPaDashboardPreference.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPaDashboardPreferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paDashboardPreferenceRepository.findAll().size();

        // Create the PaDashboardPreference with an existing ID
        paDashboardPreference.setId(1L);
        PaDashboardPreferenceDTO paDashboardPreferenceDTO = paDashboardPreferenceMapper.toDto(paDashboardPreference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaDashboardPreferenceMockMvc.perform(post("/api/pa-dashboard-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardPreferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaDashboardPreference in the database
        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkColumnNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardPreferenceRepository.findAll().size();
        // set the field null
        paDashboardPreference.setColumnNo(null);

        // Create the PaDashboardPreference, which fails.
        PaDashboardPreferenceDTO paDashboardPreferenceDTO = paDashboardPreferenceMapper.toDto(paDashboardPreference);

        restPaDashboardPreferenceMockMvc.perform(post("/api/pa-dashboard-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardPreferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRowNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardPreferenceRepository.findAll().size();
        // set the field null
        paDashboardPreference.setRowNo(null);

        // Create the PaDashboardPreference, which fails.
        PaDashboardPreferenceDTO paDashboardPreferenceDTO = paDashboardPreferenceMapper.toDto(paDashboardPreference);

        restPaDashboardPreferenceMockMvc.perform(post("/api/pa-dashboard-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardPreferenceDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferences() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paDashboardPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].rowNo").value(hasItem(DEFAULT_ROW_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPaDashboardPreference() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get the paDashboardPreference
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences/{id}", paDashboardPreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paDashboardPreference.getId().intValue()))
            .andExpect(jsonPath("$.columnNo").value(DEFAULT_COLUMN_NO))
            .andExpect(jsonPath("$.rowNo").value(DEFAULT_ROW_NO))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getPaDashboardPreferencesByIdFiltering() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        Long id = paDashboardPreference.getId();

        defaultPaDashboardPreferenceShouldBeFound("id.equals=" + id);
        defaultPaDashboardPreferenceShouldNotBeFound("id.notEquals=" + id);

        defaultPaDashboardPreferenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaDashboardPreferenceShouldNotBeFound("id.greaterThan=" + id);

        defaultPaDashboardPreferenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaDashboardPreferenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo equals to DEFAULT_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.equals=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo equals to UPDATED_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.equals=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo not equals to DEFAULT_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.notEquals=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo not equals to UPDATED_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.notEquals=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo in DEFAULT_COLUMN_NO or UPDATED_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.in=" + DEFAULT_COLUMN_NO + "," + UPDATED_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo equals to UPDATED_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.in=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo is not null
        defaultPaDashboardPreferenceShouldBeFound("columnNo.specified=true");

        // Get all the paDashboardPreferenceList where columnNo is null
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo is greater than or equal to DEFAULT_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.greaterThanOrEqual=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo is greater than or equal to UPDATED_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.greaterThanOrEqual=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo is less than or equal to DEFAULT_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.lessThanOrEqual=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo is less than or equal to SMALLER_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.lessThanOrEqual=" + SMALLER_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsLessThanSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo is less than DEFAULT_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.lessThan=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo is less than UPDATED_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.lessThan=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByColumnNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where columnNo is greater than DEFAULT_COLUMN_NO
        defaultPaDashboardPreferenceShouldNotBeFound("columnNo.greaterThan=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardPreferenceList where columnNo is greater than SMALLER_COLUMN_NO
        defaultPaDashboardPreferenceShouldBeFound("columnNo.greaterThan=" + SMALLER_COLUMN_NO);
    }


    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo equals to DEFAULT_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.equals=" + DEFAULT_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo equals to UPDATED_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.equals=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo not equals to DEFAULT_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.notEquals=" + DEFAULT_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo not equals to UPDATED_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.notEquals=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo in DEFAULT_ROW_NO or UPDATED_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.in=" + DEFAULT_ROW_NO + "," + UPDATED_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo equals to UPDATED_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.in=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo is not null
        defaultPaDashboardPreferenceShouldBeFound("rowNo.specified=true");

        // Get all the paDashboardPreferenceList where rowNo is null
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo is greater than or equal to DEFAULT_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.greaterThanOrEqual=" + DEFAULT_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo is greater than or equal to UPDATED_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.greaterThanOrEqual=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo is less than or equal to DEFAULT_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.lessThanOrEqual=" + DEFAULT_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo is less than or equal to SMALLER_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.lessThanOrEqual=" + SMALLER_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsLessThanSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo is less than DEFAULT_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.lessThan=" + DEFAULT_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo is less than UPDATED_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.lessThan=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByRowNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where rowNo is greater than DEFAULT_ROW_NO
        defaultPaDashboardPreferenceShouldNotBeFound("rowNo.greaterThan=" + DEFAULT_ROW_NO);

        // Get all the paDashboardPreferenceList where rowNo is greater than SMALLER_ROW_NO
        defaultPaDashboardPreferenceShouldBeFound("rowNo.greaterThan=" + SMALLER_ROW_NO);
    }


    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where uid equals to DEFAULT_UID
        defaultPaDashboardPreferenceShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the paDashboardPreferenceList where uid equals to UPDATED_UID
        defaultPaDashboardPreferenceShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where uid not equals to DEFAULT_UID
        defaultPaDashboardPreferenceShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the paDashboardPreferenceList where uid not equals to UPDATED_UID
        defaultPaDashboardPreferenceShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where uid in DEFAULT_UID or UPDATED_UID
        defaultPaDashboardPreferenceShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the paDashboardPreferenceList where uid equals to UPDATED_UID
        defaultPaDashboardPreferenceShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where uid is not null
        defaultPaDashboardPreferenceShouldBeFound("uid.specified=true");

        // Get all the paDashboardPreferenceList where uid is null
        defaultPaDashboardPreferenceShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where active equals to DEFAULT_ACTIVE
        defaultPaDashboardPreferenceShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the paDashboardPreferenceList where active equals to UPDATED_ACTIVE
        defaultPaDashboardPreferenceShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where active not equals to DEFAULT_ACTIVE
        defaultPaDashboardPreferenceShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the paDashboardPreferenceList where active not equals to UPDATED_ACTIVE
        defaultPaDashboardPreferenceShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultPaDashboardPreferenceShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the paDashboardPreferenceList where active equals to UPDATED_ACTIVE
        defaultPaDashboardPreferenceShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        // Get all the paDashboardPreferenceList where active is not null
        defaultPaDashboardPreferenceShouldBeFound("active.specified=true");

        // Get all the paDashboardPreferenceList where active is null
        defaultPaDashboardPreferenceShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = paDashboardPreference.getAdOrganization();
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);
        Long adOrganizationId = adOrganization.getId();

        // Get all the paDashboardPreferenceList where adOrganization equals to adOrganizationId
        defaultPaDashboardPreferenceShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the paDashboardPreferenceList where adOrganization equals to adOrganizationId + 1
        defaultPaDashboardPreferenceShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByAdUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser adUser = paDashboardPreference.getAdUser();
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);
        Long adUserId = adUser.getId();

        // Get all the paDashboardPreferenceList where adUser equals to adUserId
        defaultPaDashboardPreferenceShouldBeFound("adUserId.equals=" + adUserId);

        // Get all the paDashboardPreferenceList where adUser equals to adUserId + 1
        defaultPaDashboardPreferenceShouldNotBeFound("adUserId.equals=" + (adUserId + 1));
    }


    @Test
    @Transactional
    public void getAllPaDashboardPreferencesByPaDashboardItemIsEqualToSomething() throws Exception {
        // Get already existing entity
        PaDashboardItem paDashboardItem = paDashboardPreference.getPaDashboardItem();
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);
        Long paDashboardItemId = paDashboardItem.getId();

        // Get all the paDashboardPreferenceList where paDashboardItem equals to paDashboardItemId
        defaultPaDashboardPreferenceShouldBeFound("paDashboardItemId.equals=" + paDashboardItemId);

        // Get all the paDashboardPreferenceList where paDashboardItem equals to paDashboardItemId + 1
        defaultPaDashboardPreferenceShouldNotBeFound("paDashboardItemId.equals=" + (paDashboardItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaDashboardPreferenceShouldBeFound(String filter) throws Exception {
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paDashboardPreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].rowNo").value(hasItem(DEFAULT_ROW_NO)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaDashboardPreferenceShouldNotBeFound(String filter) throws Exception {
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaDashboardPreference() throws Exception {
        // Get the paDashboardPreference
        restPaDashboardPreferenceMockMvc.perform(get("/api/pa-dashboard-preferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaDashboardPreference() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        int databaseSizeBeforeUpdate = paDashboardPreferenceRepository.findAll().size();

        // Update the paDashboardPreference
        PaDashboardPreference updatedPaDashboardPreference = paDashboardPreferenceRepository.findById(paDashboardPreference.getId()).get();
        // Disconnect from session so that the updates on updatedPaDashboardPreference are not directly saved in db
        em.detach(updatedPaDashboardPreference);
        updatedPaDashboardPreference
            .columnNo(UPDATED_COLUMN_NO)
            .rowNo(UPDATED_ROW_NO)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        PaDashboardPreferenceDTO paDashboardPreferenceDTO = paDashboardPreferenceMapper.toDto(updatedPaDashboardPreference);

        restPaDashboardPreferenceMockMvc.perform(put("/api/pa-dashboard-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardPreferenceDTO)))
            .andExpect(status().isOk());

        // Validate the PaDashboardPreference in the database
        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeUpdate);
        PaDashboardPreference testPaDashboardPreference = paDashboardPreferenceList.get(paDashboardPreferenceList.size() - 1);
        assertThat(testPaDashboardPreference.getColumnNo()).isEqualTo(UPDATED_COLUMN_NO);
        assertThat(testPaDashboardPreference.getRowNo()).isEqualTo(UPDATED_ROW_NO);
        assertThat(testPaDashboardPreference.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testPaDashboardPreference.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaDashboardPreference() throws Exception {
        int databaseSizeBeforeUpdate = paDashboardPreferenceRepository.findAll().size();

        // Create the PaDashboardPreference
        PaDashboardPreferenceDTO paDashboardPreferenceDTO = paDashboardPreferenceMapper.toDto(paDashboardPreference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaDashboardPreferenceMockMvc.perform(put("/api/pa-dashboard-preferences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardPreferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaDashboardPreference in the database
        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaDashboardPreference() throws Exception {
        // Initialize the database
        paDashboardPreferenceRepository.saveAndFlush(paDashboardPreference);

        int databaseSizeBeforeDelete = paDashboardPreferenceRepository.findAll().size();

        // Delete the paDashboardPreference
        restPaDashboardPreferenceMockMvc.perform(delete("/api/pa-dashboard-preferences/{id}", paDashboardPreference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaDashboardPreference> paDashboardPreferenceList = paDashboardPreferenceRepository.findAll();
        assertThat(paDashboardPreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
