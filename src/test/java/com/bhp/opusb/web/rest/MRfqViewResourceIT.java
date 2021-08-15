package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MRfqView;
import com.bhp.opusb.domain.MRfq;
import com.bhp.opusb.repository.MRfqViewRepository;
import com.bhp.opusb.service.MRfqViewService;
import com.bhp.opusb.service.dto.MRfqViewDTO;
import com.bhp.opusb.service.mapper.MRfqViewMapper;
import com.bhp.opusb.service.dto.MRfqViewCriteria;
import com.bhp.opusb.service.MRfqViewQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MRfqViewResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MRfqViewResourceIT {

    private static final String DEFAULT_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_REQUIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_REQUIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_REQUIRED = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SELECTION_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_SELECTION_METHOD = "BBBBBBBBBB";

    private static final Integer DEFAULT_JOINED_VENDOR_COUNT = 1;
    private static final Integer UPDATED_JOINED_VENDOR_COUNT = 2;
    private static final Integer SMALLER_JOINED_VENDOR_COUNT = 1 - 1;

    @Autowired
    private MRfqViewRepository mRfqViewRepository;

    @Autowired
    private MRfqViewMapper mRfqViewMapper;

    @Autowired
    private MRfqViewService mRfqViewService;

    @Autowired
    private MRfqViewQueryService mRfqViewQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMRfqViewMockMvc;

    private MRfqView mRfqView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqView createEntity(EntityManager em) {
        MRfqView mRfqView = new MRfqView()
            .documentNo(DEFAULT_DOCUMENT_NO)
            .title(DEFAULT_TITLE)
            .dateRequired(DEFAULT_DATE_REQUIRED)
            .selectionMethod(DEFAULT_SELECTION_METHOD)
            .joinedVendorCount(DEFAULT_JOINED_VENDOR_COUNT);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mRfqView.setQuotation(mRfq);
        return mRfqView;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MRfqView createUpdatedEntity(EntityManager em) {
        MRfqView mRfqView = new MRfqView()
            .documentNo(UPDATED_DOCUMENT_NO)
            .title(UPDATED_TITLE)
            .dateRequired(UPDATED_DATE_REQUIRED)
            .selectionMethod(UPDATED_SELECTION_METHOD)
            .joinedVendorCount(UPDATED_JOINED_VENDOR_COUNT);
        // Add required entity
        MRfq mRfq;
        if (TestUtil.findAll(em, MRfq.class).isEmpty()) {
            mRfq = MRfqResourceIT.createUpdatedEntity(em);
            em.persist(mRfq);
            em.flush();
        } else {
            mRfq = TestUtil.findAll(em, MRfq.class).get(0);
        }
        mRfqView.setQuotation(mRfq);
        return mRfqView;
    }

    @BeforeEach
    public void initTest() {
        mRfqView = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllMRfqViews() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqView.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].selectionMethod").value(hasItem(DEFAULT_SELECTION_METHOD)))
            .andExpect(jsonPath("$.[*].joinedVendorCount").value(hasItem(DEFAULT_JOINED_VENDOR_COUNT)));
    }
    
    @Test
    @Transactional
    public void getMRfqView() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get the mRfqView
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views/{id}", mRfqView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mRfqView.getId().intValue()))
            .andExpect(jsonPath("$.documentNo").value(DEFAULT_DOCUMENT_NO))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.dateRequired").value(DEFAULT_DATE_REQUIRED.toString()))
            .andExpect(jsonPath("$.selectionMethod").value(DEFAULT_SELECTION_METHOD))
            .andExpect(jsonPath("$.joinedVendorCount").value(DEFAULT_JOINED_VENDOR_COUNT));
    }


    @Test
    @Transactional
    public void getMRfqViewsByIdFiltering() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        Long id = mRfqView.getId();

        defaultMRfqViewShouldBeFound("id.equals=" + id);
        defaultMRfqViewShouldNotBeFound("id.notEquals=" + id);

        defaultMRfqViewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMRfqViewShouldNotBeFound("id.greaterThan=" + id);

        defaultMRfqViewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMRfqViewShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMRfqViewsByDocumentNoIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where documentNo equals to DEFAULT_DOCUMENT_NO
        defaultMRfqViewShouldBeFound("documentNo.equals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqViewList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMRfqViewShouldNotBeFound("documentNo.equals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDocumentNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where documentNo not equals to DEFAULT_DOCUMENT_NO
        defaultMRfqViewShouldNotBeFound("documentNo.notEquals=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqViewList where documentNo not equals to UPDATED_DOCUMENT_NO
        defaultMRfqViewShouldBeFound("documentNo.notEquals=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDocumentNoIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where documentNo in DEFAULT_DOCUMENT_NO or UPDATED_DOCUMENT_NO
        defaultMRfqViewShouldBeFound("documentNo.in=" + DEFAULT_DOCUMENT_NO + "," + UPDATED_DOCUMENT_NO);

        // Get all the mRfqViewList where documentNo equals to UPDATED_DOCUMENT_NO
        defaultMRfqViewShouldNotBeFound("documentNo.in=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDocumentNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where documentNo is not null
        defaultMRfqViewShouldBeFound("documentNo.specified=true");

        // Get all the mRfqViewList where documentNo is null
        defaultMRfqViewShouldNotBeFound("documentNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqViewsByDocumentNoContainsSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where documentNo contains DEFAULT_DOCUMENT_NO
        defaultMRfqViewShouldBeFound("documentNo.contains=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqViewList where documentNo contains UPDATED_DOCUMENT_NO
        defaultMRfqViewShouldNotBeFound("documentNo.contains=" + UPDATED_DOCUMENT_NO);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDocumentNoNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where documentNo does not contain DEFAULT_DOCUMENT_NO
        defaultMRfqViewShouldNotBeFound("documentNo.doesNotContain=" + DEFAULT_DOCUMENT_NO);

        // Get all the mRfqViewList where documentNo does not contain UPDATED_DOCUMENT_NO
        defaultMRfqViewShouldBeFound("documentNo.doesNotContain=" + UPDATED_DOCUMENT_NO);
    }


    @Test
    @Transactional
    public void getAllMRfqViewsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where title equals to DEFAULT_TITLE
        defaultMRfqViewShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the mRfqViewList where title equals to UPDATED_TITLE
        defaultMRfqViewShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where title not equals to DEFAULT_TITLE
        defaultMRfqViewShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the mRfqViewList where title not equals to UPDATED_TITLE
        defaultMRfqViewShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultMRfqViewShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the mRfqViewList where title equals to UPDATED_TITLE
        defaultMRfqViewShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where title is not null
        defaultMRfqViewShouldBeFound("title.specified=true");

        // Get all the mRfqViewList where title is null
        defaultMRfqViewShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqViewsByTitleContainsSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where title contains DEFAULT_TITLE
        defaultMRfqViewShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the mRfqViewList where title contains UPDATED_TITLE
        defaultMRfqViewShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where title does not contain DEFAULT_TITLE
        defaultMRfqViewShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the mRfqViewList where title does not contain UPDATED_TITLE
        defaultMRfqViewShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired equals to DEFAULT_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.equals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.equals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired not equals to DEFAULT_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.notEquals=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired not equals to UPDATED_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.notEquals=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired in DEFAULT_DATE_REQUIRED or UPDATED_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.in=" + DEFAULT_DATE_REQUIRED + "," + UPDATED_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired equals to UPDATED_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.in=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired is not null
        defaultMRfqViewShouldBeFound("dateRequired.specified=true");

        // Get all the mRfqViewList where dateRequired is null
        defaultMRfqViewShouldNotBeFound("dateRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired is greater than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.greaterThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired is greater than or equal to UPDATED_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.greaterThanOrEqual=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired is less than or equal to DEFAULT_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.lessThanOrEqual=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired is less than or equal to SMALLER_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.lessThanOrEqual=" + SMALLER_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired is less than DEFAULT_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.lessThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired is less than UPDATED_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.lessThan=" + UPDATED_DATE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByDateRequiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where dateRequired is greater than DEFAULT_DATE_REQUIRED
        defaultMRfqViewShouldNotBeFound("dateRequired.greaterThan=" + DEFAULT_DATE_REQUIRED);

        // Get all the mRfqViewList where dateRequired is greater than SMALLER_DATE_REQUIRED
        defaultMRfqViewShouldBeFound("dateRequired.greaterThan=" + SMALLER_DATE_REQUIRED);
    }


    @Test
    @Transactional
    public void getAllMRfqViewsBySelectionMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where selectionMethod equals to DEFAULT_SELECTION_METHOD
        defaultMRfqViewShouldBeFound("selectionMethod.equals=" + DEFAULT_SELECTION_METHOD);

        // Get all the mRfqViewList where selectionMethod equals to UPDATED_SELECTION_METHOD
        defaultMRfqViewShouldNotBeFound("selectionMethod.equals=" + UPDATED_SELECTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsBySelectionMethodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where selectionMethod not equals to DEFAULT_SELECTION_METHOD
        defaultMRfqViewShouldNotBeFound("selectionMethod.notEquals=" + DEFAULT_SELECTION_METHOD);

        // Get all the mRfqViewList where selectionMethod not equals to UPDATED_SELECTION_METHOD
        defaultMRfqViewShouldBeFound("selectionMethod.notEquals=" + UPDATED_SELECTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsBySelectionMethodIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where selectionMethod in DEFAULT_SELECTION_METHOD or UPDATED_SELECTION_METHOD
        defaultMRfqViewShouldBeFound("selectionMethod.in=" + DEFAULT_SELECTION_METHOD + "," + UPDATED_SELECTION_METHOD);

        // Get all the mRfqViewList where selectionMethod equals to UPDATED_SELECTION_METHOD
        defaultMRfqViewShouldNotBeFound("selectionMethod.in=" + UPDATED_SELECTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsBySelectionMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where selectionMethod is not null
        defaultMRfqViewShouldBeFound("selectionMethod.specified=true");

        // Get all the mRfqViewList where selectionMethod is null
        defaultMRfqViewShouldNotBeFound("selectionMethod.specified=false");
    }
                @Test
    @Transactional
    public void getAllMRfqViewsBySelectionMethodContainsSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where selectionMethod contains DEFAULT_SELECTION_METHOD
        defaultMRfqViewShouldBeFound("selectionMethod.contains=" + DEFAULT_SELECTION_METHOD);

        // Get all the mRfqViewList where selectionMethod contains UPDATED_SELECTION_METHOD
        defaultMRfqViewShouldNotBeFound("selectionMethod.contains=" + UPDATED_SELECTION_METHOD);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsBySelectionMethodNotContainsSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where selectionMethod does not contain DEFAULT_SELECTION_METHOD
        defaultMRfqViewShouldNotBeFound("selectionMethod.doesNotContain=" + DEFAULT_SELECTION_METHOD);

        // Get all the mRfqViewList where selectionMethod does not contain UPDATED_SELECTION_METHOD
        defaultMRfqViewShouldBeFound("selectionMethod.doesNotContain=" + UPDATED_SELECTION_METHOD);
    }


    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount equals to DEFAULT_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.equals=" + DEFAULT_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount equals to UPDATED_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.equals=" + UPDATED_JOINED_VENDOR_COUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount not equals to DEFAULT_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.notEquals=" + DEFAULT_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount not equals to UPDATED_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.notEquals=" + UPDATED_JOINED_VENDOR_COUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsInShouldWork() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount in DEFAULT_JOINED_VENDOR_COUNT or UPDATED_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.in=" + DEFAULT_JOINED_VENDOR_COUNT + "," + UPDATED_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount equals to UPDATED_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.in=" + UPDATED_JOINED_VENDOR_COUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount is not null
        defaultMRfqViewShouldBeFound("joinedVendorCount.specified=true");

        // Get all the mRfqViewList where joinedVendorCount is null
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount is greater than or equal to DEFAULT_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.greaterThanOrEqual=" + DEFAULT_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount is greater than or equal to UPDATED_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.greaterThanOrEqual=" + UPDATED_JOINED_VENDOR_COUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount is less than or equal to DEFAULT_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.lessThanOrEqual=" + DEFAULT_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount is less than or equal to SMALLER_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.lessThanOrEqual=" + SMALLER_JOINED_VENDOR_COUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsLessThanSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount is less than DEFAULT_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.lessThan=" + DEFAULT_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount is less than UPDATED_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.lessThan=" + UPDATED_JOINED_VENDOR_COUNT);
    }

    @Test
    @Transactional
    public void getAllMRfqViewsByJoinedVendorCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mRfqViewRepository.saveAndFlush(mRfqView);

        // Get all the mRfqViewList where joinedVendorCount is greater than DEFAULT_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldNotBeFound("joinedVendorCount.greaterThan=" + DEFAULT_JOINED_VENDOR_COUNT);

        // Get all the mRfqViewList where joinedVendorCount is greater than SMALLER_JOINED_VENDOR_COUNT
        defaultMRfqViewShouldBeFound("joinedVendorCount.greaterThan=" + SMALLER_JOINED_VENDOR_COUNT);
    }


    @Test
    @Transactional
    public void getAllMRfqViewsByQuotationIsEqualToSomething() throws Exception {
        // Get already existing entity
        MRfq quotation = mRfqView.getQuotation();
        mRfqViewRepository.saveAndFlush(mRfqView);
        Long quotationId = quotation.getId();

        // Get all the mRfqViewList where quotation equals to quotationId
        defaultMRfqViewShouldBeFound("quotationId.equals=" + quotationId);

        // Get all the mRfqViewList where quotation equals to quotationId + 1
        defaultMRfqViewShouldNotBeFound("quotationId.equals=" + (quotationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMRfqViewShouldBeFound(String filter) throws Exception {
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mRfqView.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNo").value(hasItem(DEFAULT_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].dateRequired").value(hasItem(DEFAULT_DATE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].selectionMethod").value(hasItem(DEFAULT_SELECTION_METHOD)))
            .andExpect(jsonPath("$.[*].joinedVendorCount").value(hasItem(DEFAULT_JOINED_VENDOR_COUNT)));

        // Check, that the count call also returns 1
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMRfqViewShouldNotBeFound(String filter) throws Exception {
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMRfqView() throws Exception {
        // Get the mRfqView
        restMRfqViewMockMvc.perform(get("/api/m-rfq-views/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
