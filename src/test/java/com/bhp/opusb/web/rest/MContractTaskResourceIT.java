package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractTask;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.CTask;
import com.bhp.opusb.repository.MContractTaskRepository;
import com.bhp.opusb.service.MContractTaskService;
import com.bhp.opusb.service.dto.MContractTaskDTO;
import com.bhp.opusb.service.mapper.MContractTaskMapper;
import com.bhp.opusb.service.dto.MContractTaskCriteria;
import com.bhp.opusb.service.MContractTaskQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MContractTaskResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractTaskResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_DOCUMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractTaskRepository mContractTaskRepository;

    @Autowired
    private MContractTaskMapper mContractTaskMapper;

    @Autowired
    private MContractTaskService mContractTaskService;

    @Autowired
    private MContractTaskQueryService mContractTaskQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractTaskMockMvc;

    private MContractTask mContractTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTask createEntity(EntityManager em) {
        MContractTask mContractTask = new MContractTask()
            .name(DEFAULT_NAME)
            .contractDocument(DEFAULT_CONTRACT_DOCUMENT)
            .dueDate(DEFAULT_DUE_DATE)
            .documentAction(DEFAULT_DOCUMENT_ACTION)
            .documentStatus(DEFAULT_DOCUMENT_STATUS)
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
        mContractTask.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractTask.setContract(mContract);
        // Add required entity
        CTask cTask;
        if (TestUtil.findAll(em, CTask.class).isEmpty()) {
            cTask = CTaskResourceIT.createEntity(em);
            em.persist(cTask);
            em.flush();
        } else {
            cTask = TestUtil.findAll(em, CTask.class).get(0);
        }
        mContractTask.setTask(cTask);
        return mContractTask;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTask createUpdatedEntity(EntityManager em) {
        MContractTask mContractTask = new MContractTask()
            .name(UPDATED_NAME)
            .contractDocument(UPDATED_CONTRACT_DOCUMENT)
            .dueDate(UPDATED_DUE_DATE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
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
        mContractTask.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractTask.setContract(mContract);
        // Add required entity
        CTask cTask;
        if (TestUtil.findAll(em, CTask.class).isEmpty()) {
            cTask = CTaskResourceIT.createUpdatedEntity(em);
            em.persist(cTask);
            em.flush();
        } else {
            cTask = TestUtil.findAll(em, CTask.class).get(0);
        }
        mContractTask.setTask(cTask);
        return mContractTask;
    }

    @BeforeEach
    public void initTest() {
        mContractTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractTask() throws Exception {
        int databaseSizeBeforeCreate = mContractTaskRepository.findAll().size();

        // Create the MContractTask
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);
        restMContractTaskMockMvc.perform(post("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractTask in the database
        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeCreate + 1);
        MContractTask testMContractTask = mContractTaskList.get(mContractTaskList.size() - 1);
        assertThat(testMContractTask.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMContractTask.getContractDocument()).isEqualTo(DEFAULT_CONTRACT_DOCUMENT);
        assertThat(testMContractTask.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testMContractTask.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMContractTask.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMContractTask.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractTask.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractTaskRepository.findAll().size();

        // Create the MContractTask with an existing ID
        mContractTask.setId(1L);
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractTaskMockMvc.perform(post("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTask in the database
        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractTaskRepository.findAll().size();
        // set the field null
        mContractTask.setName(null);

        // Create the MContractTask, which fails.
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);

        restMContractTaskMockMvc.perform(post("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isBadRequest());

        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractTaskRepository.findAll().size();
        // set the field null
        mContractTask.setDueDate(null);

        // Create the MContractTask, which fails.
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);

        restMContractTaskMockMvc.perform(post("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isBadRequest());

        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractTaskRepository.findAll().size();
        // set the field null
        mContractTask.setDocumentAction(null);

        // Create the MContractTask, which fails.
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);

        restMContractTaskMockMvc.perform(post("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isBadRequest());

        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractTaskRepository.findAll().size();
        // set the field null
        mContractTask.setDocumentStatus(null);

        // Create the MContractTask, which fails.
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);

        restMContractTaskMockMvc.perform(post("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isBadRequest());

        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContractTasks() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contractDocument").value(hasItem(DEFAULT_CONTRACT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContractTask() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get the mContractTask
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks/{id}", mContractTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractTask.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contractDocument").value(DEFAULT_CONTRACT_DOCUMENT.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractTasksByIdFiltering() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        Long id = mContractTask.getId();

        defaultMContractTaskShouldBeFound("id.equals=" + id);
        defaultMContractTaskShouldNotBeFound("id.notEquals=" + id);

        defaultMContractTaskShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractTaskShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractTaskShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractTaskShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractTasksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where name equals to DEFAULT_NAME
        defaultMContractTaskShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mContractTaskList where name equals to UPDATED_NAME
        defaultMContractTaskShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where name not equals to DEFAULT_NAME
        defaultMContractTaskShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mContractTaskList where name not equals to UPDATED_NAME
        defaultMContractTaskShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMContractTaskShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mContractTaskList where name equals to UPDATED_NAME
        defaultMContractTaskShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where name is not null
        defaultMContractTaskShouldBeFound("name.specified=true");

        // Get all the mContractTaskList where name is null
        defaultMContractTaskShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractTasksByNameContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where name contains DEFAULT_NAME
        defaultMContractTaskShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mContractTaskList where name contains UPDATED_NAME
        defaultMContractTaskShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where name does not contain DEFAULT_NAME
        defaultMContractTaskShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mContractTaskList where name does not contain UPDATED_NAME
        defaultMContractTaskShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate equals to DEFAULT_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the mContractTaskList where dueDate equals to UPDATED_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate not equals to DEFAULT_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the mContractTaskList where dueDate not equals to UPDATED_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the mContractTaskList where dueDate equals to UPDATED_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate is not null
        defaultMContractTaskShouldBeFound("dueDate.specified=true");

        // Get all the mContractTaskList where dueDate is null
        defaultMContractTaskShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the mContractTaskList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the mContractTaskList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate is less than DEFAULT_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the mContractTaskList where dueDate is less than UPDATED_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where dueDate is greater than DEFAULT_DUE_DATE
        defaultMContractTaskShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the mContractTaskList where dueDate is greater than SMALLER_DUE_DATE
        defaultMContractTaskShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllMContractTasksByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMContractTaskShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractTaskList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMContractTaskShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMContractTaskShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractTaskList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMContractTaskShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMContractTaskShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mContractTaskList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMContractTaskShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentAction is not null
        defaultMContractTaskShouldBeFound("documentAction.specified=true");

        // Get all the mContractTaskList where documentAction is null
        defaultMContractTaskShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractTasksByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMContractTaskShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractTaskList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMContractTaskShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMContractTaskShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractTaskList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMContractTaskShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMContractTasksByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMContractTaskShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractTaskList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMContractTaskShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMContractTaskShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractTaskList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMContractTaskShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMContractTaskShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mContractTaskList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMContractTaskShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentStatus is not null
        defaultMContractTaskShouldBeFound("documentStatus.specified=true");

        // Get all the mContractTaskList where documentStatus is null
        defaultMContractTaskShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractTasksByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMContractTaskShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractTaskList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMContractTaskShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMContractTaskShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractTaskList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMContractTaskShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMContractTasksByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where uid equals to DEFAULT_UID
        defaultMContractTaskShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractTaskList where uid equals to UPDATED_UID
        defaultMContractTaskShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where uid not equals to DEFAULT_UID
        defaultMContractTaskShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractTaskList where uid not equals to UPDATED_UID
        defaultMContractTaskShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractTaskShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractTaskList where uid equals to UPDATED_UID
        defaultMContractTaskShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where uid is not null
        defaultMContractTaskShouldBeFound("uid.specified=true");

        // Get all the mContractTaskList where uid is null
        defaultMContractTaskShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTasksByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where active equals to DEFAULT_ACTIVE
        defaultMContractTaskShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractTaskList where active equals to UPDATED_ACTIVE
        defaultMContractTaskShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where active not equals to DEFAULT_ACTIVE
        defaultMContractTaskShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractTaskList where active not equals to UPDATED_ACTIVE
        defaultMContractTaskShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractTaskShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractTaskList where active equals to UPDATED_ACTIVE
        defaultMContractTaskShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTasksByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        // Get all the mContractTaskList where active is not null
        defaultMContractTaskShouldBeFound("active.specified=true");

        // Get all the mContractTaskList where active is null
        defaultMContractTaskShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTasksByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractTask.getAdOrganization();
        mContractTaskRepository.saveAndFlush(mContractTask);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractTaskList where adOrganization equals to adOrganizationId
        defaultMContractTaskShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractTaskList where adOrganization equals to adOrganizationId + 1
        defaultMContractTaskShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTasksByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mContractTask.getContract();
        mContractTaskRepository.saveAndFlush(mContractTask);
        Long contractId = contract.getId();

        // Get all the mContractTaskList where contract equals to contractId
        defaultMContractTaskShouldBeFound("contractId.equals=" + contractId);

        // Get all the mContractTaskList where contract equals to contractId + 1
        defaultMContractTaskShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTasksByTaskIsEqualToSomething() throws Exception {
        // Get already existing entity
        CTask task = mContractTask.getTask();
        mContractTaskRepository.saveAndFlush(mContractTask);
        Long taskId = task.getId();

        // Get all the mContractTaskList where task equals to taskId
        defaultMContractTaskShouldBeFound("taskId.equals=" + taskId);

        // Get all the mContractTaskList where task equals to taskId + 1
        defaultMContractTaskShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractTaskShouldBeFound(String filter) throws Exception {
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contractDocument").value(hasItem(DEFAULT_CONTRACT_DOCUMENT.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractTaskShouldNotBeFound(String filter) throws Exception {
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractTask() throws Exception {
        // Get the mContractTask
        restMContractTaskMockMvc.perform(get("/api/m-contract-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractTask() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        int databaseSizeBeforeUpdate = mContractTaskRepository.findAll().size();

        // Update the mContractTask
        MContractTask updatedMContractTask = mContractTaskRepository.findById(mContractTask.getId()).get();
        // Disconnect from session so that the updates on updatedMContractTask are not directly saved in db
        em.detach(updatedMContractTask);
        updatedMContractTask
            .name(UPDATED_NAME)
            .contractDocument(UPDATED_CONTRACT_DOCUMENT)
            .dueDate(UPDATED_DUE_DATE)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(updatedMContractTask);

        restMContractTaskMockMvc.perform(put("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isOk());

        // Validate the MContractTask in the database
        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeUpdate);
        MContractTask testMContractTask = mContractTaskList.get(mContractTaskList.size() - 1);
        assertThat(testMContractTask.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMContractTask.getContractDocument()).isEqualTo(UPDATED_CONTRACT_DOCUMENT);
        assertThat(testMContractTask.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testMContractTask.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMContractTask.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMContractTask.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractTask.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractTask() throws Exception {
        int databaseSizeBeforeUpdate = mContractTaskRepository.findAll().size();

        // Create the MContractTask
        MContractTaskDTO mContractTaskDTO = mContractTaskMapper.toDto(mContractTask);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractTaskMockMvc.perform(put("/api/m-contract-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTask in the database
        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractTask() throws Exception {
        // Initialize the database
        mContractTaskRepository.saveAndFlush(mContractTask);

        int databaseSizeBeforeDelete = mContractTaskRepository.findAll().size();

        // Delete the mContractTask
        restMContractTaskMockMvc.perform(delete("/api/m-contract-tasks/{id}", mContractTask.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractTask> mContractTaskList = mContractTaskRepository.findAll();
        assertThat(mContractTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
