package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CGalleryItem;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CGallery;
import com.bhp.opusb.repository.CGalleryItemRepository;
import com.bhp.opusb.service.CGalleryItemService;
import com.bhp.opusb.service.dto.CGalleryItemDTO;
import com.bhp.opusb.service.mapper.CGalleryItemMapper;
import com.bhp.opusb.service.dto.CGalleryItemCriteria;
import com.bhp.opusb.service.CGalleryItemQueryService;

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

import com.bhp.opusb.domain.enumeration.CGalleryItemType;
/**
 * Integration tests for the {@link CGalleryItemResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CGalleryItemResourceIT {

    private static final CGalleryItemType DEFAULT_TYPE = CGalleryItemType.DOCUMENT;
    private static final CGalleryItemType UPDATED_TYPE = CGalleryItemType.IMAGE;

    private static final Integer DEFAULT_SEQUENCE = 0;
    private static final Integer UPDATED_SEQUENCE = 1;
    private static final Integer SMALLER_SEQUENCE = 0 - 1;

    private static final Boolean DEFAULT_PREVIEW = false;
    private static final Boolean UPDATED_PREVIEW = true;

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CGalleryItemRepository cGalleryItemRepository;

    @Autowired
    private CGalleryItemMapper cGalleryItemMapper;

    @Autowired
    private CGalleryItemService cGalleryItemService;

    @Autowired
    private CGalleryItemQueryService cGalleryItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCGalleryItemMockMvc;

    private CGalleryItem cGalleryItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CGalleryItem createEntity(EntityManager em) {
        CGalleryItem cGalleryItem = new CGalleryItem()
            .type(DEFAULT_TYPE)
            .sequence(DEFAULT_SEQUENCE)
            .preview(DEFAULT_PREVIEW)
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        cGalleryItem.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cGalleryItem.setAdOrganization(aDOrganization);
        // Add required entity
        CGallery cGallery;
        if (TestUtil.findAll(em, CGallery.class).isEmpty()) {
            cGallery = CGalleryResourceIT.createEntity(em);
            em.persist(cGallery);
            em.flush();
        } else {
            cGallery = TestUtil.findAll(em, CGallery.class).get(0);
        }
        cGalleryItem.setCGallery(cGallery);
        return cGalleryItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CGalleryItem createUpdatedEntity(EntityManager em) {
        CGalleryItem cGalleryItem = new CGalleryItem()
            .type(UPDATED_TYPE)
            .sequence(UPDATED_SEQUENCE)
            .preview(UPDATED_PREVIEW)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        // Add required entity
        CAttachment cAttachment;
        if (TestUtil.findAll(em, CAttachment.class).isEmpty()) {
            cAttachment = CAttachmentResourceIT.createUpdatedEntity(em);
            em.persist(cAttachment);
            em.flush();
        } else {
            cAttachment = TestUtil.findAll(em, CAttachment.class).get(0);
        }
        cGalleryItem.setCAttachment(cAttachment);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        cGalleryItem.setAdOrganization(aDOrganization);
        // Add required entity
        CGallery cGallery;
        if (TestUtil.findAll(em, CGallery.class).isEmpty()) {
            cGallery = CGalleryResourceIT.createUpdatedEntity(em);
            em.persist(cGallery);
            em.flush();
        } else {
            cGallery = TestUtil.findAll(em, CGallery.class).get(0);
        }
        cGalleryItem.setCGallery(cGallery);
        return cGalleryItem;
    }

    @BeforeEach
    public void initTest() {
        cGalleryItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCGalleryItem() throws Exception {
        int databaseSizeBeforeCreate = cGalleryItemRepository.findAll().size();

        // Create the CGalleryItem
        CGalleryItemDTO cGalleryItemDTO = cGalleryItemMapper.toDto(cGalleryItem);
        restCGalleryItemMockMvc.perform(post("/api/c-gallery-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CGalleryItem in the database
        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeCreate + 1);
        CGalleryItem testCGalleryItem = cGalleryItemList.get(cGalleryItemList.size() - 1);
        assertThat(testCGalleryItem.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCGalleryItem.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testCGalleryItem.isPreview()).isEqualTo(DEFAULT_PREVIEW);
        assertThat(testCGalleryItem.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testCGalleryItem.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCGalleryItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cGalleryItemRepository.findAll().size();

        // Create the CGalleryItem with an existing ID
        cGalleryItem.setId(1L);
        CGalleryItemDTO cGalleryItemDTO = cGalleryItemMapper.toDto(cGalleryItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCGalleryItemMockMvc.perform(post("/api/c-gallery-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CGalleryItem in the database
        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cGalleryItemRepository.findAll().size();
        // set the field null
        cGalleryItem.setType(null);

        // Create the CGalleryItem, which fails.
        CGalleryItemDTO cGalleryItemDTO = cGalleryItemMapper.toDto(cGalleryItem);

        restCGalleryItemMockMvc.perform(post("/api/c-gallery-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryItemDTO)))
            .andExpect(status().isBadRequest());

        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSequenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = cGalleryItemRepository.findAll().size();
        // set the field null
        cGalleryItem.setSequence(null);

        // Create the CGalleryItem, which fails.
        CGalleryItemDTO cGalleryItemDTO = cGalleryItemMapper.toDto(cGalleryItem);

        restCGalleryItemMockMvc.perform(post("/api/c-gallery-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryItemDTO)))
            .andExpect(status().isBadRequest());

        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCGalleryItems() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cGalleryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].preview").value(hasItem(DEFAULT_PREVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCGalleryItem() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get the cGalleryItem
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items/{id}", cGalleryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cGalleryItem.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.preview").value(DEFAULT_PREVIEW.booleanValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCGalleryItemsByIdFiltering() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        Long id = cGalleryItem.getId();

        defaultCGalleryItemShouldBeFound("id.equals=" + id);
        defaultCGalleryItemShouldNotBeFound("id.notEquals=" + id);

        defaultCGalleryItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCGalleryItemShouldNotBeFound("id.greaterThan=" + id);

        defaultCGalleryItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCGalleryItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCGalleryItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where type equals to DEFAULT_TYPE
        defaultCGalleryItemShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the cGalleryItemList where type equals to UPDATED_TYPE
        defaultCGalleryItemShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where type not equals to DEFAULT_TYPE
        defaultCGalleryItemShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the cGalleryItemList where type not equals to UPDATED_TYPE
        defaultCGalleryItemShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCGalleryItemShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the cGalleryItemList where type equals to UPDATED_TYPE
        defaultCGalleryItemShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where type is not null
        defaultCGalleryItemShouldBeFound("type.specified=true");

        // Get all the cGalleryItemList where type is null
        defaultCGalleryItemShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence equals to DEFAULT_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.equals=" + DEFAULT_SEQUENCE);

        // Get all the cGalleryItemList where sequence equals to UPDATED_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.equals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence not equals to DEFAULT_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.notEquals=" + DEFAULT_SEQUENCE);

        // Get all the cGalleryItemList where sequence not equals to UPDATED_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.notEquals=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence in DEFAULT_SEQUENCE or UPDATED_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.in=" + DEFAULT_SEQUENCE + "," + UPDATED_SEQUENCE);

        // Get all the cGalleryItemList where sequence equals to UPDATED_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.in=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence is not null
        defaultCGalleryItemShouldBeFound("sequence.specified=true");

        // Get all the cGalleryItemList where sequence is null
        defaultCGalleryItemShouldNotBeFound("sequence.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence is greater than or equal to DEFAULT_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.greaterThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the cGalleryItemList where sequence is greater than or equal to UPDATED_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.greaterThanOrEqual=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence is less than or equal to DEFAULT_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.lessThanOrEqual=" + DEFAULT_SEQUENCE);

        // Get all the cGalleryItemList where sequence is less than or equal to SMALLER_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.lessThanOrEqual=" + SMALLER_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsLessThanSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence is less than DEFAULT_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.lessThan=" + DEFAULT_SEQUENCE);

        // Get all the cGalleryItemList where sequence is less than UPDATED_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.lessThan=" + UPDATED_SEQUENCE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsBySequenceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where sequence is greater than DEFAULT_SEQUENCE
        defaultCGalleryItemShouldNotBeFound("sequence.greaterThan=" + DEFAULT_SEQUENCE);

        // Get all the cGalleryItemList where sequence is greater than SMALLER_SEQUENCE
        defaultCGalleryItemShouldBeFound("sequence.greaterThan=" + SMALLER_SEQUENCE);
    }


    @Test
    @Transactional
    public void getAllCGalleryItemsByPreviewIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where preview equals to DEFAULT_PREVIEW
        defaultCGalleryItemShouldBeFound("preview.equals=" + DEFAULT_PREVIEW);

        // Get all the cGalleryItemList where preview equals to UPDATED_PREVIEW
        defaultCGalleryItemShouldNotBeFound("preview.equals=" + UPDATED_PREVIEW);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByPreviewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where preview not equals to DEFAULT_PREVIEW
        defaultCGalleryItemShouldNotBeFound("preview.notEquals=" + DEFAULT_PREVIEW);

        // Get all the cGalleryItemList where preview not equals to UPDATED_PREVIEW
        defaultCGalleryItemShouldBeFound("preview.notEquals=" + UPDATED_PREVIEW);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByPreviewIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where preview in DEFAULT_PREVIEW or UPDATED_PREVIEW
        defaultCGalleryItemShouldBeFound("preview.in=" + DEFAULT_PREVIEW + "," + UPDATED_PREVIEW);

        // Get all the cGalleryItemList where preview equals to UPDATED_PREVIEW
        defaultCGalleryItemShouldNotBeFound("preview.in=" + UPDATED_PREVIEW);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByPreviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where preview is not null
        defaultCGalleryItemShouldBeFound("preview.specified=true");

        // Get all the cGalleryItemList where preview is null
        defaultCGalleryItemShouldNotBeFound("preview.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where uid equals to DEFAULT_UID
        defaultCGalleryItemShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the cGalleryItemList where uid equals to UPDATED_UID
        defaultCGalleryItemShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where uid not equals to DEFAULT_UID
        defaultCGalleryItemShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the cGalleryItemList where uid not equals to UPDATED_UID
        defaultCGalleryItemShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where uid in DEFAULT_UID or UPDATED_UID
        defaultCGalleryItemShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the cGalleryItemList where uid equals to UPDATED_UID
        defaultCGalleryItemShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where uid is not null
        defaultCGalleryItemShouldBeFound("uid.specified=true");

        // Get all the cGalleryItemList where uid is null
        defaultCGalleryItemShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where active equals to DEFAULT_ACTIVE
        defaultCGalleryItemShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cGalleryItemList where active equals to UPDATED_ACTIVE
        defaultCGalleryItemShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where active not equals to DEFAULT_ACTIVE
        defaultCGalleryItemShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cGalleryItemList where active not equals to UPDATED_ACTIVE
        defaultCGalleryItemShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCGalleryItemShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cGalleryItemList where active equals to UPDATED_ACTIVE
        defaultCGalleryItemShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        // Get all the cGalleryItemList where active is not null
        defaultCGalleryItemShouldBeFound("active.specified=true");

        // Get all the cGalleryItemList where active is null
        defaultCGalleryItemShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllCGalleryItemsByCAttachmentIsEqualToSomething() throws Exception {
        // Get already existing entity
        CAttachment cAttachment = cGalleryItem.getCAttachment();
        cGalleryItemRepository.saveAndFlush(cGalleryItem);
        Long cAttachmentId = cAttachment.getId();

        // Get all the cGalleryItemList where cAttachment equals to cAttachmentId
        defaultCGalleryItemShouldBeFound("cAttachmentId.equals=" + cAttachmentId);

        // Get all the cGalleryItemList where cAttachment equals to cAttachmentId + 1
        defaultCGalleryItemShouldNotBeFound("cAttachmentId.equals=" + (cAttachmentId + 1));
    }


    @Test
    @Transactional
    public void getAllCGalleryItemsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = cGalleryItem.getAdOrganization();
        cGalleryItemRepository.saveAndFlush(cGalleryItem);
        Long adOrganizationId = adOrganization.getId();

        // Get all the cGalleryItemList where adOrganization equals to adOrganizationId
        defaultCGalleryItemShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the cGalleryItemList where adOrganization equals to adOrganizationId + 1
        defaultCGalleryItemShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllCGalleryItemsByCGalleryIsEqualToSomething() throws Exception {
        // Get already existing entity
        CGallery cGallery = cGalleryItem.getCGallery();
        cGalleryItemRepository.saveAndFlush(cGalleryItem);
        Long cGalleryId = cGallery.getId();

        // Get all the cGalleryItemList where cGallery equals to cGalleryId
        defaultCGalleryItemShouldBeFound("cGalleryId.equals=" + cGalleryId);

        // Get all the cGalleryItemList where cGallery equals to cGalleryId + 1
        defaultCGalleryItemShouldNotBeFound("cGalleryId.equals=" + (cGalleryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCGalleryItemShouldBeFound(String filter) throws Exception {
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cGalleryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].preview").value(hasItem(DEFAULT_PREVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCGalleryItemShouldNotBeFound(String filter) throws Exception {
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCGalleryItem() throws Exception {
        // Get the cGalleryItem
        restCGalleryItemMockMvc.perform(get("/api/c-gallery-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCGalleryItem() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        int databaseSizeBeforeUpdate = cGalleryItemRepository.findAll().size();

        // Update the cGalleryItem
        CGalleryItem updatedCGalleryItem = cGalleryItemRepository.findById(cGalleryItem.getId()).get();
        // Disconnect from session so that the updates on updatedCGalleryItem are not directly saved in db
        em.detach(updatedCGalleryItem);
        updatedCGalleryItem
            .type(UPDATED_TYPE)
            .sequence(UPDATED_SEQUENCE)
            .preview(UPDATED_PREVIEW)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        CGalleryItemDTO cGalleryItemDTO = cGalleryItemMapper.toDto(updatedCGalleryItem);

        restCGalleryItemMockMvc.perform(put("/api/c-gallery-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryItemDTO)))
            .andExpect(status().isOk());

        // Validate the CGalleryItem in the database
        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeUpdate);
        CGalleryItem testCGalleryItem = cGalleryItemList.get(cGalleryItemList.size() - 1);
        assertThat(testCGalleryItem.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCGalleryItem.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testCGalleryItem.isPreview()).isEqualTo(UPDATED_PREVIEW);
        assertThat(testCGalleryItem.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testCGalleryItem.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCGalleryItem() throws Exception {
        int databaseSizeBeforeUpdate = cGalleryItemRepository.findAll().size();

        // Create the CGalleryItem
        CGalleryItemDTO cGalleryItemDTO = cGalleryItemMapper.toDto(cGalleryItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCGalleryItemMockMvc.perform(put("/api/c-gallery-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cGalleryItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CGalleryItem in the database
        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCGalleryItem() throws Exception {
        // Initialize the database
        cGalleryItemRepository.saveAndFlush(cGalleryItem);

        int databaseSizeBeforeDelete = cGalleryItemRepository.findAll().size();

        // Delete the cGalleryItem
        restCGalleryItemMockMvc.perform(delete("/api/c-gallery-items/{id}", cGalleryItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CGalleryItem> cGalleryItemList = cGalleryItemRepository.findAll();
        assertThat(cGalleryItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
