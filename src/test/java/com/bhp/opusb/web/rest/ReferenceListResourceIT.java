package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ReferenceList;
import com.bhp.opusb.domain.Reference;
import com.bhp.opusb.repository.ReferenceListRepository;
import com.bhp.opusb.service.ReferenceListService;
import com.bhp.opusb.service.dto.ReferenceListDTO;
import com.bhp.opusb.service.mapper.ReferenceListMapper;
import com.bhp.opusb.service.dto.ReferenceListCriteria;
import com.bhp.opusb.service.ReferenceListQueryService;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReferenceListResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ReferenceListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ReferenceListRepository referenceListRepository;

    @Autowired
    private ReferenceListMapper referenceListMapper;

    @Autowired
    private ReferenceListService referenceListService;

    @Autowired
    private ReferenceListQueryService referenceListQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReferenceListMockMvc;

    private ReferenceList referenceList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceList createEntity(EntityManager em) {
        ReferenceList referenceList = new ReferenceList()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .value(DEFAULT_VALUE);
        return referenceList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReferenceList createUpdatedEntity(EntityManager em) {
        ReferenceList referenceList = new ReferenceList()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
        return referenceList;
    }

    @BeforeEach
    public void initTest() {
        referenceList = createEntity(em);
    }

    @Test
    @Transactional
    public void createReferenceList() throws Exception {
        int databaseSizeBeforeCreate = referenceListRepository.findAll().size();

        // Create the ReferenceList
        ReferenceListDTO referenceListDTO = referenceListMapper.toDto(referenceList);
        restReferenceListMockMvc.perform(post("/api/reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceListDTO)))
            .andExpect(status().isCreated());

        // Validate the ReferenceList in the database
        List<ReferenceList> referenceListList = referenceListRepository.findAll();
        assertThat(referenceListList).hasSize(databaseSizeBeforeCreate + 1);
        ReferenceList testReferenceList = referenceListList.get(referenceListList.size() - 1);
        assertThat(testReferenceList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testReferenceList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testReferenceList.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createReferenceListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = referenceListRepository.findAll().size();

        // Create the ReferenceList with an existing ID
        referenceList.setId(1L);
        ReferenceListDTO referenceListDTO = referenceListMapper.toDto(referenceList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReferenceListMockMvc.perform(post("/api/reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenceList in the database
        List<ReferenceList> referenceListList = referenceListRepository.findAll();
        assertThat(referenceListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReferenceLists() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList
        restReferenceListMockMvc.perform(get("/api/reference-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getReferenceList() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get the referenceList
        restReferenceListMockMvc.perform(get("/api/reference-lists/{id}", referenceList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(referenceList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }


    @Test
    @Transactional
    public void getReferenceListsByIdFiltering() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        Long id = referenceList.getId();

        defaultReferenceListShouldBeFound("id.equals=" + id);
        defaultReferenceListShouldNotBeFound("id.notEquals=" + id);

        defaultReferenceListShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultReferenceListShouldNotBeFound("id.greaterThan=" + id);

        defaultReferenceListShouldBeFound("id.lessThanOrEqual=" + id);
        defaultReferenceListShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllReferenceListsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where name equals to DEFAULT_NAME
        defaultReferenceListShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the referenceListList where name equals to UPDATED_NAME
        defaultReferenceListShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where name not equals to DEFAULT_NAME
        defaultReferenceListShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the referenceListList where name not equals to UPDATED_NAME
        defaultReferenceListShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where name in DEFAULT_NAME or UPDATED_NAME
        defaultReferenceListShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the referenceListList where name equals to UPDATED_NAME
        defaultReferenceListShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where name is not null
        defaultReferenceListShouldBeFound("name.specified=true");

        // Get all the referenceListList where name is null
        defaultReferenceListShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllReferenceListsByNameContainsSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where name contains DEFAULT_NAME
        defaultReferenceListShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the referenceListList where name contains UPDATED_NAME
        defaultReferenceListShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where name does not contain DEFAULT_NAME
        defaultReferenceListShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the referenceListList where name does not contain UPDATED_NAME
        defaultReferenceListShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllReferenceListsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where description equals to DEFAULT_DESCRIPTION
        defaultReferenceListShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the referenceListList where description equals to UPDATED_DESCRIPTION
        defaultReferenceListShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where description not equals to DEFAULT_DESCRIPTION
        defaultReferenceListShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the referenceListList where description not equals to UPDATED_DESCRIPTION
        defaultReferenceListShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultReferenceListShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the referenceListList where description equals to UPDATED_DESCRIPTION
        defaultReferenceListShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where description is not null
        defaultReferenceListShouldBeFound("description.specified=true");

        // Get all the referenceListList where description is null
        defaultReferenceListShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllReferenceListsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where description contains DEFAULT_DESCRIPTION
        defaultReferenceListShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the referenceListList where description contains UPDATED_DESCRIPTION
        defaultReferenceListShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where description does not contain DEFAULT_DESCRIPTION
        defaultReferenceListShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the referenceListList where description does not contain UPDATED_DESCRIPTION
        defaultReferenceListShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllReferenceListsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where value equals to DEFAULT_VALUE
        defaultReferenceListShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the referenceListList where value equals to UPDATED_VALUE
        defaultReferenceListShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where value not equals to DEFAULT_VALUE
        defaultReferenceListShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the referenceListList where value not equals to UPDATED_VALUE
        defaultReferenceListShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultReferenceListShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the referenceListList where value equals to UPDATED_VALUE
        defaultReferenceListShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where value is not null
        defaultReferenceListShouldBeFound("value.specified=true");

        // Get all the referenceListList where value is null
        defaultReferenceListShouldNotBeFound("value.specified=false");
    }
                @Test
    @Transactional
    public void getAllReferenceListsByValueContainsSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where value contains DEFAULT_VALUE
        defaultReferenceListShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the referenceListList where value contains UPDATED_VALUE
        defaultReferenceListShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllReferenceListsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        // Get all the referenceListList where value does not contain DEFAULT_VALUE
        defaultReferenceListShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the referenceListList where value does not contain UPDATED_VALUE
        defaultReferenceListShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }


    @Test
    @Transactional
    public void getAllReferenceListsByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);
        Reference reference = ReferenceResourceIT.createEntity(em);
        em.persist(reference);
        em.flush();
        referenceList.setReference(reference);
        referenceListRepository.saveAndFlush(referenceList);
        Long referenceId = reference.getId();

        // Get all the referenceListList where reference equals to referenceId
        defaultReferenceListShouldBeFound("referenceId.equals=" + referenceId);

        // Get all the referenceListList where reference equals to referenceId + 1
        defaultReferenceListShouldNotBeFound("referenceId.equals=" + (referenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReferenceListShouldBeFound(String filter) throws Exception {
        restReferenceListMockMvc.perform(get("/api/reference-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(referenceList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restReferenceListMockMvc.perform(get("/api/reference-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReferenceListShouldNotBeFound(String filter) throws Exception {
        restReferenceListMockMvc.perform(get("/api/reference-lists?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReferenceListMockMvc.perform(get("/api/reference-lists/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReferenceList() throws Exception {
        // Get the referenceList
        restReferenceListMockMvc.perform(get("/api/reference-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReferenceList() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        int databaseSizeBeforeUpdate = referenceListRepository.findAll().size();

        // Update the referenceList
        ReferenceList updatedReferenceList = referenceListRepository.findById(referenceList.getId()).get();
        // Disconnect from session so that the updates on updatedReferenceList are not directly saved in db
        em.detach(updatedReferenceList);
        updatedReferenceList
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .value(UPDATED_VALUE);
        ReferenceListDTO referenceListDTO = referenceListMapper.toDto(updatedReferenceList);

        restReferenceListMockMvc.perform(put("/api/reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceListDTO)))
            .andExpect(status().isOk());

        // Validate the ReferenceList in the database
        List<ReferenceList> referenceListList = referenceListRepository.findAll();
        assertThat(referenceListList).hasSize(databaseSizeBeforeUpdate);
        ReferenceList testReferenceList = referenceListList.get(referenceListList.size() - 1);
        assertThat(testReferenceList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testReferenceList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testReferenceList.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingReferenceList() throws Exception {
        int databaseSizeBeforeUpdate = referenceListRepository.findAll().size();

        // Create the ReferenceList
        ReferenceListDTO referenceListDTO = referenceListMapper.toDto(referenceList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReferenceListMockMvc.perform(put("/api/reference-lists")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(referenceListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReferenceList in the database
        List<ReferenceList> referenceListList = referenceListRepository.findAll();
        assertThat(referenceListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReferenceList() throws Exception {
        // Initialize the database
        referenceListRepository.saveAndFlush(referenceList);

        int databaseSizeBeforeDelete = referenceListRepository.findAll().size();

        // Delete the referenceList
        restReferenceListMockMvc.perform(delete("/api/reference-lists/{id}", referenceList.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReferenceList> referenceListList = referenceListRepository.findAll();
        assertThat(referenceListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
