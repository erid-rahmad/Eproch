package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdWatchListItem;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.repository.AdWatchListItemRepository;
import com.bhp.opusb.service.AdWatchListItemService;
import com.bhp.opusb.service.dto.AdWatchListItemDTO;
import com.bhp.opusb.service.mapper.AdWatchListItemMapper;
import com.bhp.opusb.service.dto.AdWatchListItemCriteria;
import com.bhp.opusb.service.AdWatchListItemQueryService;

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

import com.bhp.opusb.domain.enumeration.AdWatchListActionType;
/**
 * Integration tests for the {@link AdWatchListItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdWatchListItemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REST_API_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_REST_API_ENDPOINT = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSOCKET_ENDPOINT = "AAAAAAAAAA";
    private static final String UPDATED_WEBSOCKET_ENDPOINT = "BBBBBBBBBB";

    private static final AdWatchListActionType DEFAULT_ACTION_TYPE = AdWatchListActionType.MENU;
    private static final AdWatchListActionType UPDATED_ACTION_TYPE = AdWatchListActionType.URL;

    private static final String DEFAULT_ACTION_URL = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_FILTER_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_FILTER_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_ACCENT_COLOR = "AAAAAAA";
    private static final String UPDATED_ACCENT_COLOR = "BBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AdWatchListItemRepository adWatchListItemRepository;

    @Autowired
    private AdWatchListItemMapper adWatchListItemMapper;

    @Autowired
    private AdWatchListItemService adWatchListItemService;

    @Autowired
    private AdWatchListItemQueryService adWatchListItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdWatchListItemMockMvc;

    private AdWatchListItem adWatchListItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdWatchListItem createEntity(EntityManager em) {
        AdWatchListItem adWatchListItem = new AdWatchListItem()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .serviceName(DEFAULT_SERVICE_NAME)
            .restApiEndpoint(DEFAULT_REST_API_ENDPOINT)
            .websocketEndpoint(DEFAULT_WEBSOCKET_ENDPOINT)
            .actionType(DEFAULT_ACTION_TYPE)
            .actionUrl(DEFAULT_ACTION_URL)
            .filterQuery(DEFAULT_FILTER_QUERY)
            .accentColor(DEFAULT_ACCENT_COLOR)
            .icon(DEFAULT_ICON)
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
        adWatchListItem.setAdOrganization(aDOrganization);
        return adWatchListItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdWatchListItem createUpdatedEntity(EntityManager em) {
        AdWatchListItem adWatchListItem = new AdWatchListItem()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .serviceName(UPDATED_SERVICE_NAME)
            .restApiEndpoint(UPDATED_REST_API_ENDPOINT)
            .websocketEndpoint(UPDATED_WEBSOCKET_ENDPOINT)
            .actionType(UPDATED_ACTION_TYPE)
            .actionUrl(UPDATED_ACTION_URL)
            .filterQuery(UPDATED_FILTER_QUERY)
            .accentColor(UPDATED_ACCENT_COLOR)
            .icon(UPDATED_ICON)
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
        adWatchListItem.setAdOrganization(aDOrganization);
        return adWatchListItem;
    }

    @BeforeEach
    public void initTest() {
        adWatchListItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdWatchListItem() throws Exception {
        int databaseSizeBeforeCreate = adWatchListItemRepository.findAll().size();

        // Create the AdWatchListItem
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(adWatchListItem);
        restAdWatchListItemMockMvc.perform(post("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isCreated());

        // Validate the AdWatchListItem in the database
        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeCreate + 1);
        AdWatchListItem testAdWatchListItem = adWatchListItemList.get(adWatchListItemList.size() - 1);
        assertThat(testAdWatchListItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAdWatchListItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdWatchListItem.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdWatchListItem.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
        assertThat(testAdWatchListItem.getRestApiEndpoint()).isEqualTo(DEFAULT_REST_API_ENDPOINT);
        assertThat(testAdWatchListItem.getWebsocketEndpoint()).isEqualTo(DEFAULT_WEBSOCKET_ENDPOINT);
        assertThat(testAdWatchListItem.getActionType()).isEqualTo(DEFAULT_ACTION_TYPE);
        assertThat(testAdWatchListItem.getActionUrl()).isEqualTo(DEFAULT_ACTION_URL);
        assertThat(testAdWatchListItem.getFilterQuery()).isEqualTo(DEFAULT_FILTER_QUERY);
        assertThat(testAdWatchListItem.getAccentColor()).isEqualTo(DEFAULT_ACCENT_COLOR);
        assertThat(testAdWatchListItem.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testAdWatchListItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdWatchListItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAdWatchListItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adWatchListItemRepository.findAll().size();

        // Create the AdWatchListItem with an existing ID
        adWatchListItem.setId(1L);
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(adWatchListItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdWatchListItemMockMvc.perform(post("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdWatchListItem in the database
        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adWatchListItemRepository.findAll().size();
        // set the field null
        adWatchListItem.setCode(null);

        // Create the AdWatchListItem, which fails.
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(adWatchListItem);

        restAdWatchListItemMockMvc.perform(post("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isBadRequest());

        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adWatchListItemRepository.findAll().size();
        // set the field null
        adWatchListItem.setName(null);

        // Create the AdWatchListItem, which fails.
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(adWatchListItem);

        restAdWatchListItemMockMvc.perform(post("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isBadRequest());

        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = adWatchListItemRepository.findAll().size();
        // set the field null
        adWatchListItem.setActionType(null);

        // Create the AdWatchListItem, which fails.
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(adWatchListItem);

        restAdWatchListItemMockMvc.perform(post("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isBadRequest());

        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItems() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adWatchListItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].restApiEndpoint").value(hasItem(DEFAULT_REST_API_ENDPOINT)))
            .andExpect(jsonPath("$.[*].websocketEndpoint").value(hasItem(DEFAULT_WEBSOCKET_ENDPOINT)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionUrl").value(hasItem(DEFAULT_ACTION_URL)))
            .andExpect(jsonPath("$.[*].filterQuery").value(hasItem(DEFAULT_FILTER_QUERY)))
            .andExpect(jsonPath("$.[*].accentColor").value(hasItem(DEFAULT_ACCENT_COLOR)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdWatchListItem() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get the adWatchListItem
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items/{id}", adWatchListItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adWatchListItem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.serviceName").value(DEFAULT_SERVICE_NAME))
            .andExpect(jsonPath("$.restApiEndpoint").value(DEFAULT_REST_API_ENDPOINT))
            .andExpect(jsonPath("$.websocketEndpoint").value(DEFAULT_WEBSOCKET_ENDPOINT))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.actionUrl").value(DEFAULT_ACTION_URL))
            .andExpect(jsonPath("$.filterQuery").value(DEFAULT_FILTER_QUERY))
            .andExpect(jsonPath("$.accentColor").value(DEFAULT_ACCENT_COLOR))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdWatchListItemsByIdFiltering() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        Long id = adWatchListItem.getId();

        defaultAdWatchListItemShouldBeFound("id.equals=" + id);
        defaultAdWatchListItemShouldNotBeFound("id.notEquals=" + id);

        defaultAdWatchListItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdWatchListItemShouldNotBeFound("id.greaterThan=" + id);

        defaultAdWatchListItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdWatchListItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where code equals to DEFAULT_CODE
        defaultAdWatchListItemShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the adWatchListItemList where code equals to UPDATED_CODE
        defaultAdWatchListItemShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where code not equals to DEFAULT_CODE
        defaultAdWatchListItemShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the adWatchListItemList where code not equals to UPDATED_CODE
        defaultAdWatchListItemShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where code in DEFAULT_CODE or UPDATED_CODE
        defaultAdWatchListItemShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the adWatchListItemList where code equals to UPDATED_CODE
        defaultAdWatchListItemShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where code is not null
        defaultAdWatchListItemShouldBeFound("code.specified=true");

        // Get all the adWatchListItemList where code is null
        defaultAdWatchListItemShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByCodeContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where code contains DEFAULT_CODE
        defaultAdWatchListItemShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the adWatchListItemList where code contains UPDATED_CODE
        defaultAdWatchListItemShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where code does not contain DEFAULT_CODE
        defaultAdWatchListItemShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the adWatchListItemList where code does not contain UPDATED_CODE
        defaultAdWatchListItemShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where name equals to DEFAULT_NAME
        defaultAdWatchListItemShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adWatchListItemList where name equals to UPDATED_NAME
        defaultAdWatchListItemShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where name not equals to DEFAULT_NAME
        defaultAdWatchListItemShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adWatchListItemList where name not equals to UPDATED_NAME
        defaultAdWatchListItemShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdWatchListItemShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adWatchListItemList where name equals to UPDATED_NAME
        defaultAdWatchListItemShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where name is not null
        defaultAdWatchListItemShouldBeFound("name.specified=true");

        // Get all the adWatchListItemList where name is null
        defaultAdWatchListItemShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where name contains DEFAULT_NAME
        defaultAdWatchListItemShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adWatchListItemList where name contains UPDATED_NAME
        defaultAdWatchListItemShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where name does not contain DEFAULT_NAME
        defaultAdWatchListItemShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adWatchListItemList where name does not contain UPDATED_NAME
        defaultAdWatchListItemShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where description equals to DEFAULT_DESCRIPTION
        defaultAdWatchListItemShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListItemList where description equals to UPDATED_DESCRIPTION
        defaultAdWatchListItemShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where description not equals to DEFAULT_DESCRIPTION
        defaultAdWatchListItemShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListItemList where description not equals to UPDATED_DESCRIPTION
        defaultAdWatchListItemShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdWatchListItemShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adWatchListItemList where description equals to UPDATED_DESCRIPTION
        defaultAdWatchListItemShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where description is not null
        defaultAdWatchListItemShouldBeFound("description.specified=true");

        // Get all the adWatchListItemList where description is null
        defaultAdWatchListItemShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where description contains DEFAULT_DESCRIPTION
        defaultAdWatchListItemShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListItemList where description contains UPDATED_DESCRIPTION
        defaultAdWatchListItemShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where description does not contain DEFAULT_DESCRIPTION
        defaultAdWatchListItemShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adWatchListItemList where description does not contain UPDATED_DESCRIPTION
        defaultAdWatchListItemShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByServiceNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where serviceName equals to DEFAULT_SERVICE_NAME
        defaultAdWatchListItemShouldBeFound("serviceName.equals=" + DEFAULT_SERVICE_NAME);

        // Get all the adWatchListItemList where serviceName equals to UPDATED_SERVICE_NAME
        defaultAdWatchListItemShouldNotBeFound("serviceName.equals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByServiceNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where serviceName not equals to DEFAULT_SERVICE_NAME
        defaultAdWatchListItemShouldNotBeFound("serviceName.notEquals=" + DEFAULT_SERVICE_NAME);

        // Get all the adWatchListItemList where serviceName not equals to UPDATED_SERVICE_NAME
        defaultAdWatchListItemShouldBeFound("serviceName.notEquals=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByServiceNameIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where serviceName in DEFAULT_SERVICE_NAME or UPDATED_SERVICE_NAME
        defaultAdWatchListItemShouldBeFound("serviceName.in=" + DEFAULT_SERVICE_NAME + "," + UPDATED_SERVICE_NAME);

        // Get all the adWatchListItemList where serviceName equals to UPDATED_SERVICE_NAME
        defaultAdWatchListItemShouldNotBeFound("serviceName.in=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByServiceNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where serviceName is not null
        defaultAdWatchListItemShouldBeFound("serviceName.specified=true");

        // Get all the adWatchListItemList where serviceName is null
        defaultAdWatchListItemShouldNotBeFound("serviceName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByServiceNameContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where serviceName contains DEFAULT_SERVICE_NAME
        defaultAdWatchListItemShouldBeFound("serviceName.contains=" + DEFAULT_SERVICE_NAME);

        // Get all the adWatchListItemList where serviceName contains UPDATED_SERVICE_NAME
        defaultAdWatchListItemShouldNotBeFound("serviceName.contains=" + UPDATED_SERVICE_NAME);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByServiceNameNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where serviceName does not contain DEFAULT_SERVICE_NAME
        defaultAdWatchListItemShouldNotBeFound("serviceName.doesNotContain=" + DEFAULT_SERVICE_NAME);

        // Get all the adWatchListItemList where serviceName does not contain UPDATED_SERVICE_NAME
        defaultAdWatchListItemShouldBeFound("serviceName.doesNotContain=" + UPDATED_SERVICE_NAME);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByRestApiEndpointIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where restApiEndpoint equals to DEFAULT_REST_API_ENDPOINT
        defaultAdWatchListItemShouldBeFound("restApiEndpoint.equals=" + DEFAULT_REST_API_ENDPOINT);

        // Get all the adWatchListItemList where restApiEndpoint equals to UPDATED_REST_API_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("restApiEndpoint.equals=" + UPDATED_REST_API_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByRestApiEndpointIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where restApiEndpoint not equals to DEFAULT_REST_API_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("restApiEndpoint.notEquals=" + DEFAULT_REST_API_ENDPOINT);

        // Get all the adWatchListItemList where restApiEndpoint not equals to UPDATED_REST_API_ENDPOINT
        defaultAdWatchListItemShouldBeFound("restApiEndpoint.notEquals=" + UPDATED_REST_API_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByRestApiEndpointIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where restApiEndpoint in DEFAULT_REST_API_ENDPOINT or UPDATED_REST_API_ENDPOINT
        defaultAdWatchListItemShouldBeFound("restApiEndpoint.in=" + DEFAULT_REST_API_ENDPOINT + "," + UPDATED_REST_API_ENDPOINT);

        // Get all the adWatchListItemList where restApiEndpoint equals to UPDATED_REST_API_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("restApiEndpoint.in=" + UPDATED_REST_API_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByRestApiEndpointIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where restApiEndpoint is not null
        defaultAdWatchListItemShouldBeFound("restApiEndpoint.specified=true");

        // Get all the adWatchListItemList where restApiEndpoint is null
        defaultAdWatchListItemShouldNotBeFound("restApiEndpoint.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByRestApiEndpointContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where restApiEndpoint contains DEFAULT_REST_API_ENDPOINT
        defaultAdWatchListItemShouldBeFound("restApiEndpoint.contains=" + DEFAULT_REST_API_ENDPOINT);

        // Get all the adWatchListItemList where restApiEndpoint contains UPDATED_REST_API_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("restApiEndpoint.contains=" + UPDATED_REST_API_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByRestApiEndpointNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where restApiEndpoint does not contain DEFAULT_REST_API_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("restApiEndpoint.doesNotContain=" + DEFAULT_REST_API_ENDPOINT);

        // Get all the adWatchListItemList where restApiEndpoint does not contain UPDATED_REST_API_ENDPOINT
        defaultAdWatchListItemShouldBeFound("restApiEndpoint.doesNotContain=" + UPDATED_REST_API_ENDPOINT);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByWebsocketEndpointIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where websocketEndpoint equals to DEFAULT_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldBeFound("websocketEndpoint.equals=" + DEFAULT_WEBSOCKET_ENDPOINT);

        // Get all the adWatchListItemList where websocketEndpoint equals to UPDATED_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("websocketEndpoint.equals=" + UPDATED_WEBSOCKET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByWebsocketEndpointIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where websocketEndpoint not equals to DEFAULT_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("websocketEndpoint.notEquals=" + DEFAULT_WEBSOCKET_ENDPOINT);

        // Get all the adWatchListItemList where websocketEndpoint not equals to UPDATED_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldBeFound("websocketEndpoint.notEquals=" + UPDATED_WEBSOCKET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByWebsocketEndpointIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where websocketEndpoint in DEFAULT_WEBSOCKET_ENDPOINT or UPDATED_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldBeFound("websocketEndpoint.in=" + DEFAULT_WEBSOCKET_ENDPOINT + "," + UPDATED_WEBSOCKET_ENDPOINT);

        // Get all the adWatchListItemList where websocketEndpoint equals to UPDATED_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("websocketEndpoint.in=" + UPDATED_WEBSOCKET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByWebsocketEndpointIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where websocketEndpoint is not null
        defaultAdWatchListItemShouldBeFound("websocketEndpoint.specified=true");

        // Get all the adWatchListItemList where websocketEndpoint is null
        defaultAdWatchListItemShouldNotBeFound("websocketEndpoint.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByWebsocketEndpointContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where websocketEndpoint contains DEFAULT_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldBeFound("websocketEndpoint.contains=" + DEFAULT_WEBSOCKET_ENDPOINT);

        // Get all the adWatchListItemList where websocketEndpoint contains UPDATED_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("websocketEndpoint.contains=" + UPDATED_WEBSOCKET_ENDPOINT);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByWebsocketEndpointNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where websocketEndpoint does not contain DEFAULT_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldNotBeFound("websocketEndpoint.doesNotContain=" + DEFAULT_WEBSOCKET_ENDPOINT);

        // Get all the adWatchListItemList where websocketEndpoint does not contain UPDATED_WEBSOCKET_ENDPOINT
        defaultAdWatchListItemShouldBeFound("websocketEndpoint.doesNotContain=" + UPDATED_WEBSOCKET_ENDPOINT);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionType equals to DEFAULT_ACTION_TYPE
        defaultAdWatchListItemShouldBeFound("actionType.equals=" + DEFAULT_ACTION_TYPE);

        // Get all the adWatchListItemList where actionType equals to UPDATED_ACTION_TYPE
        defaultAdWatchListItemShouldNotBeFound("actionType.equals=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionType not equals to DEFAULT_ACTION_TYPE
        defaultAdWatchListItemShouldNotBeFound("actionType.notEquals=" + DEFAULT_ACTION_TYPE);

        // Get all the adWatchListItemList where actionType not equals to UPDATED_ACTION_TYPE
        defaultAdWatchListItemShouldBeFound("actionType.notEquals=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionType in DEFAULT_ACTION_TYPE or UPDATED_ACTION_TYPE
        defaultAdWatchListItemShouldBeFound("actionType.in=" + DEFAULT_ACTION_TYPE + "," + UPDATED_ACTION_TYPE);

        // Get all the adWatchListItemList where actionType equals to UPDATED_ACTION_TYPE
        defaultAdWatchListItemShouldNotBeFound("actionType.in=" + UPDATED_ACTION_TYPE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionType is not null
        defaultAdWatchListItemShouldBeFound("actionType.specified=true");

        // Get all the adWatchListItemList where actionType is null
        defaultAdWatchListItemShouldNotBeFound("actionType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionUrl equals to DEFAULT_ACTION_URL
        defaultAdWatchListItemShouldBeFound("actionUrl.equals=" + DEFAULT_ACTION_URL);

        // Get all the adWatchListItemList where actionUrl equals to UPDATED_ACTION_URL
        defaultAdWatchListItemShouldNotBeFound("actionUrl.equals=" + UPDATED_ACTION_URL);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionUrl not equals to DEFAULT_ACTION_URL
        defaultAdWatchListItemShouldNotBeFound("actionUrl.notEquals=" + DEFAULT_ACTION_URL);

        // Get all the adWatchListItemList where actionUrl not equals to UPDATED_ACTION_URL
        defaultAdWatchListItemShouldBeFound("actionUrl.notEquals=" + UPDATED_ACTION_URL);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionUrlIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionUrl in DEFAULT_ACTION_URL or UPDATED_ACTION_URL
        defaultAdWatchListItemShouldBeFound("actionUrl.in=" + DEFAULT_ACTION_URL + "," + UPDATED_ACTION_URL);

        // Get all the adWatchListItemList where actionUrl equals to UPDATED_ACTION_URL
        defaultAdWatchListItemShouldNotBeFound("actionUrl.in=" + UPDATED_ACTION_URL);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionUrl is not null
        defaultAdWatchListItemShouldBeFound("actionUrl.specified=true");

        // Get all the adWatchListItemList where actionUrl is null
        defaultAdWatchListItemShouldNotBeFound("actionUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByActionUrlContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionUrl contains DEFAULT_ACTION_URL
        defaultAdWatchListItemShouldBeFound("actionUrl.contains=" + DEFAULT_ACTION_URL);

        // Get all the adWatchListItemList where actionUrl contains UPDATED_ACTION_URL
        defaultAdWatchListItemShouldNotBeFound("actionUrl.contains=" + UPDATED_ACTION_URL);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActionUrlNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where actionUrl does not contain DEFAULT_ACTION_URL
        defaultAdWatchListItemShouldNotBeFound("actionUrl.doesNotContain=" + DEFAULT_ACTION_URL);

        // Get all the adWatchListItemList where actionUrl does not contain UPDATED_ACTION_URL
        defaultAdWatchListItemShouldBeFound("actionUrl.doesNotContain=" + UPDATED_ACTION_URL);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByFilterQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where filterQuery equals to DEFAULT_FILTER_QUERY
        defaultAdWatchListItemShouldBeFound("filterQuery.equals=" + DEFAULT_FILTER_QUERY);

        // Get all the adWatchListItemList where filterQuery equals to UPDATED_FILTER_QUERY
        defaultAdWatchListItemShouldNotBeFound("filterQuery.equals=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByFilterQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where filterQuery not equals to DEFAULT_FILTER_QUERY
        defaultAdWatchListItemShouldNotBeFound("filterQuery.notEquals=" + DEFAULT_FILTER_QUERY);

        // Get all the adWatchListItemList where filterQuery not equals to UPDATED_FILTER_QUERY
        defaultAdWatchListItemShouldBeFound("filterQuery.notEquals=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByFilterQueryIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where filterQuery in DEFAULT_FILTER_QUERY or UPDATED_FILTER_QUERY
        defaultAdWatchListItemShouldBeFound("filterQuery.in=" + DEFAULT_FILTER_QUERY + "," + UPDATED_FILTER_QUERY);

        // Get all the adWatchListItemList where filterQuery equals to UPDATED_FILTER_QUERY
        defaultAdWatchListItemShouldNotBeFound("filterQuery.in=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByFilterQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where filterQuery is not null
        defaultAdWatchListItemShouldBeFound("filterQuery.specified=true");

        // Get all the adWatchListItemList where filterQuery is null
        defaultAdWatchListItemShouldNotBeFound("filterQuery.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByFilterQueryContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where filterQuery contains DEFAULT_FILTER_QUERY
        defaultAdWatchListItemShouldBeFound("filterQuery.contains=" + DEFAULT_FILTER_QUERY);

        // Get all the adWatchListItemList where filterQuery contains UPDATED_FILTER_QUERY
        defaultAdWatchListItemShouldNotBeFound("filterQuery.contains=" + UPDATED_FILTER_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByFilterQueryNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where filterQuery does not contain DEFAULT_FILTER_QUERY
        defaultAdWatchListItemShouldNotBeFound("filterQuery.doesNotContain=" + DEFAULT_FILTER_QUERY);

        // Get all the adWatchListItemList where filterQuery does not contain UPDATED_FILTER_QUERY
        defaultAdWatchListItemShouldBeFound("filterQuery.doesNotContain=" + UPDATED_FILTER_QUERY);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByAccentColorIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where accentColor equals to DEFAULT_ACCENT_COLOR
        defaultAdWatchListItemShouldBeFound("accentColor.equals=" + DEFAULT_ACCENT_COLOR);

        // Get all the adWatchListItemList where accentColor equals to UPDATED_ACCENT_COLOR
        defaultAdWatchListItemShouldNotBeFound("accentColor.equals=" + UPDATED_ACCENT_COLOR);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByAccentColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where accentColor not equals to DEFAULT_ACCENT_COLOR
        defaultAdWatchListItemShouldNotBeFound("accentColor.notEquals=" + DEFAULT_ACCENT_COLOR);

        // Get all the adWatchListItemList where accentColor not equals to UPDATED_ACCENT_COLOR
        defaultAdWatchListItemShouldBeFound("accentColor.notEquals=" + UPDATED_ACCENT_COLOR);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByAccentColorIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where accentColor in DEFAULT_ACCENT_COLOR or UPDATED_ACCENT_COLOR
        defaultAdWatchListItemShouldBeFound("accentColor.in=" + DEFAULT_ACCENT_COLOR + "," + UPDATED_ACCENT_COLOR);

        // Get all the adWatchListItemList where accentColor equals to UPDATED_ACCENT_COLOR
        defaultAdWatchListItemShouldNotBeFound("accentColor.in=" + UPDATED_ACCENT_COLOR);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByAccentColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where accentColor is not null
        defaultAdWatchListItemShouldBeFound("accentColor.specified=true");

        // Get all the adWatchListItemList where accentColor is null
        defaultAdWatchListItemShouldNotBeFound("accentColor.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByAccentColorContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where accentColor contains DEFAULT_ACCENT_COLOR
        defaultAdWatchListItemShouldBeFound("accentColor.contains=" + DEFAULT_ACCENT_COLOR);

        // Get all the adWatchListItemList where accentColor contains UPDATED_ACCENT_COLOR
        defaultAdWatchListItemShouldNotBeFound("accentColor.contains=" + UPDATED_ACCENT_COLOR);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByAccentColorNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where accentColor does not contain DEFAULT_ACCENT_COLOR
        defaultAdWatchListItemShouldNotBeFound("accentColor.doesNotContain=" + DEFAULT_ACCENT_COLOR);

        // Get all the adWatchListItemList where accentColor does not contain UPDATED_ACCENT_COLOR
        defaultAdWatchListItemShouldBeFound("accentColor.doesNotContain=" + UPDATED_ACCENT_COLOR);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where icon equals to DEFAULT_ICON
        defaultAdWatchListItemShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the adWatchListItemList where icon equals to UPDATED_ICON
        defaultAdWatchListItemShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where icon not equals to DEFAULT_ICON
        defaultAdWatchListItemShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the adWatchListItemList where icon not equals to UPDATED_ICON
        defaultAdWatchListItemShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByIconIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultAdWatchListItemShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the adWatchListItemList where icon equals to UPDATED_ICON
        defaultAdWatchListItemShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where icon is not null
        defaultAdWatchListItemShouldBeFound("icon.specified=true");

        // Get all the adWatchListItemList where icon is null
        defaultAdWatchListItemShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdWatchListItemsByIconContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where icon contains DEFAULT_ICON
        defaultAdWatchListItemShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the adWatchListItemList where icon contains UPDATED_ICON
        defaultAdWatchListItemShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByIconNotContainsSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where icon does not contain DEFAULT_ICON
        defaultAdWatchListItemShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the adWatchListItemList where icon does not contain UPDATED_ICON
        defaultAdWatchListItemShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where uid equals to DEFAULT_UID
        defaultAdWatchListItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adWatchListItemList where uid equals to UPDATED_UID
        defaultAdWatchListItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where uid not equals to DEFAULT_UID
        defaultAdWatchListItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adWatchListItemList where uid not equals to UPDATED_UID
        defaultAdWatchListItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdWatchListItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adWatchListItemList where uid equals to UPDATED_UID
        defaultAdWatchListItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where uid is not null
        defaultAdWatchListItemShouldBeFound("uid.specified=true");

        // Get all the adWatchListItemList where uid is null
        defaultAdWatchListItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where active equals to DEFAULT_ACTIVE
        defaultAdWatchListItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adWatchListItemList where active equals to UPDATED_ACTIVE
        defaultAdWatchListItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where active not equals to DEFAULT_ACTIVE
        defaultAdWatchListItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adWatchListItemList where active not equals to UPDATED_ACTIVE
        defaultAdWatchListItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdWatchListItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adWatchListItemList where active equals to UPDATED_ACTIVE
        defaultAdWatchListItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        // Get all the adWatchListItemList where active is not null
        defaultAdWatchListItemShouldBeFound("active.specified=true");

        // Get all the adWatchListItemList where active is null
        defaultAdWatchListItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdWatchListItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adWatchListItem.getAdOrganization();
        adWatchListItemRepository.saveAndFlush(adWatchListItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adWatchListItemList where adOrganization equals to adOrganizationId
        defaultAdWatchListItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adWatchListItemList where adOrganization equals to adOrganizationId + 1
        defaultAdWatchListItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdWatchListItemsByAdMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);
        AdMenu adMenu = AdMenuResourceIT.createEntity(em);
        em.persist(adMenu);
        em.flush();
        adWatchListItem.setAdMenu(adMenu);
        adWatchListItemRepository.saveAndFlush(adWatchListItem);
        Long adMenuId = adMenu.getId();

        // Get all the adWatchListItemList where adMenu equals to adMenuId
        defaultAdWatchListItemShouldBeFound("adMenuId.equals=" + adMenuId);

        // Get all the adWatchListItemList where adMenu equals to adMenuId + 1
        defaultAdWatchListItemShouldNotBeFound("adMenuId.equals=" + (adMenuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdWatchListItemShouldBeFound(String filter) throws Exception {
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adWatchListItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME)))
            .andExpect(jsonPath("$.[*].restApiEndpoint").value(hasItem(DEFAULT_REST_API_ENDPOINT)))
            .andExpect(jsonPath("$.[*].websocketEndpoint").value(hasItem(DEFAULT_WEBSOCKET_ENDPOINT)))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].actionUrl").value(hasItem(DEFAULT_ACTION_URL)))
            .andExpect(jsonPath("$.[*].filterQuery").value(hasItem(DEFAULT_FILTER_QUERY)))
            .andExpect(jsonPath("$.[*].accentColor").value(hasItem(DEFAULT_ACCENT_COLOR)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdWatchListItemShouldNotBeFound(String filter) throws Exception {
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdWatchListItem() throws Exception {
        // Get the adWatchListItem
        restAdWatchListItemMockMvc.perform(get("/api/ad-watch-list-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdWatchListItem() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        int databaseSizeBeforeUpdate = adWatchListItemRepository.findAll().size();

        // Update the adWatchListItem
        AdWatchListItem updatedAdWatchListItem = adWatchListItemRepository.findById(adWatchListItem.getId()).get();
        // Disconnect from session so that the updates on updatedAdWatchListItem are not directly saved in db
        em.detach(updatedAdWatchListItem);
        updatedAdWatchListItem
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .serviceName(UPDATED_SERVICE_NAME)
            .restApiEndpoint(UPDATED_REST_API_ENDPOINT)
            .websocketEndpoint(UPDATED_WEBSOCKET_ENDPOINT)
            .actionType(UPDATED_ACTION_TYPE)
            .actionUrl(UPDATED_ACTION_URL)
            .filterQuery(UPDATED_FILTER_QUERY)
            .accentColor(UPDATED_ACCENT_COLOR)
            .icon(UPDATED_ICON)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(updatedAdWatchListItem);

        restAdWatchListItemMockMvc.perform(put("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isOk());

        // Validate the AdWatchListItem in the database
        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeUpdate);
        AdWatchListItem testAdWatchListItem = adWatchListItemList.get(adWatchListItemList.size() - 1);
        assertThat(testAdWatchListItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAdWatchListItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdWatchListItem.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdWatchListItem.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
        assertThat(testAdWatchListItem.getRestApiEndpoint()).isEqualTo(UPDATED_REST_API_ENDPOINT);
        assertThat(testAdWatchListItem.getWebsocketEndpoint()).isEqualTo(UPDATED_WEBSOCKET_ENDPOINT);
        assertThat(testAdWatchListItem.getActionType()).isEqualTo(UPDATED_ACTION_TYPE);
        assertThat(testAdWatchListItem.getActionUrl()).isEqualTo(UPDATED_ACTION_URL);
        assertThat(testAdWatchListItem.getFilterQuery()).isEqualTo(UPDATED_FILTER_QUERY);
        assertThat(testAdWatchListItem.getAccentColor()).isEqualTo(UPDATED_ACCENT_COLOR);
        assertThat(testAdWatchListItem.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdWatchListItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdWatchListItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdWatchListItem() throws Exception {
        int databaseSizeBeforeUpdate = adWatchListItemRepository.findAll().size();

        // Create the AdWatchListItem
        AdWatchListItemDTO adWatchListItemDTO = adWatchListItemMapper.toDto(adWatchListItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdWatchListItemMockMvc.perform(put("/api/ad-watch-list-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adWatchListItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdWatchListItem in the database
        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdWatchListItem() throws Exception {
        // Initialize the database
        adWatchListItemRepository.saveAndFlush(adWatchListItem);

        int databaseSizeBeforeDelete = adWatchListItemRepository.findAll().size();

        // Delete the adWatchListItem
        restAdWatchListItemMockMvc.perform(delete("/api/ad-watch-list-items/{id}", adWatchListItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdWatchListItem> adWatchListItemList = adWatchListItemRepository.findAll();
        assertThat(adWatchListItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
