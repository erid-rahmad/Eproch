package com.bhp.opusb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.domain.ScAccess;
import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.repository.ScAuthorityRepository;
import com.bhp.opusb.service.ScAuthorityQueryService;
import com.bhp.opusb.service.ScAuthorityService;
import com.bhp.opusb.service.dto.ScAuthorityDTO;
import com.bhp.opusb.service.mapper.ScAuthorityMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ScAuthorityResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ScAuthorityResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MASTER = false;
    private static final Boolean UPDATED_MASTER = true;

    private static final Boolean DEFAULT_ACCESS_ALL_ORGS = false;
    private static final Boolean UPDATED_ACCESS_ALL_ORGS = true;

    private static final Boolean DEFAULT_USE_USER_ORGS = false;
    private static final Boolean UPDATED_USE_USER_ORGS = true;

    @Autowired
    private ScAuthorityRepository scAuthorityRepository;

    @Autowired
    private ScAuthorityMapper scAuthorityMapper;

    @Autowired
    private ScAuthorityService scAuthorityService;

    @Autowired
    private ScAuthorityQueryService scAuthorityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScAuthorityMockMvc;

    private ScAuthority scAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAuthority createEntity(EntityManager em) {
        ScAuthority scAuthority = new ScAuthority()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .master(DEFAULT_MASTER)
            .accessAllOrgs(DEFAULT_ACCESS_ALL_ORGS)
            .useUserOrgs(DEFAULT_USE_USER_ORGS);
        // Add required entity
        Authority authority = TestUtil.findAll(em, Authority.class).get(0);
        scAuthority.setAuthority(authority);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        scAuthority.setAdOrganization(aDOrganization);
        return scAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAuthority createUpdatedEntity(EntityManager em) {
        ScAuthority scAuthority = new ScAuthority()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .master(UPDATED_MASTER)
            .accessAllOrgs(UPDATED_ACCESS_ALL_ORGS)
            .useUserOrgs(UPDATED_USE_USER_ORGS);
        // Add required entity
        Authority authority = TestUtil.findAll(em, Authority.class).get(0);
        scAuthority.setAuthority(authority);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        scAuthority.setAdOrganization(aDOrganization);
        return scAuthority;
    }

    @BeforeEach
    public void initTest() {
        scAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createScAuthority() throws Exception {
        int databaseSizeBeforeCreate = scAuthorityRepository.findAll().size();

        // Create the ScAuthority
        ScAuthorityDTO scAuthorityDTO = scAuthorityMapper.toDto(scAuthority);
        restScAuthorityMockMvc.perform(post("/api/sc-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAuthorityDTO)))
            .andExpect(status().isCreated());

        // Validate the ScAuthority in the database
        List<ScAuthority> scAuthorityList = scAuthorityRepository.findAll();
        assertThat(scAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        ScAuthority testScAuthority = scAuthorityList.get(scAuthorityList.size() - 1);
        assertThat(testScAuthority.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testScAuthority.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testScAuthority.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScAuthority.isMaster()).isEqualTo(DEFAULT_MASTER);
        assertThat(testScAuthority.isAccessAllOrgs()).isEqualTo(DEFAULT_ACCESS_ALL_ORGS);
        assertThat(testScAuthority.isUseUserOrgs()).isEqualTo(DEFAULT_USE_USER_ORGS);
    }

    @Test
    @Transactional
    public void createScAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scAuthorityRepository.findAll().size();

        // Create the ScAuthority with an existing ID
        scAuthority.setId(1L);
        ScAuthorityDTO scAuthorityDTO = scAuthorityMapper.toDto(scAuthority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScAuthorityMockMvc.perform(post("/api/sc-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScAuthority in the database
        List<ScAuthority> scAuthorityList = scAuthorityRepository.findAll();
        assertThat(scAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScAuthorities() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList
        restScAuthorityMockMvc.perform(get("/api/sc-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].master").value(hasItem(DEFAULT_MASTER.booleanValue())))
            .andExpect(jsonPath("$.[*].accessAllOrgs").value(hasItem(DEFAULT_ACCESS_ALL_ORGS.booleanValue())))
            .andExpect(jsonPath("$.[*].useUserOrgs").value(hasItem(DEFAULT_USE_USER_ORGS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getScAuthority() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get the scAuthority
        restScAuthorityMockMvc.perform(get("/api/sc-authorities/{id}", scAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scAuthority.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.master").value(DEFAULT_MASTER.booleanValue()))
            .andExpect(jsonPath("$.accessAllOrgs").value(DEFAULT_ACCESS_ALL_ORGS.booleanValue()))
            .andExpect(jsonPath("$.useUserOrgs").value(DEFAULT_USE_USER_ORGS.booleanValue()));
    }


    @Test
    @Transactional
    public void getScAuthoritiesByIdFiltering() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        Long id = scAuthority.getId();

        defaultScAuthorityShouldBeFound("id.equals=" + id);
        defaultScAuthorityShouldNotBeFound("id.notEquals=" + id);

        defaultScAuthorityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScAuthorityShouldNotBeFound("id.greaterThan=" + id);

        defaultScAuthorityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScAuthorityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScAuthoritiesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where uid equals to DEFAULT_UID
        defaultScAuthorityShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the scAuthorityList where uid equals to UPDATED_UID
        defaultScAuthorityShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where uid not equals to DEFAULT_UID
        defaultScAuthorityShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the scAuthorityList where uid not equals to UPDATED_UID
        defaultScAuthorityShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where uid in DEFAULT_UID or UPDATED_UID
        defaultScAuthorityShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the scAuthorityList where uid equals to UPDATED_UID
        defaultScAuthorityShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where uid is not null
        defaultScAuthorityShouldBeFound("uid.specified=true");

        // Get all the scAuthorityList where uid is null
        defaultScAuthorityShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where active equals to DEFAULT_ACTIVE
        defaultScAuthorityShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the scAuthorityList where active equals to UPDATED_ACTIVE
        defaultScAuthorityShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where active not equals to DEFAULT_ACTIVE
        defaultScAuthorityShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the scAuthorityList where active not equals to UPDATED_ACTIVE
        defaultScAuthorityShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultScAuthorityShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the scAuthorityList where active equals to UPDATED_ACTIVE
        defaultScAuthorityShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where active is not null
        defaultScAuthorityShouldBeFound("active.specified=true");

        // Get all the scAuthorityList where active is null
        defaultScAuthorityShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where description equals to DEFAULT_DESCRIPTION
        defaultScAuthorityShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the scAuthorityList where description equals to UPDATED_DESCRIPTION
        defaultScAuthorityShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where description not equals to DEFAULT_DESCRIPTION
        defaultScAuthorityShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the scAuthorityList where description not equals to UPDATED_DESCRIPTION
        defaultScAuthorityShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultScAuthorityShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the scAuthorityList where description equals to UPDATED_DESCRIPTION
        defaultScAuthorityShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where description is not null
        defaultScAuthorityShouldBeFound("description.specified=true");

        // Get all the scAuthorityList where description is null
        defaultScAuthorityShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllScAuthoritiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where description contains DEFAULT_DESCRIPTION
        defaultScAuthorityShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the scAuthorityList where description contains UPDATED_DESCRIPTION
        defaultScAuthorityShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where description does not contain DEFAULT_DESCRIPTION
        defaultScAuthorityShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the scAuthorityList where description does not contain UPDATED_DESCRIPTION
        defaultScAuthorityShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllScAuthoritiesByMasterIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where master equals to DEFAULT_MASTER
        defaultScAuthorityShouldBeFound("master.equals=" + DEFAULT_MASTER);

        // Get all the scAuthorityList where master equals to UPDATED_MASTER
        defaultScAuthorityShouldNotBeFound("master.equals=" + UPDATED_MASTER);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByMasterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where master not equals to DEFAULT_MASTER
        defaultScAuthorityShouldNotBeFound("master.notEquals=" + DEFAULT_MASTER);

        // Get all the scAuthorityList where master not equals to UPDATED_MASTER
        defaultScAuthorityShouldBeFound("master.notEquals=" + UPDATED_MASTER);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByMasterIsInShouldWork() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where master in DEFAULT_MASTER or UPDATED_MASTER
        defaultScAuthorityShouldBeFound("master.in=" + DEFAULT_MASTER + "," + UPDATED_MASTER);

        // Get all the scAuthorityList where master equals to UPDATED_MASTER
        defaultScAuthorityShouldNotBeFound("master.in=" + UPDATED_MASTER);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByMasterIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where master is not null
        defaultScAuthorityShouldBeFound("master.specified=true");

        // Get all the scAuthorityList where master is null
        defaultScAuthorityShouldNotBeFound("master.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByAccessAllOrgsIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where accessAllOrgs equals to DEFAULT_ACCESS_ALL_ORGS
        defaultScAuthorityShouldBeFound("accessAllOrgs.equals=" + DEFAULT_ACCESS_ALL_ORGS);

        // Get all the scAuthorityList where accessAllOrgs equals to UPDATED_ACCESS_ALL_ORGS
        defaultScAuthorityShouldNotBeFound("accessAllOrgs.equals=" + UPDATED_ACCESS_ALL_ORGS);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByAccessAllOrgsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where accessAllOrgs not equals to DEFAULT_ACCESS_ALL_ORGS
        defaultScAuthorityShouldNotBeFound("accessAllOrgs.notEquals=" + DEFAULT_ACCESS_ALL_ORGS);

        // Get all the scAuthorityList where accessAllOrgs not equals to UPDATED_ACCESS_ALL_ORGS
        defaultScAuthorityShouldBeFound("accessAllOrgs.notEquals=" + UPDATED_ACCESS_ALL_ORGS);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByAccessAllOrgsIsInShouldWork() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where accessAllOrgs in DEFAULT_ACCESS_ALL_ORGS or UPDATED_ACCESS_ALL_ORGS
        defaultScAuthorityShouldBeFound("accessAllOrgs.in=" + DEFAULT_ACCESS_ALL_ORGS + "," + UPDATED_ACCESS_ALL_ORGS);

        // Get all the scAuthorityList where accessAllOrgs equals to UPDATED_ACCESS_ALL_ORGS
        defaultScAuthorityShouldNotBeFound("accessAllOrgs.in=" + UPDATED_ACCESS_ALL_ORGS);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByAccessAllOrgsIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where accessAllOrgs is not null
        defaultScAuthorityShouldBeFound("accessAllOrgs.specified=true");

        // Get all the scAuthorityList where accessAllOrgs is null
        defaultScAuthorityShouldNotBeFound("accessAllOrgs.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUseUserOrgsIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where useUserOrgs equals to DEFAULT_USE_USER_ORGS
        defaultScAuthorityShouldBeFound("useUserOrgs.equals=" + DEFAULT_USE_USER_ORGS);

        // Get all the scAuthorityList where useUserOrgs equals to UPDATED_USE_USER_ORGS
        defaultScAuthorityShouldNotBeFound("useUserOrgs.equals=" + UPDATED_USE_USER_ORGS);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUseUserOrgsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where useUserOrgs not equals to DEFAULT_USE_USER_ORGS
        defaultScAuthorityShouldNotBeFound("useUserOrgs.notEquals=" + DEFAULT_USE_USER_ORGS);

        // Get all the scAuthorityList where useUserOrgs not equals to UPDATED_USE_USER_ORGS
        defaultScAuthorityShouldBeFound("useUserOrgs.notEquals=" + UPDATED_USE_USER_ORGS);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUseUserOrgsIsInShouldWork() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where useUserOrgs in DEFAULT_USE_USER_ORGS or UPDATED_USE_USER_ORGS
        defaultScAuthorityShouldBeFound("useUserOrgs.in=" + DEFAULT_USE_USER_ORGS + "," + UPDATED_USE_USER_ORGS);

        // Get all the scAuthorityList where useUserOrgs equals to UPDATED_USE_USER_ORGS
        defaultScAuthorityShouldNotBeFound("useUserOrgs.in=" + UPDATED_USE_USER_ORGS);
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByUseUserOrgsIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        // Get all the scAuthorityList where useUserOrgs is not null
        defaultScAuthorityShouldBeFound("useUserOrgs.specified=true");

        // Get all the scAuthorityList where useUserOrgs is null
        defaultScAuthorityShouldNotBeFound("useUserOrgs.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAuthoritiesByAuthorityIsEqualToSomething() throws Exception {
        // Get already existing entity
        Authority authority = scAuthority.getAuthority();
        scAuthorityRepository.saveAndFlush(scAuthority);
        String authorityId = authority.getName();

        // Get all the scAuthorityList where authority equals to authorityId
        defaultScAuthorityShouldBeFound("authorityId.equals=" + authorityId);

        // Get all the scAuthorityList where authority equals to authorityId + 1
        defaultScAuthorityShouldNotBeFound("authorityId.equals=" + (authorityId + 1));
    }


    @Test
    @Transactional
    public void getAllScAuthoritiesByScAccessIsEqualToSomething() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);
        ScAccess scAccess = ScAccessResourceIT.createEntity(em);
        em.persist(scAccess);
        em.flush();
        scAuthority.addScAccess(scAccess);
        scAuthorityRepository.saveAndFlush(scAuthority);
        Long scAccessId = scAccess.getId();

        // Get all the scAuthorityList where scAccess equals to scAccessId
        defaultScAuthorityShouldBeFound("scAccessId.equals=" + scAccessId);

        // Get all the scAuthorityList where scAccess equals to scAccessId + 1
        defaultScAuthorityShouldNotBeFound("scAccessId.equals=" + (scAccessId + 1));
    }


    @Test
    @Transactional
    public void getAllScAuthoritiesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = scAuthority.getAdOrganization();
        scAuthorityRepository.saveAndFlush(scAuthority);
        Long adOrganizationId = adOrganization.getId();

        // Get all the scAuthorityList where adOrganization equals to adOrganizationId
        defaultScAuthorityShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the scAuthorityList where adOrganization equals to adOrganizationId + 1
        defaultScAuthorityShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScAuthorityShouldBeFound(String filter) throws Exception {
        restScAuthorityMockMvc.perform(get("/api/sc-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].master").value(hasItem(DEFAULT_MASTER.booleanValue())))
            .andExpect(jsonPath("$.[*].accessAllOrgs").value(hasItem(DEFAULT_ACCESS_ALL_ORGS.booleanValue())))
            .andExpect(jsonPath("$.[*].useUserOrgs").value(hasItem(DEFAULT_USE_USER_ORGS.booleanValue())));

        // Check, that the count call also returns 1
        restScAuthorityMockMvc.perform(get("/api/sc-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScAuthorityShouldNotBeFound(String filter) throws Exception {
        restScAuthorityMockMvc.perform(get("/api/sc-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScAuthorityMockMvc.perform(get("/api/sc-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScAuthority() throws Exception {
        // Get the scAuthority
        restScAuthorityMockMvc.perform(get("/api/sc-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScAuthority() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        int databaseSizeBeforeUpdate = scAuthorityRepository.findAll().size();

        // Update the scAuthority
        ScAuthority updatedScAuthority = scAuthorityRepository.findById(scAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedScAuthority are not directly saved in db
        em.detach(updatedScAuthority);
        updatedScAuthority
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .master(UPDATED_MASTER)
            .accessAllOrgs(UPDATED_ACCESS_ALL_ORGS)
            .useUserOrgs(UPDATED_USE_USER_ORGS);
        ScAuthorityDTO scAuthorityDTO = scAuthorityMapper.toDto(updatedScAuthority);

        restScAuthorityMockMvc.perform(put("/api/sc-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAuthorityDTO)))
            .andExpect(status().isOk());

        // Validate the ScAuthority in the database
        List<ScAuthority> scAuthorityList = scAuthorityRepository.findAll();
        assertThat(scAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ScAuthority testScAuthority = scAuthorityList.get(scAuthorityList.size() - 1);
        assertThat(testScAuthority.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testScAuthority.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testScAuthority.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScAuthority.isMaster()).isEqualTo(UPDATED_MASTER);
        assertThat(testScAuthority.isAccessAllOrgs()).isEqualTo(UPDATED_ACCESS_ALL_ORGS);
        assertThat(testScAuthority.isUseUserOrgs()).isEqualTo(UPDATED_USE_USER_ORGS);
    }

    @Test
    @Transactional
    public void updateNonExistingScAuthority() throws Exception {
        int databaseSizeBeforeUpdate = scAuthorityRepository.findAll().size();

        // Create the ScAuthority
        ScAuthorityDTO scAuthorityDTO = scAuthorityMapper.toDto(scAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScAuthorityMockMvc.perform(put("/api/sc-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScAuthority in the database
        List<ScAuthority> scAuthorityList = scAuthorityRepository.findAll();
        assertThat(scAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScAuthority() throws Exception {
        // Initialize the database
        scAuthorityRepository.saveAndFlush(scAuthority);

        int databaseSizeBeforeDelete = scAuthorityRepository.findAll().size();

        // Delete the scAuthority
        restScAuthorityMockMvc.perform(delete("/api/sc-authorities/{id}", scAuthority.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScAuthority> scAuthorityList = scAuthorityRepository.findAll();
        assertThat(scAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
