package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.ADFieldRepository;
import com.bhp.opusb.service.ADFieldService;
import com.bhp.opusb.service.dto.ADFieldDTO;
import com.bhp.opusb.service.mapper.ADFieldMapper;
import com.bhp.opusb.service.dto.ADFieldCriteria;
import com.bhp.opusb.service.ADFieldQueryService;

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
 * Integration tests for the {@link ADFieldResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADFieldResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HINT = "AAAAAAAAAA";
    private static final String UPDATED_HINT = "BBBBBBBBBB";

    private static final String DEFAULT_STATIC_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_STATIC_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATIC_FIELD = false;
    private static final Boolean UPDATED_STATIC_FIELD = true;

    private static final Boolean DEFAULT_LABEL_ONLY = false;
    private static final Boolean UPDATED_LABEL_ONLY = true;

    private static final Boolean DEFAULT_SHOW_LABEL = false;
    private static final Boolean UPDATED_SHOW_LABEL = true;

    private static final Boolean DEFAULT_SHOW_IN_GRID = false;
    private static final Boolean UPDATED_SHOW_IN_GRID = true;

    private static final Boolean DEFAULT_SHOW_IN_DETAIL = false;
    private static final Boolean UPDATED_SHOW_IN_DETAIL = true;

    private static final Integer DEFAULT_GRID_SEQUENCE = 1;
    private static final Integer UPDATED_GRID_SEQUENCE = 2;
    private static final Integer SMALLER_GRID_SEQUENCE = 1 - 1;

    private static final Integer DEFAULT_DETAIL_SEQUENCE = 1;
    private static final Integer UPDATED_DETAIL_SEQUENCE = 2;
    private static final Integer SMALLER_DETAIL_SEQUENCE = 1 - 1;

    private static final String DEFAULT_DISPLAY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_LOGIC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WRITABLE = false;
    private static final Boolean UPDATED_WRITABLE = true;

    private static final Integer DEFAULT_COLUMN_NO = 1;
    private static final Integer UPDATED_COLUMN_NO = 2;
    private static final Integer SMALLER_COLUMN_NO = 1 - 1;

    private static final Integer DEFAULT_COLUMN_SPAN = 1;
    private static final Integer UPDATED_COLUMN_SPAN = 2;
    private static final Integer SMALLER_COLUMN_SPAN = 1 - 1;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADFieldRepository aDFieldRepository;

    @Autowired
    private ADFieldMapper aDFieldMapper;

    @Autowired
    private ADFieldService aDFieldService;

    @Autowired
    private ADFieldQueryService aDFieldQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADFieldMockMvc;

    private ADField aDField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADField createEntity(EntityManager em) {
        ADField aDField = new ADField()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .hint(DEFAULT_HINT)
            .staticText(DEFAULT_STATIC_TEXT)
            .staticField(DEFAULT_STATIC_FIELD)
            .labelOnly(DEFAULT_LABEL_ONLY)
            .showLabel(DEFAULT_SHOW_LABEL)
            .showInGrid(DEFAULT_SHOW_IN_GRID)
            .showInDetail(DEFAULT_SHOW_IN_DETAIL)
            .gridSequence(DEFAULT_GRID_SEQUENCE)
            .detailSequence(DEFAULT_DETAIL_SEQUENCE)
            .displayLogic(DEFAULT_DISPLAY_LOGIC)
            .writable(DEFAULT_WRITABLE)
            .columnNo(DEFAULT_COLUMN_NO)
            .columnSpan(DEFAULT_COLUMN_SPAN)
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
        aDField.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDField.setAdOrganization(aDOrganization);
        return aDField;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADField createUpdatedEntity(EntityManager em) {
        ADField aDField = new ADField()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hint(UPDATED_HINT)
            .staticText(UPDATED_STATIC_TEXT)
            .staticField(UPDATED_STATIC_FIELD)
            .labelOnly(UPDATED_LABEL_ONLY)
            .showLabel(UPDATED_SHOW_LABEL)
            .showInGrid(UPDATED_SHOW_IN_GRID)
            .showInDetail(UPDATED_SHOW_IN_DETAIL)
            .gridSequence(UPDATED_GRID_SEQUENCE)
            .detailSequence(UPDATED_DETAIL_SEQUENCE)
            .displayLogic(UPDATED_DISPLAY_LOGIC)
            .writable(UPDATED_WRITABLE)
            .columnNo(UPDATED_COLUMN_NO)
            .columnSpan(UPDATED_COLUMN_SPAN)
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
        aDField.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDField.setAdOrganization(aDOrganization);
        return aDField;
    }

    @BeforeEach
    public void initTest() {
        aDField = createEntity(em);
    }

    @Test
    @Transactional
    public void createADField() throws Exception {
        int databaseSizeBeforeCreate = aDFieldRepository.findAll().size();

        // Create the ADField
        ADFieldDTO aDFieldDTO = aDFieldMapper.toDto(aDField);
        restADFieldMockMvc.perform(post("/api/ad-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldDTO)))
            .andExpect(status().isCreated());

        // Validate the ADField in the database
        List<ADField> aDFieldList = aDFieldRepository.findAll();
        assertThat(aDFieldList).hasSize(databaseSizeBeforeCreate + 1);
        ADField testADField = aDFieldList.get(aDFieldList.size() - 1);
        assertThat(testADField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADField.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADField.getHint()).isEqualTo(DEFAULT_HINT);
        assertThat(testADField.getStaticText()).isEqualTo(DEFAULT_STATIC_TEXT);
        assertThat(testADField.isStaticField()).isEqualTo(DEFAULT_STATIC_FIELD);
        assertThat(testADField.isLabelOnly()).isEqualTo(DEFAULT_LABEL_ONLY);
        assertThat(testADField.isShowLabel()).isEqualTo(DEFAULT_SHOW_LABEL);
        assertThat(testADField.isShowInGrid()).isEqualTo(DEFAULT_SHOW_IN_GRID);
        assertThat(testADField.isShowInDetail()).isEqualTo(DEFAULT_SHOW_IN_DETAIL);
        assertThat(testADField.getGridSequence()).isEqualTo(DEFAULT_GRID_SEQUENCE);
        assertThat(testADField.getDetailSequence()).isEqualTo(DEFAULT_DETAIL_SEQUENCE);
        assertThat(testADField.getDisplayLogic()).isEqualTo(DEFAULT_DISPLAY_LOGIC);
        assertThat(testADField.isWritable()).isEqualTo(DEFAULT_WRITABLE);
        assertThat(testADField.getColumnNo()).isEqualTo(DEFAULT_COLUMN_NO);
        assertThat(testADField.getColumnSpan()).isEqualTo(DEFAULT_COLUMN_SPAN);
        assertThat(testADField.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDFieldRepository.findAll().size();

        // Create the ADField with an existing ID
        aDField.setId(1L);
        ADFieldDTO aDFieldDTO = aDFieldMapper.toDto(aDField);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADFieldMockMvc.perform(post("/api/ad-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADField in the database
        List<ADField> aDFieldList = aDFieldRepository.findAll();
        assertThat(aDFieldList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDFieldRepository.findAll().size();
        // set the field null
        aDField.setName(null);

        // Create the ADField, which fails.
        ADFieldDTO aDFieldDTO = aDFieldMapper.toDto(aDField);

        restADFieldMockMvc.perform(post("/api/ad-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldDTO)))
            .andExpect(status().isBadRequest());

        List<ADField> aDFieldList = aDFieldRepository.findAll();
        assertThat(aDFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADFields() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList
        restADFieldMockMvc.perform(get("/api/ad-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDField.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hint").value(hasItem(DEFAULT_HINT)))
            .andExpect(jsonPath("$.[*].staticText").value(hasItem(DEFAULT_STATIC_TEXT)))
            .andExpect(jsonPath("$.[*].staticField").value(hasItem(DEFAULT_STATIC_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].labelOnly").value(hasItem(DEFAULT_LABEL_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].showLabel").value(hasItem(DEFAULT_SHOW_LABEL.booleanValue())))
            .andExpect(jsonPath("$.[*].showInGrid").value(hasItem(DEFAULT_SHOW_IN_GRID.booleanValue())))
            .andExpect(jsonPath("$.[*].showInDetail").value(hasItem(DEFAULT_SHOW_IN_DETAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].gridSequence").value(hasItem(DEFAULT_GRID_SEQUENCE)))
            .andExpect(jsonPath("$.[*].detailSequence").value(hasItem(DEFAULT_DETAIL_SEQUENCE)))
            .andExpect(jsonPath("$.[*].displayLogic").value(hasItem(DEFAULT_DISPLAY_LOGIC)))
            .andExpect(jsonPath("$.[*].writable").value(hasItem(DEFAULT_WRITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].columnSpan").value(hasItem(DEFAULT_COLUMN_SPAN)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADField() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get the aDField
        restADFieldMockMvc.perform(get("/api/ad-fields/{id}", aDField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDField.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.hint").value(DEFAULT_HINT))
            .andExpect(jsonPath("$.staticText").value(DEFAULT_STATIC_TEXT))
            .andExpect(jsonPath("$.staticField").value(DEFAULT_STATIC_FIELD.booleanValue()))
            .andExpect(jsonPath("$.labelOnly").value(DEFAULT_LABEL_ONLY.booleanValue()))
            .andExpect(jsonPath("$.showLabel").value(DEFAULT_SHOW_LABEL.booleanValue()))
            .andExpect(jsonPath("$.showInGrid").value(DEFAULT_SHOW_IN_GRID.booleanValue()))
            .andExpect(jsonPath("$.showInDetail").value(DEFAULT_SHOW_IN_DETAIL.booleanValue()))
            .andExpect(jsonPath("$.gridSequence").value(DEFAULT_GRID_SEQUENCE))
            .andExpect(jsonPath("$.detailSequence").value(DEFAULT_DETAIL_SEQUENCE))
            .andExpect(jsonPath("$.displayLogic").value(DEFAULT_DISPLAY_LOGIC))
            .andExpect(jsonPath("$.writable").value(DEFAULT_WRITABLE.booleanValue()))
            .andExpect(jsonPath("$.columnNo").value(DEFAULT_COLUMN_NO))
            .andExpect(jsonPath("$.columnSpan").value(DEFAULT_COLUMN_SPAN))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADFieldsByIdFiltering() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        Long id = aDField.getId();

        defaultADFieldShouldBeFound("id.equals=" + id);
        defaultADFieldShouldNotBeFound("id.notEquals=" + id);

        defaultADFieldShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADFieldShouldNotBeFound("id.greaterThan=" + id);

        defaultADFieldShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADFieldShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADFieldsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where name equals to DEFAULT_NAME
        defaultADFieldShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDFieldList where name equals to UPDATED_NAME
        defaultADFieldShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where name not equals to DEFAULT_NAME
        defaultADFieldShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDFieldList where name not equals to UPDATED_NAME
        defaultADFieldShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADFieldShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDFieldList where name equals to UPDATED_NAME
        defaultADFieldShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where name is not null
        defaultADFieldShouldBeFound("name.specified=true");

        // Get all the aDFieldList where name is null
        defaultADFieldShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where name contains DEFAULT_NAME
        defaultADFieldShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDFieldList where name contains UPDATED_NAME
        defaultADFieldShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where name does not contain DEFAULT_NAME
        defaultADFieldShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDFieldList where name does not contain UPDATED_NAME
        defaultADFieldShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADFieldsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where description equals to DEFAULT_DESCRIPTION
        defaultADFieldShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDFieldList where description equals to UPDATED_DESCRIPTION
        defaultADFieldShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where description not equals to DEFAULT_DESCRIPTION
        defaultADFieldShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDFieldList where description not equals to UPDATED_DESCRIPTION
        defaultADFieldShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADFieldShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDFieldList where description equals to UPDATED_DESCRIPTION
        defaultADFieldShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where description is not null
        defaultADFieldShouldBeFound("description.specified=true");

        // Get all the aDFieldList where description is null
        defaultADFieldShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where description contains DEFAULT_DESCRIPTION
        defaultADFieldShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDFieldList where description contains UPDATED_DESCRIPTION
        defaultADFieldShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where description does not contain DEFAULT_DESCRIPTION
        defaultADFieldShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDFieldList where description does not contain UPDATED_DESCRIPTION
        defaultADFieldShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADFieldsByHintIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where hint equals to DEFAULT_HINT
        defaultADFieldShouldBeFound("hint.equals=" + DEFAULT_HINT);

        // Get all the aDFieldList where hint equals to UPDATED_HINT
        defaultADFieldShouldNotBeFound("hint.equals=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByHintIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where hint not equals to DEFAULT_HINT
        defaultADFieldShouldNotBeFound("hint.notEquals=" + DEFAULT_HINT);

        // Get all the aDFieldList where hint not equals to UPDATED_HINT
        defaultADFieldShouldBeFound("hint.notEquals=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByHintIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where hint in DEFAULT_HINT or UPDATED_HINT
        defaultADFieldShouldBeFound("hint.in=" + DEFAULT_HINT + "," + UPDATED_HINT);

        // Get all the aDFieldList where hint equals to UPDATED_HINT
        defaultADFieldShouldNotBeFound("hint.in=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByHintIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where hint is not null
        defaultADFieldShouldBeFound("hint.specified=true");

        // Get all the aDFieldList where hint is null
        defaultADFieldShouldNotBeFound("hint.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByHintContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where hint contains DEFAULT_HINT
        defaultADFieldShouldBeFound("hint.contains=" + DEFAULT_HINT);

        // Get all the aDFieldList where hint contains UPDATED_HINT
        defaultADFieldShouldNotBeFound("hint.contains=" + UPDATED_HINT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByHintNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where hint does not contain DEFAULT_HINT
        defaultADFieldShouldNotBeFound("hint.doesNotContain=" + DEFAULT_HINT);

        // Get all the aDFieldList where hint does not contain UPDATED_HINT
        defaultADFieldShouldBeFound("hint.doesNotContain=" + UPDATED_HINT);
    }


    @Test
    @Transactional
    public void getAllADFieldsByStaticTextIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticText equals to DEFAULT_STATIC_TEXT
        defaultADFieldShouldBeFound("staticText.equals=" + DEFAULT_STATIC_TEXT);

        // Get all the aDFieldList where staticText equals to UPDATED_STATIC_TEXT
        defaultADFieldShouldNotBeFound("staticText.equals=" + UPDATED_STATIC_TEXT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticText not equals to DEFAULT_STATIC_TEXT
        defaultADFieldShouldNotBeFound("staticText.notEquals=" + DEFAULT_STATIC_TEXT);

        // Get all the aDFieldList where staticText not equals to UPDATED_STATIC_TEXT
        defaultADFieldShouldBeFound("staticText.notEquals=" + UPDATED_STATIC_TEXT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticTextIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticText in DEFAULT_STATIC_TEXT or UPDATED_STATIC_TEXT
        defaultADFieldShouldBeFound("staticText.in=" + DEFAULT_STATIC_TEXT + "," + UPDATED_STATIC_TEXT);

        // Get all the aDFieldList where staticText equals to UPDATED_STATIC_TEXT
        defaultADFieldShouldNotBeFound("staticText.in=" + UPDATED_STATIC_TEXT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticText is not null
        defaultADFieldShouldBeFound("staticText.specified=true");

        // Get all the aDFieldList where staticText is null
        defaultADFieldShouldNotBeFound("staticText.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByStaticTextContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticText contains DEFAULT_STATIC_TEXT
        defaultADFieldShouldBeFound("staticText.contains=" + DEFAULT_STATIC_TEXT);

        // Get all the aDFieldList where staticText contains UPDATED_STATIC_TEXT
        defaultADFieldShouldNotBeFound("staticText.contains=" + UPDATED_STATIC_TEXT);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticTextNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticText does not contain DEFAULT_STATIC_TEXT
        defaultADFieldShouldNotBeFound("staticText.doesNotContain=" + DEFAULT_STATIC_TEXT);

        // Get all the aDFieldList where staticText does not contain UPDATED_STATIC_TEXT
        defaultADFieldShouldBeFound("staticText.doesNotContain=" + UPDATED_STATIC_TEXT);
    }


    @Test
    @Transactional
    public void getAllADFieldsByStaticFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticField equals to DEFAULT_STATIC_FIELD
        defaultADFieldShouldBeFound("staticField.equals=" + DEFAULT_STATIC_FIELD);

        // Get all the aDFieldList where staticField equals to UPDATED_STATIC_FIELD
        defaultADFieldShouldNotBeFound("staticField.equals=" + UPDATED_STATIC_FIELD);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticFieldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticField not equals to DEFAULT_STATIC_FIELD
        defaultADFieldShouldNotBeFound("staticField.notEquals=" + DEFAULT_STATIC_FIELD);

        // Get all the aDFieldList where staticField not equals to UPDATED_STATIC_FIELD
        defaultADFieldShouldBeFound("staticField.notEquals=" + UPDATED_STATIC_FIELD);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticFieldIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticField in DEFAULT_STATIC_FIELD or UPDATED_STATIC_FIELD
        defaultADFieldShouldBeFound("staticField.in=" + DEFAULT_STATIC_FIELD + "," + UPDATED_STATIC_FIELD);

        // Get all the aDFieldList where staticField equals to UPDATED_STATIC_FIELD
        defaultADFieldShouldNotBeFound("staticField.in=" + UPDATED_STATIC_FIELD);
    }

    @Test
    @Transactional
    public void getAllADFieldsByStaticFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where staticField is not null
        defaultADFieldShouldBeFound("staticField.specified=true");

        // Get all the aDFieldList where staticField is null
        defaultADFieldShouldNotBeFound("staticField.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByLabelOnlyIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where labelOnly equals to DEFAULT_LABEL_ONLY
        defaultADFieldShouldBeFound("labelOnly.equals=" + DEFAULT_LABEL_ONLY);

        // Get all the aDFieldList where labelOnly equals to UPDATED_LABEL_ONLY
        defaultADFieldShouldNotBeFound("labelOnly.equals=" + UPDATED_LABEL_ONLY);
    }

    @Test
    @Transactional
    public void getAllADFieldsByLabelOnlyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where labelOnly not equals to DEFAULT_LABEL_ONLY
        defaultADFieldShouldNotBeFound("labelOnly.notEquals=" + DEFAULT_LABEL_ONLY);

        // Get all the aDFieldList where labelOnly not equals to UPDATED_LABEL_ONLY
        defaultADFieldShouldBeFound("labelOnly.notEquals=" + UPDATED_LABEL_ONLY);
    }

    @Test
    @Transactional
    public void getAllADFieldsByLabelOnlyIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where labelOnly in DEFAULT_LABEL_ONLY or UPDATED_LABEL_ONLY
        defaultADFieldShouldBeFound("labelOnly.in=" + DEFAULT_LABEL_ONLY + "," + UPDATED_LABEL_ONLY);

        // Get all the aDFieldList where labelOnly equals to UPDATED_LABEL_ONLY
        defaultADFieldShouldNotBeFound("labelOnly.in=" + UPDATED_LABEL_ONLY);
    }

    @Test
    @Transactional
    public void getAllADFieldsByLabelOnlyIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where labelOnly is not null
        defaultADFieldShouldBeFound("labelOnly.specified=true");

        // Get all the aDFieldList where labelOnly is null
        defaultADFieldShouldNotBeFound("labelOnly.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showLabel equals to DEFAULT_SHOW_LABEL
        defaultADFieldShouldBeFound("showLabel.equals=" + DEFAULT_SHOW_LABEL);

        // Get all the aDFieldList where showLabel equals to UPDATED_SHOW_LABEL
        defaultADFieldShouldNotBeFound("showLabel.equals=" + UPDATED_SHOW_LABEL);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowLabelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showLabel not equals to DEFAULT_SHOW_LABEL
        defaultADFieldShouldNotBeFound("showLabel.notEquals=" + DEFAULT_SHOW_LABEL);

        // Get all the aDFieldList where showLabel not equals to UPDATED_SHOW_LABEL
        defaultADFieldShouldBeFound("showLabel.notEquals=" + UPDATED_SHOW_LABEL);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowLabelIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showLabel in DEFAULT_SHOW_LABEL or UPDATED_SHOW_LABEL
        defaultADFieldShouldBeFound("showLabel.in=" + DEFAULT_SHOW_LABEL + "," + UPDATED_SHOW_LABEL);

        // Get all the aDFieldList where showLabel equals to UPDATED_SHOW_LABEL
        defaultADFieldShouldNotBeFound("showLabel.in=" + UPDATED_SHOW_LABEL);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showLabel is not null
        defaultADFieldShouldBeFound("showLabel.specified=true");

        // Get all the aDFieldList where showLabel is null
        defaultADFieldShouldNotBeFound("showLabel.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInGridIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInGrid equals to DEFAULT_SHOW_IN_GRID
        defaultADFieldShouldBeFound("showInGrid.equals=" + DEFAULT_SHOW_IN_GRID);

        // Get all the aDFieldList where showInGrid equals to UPDATED_SHOW_IN_GRID
        defaultADFieldShouldNotBeFound("showInGrid.equals=" + UPDATED_SHOW_IN_GRID);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInGridIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInGrid not equals to DEFAULT_SHOW_IN_GRID
        defaultADFieldShouldNotBeFound("showInGrid.notEquals=" + DEFAULT_SHOW_IN_GRID);

        // Get all the aDFieldList where showInGrid not equals to UPDATED_SHOW_IN_GRID
        defaultADFieldShouldBeFound("showInGrid.notEquals=" + UPDATED_SHOW_IN_GRID);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInGridIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInGrid in DEFAULT_SHOW_IN_GRID or UPDATED_SHOW_IN_GRID
        defaultADFieldShouldBeFound("showInGrid.in=" + DEFAULT_SHOW_IN_GRID + "," + UPDATED_SHOW_IN_GRID);

        // Get all the aDFieldList where showInGrid equals to UPDATED_SHOW_IN_GRID
        defaultADFieldShouldNotBeFound("showInGrid.in=" + UPDATED_SHOW_IN_GRID);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInGridIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInGrid is not null
        defaultADFieldShouldBeFound("showInGrid.specified=true");

        // Get all the aDFieldList where showInGrid is null
        defaultADFieldShouldNotBeFound("showInGrid.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInDetail equals to DEFAULT_SHOW_IN_DETAIL
        defaultADFieldShouldBeFound("showInDetail.equals=" + DEFAULT_SHOW_IN_DETAIL);

        // Get all the aDFieldList where showInDetail equals to UPDATED_SHOW_IN_DETAIL
        defaultADFieldShouldNotBeFound("showInDetail.equals=" + UPDATED_SHOW_IN_DETAIL);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInDetailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInDetail not equals to DEFAULT_SHOW_IN_DETAIL
        defaultADFieldShouldNotBeFound("showInDetail.notEquals=" + DEFAULT_SHOW_IN_DETAIL);

        // Get all the aDFieldList where showInDetail not equals to UPDATED_SHOW_IN_DETAIL
        defaultADFieldShouldBeFound("showInDetail.notEquals=" + UPDATED_SHOW_IN_DETAIL);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInDetailIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInDetail in DEFAULT_SHOW_IN_DETAIL or UPDATED_SHOW_IN_DETAIL
        defaultADFieldShouldBeFound("showInDetail.in=" + DEFAULT_SHOW_IN_DETAIL + "," + UPDATED_SHOW_IN_DETAIL);

        // Get all the aDFieldList where showInDetail equals to UPDATED_SHOW_IN_DETAIL
        defaultADFieldShouldNotBeFound("showInDetail.in=" + UPDATED_SHOW_IN_DETAIL);
    }

    @Test
    @Transactional
    public void getAllADFieldsByShowInDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where showInDetail is not null
        defaultADFieldShouldBeFound("showInDetail.specified=true");

        // Get all the aDFieldList where showInDetail is null
        defaultADFieldShouldNotBeFound("showInDetail.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence equals to DEFAULT_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.equals=" + DEFAULT_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence equals to UPDATED_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.equals=" + UPDATED_GRID_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence not equals to DEFAULT_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.notEquals=" + DEFAULT_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence not equals to UPDATED_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.notEquals=" + UPDATED_GRID_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence in DEFAULT_GRID_SEQUENCE or UPDATED_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.in=" + DEFAULT_GRID_SEQUENCE + "," + UPDATED_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence equals to UPDATED_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.in=" + UPDATED_GRID_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence is not null
        defaultADFieldShouldBeFound("gridSequence.specified=true");

        // Get all the aDFieldList where gridSequence is null
        defaultADFieldShouldNotBeFound("gridSequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence is greater than or equal to DEFAULT_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.greaterThanOrEqual=" + DEFAULT_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence is greater than or equal to UPDATED_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.greaterThanOrEqual=" + UPDATED_GRID_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence is less than or equal to DEFAULT_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.lessThanOrEqual=" + DEFAULT_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence is less than or equal to SMALLER_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.lessThanOrEqual=" + SMALLER_GRID_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence is less than DEFAULT_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.lessThan=" + DEFAULT_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence is less than UPDATED_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.lessThan=" + UPDATED_GRID_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByGridSequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where gridSequence is greater than DEFAULT_GRID_SEQUENCE
        defaultADFieldShouldNotBeFound("gridSequence.greaterThan=" + DEFAULT_GRID_SEQUENCE);

        // Get all the aDFieldList where gridSequence is greater than SMALLER_GRID_SEQUENCE
        defaultADFieldShouldBeFound("gridSequence.greaterThan=" + SMALLER_GRID_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence equals to DEFAULT_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.equals=" + DEFAULT_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence equals to UPDATED_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.equals=" + UPDATED_DETAIL_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence not equals to DEFAULT_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.notEquals=" + DEFAULT_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence not equals to UPDATED_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.notEquals=" + UPDATED_DETAIL_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence in DEFAULT_DETAIL_SEQUENCE or UPDATED_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.in=" + DEFAULT_DETAIL_SEQUENCE + "," + UPDATED_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence equals to UPDATED_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.in=" + UPDATED_DETAIL_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence is not null
        defaultADFieldShouldBeFound("detailSequence.specified=true");

        // Get all the aDFieldList where detailSequence is null
        defaultADFieldShouldNotBeFound("detailSequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence is greater than or equal to DEFAULT_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.greaterThanOrEqual=" + DEFAULT_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence is greater than or equal to UPDATED_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.greaterThanOrEqual=" + UPDATED_DETAIL_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence is less than or equal to DEFAULT_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.lessThanOrEqual=" + DEFAULT_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence is less than or equal to SMALLER_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.lessThanOrEqual=" + SMALLER_DETAIL_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence is less than DEFAULT_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.lessThan=" + DEFAULT_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence is less than UPDATED_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.lessThan=" + UPDATED_DETAIL_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDetailSequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where detailSequence is greater than DEFAULT_DETAIL_SEQUENCE
        defaultADFieldShouldNotBeFound("detailSequence.greaterThan=" + DEFAULT_DETAIL_SEQUENCE);

        // Get all the aDFieldList where detailSequence is greater than SMALLER_DETAIL_SEQUENCE
        defaultADFieldShouldBeFound("detailSequence.greaterThan=" + SMALLER_DETAIL_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllADFieldsByDisplayLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where displayLogic equals to DEFAULT_DISPLAY_LOGIC
        defaultADFieldShouldBeFound("displayLogic.equals=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDFieldList where displayLogic equals to UPDATED_DISPLAY_LOGIC
        defaultADFieldShouldNotBeFound("displayLogic.equals=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDisplayLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where displayLogic not equals to DEFAULT_DISPLAY_LOGIC
        defaultADFieldShouldNotBeFound("displayLogic.notEquals=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDFieldList where displayLogic not equals to UPDATED_DISPLAY_LOGIC
        defaultADFieldShouldBeFound("displayLogic.notEquals=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDisplayLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where displayLogic in DEFAULT_DISPLAY_LOGIC or UPDATED_DISPLAY_LOGIC
        defaultADFieldShouldBeFound("displayLogic.in=" + DEFAULT_DISPLAY_LOGIC + "," + UPDATED_DISPLAY_LOGIC);

        // Get all the aDFieldList where displayLogic equals to UPDATED_DISPLAY_LOGIC
        defaultADFieldShouldNotBeFound("displayLogic.in=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDisplayLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where displayLogic is not null
        defaultADFieldShouldBeFound("displayLogic.specified=true");

        // Get all the aDFieldList where displayLogic is null
        defaultADFieldShouldNotBeFound("displayLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByDisplayLogicContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where displayLogic contains DEFAULT_DISPLAY_LOGIC
        defaultADFieldShouldBeFound("displayLogic.contains=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDFieldList where displayLogic contains UPDATED_DISPLAY_LOGIC
        defaultADFieldShouldNotBeFound("displayLogic.contains=" + UPDATED_DISPLAY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDisplayLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where displayLogic does not contain DEFAULT_DISPLAY_LOGIC
        defaultADFieldShouldNotBeFound("displayLogic.doesNotContain=" + DEFAULT_DISPLAY_LOGIC);

        // Get all the aDFieldList where displayLogic does not contain UPDATED_DISPLAY_LOGIC
        defaultADFieldShouldBeFound("displayLogic.doesNotContain=" + UPDATED_DISPLAY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADFieldsByWritableIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where writable equals to DEFAULT_WRITABLE
        defaultADFieldShouldBeFound("writable.equals=" + DEFAULT_WRITABLE);

        // Get all the aDFieldList where writable equals to UPDATED_WRITABLE
        defaultADFieldShouldNotBeFound("writable.equals=" + UPDATED_WRITABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByWritableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where writable not equals to DEFAULT_WRITABLE
        defaultADFieldShouldNotBeFound("writable.notEquals=" + DEFAULT_WRITABLE);

        // Get all the aDFieldList where writable not equals to UPDATED_WRITABLE
        defaultADFieldShouldBeFound("writable.notEquals=" + UPDATED_WRITABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByWritableIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where writable in DEFAULT_WRITABLE or UPDATED_WRITABLE
        defaultADFieldShouldBeFound("writable.in=" + DEFAULT_WRITABLE + "," + UPDATED_WRITABLE);

        // Get all the aDFieldList where writable equals to UPDATED_WRITABLE
        defaultADFieldShouldNotBeFound("writable.in=" + UPDATED_WRITABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByWritableIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where writable is not null
        defaultADFieldShouldBeFound("writable.specified=true");

        // Get all the aDFieldList where writable is null
        defaultADFieldShouldNotBeFound("writable.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo equals to DEFAULT_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.equals=" + DEFAULT_COLUMN_NO);

        // Get all the aDFieldList where columnNo equals to UPDATED_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.equals=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo not equals to DEFAULT_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.notEquals=" + DEFAULT_COLUMN_NO);

        // Get all the aDFieldList where columnNo not equals to UPDATED_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.notEquals=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo in DEFAULT_COLUMN_NO or UPDATED_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.in=" + DEFAULT_COLUMN_NO + "," + UPDATED_COLUMN_NO);

        // Get all the aDFieldList where columnNo equals to UPDATED_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.in=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo is not null
        defaultADFieldShouldBeFound("columnNo.specified=true");

        // Get all the aDFieldList where columnNo is null
        defaultADFieldShouldNotBeFound("columnNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo is greater than or equal to DEFAULT_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.greaterThanOrEqual=" + DEFAULT_COLUMN_NO);

        // Get all the aDFieldList where columnNo is greater than or equal to UPDATED_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.greaterThanOrEqual=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo is less than or equal to DEFAULT_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.lessThanOrEqual=" + DEFAULT_COLUMN_NO);

        // Get all the aDFieldList where columnNo is less than or equal to SMALLER_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.lessThanOrEqual=" + SMALLER_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo is less than DEFAULT_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.lessThan=" + DEFAULT_COLUMN_NO);

        // Get all the aDFieldList where columnNo is less than UPDATED_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.lessThan=" + UPDATED_COLUMN_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnNo is greater than DEFAULT_COLUMN_NO
        defaultADFieldShouldNotBeFound("columnNo.greaterThan=" + DEFAULT_COLUMN_NO);

        // Get all the aDFieldList where columnNo is greater than SMALLER_COLUMN_NO
        defaultADFieldShouldBeFound("columnNo.greaterThan=" + SMALLER_COLUMN_NO);
    }


    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan equals to DEFAULT_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.equals=" + DEFAULT_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan equals to UPDATED_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.equals=" + UPDATED_COLUMN_SPAN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan not equals to DEFAULT_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.notEquals=" + DEFAULT_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan not equals to UPDATED_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.notEquals=" + UPDATED_COLUMN_SPAN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan in DEFAULT_COLUMN_SPAN or UPDATED_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.in=" + DEFAULT_COLUMN_SPAN + "," + UPDATED_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan equals to UPDATED_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.in=" + UPDATED_COLUMN_SPAN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan is not null
        defaultADFieldShouldBeFound("columnSpan.specified=true");

        // Get all the aDFieldList where columnSpan is null
        defaultADFieldShouldNotBeFound("columnSpan.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan is greater than or equal to DEFAULT_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.greaterThanOrEqual=" + DEFAULT_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan is greater than or equal to UPDATED_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.greaterThanOrEqual=" + UPDATED_COLUMN_SPAN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan is less than or equal to DEFAULT_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.lessThanOrEqual=" + DEFAULT_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan is less than or equal to SMALLER_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.lessThanOrEqual=" + SMALLER_COLUMN_SPAN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan is less than DEFAULT_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.lessThan=" + DEFAULT_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan is less than UPDATED_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.lessThan=" + UPDATED_COLUMN_SPAN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnSpanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnSpan is greater than DEFAULT_COLUMN_SPAN
        defaultADFieldShouldNotBeFound("columnSpan.greaterThan=" + DEFAULT_COLUMN_SPAN);

        // Get all the aDFieldList where columnSpan is greater than SMALLER_COLUMN_SPAN
        defaultADFieldShouldBeFound("columnSpan.greaterThan=" + SMALLER_COLUMN_SPAN);
    }


    @Test
    @Transactional
    public void getAllADFieldsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where active equals to DEFAULT_ACTIVE
        defaultADFieldShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDFieldList where active equals to UPDATED_ACTIVE
        defaultADFieldShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where active not equals to DEFAULT_ACTIVE
        defaultADFieldShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDFieldList where active not equals to UPDATED_ACTIVE
        defaultADFieldShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADFieldShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDFieldList where active equals to UPDATED_ACTIVE
        defaultADFieldShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where active is not null
        defaultADFieldShouldBeFound("active.specified=true");

        // Get all the aDFieldList where active is null
        defaultADFieldShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByAdClientIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADClient adClient = aDField.getAdClient();
        aDFieldRepository.saveAndFlush(aDField);
        Long adClientId = adClient.getId();

        // Get all the aDFieldList where adClient equals to adClientId
        defaultADFieldShouldBeFound("adClientId.equals=" + adClientId);

        // Get all the aDFieldList where adClient equals to adClientId + 1
        defaultADFieldShouldNotBeFound("adClientId.equals=" + (adClientId + 1));
    }


    @Test
    @Transactional
    public void getAllADFieldsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDField.getAdOrganization();
        aDFieldRepository.saveAndFlush(aDField);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDFieldList where adOrganization equals to adOrganizationId
        defaultADFieldShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDFieldList where adOrganization equals to adOrganizationId + 1
        defaultADFieldShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADFieldShouldBeFound(String filter) throws Exception {
        restADFieldMockMvc.perform(get("/api/ad-fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDField.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hint").value(hasItem(DEFAULT_HINT)))
            .andExpect(jsonPath("$.[*].staticText").value(hasItem(DEFAULT_STATIC_TEXT)))
            .andExpect(jsonPath("$.[*].staticField").value(hasItem(DEFAULT_STATIC_FIELD.booleanValue())))
            .andExpect(jsonPath("$.[*].labelOnly").value(hasItem(DEFAULT_LABEL_ONLY.booleanValue())))
            .andExpect(jsonPath("$.[*].showLabel").value(hasItem(DEFAULT_SHOW_LABEL.booleanValue())))
            .andExpect(jsonPath("$.[*].showInGrid").value(hasItem(DEFAULT_SHOW_IN_GRID.booleanValue())))
            .andExpect(jsonPath("$.[*].showInDetail").value(hasItem(DEFAULT_SHOW_IN_DETAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].gridSequence").value(hasItem(DEFAULT_GRID_SEQUENCE)))
            .andExpect(jsonPath("$.[*].detailSequence").value(hasItem(DEFAULT_DETAIL_SEQUENCE)))
            .andExpect(jsonPath("$.[*].displayLogic").value(hasItem(DEFAULT_DISPLAY_LOGIC)))
            .andExpect(jsonPath("$.[*].writable").value(hasItem(DEFAULT_WRITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].columnSpan").value(hasItem(DEFAULT_COLUMN_SPAN)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADFieldMockMvc.perform(get("/api/ad-fields/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADFieldShouldNotBeFound(String filter) throws Exception {
        restADFieldMockMvc.perform(get("/api/ad-fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADFieldMockMvc.perform(get("/api/ad-fields/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADField() throws Exception {
        // Get the aDField
        restADFieldMockMvc.perform(get("/api/ad-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADField() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        int databaseSizeBeforeUpdate = aDFieldRepository.findAll().size();

        // Update the aDField
        ADField updatedADField = aDFieldRepository.findById(aDField.getId()).get();
        // Disconnect from session so that the updates on updatedADField are not directly saved in db
        em.detach(updatedADField);
        updatedADField
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hint(UPDATED_HINT)
            .staticText(UPDATED_STATIC_TEXT)
            .staticField(UPDATED_STATIC_FIELD)
            .labelOnly(UPDATED_LABEL_ONLY)
            .showLabel(UPDATED_SHOW_LABEL)
            .showInGrid(UPDATED_SHOW_IN_GRID)
            .showInDetail(UPDATED_SHOW_IN_DETAIL)
            .gridSequence(UPDATED_GRID_SEQUENCE)
            .detailSequence(UPDATED_DETAIL_SEQUENCE)
            .displayLogic(UPDATED_DISPLAY_LOGIC)
            .writable(UPDATED_WRITABLE)
            .columnNo(UPDATED_COLUMN_NO)
            .columnSpan(UPDATED_COLUMN_SPAN)
            .active(UPDATED_ACTIVE);
        ADFieldDTO aDFieldDTO = aDFieldMapper.toDto(updatedADField);

        restADFieldMockMvc.perform(put("/api/ad-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldDTO)))
            .andExpect(status().isOk());

        // Validate the ADField in the database
        List<ADField> aDFieldList = aDFieldRepository.findAll();
        assertThat(aDFieldList).hasSize(databaseSizeBeforeUpdate);
        ADField testADField = aDFieldList.get(aDFieldList.size() - 1);
        assertThat(testADField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADField.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADField.getHint()).isEqualTo(UPDATED_HINT);
        assertThat(testADField.getStaticText()).isEqualTo(UPDATED_STATIC_TEXT);
        assertThat(testADField.isStaticField()).isEqualTo(UPDATED_STATIC_FIELD);
        assertThat(testADField.isLabelOnly()).isEqualTo(UPDATED_LABEL_ONLY);
        assertThat(testADField.isShowLabel()).isEqualTo(UPDATED_SHOW_LABEL);
        assertThat(testADField.isShowInGrid()).isEqualTo(UPDATED_SHOW_IN_GRID);
        assertThat(testADField.isShowInDetail()).isEqualTo(UPDATED_SHOW_IN_DETAIL);
        assertThat(testADField.getGridSequence()).isEqualTo(UPDATED_GRID_SEQUENCE);
        assertThat(testADField.getDetailSequence()).isEqualTo(UPDATED_DETAIL_SEQUENCE);
        assertThat(testADField.getDisplayLogic()).isEqualTo(UPDATED_DISPLAY_LOGIC);
        assertThat(testADField.isWritable()).isEqualTo(UPDATED_WRITABLE);
        assertThat(testADField.getColumnNo()).isEqualTo(UPDATED_COLUMN_NO);
        assertThat(testADField.getColumnSpan()).isEqualTo(UPDATED_COLUMN_SPAN);
        assertThat(testADField.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADField() throws Exception {
        int databaseSizeBeforeUpdate = aDFieldRepository.findAll().size();

        // Create the ADField
        ADFieldDTO aDFieldDTO = aDFieldMapper.toDto(aDField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADFieldMockMvc.perform(put("/api/ad-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADField in the database
        List<ADField> aDFieldList = aDFieldRepository.findAll();
        assertThat(aDFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADField() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        int databaseSizeBeforeDelete = aDFieldRepository.findAll().size();

        // Delete the aDField
        restADFieldMockMvc.perform(delete("/api/ad-fields/{id}", aDField.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADField> aDFieldList = aDFieldRepository.findAll();
        assertThat(aDFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
