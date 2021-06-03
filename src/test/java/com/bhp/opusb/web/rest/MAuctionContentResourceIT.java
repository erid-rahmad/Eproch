package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MAuctionContent;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.MAuctionContentRepository;
import com.bhp.opusb.service.MAuctionContentService;
import com.bhp.opusb.service.dto.MAuctionContentDTO;
import com.bhp.opusb.service.mapper.MAuctionContentMapper;
import com.bhp.opusb.service.dto.MAuctionContentCriteria;
import com.bhp.opusb.service.MAuctionContentQueryService;

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
 * Integration tests for the {@link MAuctionContentResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MAuctionContentResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MAuctionContentRepository mAuctionContentRepository;

    @Autowired
    private MAuctionContentMapper mAuctionContentMapper;

    @Autowired
    private MAuctionContentService mAuctionContentService;

    @Autowired
    private MAuctionContentQueryService mAuctionContentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMAuctionContentMockMvc;

    private MAuctionContent mAuctionContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionContent createEntity(EntityManager em) {
        MAuctionContent mAuctionContent = new MAuctionContent()
            .description(DEFAULT_DESCRIPTION)
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
        mAuctionContent.setAdOrganization(aDOrganization);
        return mAuctionContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MAuctionContent createUpdatedEntity(EntityManager em) {
        MAuctionContent mAuctionContent = new MAuctionContent()
            .description(UPDATED_DESCRIPTION)
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
        mAuctionContent.setAdOrganization(aDOrganization);
        return mAuctionContent;
    }

    @BeforeEach
    public void initTest() {
        mAuctionContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createMAuctionContent() throws Exception {
        int databaseSizeBeforeCreate = mAuctionContentRepository.findAll().size();

        // Create the MAuctionContent
        MAuctionContentDTO mAuctionContentDTO = mAuctionContentMapper.toDto(mAuctionContent);
        restMAuctionContentMockMvc.perform(post("/api/m-auction-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionContentDTO)))
            .andExpect(status().isCreated());

        // Validate the MAuctionContent in the database
        List<MAuctionContent> mAuctionContentList = mAuctionContentRepository.findAll();
        assertThat(mAuctionContentList).hasSize(databaseSizeBeforeCreate + 1);
        MAuctionContent testMAuctionContent = mAuctionContentList.get(mAuctionContentList.size() - 1);
        assertThat(testMAuctionContent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMAuctionContent.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMAuctionContent.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMAuctionContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mAuctionContentRepository.findAll().size();

        // Create the MAuctionContent with an existing ID
        mAuctionContent.setId(1L);
        MAuctionContentDTO mAuctionContentDTO = mAuctionContentMapper.toDto(mAuctionContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMAuctionContentMockMvc.perform(post("/api/m-auction-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionContent in the database
        List<MAuctionContent> mAuctionContentList = mAuctionContentRepository.findAll();
        assertThat(mAuctionContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMAuctionContents() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMAuctionContent() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get the mAuctionContent
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents/{id}", mAuctionContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mAuctionContent.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getMAuctionContentsByIdFiltering() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        Long id = mAuctionContent.getId();

        defaultMAuctionContentShouldBeFound("id.equals=" + id);
        defaultMAuctionContentShouldNotBeFound("id.notEquals=" + id);

        defaultMAuctionContentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMAuctionContentShouldNotBeFound("id.greaterThan=" + id);

        defaultMAuctionContentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMAuctionContentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMAuctionContentsByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where uid equals to DEFAULT_UID
        defaultMAuctionContentShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mAuctionContentList where uid equals to UPDATED_UID
        defaultMAuctionContentShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where uid not equals to DEFAULT_UID
        defaultMAuctionContentShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mAuctionContentList where uid not equals to UPDATED_UID
        defaultMAuctionContentShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where uid in DEFAULT_UID or UPDATED_UID
        defaultMAuctionContentShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mAuctionContentList where uid equals to UPDATED_UID
        defaultMAuctionContentShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where uid is not null
        defaultMAuctionContentShouldBeFound("uid.specified=true");

        // Get all the mAuctionContentList where uid is null
        defaultMAuctionContentShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where active equals to DEFAULT_ACTIVE
        defaultMAuctionContentShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionContentList where active equals to UPDATED_ACTIVE
        defaultMAuctionContentShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where active not equals to DEFAULT_ACTIVE
        defaultMAuctionContentShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mAuctionContentList where active not equals to UPDATED_ACTIVE
        defaultMAuctionContentShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMAuctionContentShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mAuctionContentList where active equals to UPDATED_ACTIVE
        defaultMAuctionContentShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        // Get all the mAuctionContentList where active is not null
        defaultMAuctionContentShouldBeFound("active.specified=true");

        // Get all the mAuctionContentList where active is null
        defaultMAuctionContentShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMAuctionContentsByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mAuctionContent.getAdOrganization();
        mAuctionContentRepository.saveAndFlush(mAuctionContent);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mAuctionContentList where adOrganization equals to adOrganizationId
        defaultMAuctionContentShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mAuctionContentList where adOrganization equals to adOrganizationId + 1
        defaultMAuctionContentShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMAuctionContentShouldBeFound(String filter) throws Exception {
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mAuctionContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMAuctionContentShouldNotBeFound(String filter) throws Exception {
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMAuctionContent() throws Exception {
        // Get the mAuctionContent
        restMAuctionContentMockMvc.perform(get("/api/m-auction-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMAuctionContent() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        int databaseSizeBeforeUpdate = mAuctionContentRepository.findAll().size();

        // Update the mAuctionContent
        MAuctionContent updatedMAuctionContent = mAuctionContentRepository.findById(mAuctionContent.getId()).get();
        // Disconnect from session so that the updates on updatedMAuctionContent are not directly saved in db
        em.detach(updatedMAuctionContent);
        updatedMAuctionContent
            .description(UPDATED_DESCRIPTION)
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        MAuctionContentDTO mAuctionContentDTO = mAuctionContentMapper.toDto(updatedMAuctionContent);

        restMAuctionContentMockMvc.perform(put("/api/m-auction-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionContentDTO)))
            .andExpect(status().isOk());

        // Validate the MAuctionContent in the database
        List<MAuctionContent> mAuctionContentList = mAuctionContentRepository.findAll();
        assertThat(mAuctionContentList).hasSize(databaseSizeBeforeUpdate);
        MAuctionContent testMAuctionContent = mAuctionContentList.get(mAuctionContentList.size() - 1);
        assertThat(testMAuctionContent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMAuctionContent.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMAuctionContent.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMAuctionContent() throws Exception {
        int databaseSizeBeforeUpdate = mAuctionContentRepository.findAll().size();

        // Create the MAuctionContent
        MAuctionContentDTO mAuctionContentDTO = mAuctionContentMapper.toDto(mAuctionContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMAuctionContentMockMvc.perform(put("/api/m-auction-contents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mAuctionContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MAuctionContent in the database
        List<MAuctionContent> mAuctionContentList = mAuctionContentRepository.findAll();
        assertThat(mAuctionContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMAuctionContent() throws Exception {
        // Initialize the database
        mAuctionContentRepository.saveAndFlush(mAuctionContent);

        int databaseSizeBeforeDelete = mAuctionContentRepository.findAll().size();

        // Delete the mAuctionContent
        restMAuctionContentMockMvc.perform(delete("/api/m-auction-contents/{id}", mAuctionContent.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MAuctionContent> mAuctionContentList = mAuctionContentRepository.findAll();
        assertThat(mAuctionContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
