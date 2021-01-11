package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.CAttachmentRepository;
import com.bhp.opusb.service.CAttachmentService;
import com.bhp.opusb.service.dto.CAttachmentDTO;
import com.bhp.opusb.service.mapper.CAttachmentMapper;
import com.bhp.opusb.service.dto.CAttachmentCriteria;
import com.bhp.opusb.service.CAttachmentQueryService;

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

import com.bhp.opusb.domain.enumeration.CAttachmentType;
/**
 * Integration tests for the {@link CAttachmentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CAttachmentResourceIT {

    private static final CAttachmentType DEFAULT_TYPE = CAttachmentType.LOCAL;
    private static final CAttachmentType UPDATED_TYPE = CAttachmentType.REMOTE;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_SMALL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_SMALL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_MEDIUM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_MEDIUM = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_LARGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_LARGE = "BBBBBBBBBB";

    private static final String DEFAULT_MIME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MIME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOAD_DIR = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_DIR = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CAttachmentRepository cAttachmentRepository;

    @Autowired
    private CAttachmentMapper cAttachmentMapper;

    @Autowired
    private CAttachmentService cAttachmentService;

    @Autowired
    private CAttachmentQueryService cAttachmentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCAttachmentMockMvc;

    private CAttachment cAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAttachment createEntity(EntityManager em) {
        CAttachment cAttachment = new CAttachment()
            .type(DEFAULT_TYPE)
            .fileName(DEFAULT_FILE_NAME)
            .imageSmall(DEFAULT_IMAGE_SMALL)
            .imageMedium(DEFAULT_IMAGE_MEDIUM)
            .imageLarge(DEFAULT_IMAGE_LARGE)
            .mimeType(DEFAULT_MIME_TYPE)
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .uploadDir(DEFAULT_UPLOAD_DIR)
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
        cAttachment.setAdOrganization(aDOrganization);
        return cAttachment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CAttachment createUpdatedEntity(EntityManager em) {
        CAttachment cAttachment = new CAttachment()
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .imageSmall(UPDATED_IMAGE_SMALL)
            .imageMedium(UPDATED_IMAGE_MEDIUM)
            .imageLarge(UPDATED_IMAGE_LARGE)
            .mimeType(UPDATED_MIME_TYPE)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .uploadDir(UPDATED_UPLOAD_DIR)
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
        cAttachment.setAdOrganization(aDOrganization);
        return cAttachment;
    }

    @BeforeEach
    public void initTest() {
        cAttachment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCAttachment() throws Exception {
        int databaseSizeBeforeCreate = cAttachmentRepository.findAll().size();

        // Create the CAttachment
        CAttachmentDTO cAttachmentDTO = cAttachmentMapper.toDto(cAttachment);
        restCAttachmentMockMvc.perform(post("/api/c-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAttachmentDTO)))
            .andExpect(status().isCreated());

        // Validate the CAttachment in the database
        List<CAttachment> cAttachmentList = cAttachmentRepository.findAll();
        assertThat(cAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        CAttachment testCAttachment = cAttachmentList.get(cAttachmentList.size() - 1);
        assertThat(testCAttachment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCAttachment.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testCAttachment.getImageSmall()).isEqualTo(DEFAULT_IMAGE_SMALL);
        assertThat(testCAttachment.getImageMedium()).isEqualTo(DEFAULT_IMAGE_MEDIUM);
        assertThat(testCAttachment.getImageLarge()).isEqualTo(DEFAULT_IMAGE_LARGE);
        assertThat(testCAttachment.getMimeType()).isEqualTo(DEFAULT_MIME_TYPE);
        assertThat(testCAttachment.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testCAttachment.getUploadDir()).isEqualTo(DEFAULT_UPLOAD_DIR);
        assertThat(testCAttachment.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCAttachment.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCAttachmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cAttachmentRepository.findAll().size();

        // Create the CAttachment with an existing ID
        cAttachment.setId(1L);
        CAttachmentDTO cAttachmentDTO = cAttachmentMapper.toDto(cAttachment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCAttachmentMockMvc.perform(post("/api/c-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAttachment in the database
        List<CAttachment> cAttachmentList = cAttachmentRepository.findAll();
        assertThat(cAttachmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cAttachmentRepository.findAll().size();
        // set the field null
        cAttachment.setType(null);

        // Create the CAttachment, which fails.
        CAttachmentDTO cAttachmentDTO = cAttachmentMapper.toDto(cAttachment);

        restCAttachmentMockMvc.perform(post("/api/c-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAttachmentDTO)))
            .andExpect(status().isBadRequest());

        List<CAttachment> cAttachmentList = cAttachmentRepository.findAll();
        assertThat(cAttachmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCAttachments() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList
        restCAttachmentMockMvc.perform(get("/api/c-attachments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].imageSmall").value(hasItem(DEFAULT_IMAGE_SMALL)))
            .andExpect(jsonPath("$.[*].imageMedium").value(hasItem(DEFAULT_IMAGE_MEDIUM)))
            .andExpect(jsonPath("$.[*].imageLarge").value(hasItem(DEFAULT_IMAGE_LARGE)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadDir").value(hasItem(DEFAULT_UPLOAD_DIR)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCAttachment() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get the cAttachment
        restCAttachmentMockMvc.perform(get("/api/c-attachments/{id}", cAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cAttachment.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.imageSmall").value(DEFAULT_IMAGE_SMALL))
            .andExpect(jsonPath("$.imageMedium").value(DEFAULT_IMAGE_MEDIUM))
            .andExpect(jsonPath("$.imageLarge").value(DEFAULT_IMAGE_LARGE))
            .andExpect(jsonPath("$.mimeType").value(DEFAULT_MIME_TYPE))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE))
            .andExpect(jsonPath("$.uploadDir").value(DEFAULT_UPLOAD_DIR))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCAttachmentsByIdFiltering() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        Long id = cAttachment.getId();

        defaultCAttachmentShouldBeFound("id.equals=" + id);
        defaultCAttachmentShouldNotBeFound("id.notEquals=" + id);

        defaultCAttachmentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCAttachmentShouldNotBeFound("id.greaterThan=" + id);

        defaultCAttachmentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCAttachmentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where type equals to DEFAULT_TYPE
        defaultCAttachmentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cAttachmentList where type equals to UPDATED_TYPE
        defaultCAttachmentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where type not equals to DEFAULT_TYPE
        defaultCAttachmentShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cAttachmentList where type not equals to UPDATED_TYPE
        defaultCAttachmentShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCAttachmentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cAttachmentList where type equals to UPDATED_TYPE
        defaultCAttachmentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where type is not null
        defaultCAttachmentShouldBeFound("type.specified=true");

        // Get all the cAttachmentList where type is null
        defaultCAttachmentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where fileName equals to DEFAULT_FILE_NAME
        defaultCAttachmentShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the cAttachmentList where fileName equals to UPDATED_FILE_NAME
        defaultCAttachmentShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByFileNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where fileName not equals to DEFAULT_FILE_NAME
        defaultCAttachmentShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);

        // Get all the cAttachmentList where fileName not equals to UPDATED_FILE_NAME
        defaultCAttachmentShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultCAttachmentShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the cAttachmentList where fileName equals to UPDATED_FILE_NAME
        defaultCAttachmentShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where fileName is not null
        defaultCAttachmentShouldBeFound("fileName.specified=true");

        // Get all the cAttachmentList where fileName is null
        defaultCAttachmentShouldNotBeFound("fileName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where fileName contains DEFAULT_FILE_NAME
        defaultCAttachmentShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the cAttachmentList where fileName contains UPDATED_FILE_NAME
        defaultCAttachmentShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where fileName does not contain DEFAULT_FILE_NAME
        defaultCAttachmentShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the cAttachmentList where fileName does not contain UPDATED_FILE_NAME
        defaultCAttachmentShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByImageSmallIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageSmall equals to DEFAULT_IMAGE_SMALL
        defaultCAttachmentShouldBeFound("imageSmall.equals=" + DEFAULT_IMAGE_SMALL);

        // Get all the cAttachmentList where imageSmall equals to UPDATED_IMAGE_SMALL
        defaultCAttachmentShouldNotBeFound("imageSmall.equals=" + UPDATED_IMAGE_SMALL);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageSmallIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageSmall not equals to DEFAULT_IMAGE_SMALL
        defaultCAttachmentShouldNotBeFound("imageSmall.notEquals=" + DEFAULT_IMAGE_SMALL);

        // Get all the cAttachmentList where imageSmall not equals to UPDATED_IMAGE_SMALL
        defaultCAttachmentShouldBeFound("imageSmall.notEquals=" + UPDATED_IMAGE_SMALL);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageSmallIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageSmall in DEFAULT_IMAGE_SMALL or UPDATED_IMAGE_SMALL
        defaultCAttachmentShouldBeFound("imageSmall.in=" + DEFAULT_IMAGE_SMALL + "," + UPDATED_IMAGE_SMALL);

        // Get all the cAttachmentList where imageSmall equals to UPDATED_IMAGE_SMALL
        defaultCAttachmentShouldNotBeFound("imageSmall.in=" + UPDATED_IMAGE_SMALL);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageSmallIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageSmall is not null
        defaultCAttachmentShouldBeFound("imageSmall.specified=true");

        // Get all the cAttachmentList where imageSmall is null
        defaultCAttachmentShouldNotBeFound("imageSmall.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByImageSmallContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageSmall contains DEFAULT_IMAGE_SMALL
        defaultCAttachmentShouldBeFound("imageSmall.contains=" + DEFAULT_IMAGE_SMALL);

        // Get all the cAttachmentList where imageSmall contains UPDATED_IMAGE_SMALL
        defaultCAttachmentShouldNotBeFound("imageSmall.contains=" + UPDATED_IMAGE_SMALL);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageSmallNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageSmall does not contain DEFAULT_IMAGE_SMALL
        defaultCAttachmentShouldNotBeFound("imageSmall.doesNotContain=" + DEFAULT_IMAGE_SMALL);

        // Get all the cAttachmentList where imageSmall does not contain UPDATED_IMAGE_SMALL
        defaultCAttachmentShouldBeFound("imageSmall.doesNotContain=" + UPDATED_IMAGE_SMALL);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByImageMediumIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageMedium equals to DEFAULT_IMAGE_MEDIUM
        defaultCAttachmentShouldBeFound("imageMedium.equals=" + DEFAULT_IMAGE_MEDIUM);

        // Get all the cAttachmentList where imageMedium equals to UPDATED_IMAGE_MEDIUM
        defaultCAttachmentShouldNotBeFound("imageMedium.equals=" + UPDATED_IMAGE_MEDIUM);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageMediumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageMedium not equals to DEFAULT_IMAGE_MEDIUM
        defaultCAttachmentShouldNotBeFound("imageMedium.notEquals=" + DEFAULT_IMAGE_MEDIUM);

        // Get all the cAttachmentList where imageMedium not equals to UPDATED_IMAGE_MEDIUM
        defaultCAttachmentShouldBeFound("imageMedium.notEquals=" + UPDATED_IMAGE_MEDIUM);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageMediumIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageMedium in DEFAULT_IMAGE_MEDIUM or UPDATED_IMAGE_MEDIUM
        defaultCAttachmentShouldBeFound("imageMedium.in=" + DEFAULT_IMAGE_MEDIUM + "," + UPDATED_IMAGE_MEDIUM);

        // Get all the cAttachmentList where imageMedium equals to UPDATED_IMAGE_MEDIUM
        defaultCAttachmentShouldNotBeFound("imageMedium.in=" + UPDATED_IMAGE_MEDIUM);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageMediumIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageMedium is not null
        defaultCAttachmentShouldBeFound("imageMedium.specified=true");

        // Get all the cAttachmentList where imageMedium is null
        defaultCAttachmentShouldNotBeFound("imageMedium.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByImageMediumContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageMedium contains DEFAULT_IMAGE_MEDIUM
        defaultCAttachmentShouldBeFound("imageMedium.contains=" + DEFAULT_IMAGE_MEDIUM);

        // Get all the cAttachmentList where imageMedium contains UPDATED_IMAGE_MEDIUM
        defaultCAttachmentShouldNotBeFound("imageMedium.contains=" + UPDATED_IMAGE_MEDIUM);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageMediumNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageMedium does not contain DEFAULT_IMAGE_MEDIUM
        defaultCAttachmentShouldNotBeFound("imageMedium.doesNotContain=" + DEFAULT_IMAGE_MEDIUM);

        // Get all the cAttachmentList where imageMedium does not contain UPDATED_IMAGE_MEDIUM
        defaultCAttachmentShouldBeFound("imageMedium.doesNotContain=" + UPDATED_IMAGE_MEDIUM);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByImageLargeIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageLarge equals to DEFAULT_IMAGE_LARGE
        defaultCAttachmentShouldBeFound("imageLarge.equals=" + DEFAULT_IMAGE_LARGE);

        // Get all the cAttachmentList where imageLarge equals to UPDATED_IMAGE_LARGE
        defaultCAttachmentShouldNotBeFound("imageLarge.equals=" + UPDATED_IMAGE_LARGE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageLargeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageLarge not equals to DEFAULT_IMAGE_LARGE
        defaultCAttachmentShouldNotBeFound("imageLarge.notEquals=" + DEFAULT_IMAGE_LARGE);

        // Get all the cAttachmentList where imageLarge not equals to UPDATED_IMAGE_LARGE
        defaultCAttachmentShouldBeFound("imageLarge.notEquals=" + UPDATED_IMAGE_LARGE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageLargeIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageLarge in DEFAULT_IMAGE_LARGE or UPDATED_IMAGE_LARGE
        defaultCAttachmentShouldBeFound("imageLarge.in=" + DEFAULT_IMAGE_LARGE + "," + UPDATED_IMAGE_LARGE);

        // Get all the cAttachmentList where imageLarge equals to UPDATED_IMAGE_LARGE
        defaultCAttachmentShouldNotBeFound("imageLarge.in=" + UPDATED_IMAGE_LARGE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageLargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageLarge is not null
        defaultCAttachmentShouldBeFound("imageLarge.specified=true");

        // Get all the cAttachmentList where imageLarge is null
        defaultCAttachmentShouldNotBeFound("imageLarge.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByImageLargeContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageLarge contains DEFAULT_IMAGE_LARGE
        defaultCAttachmentShouldBeFound("imageLarge.contains=" + DEFAULT_IMAGE_LARGE);

        // Get all the cAttachmentList where imageLarge contains UPDATED_IMAGE_LARGE
        defaultCAttachmentShouldNotBeFound("imageLarge.contains=" + UPDATED_IMAGE_LARGE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByImageLargeNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where imageLarge does not contain DEFAULT_IMAGE_LARGE
        defaultCAttachmentShouldNotBeFound("imageLarge.doesNotContain=" + DEFAULT_IMAGE_LARGE);

        // Get all the cAttachmentList where imageLarge does not contain UPDATED_IMAGE_LARGE
        defaultCAttachmentShouldBeFound("imageLarge.doesNotContain=" + UPDATED_IMAGE_LARGE);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByMimeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where mimeType equals to DEFAULT_MIME_TYPE
        defaultCAttachmentShouldBeFound("mimeType.equals=" + DEFAULT_MIME_TYPE);

        // Get all the cAttachmentList where mimeType equals to UPDATED_MIME_TYPE
        defaultCAttachmentShouldNotBeFound("mimeType.equals=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByMimeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where mimeType not equals to DEFAULT_MIME_TYPE
        defaultCAttachmentShouldNotBeFound("mimeType.notEquals=" + DEFAULT_MIME_TYPE);

        // Get all the cAttachmentList where mimeType not equals to UPDATED_MIME_TYPE
        defaultCAttachmentShouldBeFound("mimeType.notEquals=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByMimeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where mimeType in DEFAULT_MIME_TYPE or UPDATED_MIME_TYPE
        defaultCAttachmentShouldBeFound("mimeType.in=" + DEFAULT_MIME_TYPE + "," + UPDATED_MIME_TYPE);

        // Get all the cAttachmentList where mimeType equals to UPDATED_MIME_TYPE
        defaultCAttachmentShouldNotBeFound("mimeType.in=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByMimeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where mimeType is not null
        defaultCAttachmentShouldBeFound("mimeType.specified=true");

        // Get all the cAttachmentList where mimeType is null
        defaultCAttachmentShouldNotBeFound("mimeType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByMimeTypeContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where mimeType contains DEFAULT_MIME_TYPE
        defaultCAttachmentShouldBeFound("mimeType.contains=" + DEFAULT_MIME_TYPE);

        // Get all the cAttachmentList where mimeType contains UPDATED_MIME_TYPE
        defaultCAttachmentShouldNotBeFound("mimeType.contains=" + UPDATED_MIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByMimeTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where mimeType does not contain DEFAULT_MIME_TYPE
        defaultCAttachmentShouldNotBeFound("mimeType.doesNotContain=" + DEFAULT_MIME_TYPE);

        // Get all the cAttachmentList where mimeType does not contain UPDATED_MIME_TYPE
        defaultCAttachmentShouldBeFound("mimeType.doesNotContain=" + UPDATED_MIME_TYPE);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByDocumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where documentType equals to DEFAULT_DOCUMENT_TYPE
        defaultCAttachmentShouldBeFound("documentType.equals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the cAttachmentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultCAttachmentShouldNotBeFound("documentType.equals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByDocumentTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where documentType not equals to DEFAULT_DOCUMENT_TYPE
        defaultCAttachmentShouldNotBeFound("documentType.notEquals=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the cAttachmentList where documentType not equals to UPDATED_DOCUMENT_TYPE
        defaultCAttachmentShouldBeFound("documentType.notEquals=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByDocumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where documentType in DEFAULT_DOCUMENT_TYPE or UPDATED_DOCUMENT_TYPE
        defaultCAttachmentShouldBeFound("documentType.in=" + DEFAULT_DOCUMENT_TYPE + "," + UPDATED_DOCUMENT_TYPE);

        // Get all the cAttachmentList where documentType equals to UPDATED_DOCUMENT_TYPE
        defaultCAttachmentShouldNotBeFound("documentType.in=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByDocumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where documentType is not null
        defaultCAttachmentShouldBeFound("documentType.specified=true");

        // Get all the cAttachmentList where documentType is null
        defaultCAttachmentShouldNotBeFound("documentType.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByDocumentTypeContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where documentType contains DEFAULT_DOCUMENT_TYPE
        defaultCAttachmentShouldBeFound("documentType.contains=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the cAttachmentList where documentType contains UPDATED_DOCUMENT_TYPE
        defaultCAttachmentShouldNotBeFound("documentType.contains=" + UPDATED_DOCUMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByDocumentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where documentType does not contain DEFAULT_DOCUMENT_TYPE
        defaultCAttachmentShouldNotBeFound("documentType.doesNotContain=" + DEFAULT_DOCUMENT_TYPE);

        // Get all the cAttachmentList where documentType does not contain UPDATED_DOCUMENT_TYPE
        defaultCAttachmentShouldBeFound("documentType.doesNotContain=" + UPDATED_DOCUMENT_TYPE);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByUploadDirIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uploadDir equals to DEFAULT_UPLOAD_DIR
        defaultCAttachmentShouldBeFound("uploadDir.equals=" + DEFAULT_UPLOAD_DIR);

        // Get all the cAttachmentList where uploadDir equals to UPDATED_UPLOAD_DIR
        defaultCAttachmentShouldNotBeFound("uploadDir.equals=" + UPDATED_UPLOAD_DIR);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUploadDirIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uploadDir not equals to DEFAULT_UPLOAD_DIR
        defaultCAttachmentShouldNotBeFound("uploadDir.notEquals=" + DEFAULT_UPLOAD_DIR);

        // Get all the cAttachmentList where uploadDir not equals to UPDATED_UPLOAD_DIR
        defaultCAttachmentShouldBeFound("uploadDir.notEquals=" + UPDATED_UPLOAD_DIR);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUploadDirIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uploadDir in DEFAULT_UPLOAD_DIR or UPDATED_UPLOAD_DIR
        defaultCAttachmentShouldBeFound("uploadDir.in=" + DEFAULT_UPLOAD_DIR + "," + UPDATED_UPLOAD_DIR);

        // Get all the cAttachmentList where uploadDir equals to UPDATED_UPLOAD_DIR
        defaultCAttachmentShouldNotBeFound("uploadDir.in=" + UPDATED_UPLOAD_DIR);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUploadDirIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uploadDir is not null
        defaultCAttachmentShouldBeFound("uploadDir.specified=true");

        // Get all the cAttachmentList where uploadDir is null
        defaultCAttachmentShouldNotBeFound("uploadDir.specified=false");
    }
                @Test
    @Transactional
    public void getAllCAttachmentsByUploadDirContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uploadDir contains DEFAULT_UPLOAD_DIR
        defaultCAttachmentShouldBeFound("uploadDir.contains=" + DEFAULT_UPLOAD_DIR);

        // Get all the cAttachmentList where uploadDir contains UPDATED_UPLOAD_DIR
        defaultCAttachmentShouldNotBeFound("uploadDir.contains=" + UPDATED_UPLOAD_DIR);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUploadDirNotContainsSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uploadDir does not contain DEFAULT_UPLOAD_DIR
        defaultCAttachmentShouldNotBeFound("uploadDir.doesNotContain=" + DEFAULT_UPLOAD_DIR);

        // Get all the cAttachmentList where uploadDir does not contain UPDATED_UPLOAD_DIR
        defaultCAttachmentShouldBeFound("uploadDir.doesNotContain=" + UPDATED_UPLOAD_DIR);
    }


    @Test
    @Transactional
    public void getAllCAttachmentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uid equals to DEFAULT_UID
        defaultCAttachmentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cAttachmentList where uid equals to UPDATED_UID
        defaultCAttachmentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uid not equals to DEFAULT_UID
        defaultCAttachmentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cAttachmentList where uid not equals to UPDATED_UID
        defaultCAttachmentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uid in DEFAULT_UID or UPDATED_UID
        defaultCAttachmentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cAttachmentList where uid equals to UPDATED_UID
        defaultCAttachmentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where uid is not null
        defaultCAttachmentShouldBeFound("uid.specified=true");

        // Get all the cAttachmentList where uid is null
        defaultCAttachmentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where active equals to DEFAULT_ACTIVE
        defaultCAttachmentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cAttachmentList where active equals to UPDATED_ACTIVE
        defaultCAttachmentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where active not equals to DEFAULT_ACTIVE
        defaultCAttachmentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cAttachmentList where active not equals to UPDATED_ACTIVE
        defaultCAttachmentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCAttachmentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cAttachmentList where active equals to UPDATED_ACTIVE
        defaultCAttachmentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        // Get all the cAttachmentList where active is not null
        defaultCAttachmentShouldBeFound("active.specified=true");

        // Get all the cAttachmentList where active is null
        defaultCAttachmentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCAttachmentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cAttachment.getAdOrganization();
        cAttachmentRepository.saveAndFlush(cAttachment);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cAttachmentList where adOrganization equals to adOrganizationId
        defaultCAttachmentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cAttachmentList where adOrganization equals to adOrganizationId + 1
        defaultCAttachmentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCAttachmentShouldBeFound(String filter) throws Exception {
        restCAttachmentMockMvc.perform(get("/api/c-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].imageSmall").value(hasItem(DEFAULT_IMAGE_SMALL)))
            .andExpect(jsonPath("$.[*].imageMedium").value(hasItem(DEFAULT_IMAGE_MEDIUM)))
            .andExpect(jsonPath("$.[*].imageLarge").value(hasItem(DEFAULT_IMAGE_LARGE)))
            .andExpect(jsonPath("$.[*].mimeType").value(hasItem(DEFAULT_MIME_TYPE)))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadDir").value(hasItem(DEFAULT_UPLOAD_DIR)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCAttachmentMockMvc.perform(get("/api/c-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCAttachmentShouldNotBeFound(String filter) throws Exception {
        restCAttachmentMockMvc.perform(get("/api/c-attachments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCAttachmentMockMvc.perform(get("/api/c-attachments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCAttachment() throws Exception {
        // Get the cAttachment
        restCAttachmentMockMvc.perform(get("/api/c-attachments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCAttachment() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        int databaseSizeBeforeUpdate = cAttachmentRepository.findAll().size();

        // Update the cAttachment
        CAttachment updatedCAttachment = cAttachmentRepository.findById(cAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedCAttachment are not directly saved in db
        em.detach(updatedCAttachment);
        updatedCAttachment
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .imageSmall(UPDATED_IMAGE_SMALL)
            .imageMedium(UPDATED_IMAGE_MEDIUM)
            .imageLarge(UPDATED_IMAGE_LARGE)
            .mimeType(UPDATED_MIME_TYPE)
            .documentType(UPDATED_DOCUMENT_TYPE)
            .uploadDir(UPDATED_UPLOAD_DIR)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CAttachmentDTO cAttachmentDTO = cAttachmentMapper.toDto(updatedCAttachment);

        restCAttachmentMockMvc.perform(put("/api/c-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAttachmentDTO)))
            .andExpect(status().isOk());

        // Validate the CAttachment in the database
        List<CAttachment> cAttachmentList = cAttachmentRepository.findAll();
        assertThat(cAttachmentList).hasSize(databaseSizeBeforeUpdate);
        CAttachment testCAttachment = cAttachmentList.get(cAttachmentList.size() - 1);
        assertThat(testCAttachment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCAttachment.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testCAttachment.getImageSmall()).isEqualTo(UPDATED_IMAGE_SMALL);
        assertThat(testCAttachment.getImageMedium()).isEqualTo(UPDATED_IMAGE_MEDIUM);
        assertThat(testCAttachment.getImageLarge()).isEqualTo(UPDATED_IMAGE_LARGE);
        assertThat(testCAttachment.getMimeType()).isEqualTo(UPDATED_MIME_TYPE);
        assertThat(testCAttachment.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testCAttachment.getUploadDir()).isEqualTo(UPDATED_UPLOAD_DIR);
        assertThat(testCAttachment.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCAttachment.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCAttachment() throws Exception {
        int databaseSizeBeforeUpdate = cAttachmentRepository.findAll().size();

        // Create the CAttachment
        CAttachmentDTO cAttachmentDTO = cAttachmentMapper.toDto(cAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCAttachmentMockMvc.perform(put("/api/c-attachments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CAttachment in the database
        List<CAttachment> cAttachmentList = cAttachmentRepository.findAll();
        assertThat(cAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCAttachment() throws Exception {
        // Initialize the database
        cAttachmentRepository.saveAndFlush(cAttachment);

        int databaseSizeBeforeDelete = cAttachmentRepository.findAll().size();

        // Delete the cAttachment
        restCAttachmentMockMvc.perform(delete("/api/c-attachments/{id}", cAttachment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CAttachment> cAttachmentList = cAttachmentRepository.findAll();
        assertThat(cAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
