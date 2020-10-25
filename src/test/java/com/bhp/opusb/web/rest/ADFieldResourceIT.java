package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.domain.AdCallout;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.AdValidationRule;
import com.bhp.opusb.domain.AdButton;
import com.bhp.opusb.domain.ADTab;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhp.opusb.domain.enumeration.ADColumnType;
/**
 * Integration tests for the {@link ADFieldResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADFieldResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

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

    private static final String DEFAULT_READ_ONLY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_READ_ONLY_LOGIC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_WRITABLE = false;
    private static final Boolean UPDATED_WRITABLE = true;

    private static final Integer DEFAULT_COLUMN_NO = 1;
    private static final Integer UPDATED_COLUMN_NO = 2;
    private static final Integer SMALLER_COLUMN_NO = 1 - 1;

    private static final Integer DEFAULT_COLUMN_OFFSET = 1;
    private static final Integer UPDATED_COLUMN_OFFSET = 2;
    private static final Integer SMALLER_COLUMN_OFFSET = 1 - 1;

    private static final Integer DEFAULT_COLUMN_SPAN = 1;
    private static final Integer UPDATED_COLUMN_SPAN = 2;
    private static final Integer SMALLER_COLUMN_SPAN = 1 - 1;

    private static final Integer DEFAULT_ROW_NO = 1;
    private static final Integer UPDATED_ROW_NO = 2;
    private static final Integer SMALLER_ROW_NO = 1 - 1;

    private static final String DEFAULT_VIRTUAL_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VIRTUAL_COLUMN_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final String DEFAULT_MANDATORY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_MANDATORY_LOGIC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UPDATABLE = false;
    private static final Boolean UPDATED_UPDATABLE = true;

    private static final Boolean DEFAULT_ALWAYS_UPDATABLE = false;
    private static final Boolean UPDATED_ALWAYS_UPDATABLE = true;

    private static final Boolean DEFAULT_COPYABLE = false;
    private static final Boolean UPDATED_COPYABLE = true;

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT_PATTERN = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_LENGTH = 1;
    private static final Integer UPDATED_MIN_LENGTH = 2;
    private static final Integer SMALLER_MIN_LENGTH = 1 - 1;

    private static final Integer DEFAULT_MAX_LENGTH = 1;
    private static final Integer UPDATED_MAX_LENGTH = 2;
    private static final Integer SMALLER_MAX_LENGTH = 1 - 1;

    private static final Long DEFAULT_MIN_VALUE = 1L;
    private static final Long UPDATED_MIN_VALUE = 2L;
    private static final Long SMALLER_MIN_VALUE = 1L - 1L;

    private static final Long DEFAULT_MAX_VALUE = 1L;
    private static final Long UPDATED_MAX_VALUE = 2L;
    private static final Long SMALLER_MAX_VALUE = 1L - 1L;

    private static final ADColumnType DEFAULT_TYPE = ADColumnType.STRING;
    private static final ADColumnType UPDATED_TYPE = ADColumnType.INTEGER;

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
            .uid(DEFAULT_UID)
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
            .readOnlyLogic(DEFAULT_READ_ONLY_LOGIC)
            .writable(DEFAULT_WRITABLE)
            .columnNo(DEFAULT_COLUMN_NO)
            .columnOffset(DEFAULT_COLUMN_OFFSET)
            .columnSpan(DEFAULT_COLUMN_SPAN)
            .rowNo(DEFAULT_ROW_NO)
            .virtualColumnName(DEFAULT_VIRTUAL_COLUMN_NAME)
            .mandatory(DEFAULT_MANDATORY)
            .mandatoryLogic(DEFAULT_MANDATORY_LOGIC)
            .updatable(DEFAULT_UPDATABLE)
            .alwaysUpdatable(DEFAULT_ALWAYS_UPDATABLE)
            .copyable(DEFAULT_COPYABLE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .formatPattern(DEFAULT_FORMAT_PATTERN)
            .minLength(DEFAULT_MIN_LENGTH)
            .maxLength(DEFAULT_MAX_LENGTH)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
            .type(DEFAULT_TYPE)
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
        aDField.setAdOrganization(aDOrganization);
        // Add required entity
        ADTab aDTab;
        if (TestUtil.findAll(em, ADTab.class).isEmpty()) {
            aDTab = ADTabResourceIT.createEntity(em);
            em.persist(aDTab);
            em.flush();
        } else {
            aDTab = TestUtil.findAll(em, ADTab.class).get(0);
        }
        aDField.setAdTab(aDTab);
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
            .uid(UPDATED_UID)
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
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .writable(UPDATED_WRITABLE)
            .columnNo(UPDATED_COLUMN_NO)
            .columnOffset(UPDATED_COLUMN_OFFSET)
            .columnSpan(UPDATED_COLUMN_SPAN)
            .rowNo(UPDATED_ROW_NO)
            .virtualColumnName(UPDATED_VIRTUAL_COLUMN_NAME)
            .mandatory(UPDATED_MANDATORY)
            .mandatoryLogic(UPDATED_MANDATORY_LOGIC)
            .updatable(UPDATED_UPDATABLE)
            .alwaysUpdatable(UPDATED_ALWAYS_UPDATABLE)
            .copyable(UPDATED_COPYABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .formatPattern(UPDATED_FORMAT_PATTERN)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .type(UPDATED_TYPE)
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
        aDField.setAdOrganization(aDOrganization);
        // Add required entity
        ADTab aDTab;
        if (TestUtil.findAll(em, ADTab.class).isEmpty()) {
            aDTab = ADTabResourceIT.createUpdatedEntity(em);
            em.persist(aDTab);
            em.flush();
        } else {
            aDTab = TestUtil.findAll(em, ADTab.class).get(0);
        }
        aDField.setAdTab(aDTab);
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
        assertThat(testADField.getUid()).isEqualTo(DEFAULT_UID);
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
        assertThat(testADField.getReadOnlyLogic()).isEqualTo(DEFAULT_READ_ONLY_LOGIC);
        assertThat(testADField.isWritable()).isEqualTo(DEFAULT_WRITABLE);
        assertThat(testADField.getColumnNo()).isEqualTo(DEFAULT_COLUMN_NO);
        assertThat(testADField.getColumnOffset()).isEqualTo(DEFAULT_COLUMN_OFFSET);
        assertThat(testADField.getColumnSpan()).isEqualTo(DEFAULT_COLUMN_SPAN);
        assertThat(testADField.getRowNo()).isEqualTo(DEFAULT_ROW_NO);
        assertThat(testADField.getVirtualColumnName()).isEqualTo(DEFAULT_VIRTUAL_COLUMN_NAME);
        assertThat(testADField.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testADField.getMandatoryLogic()).isEqualTo(DEFAULT_MANDATORY_LOGIC);
        assertThat(testADField.isUpdatable()).isEqualTo(DEFAULT_UPDATABLE);
        assertThat(testADField.isAlwaysUpdatable()).isEqualTo(DEFAULT_ALWAYS_UPDATABLE);
        assertThat(testADField.isCopyable()).isEqualTo(DEFAULT_COPYABLE);
        assertThat(testADField.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testADField.getFormatPattern()).isEqualTo(DEFAULT_FORMAT_PATTERN);
        assertThat(testADField.getMinLength()).isEqualTo(DEFAULT_MIN_LENGTH);
        assertThat(testADField.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
        assertThat(testADField.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testADField.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testADField.getType()).isEqualTo(DEFAULT_TYPE);
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
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
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
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].writable").value(hasItem(DEFAULT_WRITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].columnOffset").value(hasItem(DEFAULT_COLUMN_OFFSET)))
            .andExpect(jsonPath("$.[*].columnSpan").value(hasItem(DEFAULT_COLUMN_SPAN)))
            .andExpect(jsonPath("$.[*].rowNo").value(hasItem(DEFAULT_ROW_NO)))
            .andExpect(jsonPath("$.[*].virtualColumnName").value(hasItem(DEFAULT_VIRTUAL_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryLogic").value(hasItem(DEFAULT_MANDATORY_LOGIC)))
            .andExpect(jsonPath("$.[*].updatable").value(hasItem(DEFAULT_UPDATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].alwaysUpdatable").value(hasItem(DEFAULT_ALWAYS_UPDATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].copyable").value(hasItem(DEFAULT_COPYABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].formatPattern").value(hasItem(DEFAULT_FORMAT_PATTERN)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
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
            .andExpect(jsonPath("$.readOnlyLogic").value(DEFAULT_READ_ONLY_LOGIC))
            .andExpect(jsonPath("$.writable").value(DEFAULT_WRITABLE.booleanValue()))
            .andExpect(jsonPath("$.columnNo").value(DEFAULT_COLUMN_NO))
            .andExpect(jsonPath("$.columnOffset").value(DEFAULT_COLUMN_OFFSET))
            .andExpect(jsonPath("$.columnSpan").value(DEFAULT_COLUMN_SPAN))
            .andExpect(jsonPath("$.rowNo").value(DEFAULT_ROW_NO))
            .andExpect(jsonPath("$.virtualColumnName").value(DEFAULT_VIRTUAL_COLUMN_NAME))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.mandatoryLogic").value(DEFAULT_MANDATORY_LOGIC))
            .andExpect(jsonPath("$.updatable").value(DEFAULT_UPDATABLE.booleanValue()))
            .andExpect(jsonPath("$.alwaysUpdatable").value(DEFAULT_ALWAYS_UPDATABLE.booleanValue()))
            .andExpect(jsonPath("$.copyable").value(DEFAULT_COPYABLE.booleanValue()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.formatPattern").value(DEFAULT_FORMAT_PATTERN))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE.intValue()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
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
    public void getAllADFieldsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where uid equals to DEFAULT_UID
        defaultADFieldShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the aDFieldList where uid equals to UPDATED_UID
        defaultADFieldShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADFieldsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where uid not equals to DEFAULT_UID
        defaultADFieldShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the aDFieldList where uid not equals to UPDATED_UID
        defaultADFieldShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADFieldsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where uid in DEFAULT_UID or UPDATED_UID
        defaultADFieldShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the aDFieldList where uid equals to UPDATED_UID
        defaultADFieldShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllADFieldsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where uid is not null
        defaultADFieldShouldBeFound("uid.specified=true");

        // Get all the aDFieldList where uid is null
        defaultADFieldShouldNotBeFound("uid.specified=false");
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
    public void getAllADFieldsByReadOnlyLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where readOnlyLogic equals to DEFAULT_READ_ONLY_LOGIC
        defaultADFieldShouldBeFound("readOnlyLogic.equals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDFieldList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultADFieldShouldNotBeFound("readOnlyLogic.equals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByReadOnlyLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where readOnlyLogic not equals to DEFAULT_READ_ONLY_LOGIC
        defaultADFieldShouldNotBeFound("readOnlyLogic.notEquals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDFieldList where readOnlyLogic not equals to UPDATED_READ_ONLY_LOGIC
        defaultADFieldShouldBeFound("readOnlyLogic.notEquals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByReadOnlyLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where readOnlyLogic in DEFAULT_READ_ONLY_LOGIC or UPDATED_READ_ONLY_LOGIC
        defaultADFieldShouldBeFound("readOnlyLogic.in=" + DEFAULT_READ_ONLY_LOGIC + "," + UPDATED_READ_ONLY_LOGIC);

        // Get all the aDFieldList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultADFieldShouldNotBeFound("readOnlyLogic.in=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByReadOnlyLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where readOnlyLogic is not null
        defaultADFieldShouldBeFound("readOnlyLogic.specified=true");

        // Get all the aDFieldList where readOnlyLogic is null
        defaultADFieldShouldNotBeFound("readOnlyLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByReadOnlyLogicContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where readOnlyLogic contains DEFAULT_READ_ONLY_LOGIC
        defaultADFieldShouldBeFound("readOnlyLogic.contains=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDFieldList where readOnlyLogic contains UPDATED_READ_ONLY_LOGIC
        defaultADFieldShouldNotBeFound("readOnlyLogic.contains=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByReadOnlyLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where readOnlyLogic does not contain DEFAULT_READ_ONLY_LOGIC
        defaultADFieldShouldNotBeFound("readOnlyLogic.doesNotContain=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDFieldList where readOnlyLogic does not contain UPDATED_READ_ONLY_LOGIC
        defaultADFieldShouldBeFound("readOnlyLogic.doesNotContain=" + UPDATED_READ_ONLY_LOGIC);
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
    public void getAllADFieldsByColumnOffsetIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset equals to DEFAULT_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.equals=" + DEFAULT_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset equals to UPDATED_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.equals=" + UPDATED_COLUMN_OFFSET);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset not equals to DEFAULT_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.notEquals=" + DEFAULT_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset not equals to UPDATED_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.notEquals=" + UPDATED_COLUMN_OFFSET);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset in DEFAULT_COLUMN_OFFSET or UPDATED_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.in=" + DEFAULT_COLUMN_OFFSET + "," + UPDATED_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset equals to UPDATED_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.in=" + UPDATED_COLUMN_OFFSET);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset is not null
        defaultADFieldShouldBeFound("columnOffset.specified=true");

        // Get all the aDFieldList where columnOffset is null
        defaultADFieldShouldNotBeFound("columnOffset.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset is greater than or equal to DEFAULT_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.greaterThanOrEqual=" + DEFAULT_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset is greater than or equal to UPDATED_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.greaterThanOrEqual=" + UPDATED_COLUMN_OFFSET);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset is less than or equal to DEFAULT_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.lessThanOrEqual=" + DEFAULT_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset is less than or equal to SMALLER_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.lessThanOrEqual=" + SMALLER_COLUMN_OFFSET);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset is less than DEFAULT_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.lessThan=" + DEFAULT_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset is less than UPDATED_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.lessThan=" + UPDATED_COLUMN_OFFSET);
    }

    @Test
    @Transactional
    public void getAllADFieldsByColumnOffsetIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where columnOffset is greater than DEFAULT_COLUMN_OFFSET
        defaultADFieldShouldNotBeFound("columnOffset.greaterThan=" + DEFAULT_COLUMN_OFFSET);

        // Get all the aDFieldList where columnOffset is greater than SMALLER_COLUMN_OFFSET
        defaultADFieldShouldBeFound("columnOffset.greaterThan=" + SMALLER_COLUMN_OFFSET);
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
    public void getAllADFieldsByRowNoIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo equals to DEFAULT_ROW_NO
        defaultADFieldShouldBeFound("rowNo.equals=" + DEFAULT_ROW_NO);

        // Get all the aDFieldList where rowNo equals to UPDATED_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.equals=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo not equals to DEFAULT_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.notEquals=" + DEFAULT_ROW_NO);

        // Get all the aDFieldList where rowNo not equals to UPDATED_ROW_NO
        defaultADFieldShouldBeFound("rowNo.notEquals=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo in DEFAULT_ROW_NO or UPDATED_ROW_NO
        defaultADFieldShouldBeFound("rowNo.in=" + DEFAULT_ROW_NO + "," + UPDATED_ROW_NO);

        // Get all the aDFieldList where rowNo equals to UPDATED_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.in=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo is not null
        defaultADFieldShouldBeFound("rowNo.specified=true");

        // Get all the aDFieldList where rowNo is null
        defaultADFieldShouldNotBeFound("rowNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo is greater than or equal to DEFAULT_ROW_NO
        defaultADFieldShouldBeFound("rowNo.greaterThanOrEqual=" + DEFAULT_ROW_NO);

        // Get all the aDFieldList where rowNo is greater than or equal to UPDATED_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.greaterThanOrEqual=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo is less than or equal to DEFAULT_ROW_NO
        defaultADFieldShouldBeFound("rowNo.lessThanOrEqual=" + DEFAULT_ROW_NO);

        // Get all the aDFieldList where rowNo is less than or equal to SMALLER_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.lessThanOrEqual=" + SMALLER_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo is less than DEFAULT_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.lessThan=" + DEFAULT_ROW_NO);

        // Get all the aDFieldList where rowNo is less than UPDATED_ROW_NO
        defaultADFieldShouldBeFound("rowNo.lessThan=" + UPDATED_ROW_NO);
    }

    @Test
    @Transactional
    public void getAllADFieldsByRowNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where rowNo is greater than DEFAULT_ROW_NO
        defaultADFieldShouldNotBeFound("rowNo.greaterThan=" + DEFAULT_ROW_NO);

        // Get all the aDFieldList where rowNo is greater than SMALLER_ROW_NO
        defaultADFieldShouldBeFound("rowNo.greaterThan=" + SMALLER_ROW_NO);
    }


    @Test
    @Transactional
    public void getAllADFieldsByVirtualColumnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where virtualColumnName equals to DEFAULT_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldBeFound("virtualColumnName.equals=" + DEFAULT_VIRTUAL_COLUMN_NAME);

        // Get all the aDFieldList where virtualColumnName equals to UPDATED_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldNotBeFound("virtualColumnName.equals=" + UPDATED_VIRTUAL_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByVirtualColumnNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where virtualColumnName not equals to DEFAULT_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldNotBeFound("virtualColumnName.notEquals=" + DEFAULT_VIRTUAL_COLUMN_NAME);

        // Get all the aDFieldList where virtualColumnName not equals to UPDATED_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldBeFound("virtualColumnName.notEquals=" + UPDATED_VIRTUAL_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByVirtualColumnNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where virtualColumnName in DEFAULT_VIRTUAL_COLUMN_NAME or UPDATED_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldBeFound("virtualColumnName.in=" + DEFAULT_VIRTUAL_COLUMN_NAME + "," + UPDATED_VIRTUAL_COLUMN_NAME);

        // Get all the aDFieldList where virtualColumnName equals to UPDATED_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldNotBeFound("virtualColumnName.in=" + UPDATED_VIRTUAL_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByVirtualColumnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where virtualColumnName is not null
        defaultADFieldShouldBeFound("virtualColumnName.specified=true");

        // Get all the aDFieldList where virtualColumnName is null
        defaultADFieldShouldNotBeFound("virtualColumnName.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByVirtualColumnNameContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where virtualColumnName contains DEFAULT_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldBeFound("virtualColumnName.contains=" + DEFAULT_VIRTUAL_COLUMN_NAME);

        // Get all the aDFieldList where virtualColumnName contains UPDATED_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldNotBeFound("virtualColumnName.contains=" + UPDATED_VIRTUAL_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllADFieldsByVirtualColumnNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where virtualColumnName does not contain DEFAULT_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldNotBeFound("virtualColumnName.doesNotContain=" + DEFAULT_VIRTUAL_COLUMN_NAME);

        // Get all the aDFieldList where virtualColumnName does not contain UPDATED_VIRTUAL_COLUMN_NAME
        defaultADFieldShouldBeFound("virtualColumnName.doesNotContain=" + UPDATED_VIRTUAL_COLUMN_NAME);
    }


    @Test
    @Transactional
    public void getAllADFieldsByMandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatory equals to DEFAULT_MANDATORY
        defaultADFieldShouldBeFound("mandatory.equals=" + DEFAULT_MANDATORY);

        // Get all the aDFieldList where mandatory equals to UPDATED_MANDATORY
        defaultADFieldShouldNotBeFound("mandatory.equals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatory not equals to DEFAULT_MANDATORY
        defaultADFieldShouldNotBeFound("mandatory.notEquals=" + DEFAULT_MANDATORY);

        // Get all the aDFieldList where mandatory not equals to UPDATED_MANDATORY
        defaultADFieldShouldBeFound("mandatory.notEquals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatory in DEFAULT_MANDATORY or UPDATED_MANDATORY
        defaultADFieldShouldBeFound("mandatory.in=" + DEFAULT_MANDATORY + "," + UPDATED_MANDATORY);

        // Get all the aDFieldList where mandatory equals to UPDATED_MANDATORY
        defaultADFieldShouldNotBeFound("mandatory.in=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatory is not null
        defaultADFieldShouldBeFound("mandatory.specified=true");

        // Get all the aDFieldList where mandatory is null
        defaultADFieldShouldNotBeFound("mandatory.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatoryLogic equals to DEFAULT_MANDATORY_LOGIC
        defaultADFieldShouldBeFound("mandatoryLogic.equals=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDFieldList where mandatoryLogic equals to UPDATED_MANDATORY_LOGIC
        defaultADFieldShouldNotBeFound("mandatoryLogic.equals=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatoryLogic not equals to DEFAULT_MANDATORY_LOGIC
        defaultADFieldShouldNotBeFound("mandatoryLogic.notEquals=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDFieldList where mandatoryLogic not equals to UPDATED_MANDATORY_LOGIC
        defaultADFieldShouldBeFound("mandatoryLogic.notEquals=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatoryLogic in DEFAULT_MANDATORY_LOGIC or UPDATED_MANDATORY_LOGIC
        defaultADFieldShouldBeFound("mandatoryLogic.in=" + DEFAULT_MANDATORY_LOGIC + "," + UPDATED_MANDATORY_LOGIC);

        // Get all the aDFieldList where mandatoryLogic equals to UPDATED_MANDATORY_LOGIC
        defaultADFieldShouldNotBeFound("mandatoryLogic.in=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatoryLogic is not null
        defaultADFieldShouldBeFound("mandatoryLogic.specified=true");

        // Get all the aDFieldList where mandatoryLogic is null
        defaultADFieldShouldNotBeFound("mandatoryLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByMandatoryLogicContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatoryLogic contains DEFAULT_MANDATORY_LOGIC
        defaultADFieldShouldBeFound("mandatoryLogic.contains=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDFieldList where mandatoryLogic contains UPDATED_MANDATORY_LOGIC
        defaultADFieldShouldNotBeFound("mandatoryLogic.contains=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMandatoryLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where mandatoryLogic does not contain DEFAULT_MANDATORY_LOGIC
        defaultADFieldShouldNotBeFound("mandatoryLogic.doesNotContain=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDFieldList where mandatoryLogic does not contain UPDATED_MANDATORY_LOGIC
        defaultADFieldShouldBeFound("mandatoryLogic.doesNotContain=" + UPDATED_MANDATORY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADFieldsByUpdatableIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where updatable equals to DEFAULT_UPDATABLE
        defaultADFieldShouldBeFound("updatable.equals=" + DEFAULT_UPDATABLE);

        // Get all the aDFieldList where updatable equals to UPDATED_UPDATABLE
        defaultADFieldShouldNotBeFound("updatable.equals=" + UPDATED_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByUpdatableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where updatable not equals to DEFAULT_UPDATABLE
        defaultADFieldShouldNotBeFound("updatable.notEquals=" + DEFAULT_UPDATABLE);

        // Get all the aDFieldList where updatable not equals to UPDATED_UPDATABLE
        defaultADFieldShouldBeFound("updatable.notEquals=" + UPDATED_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByUpdatableIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where updatable in DEFAULT_UPDATABLE or UPDATED_UPDATABLE
        defaultADFieldShouldBeFound("updatable.in=" + DEFAULT_UPDATABLE + "," + UPDATED_UPDATABLE);

        // Get all the aDFieldList where updatable equals to UPDATED_UPDATABLE
        defaultADFieldShouldNotBeFound("updatable.in=" + UPDATED_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByUpdatableIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where updatable is not null
        defaultADFieldShouldBeFound("updatable.specified=true");

        // Get all the aDFieldList where updatable is null
        defaultADFieldShouldNotBeFound("updatable.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByAlwaysUpdatableIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where alwaysUpdatable equals to DEFAULT_ALWAYS_UPDATABLE
        defaultADFieldShouldBeFound("alwaysUpdatable.equals=" + DEFAULT_ALWAYS_UPDATABLE);

        // Get all the aDFieldList where alwaysUpdatable equals to UPDATED_ALWAYS_UPDATABLE
        defaultADFieldShouldNotBeFound("alwaysUpdatable.equals=" + UPDATED_ALWAYS_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByAlwaysUpdatableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where alwaysUpdatable not equals to DEFAULT_ALWAYS_UPDATABLE
        defaultADFieldShouldNotBeFound("alwaysUpdatable.notEquals=" + DEFAULT_ALWAYS_UPDATABLE);

        // Get all the aDFieldList where alwaysUpdatable not equals to UPDATED_ALWAYS_UPDATABLE
        defaultADFieldShouldBeFound("alwaysUpdatable.notEquals=" + UPDATED_ALWAYS_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByAlwaysUpdatableIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where alwaysUpdatable in DEFAULT_ALWAYS_UPDATABLE or UPDATED_ALWAYS_UPDATABLE
        defaultADFieldShouldBeFound("alwaysUpdatable.in=" + DEFAULT_ALWAYS_UPDATABLE + "," + UPDATED_ALWAYS_UPDATABLE);

        // Get all the aDFieldList where alwaysUpdatable equals to UPDATED_ALWAYS_UPDATABLE
        defaultADFieldShouldNotBeFound("alwaysUpdatable.in=" + UPDATED_ALWAYS_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByAlwaysUpdatableIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where alwaysUpdatable is not null
        defaultADFieldShouldBeFound("alwaysUpdatable.specified=true");

        // Get all the aDFieldList where alwaysUpdatable is null
        defaultADFieldShouldNotBeFound("alwaysUpdatable.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByCopyableIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where copyable equals to DEFAULT_COPYABLE
        defaultADFieldShouldBeFound("copyable.equals=" + DEFAULT_COPYABLE);

        // Get all the aDFieldList where copyable equals to UPDATED_COPYABLE
        defaultADFieldShouldNotBeFound("copyable.equals=" + UPDATED_COPYABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByCopyableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where copyable not equals to DEFAULT_COPYABLE
        defaultADFieldShouldNotBeFound("copyable.notEquals=" + DEFAULT_COPYABLE);

        // Get all the aDFieldList where copyable not equals to UPDATED_COPYABLE
        defaultADFieldShouldBeFound("copyable.notEquals=" + UPDATED_COPYABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByCopyableIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where copyable in DEFAULT_COPYABLE or UPDATED_COPYABLE
        defaultADFieldShouldBeFound("copyable.in=" + DEFAULT_COPYABLE + "," + UPDATED_COPYABLE);

        // Get all the aDFieldList where copyable equals to UPDATED_COPYABLE
        defaultADFieldShouldNotBeFound("copyable.in=" + UPDATED_COPYABLE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByCopyableIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where copyable is not null
        defaultADFieldShouldBeFound("copyable.specified=true");

        // Get all the aDFieldList where copyable is null
        defaultADFieldShouldNotBeFound("copyable.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByDefaultValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where defaultValue equals to DEFAULT_DEFAULT_VALUE
        defaultADFieldShouldBeFound("defaultValue.equals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDFieldList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultADFieldShouldNotBeFound("defaultValue.equals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDefaultValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where defaultValue not equals to DEFAULT_DEFAULT_VALUE
        defaultADFieldShouldNotBeFound("defaultValue.notEquals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDFieldList where defaultValue not equals to UPDATED_DEFAULT_VALUE
        defaultADFieldShouldBeFound("defaultValue.notEquals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDefaultValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where defaultValue in DEFAULT_DEFAULT_VALUE or UPDATED_DEFAULT_VALUE
        defaultADFieldShouldBeFound("defaultValue.in=" + DEFAULT_DEFAULT_VALUE + "," + UPDATED_DEFAULT_VALUE);

        // Get all the aDFieldList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultADFieldShouldNotBeFound("defaultValue.in=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDefaultValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where defaultValue is not null
        defaultADFieldShouldBeFound("defaultValue.specified=true");

        // Get all the aDFieldList where defaultValue is null
        defaultADFieldShouldNotBeFound("defaultValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByDefaultValueContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where defaultValue contains DEFAULT_DEFAULT_VALUE
        defaultADFieldShouldBeFound("defaultValue.contains=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDFieldList where defaultValue contains UPDATED_DEFAULT_VALUE
        defaultADFieldShouldNotBeFound("defaultValue.contains=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByDefaultValueNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where defaultValue does not contain DEFAULT_DEFAULT_VALUE
        defaultADFieldShouldNotBeFound("defaultValue.doesNotContain=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDFieldList where defaultValue does not contain UPDATED_DEFAULT_VALUE
        defaultADFieldShouldBeFound("defaultValue.doesNotContain=" + UPDATED_DEFAULT_VALUE);
    }


    @Test
    @Transactional
    public void getAllADFieldsByFormatPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where formatPattern equals to DEFAULT_FORMAT_PATTERN
        defaultADFieldShouldBeFound("formatPattern.equals=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDFieldList where formatPattern equals to UPDATED_FORMAT_PATTERN
        defaultADFieldShouldNotBeFound("formatPattern.equals=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByFormatPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where formatPattern not equals to DEFAULT_FORMAT_PATTERN
        defaultADFieldShouldNotBeFound("formatPattern.notEquals=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDFieldList where formatPattern not equals to UPDATED_FORMAT_PATTERN
        defaultADFieldShouldBeFound("formatPattern.notEquals=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByFormatPatternIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where formatPattern in DEFAULT_FORMAT_PATTERN or UPDATED_FORMAT_PATTERN
        defaultADFieldShouldBeFound("formatPattern.in=" + DEFAULT_FORMAT_PATTERN + "," + UPDATED_FORMAT_PATTERN);

        // Get all the aDFieldList where formatPattern equals to UPDATED_FORMAT_PATTERN
        defaultADFieldShouldNotBeFound("formatPattern.in=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByFormatPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where formatPattern is not null
        defaultADFieldShouldBeFound("formatPattern.specified=true");

        // Get all the aDFieldList where formatPattern is null
        defaultADFieldShouldNotBeFound("formatPattern.specified=false");
    }
                @Test
    @Transactional
    public void getAllADFieldsByFormatPatternContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where formatPattern contains DEFAULT_FORMAT_PATTERN
        defaultADFieldShouldBeFound("formatPattern.contains=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDFieldList where formatPattern contains UPDATED_FORMAT_PATTERN
        defaultADFieldShouldNotBeFound("formatPattern.contains=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADFieldsByFormatPatternNotContainsSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where formatPattern does not contain DEFAULT_FORMAT_PATTERN
        defaultADFieldShouldNotBeFound("formatPattern.doesNotContain=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDFieldList where formatPattern does not contain UPDATED_FORMAT_PATTERN
        defaultADFieldShouldBeFound("formatPattern.doesNotContain=" + UPDATED_FORMAT_PATTERN);
    }


    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength equals to DEFAULT_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.equals=" + DEFAULT_MIN_LENGTH);

        // Get all the aDFieldList where minLength equals to UPDATED_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.equals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength not equals to DEFAULT_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.notEquals=" + DEFAULT_MIN_LENGTH);

        // Get all the aDFieldList where minLength not equals to UPDATED_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.notEquals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength in DEFAULT_MIN_LENGTH or UPDATED_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.in=" + DEFAULT_MIN_LENGTH + "," + UPDATED_MIN_LENGTH);

        // Get all the aDFieldList where minLength equals to UPDATED_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.in=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength is not null
        defaultADFieldShouldBeFound("minLength.specified=true");

        // Get all the aDFieldList where minLength is null
        defaultADFieldShouldNotBeFound("minLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength is greater than or equal to DEFAULT_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.greaterThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the aDFieldList where minLength is greater than or equal to UPDATED_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.greaterThanOrEqual=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength is less than or equal to DEFAULT_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.lessThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the aDFieldList where minLength is less than or equal to SMALLER_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.lessThanOrEqual=" + SMALLER_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength is less than DEFAULT_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.lessThan=" + DEFAULT_MIN_LENGTH);

        // Get all the aDFieldList where minLength is less than UPDATED_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.lessThan=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minLength is greater than DEFAULT_MIN_LENGTH
        defaultADFieldShouldNotBeFound("minLength.greaterThan=" + DEFAULT_MIN_LENGTH);

        // Get all the aDFieldList where minLength is greater than SMALLER_MIN_LENGTH
        defaultADFieldShouldBeFound("minLength.greaterThan=" + SMALLER_MIN_LENGTH);
    }


    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength equals to DEFAULT_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.equals=" + DEFAULT_MAX_LENGTH);

        // Get all the aDFieldList where maxLength equals to UPDATED_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.equals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength not equals to DEFAULT_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.notEquals=" + DEFAULT_MAX_LENGTH);

        // Get all the aDFieldList where maxLength not equals to UPDATED_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.notEquals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength in DEFAULT_MAX_LENGTH or UPDATED_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.in=" + DEFAULT_MAX_LENGTH + "," + UPDATED_MAX_LENGTH);

        // Get all the aDFieldList where maxLength equals to UPDATED_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.in=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength is not null
        defaultADFieldShouldBeFound("maxLength.specified=true");

        // Get all the aDFieldList where maxLength is null
        defaultADFieldShouldNotBeFound("maxLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength is greater than or equal to DEFAULT_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.greaterThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the aDFieldList where maxLength is greater than or equal to UPDATED_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.greaterThanOrEqual=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength is less than or equal to DEFAULT_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.lessThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the aDFieldList where maxLength is less than or equal to SMALLER_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.lessThanOrEqual=" + SMALLER_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength is less than DEFAULT_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.lessThan=" + DEFAULT_MAX_LENGTH);

        // Get all the aDFieldList where maxLength is less than UPDATED_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.lessThan=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxLength is greater than DEFAULT_MAX_LENGTH
        defaultADFieldShouldNotBeFound("maxLength.greaterThan=" + DEFAULT_MAX_LENGTH);

        // Get all the aDFieldList where maxLength is greater than SMALLER_MAX_LENGTH
        defaultADFieldShouldBeFound("maxLength.greaterThan=" + SMALLER_MAX_LENGTH);
    }


    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue equals to DEFAULT_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.equals=" + DEFAULT_MIN_VALUE);

        // Get all the aDFieldList where minValue equals to UPDATED_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.equals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue not equals to DEFAULT_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.notEquals=" + DEFAULT_MIN_VALUE);

        // Get all the aDFieldList where minValue not equals to UPDATED_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.notEquals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue in DEFAULT_MIN_VALUE or UPDATED_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.in=" + DEFAULT_MIN_VALUE + "," + UPDATED_MIN_VALUE);

        // Get all the aDFieldList where minValue equals to UPDATED_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.in=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue is not null
        defaultADFieldShouldBeFound("minValue.specified=true");

        // Get all the aDFieldList where minValue is null
        defaultADFieldShouldNotBeFound("minValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue is greater than or equal to DEFAULT_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.greaterThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the aDFieldList where minValue is greater than or equal to UPDATED_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.greaterThanOrEqual=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue is less than or equal to DEFAULT_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.lessThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the aDFieldList where minValue is less than or equal to SMALLER_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.lessThanOrEqual=" + SMALLER_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue is less than DEFAULT_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.lessThan=" + DEFAULT_MIN_VALUE);

        // Get all the aDFieldList where minValue is less than UPDATED_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.lessThan=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMinValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where minValue is greater than DEFAULT_MIN_VALUE
        defaultADFieldShouldNotBeFound("minValue.greaterThan=" + DEFAULT_MIN_VALUE);

        // Get all the aDFieldList where minValue is greater than SMALLER_MIN_VALUE
        defaultADFieldShouldBeFound("minValue.greaterThan=" + SMALLER_MIN_VALUE);
    }


    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue equals to DEFAULT_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.equals=" + DEFAULT_MAX_VALUE);

        // Get all the aDFieldList where maxValue equals to UPDATED_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.equals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue not equals to DEFAULT_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.notEquals=" + DEFAULT_MAX_VALUE);

        // Get all the aDFieldList where maxValue not equals to UPDATED_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.notEquals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue in DEFAULT_MAX_VALUE or UPDATED_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.in=" + DEFAULT_MAX_VALUE + "," + UPDATED_MAX_VALUE);

        // Get all the aDFieldList where maxValue equals to UPDATED_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.in=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue is not null
        defaultADFieldShouldBeFound("maxValue.specified=true");

        // Get all the aDFieldList where maxValue is null
        defaultADFieldShouldNotBeFound("maxValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue is greater than or equal to DEFAULT_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.greaterThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the aDFieldList where maxValue is greater than or equal to UPDATED_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.greaterThanOrEqual=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue is less than or equal to DEFAULT_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.lessThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the aDFieldList where maxValue is less than or equal to SMALLER_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.lessThanOrEqual=" + SMALLER_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsLessThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue is less than DEFAULT_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.lessThan=" + DEFAULT_MAX_VALUE);

        // Get all the aDFieldList where maxValue is less than UPDATED_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.lessThan=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByMaxValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where maxValue is greater than DEFAULT_MAX_VALUE
        defaultADFieldShouldNotBeFound("maxValue.greaterThan=" + DEFAULT_MAX_VALUE);

        // Get all the aDFieldList where maxValue is greater than SMALLER_MAX_VALUE
        defaultADFieldShouldBeFound("maxValue.greaterThan=" + SMALLER_MAX_VALUE);
    }


    @Test
    @Transactional
    public void getAllADFieldsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where type equals to DEFAULT_TYPE
        defaultADFieldShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the aDFieldList where type equals to UPDATED_TYPE
        defaultADFieldShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where type not equals to DEFAULT_TYPE
        defaultADFieldShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the aDFieldList where type not equals to UPDATED_TYPE
        defaultADFieldShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultADFieldShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the aDFieldList where type equals to UPDATED_TYPE
        defaultADFieldShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADFieldsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);

        // Get all the aDFieldList where type is not null
        defaultADFieldShouldBeFound("type.specified=true");

        // Get all the aDFieldList where type is null
        defaultADFieldShouldNotBeFound("type.specified=false");
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
    public void getAllADFieldsByAdCalloutIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);
        AdCallout adCallout = AdCalloutResourceIT.createEntity(em);
        em.persist(adCallout);
        em.flush();
        aDField.addAdCallout(adCallout);
        aDFieldRepository.saveAndFlush(aDField);
        Long adCalloutId = adCallout.getId();

        // Get all the aDFieldList where adCallout equals to adCalloutId
        defaultADFieldShouldBeFound("adCalloutId.equals=" + adCalloutId);

        // Get all the aDFieldList where adCallout equals to adCalloutId + 1
        defaultADFieldShouldNotBeFound("adCalloutId.equals=" + (adCalloutId + 1));
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


    @Test
    @Transactional
    public void getAllADFieldsByAdReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);
        ADReference adReference = ADReferenceResourceIT.createEntity(em);
        em.persist(adReference);
        em.flush();
        aDField.setAdReference(adReference);
        aDFieldRepository.saveAndFlush(aDField);
        Long adReferenceId = adReference.getId();

        // Get all the aDFieldList where adReference equals to adReferenceId
        defaultADFieldShouldBeFound("adReferenceId.equals=" + adReferenceId);

        // Get all the aDFieldList where adReference equals to adReferenceId + 1
        defaultADFieldShouldNotBeFound("adReferenceId.equals=" + (adReferenceId + 1));
    }


    @Test
    @Transactional
    public void getAllADFieldsByAdColumnIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);
        ADColumn adColumn = ADColumnResourceIT.createEntity(em);
        em.persist(adColumn);
        em.flush();
        aDField.setAdColumn(adColumn);
        aDFieldRepository.saveAndFlush(aDField);
        Long adColumnId = adColumn.getId();

        // Get all the aDFieldList where adColumn equals to adColumnId
        defaultADFieldShouldBeFound("adColumnId.equals=" + adColumnId);

        // Get all the aDFieldList where adColumn equals to adColumnId + 1
        defaultADFieldShouldNotBeFound("adColumnId.equals=" + (adColumnId + 1));
    }


    @Test
    @Transactional
    public void getAllADFieldsByAdValidationRuleIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);
        AdValidationRule adValidationRule = AdValidationRuleResourceIT.createEntity(em);
        em.persist(adValidationRule);
        em.flush();
        aDField.setAdValidationRule(adValidationRule);
        aDFieldRepository.saveAndFlush(aDField);
        Long adValidationRuleId = adValidationRule.getId();

        // Get all the aDFieldList where adValidationRule equals to adValidationRuleId
        defaultADFieldShouldBeFound("adValidationRuleId.equals=" + adValidationRuleId);

        // Get all the aDFieldList where adValidationRule equals to adValidationRuleId + 1
        defaultADFieldShouldNotBeFound("adValidationRuleId.equals=" + (adValidationRuleId + 1));
    }


    @Test
    @Transactional
    public void getAllADFieldsByAdButtonIsEqualToSomething() throws Exception {
        // Initialize the database
        aDFieldRepository.saveAndFlush(aDField);
        AdButton adButton = AdButtonResourceIT.createEntity(em);
        em.persist(adButton);
        em.flush();
        aDField.setAdButton(adButton);
        aDFieldRepository.saveAndFlush(aDField);
        Long adButtonId = adButton.getId();

        // Get all the aDFieldList where adButton equals to adButtonId
        defaultADFieldShouldBeFound("adButtonId.equals=" + adButtonId);

        // Get all the aDFieldList where adButton equals to adButtonId + 1
        defaultADFieldShouldNotBeFound("adButtonId.equals=" + (adButtonId + 1));
    }


    @Test
    @Transactional
    public void getAllADFieldsByAdTabIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADTab adTab = aDField.getAdTab();
        aDFieldRepository.saveAndFlush(aDField);
        Long adTabId = adTab.getId();

        // Get all the aDFieldList where adTab equals to adTabId
        defaultADFieldShouldBeFound("adTabId.equals=" + adTabId);

        // Get all the aDFieldList where adTab equals to adTabId + 1
        defaultADFieldShouldNotBeFound("adTabId.equals=" + (adTabId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADFieldShouldBeFound(String filter) throws Exception {
        restADFieldMockMvc.perform(get("/api/ad-fields?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDField.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
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
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].writable").value(hasItem(DEFAULT_WRITABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].columnNo").value(hasItem(DEFAULT_COLUMN_NO)))
            .andExpect(jsonPath("$.[*].columnOffset").value(hasItem(DEFAULT_COLUMN_OFFSET)))
            .andExpect(jsonPath("$.[*].columnSpan").value(hasItem(DEFAULT_COLUMN_SPAN)))
            .andExpect(jsonPath("$.[*].rowNo").value(hasItem(DEFAULT_ROW_NO)))
            .andExpect(jsonPath("$.[*].virtualColumnName").value(hasItem(DEFAULT_VIRTUAL_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryLogic").value(hasItem(DEFAULT_MANDATORY_LOGIC)))
            .andExpect(jsonPath("$.[*].updatable").value(hasItem(DEFAULT_UPDATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].alwaysUpdatable").value(hasItem(DEFAULT_ALWAYS_UPDATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].copyable").value(hasItem(DEFAULT_COPYABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].formatPattern").value(hasItem(DEFAULT_FORMAT_PATTERN)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
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
            .uid(UPDATED_UID)
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
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .writable(UPDATED_WRITABLE)
            .columnNo(UPDATED_COLUMN_NO)
            .columnOffset(UPDATED_COLUMN_OFFSET)
            .columnSpan(UPDATED_COLUMN_SPAN)
            .rowNo(UPDATED_ROW_NO)
            .virtualColumnName(UPDATED_VIRTUAL_COLUMN_NAME)
            .mandatory(UPDATED_MANDATORY)
            .mandatoryLogic(UPDATED_MANDATORY_LOGIC)
            .updatable(UPDATED_UPDATABLE)
            .alwaysUpdatable(UPDATED_ALWAYS_UPDATABLE)
            .copyable(UPDATED_COPYABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .formatPattern(UPDATED_FORMAT_PATTERN)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .type(UPDATED_TYPE)
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
        assertThat(testADField.getUid()).isEqualTo(UPDATED_UID);
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
        assertThat(testADField.getReadOnlyLogic()).isEqualTo(UPDATED_READ_ONLY_LOGIC);
        assertThat(testADField.isWritable()).isEqualTo(UPDATED_WRITABLE);
        assertThat(testADField.getColumnNo()).isEqualTo(UPDATED_COLUMN_NO);
        assertThat(testADField.getColumnOffset()).isEqualTo(UPDATED_COLUMN_OFFSET);
        assertThat(testADField.getColumnSpan()).isEqualTo(UPDATED_COLUMN_SPAN);
        assertThat(testADField.getRowNo()).isEqualTo(UPDATED_ROW_NO);
        assertThat(testADField.getVirtualColumnName()).isEqualTo(UPDATED_VIRTUAL_COLUMN_NAME);
        assertThat(testADField.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testADField.getMandatoryLogic()).isEqualTo(UPDATED_MANDATORY_LOGIC);
        assertThat(testADField.isUpdatable()).isEqualTo(UPDATED_UPDATABLE);
        assertThat(testADField.isAlwaysUpdatable()).isEqualTo(UPDATED_ALWAYS_UPDATABLE);
        assertThat(testADField.isCopyable()).isEqualTo(UPDATED_COPYABLE);
        assertThat(testADField.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testADField.getFormatPattern()).isEqualTo(UPDATED_FORMAT_PATTERN);
        assertThat(testADField.getMinLength()).isEqualTo(UPDATED_MIN_LENGTH);
        assertThat(testADField.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
        assertThat(testADField.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testADField.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testADField.getType()).isEqualTo(UPDATED_TYPE);
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
