package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.PaDashboardItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdWatchList;
import com.bhp.opusb.domain.PaDashboard;
import com.bhp.opusb.repository.PaDashboardItemRepository;
import com.bhp.opusb.service.PaDashboardItemService;
import com.bhp.opusb.service.dto.PaDashboardItemDTO;
import com.bhp.opusb.service.mapper.PaDashboardItemMapper;
import com.bhp.opusb.service.dto.PaDashboardItemCriteria;
import com.bhp.opusb.service.PaDashboardItemQueryService;

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

import com.bhp.opusb.domain.enumeration.PaDashboardItemType;
/**
 * Integration tests for the {@link PaDashboardItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PaDashboardItemResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_COLUMN_NO = 1;
    private static final Integer UPDATED_COLUMN_NO = 2;
    private static final Integer SMALLER_COLUMN_NO = 1 - 1;

    private static final Integer DEFAULT_ROW_NO = 1;
    private static final Integer UPDATED_ROW_NO = 2;
    private static final Integer SMALLER_ROW_NO = 1 - 1;

    private static final PaDashboardItemType DEFAULT_TYPE = PaDashboardItemType.CHART;
    private static final PaDashboardItemType UPDATED_TYPE = PaDashboardItemType.WATCHLIST;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PaDashboardItemRepository paDashboardItemRepository;

    @Autowired
    private PaDashboardItemMapper paDashboardItemMapper;

    @Autowired
    private PaDashboardItemService paDashboardItemService;

    @Autowired
    private PaDashboardItemQueryService paDashboardItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaDashboardItemMockMvc;

    private PaDashboardItem paDashboardItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaDashboardItem createEntity(EntityManager em) {
        PaDashboardItem paDashboardItem = new PaDashboardItem()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .columnNo(DEFAULT_COLUMN_NO)
            .rowNo(DEFAULT_ROW_NO)
            .type(DEFAULT_TYPE)
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
        paDashboardItem.setAdOrganization(aDOrganization);
        // Add required entity
        PaDashboard paDashboard;
        if (TestUtil.findAll(em, PaDashboard.class).isEmpty()) {
            paDashboard = PaDashboardResourceIT.createEntity(em);
            em.persist(paDashboard);
            em.flush();
        } else {
            paDashboard = TestUtil.findAll(em, PaDashboard.class).get(0);
        }
        paDashboardItem.setPaDashboard(paDashboard);
        return paDashboardItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaDashboardItem createUpdatedEntity(EntityManager em) {
        PaDashboardItem paDashboardItem = new PaDashboardItem()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .columnNo(UPDATED_COLUMN_NO)
            .rowNo(UPDATED_ROW_NO)
            .type(UPDATED_TYPE)
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
        paDashboardItem.setAdOrganization(aDOrganization);
        // Add required entity
        PaDashboard paDashboard;
        if (TestUtil.findAll(em, PaDashboard.class).isEmpty()) {
            paDashboard = PaDashboardResourceIT.createUpdatedEntity(em);
            em.persist(paDashboard);
            em.flush();
        } else {
            paDashboard = TestUtil.findAll(em, PaDashboard.class).get(0);
        }
        paDashboardItem.setPaDashboard(paDashboard);
        return paDashboardItem;
    }

    @BeforeEach
    public void initTest() {
        paDashboardItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaDashboardItem() throws Exception {
        int databaseSizeBeforeCreate = paDashboardItemRepository.findAll().size();

        // Create the PaDashboardItem
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);
        restPaDashboardItemMockMvc.perform(post("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isCreated());

        // Validate the PaDashboardItem in the database
        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeCreate + 1);
        PaDashboardItem testPaDashboardItem = paDashboardItemList.get(paDashboardItemList.size() - 1);
        assertThat(testPaDashboardItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaDashboardItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPaDashboardItem.getColumnNo()).isEqualTo(DEFAULT_COLUMN_NO);
        assertThat(testPaDashboardItem.getRowNo()).isEqualTo(DEFAULT_ROW_NO);
        assertThat(testPaDashboardItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPaDashboardItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testPaDashboardItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPaDashboardItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paDashboardItemRepository.findAll().size();

        // Create the PaDashboardItem with an existing ID
        paDashboardItem.setId(1L);
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaDashboardItemMockMvc.perform(post("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaDashboardItem in the database
        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardItemRepository.findAll().size();
        // set the field null
        paDashboardItem.setName(null);

        // Create the PaDashboardItem, which fails.
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);

        restPaDashboardItemMockMvc.perform(post("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColumnNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardItemRepository.findAll().size();
        // set the field null
        paDashboardItem.setColumnNo(null);

        // Create the PaDashboardItem, which fails.
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);

        restPaDashboardItemMockMvc.perform(post("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRowNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardItemRepository.findAll().size();
        // set the field null
        paDashboardItem.setRowNo(null);

        // Create the PaDashboardItem, which fails.
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);

        restPaDashboardItemMockMvc.perform(post("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paDashboardItemRepository.findAll().size();
        // set the field null
        paDashboardItem.setType(null);

        // Create the PaDashboardItem, which fails.
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);

        restPaDashboardItemMockMvc.perform(post("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isBadRequest());

        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItems() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paDashboardItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].rowNo").value(hasItem(DEFAULT_ROW_NO)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPaDashboardItem() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get the paDashboardItem
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items/{id}", paDashboardItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paDashboardItem.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.columnNo").value(DEFAULT_COLUMN_NO))
            .andExpect(jsonPath("$.rowNo").value(DEFAULT_ROW_NO))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getPaDashboardItemsByIdFiltering() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        Long id = paDashboardItem.getId();

        defaultPaDashboardItemShouldBeFound("id.equals=" + id);
        defaultPaDashboardItemShouldNotBeFound("id.notEquals=" + id);

        defaultPaDashboardItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaDashboardItemShouldNotBeFound("id.greaterThan=" + id);

        defaultPaDashboardItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaDashboardItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where name equals to DEFAULT_NAME
        defaultPaDashboardItemShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the paDashboardItemList where name equals to UPDATED_NAME
        defaultPaDashboardItemShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where name not equals to DEFAULT_NAME
        defaultPaDashboardItemShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the paDashboardItemList where name not equals to UPDATED_NAME
        defaultPaDashboardItemShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPaDashboardItemShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the paDashboardItemList where name equals to UPDATED_NAME
        defaultPaDashboardItemShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where name is not null
        defaultPaDashboardItemShouldBeFound("name.specified=true");

        // Get all the paDashboardItemList where name is null
        defaultPaDashboardItemShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaDashboardItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where name contains DEFAULT_NAME
        defaultPaDashboardItemShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the paDashboardItemList where name contains UPDATED_NAME
        defaultPaDashboardItemShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where name does not contain DEFAULT_NAME
        defaultPaDashboardItemShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the paDashboardItemList where name does not contain UPDATED_NAME
        defaultPaDashboardItemShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where description equals to DEFAULT_DESCRIPTION
        defaultPaDashboardItemShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardItemList where description equals to UPDATED_DESCRIPTION
        defaultPaDashboardItemShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where description not equals to DEFAULT_DESCRIPTION
        defaultPaDashboardItemShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardItemList where description not equals to UPDATED_DESCRIPTION
        defaultPaDashboardItemShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPaDashboardItemShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the paDashboardItemList where description equals to UPDATED_DESCRIPTION
        defaultPaDashboardItemShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where description is not null
        defaultPaDashboardItemShouldBeFound("description.specified=true");

        // Get all the paDashboardItemList where description is null
        defaultPaDashboardItemShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaDashboardItemsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where description contains DEFAULT_DESCRIPTION
        defaultPaDashboardItemShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardItemList where description contains UPDATED_DESCRIPTION
        defaultPaDashboardItemShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where description does not contain DEFAULT_DESCRIPTION
        defaultPaDashboardItemShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the paDashboardItemList where description does not contain UPDATED_DESCRIPTION
        defaultPaDashboardItemShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo equals to DEFAULT_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.equals=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo equals to UPDATED_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.equals=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo not equals to DEFAULT_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.notEquals=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo not equals to UPDATED_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.notEquals=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo in DEFAULT_COLUMN_NO or UPDATED_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.in=" + DEFAULT_COLUMN_NO + "," + UPDATED_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo equals to UPDATED_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.in=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo is not null
        defaultPaDashboardItemShouldBeFound("columnNo.specified=true");

        // Get all the paDashboardItemList where columnNo is null
        defaultPaDashboardItemShouldNotBeFound("columnNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo is greater than or equal to DEFAULT_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.greaterThanOrEqual=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo is greater than or equal to UPDATED_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.greaterThanOrEqual=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo is less than or equal to DEFAULT_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.lessThanOrEqual=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo is less than or equal to SMALLER_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.lessThanOrEqual=" + SMALLER_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsLessThanSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo is less than DEFAULT_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.lessThan=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo is less than UPDATED_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.lessThan=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByColumnNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where columnNo is greater than DEFAULT_COLUMN_NO
        defaultPaDashboardItemShouldNotBeFound("columnNo.greaterThan=" + DEFAULT_COLUMN_NO);

        // Get all the paDashboardItemList where columnNo is greater than SMALLER_COLUMN_NO
        defaultPaDashboardItemShouldBeFound("columnNo.greaterThan=" + SMALLER_COLUMN_NO);
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo equals to DEFAULT_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.equals=" + DEFAULT_ROW_NO);

        // Get all the paDashboardItemList where rowNo equals to UPDATED_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.equals=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo not equals to DEFAULT_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.notEquals=" + DEFAULT_ROW_NO);

        // Get all the paDashboardItemList where rowNo not equals to UPDATED_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.notEquals=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo in DEFAULT_ROW_NO or UPDATED_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.in=" + DEFAULT_ROW_NO + "," + UPDATED_ROW_NO);

        // Get all the paDashboardItemList where rowNo equals to UPDATED_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.in=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo is not null
        defaultPaDashboardItemShouldBeFound("rowNo.specified=true");

        // Get all the paDashboardItemList where rowNo is null
        defaultPaDashboardItemShouldNotBeFound("rowNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo is greater than or equal to DEFAULT_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.greaterThanOrEqual=" + DEFAULT_ROW_NO);

        // Get all the paDashboardItemList where rowNo is greater than or equal to UPDATED_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.greaterThanOrEqual=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo is less than or equal to DEFAULT_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.lessThanOrEqual=" + DEFAULT_ROW_NO);

        // Get all the paDashboardItemList where rowNo is less than or equal to SMALLER_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.lessThanOrEqual=" + SMALLER_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsLessThanSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo is less than DEFAULT_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.lessThan=" + DEFAULT_ROW_NO);

        // Get all the paDashboardItemList where rowNo is less than UPDATED_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.lessThan=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByRowNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where rowNo is greater than DEFAULT_ROW_NO
        defaultPaDashboardItemShouldNotBeFound("rowNo.greaterThan=" + DEFAULT_ROW_NO);

        // Get all the paDashboardItemList where rowNo is greater than SMALLER_ROW_NO
        defaultPaDashboardItemShouldBeFound("rowNo.greaterThan=" + SMALLER_ROW_NO);
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where type equals to DEFAULT_TYPE
        defaultPaDashboardItemShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the paDashboardItemList where type equals to UPDATED_TYPE
        defaultPaDashboardItemShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where type not equals to DEFAULT_TYPE
        defaultPaDashboardItemShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the paDashboardItemList where type not equals to UPDATED_TYPE
        defaultPaDashboardItemShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPaDashboardItemShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the paDashboardItemList where type equals to UPDATED_TYPE
        defaultPaDashboardItemShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where type is not null
        defaultPaDashboardItemShouldBeFound("type.specified=true");

        // Get all the paDashboardItemList where type is null
        defaultPaDashboardItemShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where uid equals to DEFAULT_UID
        defaultPaDashboardItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the paDashboardItemList where uid equals to UPDATED_UID
        defaultPaDashboardItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where uid not equals to DEFAULT_UID
        defaultPaDashboardItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the paDashboardItemList where uid not equals to UPDATED_UID
        defaultPaDashboardItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultPaDashboardItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the paDashboardItemList where uid equals to UPDATED_UID
        defaultPaDashboardItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where uid is not null
        defaultPaDashboardItemShouldBeFound("uid.specified=true");

        // Get all the paDashboardItemList where uid is null
        defaultPaDashboardItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where active equals to DEFAULT_ACTIVE
        defaultPaDashboardItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the paDashboardItemList where active equals to UPDATED_ACTIVE
        defaultPaDashboardItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where active not equals to DEFAULT_ACTIVE
        defaultPaDashboardItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the paDashboardItemList where active not equals to UPDATED_ACTIVE
        defaultPaDashboardItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultPaDashboardItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the paDashboardItemList where active equals to UPDATED_ACTIVE
        defaultPaDashboardItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        // Get all the paDashboardItemList where active is not null
        defaultPaDashboardItemShouldBeFound("active.specified=true");

        // Get all the paDashboardItemList where active is null
        defaultPaDashboardItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaDashboardItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = paDashboardItem.getAdOrganization();
        paDashboardItemRepository.saveAndFlush(paDashboardItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the paDashboardItemList where adOrganization equals to adOrganizationId
        defaultPaDashboardItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the paDashboardItemList where adOrganization equals to adOrganizationId + 1
        defaultPaDashboardItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByAdWatchListIsEqualToSomething() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);
        AdWatchList adWatchList = AdWatchListResourceIT.createEntity(em);
        em.persist(adWatchList);
        em.flush();
        paDashboardItem.setAdWatchList(adWatchList);
        paDashboardItemRepository.saveAndFlush(paDashboardItem);
        Long adWatchListId = adWatchList.getId();

        // Get all the paDashboardItemList where adWatchList equals to adWatchListId
        defaultPaDashboardItemShouldBeFound("adWatchListId.equals=" + adWatchListId);

        // Get all the paDashboardItemList where adWatchList equals to adWatchListId + 1
        defaultPaDashboardItemShouldNotBeFound("adWatchListId.equals=" + (adWatchListId + 1));
    }


    @Test
    @Transactional
    public void getAllPaDashboardItemsByPaDashboardIsEqualToSomething() throws Exception {
        // Get already existing entity
        PaDashboard paDashboard = paDashboardItem.getPaDashboard();
        paDashboardItemRepository.saveAndFlush(paDashboardItem);
        Long paDashboardId = paDashboard.getId();

        // Get all the paDashboardItemList where paDashboard equals to paDashboardId
        defaultPaDashboardItemShouldBeFound("paDashboardId.equals=" + paDashboardId);

        // Get all the paDashboardItemList where paDashboard equals to paDashboardId + 1
        defaultPaDashboardItemShouldNotBeFound("paDashboardId.equals=" + (paDashboardId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaDashboardItemShouldBeFound(String filter) throws Exception {
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paDashboardItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].rowNo").value(hasItem(DEFAULT_ROW_NO)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaDashboardItemShouldNotBeFound(String filter) throws Exception {
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaDashboardItem() throws Exception {
        // Get the paDashboardItem
        restPaDashboardItemMockMvc.perform(get("/api/pa-dashboard-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaDashboardItem() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        int databaseSizeBeforeUpdate = paDashboardItemRepository.findAll().size();

        // Update the paDashboardItem
        PaDashboardItem updatedPaDashboardItem = paDashboardItemRepository.findById(paDashboardItem.getId()).get();
        // Disconnect from session so that the updates on updatedPaDashboardItem are not directly saved in db
        em.detach(updatedPaDashboardItem);
        updatedPaDashboardItem
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .columnNo(UPDATED_COLUMN_NO)
            .rowNo(UPDATED_ROW_NO)
            .type(UPDATED_TYPE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(updatedPaDashboardItem);

        restPaDashboardItemMockMvc.perform(put("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isOk());

        // Validate the PaDashboardItem in the database
        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeUpdate);
        PaDashboardItem testPaDashboardItem = paDashboardItemList.get(paDashboardItemList.size() - 1);
        assertThat(testPaDashboardItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaDashboardItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPaDashboardItem.getColumnNo()).isEqualTo(UPDATED_COLUMN_NO);
        assertThat(testPaDashboardItem.getRowNo()).isEqualTo(UPDATED_ROW_NO);
        assertThat(testPaDashboardItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPaDashboardItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testPaDashboardItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPaDashboardItem() throws Exception {
        int databaseSizeBeforeUpdate = paDashboardItemRepository.findAll().size();

        // Create the PaDashboardItem
        PaDashboardItemDTO paDashboardItemDTO = paDashboardItemMapper.toDto(paDashboardItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaDashboardItemMockMvc.perform(put("/api/pa-dashboard-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paDashboardItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaDashboardItem in the database
        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaDashboardItem() throws Exception {
        // Initialize the database
        paDashboardItemRepository.saveAndFlush(paDashboardItem);

        int databaseSizeBeforeDelete = paDashboardItemRepository.findAll().size();

        // Delete the paDashboardItem
        restPaDashboardItemMockMvc.perform(delete("/api/pa-dashboard-items/{id}", paDashboardItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaDashboardItem> paDashboardItemList = paDashboardItemRepository.findAll();
        assertThat(paDashboardItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
