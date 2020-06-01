package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdValidationRule;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.repository.AdValidationRuleRepository;
import com.bhp.opusb.service.AdValidationRuleService;
import com.bhp.opusb.service.dto.AdValidationRuleDTO;
import com.bhp.opusb.service.mapper.AdValidationRuleMapper;
import com.bhp.opusb.service.dto.AdValidationRuleCriteria;
import com.bhp.opusb.service.AdValidationRuleQueryService;

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
 * Integration tests for the {@link AdValidationRuleResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdValidationRuleResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    @Autowired
    private AdValidationRuleRepository adValidationRuleRepository;

    @Autowired
    private AdValidationRuleMapper adValidationRuleMapper;

    @Autowired
    private AdValidationRuleService adValidationRuleService;

    @Autowired
    private AdValidationRuleQueryService adValidationRuleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdValidationRuleMockMvc;

    private AdValidationRule adValidationRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdValidationRule createEntity(EntityManager em) {
        AdValidationRule adValidationRule = new AdValidationRule()
            .uid(DEFAULT_UID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .query(DEFAULT_QUERY);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adValidationRule.setAdOrganization(aDOrganization);
        return adValidationRule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdValidationRule createUpdatedEntity(EntityManager em) {
        AdValidationRule adValidationRule = new AdValidationRule()
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .query(UPDATED_QUERY);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        adValidationRule.setAdOrganization(aDOrganization);
        return adValidationRule;
    }

    @BeforeEach
    public void initTest() {
        adValidationRule = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdValidationRule() throws Exception {
        int databaseSizeBeforeCreate = adValidationRuleRepository.findAll().size();

        // Create the AdValidationRule
        AdValidationRuleDTO adValidationRuleDTO = adValidationRuleMapper.toDto(adValidationRule);
        restAdValidationRuleMockMvc.perform(post("/api/ad-validation-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adValidationRuleDTO)))
            .andExpect(status().isCreated());

        // Validate the AdValidationRule in the database
        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeCreate + 1);
        AdValidationRule testAdValidationRule = adValidationRuleList.get(adValidationRuleList.size() - 1);
        assertThat(testAdValidationRule.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdValidationRule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAdValidationRule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAdValidationRule.getQuery()).isEqualTo(DEFAULT_QUERY);
    }

    @Test
    @Transactional
    public void createAdValidationRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adValidationRuleRepository.findAll().size();

        // Create the AdValidationRule with an existing ID
        adValidationRule.setId(1L);
        AdValidationRuleDTO adValidationRuleDTO = adValidationRuleMapper.toDto(adValidationRule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdValidationRuleMockMvc.perform(post("/api/ad-validation-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adValidationRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdValidationRule in the database
        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = adValidationRuleRepository.findAll().size();
        // set the field null
        adValidationRule.setUid(null);

        // Create the AdValidationRule, which fails.
        AdValidationRuleDTO adValidationRuleDTO = adValidationRuleMapper.toDto(adValidationRule);

        restAdValidationRuleMockMvc.perform(post("/api/ad-validation-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adValidationRuleDTO)))
            .andExpect(status().isBadRequest());

        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adValidationRuleRepository.findAll().size();
        // set the field null
        adValidationRule.setName(null);

        // Create the AdValidationRule, which fails.
        AdValidationRuleDTO adValidationRuleDTO = adValidationRuleMapper.toDto(adValidationRule);

        restAdValidationRuleMockMvc.perform(post("/api/ad-validation-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adValidationRuleDTO)))
            .andExpect(status().isBadRequest());

        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdValidationRules() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adValidationRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)));
    }
    
    @Test
    @Transactional
    public void getAdValidationRule() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get the adValidationRule
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules/{id}", adValidationRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adValidationRule.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY));
    }


    @Test
    @Transactional
    public void getAdValidationRulesByIdFiltering() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        Long id = adValidationRule.getId();

        defaultAdValidationRuleShouldBeFound("id.equals=" + id);
        defaultAdValidationRuleShouldNotBeFound("id.notEquals=" + id);

        defaultAdValidationRuleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdValidationRuleShouldNotBeFound("id.greaterThan=" + id);

        defaultAdValidationRuleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdValidationRuleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdValidationRulesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where uid equals to DEFAULT_UID
        defaultAdValidationRuleShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adValidationRuleList where uid equals to UPDATED_UID
        defaultAdValidationRuleShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where uid not equals to DEFAULT_UID
        defaultAdValidationRuleShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adValidationRuleList where uid not equals to UPDATED_UID
        defaultAdValidationRuleShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdValidationRuleShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adValidationRuleList where uid equals to UPDATED_UID
        defaultAdValidationRuleShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where uid is not null
        defaultAdValidationRuleShouldBeFound("uid.specified=true");

        // Get all the adValidationRuleList where uid is null
        defaultAdValidationRuleShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where name equals to DEFAULT_NAME
        defaultAdValidationRuleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the adValidationRuleList where name equals to UPDATED_NAME
        defaultAdValidationRuleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where name not equals to DEFAULT_NAME
        defaultAdValidationRuleShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the adValidationRuleList where name not equals to UPDATED_NAME
        defaultAdValidationRuleShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAdValidationRuleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the adValidationRuleList where name equals to UPDATED_NAME
        defaultAdValidationRuleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where name is not null
        defaultAdValidationRuleShouldBeFound("name.specified=true");

        // Get all the adValidationRuleList where name is null
        defaultAdValidationRuleShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdValidationRulesByNameContainsSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where name contains DEFAULT_NAME
        defaultAdValidationRuleShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the adValidationRuleList where name contains UPDATED_NAME
        defaultAdValidationRuleShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where name does not contain DEFAULT_NAME
        defaultAdValidationRuleShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the adValidationRuleList where name does not contain UPDATED_NAME
        defaultAdValidationRuleShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAdValidationRulesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where description equals to DEFAULT_DESCRIPTION
        defaultAdValidationRuleShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the adValidationRuleList where description equals to UPDATED_DESCRIPTION
        defaultAdValidationRuleShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where description not equals to DEFAULT_DESCRIPTION
        defaultAdValidationRuleShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the adValidationRuleList where description not equals to UPDATED_DESCRIPTION
        defaultAdValidationRuleShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAdValidationRuleShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the adValidationRuleList where description equals to UPDATED_DESCRIPTION
        defaultAdValidationRuleShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where description is not null
        defaultAdValidationRuleShouldBeFound("description.specified=true");

        // Get all the adValidationRuleList where description is null
        defaultAdValidationRuleShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdValidationRulesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where description contains DEFAULT_DESCRIPTION
        defaultAdValidationRuleShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the adValidationRuleList where description contains UPDATED_DESCRIPTION
        defaultAdValidationRuleShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where description does not contain DEFAULT_DESCRIPTION
        defaultAdValidationRuleShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the adValidationRuleList where description does not contain UPDATED_DESCRIPTION
        defaultAdValidationRuleShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllAdValidationRulesByQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where query equals to DEFAULT_QUERY
        defaultAdValidationRuleShouldBeFound("query.equals=" + DEFAULT_QUERY);

        // Get all the adValidationRuleList where query equals to UPDATED_QUERY
        defaultAdValidationRuleShouldNotBeFound("query.equals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where query not equals to DEFAULT_QUERY
        defaultAdValidationRuleShouldNotBeFound("query.notEquals=" + DEFAULT_QUERY);

        // Get all the adValidationRuleList where query not equals to UPDATED_QUERY
        defaultAdValidationRuleShouldBeFound("query.notEquals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByQueryIsInShouldWork() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where query in DEFAULT_QUERY or UPDATED_QUERY
        defaultAdValidationRuleShouldBeFound("query.in=" + DEFAULT_QUERY + "," + UPDATED_QUERY);

        // Get all the adValidationRuleList where query equals to UPDATED_QUERY
        defaultAdValidationRuleShouldNotBeFound("query.in=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where query is not null
        defaultAdValidationRuleShouldBeFound("query.specified=true");

        // Get all the adValidationRuleList where query is null
        defaultAdValidationRuleShouldNotBeFound("query.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdValidationRulesByQueryContainsSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where query contains DEFAULT_QUERY
        defaultAdValidationRuleShouldBeFound("query.contains=" + DEFAULT_QUERY);

        // Get all the adValidationRuleList where query contains UPDATED_QUERY
        defaultAdValidationRuleShouldNotBeFound("query.contains=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllAdValidationRulesByQueryNotContainsSomething() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        // Get all the adValidationRuleList where query does not contain DEFAULT_QUERY
        defaultAdValidationRuleShouldNotBeFound("query.doesNotContain=" + DEFAULT_QUERY);

        // Get all the adValidationRuleList where query does not contain UPDATED_QUERY
        defaultAdValidationRuleShouldBeFound("query.doesNotContain=" + UPDATED_QUERY);
    }


    @Test
    @Transactional
    public void getAllAdValidationRulesByAdValidationRuleIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adValidationRule.getAdOrganization();
        adValidationRuleRepository.saveAndFlush(adValidationRule);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adValidationRuleList where adValidationRule equals to adValidationRuleId
        defaultAdValidationRuleShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adValidationRuleList where adValidationRule equals to adValidationRuleId + 1
        defaultAdValidationRuleShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdValidationRuleShouldBeFound(String filter) throws Exception {
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adValidationRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)));

        // Check, that the count call also returns 1
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdValidationRuleShouldNotBeFound(String filter) throws Exception {
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdValidationRule() throws Exception {
        // Get the adValidationRule
        restAdValidationRuleMockMvc.perform(get("/api/ad-validation-rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdValidationRule() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        int databaseSizeBeforeUpdate = adValidationRuleRepository.findAll().size();

        // Update the adValidationRule
        AdValidationRule updatedAdValidationRule = adValidationRuleRepository.findById(adValidationRule.getId()).get();
        // Disconnect from session so that the updates on updatedAdValidationRule are not directly saved in db
        em.detach(updatedAdValidationRule);
        updatedAdValidationRule
            .uid(UPDATED_UID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .query(UPDATED_QUERY);
        AdValidationRuleDTO adValidationRuleDTO = adValidationRuleMapper.toDto(updatedAdValidationRule);

        restAdValidationRuleMockMvc.perform(put("/api/ad-validation-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adValidationRuleDTO)))
            .andExpect(status().isOk());

        // Validate the AdValidationRule in the database
        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeUpdate);
        AdValidationRule testAdValidationRule = adValidationRuleList.get(adValidationRuleList.size() - 1);
        assertThat(testAdValidationRule.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdValidationRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAdValidationRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAdValidationRule.getQuery()).isEqualTo(UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void updateNonExistingAdValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = adValidationRuleRepository.findAll().size();

        // Create the AdValidationRule
        AdValidationRuleDTO adValidationRuleDTO = adValidationRuleMapper.toDto(adValidationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdValidationRuleMockMvc.perform(put("/api/ad-validation-rules")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adValidationRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdValidationRule in the database
        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdValidationRule() throws Exception {
        // Initialize the database
        adValidationRuleRepository.saveAndFlush(adValidationRule);

        int databaseSizeBeforeDelete = adValidationRuleRepository.findAll().size();

        // Delete the adValidationRule
        restAdValidationRuleMockMvc.perform(delete("/api/ad-validation-rules/{id}", adValidationRule.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdValidationRule> adValidationRuleList = adValidationRuleRepository.findAll();
        assertThat(adValidationRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
