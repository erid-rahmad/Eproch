package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.ADWindowRepository;
import com.bhp.opusb.service.ADWindowService;
import com.bhp.opusb.service.dto.ADWindowDTO;
import com.bhp.opusb.service.mapper.ADWindowMapper;
import com.bhp.opusb.service.dto.ADWindowCriteria;
import com.bhp.opusb.service.ADWindowQueryService;

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

import com.bhp.opusb.domain.enumeration.ADWindowType;
/**
 * Integration tests for the {@link ADWindowResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADWindowResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_LOGIC = "BBBBBBBBBB";

    private static final ADWindowType DEFAULT_TYPE = ADWindowType.MAINTAIN;
    private static final ADWindowType UPDATED_TYPE = ADWindowType.QUERY;

    private static final Boolean DEFAULT_TREE_VIEW = false;
    private static final Boolean UPDATED_TREE_VIEW = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADWindowRepository aDWindowRepository;

    @Autowired
    private ADWindowMapper aDWindowMapper;

    @Autowired
    private ADWindowService aDWindowService;

    @Autowired
    private ADWindowQueryService aDWindowQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADWindowMockMvc;

    private ADWindow aDWindow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADWindow createEntity(EntityManager em) {
        ADWindow aDWindow = new ADWindow()
            .uid(DEFAULT_UID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .titleLogic(DEFAULT_TITLE_LOGIC)
            .type(DEFAULT_TYPE)
            .treeView(DEFAULT_TREE_VIEW)
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
        aDWindow.setAdOrganization(aDOrganization);
        return aDWindow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADWindow createUpdatedEntity(EntityManager em) {
        ADWindow aDWindow = new ADWindow()
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .titleLogic(UPDATED_TITLE_LOGIC)
            .type(UPDATED_TYPE)
            .treeView(UPDATED_TREE_VIEW)
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
        aDWindow.setAdOrganization(aDOrganization);
        return aDWindow;
    }

    @BeforeEach
    public void initTest() {
        aDWindow = createEntity(em);
    }

    @Test
    @Transactional
    public void createADWindow() throws Exception {
        int databaseSizeBeforeCreate = aDWindowRepository.findAll().size();

        // Create the ADWindow
        ADWindowDTO aDWindowDTO = aDWindowMapper.toDto(aDWindow);
        restADWindowMockMvc.perform(post("/api/ad-windows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDWindowDTO)))
            .andExpect(status().isCreated());

        // Validate the ADWindow in the database
        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeCreate + 1);
        ADWindow testADWindow = aDWindowList.get(aDWindowList.size() - 1);
        assertThat(testADWindow.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testADWindow.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADWindow.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADWindow.getTitleLogic()).isEqualTo(DEFAULT_TITLE_LOGIC);
        assertThat(testADWindow.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testADWindow.isTreeView()).isEqualTo(DEFAULT_TREE_VIEW);
        assertThat(testADWindow.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADWindowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDWindowRepository.findAll().size();

        // Create the ADWindow with an existing ID
        aDWindow.setId(1L);
        ADWindowDTO aDWindowDTO = aDWindowMapper.toDto(aDWindow);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADWindowMockMvc.perform(post("/api/ad-windows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDWindowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADWindow in the database
        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDWindowRepository.findAll().size();
        // set the field null
        aDWindow.setName(null);

        // Create the ADWindow, which fails.
        ADWindowDTO aDWindowDTO = aDWindowMapper.toDto(aDWindow);

        restADWindowMockMvc.perform(post("/api/ad-windows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDWindowDTO)))
            .andExpect(status().isBadRequest());

        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDWindowRepository.findAll().size();
        // set the field null
        aDWindow.setType(null);

        // Create the ADWindow, which fails.
        ADWindowDTO aDWindowDTO = aDWindowMapper.toDto(aDWindow);

        restADWindowMockMvc.perform(post("/api/ad-windows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDWindowDTO)))
            .andExpect(status().isBadRequest());

        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADWindows() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList
        restADWindowMockMvc.perform(get("/api/ad-windows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDWindow.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].titleLogic").value(hasItem(DEFAULT_TITLE_LOGIC)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].treeView").value(hasItem(DEFAULT_TREE_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADWindow() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get the aDWindow
        restADWindowMockMvc.perform(get("/api/ad-windows/{id}", aDWindow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDWindow.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.titleLogic").value(DEFAULT_TITLE_LOGIC))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.treeView").value(DEFAULT_TREE_VIEW.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADWindowsByIdFiltering() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        Long id = aDWindow.getId();

        defaultADWindowShouldBeFound("id.equals=" + id);
        defaultADWindowShouldNotBeFound("id.notEquals=" + id);

        defaultADWindowShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADWindowShouldNotBeFound("id.greaterThan=" + id);

        defaultADWindowShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADWindowShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADWindowsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where uid equals to DEFAULT_UID
        defaultADWindowShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the aDWindowList where uid equals to UPDATED_UID
        defaultADWindowShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADWindowsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where uid not equals to DEFAULT_UID
        defaultADWindowShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the aDWindowList where uid not equals to UPDATED_UID
        defaultADWindowShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADWindowsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where uid in DEFAULT_UID or UPDATED_UID
        defaultADWindowShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the aDWindowList where uid equals to UPDATED_UID
        defaultADWindowShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADWindowsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where uid is not null
        defaultADWindowShouldBeFound("uid.specified=true");

        // Get all the aDWindowList where uid is null
        defaultADWindowShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllADWindowsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where name equals to DEFAULT_NAME
        defaultADWindowShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDWindowList where name equals to UPDATED_NAME
        defaultADWindowShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADWindowsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where name not equals to DEFAULT_NAME
        defaultADWindowShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDWindowList where name not equals to UPDATED_NAME
        defaultADWindowShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADWindowsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADWindowShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDWindowList where name equals to UPDATED_NAME
        defaultADWindowShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADWindowsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where name is not null
        defaultADWindowShouldBeFound("name.specified=true");

        // Get all the aDWindowList where name is null
        defaultADWindowShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADWindowsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where name contains DEFAULT_NAME
        defaultADWindowShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDWindowList where name contains UPDATED_NAME
        defaultADWindowShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADWindowsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where name does not contain DEFAULT_NAME
        defaultADWindowShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDWindowList where name does not contain UPDATED_NAME
        defaultADWindowShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADWindowsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where description equals to DEFAULT_DESCRIPTION
        defaultADWindowShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDWindowList where description equals to UPDATED_DESCRIPTION
        defaultADWindowShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADWindowsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where description not equals to DEFAULT_DESCRIPTION
        defaultADWindowShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDWindowList where description not equals to UPDATED_DESCRIPTION
        defaultADWindowShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADWindowsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADWindowShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDWindowList where description equals to UPDATED_DESCRIPTION
        defaultADWindowShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADWindowsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where description is not null
        defaultADWindowShouldBeFound("description.specified=true");

        // Get all the aDWindowList where description is null
        defaultADWindowShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADWindowsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where description contains DEFAULT_DESCRIPTION
        defaultADWindowShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDWindowList where description contains UPDATED_DESCRIPTION
        defaultADWindowShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADWindowsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where description does not contain DEFAULT_DESCRIPTION
        defaultADWindowShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDWindowList where description does not contain UPDATED_DESCRIPTION
        defaultADWindowShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADWindowsByTitleLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where titleLogic equals to DEFAULT_TITLE_LOGIC
        defaultADWindowShouldBeFound("titleLogic.equals=" + DEFAULT_TITLE_LOGIC);

        // Get all the aDWindowList where titleLogic equals to UPDATED_TITLE_LOGIC
        defaultADWindowShouldNotBeFound("titleLogic.equals=" + UPDATED_TITLE_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTitleLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where titleLogic not equals to DEFAULT_TITLE_LOGIC
        defaultADWindowShouldNotBeFound("titleLogic.notEquals=" + DEFAULT_TITLE_LOGIC);

        // Get all the aDWindowList where titleLogic not equals to UPDATED_TITLE_LOGIC
        defaultADWindowShouldBeFound("titleLogic.notEquals=" + UPDATED_TITLE_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTitleLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where titleLogic in DEFAULT_TITLE_LOGIC or UPDATED_TITLE_LOGIC
        defaultADWindowShouldBeFound("titleLogic.in=" + DEFAULT_TITLE_LOGIC + "," + UPDATED_TITLE_LOGIC);

        // Get all the aDWindowList where titleLogic equals to UPDATED_TITLE_LOGIC
        defaultADWindowShouldNotBeFound("titleLogic.in=" + UPDATED_TITLE_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTitleLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where titleLogic is not null
        defaultADWindowShouldBeFound("titleLogic.specified=true");

        // Get all the aDWindowList where titleLogic is null
        defaultADWindowShouldNotBeFound("titleLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADWindowsByTitleLogicContainsSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where titleLogic contains DEFAULT_TITLE_LOGIC
        defaultADWindowShouldBeFound("titleLogic.contains=" + DEFAULT_TITLE_LOGIC);

        // Get all the aDWindowList where titleLogic contains UPDATED_TITLE_LOGIC
        defaultADWindowShouldNotBeFound("titleLogic.contains=" + UPDATED_TITLE_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTitleLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where titleLogic does not contain DEFAULT_TITLE_LOGIC
        defaultADWindowShouldNotBeFound("titleLogic.doesNotContain=" + DEFAULT_TITLE_LOGIC);

        // Get all the aDWindowList where titleLogic does not contain UPDATED_TITLE_LOGIC
        defaultADWindowShouldBeFound("titleLogic.doesNotContain=" + UPDATED_TITLE_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADWindowsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where type equals to DEFAULT_TYPE
        defaultADWindowShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the aDWindowList where type equals to UPDATED_TYPE
        defaultADWindowShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where type not equals to DEFAULT_TYPE
        defaultADWindowShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the aDWindowList where type not equals to UPDATED_TYPE
        defaultADWindowShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultADWindowShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the aDWindowList where type equals to UPDATED_TYPE
        defaultADWindowShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where type is not null
        defaultADWindowShouldBeFound("type.specified=true");

        // Get all the aDWindowList where type is null
        defaultADWindowShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllADWindowsByTreeViewIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where treeView equals to DEFAULT_TREE_VIEW
        defaultADWindowShouldBeFound("treeView.equals=" + DEFAULT_TREE_VIEW);

        // Get all the aDWindowList where treeView equals to UPDATED_TREE_VIEW
        defaultADWindowShouldNotBeFound("treeView.equals=" + UPDATED_TREE_VIEW);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTreeViewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where treeView not equals to DEFAULT_TREE_VIEW
        defaultADWindowShouldNotBeFound("treeView.notEquals=" + DEFAULT_TREE_VIEW);

        // Get all the aDWindowList where treeView not equals to UPDATED_TREE_VIEW
        defaultADWindowShouldBeFound("treeView.notEquals=" + UPDATED_TREE_VIEW);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTreeViewIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where treeView in DEFAULT_TREE_VIEW or UPDATED_TREE_VIEW
        defaultADWindowShouldBeFound("treeView.in=" + DEFAULT_TREE_VIEW + "," + UPDATED_TREE_VIEW);

        // Get all the aDWindowList where treeView equals to UPDATED_TREE_VIEW
        defaultADWindowShouldNotBeFound("treeView.in=" + UPDATED_TREE_VIEW);
    }

    @Test
    @Transactional
    public void getAllADWindowsByTreeViewIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where treeView is not null
        defaultADWindowShouldBeFound("treeView.specified=true");

        // Get all the aDWindowList where treeView is null
        defaultADWindowShouldNotBeFound("treeView.specified=false");
    }

    @Test
    @Transactional
    public void getAllADWindowsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where active equals to DEFAULT_ACTIVE
        defaultADWindowShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDWindowList where active equals to UPDATED_ACTIVE
        defaultADWindowShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADWindowsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where active not equals to DEFAULT_ACTIVE
        defaultADWindowShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDWindowList where active not equals to UPDATED_ACTIVE
        defaultADWindowShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADWindowsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADWindowShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDWindowList where active equals to UPDATED_ACTIVE
        defaultADWindowShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADWindowsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        // Get all the aDWindowList where active is not null
        defaultADWindowShouldBeFound("active.specified=true");

        // Get all the aDWindowList where active is null
        defaultADWindowShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADWindowsByADTabIsEqualToSomething() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);
        ADTab aDTab = ADTabResourceIT.createEntity(em);
        em.persist(aDTab);
        em.flush();
        aDWindow.addADTab(aDTab);
        aDWindowRepository.saveAndFlush(aDWindow);
        Long aDTabId = aDTab.getId();

        // Get all the aDWindowList where aDTab equals to aDTabId
        defaultADWindowShouldBeFound("aDTabId.equals=" + aDTabId);

        // Get all the aDWindowList where aDTab equals to aDTabId + 1
        defaultADWindowShouldNotBeFound("aDTabId.equals=" + (aDTabId + 1));
    }


    @Test
    @Transactional
    public void getAllADWindowsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDWindow.getAdOrganization();
        aDWindowRepository.saveAndFlush(aDWindow);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDWindowList where adOrganization equals to adOrganizationId
        defaultADWindowShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDWindowList where adOrganization equals to adOrganizationId + 1
        defaultADWindowShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADWindowShouldBeFound(String filter) throws Exception {
        restADWindowMockMvc.perform(get("/api/ad-windows?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDWindow.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].titleLogic").value(hasItem(DEFAULT_TITLE_LOGIC)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].treeView").value(hasItem(DEFAULT_TREE_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADWindowMockMvc.perform(get("/api/ad-windows/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADWindowShouldNotBeFound(String filter) throws Exception {
        restADWindowMockMvc.perform(get("/api/ad-windows?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADWindowMockMvc.perform(get("/api/ad-windows/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADWindow() throws Exception {
        // Get the aDWindow
        restADWindowMockMvc.perform(get("/api/ad-windows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADWindow() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        int databaseSizeBeforeUpdate = aDWindowRepository.findAll().size();

        // Update the aDWindow
        ADWindow updatedADWindow = aDWindowRepository.findById(aDWindow.getId()).get();
        // Disconnect from session so that the updates on updatedADWindow are not directly saved in db
        em.detach(updatedADWindow);
        updatedADWindow
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .titleLogic(UPDATED_TITLE_LOGIC)
            .type(UPDATED_TYPE)
            .treeView(UPDATED_TREE_VIEW)
            .active(UPDATED_ACTIVE);
        ADWindowDTO aDWindowDTO = aDWindowMapper.toDto(updatedADWindow);

        restADWindowMockMvc.perform(put("/api/ad-windows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDWindowDTO)))
            .andExpect(status().isOk());

        // Validate the ADWindow in the database
        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeUpdate);
        ADWindow testADWindow = aDWindowList.get(aDWindowList.size() - 1);
        assertThat(testADWindow.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testADWindow.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADWindow.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADWindow.getTitleLogic()).isEqualTo(UPDATED_TITLE_LOGIC);
        assertThat(testADWindow.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testADWindow.isTreeView()).isEqualTo(UPDATED_TREE_VIEW);
        assertThat(testADWindow.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADWindow() throws Exception {
        int databaseSizeBeforeUpdate = aDWindowRepository.findAll().size();

        // Create the ADWindow
        ADWindowDTO aDWindowDTO = aDWindowMapper.toDto(aDWindow);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADWindowMockMvc.perform(put("/api/ad-windows")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDWindowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADWindow in the database
        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADWindow() throws Exception {
        // Initialize the database
        aDWindowRepository.saveAndFlush(aDWindow);

        int databaseSizeBeforeDelete = aDWindowRepository.findAll().size();

        // Delete the aDWindow
        restADWindowMockMvc.perform(delete("/api/ad-windows/{id}", aDWindow.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADWindow> aDWindowList = aDWindowRepository.findAll();
        assertThat(aDWindowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
