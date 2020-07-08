package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdButton;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdTrigger;
import com.bhp.opusb.repository.AdButtonRepository;
import com.bhp.opusb.service.AdButtonService;
import com.bhp.opusb.service.dto.AdButtonDTO;
import com.bhp.opusb.service.mapper.AdButtonMapper;
import com.bhp.opusb.service.dto.AdButtonCriteria;
import com.bhp.opusb.service.AdButtonQueryService;

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
 * Integration tests for the {@link AdButtonResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdButtonResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TOOLTIP = "AAAAAAAAAA";
    private static final String UPDATED_TOOLTIP = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TOOLBAR = false;
    private static final Boolean UPDATED_TOOLBAR = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    @Autowired
    private AdButtonRepository adButtonRepository;

    @Autowired
    private AdButtonMapper adButtonMapper;

    @Autowired
    private AdButtonService adButtonService;

    @Autowired
    private AdButtonQueryService adButtonQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdButtonMockMvc;

    private AdButton adButton;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdButton createEntity(EntityManager em) {
        AdButton adButton = new AdButton()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .tooltip(DEFAULT_TOOLTIP)
            .description(DEFAULT_DESCRIPTION)
            .toolbar(DEFAULT_TOOLBAR)
            .icon(DEFAULT_ICON);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adButton.setAdOrganization(aDOrganization);
        return adButton;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdButton createUpdatedEntity(EntityManager em) {
        AdButton adButton = new AdButton()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .tooltip(UPDATED_TOOLTIP)
            .description(UPDATED_DESCRIPTION)
            .toolbar(UPDATED_TOOLBAR)
            .icon(UPDATED_ICON);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adButton.setAdOrganization(aDOrganization);
        return adButton;
    }

    @BeforeEach
    public void initTest() {
        adButton = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdButton() throws Exception {
        int databaseSizeBeforeCreate = adButtonRepository.findAll().size();

        // Create the AdButton
        AdButtonDTO adButtonDTO = adButtonMapper.toDto(adButton);
        restAdButtonMockMvc.perform(post("/api/ad-buttons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adButtonDTO)))
            .andExpect(status().isCreated());

        // Validate the AdButton in the database
        List<AdButton> adButtonList = adButtonRepository.findAll();
        assertThat(adButtonList).hasSize(databaseSizeBeforeCreate + 1);
        AdButton testAdButton = adButtonList.get(adButtonList.size() - 1);
        assertThat(testAdButton.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdButton.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testAdButton.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdButton.getTooltip()).isEqualTo(DEFAULT_TOOLTIP);
        assertThat(testAdButton.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdButton.isToolbar()).isEqualTo(DEFAULT_TOOLBAR);
        assertThat(testAdButton.getIcon()).isEqualTo(DEFAULT_ICON);
    }

    @Test
    @Transactional
    public void createAdButtonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adButtonRepository.findAll().size();

        // Create the AdButton with an existing ID
        adButton.setId(1L);
        AdButtonDTO adButtonDTO = adButtonMapper.toDto(adButton);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdButtonMockMvc.perform(post("/api/ad-buttons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adButtonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdButton in the database
        List<AdButton> adButtonList = adButtonRepository.findAll();
        assertThat(adButtonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adButtonRepository.findAll().size();
        // set the field null
        adButton.setName(null);

        // Create the AdButton, which fails.
        AdButtonDTO adButtonDTO = adButtonMapper.toDto(adButton);

        restAdButtonMockMvc.perform(post("/api/ad-buttons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adButtonDTO)))
            .andExpect(status().isBadRequest());

        List<AdButton> adButtonList = adButtonRepository.findAll();
        assertThat(adButtonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdButtons() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList
        restAdButtonMockMvc.perform(get("/api/ad-buttons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adButton.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tooltip").value(hasItem(DEFAULT_TOOLTIP)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].toolbar").value(hasItem(DEFAULT_TOOLBAR.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));
    }
    
    @Test
    @Transactional
    public void getAdButton() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get the adButton
        restAdButtonMockMvc.perform(get("/api/ad-buttons/{id}", adButton.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adButton.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.tooltip").value(DEFAULT_TOOLTIP))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.toolbar").value(DEFAULT_TOOLBAR.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON));
    }


    @Test
    @Transactional
    public void getAdButtonsByIdFiltering() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        Long id = adButton.getId();

        defaultAdButtonShouldBeFound("id.equals=" + id);
        defaultAdButtonShouldNotBeFound("id.notEquals=" + id);

        defaultAdButtonShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdButtonShouldNotBeFound("id.greaterThan=" + id);

        defaultAdButtonShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdButtonShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdButtonsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where uid equals to DEFAULT_UID
        defaultAdButtonShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adButtonList where uid equals to UPDATED_UID
        defaultAdButtonShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where uid not equals to DEFAULT_UID
        defaultAdButtonShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adButtonList where uid not equals to UPDATED_UID
        defaultAdButtonShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdButtonShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adButtonList where uid equals to UPDATED_UID
        defaultAdButtonShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where uid is not null
        defaultAdButtonShouldBeFound("uid.specified=true");

        // Get all the adButtonList where uid is null
        defaultAdButtonShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdButtonsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where active equals to DEFAULT_ACTIVE
        defaultAdButtonShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adButtonList where active equals to UPDATED_ACTIVE
        defaultAdButtonShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where active not equals to DEFAULT_ACTIVE
        defaultAdButtonShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adButtonList where active not equals to UPDATED_ACTIVE
        defaultAdButtonShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdButtonShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adButtonList where active equals to UPDATED_ACTIVE
        defaultAdButtonShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where active is not null
        defaultAdButtonShouldBeFound("active.specified=true");

        // Get all the adButtonList where active is null
        defaultAdButtonShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdButtonsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where name equals to DEFAULT_NAME
        defaultAdButtonShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adButtonList where name equals to UPDATED_NAME
        defaultAdButtonShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where name not equals to DEFAULT_NAME
        defaultAdButtonShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adButtonList where name not equals to UPDATED_NAME
        defaultAdButtonShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdButtonShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adButtonList where name equals to UPDATED_NAME
        defaultAdButtonShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where name is not null
        defaultAdButtonShouldBeFound("name.specified=true");

        // Get all the adButtonList where name is null
        defaultAdButtonShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdButtonsByNameContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where name contains DEFAULT_NAME
        defaultAdButtonShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adButtonList where name contains UPDATED_NAME
        defaultAdButtonShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where name does not contain DEFAULT_NAME
        defaultAdButtonShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adButtonList where name does not contain UPDATED_NAME
        defaultAdButtonShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdButtonsByTooltipIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where tooltip equals to DEFAULT_TOOLTIP
        defaultAdButtonShouldBeFound("tooltip.equals=" + DEFAULT_TOOLTIP);

        // Get all the adButtonList where tooltip equals to UPDATED_TOOLTIP
        defaultAdButtonShouldNotBeFound("tooltip.equals=" + UPDATED_TOOLTIP);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByTooltipIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where tooltip not equals to DEFAULT_TOOLTIP
        defaultAdButtonShouldNotBeFound("tooltip.notEquals=" + DEFAULT_TOOLTIP);

        // Get all the adButtonList where tooltip not equals to UPDATED_TOOLTIP
        defaultAdButtonShouldBeFound("tooltip.notEquals=" + UPDATED_TOOLTIP);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByTooltipIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where tooltip in DEFAULT_TOOLTIP or UPDATED_TOOLTIP
        defaultAdButtonShouldBeFound("tooltip.in=" + DEFAULT_TOOLTIP + "," + UPDATED_TOOLTIP);

        // Get all the adButtonList where tooltip equals to UPDATED_TOOLTIP
        defaultAdButtonShouldNotBeFound("tooltip.in=" + UPDATED_TOOLTIP);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByTooltipIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where tooltip is not null
        defaultAdButtonShouldBeFound("tooltip.specified=true");

        // Get all the adButtonList where tooltip is null
        defaultAdButtonShouldNotBeFound("tooltip.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdButtonsByTooltipContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where tooltip contains DEFAULT_TOOLTIP
        defaultAdButtonShouldBeFound("tooltip.contains=" + DEFAULT_TOOLTIP);

        // Get all the adButtonList where tooltip contains UPDATED_TOOLTIP
        defaultAdButtonShouldNotBeFound("tooltip.contains=" + UPDATED_TOOLTIP);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByTooltipNotContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where tooltip does not contain DEFAULT_TOOLTIP
        defaultAdButtonShouldNotBeFound("tooltip.doesNotContain=" + DEFAULT_TOOLTIP);

        // Get all the adButtonList where tooltip does not contain UPDATED_TOOLTIP
        defaultAdButtonShouldBeFound("tooltip.doesNotContain=" + UPDATED_TOOLTIP);
    }


    @Test
    @Transactional
    public void getAllAdButtonsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where description equals to DEFAULT_DESCRIPTION
        defaultAdButtonShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adButtonList where description equals to UPDATED_DESCRIPTION
        defaultAdButtonShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where description not equals to DEFAULT_DESCRIPTION
        defaultAdButtonShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adButtonList where description not equals to UPDATED_DESCRIPTION
        defaultAdButtonShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdButtonShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adButtonList where description equals to UPDATED_DESCRIPTION
        defaultAdButtonShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where description is not null
        defaultAdButtonShouldBeFound("description.specified=true");

        // Get all the adButtonList where description is null
        defaultAdButtonShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdButtonsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where description contains DEFAULT_DESCRIPTION
        defaultAdButtonShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adButtonList where description contains UPDATED_DESCRIPTION
        defaultAdButtonShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where description does not contain DEFAULT_DESCRIPTION
        defaultAdButtonShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adButtonList where description does not contain UPDATED_DESCRIPTION
        defaultAdButtonShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdButtonsByToolbarIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where toolbar equals to DEFAULT_TOOLBAR
        defaultAdButtonShouldBeFound("toolbar.equals=" + DEFAULT_TOOLBAR);

        // Get all the adButtonList where toolbar equals to UPDATED_TOOLBAR
        defaultAdButtonShouldNotBeFound("toolbar.equals=" + UPDATED_TOOLBAR);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByToolbarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where toolbar not equals to DEFAULT_TOOLBAR
        defaultAdButtonShouldNotBeFound("toolbar.notEquals=" + DEFAULT_TOOLBAR);

        // Get all the adButtonList where toolbar not equals to UPDATED_TOOLBAR
        defaultAdButtonShouldBeFound("toolbar.notEquals=" + UPDATED_TOOLBAR);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByToolbarIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where toolbar in DEFAULT_TOOLBAR or UPDATED_TOOLBAR
        defaultAdButtonShouldBeFound("toolbar.in=" + DEFAULT_TOOLBAR + "," + UPDATED_TOOLBAR);

        // Get all the adButtonList where toolbar equals to UPDATED_TOOLBAR
        defaultAdButtonShouldNotBeFound("toolbar.in=" + UPDATED_TOOLBAR);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByToolbarIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where toolbar is not null
        defaultAdButtonShouldBeFound("toolbar.specified=true");

        // Get all the adButtonList where toolbar is null
        defaultAdButtonShouldNotBeFound("toolbar.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdButtonsByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where icon equals to DEFAULT_ICON
        defaultAdButtonShouldBeFound("icon.equals=" + DEFAULT_ICON);

        // Get all the adButtonList where icon equals to UPDATED_ICON
        defaultAdButtonShouldNotBeFound("icon.equals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByIconIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where icon not equals to DEFAULT_ICON
        defaultAdButtonShouldNotBeFound("icon.notEquals=" + DEFAULT_ICON);

        // Get all the adButtonList where icon not equals to UPDATED_ICON
        defaultAdButtonShouldBeFound("icon.notEquals=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByIconIsInShouldWork() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where icon in DEFAULT_ICON or UPDATED_ICON
        defaultAdButtonShouldBeFound("icon.in=" + DEFAULT_ICON + "," + UPDATED_ICON);

        // Get all the adButtonList where icon equals to UPDATED_ICON
        defaultAdButtonShouldNotBeFound("icon.in=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByIconIsNullOrNotNull() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where icon is not null
        defaultAdButtonShouldBeFound("icon.specified=true");

        // Get all the adButtonList where icon is null
        defaultAdButtonShouldNotBeFound("icon.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdButtonsByIconContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where icon contains DEFAULT_ICON
        defaultAdButtonShouldBeFound("icon.contains=" + DEFAULT_ICON);

        // Get all the adButtonList where icon contains UPDATED_ICON
        defaultAdButtonShouldNotBeFound("icon.contains=" + UPDATED_ICON);
    }

    @Test
    @Transactional
    public void getAllAdButtonsByIconNotContainsSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        // Get all the adButtonList where icon does not contain DEFAULT_ICON
        defaultAdButtonShouldNotBeFound("icon.doesNotContain=" + DEFAULT_ICON);

        // Get all the adButtonList where icon does not contain UPDATED_ICON
        defaultAdButtonShouldBeFound("icon.doesNotContain=" + UPDATED_ICON);
    }


    @Test
    @Transactional
    public void getAllAdButtonsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adButton.getAdOrganization();
        adButtonRepository.saveAndFlush(adButton);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adButtonList where adOrganization equals to adOrganizationId
        defaultAdButtonShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adButtonList where adOrganization equals to adOrganizationId + 1
        defaultAdButtonShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdButtonsByAdTriggerIsEqualToSomething() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);
        AdTrigger adTrigger = AdTriggerResourceIT.createEntity(em);
        em.persist(adTrigger);
        em.flush();
        adButton.setAdTrigger(adTrigger);
        adButtonRepository.saveAndFlush(adButton);
        Long adTriggerId = adTrigger.getId();

        // Get all the adButtonList where adTrigger equals to adTriggerId
        defaultAdButtonShouldBeFound("adTriggerId.equals=" + adTriggerId);

        // Get all the adButtonList where adTrigger equals to adTriggerId + 1
        defaultAdButtonShouldNotBeFound("adTriggerId.equals=" + (adTriggerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdButtonShouldBeFound(String filter) throws Exception {
        restAdButtonMockMvc.perform(get("/api/ad-buttons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adButton.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].tooltip").value(hasItem(DEFAULT_TOOLTIP)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].toolbar").value(hasItem(DEFAULT_TOOLBAR.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)));

        // Check, that the count call also returns 1
        restAdButtonMockMvc.perform(get("/api/ad-buttons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdButtonShouldNotBeFound(String filter) throws Exception {
        restAdButtonMockMvc.perform(get("/api/ad-buttons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdButtonMockMvc.perform(get("/api/ad-buttons/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdButton() throws Exception {
        // Get the adButton
        restAdButtonMockMvc.perform(get("/api/ad-buttons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdButton() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        int databaseSizeBeforeUpdate = adButtonRepository.findAll().size();

        // Update the adButton
        AdButton updatedAdButton = adButtonRepository.findById(adButton.getId()).get();
        // Disconnect from session so that the updates on updatedAdButton are not directly saved in db
        em.detach(updatedAdButton);
        updatedAdButton
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .tooltip(UPDATED_TOOLTIP)
            .description(UPDATED_DESCRIPTION)
            .toolbar(UPDATED_TOOLBAR)
            .icon(UPDATED_ICON);
        AdButtonDTO adButtonDTO = adButtonMapper.toDto(updatedAdButton);

        restAdButtonMockMvc.perform(put("/api/ad-buttons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adButtonDTO)))
            .andExpect(status().isOk());

        // Validate the AdButton in the database
        List<AdButton> adButtonList = adButtonRepository.findAll();
        assertThat(adButtonList).hasSize(databaseSizeBeforeUpdate);
        AdButton testAdButton = adButtonList.get(adButtonList.size() - 1);
        assertThat(testAdButton.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdButton.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testAdButton.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdButton.getTooltip()).isEqualTo(UPDATED_TOOLTIP);
        assertThat(testAdButton.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdButton.isToolbar()).isEqualTo(UPDATED_TOOLBAR);
        assertThat(testAdButton.getIcon()).isEqualTo(UPDATED_ICON);
    }

    @Test
    @Transactional
    public void updateNonExistingAdButton() throws Exception {
        int databaseSizeBeforeUpdate = adButtonRepository.findAll().size();

        // Create the AdButton
        AdButtonDTO adButtonDTO = adButtonMapper.toDto(adButton);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdButtonMockMvc.perform(put("/api/ad-buttons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adButtonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdButton in the database
        List<AdButton> adButtonList = adButtonRepository.findAll();
        assertThat(adButtonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdButton() throws Exception {
        // Initialize the database
        adButtonRepository.saveAndFlush(adButton);

        int databaseSizeBeforeDelete = adButtonRepository.findAll().size();

        // Delete the adButton
        restAdButtonMockMvc.perform(delete("/api/ad-buttons/{id}", adButton.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdButton> adButtonList = adButtonRepository.findAll();
        assertThat(adButtonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
