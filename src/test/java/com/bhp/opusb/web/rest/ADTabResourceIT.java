package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.service.ADTabService;
import com.bhp.opusb.service.dto.ADTabDTO;
import com.bhp.opusb.service.mapper.ADTabMapper;
import com.bhp.opusb.service.dto.ADTabCriteria;
import com.bhp.opusb.service.ADTabQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ADTabResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADTabResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_ENDPOINT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WRITABLE = false;
    private static final Boolean UPDATED_WRITABLE = true;

    private static final String DEFAULT_DISPLAY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_READ_ONLY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_READ_ONLY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_FILTER_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_FILTER_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_QUERY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADTabRepository aDTabRepository;

    @Autowired
    private ADTabMapper aDTabMapper;

    @Autowired
    private ADTabService aDTabService;

    @Autowired
    private ADTabQueryService aDTabQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADTabMockMvc;

    private ADTab aDTab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADTab createEntity(EntityManager em) {
        ADTab aDTab = new ADTab()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .targetEndpoint(DEFAULT_TARGET_ENDPOINT)
            .writable(DEFAULT_WRITABLE)
            .displayLogic(DEFAULT_DISPLAY_LOGIC)
            .readOnlyLogic(DEFAULT_READ_ONLY_LOGIC)
            .filterQuery(DEFAULT_FILTER_QUERY)
            .orderQuery(DEFAULT_ORDER_QUERY)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        ADClient aDClient;
        if (TestUtil.findAll(em, ADClient.class).isEmpty()) {
            aDClient = ADClientResourceIT.createEntity(em);
            em.persist(aDClient);
            em.flush();
        } else {
            aDClient = TestUtil.findAll(em, ADClient.class).get(0);
        }
        aDTab.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDTab.setAdOrganization(aDOrganization);
        // Add required entity
        ADTable aDTable;
        if (TestUtil.findAll(em, ADTable.class).isEmpty()) {
            aDTable = ADTableResourceIT.createEntity(em);
            em.persist(aDTable);
            em.flush();
        } else {
            aDTable = TestUtil.findAll(em, ADTable.class).get(0);
        }
        aDTab.setAdTable(aDTable);
        // Add required entity
        ADWindow aDWindow;
        if (TestUtil.findAll(em, ADWindow.class).isEmpty()) {
            aDWindow = ADWindowResourceIT.createEntity(em);
            em.persist(aDWindow);
            em.flush();
        } else {
            aDWindow = TestUtil.findAll(em, ADWindow.class).get(0);
        }
        aDTab.setAdWindow(aDWindow);
        return aDTab;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADTab createUpdatedEntity(EntityManager em) {
        ADTab aDTab = new ADTab()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .targetEndpoint(UPDATED_TARGET_ENDPOINT)
            .writable(UPDATED_WRITABLE)
            .displayLogic(UPDATED_DISPLAY_LOGIC)
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .filterQuery(UPDATED_FILTER_QUERY)
            .orderQuery(UPDATED_ORDER_QUERY)
            .active(UPDATED_ACTIVE);
        // Add required entity
        ADClient aDClient;
        if (TestUtil.findAll(em, ADClient.class).isEmpty()) {
            aDClient = ADClientResourceIT.createUpdatedEntity(em);
            em.persist(aDClient);
            em.flush();
        } else {
            aDClient = TestUtil.findAll(em, ADClient.class).get(0);
        }
        aDTab.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDTab.setAdOrganization(aDOrganization);
        // Add required entity
        ADTable aDTable;
        if (TestUtil.findAll(em, ADTable.class).isEmpty()) {
            aDTable = ADTableResourceIT.createUpdatedEntity(em);
            em.persist(aDTable);
            em.flush();
        } else {
            aDTable = TestUtil.findAll(em, ADTable.class).get(0);
        }
        aDTab.setAdTable(aDTable);
        // Add required entity
        ADWindow aDWindow;
        if (TestUtil.findAll(em, ADWindow.class).isEmpty()) {
            aDWindow = ADWindowResourceIT.createUpdatedEntity(em);
            em.persist(aDWindow);
            em.flush();
        } else {
            aDWindow = TestUtil.findAll(em, ADWindow.class).get(0);
        }
        aDTab.setAdWindow(aDWindow);
        return aDTab;
    }

    @BeforeEach
    public void initTest() {
        aDTab = createEntity(em);
    }

    @Test
    @Transactional
    public void createADTab() throws Exception {
        int databaseSizeBeforeCreate = aDTabRepository.findAll().size();

        // Create the ADTab
        ADTabDTO aDTabDTO = aDTabMapper.toDto(aDTab);
        restADTabMockMvc.perform(post("/api/ad-tabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTabDTO)))
            .andExpect(status().isCreated());

        // Validate the ADTab in the database
        List<ADTab> aDTabList = aDTabRepository.findAll();
        assertThat(aDTabList).hasSize(databaseSizeBeforeCreate + 1);
        ADTab testADTab = aDTabList.get(aDTabList.size() - 1);
        assertThat(testADTab.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADTab.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADTab.getTargetEndpoint()).isEqualTo(DEFAULT_TARGET_ENDPOINT);
        assertThat(testADTab.isWritable()).isEqualTo(DEFAULT_WRITABLE);
        assertThat(testADTab.getDisplayLogic()).isEqualTo(DEFAULT_DISPLAY_LOGIC);
        assertThat(testADTab.getReadOnlyLogic()).isEqualTo(DEFAULT_READ_ONLY_LOGIC);
        assertThat(testADTab.getFilterQuery()).isEqualTo(DEFAULT_FILTER_QUERY);
        assertThat(testADTab.getOrderQuery()).isEqualTo(DEFAULT_ORDER_QUERY);
        assertThat(testADTab.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADTabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDTabRepository.findAll().size();

        // Create the ADTab with an existing ID
        aDTab.setId(1L);
        ADTabDTO aDTabDTO = aDTabMapper.toDto(aDTab);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADTabMockMvc.perform(post("/api/ad-tabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTabDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADTab in the database
        List<ADTab> aDTabList = aDTabRepository.findAll();
        assertThat(aDTabList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDTabRepository.findAll().size();
        // set the field null
        aDTab.setName(null);

        // Create the ADTab, which fails.
        ADTabDTO aDTabDTO = aDTabMapper.toDto(aDTab);

        restADTabMockMvc.perform(post("/api/ad-tabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTabDTO)))
            .andExpect(status().isBadRequest());

        List<ADTab> aDTabList = aDTabRepository.findAll();
        assertThat(aDTabList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADTabs() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList
        restADTabMockMvc.perform(get("/api/ad-tabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDTab.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].targetEndpoint").value(hasItem(DEFAULT_TARGET_ENDPOINT)))
            .andExpect(jsonPath("$.[*].writable").value(hasItem(DEFAULT_WRITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].displayLogic").value(hasItem(DEFAULT_DISPLAY_LOGIC)))
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].filterQuery").value(hasItem(DEFAULT_FILTER_QUERY)))
            .andExpect(jsonPath("$.[*].orderQuery").value(hasItem(DEFAULT_ORDER_QUERY)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADTab() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get the aDTab
        restADTabMockMvc.perform(get("/api/ad-tabs/{id}", aDTab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDTab.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.targetEndpoint").value(DEFAULT_TARGET_ENDPOINT))
            .andExpect(jsonPath("$.writable").value(DEFAULT_WRITABLE.booleanValue()))
            .andExpect(jsonPath("$.displayLogic").value(DEFAULT_DISPLAY_LOGIC))
            .andExpect(jsonPath("$.readOnlyLogic").value(DEFAULT_READ_ONLY_LOGIC))
            .andExpect(jsonPath("$.filterQuery").value(DEFAULT_FILTER_QUERY))
            .andExpect(jsonPath("$.orderQuery").value(DEFAULT_ORDER_QUERY))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADTabsByIdFiltering() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        Long id = aDTab.getId();

        defaultADTabShouldBeFound("id.equals=" + id);
        defaultADTabShouldNotBeFound("id.notEquals=" + id);

        defaultADTabShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADTabShouldNotBeFound("id.greaterThan=" + id);

        defaultADTabShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADTabShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADTabsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where name equals to DEFAULT_NAME
        defaultADTabShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDTabList where name equals to UPDATED_NAME
        defaultADTabShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTabsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where name not equals to DEFAULT_NAME
        defaultADTabShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDTabList where name not equals to UPDATED_NAME
        defaultADTabShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTabsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADTabShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDTabList where name equals to UPDATED_NAME
        defaultADTabShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTabsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where name is not null
        defaultADTabShouldBeFound("name.specified=true");

        // Get all the aDTabList where name is null
        defaultADTabShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where name contains DEFAULT_NAME
        defaultADTabShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDTabList where name contains UPDATED_NAME
        defaultADTabShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADTabsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where name does not contain DEFAULT_NAME
        defaultADTabShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDTabList where name does not contain UPDATED_NAME
        defaultADTabShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADTabsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where description equals to DEFAULT_DESCRIPTION
        defaultADTabShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDTabList where description equals to UPDATED_DESCRIPTION
        defaultADTabShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADTabsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where description not equals to DEFAULT_DESCRIPTION
        defaultADTabShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDTabList where description not equals to UPDATED_DESCRIPTION
        defaultADTabShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADTabsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADTabShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDTabList where description equals to UPDATED_DESCRIPTION
        defaultADTabShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADTabsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where description is not null
        defaultADTabShouldBeFound("description.specified=true");

        // Get all the aDTabList where description is null
        defaultADTabShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where description contains DEFAULT_DESCRIPTION
        defaultADTabShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDTabList where description contains UPDATED_DESCRIPTION
        defaultADTabShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADTabsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where description does not contain DEFAULT_DESCRIPTION
        defaultADTabShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDTabList where description does not contain UPDATED_DESCRIPTION
        defaultADTabShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADTabsByTargetEndpointIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where targetEndpoint equals to DEFAULT_TARGET_ENDPOINT
        defaultADTabShouldBeFound("targetEndpoint.equals=" + DEFAULT_TARGET_ENDPOINT);

        // Get all the aDTabList where targetEndpoint equals to UPDATED_TARGET_ENDPOINT
        defaultADTabShouldNotBeFound("targetEndpoint.equals=" + UPDATED_TARGET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllADTabsByTargetEndpointIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where targetEndpoint not equals to DEFAULT_TARGET_ENDPOINT
        defaultADTabShouldNotBeFound("targetEndpoint.notEquals=" + DEFAULT_TARGET_ENDPOINT);

        // Get all the aDTabList where targetEndpoint not equals to UPDATED_TARGET_ENDPOINT
        defaultADTabShouldBeFound("targetEndpoint.notEquals=" + UPDATED_TARGET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllADTabsByTargetEndpointIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where targetEndpoint in DEFAULT_TARGET_ENDPOINT or UPDATED_TARGET_ENDPOINT
        defaultADTabShouldBeFound("targetEndpoint.in=" + DEFAULT_TARGET_ENDPOINT + "," + UPDATED_TARGET_ENDPOINT);

        // Get all the aDTabList where targetEndpoint equals to UPDATED_TARGET_ENDPOINT
        defaultADTabShouldNotBeFound("targetEndpoint.in=" + UPDATED_TARGET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllADTabsByTargetEndpointIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where targetEndpoint is not null
        defaultADTabShouldBeFound("targetEndpoint.specified=true");

        // Get all the aDTabList where targetEndpoint is null
        defaultADTabShouldNotBeFound("targetEndpoint.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByTargetEndpointContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where targetEndpoint contains DEFAULT_TARGET_ENDPOINT
        defaultADTabShouldBeFound("targetEndpoint.contains=" + DEFAULT_TARGET_ENDPOINT);

        // Get all the aDTabList where targetEndpoint contains UPDATED_TARGET_ENDPOINT
        defaultADTabShouldNotBeFound("targetEndpoint.contains=" + UPDATED_TARGET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllADTabsByTargetEndpointNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where targetEndpoint does not contain DEFAULT_TARGET_ENDPOINT
        defaultADTabShouldNotBeFound("targetEndpoint.doesNotContain=" + DEFAULT_TARGET_ENDPOINT);

        // Get all the aDTabList where targetEndpoint does not contain UPDATED_TARGET_ENDPOINT
        defaultADTabShouldBeFound("targetEndpoint.doesNotContain=" + UPDATED_TARGET_ENDPOINT);
    }


    @Test
    @Transactional
    public void getAllADTabsByWritableIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where writable equals to DEFAULT_WRITABLE
        defaultADTabShouldBeFound("writable.equals=" + DEFAULT_WRITABLE);

        // Get all the aDTabList where writable equals to UPDATED_WRITABLE
        defaultADTabShouldNotBeFound("writable.equals=" + UPDATED_WRITABLE);
    }

    @Test
    @Transactional
    public void getAllADTabsByWritableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where writable not equals to DEFAULT_WRITABLE
        defaultADTabShouldNotBeFound("writable.notEquals=" + DEFAULT_WRITABLE);

        // Get all the aDTabList where writable not equals to UPDATED_WRITABLE
        defaultADTabShouldBeFound("writable.notEquals=" + UPDATED_WRITABLE);
    }

    @Test
    @Transactional
    public void getAllADTabsByWritableIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where writable in DEFAULT_WRITABLE or UPDATED_WRITABLE
        defaultADTabShouldBeFound("writable.in=" + DEFAULT_WRITABLE + "," + UPDATED_WRITABLE);

        // Get all the aDTabList where writable equals to UPDATED_WRITABLE
        defaultADTabShouldNotBeFound("writable.in=" + UPDATED_WRITABLE);
    }

    @Test
    @Transactional
    public void getAllADTabsByWritableIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where writable is not null
        defaultADTabShouldBeFound("writable.specified=true");

        // Get all the aDTabList where writable is null
        defaultADTabShouldNotBeFound("writable.specified=false");
    }

    @Test
    @Transactional
    public void getAllADTabsByDisplayLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where displayLogic equals to DEFAULT_DISPLAY_LOGIC
        defaultADTabShouldBeFound("displayLogic.equals=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDTabList where displayLogic equals to UPDATED_DISPLAY_LOGIC
        defaultADTabShouldNotBeFound("displayLogic.equals=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByDisplayLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where displayLogic not equals to DEFAULT_DISPLAY_LOGIC
        defaultADTabShouldNotBeFound("displayLogic.notEquals=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDTabList where displayLogic not equals to UPDATED_DISPLAY_LOGIC
        defaultADTabShouldBeFound("displayLogic.notEquals=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByDisplayLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where displayLogic in DEFAULT_DISPLAY_LOGIC or UPDATED_DISPLAY_LOGIC
        defaultADTabShouldBeFound("displayLogic.in=" + DEFAULT_DISPLAY_LOGIC + "," + UPDATED_DISPLAY_LOGIC);

        // Get all the aDTabList where displayLogic equals to UPDATED_DISPLAY_LOGIC
        defaultADTabShouldNotBeFound("displayLogic.in=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByDisplayLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where displayLogic is not null
        defaultADTabShouldBeFound("displayLogic.specified=true");

        // Get all the aDTabList where displayLogic is null
        defaultADTabShouldNotBeFound("displayLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByDisplayLogicContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where displayLogic contains DEFAULT_DISPLAY_LOGIC
        defaultADTabShouldBeFound("displayLogic.contains=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDTabList where displayLogic contains UPDATED_DISPLAY_LOGIC
        defaultADTabShouldNotBeFound("displayLogic.contains=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByDisplayLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where displayLogic does not contain DEFAULT_DISPLAY_LOGIC
        defaultADTabShouldNotBeFound("displayLogic.doesNotContain=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDTabList where displayLogic does not contain UPDATED_DISPLAY_LOGIC
        defaultADTabShouldBeFound("displayLogic.doesNotContain=" + UPDATED_DISPLAY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADTabsByReadOnlyLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where readOnlyLogic equals to DEFAULT_READ_ONLY_LOGIC
        defaultADTabShouldBeFound("readOnlyLogic.equals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDTabList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultADTabShouldNotBeFound("readOnlyLogic.equals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByReadOnlyLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where readOnlyLogic not equals to DEFAULT_READ_ONLY_LOGIC
        defaultADTabShouldNotBeFound("readOnlyLogic.notEquals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDTabList where readOnlyLogic not equals to UPDATED_READ_ONLY_LOGIC
        defaultADTabShouldBeFound("readOnlyLogic.notEquals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByReadOnlyLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where readOnlyLogic in DEFAULT_READ_ONLY_LOGIC or UPDATED_READ_ONLY_LOGIC
        defaultADTabShouldBeFound("readOnlyLogic.in=" + DEFAULT_READ_ONLY_LOGIC + "," + UPDATED_READ_ONLY_LOGIC);

        // Get all the aDTabList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultADTabShouldNotBeFound("readOnlyLogic.in=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByReadOnlyLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where readOnlyLogic is not null
        defaultADTabShouldBeFound("readOnlyLogic.specified=true");

        // Get all the aDTabList where readOnlyLogic is null
        defaultADTabShouldNotBeFound("readOnlyLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByReadOnlyLogicContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where readOnlyLogic contains DEFAULT_READ_ONLY_LOGIC
        defaultADTabShouldBeFound("readOnlyLogic.contains=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDTabList where readOnlyLogic contains UPDATED_READ_ONLY_LOGIC
        defaultADTabShouldNotBeFound("readOnlyLogic.contains=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADTabsByReadOnlyLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where readOnlyLogic does not contain DEFAULT_READ_ONLY_LOGIC
        defaultADTabShouldNotBeFound("readOnlyLogic.doesNotContain=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDTabList where readOnlyLogic does not contain UPDATED_READ_ONLY_LOGIC
        defaultADTabShouldBeFound("readOnlyLogic.doesNotContain=" + UPDATED_READ_ONLY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADTabsByFilterQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where filterQuery equals to DEFAULT_FILTER_QUERY
        defaultADTabShouldBeFound("filterQuery.equals=" + DEFAULT_FILTER_QUERY);

        // Get all the aDTabList where filterQuery equals to UPDATED_FILTER_QUERY
        defaultADTabShouldNotBeFound("filterQuery.equals=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByFilterQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where filterQuery not equals to DEFAULT_FILTER_QUERY
        defaultADTabShouldNotBeFound("filterQuery.notEquals=" + DEFAULT_FILTER_QUERY);

        // Get all the aDTabList where filterQuery not equals to UPDATED_FILTER_QUERY
        defaultADTabShouldBeFound("filterQuery.notEquals=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByFilterQueryIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where filterQuery in DEFAULT_FILTER_QUERY or UPDATED_FILTER_QUERY
        defaultADTabShouldBeFound("filterQuery.in=" + DEFAULT_FILTER_QUERY + "," + UPDATED_FILTER_QUERY);

        // Get all the aDTabList where filterQuery equals to UPDATED_FILTER_QUERY
        defaultADTabShouldNotBeFound("filterQuery.in=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByFilterQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where filterQuery is not null
        defaultADTabShouldBeFound("filterQuery.specified=true");

        // Get all the aDTabList where filterQuery is null
        defaultADTabShouldNotBeFound("filterQuery.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByFilterQueryContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where filterQuery contains DEFAULT_FILTER_QUERY
        defaultADTabShouldBeFound("filterQuery.contains=" + DEFAULT_FILTER_QUERY);

        // Get all the aDTabList where filterQuery contains UPDATED_FILTER_QUERY
        defaultADTabShouldNotBeFound("filterQuery.contains=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByFilterQueryNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where filterQuery does not contain DEFAULT_FILTER_QUERY
        defaultADTabShouldNotBeFound("filterQuery.doesNotContain=" + DEFAULT_FILTER_QUERY);

        // Get all the aDTabList where filterQuery does not contain UPDATED_FILTER_QUERY
        defaultADTabShouldBeFound("filterQuery.doesNotContain=" + UPDATED_FILTER_QUERY);
    }


    @Test
    @Transactional
    public void getAllADTabsByOrderQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where orderQuery equals to DEFAULT_ORDER_QUERY
        defaultADTabShouldBeFound("orderQuery.equals=" + DEFAULT_ORDER_QUERY);

        // Get all the aDTabList where orderQuery equals to UPDATED_ORDER_QUERY
        defaultADTabShouldNotBeFound("orderQuery.equals=" + UPDATED_ORDER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByOrderQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where orderQuery not equals to DEFAULT_ORDER_QUERY
        defaultADTabShouldNotBeFound("orderQuery.notEquals=" + DEFAULT_ORDER_QUERY);

        // Get all the aDTabList where orderQuery not equals to UPDATED_ORDER_QUERY
        defaultADTabShouldBeFound("orderQuery.notEquals=" + UPDATED_ORDER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByOrderQueryIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where orderQuery in DEFAULT_ORDER_QUERY or UPDATED_ORDER_QUERY
        defaultADTabShouldBeFound("orderQuery.in=" + DEFAULT_ORDER_QUERY + "," + UPDATED_ORDER_QUERY);

        // Get all the aDTabList where orderQuery equals to UPDATED_ORDER_QUERY
        defaultADTabShouldNotBeFound("orderQuery.in=" + UPDATED_ORDER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByOrderQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where orderQuery is not null
        defaultADTabShouldBeFound("orderQuery.specified=true");

        // Get all the aDTabList where orderQuery is null
        defaultADTabShouldNotBeFound("orderQuery.specified=false");
    }
                @Test
    @Transactional
    public void getAllADTabsByOrderQueryContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where orderQuery contains DEFAULT_ORDER_QUERY
        defaultADTabShouldBeFound("orderQuery.contains=" + DEFAULT_ORDER_QUERY);

        // Get all the aDTabList where orderQuery contains UPDATED_ORDER_QUERY
        defaultADTabShouldNotBeFound("orderQuery.contains=" + UPDATED_ORDER_QUERY);
    }

    @Test
    @Transactional
    public void getAllADTabsByOrderQueryNotContainsSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where orderQuery does not contain DEFAULT_ORDER_QUERY
        defaultADTabShouldNotBeFound("orderQuery.doesNotContain=" + DEFAULT_ORDER_QUERY);

        // Get all the aDTabList where orderQuery does not contain UPDATED_ORDER_QUERY
        defaultADTabShouldBeFound("orderQuery.doesNotContain=" + UPDATED_ORDER_QUERY);
    }


    @Test
    @Transactional
    public void getAllADTabsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where active equals to DEFAULT_ACTIVE
        defaultADTabShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDTabList where active equals to UPDATED_ACTIVE
        defaultADTabShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADTabsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where active not equals to DEFAULT_ACTIVE
        defaultADTabShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDTabList where active not equals to UPDATED_ACTIVE
        defaultADTabShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADTabsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADTabShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDTabList where active equals to UPDATED_ACTIVE
        defaultADTabShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADTabsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        // Get all the aDTabList where active is not null
        defaultADTabShouldBeFound("active.specified=true");

        // Get all the aDTabList where active is null
        defaultADTabShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADTabsByADTabIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);
        ADTab aDTab = ADTabResourceIT.createEntity(em);
        em.persist(aDTab);
        em.flush();
        aDTab.addADTab(aDTab);
        aDTabRepository.saveAndFlush(aDTab);
        Long aDTabId = aDTab.getId();

        // Get all the aDTabList where aDTab equals to aDTabId
        defaultADTabShouldBeFound("aDTabId.equals=" + aDTabId);

        // Get all the aDTabList where aDTab equals to aDTabId + 1
        defaultADTabShouldNotBeFound("aDTabId.equals=" + (aDTabId + 1));
    }


    @Test
    @Transactional
    public void getAllADTabsByAdClientIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADClient adClient = aDTab.getAdClient();
        aDTabRepository.saveAndFlush(aDTab);
        Long adClientId = adClient.getId();

        // Get all the aDTabList where adClient equals to adClientId
        defaultADTabShouldBeFound("adClientId.equals=" + adClientId);

        // Get all the aDTabList where adClient equals to adClientId + 1
        defaultADTabShouldNotBeFound("adClientId.equals=" + (adClientId + 1));
    }


    @Test
    @Transactional
    public void getAllADTabsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDTab.getAdOrganization();
        aDTabRepository.saveAndFlush(aDTab);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDTabList where adOrganization equals to adOrganizationId
        defaultADTabShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDTabList where adOrganization equals to adOrganizationId + 1
        defaultADTabShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllADTabsByAdTableIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADTable adTable = aDTab.getAdTable();
        aDTabRepository.saveAndFlush(aDTab);
        Long adTableId = adTable.getId();

        // Get all the aDTabList where adTable equals to adTableId
        defaultADTabShouldBeFound("adTableId.equals=" + adTableId);

        // Get all the aDTabList where adTable equals to adTableId + 1
        defaultADTabShouldNotBeFound("adTableId.equals=" + (adTableId + 1));
    }


    @Test
    @Transactional
    public void getAllADTabsByAdWindowIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADWindow adWindow = aDTab.getAdWindow();
        aDTabRepository.saveAndFlush(aDTab);
        Long adWindowId = adWindow.getId();

        // Get all the aDTabList where adWindow equals to adWindowId
        defaultADTabShouldBeFound("adWindowId.equals=" + adWindowId);

        // Get all the aDTabList where adWindow equals to adWindowId + 1
        defaultADTabShouldNotBeFound("adWindowId.equals=" + (adWindowId + 1));
    }


    @Test
    @Transactional
    public void getAllADTabsByParentTabIsEqualToSomething() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);
        ADTab parentTab = ADTabResourceIT.createEntity(em);
        em.persist(parentTab);
        em.flush();
        aDTab.setParentTab(parentTab);
        aDTabRepository.saveAndFlush(aDTab);
        Long parentTabId = parentTab.getId();

        // Get all the aDTabList where parentTab equals to parentTabId
        defaultADTabShouldBeFound("parentTabId.equals=" + parentTabId);

        // Get all the aDTabList where parentTab equals to parentTabId + 1
        defaultADTabShouldNotBeFound("parentTabId.equals=" + (parentTabId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADTabShouldBeFound(String filter) throws Exception {
        restADTabMockMvc.perform(get("/api/ad-tabs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDTab.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].targetEndpoint").value(hasItem(DEFAULT_TARGET_ENDPOINT)))
            .andExpect(jsonPath("$.[*].writable").value(hasItem(DEFAULT_WRITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].displayLogic").value(hasItem(DEFAULT_DISPLAY_LOGIC)))
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].filterQuery").value(hasItem(DEFAULT_FILTER_QUERY)))
            .andExpect(jsonPath("$.[*].orderQuery").value(hasItem(DEFAULT_ORDER_QUERY)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADTabMockMvc.perform(get("/api/ad-tabs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADTabShouldNotBeFound(String filter) throws Exception {
        restADTabMockMvc.perform(get("/api/ad-tabs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADTabMockMvc.perform(get("/api/ad-tabs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADTab() throws Exception {
        // Get the aDTab
        restADTabMockMvc.perform(get("/api/ad-tabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADTab() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        int databaseSizeBeforeUpdate = aDTabRepository.findAll().size();

        // Update the aDTab
        ADTab updatedADTab = aDTabRepository.findById(aDTab.getId()).get();
        // Disconnect from session so that the updates on updatedADTab are not directly saved in db
        em.detach(updatedADTab);
        updatedADTab
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .targetEndpoint(UPDATED_TARGET_ENDPOINT)
            .writable(UPDATED_WRITABLE)
            .displayLogic(UPDATED_DISPLAY_LOGIC)
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .filterQuery(UPDATED_FILTER_QUERY)
            .orderQuery(UPDATED_ORDER_QUERY)
            .active(UPDATED_ACTIVE);
        ADTabDTO aDTabDTO = aDTabMapper.toDto(updatedADTab);

        restADTabMockMvc.perform(put("/api/ad-tabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTabDTO)))
            .andExpect(status().isOk());

        // Validate the ADTab in the database
        List<ADTab> aDTabList = aDTabRepository.findAll();
        assertThat(aDTabList).hasSize(databaseSizeBeforeUpdate);
        ADTab testADTab = aDTabList.get(aDTabList.size() - 1);
        assertThat(testADTab.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADTab.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADTab.getTargetEndpoint()).isEqualTo(UPDATED_TARGET_ENDPOINT);
        assertThat(testADTab.isWritable()).isEqualTo(UPDATED_WRITABLE);
        assertThat(testADTab.getDisplayLogic()).isEqualTo(UPDATED_DISPLAY_LOGIC);
        assertThat(testADTab.getReadOnlyLogic()).isEqualTo(UPDATED_READ_ONLY_LOGIC);
        assertThat(testADTab.getFilterQuery()).isEqualTo(UPDATED_FILTER_QUERY);
        assertThat(testADTab.getOrderQuery()).isEqualTo(UPDATED_ORDER_QUERY);
        assertThat(testADTab.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADTab() throws Exception {
        int databaseSizeBeforeUpdate = aDTabRepository.findAll().size();

        // Create the ADTab
        ADTabDTO aDTabDTO = aDTabMapper.toDto(aDTab);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADTabMockMvc.perform(put("/api/ad-tabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDTabDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADTab in the database
        List<ADTab> aDTabList = aDTabRepository.findAll();
        assertThat(aDTabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADTab() throws Exception {
        // Initialize the database
        aDTabRepository.saveAndFlush(aDTab);

        int databaseSizeBeforeDelete = aDTabRepository.findAll().size();

        // Delete the aDTab
        restADTabMockMvc.perform(delete("/api/ad-tabs/{id}", aDTab.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADTab> aDTabList = aDTabRepository.findAll();
        assertThat(aDTabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
