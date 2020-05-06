package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADClient;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.ADColumnRepository;
import com.bhp.opusb.service.ADColumnService;
import com.bhp.opusb.service.dto.ADColumnDTO;
import com.bhp.opusb.service.mapper.ADColumnMapper;
import com.bhp.opusb.service.dto.ADColumnCriteria;
import com.bhp.opusb.service.ADColumnQueryService;

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

import com.bhp.opusb.domain.enumeration.ADColumnType;
/**
 * Integration tests for the {@link ADColumnResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ADColumnResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SQL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SQL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_FIELD_LENGTH = 1L;
    private static final Long UPDATED_FIELD_LENGTH = 2L;
    private static final Long SMALLER_FIELD_LENGTH = 1L - 1L;

    private static final Boolean DEFAULT_KEY = false;
    private static final Boolean UPDATED_KEY = true;

    private static final ADColumnType DEFAULT_TYPE = ADColumnType.STRING;
    private static final ADColumnType UPDATED_TYPE = ADColumnType.INTEGER;

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final String DEFAULT_MANDATORY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_MANDATORY_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_READ_ONLY_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_READ_ONLY_LOGIC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UPDATABLE = false;
    private static final Boolean UPDATED_UPDATABLE = true;

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

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ADColumnRepository aDColumnRepository;

    @Autowired
    private ADColumnMapper aDColumnMapper;

    @Autowired
    private ADColumnService aDColumnService;

    @Autowired
    private ADColumnQueryService aDColumnQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restADColumnMockMvc;

    private ADColumn aDColumn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADColumn createEntity(EntityManager em) {
        ADColumn aDColumn = new ADColumn()
            .name(DEFAULT_NAME)
            .sqlName(DEFAULT_SQL_NAME)
            .description(DEFAULT_DESCRIPTION)
            .fieldLength(DEFAULT_FIELD_LENGTH)
            .key(DEFAULT_KEY)
            .type(DEFAULT_TYPE)
            .mandatory(DEFAULT_MANDATORY)
            .mandatoryLogic(DEFAULT_MANDATORY_LOGIC)
            .readOnlyLogic(DEFAULT_READ_ONLY_LOGIC)
            .updatable(DEFAULT_UPDATABLE)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .formatPattern(DEFAULT_FORMAT_PATTERN)
            .minLength(DEFAULT_MIN_LENGTH)
            .maxLength(DEFAULT_MAX_LENGTH)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
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
        aDColumn.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDColumn.setAdOrganization(aDOrganization);
        // Add required entity
        ADTable aDTable;
        if (TestUtil.findAll(em, ADTable.class).isEmpty()) {
            aDTable = ADTableResourceIT.createEntity(em);
            em.persist(aDTable);
            em.flush();
        } else {
            aDTable = TestUtil.findAll(em, ADTable.class).get(0);
        }
        aDColumn.setAdTable(aDTable);
        return aDColumn;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ADColumn createUpdatedEntity(EntityManager em) {
        ADColumn aDColumn = new ADColumn()
            .name(UPDATED_NAME)
            .sqlName(UPDATED_SQL_NAME)
            .description(UPDATED_DESCRIPTION)
            .fieldLength(UPDATED_FIELD_LENGTH)
            .key(UPDATED_KEY)
            .type(UPDATED_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .mandatoryLogic(UPDATED_MANDATORY_LOGIC)
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .updatable(UPDATED_UPDATABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .formatPattern(UPDATED_FORMAT_PATTERN)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
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
        aDColumn.setAdClient(aDClient);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        aDColumn.setAdOrganization(aDOrganization);
        // Add required entity
        ADTable aDTable;
        if (TestUtil.findAll(em, ADTable.class).isEmpty()) {
            aDTable = ADTableResourceIT.createUpdatedEntity(em);
            em.persist(aDTable);
            em.flush();
        } else {
            aDTable = TestUtil.findAll(em, ADTable.class).get(0);
        }
        aDColumn.setAdTable(aDTable);
        return aDColumn;
    }

    @BeforeEach
    public void initTest() {
        aDColumn = createEntity(em);
    }

    @Test
    @Transactional
    public void createADColumn() throws Exception {
        int databaseSizeBeforeCreate = aDColumnRepository.findAll().size();

        // Create the ADColumn
        ADColumnDTO aDColumnDTO = aDColumnMapper.toDto(aDColumn);
        restADColumnMockMvc.perform(post("/api/ad-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDColumnDTO)))
            .andExpect(status().isCreated());

        // Validate the ADColumn in the database
        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeCreate + 1);
        ADColumn testADColumn = aDColumnList.get(aDColumnList.size() - 1);
        assertThat(testADColumn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testADColumn.getSqlName()).isEqualTo(DEFAULT_SQL_NAME);
        assertThat(testADColumn.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testADColumn.getFieldLength()).isEqualTo(DEFAULT_FIELD_LENGTH);
        assertThat(testADColumn.isKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testADColumn.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testADColumn.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testADColumn.getMandatoryLogic()).isEqualTo(DEFAULT_MANDATORY_LOGIC);
        assertThat(testADColumn.getReadOnlyLogic()).isEqualTo(DEFAULT_READ_ONLY_LOGIC);
        assertThat(testADColumn.isUpdatable()).isEqualTo(DEFAULT_UPDATABLE);
        assertThat(testADColumn.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testADColumn.getFormatPattern()).isEqualTo(DEFAULT_FORMAT_PATTERN);
        assertThat(testADColumn.getMinLength()).isEqualTo(DEFAULT_MIN_LENGTH);
        assertThat(testADColumn.getMaxLength()).isEqualTo(DEFAULT_MAX_LENGTH);
        assertThat(testADColumn.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testADColumn.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testADColumn.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createADColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aDColumnRepository.findAll().size();

        // Create the ADColumn with an existing ID
        aDColumn.setId(1L);
        ADColumnDTO aDColumnDTO = aDColumnMapper.toDto(aDColumn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restADColumnMockMvc.perform(post("/api/ad-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADColumn in the database
        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDColumnRepository.findAll().size();
        // set the field null
        aDColumn.setName(null);

        // Create the ADColumn, which fails.
        ADColumnDTO aDColumnDTO = aDColumnMapper.toDto(aDColumn);

        restADColumnMockMvc.perform(post("/api/ad-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDColumnDTO)))
            .andExpect(status().isBadRequest());

        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSqlNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = aDColumnRepository.findAll().size();
        // set the field null
        aDColumn.setSqlName(null);

        // Create the ADColumn, which fails.
        ADColumnDTO aDColumnDTO = aDColumnMapper.toDto(aDColumn);

        restADColumnMockMvc.perform(post("/api/ad-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDColumnDTO)))
            .andExpect(status().isBadRequest());

        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllADColumns() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList
        restADColumnMockMvc.perform(get("/api/ad-columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sqlName").value(hasItem(DEFAULT_SQL_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fieldLength").value(hasItem(DEFAULT_FIELD_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryLogic").value(hasItem(DEFAULT_MANDATORY_LOGIC)))
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].updatable").value(hasItem(DEFAULT_UPDATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].formatPattern").value(hasItem(DEFAULT_FORMAT_PATTERN)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getADColumn() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get the aDColumn
        restADColumnMockMvc.perform(get("/api/ad-columns/{id}", aDColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aDColumn.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sqlName").value(DEFAULT_SQL_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fieldLength").value(DEFAULT_FIELD_LENGTH.intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.mandatoryLogic").value(DEFAULT_MANDATORY_LOGIC))
            .andExpect(jsonPath("$.readOnlyLogic").value(DEFAULT_READ_ONLY_LOGIC))
            .andExpect(jsonPath("$.updatable").value(DEFAULT_UPDATABLE.booleanValue()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.formatPattern").value(DEFAULT_FORMAT_PATTERN))
            .andExpect(jsonPath("$.minLength").value(DEFAULT_MIN_LENGTH))
            .andExpect(jsonPath("$.maxLength").value(DEFAULT_MAX_LENGTH))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE.intValue()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getADColumnsByIdFiltering() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        Long id = aDColumn.getId();

        defaultADColumnShouldBeFound("id.equals=" + id);
        defaultADColumnShouldNotBeFound("id.notEquals=" + id);

        defaultADColumnShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultADColumnShouldNotBeFound("id.greaterThan=" + id);

        defaultADColumnShouldBeFound("id.lessThanOrEqual=" + id);
        defaultADColumnShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllADColumnsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where name equals to DEFAULT_NAME
        defaultADColumnShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the aDColumnList where name equals to UPDATED_NAME
        defaultADColumnShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where name not equals to DEFAULT_NAME
        defaultADColumnShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the aDColumnList where name not equals to UPDATED_NAME
        defaultADColumnShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where name in DEFAULT_NAME or UPDATED_NAME
        defaultADColumnShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the aDColumnList where name equals to UPDATED_NAME
        defaultADColumnShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where name is not null
        defaultADColumnShouldBeFound("name.specified=true");

        // Get all the aDColumnList where name is null
        defaultADColumnShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsByNameContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where name contains DEFAULT_NAME
        defaultADColumnShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the aDColumnList where name contains UPDATED_NAME
        defaultADColumnShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where name does not contain DEFAULT_NAME
        defaultADColumnShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the aDColumnList where name does not contain UPDATED_NAME
        defaultADColumnShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllADColumnsBySqlNameIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where sqlName equals to DEFAULT_SQL_NAME
        defaultADColumnShouldBeFound("sqlName.equals=" + DEFAULT_SQL_NAME);

        // Get all the aDColumnList where sqlName equals to UPDATED_SQL_NAME
        defaultADColumnShouldNotBeFound("sqlName.equals=" + UPDATED_SQL_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsBySqlNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where sqlName not equals to DEFAULT_SQL_NAME
        defaultADColumnShouldNotBeFound("sqlName.notEquals=" + DEFAULT_SQL_NAME);

        // Get all the aDColumnList where sqlName not equals to UPDATED_SQL_NAME
        defaultADColumnShouldBeFound("sqlName.notEquals=" + UPDATED_SQL_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsBySqlNameIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where sqlName in DEFAULT_SQL_NAME or UPDATED_SQL_NAME
        defaultADColumnShouldBeFound("sqlName.in=" + DEFAULT_SQL_NAME + "," + UPDATED_SQL_NAME);

        // Get all the aDColumnList where sqlName equals to UPDATED_SQL_NAME
        defaultADColumnShouldNotBeFound("sqlName.in=" + UPDATED_SQL_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsBySqlNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where sqlName is not null
        defaultADColumnShouldBeFound("sqlName.specified=true");

        // Get all the aDColumnList where sqlName is null
        defaultADColumnShouldNotBeFound("sqlName.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsBySqlNameContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where sqlName contains DEFAULT_SQL_NAME
        defaultADColumnShouldBeFound("sqlName.contains=" + DEFAULT_SQL_NAME);

        // Get all the aDColumnList where sqlName contains UPDATED_SQL_NAME
        defaultADColumnShouldNotBeFound("sqlName.contains=" + UPDATED_SQL_NAME);
    }

    @Test
    @Transactional
    public void getAllADColumnsBySqlNameNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where sqlName does not contain DEFAULT_SQL_NAME
        defaultADColumnShouldNotBeFound("sqlName.doesNotContain=" + DEFAULT_SQL_NAME);

        // Get all the aDColumnList where sqlName does not contain UPDATED_SQL_NAME
        defaultADColumnShouldBeFound("sqlName.doesNotContain=" + UPDATED_SQL_NAME);
    }


    @Test
    @Transactional
    public void getAllADColumnsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where description equals to DEFAULT_DESCRIPTION
        defaultADColumnShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the aDColumnList where description equals to UPDATED_DESCRIPTION
        defaultADColumnShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where description not equals to DEFAULT_DESCRIPTION
        defaultADColumnShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the aDColumnList where description not equals to UPDATED_DESCRIPTION
        defaultADColumnShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultADColumnShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the aDColumnList where description equals to UPDATED_DESCRIPTION
        defaultADColumnShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where description is not null
        defaultADColumnShouldBeFound("description.specified=true");

        // Get all the aDColumnList where description is null
        defaultADColumnShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where description contains DEFAULT_DESCRIPTION
        defaultADColumnShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the aDColumnList where description contains UPDATED_DESCRIPTION
        defaultADColumnShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where description does not contain DEFAULT_DESCRIPTION
        defaultADColumnShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the aDColumnList where description does not contain UPDATED_DESCRIPTION
        defaultADColumnShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength equals to DEFAULT_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.equals=" + DEFAULT_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength equals to UPDATED_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.equals=" + UPDATED_FIELD_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength not equals to DEFAULT_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.notEquals=" + DEFAULT_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength not equals to UPDATED_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.notEquals=" + UPDATED_FIELD_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength in DEFAULT_FIELD_LENGTH or UPDATED_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.in=" + DEFAULT_FIELD_LENGTH + "," + UPDATED_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength equals to UPDATED_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.in=" + UPDATED_FIELD_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength is not null
        defaultADColumnShouldBeFound("fieldLength.specified=true");

        // Get all the aDColumnList where fieldLength is null
        defaultADColumnShouldNotBeFound("fieldLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength is greater than or equal to DEFAULT_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.greaterThanOrEqual=" + DEFAULT_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength is greater than or equal to UPDATED_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.greaterThanOrEqual=" + UPDATED_FIELD_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength is less than or equal to DEFAULT_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.lessThanOrEqual=" + DEFAULT_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength is less than or equal to SMALLER_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.lessThanOrEqual=" + SMALLER_FIELD_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength is less than DEFAULT_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.lessThan=" + DEFAULT_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength is less than UPDATED_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.lessThan=" + UPDATED_FIELD_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFieldLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where fieldLength is greater than DEFAULT_FIELD_LENGTH
        defaultADColumnShouldNotBeFound("fieldLength.greaterThan=" + DEFAULT_FIELD_LENGTH);

        // Get all the aDColumnList where fieldLength is greater than SMALLER_FIELD_LENGTH
        defaultADColumnShouldBeFound("fieldLength.greaterThan=" + SMALLER_FIELD_LENGTH);
    }


    @Test
    @Transactional
    public void getAllADColumnsByKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where key equals to DEFAULT_KEY
        defaultADColumnShouldBeFound("key.equals=" + DEFAULT_KEY);

        // Get all the aDColumnList where key equals to UPDATED_KEY
        defaultADColumnShouldNotBeFound("key.equals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllADColumnsByKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where key not equals to DEFAULT_KEY
        defaultADColumnShouldNotBeFound("key.notEquals=" + DEFAULT_KEY);

        // Get all the aDColumnList where key not equals to UPDATED_KEY
        defaultADColumnShouldBeFound("key.notEquals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllADColumnsByKeyIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where key in DEFAULT_KEY or UPDATED_KEY
        defaultADColumnShouldBeFound("key.in=" + DEFAULT_KEY + "," + UPDATED_KEY);

        // Get all the aDColumnList where key equals to UPDATED_KEY
        defaultADColumnShouldNotBeFound("key.in=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllADColumnsByKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where key is not null
        defaultADColumnShouldBeFound("key.specified=true");

        // Get all the aDColumnList where key is null
        defaultADColumnShouldNotBeFound("key.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where type equals to DEFAULT_TYPE
        defaultADColumnShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the aDColumnList where type equals to UPDATED_TYPE
        defaultADColumnShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where type not equals to DEFAULT_TYPE
        defaultADColumnShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the aDColumnList where type not equals to UPDATED_TYPE
        defaultADColumnShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultADColumnShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the aDColumnList where type equals to UPDATED_TYPE
        defaultADColumnShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where type is not null
        defaultADColumnShouldBeFound("type.specified=true");

        // Get all the aDColumnList where type is null
        defaultADColumnShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatory equals to DEFAULT_MANDATORY
        defaultADColumnShouldBeFound("mandatory.equals=" + DEFAULT_MANDATORY);

        // Get all the aDColumnList where mandatory equals to UPDATED_MANDATORY
        defaultADColumnShouldNotBeFound("mandatory.equals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatory not equals to DEFAULT_MANDATORY
        defaultADColumnShouldNotBeFound("mandatory.notEquals=" + DEFAULT_MANDATORY);

        // Get all the aDColumnList where mandatory not equals to UPDATED_MANDATORY
        defaultADColumnShouldBeFound("mandatory.notEquals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatory in DEFAULT_MANDATORY or UPDATED_MANDATORY
        defaultADColumnShouldBeFound("mandatory.in=" + DEFAULT_MANDATORY + "," + UPDATED_MANDATORY);

        // Get all the aDColumnList where mandatory equals to UPDATED_MANDATORY
        defaultADColumnShouldNotBeFound("mandatory.in=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatory is not null
        defaultADColumnShouldBeFound("mandatory.specified=true");

        // Get all the aDColumnList where mandatory is null
        defaultADColumnShouldNotBeFound("mandatory.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatoryLogic equals to DEFAULT_MANDATORY_LOGIC
        defaultADColumnShouldBeFound("mandatoryLogic.equals=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDColumnList where mandatoryLogic equals to UPDATED_MANDATORY_LOGIC
        defaultADColumnShouldNotBeFound("mandatoryLogic.equals=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatoryLogic not equals to DEFAULT_MANDATORY_LOGIC
        defaultADColumnShouldNotBeFound("mandatoryLogic.notEquals=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDColumnList where mandatoryLogic not equals to UPDATED_MANDATORY_LOGIC
        defaultADColumnShouldBeFound("mandatoryLogic.notEquals=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatoryLogic in DEFAULT_MANDATORY_LOGIC or UPDATED_MANDATORY_LOGIC
        defaultADColumnShouldBeFound("mandatoryLogic.in=" + DEFAULT_MANDATORY_LOGIC + "," + UPDATED_MANDATORY_LOGIC);

        // Get all the aDColumnList where mandatoryLogic equals to UPDATED_MANDATORY_LOGIC
        defaultADColumnShouldNotBeFound("mandatoryLogic.in=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatoryLogic is not null
        defaultADColumnShouldBeFound("mandatoryLogic.specified=true");

        // Get all the aDColumnList where mandatoryLogic is null
        defaultADColumnShouldNotBeFound("mandatoryLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsByMandatoryLogicContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatoryLogic contains DEFAULT_MANDATORY_LOGIC
        defaultADColumnShouldBeFound("mandatoryLogic.contains=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDColumnList where mandatoryLogic contains UPDATED_MANDATORY_LOGIC
        defaultADColumnShouldNotBeFound("mandatoryLogic.contains=" + UPDATED_MANDATORY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMandatoryLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where mandatoryLogic does not contain DEFAULT_MANDATORY_LOGIC
        defaultADColumnShouldNotBeFound("mandatoryLogic.doesNotContain=" + DEFAULT_MANDATORY_LOGIC);

        // Get all the aDColumnList where mandatoryLogic does not contain UPDATED_MANDATORY_LOGIC
        defaultADColumnShouldBeFound("mandatoryLogic.doesNotContain=" + UPDATED_MANDATORY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADColumnsByReadOnlyLogicIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where readOnlyLogic equals to DEFAULT_READ_ONLY_LOGIC
        defaultADColumnShouldBeFound("readOnlyLogic.equals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDColumnList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultADColumnShouldNotBeFound("readOnlyLogic.equals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByReadOnlyLogicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where readOnlyLogic not equals to DEFAULT_READ_ONLY_LOGIC
        defaultADColumnShouldNotBeFound("readOnlyLogic.notEquals=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDColumnList where readOnlyLogic not equals to UPDATED_READ_ONLY_LOGIC
        defaultADColumnShouldBeFound("readOnlyLogic.notEquals=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByReadOnlyLogicIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where readOnlyLogic in DEFAULT_READ_ONLY_LOGIC or UPDATED_READ_ONLY_LOGIC
        defaultADColumnShouldBeFound("readOnlyLogic.in=" + DEFAULT_READ_ONLY_LOGIC + "," + UPDATED_READ_ONLY_LOGIC);

        // Get all the aDColumnList where readOnlyLogic equals to UPDATED_READ_ONLY_LOGIC
        defaultADColumnShouldNotBeFound("readOnlyLogic.in=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByReadOnlyLogicIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where readOnlyLogic is not null
        defaultADColumnShouldBeFound("readOnlyLogic.specified=true");

        // Get all the aDColumnList where readOnlyLogic is null
        defaultADColumnShouldNotBeFound("readOnlyLogic.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsByReadOnlyLogicContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where readOnlyLogic contains DEFAULT_READ_ONLY_LOGIC
        defaultADColumnShouldBeFound("readOnlyLogic.contains=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDColumnList where readOnlyLogic contains UPDATED_READ_ONLY_LOGIC
        defaultADColumnShouldNotBeFound("readOnlyLogic.contains=" + UPDATED_READ_ONLY_LOGIC);
    }

    @Test
    @Transactional
    public void getAllADColumnsByReadOnlyLogicNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where readOnlyLogic does not contain DEFAULT_READ_ONLY_LOGIC
        defaultADColumnShouldNotBeFound("readOnlyLogic.doesNotContain=" + DEFAULT_READ_ONLY_LOGIC);

        // Get all the aDColumnList where readOnlyLogic does not contain UPDATED_READ_ONLY_LOGIC
        defaultADColumnShouldBeFound("readOnlyLogic.doesNotContain=" + UPDATED_READ_ONLY_LOGIC);
    }


    @Test
    @Transactional
    public void getAllADColumnsByUpdatableIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where updatable equals to DEFAULT_UPDATABLE
        defaultADColumnShouldBeFound("updatable.equals=" + DEFAULT_UPDATABLE);

        // Get all the aDColumnList where updatable equals to UPDATED_UPDATABLE
        defaultADColumnShouldNotBeFound("updatable.equals=" + UPDATED_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByUpdatableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where updatable not equals to DEFAULT_UPDATABLE
        defaultADColumnShouldNotBeFound("updatable.notEquals=" + DEFAULT_UPDATABLE);

        // Get all the aDColumnList where updatable not equals to UPDATED_UPDATABLE
        defaultADColumnShouldBeFound("updatable.notEquals=" + UPDATED_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByUpdatableIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where updatable in DEFAULT_UPDATABLE or UPDATED_UPDATABLE
        defaultADColumnShouldBeFound("updatable.in=" + DEFAULT_UPDATABLE + "," + UPDATED_UPDATABLE);

        // Get all the aDColumnList where updatable equals to UPDATED_UPDATABLE
        defaultADColumnShouldNotBeFound("updatable.in=" + UPDATED_UPDATABLE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByUpdatableIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where updatable is not null
        defaultADColumnShouldBeFound("updatable.specified=true");

        // Get all the aDColumnList where updatable is null
        defaultADColumnShouldNotBeFound("updatable.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByDefaultValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where defaultValue equals to DEFAULT_DEFAULT_VALUE
        defaultADColumnShouldBeFound("defaultValue.equals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDColumnList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultADColumnShouldNotBeFound("defaultValue.equals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDefaultValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where defaultValue not equals to DEFAULT_DEFAULT_VALUE
        defaultADColumnShouldNotBeFound("defaultValue.notEquals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDColumnList where defaultValue not equals to UPDATED_DEFAULT_VALUE
        defaultADColumnShouldBeFound("defaultValue.notEquals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDefaultValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where defaultValue in DEFAULT_DEFAULT_VALUE or UPDATED_DEFAULT_VALUE
        defaultADColumnShouldBeFound("defaultValue.in=" + DEFAULT_DEFAULT_VALUE + "," + UPDATED_DEFAULT_VALUE);

        // Get all the aDColumnList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultADColumnShouldNotBeFound("defaultValue.in=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDefaultValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where defaultValue is not null
        defaultADColumnShouldBeFound("defaultValue.specified=true");

        // Get all the aDColumnList where defaultValue is null
        defaultADColumnShouldNotBeFound("defaultValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsByDefaultValueContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where defaultValue contains DEFAULT_DEFAULT_VALUE
        defaultADColumnShouldBeFound("defaultValue.contains=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDColumnList where defaultValue contains UPDATED_DEFAULT_VALUE
        defaultADColumnShouldNotBeFound("defaultValue.contains=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByDefaultValueNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where defaultValue does not contain DEFAULT_DEFAULT_VALUE
        defaultADColumnShouldNotBeFound("defaultValue.doesNotContain=" + DEFAULT_DEFAULT_VALUE);

        // Get all the aDColumnList where defaultValue does not contain UPDATED_DEFAULT_VALUE
        defaultADColumnShouldBeFound("defaultValue.doesNotContain=" + UPDATED_DEFAULT_VALUE);
    }


    @Test
    @Transactional
    public void getAllADColumnsByFormatPatternIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where formatPattern equals to DEFAULT_FORMAT_PATTERN
        defaultADColumnShouldBeFound("formatPattern.equals=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDColumnList where formatPattern equals to UPDATED_FORMAT_PATTERN
        defaultADColumnShouldNotBeFound("formatPattern.equals=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFormatPatternIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where formatPattern not equals to DEFAULT_FORMAT_PATTERN
        defaultADColumnShouldNotBeFound("formatPattern.notEquals=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDColumnList where formatPattern not equals to UPDATED_FORMAT_PATTERN
        defaultADColumnShouldBeFound("formatPattern.notEquals=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFormatPatternIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where formatPattern in DEFAULT_FORMAT_PATTERN or UPDATED_FORMAT_PATTERN
        defaultADColumnShouldBeFound("formatPattern.in=" + DEFAULT_FORMAT_PATTERN + "," + UPDATED_FORMAT_PATTERN);

        // Get all the aDColumnList where formatPattern equals to UPDATED_FORMAT_PATTERN
        defaultADColumnShouldNotBeFound("formatPattern.in=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFormatPatternIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where formatPattern is not null
        defaultADColumnShouldBeFound("formatPattern.specified=true");

        // Get all the aDColumnList where formatPattern is null
        defaultADColumnShouldNotBeFound("formatPattern.specified=false");
    }
                @Test
    @Transactional
    public void getAllADColumnsByFormatPatternContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where formatPattern contains DEFAULT_FORMAT_PATTERN
        defaultADColumnShouldBeFound("formatPattern.contains=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDColumnList where formatPattern contains UPDATED_FORMAT_PATTERN
        defaultADColumnShouldNotBeFound("formatPattern.contains=" + UPDATED_FORMAT_PATTERN);
    }

    @Test
    @Transactional
    public void getAllADColumnsByFormatPatternNotContainsSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where formatPattern does not contain DEFAULT_FORMAT_PATTERN
        defaultADColumnShouldNotBeFound("formatPattern.doesNotContain=" + DEFAULT_FORMAT_PATTERN);

        // Get all the aDColumnList where formatPattern does not contain UPDATED_FORMAT_PATTERN
        defaultADColumnShouldBeFound("formatPattern.doesNotContain=" + UPDATED_FORMAT_PATTERN);
    }


    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength equals to DEFAULT_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.equals=" + DEFAULT_MIN_LENGTH);

        // Get all the aDColumnList where minLength equals to UPDATED_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.equals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength not equals to DEFAULT_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.notEquals=" + DEFAULT_MIN_LENGTH);

        // Get all the aDColumnList where minLength not equals to UPDATED_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.notEquals=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength in DEFAULT_MIN_LENGTH or UPDATED_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.in=" + DEFAULT_MIN_LENGTH + "," + UPDATED_MIN_LENGTH);

        // Get all the aDColumnList where minLength equals to UPDATED_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.in=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength is not null
        defaultADColumnShouldBeFound("minLength.specified=true");

        // Get all the aDColumnList where minLength is null
        defaultADColumnShouldNotBeFound("minLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength is greater than or equal to DEFAULT_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.greaterThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the aDColumnList where minLength is greater than or equal to UPDATED_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.greaterThanOrEqual=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength is less than or equal to DEFAULT_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.lessThanOrEqual=" + DEFAULT_MIN_LENGTH);

        // Get all the aDColumnList where minLength is less than or equal to SMALLER_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.lessThanOrEqual=" + SMALLER_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength is less than DEFAULT_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.lessThan=" + DEFAULT_MIN_LENGTH);

        // Get all the aDColumnList where minLength is less than UPDATED_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.lessThan=" + UPDATED_MIN_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minLength is greater than DEFAULT_MIN_LENGTH
        defaultADColumnShouldNotBeFound("minLength.greaterThan=" + DEFAULT_MIN_LENGTH);

        // Get all the aDColumnList where minLength is greater than SMALLER_MIN_LENGTH
        defaultADColumnShouldBeFound("minLength.greaterThan=" + SMALLER_MIN_LENGTH);
    }


    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength equals to DEFAULT_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.equals=" + DEFAULT_MAX_LENGTH);

        // Get all the aDColumnList where maxLength equals to UPDATED_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.equals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength not equals to DEFAULT_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.notEquals=" + DEFAULT_MAX_LENGTH);

        // Get all the aDColumnList where maxLength not equals to UPDATED_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.notEquals=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength in DEFAULT_MAX_LENGTH or UPDATED_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.in=" + DEFAULT_MAX_LENGTH + "," + UPDATED_MAX_LENGTH);

        // Get all the aDColumnList where maxLength equals to UPDATED_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.in=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength is not null
        defaultADColumnShouldBeFound("maxLength.specified=true");

        // Get all the aDColumnList where maxLength is null
        defaultADColumnShouldNotBeFound("maxLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength is greater than or equal to DEFAULT_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.greaterThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the aDColumnList where maxLength is greater than or equal to UPDATED_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.greaterThanOrEqual=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength is less than or equal to DEFAULT_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.lessThanOrEqual=" + DEFAULT_MAX_LENGTH);

        // Get all the aDColumnList where maxLength is less than or equal to SMALLER_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.lessThanOrEqual=" + SMALLER_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength is less than DEFAULT_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.lessThan=" + DEFAULT_MAX_LENGTH);

        // Get all the aDColumnList where maxLength is less than UPDATED_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.lessThan=" + UPDATED_MAX_LENGTH);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxLengthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxLength is greater than DEFAULT_MAX_LENGTH
        defaultADColumnShouldNotBeFound("maxLength.greaterThan=" + DEFAULT_MAX_LENGTH);

        // Get all the aDColumnList where maxLength is greater than SMALLER_MAX_LENGTH
        defaultADColumnShouldBeFound("maxLength.greaterThan=" + SMALLER_MAX_LENGTH);
    }


    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue equals to DEFAULT_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.equals=" + DEFAULT_MIN_VALUE);

        // Get all the aDColumnList where minValue equals to UPDATED_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.equals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue not equals to DEFAULT_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.notEquals=" + DEFAULT_MIN_VALUE);

        // Get all the aDColumnList where minValue not equals to UPDATED_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.notEquals=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue in DEFAULT_MIN_VALUE or UPDATED_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.in=" + DEFAULT_MIN_VALUE + "," + UPDATED_MIN_VALUE);

        // Get all the aDColumnList where minValue equals to UPDATED_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.in=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue is not null
        defaultADColumnShouldBeFound("minValue.specified=true");

        // Get all the aDColumnList where minValue is null
        defaultADColumnShouldNotBeFound("minValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue is greater than or equal to DEFAULT_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.greaterThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the aDColumnList where minValue is greater than or equal to UPDATED_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.greaterThanOrEqual=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue is less than or equal to DEFAULT_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.lessThanOrEqual=" + DEFAULT_MIN_VALUE);

        // Get all the aDColumnList where minValue is less than or equal to SMALLER_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.lessThanOrEqual=" + SMALLER_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsLessThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue is less than DEFAULT_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.lessThan=" + DEFAULT_MIN_VALUE);

        // Get all the aDColumnList where minValue is less than UPDATED_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.lessThan=" + UPDATED_MIN_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMinValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where minValue is greater than DEFAULT_MIN_VALUE
        defaultADColumnShouldNotBeFound("minValue.greaterThan=" + DEFAULT_MIN_VALUE);

        // Get all the aDColumnList where minValue is greater than SMALLER_MIN_VALUE
        defaultADColumnShouldBeFound("minValue.greaterThan=" + SMALLER_MIN_VALUE);
    }


    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue equals to DEFAULT_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.equals=" + DEFAULT_MAX_VALUE);

        // Get all the aDColumnList where maxValue equals to UPDATED_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.equals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue not equals to DEFAULT_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.notEquals=" + DEFAULT_MAX_VALUE);

        // Get all the aDColumnList where maxValue not equals to UPDATED_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.notEquals=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue in DEFAULT_MAX_VALUE or UPDATED_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.in=" + DEFAULT_MAX_VALUE + "," + UPDATED_MAX_VALUE);

        // Get all the aDColumnList where maxValue equals to UPDATED_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.in=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue is not null
        defaultADColumnShouldBeFound("maxValue.specified=true");

        // Get all the aDColumnList where maxValue is null
        defaultADColumnShouldNotBeFound("maxValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue is greater than or equal to DEFAULT_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.greaterThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the aDColumnList where maxValue is greater than or equal to UPDATED_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.greaterThanOrEqual=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue is less than or equal to DEFAULT_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.lessThanOrEqual=" + DEFAULT_MAX_VALUE);

        // Get all the aDColumnList where maxValue is less than or equal to SMALLER_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.lessThanOrEqual=" + SMALLER_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsLessThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue is less than DEFAULT_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.lessThan=" + DEFAULT_MAX_VALUE);

        // Get all the aDColumnList where maxValue is less than UPDATED_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.lessThan=" + UPDATED_MAX_VALUE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByMaxValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where maxValue is greater than DEFAULT_MAX_VALUE
        defaultADColumnShouldNotBeFound("maxValue.greaterThan=" + DEFAULT_MAX_VALUE);

        // Get all the aDColumnList where maxValue is greater than SMALLER_MAX_VALUE
        defaultADColumnShouldBeFound("maxValue.greaterThan=" + SMALLER_MAX_VALUE);
    }


    @Test
    @Transactional
    public void getAllADColumnsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where active equals to DEFAULT_ACTIVE
        defaultADColumnShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the aDColumnList where active equals to UPDATED_ACTIVE
        defaultADColumnShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where active not equals to DEFAULT_ACTIVE
        defaultADColumnShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the aDColumnList where active not equals to UPDATED_ACTIVE
        defaultADColumnShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultADColumnShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the aDColumnList where active equals to UPDATED_ACTIVE
        defaultADColumnShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllADColumnsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        // Get all the aDColumnList where active is not null
        defaultADColumnShouldBeFound("active.specified=true");

        // Get all the aDColumnList where active is null
        defaultADColumnShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllADColumnsByAdClientIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADClient adClient = aDColumn.getAdClient();
        aDColumnRepository.saveAndFlush(aDColumn);
        Long adClientId = adClient.getId();

        // Get all the aDColumnList where adClient equals to adClientId
        defaultADColumnShouldBeFound("adClientId.equals=" + adClientId);

        // Get all the aDColumnList where adClient equals to adClientId + 1
        defaultADColumnShouldNotBeFound("adClientId.equals=" + (adClientId + 1));
    }


    @Test
    @Transactional
    public void getAllADColumnsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = aDColumn.getAdOrganization();
        aDColumnRepository.saveAndFlush(aDColumn);
        Long adOrganizationId = adOrganization.getId();

        // Get all the aDColumnList where adOrganization equals to adOrganizationId
        defaultADColumnShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the aDColumnList where adOrganization equals to adOrganizationId + 1
        defaultADColumnShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllADColumnsByAdReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);
        ADReference adReference = ADReferenceResourceIT.createEntity(em);
        em.persist(adReference);
        em.flush();
        aDColumn.setAdReference(adReference);
        aDColumnRepository.saveAndFlush(aDColumn);
        Long adReferenceId = adReference.getId();

        // Get all the aDColumnList where adReference equals to adReferenceId
        defaultADColumnShouldBeFound("adReferenceId.equals=" + adReferenceId);

        // Get all the aDColumnList where adReference equals to adReferenceId + 1
        defaultADColumnShouldNotBeFound("adReferenceId.equals=" + (adReferenceId + 1));
    }


    @Test
    @Transactional
    public void getAllADColumnsByAdTableIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADTable adTable = aDColumn.getAdTable();
        aDColumnRepository.saveAndFlush(aDColumn);
        Long adTableId = adTable.getId();

        // Get all the aDColumnList where adTable equals to adTableId
        defaultADColumnShouldBeFound("adTableId.equals=" + adTableId);

        // Get all the aDColumnList where adTable equals to adTableId + 1
        defaultADColumnShouldNotBeFound("adTableId.equals=" + (adTableId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultADColumnShouldBeFound(String filter) throws Exception {
        restADColumnMockMvc.perform(get("/api/ad-columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aDColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sqlName").value(hasItem(DEFAULT_SQL_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fieldLength").value(hasItem(DEFAULT_FIELD_LENGTH.intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].mandatoryLogic").value(hasItem(DEFAULT_MANDATORY_LOGIC)))
            .andExpect(jsonPath("$.[*].readOnlyLogic").value(hasItem(DEFAULT_READ_ONLY_LOGIC)))
            .andExpect(jsonPath("$.[*].updatable").value(hasItem(DEFAULT_UPDATABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].formatPattern").value(hasItem(DEFAULT_FORMAT_PATTERN)))
            .andExpect(jsonPath("$.[*].minLength").value(hasItem(DEFAULT_MIN_LENGTH)))
            .andExpect(jsonPath("$.[*].maxLength").value(hasItem(DEFAULT_MAX_LENGTH)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restADColumnMockMvc.perform(get("/api/ad-columns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultADColumnShouldNotBeFound(String filter) throws Exception {
        restADColumnMockMvc.perform(get("/api/ad-columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restADColumnMockMvc.perform(get("/api/ad-columns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingADColumn() throws Exception {
        // Get the aDColumn
        restADColumnMockMvc.perform(get("/api/ad-columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateADColumn() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        int databaseSizeBeforeUpdate = aDColumnRepository.findAll().size();

        // Update the aDColumn
        ADColumn updatedADColumn = aDColumnRepository.findById(aDColumn.getId()).get();
        // Disconnect from session so that the updates on updatedADColumn are not directly saved in db
        em.detach(updatedADColumn);
        updatedADColumn
            .name(UPDATED_NAME)
            .sqlName(UPDATED_SQL_NAME)
            .description(UPDATED_DESCRIPTION)
            .fieldLength(UPDATED_FIELD_LENGTH)
            .key(UPDATED_KEY)
            .type(UPDATED_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .mandatoryLogic(UPDATED_MANDATORY_LOGIC)
            .readOnlyLogic(UPDATED_READ_ONLY_LOGIC)
            .updatable(UPDATED_UPDATABLE)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .formatPattern(UPDATED_FORMAT_PATTERN)
            .minLength(UPDATED_MIN_LENGTH)
            .maxLength(UPDATED_MAX_LENGTH)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .active(UPDATED_ACTIVE);
        ADColumnDTO aDColumnDTO = aDColumnMapper.toDto(updatedADColumn);

        restADColumnMockMvc.perform(put("/api/ad-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDColumnDTO)))
            .andExpect(status().isOk());

        // Validate the ADColumn in the database
        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeUpdate);
        ADColumn testADColumn = aDColumnList.get(aDColumnList.size() - 1);
        assertThat(testADColumn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testADColumn.getSqlName()).isEqualTo(UPDATED_SQL_NAME);
        assertThat(testADColumn.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testADColumn.getFieldLength()).isEqualTo(UPDATED_FIELD_LENGTH);
        assertThat(testADColumn.isKey()).isEqualTo(UPDATED_KEY);
        assertThat(testADColumn.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testADColumn.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testADColumn.getMandatoryLogic()).isEqualTo(UPDATED_MANDATORY_LOGIC);
        assertThat(testADColumn.getReadOnlyLogic()).isEqualTo(UPDATED_READ_ONLY_LOGIC);
        assertThat(testADColumn.isUpdatable()).isEqualTo(UPDATED_UPDATABLE);
        assertThat(testADColumn.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testADColumn.getFormatPattern()).isEqualTo(UPDATED_FORMAT_PATTERN);
        assertThat(testADColumn.getMinLength()).isEqualTo(UPDATED_MIN_LENGTH);
        assertThat(testADColumn.getMaxLength()).isEqualTo(UPDATED_MAX_LENGTH);
        assertThat(testADColumn.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testADColumn.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testADColumn.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingADColumn() throws Exception {
        int databaseSizeBeforeUpdate = aDColumnRepository.findAll().size();

        // Create the ADColumn
        ADColumnDTO aDColumnDTO = aDColumnMapper.toDto(aDColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restADColumnMockMvc.perform(put("/api/ad-columns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(aDColumnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ADColumn in the database
        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteADColumn() throws Exception {
        // Initialize the database
        aDColumnRepository.saveAndFlush(aDColumn);

        int databaseSizeBeforeDelete = aDColumnRepository.findAll().size();

        // Delete the aDColumn
        restADColumnMockMvc.perform(delete("/api/ad-columns/{id}", aDColumn.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ADColumn> aDColumnList = aDColumnRepository.findAll();
        assertThat(aDColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
