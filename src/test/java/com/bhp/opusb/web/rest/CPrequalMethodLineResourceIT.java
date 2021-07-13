package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalMethodLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CPrequalificationMethod;
import com.bhp.opusb.repository.CPrequalMethodLineRepository;
import com.bhp.opusb.service.CPrequalMethodLineService;
import com.bhp.opusb.service.dto.CPrequalMethodLineDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodLineMapper;
import com.bhp.opusb.service.dto.CPrequalMethodLineCriteria;
import com.bhp.opusb.service.CPrequalMethodLineQueryService;

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
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CPrequalMethodLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalMethodLineResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(100);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(99);
    private static final BigDecimal SMALLER_WEIGHT = new BigDecimal(100 - 1);

    private static final BigDecimal DEFAULT_PASSING_GRADE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PASSING_GRADE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PASSING_GRADE = new BigDecimal(1 - 1);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalMethodLineRepository cPrequalMethodLineRepository;

    @Autowired
    private CPrequalMethodLineMapper cPrequalMethodLineMapper;

    @Autowired
    private CPrequalMethodLineService cPrequalMethodLineService;

    @Autowired
    private CPrequalMethodLineQueryService cPrequalMethodLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalMethodLineMockMvc;

    private CPrequalMethodLine cPrequalMethodLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalMethodLine createEntity(EntityManager em) {
        CPrequalMethodLine cPrequalMethodLine = new CPrequalMethodLine()
            .type(DEFAULT_TYPE)
            .weight(DEFAULT_WEIGHT)
            .passingGrade(DEFAULT_PASSING_GRADE)
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
        cPrequalMethodLine.setAdOrganization(aDOrganization);
        // Add required entity
        CPrequalificationMethod cPrequalificationMethod;
        if (TestUtil.findAll(em, CPrequalificationMethod.class).isEmpty()) {
            cPrequalificationMethod = CPrequalificationMethodResourceIT.createEntity(em);
            em.persist(cPrequalificationMethod);
            em.flush();
        } else {
            cPrequalificationMethod = TestUtil.findAll(em, CPrequalificationMethod.class).get(0);
        }
        cPrequalMethodLine.setPrequalMethod(cPrequalificationMethod);
        return cPrequalMethodLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalMethodLine createUpdatedEntity(EntityManager em) {
        CPrequalMethodLine cPrequalMethodLine = new CPrequalMethodLine()
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .passingGrade(UPDATED_PASSING_GRADE)
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
        cPrequalMethodLine.setAdOrganization(aDOrganization);
        // Add required entity
        CPrequalificationMethod cPrequalificationMethod;
        if (TestUtil.findAll(em, CPrequalificationMethod.class).isEmpty()) {
            cPrequalificationMethod = CPrequalificationMethodResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalificationMethod);
            em.flush();
        } else {
            cPrequalificationMethod = TestUtil.findAll(em, CPrequalificationMethod.class).get(0);
        }
        cPrequalMethodLine.setPrequalMethod(cPrequalificationMethod);
        return cPrequalMethodLine;
    }

    @BeforeEach
    public void initTest() {
        cPrequalMethodLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalMethodLine() throws Exception {
        int databaseSizeBeforeCreate = cPrequalMethodLineRepository.findAll().size();

        // Create the CPrequalMethodLine
        CPrequalMethodLineDTO cPrequalMethodLineDTO = cPrequalMethodLineMapper.toDto(cPrequalMethodLine);
        restCPrequalMethodLineMockMvc.perform(post("/api/c-prequal-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalMethodLine in the database
        List<CPrequalMethodLine> cPrequalMethodLineList = cPrequalMethodLineRepository.findAll();
        assertThat(cPrequalMethodLineList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalMethodLine testCPrequalMethodLine = cPrequalMethodLineList.get(cPrequalMethodLineList.size() - 1);
        assertThat(testCPrequalMethodLine.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCPrequalMethodLine.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testCPrequalMethodLine.getPassingGrade()).isEqualTo(DEFAULT_PASSING_GRADE);
        assertThat(testCPrequalMethodLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalMethodLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalMethodLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalMethodLineRepository.findAll().size();

        // Create the CPrequalMethodLine with an existing ID
        cPrequalMethodLine.setId(1L);
        CPrequalMethodLineDTO cPrequalMethodLineDTO = cPrequalMethodLineMapper.toDto(cPrequalMethodLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalMethodLineMockMvc.perform(post("/api/c-prequal-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalMethodLine in the database
        List<CPrequalMethodLine> cPrequalMethodLineList = cPrequalMethodLineRepository.findAll();
        assertThat(cPrequalMethodLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPrequalMethodLineRepository.findAll().size();
        // set the field null
        cPrequalMethodLine.setType(null);

        // Create the CPrequalMethodLine, which fails.
        CPrequalMethodLineDTO cPrequalMethodLineDTO = cPrequalMethodLineMapper.toDto(cPrequalMethodLine);

        restCPrequalMethodLineMockMvc.perform(post("/api/c-prequal-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodLineDTO)))
            .andExpect(status().isBadRequest());

        List<CPrequalMethodLine> cPrequalMethodLineList = cPrequalMethodLineRepository.findAll();
        assertThat(cPrequalMethodLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLines() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalMethodLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].passingGrade").value(hasItem(DEFAULT_PASSING_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalMethodLine() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get the cPrequalMethodLine
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines/{id}", cPrequalMethodLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalMethodLine.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.passingGrade").value(DEFAULT_PASSING_GRADE.intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalMethodLinesByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        Long id = cPrequalMethodLine.getId();

        defaultCPrequalMethodLineShouldBeFound("id.equals=" + id);
        defaultCPrequalMethodLineShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalMethodLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalMethodLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalMethodLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalMethodLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where type equals to DEFAULT_TYPE
        defaultCPrequalMethodLineShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cPrequalMethodLineList where type equals to UPDATED_TYPE
        defaultCPrequalMethodLineShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where type not equals to DEFAULT_TYPE
        defaultCPrequalMethodLineShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cPrequalMethodLineList where type not equals to UPDATED_TYPE
        defaultCPrequalMethodLineShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCPrequalMethodLineShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cPrequalMethodLineList where type equals to UPDATED_TYPE
        defaultCPrequalMethodLineShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where type is not null
        defaultCPrequalMethodLineShouldBeFound("type.specified=true");

        // Get all the cPrequalMethodLineList where type is null
        defaultCPrequalMethodLineShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalMethodLinesByTypeContainsSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where type contains DEFAULT_TYPE
        defaultCPrequalMethodLineShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the cPrequalMethodLineList where type contains UPDATED_TYPE
        defaultCPrequalMethodLineShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where type does not contain DEFAULT_TYPE
        defaultCPrequalMethodLineShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the cPrequalMethodLineList where type does not contain UPDATED_TYPE
        defaultCPrequalMethodLineShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight equals to DEFAULT_WEIGHT
        defaultCPrequalMethodLineShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodLineList where weight equals to UPDATED_WEIGHT
        defaultCPrequalMethodLineShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight not equals to DEFAULT_WEIGHT
        defaultCPrequalMethodLineShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodLineList where weight not equals to UPDATED_WEIGHT
        defaultCPrequalMethodLineShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultCPrequalMethodLineShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the cPrequalMethodLineList where weight equals to UPDATED_WEIGHT
        defaultCPrequalMethodLineShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight is not null
        defaultCPrequalMethodLineShouldBeFound("weight.specified=true");

        // Get all the cPrequalMethodLineList where weight is null
        defaultCPrequalMethodLineShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultCPrequalMethodLineShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodLineList where weight is greater than or equal to (DEFAULT_WEIGHT.add(BigDecimal.ONE))
        defaultCPrequalMethodLineShouldNotBeFound("weight.greaterThanOrEqual=" + (DEFAULT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight is less than or equal to DEFAULT_WEIGHT
        defaultCPrequalMethodLineShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodLineList where weight is less than or equal to SMALLER_WEIGHT
        defaultCPrequalMethodLineShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight is less than DEFAULT_WEIGHT
        defaultCPrequalMethodLineShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodLineList where weight is less than (DEFAULT_WEIGHT.add(BigDecimal.ONE))
        defaultCPrequalMethodLineShouldBeFound("weight.lessThan=" + (DEFAULT_WEIGHT.add(BigDecimal.ONE)));
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where weight is greater than DEFAULT_WEIGHT
        defaultCPrequalMethodLineShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the cPrequalMethodLineList where weight is greater than SMALLER_WEIGHT
        defaultCPrequalMethodLineShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade equals to DEFAULT_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.equals=" + DEFAULT_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade equals to UPDATED_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.equals=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade not equals to DEFAULT_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.notEquals=" + DEFAULT_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade not equals to UPDATED_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.notEquals=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade in DEFAULT_PASSING_GRADE or UPDATED_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.in=" + DEFAULT_PASSING_GRADE + "," + UPDATED_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade equals to UPDATED_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.in=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade is not null
        defaultCPrequalMethodLineShouldBeFound("passingGrade.specified=true");

        // Get all the cPrequalMethodLineList where passingGrade is null
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade is greater than or equal to DEFAULT_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.greaterThanOrEqual=" + DEFAULT_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade is greater than or equal to UPDATED_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.greaterThanOrEqual=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade is less than or equal to DEFAULT_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.lessThanOrEqual=" + DEFAULT_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade is less than or equal to SMALLER_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.lessThanOrEqual=" + SMALLER_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade is less than DEFAULT_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.lessThan=" + DEFAULT_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade is less than UPDATED_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.lessThan=" + UPDATED_PASSING_GRADE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPassingGradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where passingGrade is greater than DEFAULT_PASSING_GRADE
        defaultCPrequalMethodLineShouldNotBeFound("passingGrade.greaterThan=" + DEFAULT_PASSING_GRADE);

        // Get all the cPrequalMethodLineList where passingGrade is greater than SMALLER_PASSING_GRADE
        defaultCPrequalMethodLineShouldBeFound("passingGrade.greaterThan=" + SMALLER_PASSING_GRADE);
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where uid equals to DEFAULT_UID
        defaultCPrequalMethodLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalMethodLineList where uid equals to UPDATED_UID
        defaultCPrequalMethodLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where uid not equals to DEFAULT_UID
        defaultCPrequalMethodLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalMethodLineList where uid not equals to UPDATED_UID
        defaultCPrequalMethodLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalMethodLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalMethodLineList where uid equals to UPDATED_UID
        defaultCPrequalMethodLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where uid is not null
        defaultCPrequalMethodLineShouldBeFound("uid.specified=true");

        // Get all the cPrequalMethodLineList where uid is null
        defaultCPrequalMethodLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where active equals to DEFAULT_ACTIVE
        defaultCPrequalMethodLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalMethodLineList where active equals to UPDATED_ACTIVE
        defaultCPrequalMethodLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalMethodLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalMethodLineList where active not equals to UPDATED_ACTIVE
        defaultCPrequalMethodLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalMethodLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalMethodLineList where active equals to UPDATED_ACTIVE
        defaultCPrequalMethodLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        // Get all the cPrequalMethodLineList where active is not null
        defaultCPrequalMethodLineShouldBeFound("active.specified=true");

        // Get all the cPrequalMethodLineList where active is null
        defaultCPrequalMethodLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalMethodLine.getAdOrganization();
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalMethodLineList where adOrganization equals to adOrganizationId
        defaultCPrequalMethodLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalMethodLineList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalMethodLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalMethodLinesByPrequalMethodIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalificationMethod prequalMethod = cPrequalMethodLine.getPrequalMethod();
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);
        Long prequalMethodId = prequalMethod.getId();

        // Get all the cPrequalMethodLineList where prequalMethod equals to prequalMethodId
        defaultCPrequalMethodLineShouldBeFound("prequalMethodId.equals=" + prequalMethodId);

        // Get all the cPrequalMethodLineList where prequalMethod equals to prequalMethodId + 1
        defaultCPrequalMethodLineShouldNotBeFound("prequalMethodId.equals=" + (prequalMethodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalMethodLineShouldBeFound(String filter) throws Exception {
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalMethodLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].passingGrade").value(hasItem(DEFAULT_PASSING_GRADE.intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalMethodLineShouldNotBeFound(String filter) throws Exception {
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalMethodLine() throws Exception {
        // Get the cPrequalMethodLine
        restCPrequalMethodLineMockMvc.perform(get("/api/c-prequal-method-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalMethodLine() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        int databaseSizeBeforeUpdate = cPrequalMethodLineRepository.findAll().size();

        // Update the cPrequalMethodLine
        CPrequalMethodLine updatedCPrequalMethodLine = cPrequalMethodLineRepository.findById(cPrequalMethodLine.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalMethodLine are not directly saved in db
        em.detach(updatedCPrequalMethodLine);
        updatedCPrequalMethodLine
            .type(UPDATED_TYPE)
            .weight(UPDATED_WEIGHT)
            .passingGrade(UPDATED_PASSING_GRADE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalMethodLineDTO cPrequalMethodLineDTO = cPrequalMethodLineMapper.toDto(updatedCPrequalMethodLine);

        restCPrequalMethodLineMockMvc.perform(put("/api/c-prequal-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodLineDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalMethodLine in the database
        List<CPrequalMethodLine> cPrequalMethodLineList = cPrequalMethodLineRepository.findAll();
        assertThat(cPrequalMethodLineList).hasSize(databaseSizeBeforeUpdate);
        CPrequalMethodLine testCPrequalMethodLine = cPrequalMethodLineList.get(cPrequalMethodLineList.size() - 1);
        assertThat(testCPrequalMethodLine.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCPrequalMethodLine.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testCPrequalMethodLine.getPassingGrade()).isEqualTo(UPDATED_PASSING_GRADE);
        assertThat(testCPrequalMethodLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalMethodLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalMethodLine() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalMethodLineRepository.findAll().size();

        // Create the CPrequalMethodLine
        CPrequalMethodLineDTO cPrequalMethodLineDTO = cPrequalMethodLineMapper.toDto(cPrequalMethodLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalMethodLineMockMvc.perform(put("/api/c-prequal-method-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalMethodLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalMethodLine in the database
        List<CPrequalMethodLine> cPrequalMethodLineList = cPrequalMethodLineRepository.findAll();
        assertThat(cPrequalMethodLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalMethodLine() throws Exception {
        // Initialize the database
        cPrequalMethodLineRepository.saveAndFlush(cPrequalMethodLine);

        int databaseSizeBeforeDelete = cPrequalMethodLineRepository.findAll().size();

        // Delete the cPrequalMethodLine
        restCPrequalMethodLineMockMvc.perform(delete("/api/c-prequal-method-lines/{id}", cPrequalMethodLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalMethodLine> cPrequalMethodLineList = cPrequalMethodLineRepository.findAll();
        assertThat(cPrequalMethodLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
