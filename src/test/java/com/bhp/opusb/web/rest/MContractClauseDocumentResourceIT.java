package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractClauseDocument;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.repository.MContractClauseDocumentRepository;
import com.bhp.opusb.service.MContractClauseDocumentService;
import com.bhp.opusb.service.dto.MContractClauseDocumentDTO;
import com.bhp.opusb.service.mapper.MContractClauseDocumentMapper;
import com.bhp.opusb.service.dto.MContractClauseDocumentCriteria;
import com.bhp.opusb.service.MContractClauseDocumentQueryService;

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
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MContractClauseDocumentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractClauseDocumentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_STATUS = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MContractClauseDocumentRepository mContractClauseDocumentRepository;

    @Autowired
    private MContractClauseDocumentMapper mContractClauseDocumentMapper;

    @Autowired
    private MContractClauseDocumentService mContractClauseDocumentService;

    @Autowired
    private MContractClauseDocumentQueryService mContractClauseDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractClauseDocumentMockMvc;

    private MContractClauseDocument mContractClauseDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractClauseDocument createEntity(EntityManager em) {
        MContractClauseDocument mContractClauseDocument = new MContractClauseDocument()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .comment(DEFAULT_COMMENT)
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
        mContractClauseDocument.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractClauseDocument.setContract(mContract);
        return mContractClauseDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractClauseDocument createUpdatedEntity(EntityManager em) {
        MContractClauseDocument mContractClauseDocument = new MContractClauseDocument()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .comment(UPDATED_COMMENT)
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
        mContractClauseDocument.setAdOrganization(aDOrganization);
        // Add required entity
        MContract mContract;
        if (TestUtil.findAll(em, MContract.class).isEmpty()) {
            mContract = MContractResourceIT.createUpdatedEntity(em);
            em.persist(mContract);
            em.flush();
        } else {
            mContract = TestUtil.findAll(em, MContract.class).get(0);
        }
        mContractClauseDocument.setContract(mContract);
        return mContractClauseDocument;
    }

    @BeforeEach
    public void initTest() {
        mContractClauseDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractClauseDocument() throws Exception {
        int databaseSizeBeforeCreate = mContractClauseDocumentRepository.findAll().size();

        // Create the MContractClauseDocument
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(mContractClauseDocument);
        restMContractClauseDocumentMockMvc.perform(post("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractClauseDocument in the database
        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        MContractClauseDocument testMContractClauseDocument = mContractClauseDocumentList.get(mContractClauseDocumentList.size() - 1);
        assertThat(testMContractClauseDocument.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMContractClauseDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMContractClauseDocument.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMContractClauseDocument.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testMContractClauseDocument.getDocumentAction()).isEqualTo(DEFAULT_DOCUMENT_ACTION);
        assertThat(testMContractClauseDocument.getDocumentStatus()).isEqualTo(DEFAULT_DOCUMENT_STATUS);
        assertThat(testMContractClauseDocument.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractClauseDocument.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMContractClauseDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractClauseDocumentRepository.findAll().size();

        // Create the MContractClauseDocument with an existing ID
        mContractClauseDocument.setId(1L);
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(mContractClauseDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractClauseDocumentMockMvc.perform(post("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractClauseDocument in the database
        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractClauseDocumentRepository.findAll().size();
        // set the field null
        mContractClauseDocument.setName(null);

        // Create the MContractClauseDocument, which fails.
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(mContractClauseDocument);

        restMContractClauseDocumentMockMvc.perform(post("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractClauseDocumentRepository.findAll().size();
        // set the field null
        mContractClauseDocument.setDocumentAction(null);

        // Create the MContractClauseDocument, which fails.
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(mContractClauseDocument);

        restMContractClauseDocumentMockMvc.perform(post("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractClauseDocumentRepository.findAll().size();
        // set the field null
        mContractClauseDocument.setDocumentStatus(null);

        // Create the MContractClauseDocument, which fails.
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(mContractClauseDocument);

        restMContractClauseDocumentMockMvc.perform(post("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocuments() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractClauseDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMContractClauseDocument() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get the mContractClauseDocument
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents/{id}", mContractClauseDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractClauseDocument.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.documentAction").value(DEFAULT_DOCUMENT_ACTION))
            .andExpect(jsonPath("$.documentStatus").value(DEFAULT_DOCUMENT_STATUS))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMContractClauseDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        Long id = mContractClauseDocument.getId();

        defaultMContractClauseDocumentShouldBeFound("id.equals=" + id);
        defaultMContractClauseDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultMContractClauseDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractClauseDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractClauseDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractClauseDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where name equals to DEFAULT_NAME
        defaultMContractClauseDocumentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the mContractClauseDocumentList where name equals to UPDATED_NAME
        defaultMContractClauseDocumentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where name not equals to DEFAULT_NAME
        defaultMContractClauseDocumentShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the mContractClauseDocumentList where name not equals to UPDATED_NAME
        defaultMContractClauseDocumentShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMContractClauseDocumentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the mContractClauseDocumentList where name equals to UPDATED_NAME
        defaultMContractClauseDocumentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where name is not null
        defaultMContractClauseDocumentShouldBeFound("name.specified=true");

        // Get all the mContractClauseDocumentList where name is null
        defaultMContractClauseDocumentShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractClauseDocumentsByNameContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where name contains DEFAULT_NAME
        defaultMContractClauseDocumentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the mContractClauseDocumentList where name contains UPDATED_NAME
        defaultMContractClauseDocumentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where name does not contain DEFAULT_NAME
        defaultMContractClauseDocumentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the mContractClauseDocumentList where name does not contain UPDATED_NAME
        defaultMContractClauseDocumentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where type equals to DEFAULT_TYPE
        defaultMContractClauseDocumentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the mContractClauseDocumentList where type equals to UPDATED_TYPE
        defaultMContractClauseDocumentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where type not equals to DEFAULT_TYPE
        defaultMContractClauseDocumentShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the mContractClauseDocumentList where type not equals to UPDATED_TYPE
        defaultMContractClauseDocumentShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMContractClauseDocumentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the mContractClauseDocumentList where type equals to UPDATED_TYPE
        defaultMContractClauseDocumentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where type is not null
        defaultMContractClauseDocumentShouldBeFound("type.specified=true");

        // Get all the mContractClauseDocumentList where type is null
        defaultMContractClauseDocumentShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractClauseDocumentsByTypeContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where type contains DEFAULT_TYPE
        defaultMContractClauseDocumentShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the mContractClauseDocumentList where type contains UPDATED_TYPE
        defaultMContractClauseDocumentShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where type does not contain DEFAULT_TYPE
        defaultMContractClauseDocumentShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the mContractClauseDocumentList where type does not contain UPDATED_TYPE
        defaultMContractClauseDocumentShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where comment equals to DEFAULT_COMMENT
        defaultMContractClauseDocumentShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the mContractClauseDocumentList where comment equals to UPDATED_COMMENT
        defaultMContractClauseDocumentShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where comment not equals to DEFAULT_COMMENT
        defaultMContractClauseDocumentShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the mContractClauseDocumentList where comment not equals to UPDATED_COMMENT
        defaultMContractClauseDocumentShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultMContractClauseDocumentShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the mContractClauseDocumentList where comment equals to UPDATED_COMMENT
        defaultMContractClauseDocumentShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where comment is not null
        defaultMContractClauseDocumentShouldBeFound("comment.specified=true");

        // Get all the mContractClauseDocumentList where comment is null
        defaultMContractClauseDocumentShouldNotBeFound("comment.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractClauseDocumentsByCommentContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where comment contains DEFAULT_COMMENT
        defaultMContractClauseDocumentShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the mContractClauseDocumentList where comment contains UPDATED_COMMENT
        defaultMContractClauseDocumentShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where comment does not contain DEFAULT_COMMENT
        defaultMContractClauseDocumentShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the mContractClauseDocumentList where comment does not contain UPDATED_COMMENT
        defaultMContractClauseDocumentShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentActionIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentAction equals to DEFAULT_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldBeFound("documentAction.equals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractClauseDocumentList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldNotBeFound("documentAction.equals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentAction not equals to DEFAULT_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldNotBeFound("documentAction.notEquals=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractClauseDocumentList where documentAction not equals to UPDATED_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldBeFound("documentAction.notEquals=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentActionIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentAction in DEFAULT_DOCUMENT_ACTION or UPDATED_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldBeFound("documentAction.in=" + DEFAULT_DOCUMENT_ACTION + "," + UPDATED_DOCUMENT_ACTION);

        // Get all the mContractClauseDocumentList where documentAction equals to UPDATED_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldNotBeFound("documentAction.in=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentAction is not null
        defaultMContractClauseDocumentShouldBeFound("documentAction.specified=true");

        // Get all the mContractClauseDocumentList where documentAction is null
        defaultMContractClauseDocumentShouldNotBeFound("documentAction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentActionContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentAction contains DEFAULT_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldBeFound("documentAction.contains=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractClauseDocumentList where documentAction contains UPDATED_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldNotBeFound("documentAction.contains=" + UPDATED_DOCUMENT_ACTION);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentActionNotContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentAction does not contain DEFAULT_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldNotBeFound("documentAction.doesNotContain=" + DEFAULT_DOCUMENT_ACTION);

        // Get all the mContractClauseDocumentList where documentAction does not contain UPDATED_DOCUMENT_ACTION
        defaultMContractClauseDocumentShouldBeFound("documentAction.doesNotContain=" + UPDATED_DOCUMENT_ACTION);
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentStatus equals to DEFAULT_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldBeFound("documentStatus.equals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractClauseDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldNotBeFound("documentStatus.equals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentStatus not equals to DEFAULT_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldNotBeFound("documentStatus.notEquals=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractClauseDocumentList where documentStatus not equals to UPDATED_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldBeFound("documentStatus.notEquals=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentStatus in DEFAULT_DOCUMENT_STATUS or UPDATED_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldBeFound("documentStatus.in=" + DEFAULT_DOCUMENT_STATUS + "," + UPDATED_DOCUMENT_STATUS);

        // Get all the mContractClauseDocumentList where documentStatus equals to UPDATED_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldNotBeFound("documentStatus.in=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentStatus is not null
        defaultMContractClauseDocumentShouldBeFound("documentStatus.specified=true");

        // Get all the mContractClauseDocumentList where documentStatus is null
        defaultMContractClauseDocumentShouldNotBeFound("documentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentStatusContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentStatus contains DEFAULT_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldBeFound("documentStatus.contains=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractClauseDocumentList where documentStatus contains UPDATED_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldNotBeFound("documentStatus.contains=" + UPDATED_DOCUMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByDocumentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where documentStatus does not contain DEFAULT_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldNotBeFound("documentStatus.doesNotContain=" + DEFAULT_DOCUMENT_STATUS);

        // Get all the mContractClauseDocumentList where documentStatus does not contain UPDATED_DOCUMENT_STATUS
        defaultMContractClauseDocumentShouldBeFound("documentStatus.doesNotContain=" + UPDATED_DOCUMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where uid equals to DEFAULT_UID
        defaultMContractClauseDocumentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractClauseDocumentList where uid equals to UPDATED_UID
        defaultMContractClauseDocumentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where uid not equals to DEFAULT_UID
        defaultMContractClauseDocumentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractClauseDocumentList where uid not equals to UPDATED_UID
        defaultMContractClauseDocumentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractClauseDocumentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractClauseDocumentList where uid equals to UPDATED_UID
        defaultMContractClauseDocumentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where uid is not null
        defaultMContractClauseDocumentShouldBeFound("uid.specified=true");

        // Get all the mContractClauseDocumentList where uid is null
        defaultMContractClauseDocumentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where active equals to DEFAULT_ACTIVE
        defaultMContractClauseDocumentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractClauseDocumentList where active equals to UPDATED_ACTIVE
        defaultMContractClauseDocumentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where active not equals to DEFAULT_ACTIVE
        defaultMContractClauseDocumentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractClauseDocumentList where active not equals to UPDATED_ACTIVE
        defaultMContractClauseDocumentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractClauseDocumentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractClauseDocumentList where active equals to UPDATED_ACTIVE
        defaultMContractClauseDocumentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        // Get all the mContractClauseDocumentList where active is not null
        defaultMContractClauseDocumentShouldBeFound("active.specified=true");

        // Get all the mContractClauseDocumentList where active is null
        defaultMContractClauseDocumentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractClauseDocument.getAdOrganization();
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractClauseDocumentList where adOrganization equals to adOrganizationId
        defaultMContractClauseDocumentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractClauseDocumentList where adOrganization equals to adOrganizationId + 1
        defaultMContractClauseDocumentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractClauseDocumentsByContractIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContract contract = mContractClauseDocument.getContract();
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);
        Long contractId = contract.getId();

        // Get all the mContractClauseDocumentList where contract equals to contractId
        defaultMContractClauseDocumentShouldBeFound("contractId.equals=" + contractId);

        // Get all the mContractClauseDocumentList where contract equals to contractId + 1
        defaultMContractClauseDocumentShouldNotBeFound("contractId.equals=" + (contractId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractClauseDocumentShouldBeFound(String filter) throws Exception {
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractClauseDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].documentAction").value(hasItem(DEFAULT_DOCUMENT_ACTION)))
            .andExpect(jsonPath("$.[*].documentStatus").value(hasItem(DEFAULT_DOCUMENT_STATUS)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractClauseDocumentShouldNotBeFound(String filter) throws Exception {
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractClauseDocument() throws Exception {
        // Get the mContractClauseDocument
        restMContractClauseDocumentMockMvc.perform(get("/api/m-contract-clause-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractClauseDocument() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        int databaseSizeBeforeUpdate = mContractClauseDocumentRepository.findAll().size();

        // Update the mContractClauseDocument
        MContractClauseDocument updatedMContractClauseDocument = mContractClauseDocumentRepository.findById(mContractClauseDocument.getId()).get();
        // Disconnect from session so that the updates on updatedMContractClauseDocument are not directly saved in db
        em.detach(updatedMContractClauseDocument);
        updatedMContractClauseDocument
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .comment(UPDATED_COMMENT)
            .documentAction(UPDATED_DOCUMENT_ACTION)
            .documentStatus(UPDATED_DOCUMENT_STATUS)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(updatedMContractClauseDocument);

        restMContractClauseDocumentMockMvc.perform(put("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the MContractClauseDocument in the database
        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeUpdate);
        MContractClauseDocument testMContractClauseDocument = mContractClauseDocumentList.get(mContractClauseDocumentList.size() - 1);
        assertThat(testMContractClauseDocument.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMContractClauseDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMContractClauseDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMContractClauseDocument.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testMContractClauseDocument.getDocumentAction()).isEqualTo(UPDATED_DOCUMENT_ACTION);
        assertThat(testMContractClauseDocument.getDocumentStatus()).isEqualTo(UPDATED_DOCUMENT_STATUS);
        assertThat(testMContractClauseDocument.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractClauseDocument.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractClauseDocument() throws Exception {
        int databaseSizeBeforeUpdate = mContractClauseDocumentRepository.findAll().size();

        // Create the MContractClauseDocument
        MContractClauseDocumentDTO mContractClauseDocumentDTO = mContractClauseDocumentMapper.toDto(mContractClauseDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractClauseDocumentMockMvc.perform(put("/api/m-contract-clause-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractClauseDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractClauseDocument in the database
        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractClauseDocument() throws Exception {
        // Initialize the database
        mContractClauseDocumentRepository.saveAndFlush(mContractClauseDocument);

        int databaseSizeBeforeDelete = mContractClauseDocumentRepository.findAll().size();

        // Delete the mContractClauseDocument
        restMContractClauseDocumentMockMvc.perform(delete("/api/m-contract-clause-documents/{id}", mContractClauseDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractClauseDocument> mContractClauseDocumentList = mContractClauseDocumentRepository.findAll();
        assertThat(mContractClauseDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
