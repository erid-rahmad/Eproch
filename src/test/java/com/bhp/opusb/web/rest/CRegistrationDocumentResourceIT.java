package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CRegistrationDocument;
import com.bhp.opusb.domain.CRegistrationDocType;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CRegistrationDocumentRepository;
import com.bhp.opusb.service.CRegistrationDocumentService;
import com.bhp.opusb.service.dto.CRegistrationDocumentDTO;
import com.bhp.opusb.service.mapper.CRegistrationDocumentMapper;
import com.bhp.opusb.service.dto.CRegistrationDocumentCriteria;
import com.bhp.opusb.service.CRegistrationDocumentQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CRegistrationDocumentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CRegistrationDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPIRATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CRegistrationDocumentRepository cRegistrationDocumentRepository;

    @Autowired
    private CRegistrationDocumentMapper cRegistrationDocumentMapper;

    @Autowired
    private CRegistrationDocumentService cRegistrationDocumentService;

    @Autowired
    private CRegistrationDocumentQueryService cRegistrationDocumentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCRegistrationDocumentMockMvc;

    private CRegistrationDocument cRegistrationDocument;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegistrationDocument createEntity(EntityManager em) {
        CRegistrationDocument cRegistrationDocument = new CRegistrationDocument()
            .documentNo(DEFAULT_DOCUMENT_NO)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CRegistrationDocType cRegistrationDocType;
        if (TestUtil.findAll(em, CRegistrationDocType.class).isEmpty()) {
            cRegistrationDocType = CRegistrationDocTypeResourceIT.createEntity(em);
            em.persist(cRegistrationDocType);
            em.flush();
        } else {
            cRegistrationDocType = TestUtil.findAll(em, CRegistrationDocType.class).get(0);
        }
        cRegistrationDocument.setType(cRegistrationDocType);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        cRegistrationDocument.setFile(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cRegistrationDocument.setAdOrganization(aDOrganization);
        return cRegistrationDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CRegistrationDocument createUpdatedEntity(EntityManager em) {
        CRegistrationDocument cRegistrationDocument = new CRegistrationDocument()
            .documentNo(UPDATED_DOCUMENT_NO)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CRegistrationDocType cRegistrationDocType;
        if (TestUtil.findAll(em, CRegistrationDocType.class).isEmpty()) {
            cRegistrationDocType = CRegistrationDocTypeResourceIT.createUpdatedEntity(em);
            em.persist(cRegistrationDocType);
            em.flush();
        } else {
            cRegistrationDocType = TestUtil.findAll(em, CRegistrationDocType.class).get(0);
        }
        cRegistrationDocument.setType(cRegistrationDocType);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        cRegistrationDocument.setFile(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cRegistrationDocument.setAdOrganization(aDOrganization);
        return cRegistrationDocument;
    }

    @BeforeEach
    public void initTest() {
        cRegistrationDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createCRegistrationDocument() throws Exception {
        int databaseSizeBeforeCreate = cRegistrationDocumentRepository.findAll().size();

        // Create the CRegistrationDocument
        CRegistrationDocumentDTO cRegistrationDocumentDTO = cRegistrationDocumentMapper.toDto(cRegistrationDocument);
        restCRegistrationDocumentMockMvc.perform(post("/api/c-registration-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the CRegistrationDocument in the database
        List<CRegistrationDocument> cRegistrationDocumentList = cRegistrationDocumentRepository.findAll();
        assertThat(cRegistrationDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        CRegistrationDocument testCRegistrationDocument = cRegistrationDocumentList.get(cRegistrationDocumentList.size() - 1);
        assertThat(testCRegistrationDocument.getDocumentNo()).isEqualTo(DEFAULT_DOCUMENT_NO);
        assertThat(testCRegistrationDocument.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testCRegistrationDocument.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCRegistrationDocument.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCRegistrationDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cRegistrationDocumentRepository.findAll().size();

        // Create the CRegistrationDocument with an existing ID
        cRegistrationDocument.setId(1L);
        CRegistrationDocumentDTO cRegistrationDocumentDTO = cRegistrationDocumentMapper.toDto(cRegistrationDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCRegistrationDocumentMockMvc.perform(post("/api/c-registration-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegistrationDocument in the database
        List<CRegistrationDocument> cRegistrationDocumentList = cRegistrationDocumentRepository.findAll();
        assertThat(cRegistrationDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDocumentNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cRegistrationDocumentRepository.findAll().size();
        // set the field null
        cRegistrationDocument.setDocumentNo(null);

        // Create the CRegistrationDocument, which fails.
        CRegistrationDocumentDTO cRegistrationDocumentDTO = cRegistrationDocumentMapper.toDto(cRegistrationDocument);

        restCRegistrationDocumentMockMvc.perform(post("/api/c-registration-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<CRegistrationDocument> cRegistrationDocumentList = cRegistrationDocumentRepository.findAll();
        assertThat(cRegistrationDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocuments() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegistrationDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCRegistrationDocument() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get the cRegistrationDocument
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents/{id}", cRegistrationDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cRegistrationDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCRegistrationDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        Long id = cRegistrationDocument.getId();

        defaultCRegistrationDocumentShouldBeFound("id.equals=" + id);
        defaultCRegistrationDocumentShouldNotBeFound("id.notEquals=" + id);

        defaultCRegistrationDocumentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCRegistrationDocumentShouldNotBeFound("id.greaterThan=" + id);

        defaultCRegistrationDocumentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCRegistrationDocumentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultCRegistrationDocumentShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the cRegistrationDocumentList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultCRegistrationDocumentShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultCRegistrationDocumentShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the cRegistrationDocumentList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultCRegistrationDocumentShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultCRegistrationDocumentShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the cRegistrationDocumentList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultCRegistrationDocumentShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where documentNo is not null
        defaultCRegistrationDocumentShouldBeFound("documentNo.specified=true");

        // Get all the cRegistrationDocumentList where documentNo is null
        defaultCRegistrationDocumentShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCRegistrationDocumentsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultCRegistrationDocumentShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the cRegistrationDocumentList where documentNo contains UPDATED_DOCUMENT_NO
        defaultCRegistrationDocumentShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultCRegistrationDocumentShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the cRegistrationDocumentList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultCRegistrationDocumentShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate equals to DEFAULT_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.equals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.equals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate not equals to DEFAULT_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.notEquals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate not equals to UPDATED_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.notEquals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate in DEFAULT_EXPIRATION_DATE or UPDATED_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.in=" + DEFAULT_EXPIRATION_DATE + "," + UPDATED_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.in=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate is not null
        defaultCRegistrationDocumentShouldBeFound("expirationDate.specified=true");

        // Get all the cRegistrationDocumentList where expirationDate is null
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate is greater than or equal to DEFAULT_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.greaterThanOrEqual=" + DEFAULT_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate is greater than or equal to UPDATED_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.greaterThanOrEqual=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate is less than or equal to DEFAULT_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.lessThanOrEqual=" + DEFAULT_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate is less than or equal to SMALLER_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.lessThanOrEqual=" + SMALLER_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate is less than DEFAULT_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.lessThan=" + DEFAULT_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate is less than UPDATED_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.lessThan=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByExpirationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where expirationDate is greater than DEFAULT_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldNotBeFound("expirationDate.greaterThan=" + DEFAULT_EXPIRATION_DATE);

        // Get all the cRegistrationDocumentList where expirationDate is greater than SMALLER_EXPIRATION_DATE
        defaultCRegistrationDocumentShouldBeFound("expirationDate.greaterThan=" + SMALLER_EXPIRATION_DATE);
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where uid equals to DEFAULT_UID
        defaultCRegistrationDocumentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cRegistrationDocumentList where uid equals to UPDATED_UID
        defaultCRegistrationDocumentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where uid not equals to DEFAULT_UID
        defaultCRegistrationDocumentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cRegistrationDocumentList where uid not equals to UPDATED_UID
        defaultCRegistrationDocumentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where uid in DEFAULT_UID or UPDATED_UID
        defaultCRegistrationDocumentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cRegistrationDocumentList where uid equals to UPDATED_UID
        defaultCRegistrationDocumentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where uid is not null
        defaultCRegistrationDocumentShouldBeFound("uid.specified=true");

        // Get all the cRegistrationDocumentList where uid is null
        defaultCRegistrationDocumentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where active equals to DEFAULT_ACTIVE
        defaultCRegistrationDocumentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cRegistrationDocumentList where active equals to UPDATED_ACTIVE
        defaultCRegistrationDocumentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where active not equals to DEFAULT_ACTIVE
        defaultCRegistrationDocumentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cRegistrationDocumentList where active not equals to UPDATED_ACTIVE
        defaultCRegistrationDocumentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCRegistrationDocumentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cRegistrationDocumentList where active equals to UPDATED_ACTIVE
        defaultCRegistrationDocumentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        // Get all the cRegistrationDocumentList where active is not null
        defaultCRegistrationDocumentShouldBeFound("active.specified=true");

        // Get all the cRegistrationDocumentList where active is null
        defaultCRegistrationDocumentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CRegistrationDocType type = cRegistrationDocument.getType();
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);
        Long typeId = type.getId();

        // Get all the cRegistrationDocumentList where type equals to typeId
        defaultCRegistrationDocumentShouldBeFound("typeId.equals=" + typeId);

        // Get all the cRegistrationDocumentList where type equals to typeId + 1
        defaultCRegistrationDocumentShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByFileIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment file = cRegistrationDocument.getFile();
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);
        Long fileId = file.getId();

        // Get all the cRegistrationDocumentList where file equals to fileId
        defaultCRegistrationDocumentShouldBeFound("fileId.equals=" + fileId);

        // Get all the cRegistrationDocumentList where file equals to fileId + 1
        defaultCRegistrationDocumentShouldNotBeFound("fileId.equals=" + (fileId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);
        CVendor vendor = CVendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        cRegistrationDocument.setVendor(vendor);
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);
        Long vendorId = vendor.getId();

        // Get all the cRegistrationDocumentList where vendor equals to vendorId
        defaultCRegistrationDocumentShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the cRegistrationDocumentList where vendor equals to vendorId + 1
        defaultCRegistrationDocumentShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllCRegistrationDocumentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cRegistrationDocument.getAdOrganization();
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cRegistrationDocumentList where adOrganization equals to adOrganizationId
        defaultCRegistrationDocumentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cRegistrationDocumentList where adOrganization equals to adOrganizationId + 1
        defaultCRegistrationDocumentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCRegistrationDocumentShouldBeFound(String filter) throws Exception {
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cRegistrationDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCRegistrationDocumentShouldNotBeFound(String filter) throws Exception {
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCRegistrationDocument() throws Exception {
        // Get the cRegistrationDocument
        restCRegistrationDocumentMockMvc.perform(get("/api/c-registration-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCRegistrationDocument() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        int databaseSizeBeforeUpdate = cRegistrationDocumentRepository.findAll().size();

        // Update the cRegistrationDocument
        CRegistrationDocument updatedCRegistrationDocument = cRegistrationDocumentRepository.findById(cRegistrationDocument.getId()).get();
        // Disconnect from session so that the updates on updatedCRegistrationDocument are not directly saved in db
        em.detach(updatedCRegistrationDocument);
        updatedCRegistrationDocument
            .documentNo(UPDATED_DOCUMENT_NO)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CRegistrationDocumentDTO cRegistrationDocumentDTO = cRegistrationDocumentMapper.toDto(updatedCRegistrationDocument);

        restCRegistrationDocumentMockMvc.perform(put("/api/c-registration-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the CRegistrationDocument in the database
        List<CRegistrationDocument> cRegistrationDocumentList = cRegistrationDocumentRepository.findAll();
        assertThat(cRegistrationDocumentList).hasSize(databaseSizeBeforeUpdate);
        CRegistrationDocument testCRegistrationDocument = cRegistrationDocumentList.get(cRegistrationDocumentList.size() - 1);
        assertThat(testCRegistrationDocument.getDocumentNo()).isEqualTo(UPDATED_DOCUMENT_NO);
        assertThat(testCRegistrationDocument.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testCRegistrationDocument.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCRegistrationDocument.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCRegistrationDocument() throws Exception {
        int databaseSizeBeforeUpdate = cRegistrationDocumentRepository.findAll().size();

        // Create the CRegistrationDocument
        CRegistrationDocumentDTO cRegistrationDocumentDTO = cRegistrationDocumentMapper.toDto(cRegistrationDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCRegistrationDocumentMockMvc.perform(put("/api/c-registration-documents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cRegistrationDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CRegistrationDocument in the database
        List<CRegistrationDocument> cRegistrationDocumentList = cRegistrationDocumentRepository.findAll();
        assertThat(cRegistrationDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCRegistrationDocument() throws Exception {
        // Initialize the database
        cRegistrationDocumentRepository.saveAndFlush(cRegistrationDocument);

        int databaseSizeBeforeDelete = cRegistrationDocumentRepository.findAll().size();

        // Delete the cRegistrationDocument
        restCRegistrationDocumentMockMvc.perform(delete("/api/c-registration-documents/{id}", cRegistrationDocument.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CRegistrationDocument> cRegistrationDocumentList = cRegistrationDocumentRepository.findAll();
        assertThat(cRegistrationDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
