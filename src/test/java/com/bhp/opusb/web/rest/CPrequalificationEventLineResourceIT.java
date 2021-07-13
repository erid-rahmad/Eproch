package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CPrequalificationEventLine;
import com.bhp.opusb.domain.CPrequalificationEvent;
import com.bhp.opusb.domain.CPrequalificationStep;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CPrequalificationEventLineRepository;
import com.bhp.opusb.service.CPrequalificationEventLineService;
import com.bhp.opusb.service.dto.CPrequalificationEventLineDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventLineMapper;
import com.bhp.opusb.service.dto.CPrequalificationEventLineCriteria;
import com.bhp.opusb.service.CPrequalificationEventLineQueryService;

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
 * Integration tests for the {@link CPrequalificationEventLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CPrequalificationEventLineResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 0;
    private static final Integer UPDATED_SEQUENCE = 1;
    private static final Integer SMALLER_SEQUENCE = 0 - 1;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CPrequalificationEventLineRepository cPrequalificationEventLineRepository;

    @Autowired
    private CPrequalificationEventLineMapper cPrequalificationEventLineMapper;

    @Autowired
    private CPrequalificationEventLineService cPrequalificationEventLineService;

    @Autowired
    private CPrequalificationEventLineQueryService cPrequalificationEventLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCPrequalificationEventLineMockMvc;

    private CPrequalificationEventLine cPrequalificationEventLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationEventLine createEntity(EntityManager em) {
        CPrequalificationEventLine cPrequalificationEventLine = new CPrequalificationEventLine()
            .description(DEFAULT_DESCRIPTION)
            .sequence(DEFAULT_SEQUENCE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CPrequalificationEvent cPrequalificationEvent;
        if (TestUtil.findAll(em, CPrequalificationEvent.class).isEmpty()) {
            cPrequalificationEvent = CPrequalificationEventResourceIT.createEntity(em);
            em.persist(cPrequalificationEvent);
            em.flush();
        } else {
            cPrequalificationEvent = TestUtil.findAll(em, CPrequalificationEvent.class).get(0);
        }
        cPrequalificationEventLine.setPrequalificationEvent(cPrequalificationEvent);
        // Add required entity
        CPrequalificationStep cPrequalificationStep;
        if (TestUtil.findAll(em, CPrequalificationStep.class).isEmpty()) {
            cPrequalificationStep = CPrequalificationStepResourceIT.createEntity(em);
            em.persist(cPrequalificationStep);
            em.flush();
        } else {
            cPrequalificationStep = TestUtil.findAll(em, CPrequalificationStep.class).get(0);
        }
        cPrequalificationEventLine.setPrequalificationStep(cPrequalificationStep);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPrequalificationEventLine.setAdOrganization(aDOrganization);
        return cPrequalificationEventLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CPrequalificationEventLine createUpdatedEntity(EntityManager em) {
        CPrequalificationEventLine cPrequalificationEventLine = new CPrequalificationEventLine()
            .description(UPDATED_DESCRIPTION)
            .sequence(UPDATED_SEQUENCE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CPrequalificationEvent cPrequalificationEvent;
        if (TestUtil.findAll(em, CPrequalificationEvent.class).isEmpty()) {
            cPrequalificationEvent = CPrequalificationEventResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalificationEvent);
            em.flush();
        } else {
            cPrequalificationEvent = TestUtil.findAll(em, CPrequalificationEvent.class).get(0);
        }
        cPrequalificationEventLine.setPrequalificationEvent(cPrequalificationEvent);
        // Add required entity
        CPrequalificationStep cPrequalificationStep;
        if (TestUtil.findAll(em, CPrequalificationStep.class).isEmpty()) {
            cPrequalificationStep = CPrequalificationStepResourceIT.createUpdatedEntity(em);
            em.persist(cPrequalificationStep);
            em.flush();
        } else {
            cPrequalificationStep = TestUtil.findAll(em, CPrequalificationStep.class).get(0);
        }
        cPrequalificationEventLine.setPrequalificationStep(cPrequalificationStep);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cPrequalificationEventLine.setAdOrganization(aDOrganization);
        return cPrequalificationEventLine;
    }

    @BeforeEach
    public void initTest() {
        cPrequalificationEventLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPrequalificationEventLine() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationEventLineRepository.findAll().size();

        // Create the CPrequalificationEventLine
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO = cPrequalificationEventLineMapper.toDto(cPrequalificationEventLine);
        restCPrequalificationEventLineMockMvc.perform(post("/api/c-prequalification-event-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventLineDTO)))
            .andExpect(status().isCreated());

        // Validate the CPrequalificationEventLine in the database
        List<CPrequalificationEventLine> cPrequalificationEventLineList = cPrequalificationEventLineRepository.findAll();
        assertThat(cPrequalificationEventLineList).hasSize(databaseSizeBeforeCreate + 1);
        CPrequalificationEventLine testCPrequalificationEventLine = cPrequalificationEventLineList.get(cPrequalificationEventLineList.size() - 1);
        assertThat(testCPrequalificationEventLine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCPrequalificationEventLine.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testCPrequalificationEventLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCPrequalificationEventLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCPrequalificationEventLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPrequalificationEventLineRepository.findAll().size();

        // Create the CPrequalificationEventLine with an existing ID
        cPrequalificationEventLine.setId(1L);
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO = cPrequalificationEventLineMapper.toDto(cPrequalificationEventLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPrequalificationEventLineMockMvc.perform(post("/api/c-prequalification-event-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationEventLine in the database
        List<CPrequalificationEventLine> cPrequalificationEventLineList = cPrequalificationEventLineRepository.findAll();
        assertThat(cPrequalificationEventLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPrequalificationEventLineRepository.findAll().size();
        // set the field null
        cPrequalificationEventLine.setSequence(null);

        // Create the CPrequalificationEventLine, which fails.
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO = cPrequalificationEventLineMapper.toDto(cPrequalificationEventLine);

        restCPrequalificationEventLineMockMvc.perform(post("/api/c-prequalification-event-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventLineDTO)))
            .andExpect(status().isBadRequest());

        List<CPrequalificationEventLine> cPrequalificationEventLineList = cPrequalificationEventLineRepository.findAll();
        assertThat(cPrequalificationEventLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLines() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationEventLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCPrequalificationEventLine() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get the cPrequalificationEventLine
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines/{id}", cPrequalificationEventLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cPrequalificationEventLine.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCPrequalificationEventLinesByIdFiltering() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        Long id = cPrequalificationEventLine.getId();

        defaultCPrequalificationEventLineShouldBeFound("id.equals=" + id);
        defaultCPrequalificationEventLineShouldNotBeFound("id.notEquals=" + id);

        defaultCPrequalificationEventLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCPrequalificationEventLineShouldNotBeFound("id.greaterThan=" + id);

        defaultCPrequalificationEventLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCPrequalificationEventLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where description equals to DEFAULT_DESCRIPTION
        defaultCPrequalificationEventLineShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationEventLineList where description equals to UPDATED_DESCRIPTION
        defaultCPrequalificationEventLineShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where description not equals to DEFAULT_DESCRIPTION
        defaultCPrequalificationEventLineShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationEventLineList where description not equals to UPDATED_DESCRIPTION
        defaultCPrequalificationEventLineShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCPrequalificationEventLineShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the cPrequalificationEventLineList where description equals to UPDATED_DESCRIPTION
        defaultCPrequalificationEventLineShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where description is not null
        defaultCPrequalificationEventLineShouldBeFound("description.specified=true");

        // Get all the cPrequalificationEventLineList where description is null
        defaultCPrequalificationEventLineShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where description contains DEFAULT_DESCRIPTION
        defaultCPrequalificationEventLineShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationEventLineList where description contains UPDATED_DESCRIPTION
        defaultCPrequalificationEventLineShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where description does not contain DEFAULT_DESCRIPTION
        defaultCPrequalificationEventLineShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the cPrequalificationEventLineList where description does not contain UPDATED_DESCRIPTION
        defaultCPrequalificationEventLineShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence equals to DEFAULT_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence equals to UPDATED_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence not equals to DEFAULT_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.notEquals=" + DEFAULT_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence not equals to UPDATED_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.notEquals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence equals to UPDATED_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence is not null
        defaultCPrequalificationEventLineShouldBeFound("sequence.specified=true");

        // Get all the cPrequalificationEventLineList where sequence is null
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence is greater than or equal to DEFAULT_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.greaterThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence is greater than or equal to UPDATED_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.greaterThanOrEqual=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence is less than or equal to DEFAULT_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.lessThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence is less than or equal to SMALLER_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.lessThanOrEqual=" + SMALLER_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence is less than DEFAULT_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence is less than UPDATED_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesBySequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where sequence is greater than DEFAULT_SEQUENCE
        defaultCPrequalificationEventLineShouldNotBeFound("sequence.greaterThan=" + DEFAULT_SEQUENCE);

        // Get all the cPrequalificationEventLineList where sequence is greater than SMALLER_SEQUENCE
        defaultCPrequalificationEventLineShouldBeFound("sequence.greaterThan=" + SMALLER_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where uid equals to DEFAULT_UID
        defaultCPrequalificationEventLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cPrequalificationEventLineList where uid equals to UPDATED_UID
        defaultCPrequalificationEventLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where uid not equals to DEFAULT_UID
        defaultCPrequalificationEventLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cPrequalificationEventLineList where uid not equals to UPDATED_UID
        defaultCPrequalificationEventLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultCPrequalificationEventLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cPrequalificationEventLineList where uid equals to UPDATED_UID
        defaultCPrequalificationEventLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where uid is not null
        defaultCPrequalificationEventLineShouldBeFound("uid.specified=true");

        // Get all the cPrequalificationEventLineList where uid is null
        defaultCPrequalificationEventLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where active equals to DEFAULT_ACTIVE
        defaultCPrequalificationEventLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationEventLineList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationEventLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where active not equals to DEFAULT_ACTIVE
        defaultCPrequalificationEventLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cPrequalificationEventLineList where active not equals to UPDATED_ACTIVE
        defaultCPrequalificationEventLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCPrequalificationEventLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cPrequalificationEventLineList where active equals to UPDATED_ACTIVE
        defaultCPrequalificationEventLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        // Get all the cPrequalificationEventLineList where active is not null
        defaultCPrequalificationEventLineShouldBeFound("active.specified=true");

        // Get all the cPrequalificationEventLineList where active is null
        defaultCPrequalificationEventLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByPrequalificationEventIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalificationEvent prequalificationEvent = cPrequalificationEventLine.getPrequalificationEvent();
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);
        Long prequalificationEventId = prequalificationEvent.getId();

        // Get all the cPrequalificationEventLineList where prequalificationEvent equals to prequalificationEventId
        defaultCPrequalificationEventLineShouldBeFound("prequalificationEventId.equals=" + prequalificationEventId);

        // Get all the cPrequalificationEventLineList where prequalificationEvent equals to prequalificationEventId + 1
        defaultCPrequalificationEventLineShouldNotBeFound("prequalificationEventId.equals=" + (prequalificationEventId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByPrequalificationStepIsEqualToSomething() throws Exception {
        // Get already existing entity
        CPrequalificationStep prequalificationStep = cPrequalificationEventLine.getPrequalificationStep();
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);
        Long prequalificationStepId = prequalificationStep.getId();

        // Get all the cPrequalificationEventLineList where prequalificationStep equals to prequalificationStepId
        defaultCPrequalificationEventLineShouldBeFound("prequalificationStepId.equals=" + prequalificationStepId);

        // Get all the cPrequalificationEventLineList where prequalificationStep equals to prequalificationStepId + 1
        defaultCPrequalificationEventLineShouldNotBeFound("prequalificationStepId.equals=" + (prequalificationStepId + 1));
    }


    @Test
    @Transactional
    public void getAllCPrequalificationEventLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cPrequalificationEventLine.getAdOrganization();
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cPrequalificationEventLineList where adOrganization equals to adOrganizationId
        defaultCPrequalificationEventLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cPrequalificationEventLineList where adOrganization equals to adOrganizationId + 1
        defaultCPrequalificationEventLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCPrequalificationEventLineShouldBeFound(String filter) throws Exception {
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPrequalificationEventLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCPrequalificationEventLineShouldNotBeFound(String filter) throws Exception {
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCPrequalificationEventLine() throws Exception {
        // Get the cPrequalificationEventLine
        restCPrequalificationEventLineMockMvc.perform(get("/api/c-prequalification-event-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPrequalificationEventLine() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        int databaseSizeBeforeUpdate = cPrequalificationEventLineRepository.findAll().size();

        // Update the cPrequalificationEventLine
        CPrequalificationEventLine updatedCPrequalificationEventLine = cPrequalificationEventLineRepository.findById(cPrequalificationEventLine.getId()).get();
        // Disconnect from session so that the updates on updatedCPrequalificationEventLine are not directly saved in db
        em.detach(updatedCPrequalificationEventLine);
        updatedCPrequalificationEventLine
            .description(UPDATED_DESCRIPTION)
            .sequence(UPDATED_SEQUENCE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO = cPrequalificationEventLineMapper.toDto(updatedCPrequalificationEventLine);

        restCPrequalificationEventLineMockMvc.perform(put("/api/c-prequalification-event-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventLineDTO)))
            .andExpect(status().isOk());

        // Validate the CPrequalificationEventLine in the database
        List<CPrequalificationEventLine> cPrequalificationEventLineList = cPrequalificationEventLineRepository.findAll();
        assertThat(cPrequalificationEventLineList).hasSize(databaseSizeBeforeUpdate);
        CPrequalificationEventLine testCPrequalificationEventLine = cPrequalificationEventLineList.get(cPrequalificationEventLineList.size() - 1);
        assertThat(testCPrequalificationEventLine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCPrequalificationEventLine.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCPrequalificationEventLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCPrequalificationEventLine.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCPrequalificationEventLine() throws Exception {
        int databaseSizeBeforeUpdate = cPrequalificationEventLineRepository.findAll().size();

        // Create the CPrequalificationEventLine
        CPrequalificationEventLineDTO cPrequalificationEventLineDTO = cPrequalificationEventLineMapper.toDto(cPrequalificationEventLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCPrequalificationEventLineMockMvc.perform(put("/api/c-prequalification-event-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cPrequalificationEventLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPrequalificationEventLine in the database
        List<CPrequalificationEventLine> cPrequalificationEventLineList = cPrequalificationEventLineRepository.findAll();
        assertThat(cPrequalificationEventLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCPrequalificationEventLine() throws Exception {
        // Initialize the database
        cPrequalificationEventLineRepository.saveAndFlush(cPrequalificationEventLine);

        int databaseSizeBeforeDelete = cPrequalificationEventLineRepository.findAll().size();

        // Delete the cPrequalificationEventLine
        restCPrequalificationEventLineMockMvc.perform(delete("/api/c-prequalification-event-lines/{id}", cPrequalificationEventLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CPrequalificationEventLine> cPrequalificationEventLineList = cPrequalificationEventLineRepository.findAll();
        assertThat(cPrequalificationEventLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
