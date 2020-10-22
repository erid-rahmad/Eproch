package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.AdUserAuthority;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.repository.AdUserAuthorityRepository;
import com.bhp.opusb.service.AdUserAuthorityService;
import com.bhp.opusb.service.dto.AdUserAuthorityDTO;
import com.bhp.opusb.service.mapper.AdUserAuthorityMapper;
import com.bhp.opusb.service.dto.AdUserAuthorityCriteria;
import com.bhp.opusb.service.AdUserAuthorityQueryService;

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
 * Integration tests for the {@link AdUserAuthorityResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdUserAuthorityResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AdUserAuthorityRepository adUserAuthorityRepository;

    @Autowired
    private AdUserAuthorityMapper adUserAuthorityMapper;

    @Autowired
    private AdUserAuthorityService adUserAuthorityService;

    @Autowired
    private AdUserAuthorityQueryService adUserAuthorityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdUserAuthorityMockMvc;

    private AdUserAuthority adUserAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdUserAuthority createEntity(EntityManager em) {
        AdUserAuthority adUserAuthority = new AdUserAuthority()
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
        adUserAuthority.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        adUserAuthority.setUser(adUser);
        // Add required entity
        ScAuthority scAuthority;
        if (TestUtil.findAll(em, ScAuthority.class).isEmpty()) {
            scAuthority = ScAuthorityResourceIT.createEntity(em);
            em.persist(scAuthority);
            em.flush();
        } else {
            scAuthority = TestUtil.findAll(em, ScAuthority.class).get(0);
        }
        adUserAuthority.setAuthority(scAuthority);
        return adUserAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdUserAuthority createUpdatedEntity(EntityManager em) {
        AdUserAuthority adUserAuthority = new AdUserAuthority()
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
        adUserAuthority.setAdOrganization(aDOrganization);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        adUserAuthority.setUser(adUser);
        // Add required entity
        ScAuthority scAuthority;
        if (TestUtil.findAll(em, ScAuthority.class).isEmpty()) {
            scAuthority = ScAuthorityResourceIT.createUpdatedEntity(em);
            em.persist(scAuthority);
            em.flush();
        } else {
            scAuthority = TestUtil.findAll(em, ScAuthority.class).get(0);
        }
        adUserAuthority.setAuthority(scAuthority);
        return adUserAuthority;
    }

    @BeforeEach
    public void initTest() {
        adUserAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdUserAuthority() throws Exception {
        int databaseSizeBeforeCreate = adUserAuthorityRepository.findAll().size();

        // Create the AdUserAuthority
        AdUserAuthorityDTO adUserAuthorityDTO = adUserAuthorityMapper.toDto(adUserAuthority);
        restAdUserAuthorityMockMvc.perform(post("/api/ad-user-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserAuthorityDTO)))
            .andExpect(status().isCreated());

        // Validate the AdUserAuthority in the database
        List<AdUserAuthority> adUserAuthorityList = adUserAuthorityRepository.findAll();
        assertThat(adUserAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        AdUserAuthority testAdUserAuthority = adUserAuthorityList.get(adUserAuthorityList.size() - 1);
        assertThat(testAdUserAuthority.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAdUserAuthority.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAdUserAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adUserAuthorityRepository.findAll().size();

        // Create the AdUserAuthority with an existing ID
        adUserAuthority.setId(1L);
        AdUserAuthorityDTO adUserAuthorityDTO = adUserAuthorityMapper.toDto(adUserAuthority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdUserAuthorityMockMvc.perform(post("/api/ad-user-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdUserAuthority in the database
        List<AdUserAuthority> adUserAuthorityList = adUserAuthorityRepository.findAll();
        assertThat(adUserAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAdUserAuthorities() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adUserAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAdUserAuthority() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get the adUserAuthority
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities/{id}", adUserAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adUserAuthority.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getAdUserAuthoritiesByIdFiltering() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        Long id = adUserAuthority.getId();

        defaultAdUserAuthorityShouldBeFound("id.equals=" + id);
        defaultAdUserAuthorityShouldNotBeFound("id.notEquals=" + id);

        defaultAdUserAuthorityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdUserAuthorityShouldNotBeFound("id.greaterThan=" + id);

        defaultAdUserAuthorityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdUserAuthorityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where uid equals to DEFAULT_UID
        defaultAdUserAuthorityShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the adUserAuthorityList where uid equals to UPDATED_UID
        defaultAdUserAuthorityShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where uid not equals to DEFAULT_UID
        defaultAdUserAuthorityShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the adUserAuthorityList where uid not equals to UPDATED_UID
        defaultAdUserAuthorityShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where uid in DEFAULT_UID or UPDATED_UID
        defaultAdUserAuthorityShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the adUserAuthorityList where uid equals to UPDATED_UID
        defaultAdUserAuthorityShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where uid is not null
        defaultAdUserAuthorityShouldBeFound("uid.specified=true");

        // Get all the adUserAuthorityList where uid is null
        defaultAdUserAuthorityShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where active equals to DEFAULT_ACTIVE
        defaultAdUserAuthorityShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the adUserAuthorityList where active equals to UPDATED_ACTIVE
        defaultAdUserAuthorityShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where active not equals to DEFAULT_ACTIVE
        defaultAdUserAuthorityShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the adUserAuthorityList where active not equals to UPDATED_ACTIVE
        defaultAdUserAuthorityShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultAdUserAuthorityShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the adUserAuthorityList where active equals to UPDATED_ACTIVE
        defaultAdUserAuthorityShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        // Get all the adUserAuthorityList where active is not null
        defaultAdUserAuthorityShouldBeFound("active.specified=true");

        // Get all the adUserAuthorityList where active is null
        defaultAdUserAuthorityShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = adUserAuthority.getAdOrganization();
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);
        Long adOrganizationId = adOrganization.getId();

        // Get all the adUserAuthorityList where adOrganization equals to adOrganizationId
        defaultAdUserAuthorityShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the adUserAuthorityList where adOrganization equals to adOrganizationId + 1
        defaultAdUserAuthorityShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser user = adUserAuthority.getUser();
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);
        Long userId = user.getId();

        // Get all the adUserAuthorityList where user equals to userId
        defaultAdUserAuthorityShouldBeFound("userId.equals=" + userId);

        // Get all the adUserAuthorityList where user equals to userId + 1
        defaultAdUserAuthorityShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllAdUserAuthoritiesByAuthorityIsEqualToSomething() throws Exception {
        // Get already existing entity
        ScAuthority authority = adUserAuthority.getAuthority();
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);
        Long authorityId = authority.getId();

        // Get all the adUserAuthorityList where authority equals to authorityId
        defaultAdUserAuthorityShouldBeFound("authorityId.equals=" + authorityId);

        // Get all the adUserAuthorityList where authority equals to authorityId + 1
        defaultAdUserAuthorityShouldNotBeFound("authorityId.equals=" + (authorityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdUserAuthorityShouldBeFound(String filter) throws Exception {
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adUserAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdUserAuthorityShouldNotBeFound(String filter) throws Exception {
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdUserAuthority() throws Exception {
        // Get the adUserAuthority
        restAdUserAuthorityMockMvc.perform(get("/api/ad-user-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdUserAuthority() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        int databaseSizeBeforeUpdate = adUserAuthorityRepository.findAll().size();

        // Update the adUserAuthority
        AdUserAuthority updatedAdUserAuthority = adUserAuthorityRepository.findById(adUserAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedAdUserAuthority are not directly saved in db
        em.detach(updatedAdUserAuthority);
        updatedAdUserAuthority
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE);
        AdUserAuthorityDTO adUserAuthorityDTO = adUserAuthorityMapper.toDto(updatedAdUserAuthority);

        restAdUserAuthorityMockMvc.perform(put("/api/ad-user-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserAuthorityDTO)))
            .andExpect(status().isOk());

        // Validate the AdUserAuthority in the database
        List<AdUserAuthority> adUserAuthorityList = adUserAuthorityRepository.findAll();
        assertThat(adUserAuthorityList).hasSize(databaseSizeBeforeUpdate);
        AdUserAuthority testAdUserAuthority = adUserAuthorityList.get(adUserAuthorityList.size() - 1);
        assertThat(testAdUserAuthority.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAdUserAuthority.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdUserAuthority() throws Exception {
        int databaseSizeBeforeUpdate = adUserAuthorityRepository.findAll().size();

        // Create the AdUserAuthority
        AdUserAuthorityDTO adUserAuthorityDTO = adUserAuthorityMapper.toDto(adUserAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdUserAuthorityMockMvc.perform(put("/api/ad-user-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(adUserAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AdUserAuthority in the database
        List<AdUserAuthority> adUserAuthorityList = adUserAuthorityRepository.findAll();
        assertThat(adUserAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdUserAuthority() throws Exception {
        // Initialize the database
        adUserAuthorityRepository.saveAndFlush(adUserAuthority);

        int databaseSizeBeforeDelete = adUserAuthorityRepository.findAll().size();

        // Delete the adUserAuthority
        restAdUserAuthorityMockMvc.perform(delete("/api/ad-user-authorities/{id}", adUserAuthority.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdUserAuthority> adUserAuthorityList = adUserAuthorityRepository.findAll();
        assertThat(adUserAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
