package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MVendorPerformanceReport;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CBusinessCategory;
import com.bhp.opusb.repository.MVendorPerformanceReportRepository;
import com.bhp.opusb.service.MVendorPerformanceReportService;
import com.bhp.opusb.service.dto.MVendorPerformanceReportDTO;
import com.bhp.opusb.service.mapper.MVendorPerformanceReportMapper;
import com.bhp.opusb.service.dto.MVendorPerformanceReportCriteria;
import com.bhp.opusb.service.MVendorPerformanceReportQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MVendorPerformanceReportResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MVendorPerformanceReportResourceIT {

    private static final BigDecimal DEFAULT_EVALUATION_SCORE = new BigDecimal(1);
    private static final BigDecimal UPDATED_EVALUATION_SCORE = new BigDecimal(2);
    private static final BigDecimal SMALLER_EVALUATION_SCORE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_COMPLAINTS = 1;
    private static final Integer UPDATED_COMPLAINTS = 2;
    private static final Integer SMALLER_COMPLAINTS = 1 - 1;

    @Autowired
    private MVendorPerformanceReportRepository mVendorPerformanceReportRepository;

    @Autowired
    private MVendorPerformanceReportMapper mVendorPerformanceReportMapper;

    @Autowired
    private MVendorPerformanceReportService mVendorPerformanceReportService;

    @Autowired
    private MVendorPerformanceReportQueryService mVendorPerformanceReportQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMVendorPerformanceReportMockMvc;

    private MVendorPerformanceReport mVendorPerformanceReport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorPerformanceReport createEntity(EntityManager em) {
        MVendorPerformanceReport mVendorPerformanceReport = new MVendorPerformanceReport()
            .evaluationScore(DEFAULT_EVALUATION_SCORE)
            .complaints(DEFAULT_COMPLAINTS);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVendorPerformanceReport.setVendor(cVendor);
        return mVendorPerformanceReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MVendorPerformanceReport createUpdatedEntity(EntityManager em) {
        MVendorPerformanceReport mVendorPerformanceReport = new MVendorPerformanceReport()
            .evaluationScore(UPDATED_EVALUATION_SCORE)
            .complaints(UPDATED_COMPLAINTS);
        // Add required entity
        CVendor cVendor;
        if (TestUtil.findAll(em, CVendor.class).isEmpty()) {
            cVendor = CVendorResourceIT.createUpdatedEntity(em);
            em.persist(cVendor);
            em.flush();
        } else {
            cVendor = TestUtil.findAll(em, CVendor.class).get(0);
        }
        mVendorPerformanceReport.setVendor(cVendor);
        return mVendorPerformanceReport;
    }

    @BeforeEach
    public void initTest() {
        mVendorPerformanceReport = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReports() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorPerformanceReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluationScore").value(hasItem(DEFAULT_EVALUATION_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].complaints").value(hasItem(DEFAULT_COMPLAINTS)));
    }
    
    @Test
    @Transactional
    public void getMVendorPerformanceReport() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get the mVendorPerformanceReport
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports/{id}", mVendorPerformanceReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mVendorPerformanceReport.getId().intValue()))
            .andExpect(jsonPath("$.evaluationScore").value(DEFAULT_EVALUATION_SCORE.intValue()))
            .andExpect(jsonPath("$.complaints").value(DEFAULT_COMPLAINTS));
    }


    @Test
    @Transactional
    public void getMVendorPerformanceReportsByIdFiltering() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        Long id = mVendorPerformanceReport.getId();

        defaultMVendorPerformanceReportShouldBeFound("id.equals=" + id);
        defaultMVendorPerformanceReportShouldNotBeFound("id.notEquals=" + id);

        defaultMVendorPerformanceReportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMVendorPerformanceReportShouldNotBeFound("id.greaterThan=" + id);

        defaultMVendorPerformanceReportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMVendorPerformanceReportShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore equals to DEFAULT_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.equals=" + DEFAULT_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore equals to UPDATED_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.equals=" + UPDATED_EVALUATION_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore not equals to DEFAULT_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.notEquals=" + DEFAULT_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore not equals to UPDATED_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.notEquals=" + UPDATED_EVALUATION_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore in DEFAULT_EVALUATION_SCORE or UPDATED_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.in=" + DEFAULT_EVALUATION_SCORE + "," + UPDATED_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore equals to UPDATED_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.in=" + UPDATED_EVALUATION_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore is not null
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.specified=true");

        // Get all the mVendorPerformanceReportList where evaluationScore is null
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore is greater than or equal to DEFAULT_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.greaterThanOrEqual=" + DEFAULT_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore is greater than or equal to UPDATED_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.greaterThanOrEqual=" + UPDATED_EVALUATION_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore is less than or equal to DEFAULT_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.lessThanOrEqual=" + DEFAULT_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore is less than or equal to SMALLER_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.lessThanOrEqual=" + SMALLER_EVALUATION_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore is less than DEFAULT_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.lessThan=" + DEFAULT_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore is less than UPDATED_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.lessThan=" + UPDATED_EVALUATION_SCORE);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByEvaluationScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where evaluationScore is greater than DEFAULT_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldNotBeFound("evaluationScore.greaterThan=" + DEFAULT_EVALUATION_SCORE);

        // Get all the mVendorPerformanceReportList where evaluationScore is greater than SMALLER_EVALUATION_SCORE
        defaultMVendorPerformanceReportShouldBeFound("evaluationScore.greaterThan=" + SMALLER_EVALUATION_SCORE);
    }


    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints equals to DEFAULT_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.equals=" + DEFAULT_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints equals to UPDATED_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.equals=" + UPDATED_COMPLAINTS);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints not equals to DEFAULT_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.notEquals=" + DEFAULT_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints not equals to UPDATED_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.notEquals=" + UPDATED_COMPLAINTS);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsInShouldWork() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints in DEFAULT_COMPLAINTS or UPDATED_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.in=" + DEFAULT_COMPLAINTS + "," + UPDATED_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints equals to UPDATED_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.in=" + UPDATED_COMPLAINTS);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsNullOrNotNull() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints is not null
        defaultMVendorPerformanceReportShouldBeFound("complaints.specified=true");

        // Get all the mVendorPerformanceReportList where complaints is null
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.specified=false");
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints is greater than or equal to DEFAULT_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.greaterThanOrEqual=" + DEFAULT_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints is greater than or equal to UPDATED_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.greaterThanOrEqual=" + UPDATED_COMPLAINTS);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints is less than or equal to DEFAULT_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.lessThanOrEqual=" + DEFAULT_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints is less than or equal to SMALLER_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.lessThanOrEqual=" + SMALLER_COMPLAINTS);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsLessThanSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints is less than DEFAULT_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.lessThan=" + DEFAULT_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints is less than UPDATED_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.lessThan=" + UPDATED_COMPLAINTS);
    }

    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByComplaintsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);

        // Get all the mVendorPerformanceReportList where complaints is greater than DEFAULT_COMPLAINTS
        defaultMVendorPerformanceReportShouldNotBeFound("complaints.greaterThan=" + DEFAULT_COMPLAINTS);

        // Get all the mVendorPerformanceReportList where complaints is greater than SMALLER_COMPLAINTS
        defaultMVendorPerformanceReportShouldBeFound("complaints.greaterThan=" + SMALLER_COMPLAINTS);
    }


    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByVendorIsEqualToSomething() throws Exception {
        // Get already existing entity
        CVendor vendor = mVendorPerformanceReport.getVendor();
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);
        Long vendorId = vendor.getId();

        // Get all the mVendorPerformanceReportList where vendor equals to vendorId
        defaultMVendorPerformanceReportShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the mVendorPerformanceReportList where vendor equals to vendorId + 1
        defaultMVendorPerformanceReportShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }


    @Test
    @Transactional
    public void getAllMVendorPerformanceReportsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);
        CBusinessCategory businessCategory = CBusinessCategoryResourceIT.createEntity(em);
        em.persist(businessCategory);
        em.flush();
        mVendorPerformanceReport.setBusinessCategory(businessCategory);
        mVendorPerformanceReportRepository.saveAndFlush(mVendorPerformanceReport);
        Long businessCategoryId = businessCategory.getId();

        // Get all the mVendorPerformanceReportList where businessCategory equals to businessCategoryId
        defaultMVendorPerformanceReportShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the mVendorPerformanceReportList where businessCategory equals to businessCategoryId + 1
        defaultMVendorPerformanceReportShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMVendorPerformanceReportShouldBeFound(String filter) throws Exception {
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mVendorPerformanceReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].evaluationScore").value(hasItem(DEFAULT_EVALUATION_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].complaints").value(hasItem(DEFAULT_COMPLAINTS)));

        // Check, that the count call also returns 1
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMVendorPerformanceReportShouldNotBeFound(String filter) throws Exception {
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMVendorPerformanceReport() throws Exception {
        // Get the mVendorPerformanceReport
        restMVendorPerformanceReportMockMvc.perform(get("/api/m-vendor-performance-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
