package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.domain.AdMenu;
import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdMenuRepository;
import com.bhp.opusb.service.AdMenuService;
import com.bhp.opusb.service.dto.AdMenuDTO;
import com.bhp.opusb.service.mapper.AdMenuMapper;
import com.bhp.opusb.service.dto.AdMenuCriteria;
import com.bhp.opusb.service.AdMenuQueryService;

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

import com.bhp.opusb.domain.enumeration.AdMenuAction;
/**
 * Integration tests for the {@link AdMenuResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdMenuResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final AdMenuAction DEFAULT_ACTION = AdMenuAction.WINDOW;
    private static final AdMenuAction UPDATED_ACTION = AdMenuAction.WINDOW;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_REDIRECT = "AAAAAAAAAA";
    private static final String UPDATED_REDIRECT = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;
    private static final Integer SMALLER_SEQUENCE = 1 - 1;

    private static final Boolean DEFAULT_ALWAYS_SHOW = false;
    private static final Boolean UPDATED_ALWAYS_SHOW = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AdMenuRepository adMenuRepository;

    @Autowired
    private AdMenuMapper adMenuMapper;

    @Autowired
    private AdMenuService adMenuService;

    @Autowired
    private AdMenuQueryService adMenuQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdMenuMockMvc;

    private AdMenu adMenu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdMenu createEntity(EntityManager em) {
        AdMenu adMenu = new AdMenu()
            .uid(DEFAULT_UID)
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .translationKey(DEFAULT_TRANSLATION_KEY)
            .description(DEFAULT_DESCRIPTION)
            .path(DEFAULT_PATH)
            .action(DEFAULT_ACTION)
            .icon(DEFAULT_ICON)
            .redirect(DEFAULT_REDIRECT)
            .sequence(DEFAULT_SEQUENCE)
            .alwaysShow(DEFAULT_ALWAYS_SHOW)
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
        adMenu.setAdOrganization(aDOrganization);
        return adMenu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdMenu createUpdatedEntity(EntityManager em) {
        AdMenu adMenu = new AdMenu()
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .translationKey(UPDATED_TRANSLATION_KEY)
            .description(UPDATED_DESCRIPTION)
            .path(UPDATED_PATH)
            .action(UPDATED_ACTION)
            .icon(UPDATED_ICON)
            .redirect(UPDATED_REDIRECT)
            .sequence(UPDATED_SEQUENCE)
            .alwaysShow(UPDATED_ALWAYS_SHOW)
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
        adMenu.setAdOrganization(aDOrganization);
        return adMenu;
    }

    @BeforeEach
    public void initTest() {
        adMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdMenu() throws Exception {
        int databaseSizeBeforeCreate = adMenuRepository.findAll().size();

        // Create the AdMenu
        AdMenuDTO adMenuDTO = adMenuMapper.toDto(adMenu);
        restAdMenuMockMvc.perform(post("/api/ad-menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adMenuDTO)))
            .andExpect(status().isCreated());

        // Validate the AdMenu in the database
        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeCreate + 1);
        AdMenu testAdMenu = adMenuList.get(adMenuList.size() - 1);
        assertThat(testAdMenu.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdMenu.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAdMenu.getTranslationKey()).isEqualTo(DEFAULT_TRANSLATION_KEY);
        assertThat(testAdMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdMenu.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testAdMenu.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAdMenu.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testAdMenu.getRedirect()).isEqualTo(DEFAULT_REDIRECT);
        assertThat(testAdMenu.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testAdMenu.isAlwaysShow()).isEqualTo(DEFAULT_ALWAYS_SHOW);
        assertThat(testAdMenu.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAdMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adMenuRepository.findAll().size();

        // Create the AdMenu with an existing ID
        adMenu.setId(1L);
        AdMenuDTO adMenuDTO = adMenuMapper.toDto(adMenu);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdMenuMockMvc.perform(post("/api/ad-menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdMenu in the database
        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adMenuRepository.findAll().size();
        // set the field null
        adMenu.setName(null);

        // Create the AdMenu, which fails.
        AdMenuDTO adMenuDTO = adMenuMapper.toDto(adMenu);

        restAdMenuMockMvc.perform(post("/api/ad-menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adMenuDTO)))
            .andExpect(status().isBadRequest());

        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = adMenuRepository.findAll().size();
        // set the field null
        adMenu.setPath(null);

        // Create the AdMenu, which fails.
        AdMenuDTO adMenuDTO = adMenuMapper.toDto(adMenu);

        restAdMenuMockMvc.perform(post("/api/ad-menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adMenuDTO)))
            .andExpect(status().isBadRequest());

        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdMenus() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList
        restAdMenuMockMvc.perform(get("/api/ad-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].translationKey").value(hasItem(DEFAULT_TRANSLATION_KEY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].redirect").value(hasItem(DEFAULT_REDIRECT)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].alwaysShow").value(hasItem(DEFAULT_ALWAYS_SHOW.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdMenu() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get the adMenu
        restAdMenuMockMvc.perform(get("/api/ad-menus/{id}", adMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adMenu.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.translationKey").value(DEFAULT_TRANSLATION_KEY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.redirect").value(DEFAULT_REDIRECT))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.alwaysShow").value(DEFAULT_ALWAYS_SHOW.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdMenusByIdFiltering() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        Long id = adMenu.getId();

        defaultAdMenuShouldBeFound("id.equals=" + id);
        defaultAdMenuShouldNotBeFound("id.notEquals=" + id);

        defaultAdMenuShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdMenuShouldNotBeFound("id.greaterThan=" + id);

        defaultAdMenuShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdMenuShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdMenusByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where uid equals to DEFAULT_UID
        defaultAdMenuShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adMenuList where uid equals to UPDATED_UID
        defaultAdMenuShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdMenusByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where uid not equals to DEFAULT_UID
        defaultAdMenuShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adMenuList where uid not equals to UPDATED_UID
        defaultAdMenuShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdMenusByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdMenuShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adMenuList where uid equals to UPDATED_UID
        defaultAdMenuShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdMenusByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where uid is not null
        defaultAdMenuShouldBeFound("uid.specified=true");

        // Get all the adMenuList where uid is null
        defaultAdMenuShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdMenusByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where name equals to DEFAULT_NAME
        defaultAdMenuShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adMenuList where name equals to UPDATED_NAME
        defaultAdMenuShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdMenusByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where name not equals to DEFAULT_NAME
        defaultAdMenuShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adMenuList where name not equals to UPDATED_NAME
        defaultAdMenuShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdMenusByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdMenuShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adMenuList where name equals to UPDATED_NAME
        defaultAdMenuShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdMenusByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where name is not null
        defaultAdMenuShouldBeFound("name.specified=true");

        // Get all the adMenuList where name is null
        defaultAdMenuShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByNameContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where name contains DEFAULT_NAME
        defaultAdMenuShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adMenuList where name contains UPDATED_NAME
        defaultAdMenuShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdMenusByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where name does not contain DEFAULT_NAME
        defaultAdMenuShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adMenuList where name does not contain UPDATED_NAME
        defaultAdMenuShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdMenusByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where value equals to DEFAULT_VALUE
        defaultAdMenuShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the adMenuList where value equals to UPDATED_VALUE
        defaultAdMenuShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where value not equals to DEFAULT_VALUE
        defaultAdMenuShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the adMenuList where value not equals to UPDATED_VALUE
        defaultAdMenuShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByValueIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAdMenuShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the adMenuList where value equals to UPDATED_VALUE
        defaultAdMenuShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where value is not null
        defaultAdMenuShouldBeFound("value.specified=true");

        // Get all the adMenuList where value is null
        defaultAdMenuShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByValueContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where value contains DEFAULT_VALUE
        defaultAdMenuShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the adMenuList where value contains UPDATED_VALUE
        defaultAdMenuShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByValueNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where value does not contain DEFAULT_VALUE
        defaultAdMenuShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the adMenuList where value does not contain UPDATED_VALUE
        defaultAdMenuShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllAdMenusByTranslationKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where translationKey equals to DEFAULT_TRANSLATION_KEY
        defaultAdMenuShouldBeFound("translationKey.equals=" + DEFAULT_TRANSLATION_KEY);

        // Get all the adMenuList where translationKey equals to UPDATED_TRANSLATION_KEY
        defaultAdMenuShouldNotBeFound("translationKey.equals=" + UPDATED_TRANSLATION_KEY);
    }

    @Test
    @Transactional
    public void getAllAdMenusByTranslationKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where translationKey not equals to DEFAULT_TRANSLATION_KEY
        defaultAdMenuShouldNotBeFound("translationKey.notEquals=" + DEFAULT_TRANSLATION_KEY);

        // Get all the adMenuList where translationKey not equals to UPDATED_TRANSLATION_KEY
        defaultAdMenuShouldBeFound("translationKey.notEquals=" + UPDATED_TRANSLATION_KEY);
    }

    @Test
    @Transactional
    public void getAllAdMenusByTranslationKeyIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where translationKey in DEFAULT_TRANSLATION_KEY or UPDATED_TRANSLATION_KEY
        defaultAdMenuShouldBeFound("translationKey.in=" + DEFAULT_TRANSLATION_KEY + "," + UPDATED_TRANSLATION_KEY);

        // Get all the adMenuList where translationKey equals to UPDATED_TRANSLATION_KEY
        defaultAdMenuShouldNotBeFound("translationKey.in=" + UPDATED_TRANSLATION_KEY);
    }

    @Test
    @Transactional
    public void getAllAdMenusByTranslationKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where translationKey is not null
        defaultAdMenuShouldBeFound("translationKey.specified=true");

        // Get all the adMenuList where translationKey is null
        defaultAdMenuShouldNotBeFound("translationKey.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByTranslationKeyContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where translationKey contains DEFAULT_TRANSLATION_KEY
        defaultAdMenuShouldBeFound("translationKey.contains=" + DEFAULT_TRANSLATION_KEY);

        // Get all the adMenuList where translationKey contains UPDATED_TRANSLATION_KEY
        defaultAdMenuShouldNotBeFound("translationKey.contains=" + UPDATED_TRANSLATION_KEY);
    }

    @Test
    @Transactional
    public void getAllAdMenusByTranslationKeyNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where translationKey does not contain DEFAULT_TRANSLATION_KEY
        defaultAdMenuShouldNotBeFound("translationKey.doesNotContain=" + DEFAULT_TRANSLATION_KEY);

        // Get all the adMenuList where translationKey does not contain UPDATED_TRANSLATION_KEY
        defaultAdMenuShouldBeFound("translationKey.doesNotContain=" + UPDATED_TRANSLATION_KEY);
    }


    @Test
    @Transactional
    public void getAllAdMenusByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where description equals to DEFAULT_DESCRIPTION
        defaultAdMenuShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adMenuList where description equals to UPDATED_DESCRIPTION
        defaultAdMenuShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where description not equals to DEFAULT_DESCRIPTION
        defaultAdMenuShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adMenuList where description not equals to UPDATED_DESCRIPTION
        defaultAdMenuShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdMenuShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adMenuList where description equals to UPDATED_DESCRIPTION
        defaultAdMenuShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where description is not null
        defaultAdMenuShouldBeFound("description.specified=true");

        // Get all the adMenuList where description is null
        defaultAdMenuShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where description contains DEFAULT_DESCRIPTION
        defaultAdMenuShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adMenuList where description contains UPDATED_DESCRIPTION
        defaultAdMenuShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where description does not contain DEFAULT_DESCRIPTION
        defaultAdMenuShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adMenuList where description does not contain UPDATED_DESCRIPTION
        defaultAdMenuShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdMenusByPathIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where path equals to DEFAULT_PATH
        defaultAdMenuShouldBeFound("path.equals=" + DEFAULT_PATH);

        // Get all the adMenuList where path equals to UPDATED_PATH
        defaultAdMenuShouldNotBeFound("path.equals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllAdMenusByPathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where path not equals to DEFAULT_PATH
        defaultAdMenuShouldNotBeFound("path.notEquals=" + DEFAULT_PATH);

        // Get all the adMenuList where path not equals to UPDATED_PATH
        defaultAdMenuShouldBeFound("path.notEquals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllAdMenusByPathIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where path in DEFAULT_PATH or UPDATED_PATH
        defaultAdMenuShouldBeFound("path.in=" + DEFAULT_PATH + "," + UPDATED_PATH);

        // Get all the adMenuList where path equals to UPDATED_PATH
        defaultAdMenuShouldNotBeFound("path.in=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllAdMenusByPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where path is not null
        defaultAdMenuShouldBeFound("path.specified=true");

        // Get all the adMenuList where path is null
        defaultAdMenuShouldNotBeFound("path.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByPathContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where path contains DEFAULT_PATH
        defaultAdMenuShouldBeFound("path.contains=" + DEFAULT_PATH);

        // Get all the adMenuList where path contains UPDATED_PATH
        defaultAdMenuShouldNotBeFound("path.contains=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    public void getAllAdMenusByPathNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where path does not contain DEFAULT_PATH
        defaultAdMenuShouldNotBeFound("path.doesNotContain=" + DEFAULT_PATH);

        // Get all the adMenuList where path does not contain UPDATED_PATH
        defaultAdMenuShouldBeFound("path.doesNotContain=" + UPDATED_PATH);
    }


    @Test
    @Transactional
    public void getAllAdMenusByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where action equals to DEFAULT_ACTION
        defaultAdMenuShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the adMenuList where action equals to UPDATED_ACTION
        defaultAdMenuShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where action not equals to DEFAULT_ACTION
        defaultAdMenuShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the adMenuList where action not equals to UPDATED_ACTION
        defaultAdMenuShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByActionIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultAdMenuShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the adMenuList where action equals to UPDATED_ACTION
        defaultAdMenuShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    public void getAllAdMenusByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where action is not null
        defaultAdMenuShouldBeFound("action.specified=true");

        // Get all the adMenuList where action is null
        defaultAdMenuShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdMenusByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where icon equals to DEFAULT_ICON
        defaultAdMenuShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the adMenuList where icon equals to UPDATED_ICON
        defaultAdMenuShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdMenusByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where icon not equals to DEFAULT_ICON
        defaultAdMenuShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the adMenuList where icon not equals to UPDATED_ICON
        defaultAdMenuShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdMenusByIconIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultAdMenuShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the adMenuList where icon equals to UPDATED_ICON
        defaultAdMenuShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdMenusByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where icon is not null
        defaultAdMenuShouldBeFound("icon.specified=true");

        // Get all the adMenuList where icon is null
        defaultAdMenuShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByIconContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where icon contains DEFAULT_ICON
        defaultAdMenuShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the adMenuList where icon contains UPDATED_ICON
        defaultAdMenuShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdMenusByIconNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where icon does not contain DEFAULT_ICON
        defaultAdMenuShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the adMenuList where icon does not contain UPDATED_ICON
        defaultAdMenuShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllAdMenusByRedirectIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where redirect equals to DEFAULT_REDIRECT
        defaultAdMenuShouldBeFound("redirect.equals=" + DEFAULT_REDIRECT);

        // Get all the adMenuList where redirect equals to UPDATED_REDIRECT
        defaultAdMenuShouldNotBeFound("redirect.equals=" + UPDATED_REDIRECT);
    }

    @Test
    @Transactional
    public void getAllAdMenusByRedirectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where redirect not equals to DEFAULT_REDIRECT
        defaultAdMenuShouldNotBeFound("redirect.notEquals=" + DEFAULT_REDIRECT);

        // Get all the adMenuList where redirect not equals to UPDATED_REDIRECT
        defaultAdMenuShouldBeFound("redirect.notEquals=" + UPDATED_REDIRECT);
    }

    @Test
    @Transactional
    public void getAllAdMenusByRedirectIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where redirect in DEFAULT_REDIRECT or UPDATED_REDIRECT
        defaultAdMenuShouldBeFound("redirect.in=" + DEFAULT_REDIRECT + "," + UPDATED_REDIRECT);

        // Get all the adMenuList where redirect equals to UPDATED_REDIRECT
        defaultAdMenuShouldNotBeFound("redirect.in=" + UPDATED_REDIRECT);
    }

    @Test
    @Transactional
    public void getAllAdMenusByRedirectIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where redirect is not null
        defaultAdMenuShouldBeFound("redirect.specified=true");

        // Get all the adMenuList where redirect is null
        defaultAdMenuShouldNotBeFound("redirect.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdMenusByRedirectContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where redirect contains DEFAULT_REDIRECT
        defaultAdMenuShouldBeFound("redirect.contains=" + DEFAULT_REDIRECT);

        // Get all the adMenuList where redirect contains UPDATED_REDIRECT
        defaultAdMenuShouldNotBeFound("redirect.contains=" + UPDATED_REDIRECT);
    }

    @Test
    @Transactional
    public void getAllAdMenusByRedirectNotContainsSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where redirect does not contain DEFAULT_REDIRECT
        defaultAdMenuShouldNotBeFound("redirect.doesNotContain=" + DEFAULT_REDIRECT);

        // Get all the adMenuList where redirect does not contain UPDATED_REDIRECT
        defaultAdMenuShouldBeFound("redirect.doesNotContain=" + UPDATED_REDIRECT);
    }


    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence equals to DEFAULT_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the adMenuList where sequence equals to UPDATED_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence not equals to DEFAULT_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.notEquals=" + DEFAULT_SEQUENCE);

        // Get all the adMenuList where sequence not equals to UPDATED_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.notEquals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the adMenuList where sequence equals to UPDATED_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence is not null
        defaultAdMenuShouldBeFound("sequence.specified=true");

        // Get all the adMenuList where sequence is null
        defaultAdMenuShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence is greater than or equal to DEFAULT_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.greaterThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the adMenuList where sequence is greater than or equal to UPDATED_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.greaterThanOrEqual=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence is less than or equal to DEFAULT_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.lessThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the adMenuList where sequence is less than or equal to SMALLER_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.lessThanOrEqual=" + SMALLER_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence is less than DEFAULT_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the adMenuList where sequence is less than UPDATED_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllAdMenusBySequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where sequence is greater than DEFAULT_SEQUENCE
        defaultAdMenuShouldNotBeFound("sequence.greaterThan=" + DEFAULT_SEQUENCE);

        // Get all the adMenuList where sequence is greater than SMALLER_SEQUENCE
        defaultAdMenuShouldBeFound("sequence.greaterThan=" + SMALLER_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllAdMenusByAlwaysShowIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where alwaysShow equals to DEFAULT_ALWAYS_SHOW
        defaultAdMenuShouldBeFound("alwaysShow.equals=" + DEFAULT_ALWAYS_SHOW);

        // Get all the adMenuList where alwaysShow equals to UPDATED_ALWAYS_SHOW
        defaultAdMenuShouldNotBeFound("alwaysShow.equals=" + UPDATED_ALWAYS_SHOW);
    }

    @Test
    @Transactional
    public void getAllAdMenusByAlwaysShowIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where alwaysShow not equals to DEFAULT_ALWAYS_SHOW
        defaultAdMenuShouldNotBeFound("alwaysShow.notEquals=" + DEFAULT_ALWAYS_SHOW);

        // Get all the adMenuList where alwaysShow not equals to UPDATED_ALWAYS_SHOW
        defaultAdMenuShouldBeFound("alwaysShow.notEquals=" + UPDATED_ALWAYS_SHOW);
    }

    @Test
    @Transactional
    public void getAllAdMenusByAlwaysShowIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where alwaysShow in DEFAULT_ALWAYS_SHOW or UPDATED_ALWAYS_SHOW
        defaultAdMenuShouldBeFound("alwaysShow.in=" + DEFAULT_ALWAYS_SHOW + "," + UPDATED_ALWAYS_SHOW);

        // Get all the adMenuList where alwaysShow equals to UPDATED_ALWAYS_SHOW
        defaultAdMenuShouldNotBeFound("alwaysShow.in=" + UPDATED_ALWAYS_SHOW);
    }

    @Test
    @Transactional
    public void getAllAdMenusByAlwaysShowIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where alwaysShow is not null
        defaultAdMenuShouldBeFound("alwaysShow.specified=true");

        // Get all the adMenuList where alwaysShow is null
        defaultAdMenuShouldNotBeFound("alwaysShow.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdMenusByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where active equals to DEFAULT_ACTIVE
        defaultAdMenuShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adMenuList where active equals to UPDATED_ACTIVE
        defaultAdMenuShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where active not equals to DEFAULT_ACTIVE
        defaultAdMenuShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adMenuList where active not equals to UPDATED_ACTIVE
        defaultAdMenuShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdMenuShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adMenuList where active equals to UPDATED_ACTIVE
        defaultAdMenuShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdMenusByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        // Get all the adMenuList where active is not null
        defaultAdMenuShouldBeFound("active.specified=true");

        // Get all the adMenuList where active is null
        defaultAdMenuShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdMenusByAdMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);
        AdMenu adMenu = AdMenuResourceIT.createEntity(em);
        em.persist(adMenu);
        em.flush();
        adMenu.addAdMenu(adMenu);
        adMenuRepository.saveAndFlush(adMenu);
        Long adMenuId = adMenu.getId();

        // Get all the adMenuList where adMenu equals to adMenuId
        defaultAdMenuShouldBeFound("adMenuId.equals=" + adMenuId);

        // Get all the adMenuList where adMenu equals to adMenuId + 1
        defaultAdMenuShouldNotBeFound("adMenuId.equals=" + (adMenuId + 1));
    }


    @Test
    @Transactional
    public void getAllAdMenusByAdWindowIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);
        ADWindow adWindow = ADWindowResourceIT.createEntity(em);
        em.persist(adWindow);
        em.flush();
        adMenu.setAdWindow(adWindow);
        adMenuRepository.saveAndFlush(adMenu);
        Long adWindowId = adWindow.getId();

        // Get all the adMenuList where adWindow equals to adWindowId
        defaultAdMenuShouldBeFound("adWindowId.equals=" + adWindowId);

        // Get all the adMenuList where adWindow equals to adWindowId + 1
        defaultAdMenuShouldNotBeFound("adWindowId.equals=" + (adWindowId + 1));
    }


    @Test
    @Transactional
    public void getAllAdMenusByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adMenu.getAdOrganization();
        adMenuRepository.saveAndFlush(adMenu);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adMenuList where adOrganization equals to adOrganizationId
        defaultAdMenuShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adMenuList where adOrganization equals to adOrganizationId + 1
        defaultAdMenuShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdMenusByParentMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);
        AdMenu parentMenu = AdMenuResourceIT.createEntity(em);
        em.persist(parentMenu);
        em.flush();
        adMenu.setParentMenu(parentMenu);
        adMenuRepository.saveAndFlush(adMenu);
        Long parentMenuId = parentMenu.getId();

        // Get all the adMenuList where parentMenu equals to parentMenuId
        defaultAdMenuShouldBeFound("parentMenuId.equals=" + parentMenuId);

        // Get all the adMenuList where parentMenu equals to parentMenuId + 1
        defaultAdMenuShouldNotBeFound("parentMenuId.equals=" + (parentMenuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdMenuShouldBeFound(String filter) throws Exception {
        restAdMenuMockMvc.perform(get("/api/ad-menus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].translationKey").value(hasItem(DEFAULT_TRANSLATION_KEY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].redirect").value(hasItem(DEFAULT_REDIRECT)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].alwaysShow").value(hasItem(DEFAULT_ALWAYS_SHOW.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restAdMenuMockMvc.perform(get("/api/ad-menus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdMenuShouldNotBeFound(String filter) throws Exception {
        restAdMenuMockMvc.perform(get("/api/ad-menus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdMenuMockMvc.perform(get("/api/ad-menus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdMenu() throws Exception {
        // Get the adMenu
        restAdMenuMockMvc.perform(get("/api/ad-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdMenu() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        int databaseSizeBeforeUpdate = adMenuRepository.findAll().size();

        // Update the adMenu
        AdMenu updatedAdMenu = adMenuRepository.findById(adMenu.getId()).get();
        // Disconnect from session so that the updates on updatedAdMenu are not directly saved in db
        em.detach(updatedAdMenu);
        updatedAdMenu
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .translationKey(UPDATED_TRANSLATION_KEY)
            .description(UPDATED_DESCRIPTION)
            .path(UPDATED_PATH)
            .action(UPDATED_ACTION)
            .icon(UPDATED_ICON)
            .redirect(UPDATED_REDIRECT)
            .sequence(UPDATED_SEQUENCE)
            .alwaysShow(UPDATED_ALWAYS_SHOW)
            .active(UPDATED_ACTIVE);
        AdMenuDTO adMenuDTO = adMenuMapper.toDto(updatedAdMenu);

        restAdMenuMockMvc.perform(put("/api/ad-menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adMenuDTO)))
            .andExpect(status().isOk());

        // Validate the AdMenu in the database
        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeUpdate);
        AdMenu testAdMenu = adMenuList.get(adMenuList.size() - 1);
        assertThat(testAdMenu.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdMenu.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAdMenu.getTranslationKey()).isEqualTo(UPDATED_TRANSLATION_KEY);
        assertThat(testAdMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdMenu.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testAdMenu.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAdMenu.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testAdMenu.getRedirect()).isEqualTo(UPDATED_REDIRECT);
        assertThat(testAdMenu.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testAdMenu.isAlwaysShow()).isEqualTo(UPDATED_ALWAYS_SHOW);
        assertThat(testAdMenu.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdMenu() throws Exception {
        int databaseSizeBeforeUpdate = adMenuRepository.findAll().size();

        // Create the AdMenu
        AdMenuDTO adMenuDTO = adMenuMapper.toDto(adMenu);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdMenuMockMvc.perform(put("/api/ad-menus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adMenuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdMenu in the database
        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdMenu() throws Exception {
        // Initialize the database
        adMenuRepository.saveAndFlush(adMenu);

        int databaseSizeBeforeDelete = adMenuRepository.findAll().size();

        // Delete the adMenu
        restAdMenuMockMvc.perform(delete("/api/ad-menus/{id}", adMenu.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdMenu> adMenuList = adMenuRepository.findAll();
        assertThat(adMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
