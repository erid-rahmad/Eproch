package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.ScAccess;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.ScAccessType;
import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.domain.AdForm;
import com.bhp.opusb.domain.ScAuthority;
import com.bhp.opusb.repository.ScAccessRepository;
import com.bhp.opusb.service.ScAccessService;
import com.bhp.opusb.service.dto.ScAccessDTO;
import com.bhp.opusb.service.mapper.ScAccessMapper;
import com.bhp.opusb.service.dto.ScAccessCriteria;
import com.bhp.opusb.service.ScAccessQueryService;

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
 * Integration tests for the {@link ScAccessResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ScAccessResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ScAccessRepository scAccessRepository;

    @Autowired
    private ScAccessMapper scAccessMapper;

    @Autowired
    private ScAccessService scAccessService;

    @Autowired
    private ScAccessQueryService scAccessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScAccessMockMvc;

    private ScAccess scAccess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAccess createEntity(EntityManager em) {
        ScAccess scAccess = new ScAccess()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        scAccess.setAdOrganization(aDOrganization);
        // Add required entity
        ScAccessType scAccessType;
        if (TestUtil.findAll(em, ScAccessType.class).isEmpty()) {
            scAccessType = ScAccessTypeResourceIT.createEntity(em);
            em.persist(scAccessType);
            em.flush();
        } else {
            scAccessType = TestUtil.findAll(em, ScAccessType.class).get(0);
        }
        scAccess.setType(scAccessType);
        // Add required entity
        ScAuthority scAuthority;
        if (TestUtil.findAll(em, ScAuthority.class).isEmpty()) {
            scAuthority = ScAuthorityResourceIT.createEntity(em);
            em.persist(scAuthority);
            em.flush();
        } else {
            scAuthority = TestUtil.findAll(em, ScAuthority.class).get(0);
        }
        scAccess.setAuthority(scAuthority);
        return scAccess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScAccess createUpdatedEntity(EntityManager em) {
        ScAccess scAccess = new ScAccess()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        scAccess.setAdOrganization(aDOrganization);
        // Add required entity
        ScAccessType scAccessType;
        if (TestUtil.findAll(em, ScAccessType.class).isEmpty()) {
            scAccessType = ScAccessTypeResourceIT.createUpdatedEntity(em);
            em.persist(scAccessType);
            em.flush();
        } else {
            scAccessType = TestUtil.findAll(em, ScAccessType.class).get(0);
        }
        scAccess.setType(scAccessType);
        // Add required entity
        ScAuthority scAuthority;
        if (TestUtil.findAll(em, ScAuthority.class).isEmpty()) {
            scAuthority = ScAuthorityResourceIT.createUpdatedEntity(em);
            em.persist(scAuthority);
            em.flush();
        } else {
            scAuthority = TestUtil.findAll(em, ScAuthority.class).get(0);
        }
        scAccess.setAuthority(scAuthority);
        return scAccess;
    }

    @BeforeEach
    public void initTest() {
        scAccess = createEntity(em);
    }

    @Test
    @Transactional
    public void createScAccess() throws Exception {
        int databaseSizeBeforeCreate = scAccessRepository.findAll().size();

        // Create the ScAccess
        ScAccessDTO scAccessDTO = scAccessMapper.toDto(scAccess);
        restScAccessMockMvc.perform(post("/api/sc-accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the ScAccess in the database
        List<ScAccess> scAccessList = scAccessRepository.findAll();
        assertThat(scAccessList).hasSize(databaseSizeBeforeCreate + 1);
        ScAccess testScAccess = scAccessList.get(scAccessList.size() - 1);
        assertThat(testScAccess.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testScAccess.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testScAccess.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScAccess.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createScAccessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scAccessRepository.findAll().size();

        // Create the ScAccess with an existing ID
        scAccess.setId(1L);
        ScAccessDTO scAccessDTO = scAccessMapper.toDto(scAccess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScAccessMockMvc.perform(post("/api/sc-accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScAccess in the database
        List<ScAccess> scAccessList = scAccessRepository.findAll();
        assertThat(scAccessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScAccesses() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList
        restScAccessMockMvc.perform(get("/api/sc-accesses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getScAccess() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get the scAccess
        restScAccessMockMvc.perform(get("/api/sc-accesses/{id}", scAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scAccess.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getScAccessesByIdFiltering() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        Long id = scAccess.getId();

        defaultScAccessShouldBeFound("id.equals=" + id);
        defaultScAccessShouldNotBeFound("id.notEquals=" + id);

        defaultScAccessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultScAccessShouldNotBeFound("id.greaterThan=" + id);

        defaultScAccessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultScAccessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllScAccessesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where uid equals to DEFAULT_UID
        defaultScAccessShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the scAccessList where uid equals to UPDATED_UID
        defaultScAccessShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAccessesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where uid not equals to DEFAULT_UID
        defaultScAccessShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the scAccessList where uid not equals to UPDATED_UID
        defaultScAccessShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAccessesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where uid in DEFAULT_UID or UPDATED_UID
        defaultScAccessShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the scAccessList where uid equals to UPDATED_UID
        defaultScAccessShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllScAccessesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where uid is not null
        defaultScAccessShouldBeFound("uid.specified=true");

        // Get all the scAccessList where uid is null
        defaultScAccessShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAccessesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where active equals to DEFAULT_ACTIVE
        defaultScAccessShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the scAccessList where active equals to UPDATED_ACTIVE
        defaultScAccessShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAccessesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where active not equals to DEFAULT_ACTIVE
        defaultScAccessShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the scAccessList where active not equals to UPDATED_ACTIVE
        defaultScAccessShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAccessesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultScAccessShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the scAccessList where active equals to UPDATED_ACTIVE
        defaultScAccessShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllScAccessesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where active is not null
        defaultScAccessShouldBeFound("active.specified=true");

        // Get all the scAccessList where active is null
        defaultScAccessShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllScAccessesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where name equals to DEFAULT_NAME
        defaultScAccessShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the scAccessList where name equals to UPDATED_NAME
        defaultScAccessShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where name not equals to DEFAULT_NAME
        defaultScAccessShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the scAccessList where name not equals to UPDATED_NAME
        defaultScAccessShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where name in DEFAULT_NAME or UPDATED_NAME
        defaultScAccessShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the scAccessList where name equals to UPDATED_NAME
        defaultScAccessShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where name is not null
        defaultScAccessShouldBeFound("name.specified=true");

        // Get all the scAccessList where name is null
        defaultScAccessShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllScAccessesByNameContainsSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where name contains DEFAULT_NAME
        defaultScAccessShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the scAccessList where name contains UPDATED_NAME
        defaultScAccessShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllScAccessesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where name does not contain DEFAULT_NAME
        defaultScAccessShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the scAccessList where name does not contain UPDATED_NAME
        defaultScAccessShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllScAccessesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where description equals to DEFAULT_DESCRIPTION
        defaultScAccessShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessList where description equals to UPDATED_DESCRIPTION
        defaultScAccessShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where description not equals to DEFAULT_DESCRIPTION
        defaultScAccessShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessList where description not equals to UPDATED_DESCRIPTION
        defaultScAccessShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultScAccessShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the scAccessList where description equals to UPDATED_DESCRIPTION
        defaultScAccessShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where description is not null
        defaultScAccessShouldBeFound("description.specified=true");

        // Get all the scAccessList where description is null
        defaultScAccessShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllScAccessesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where description contains DEFAULT_DESCRIPTION
        defaultScAccessShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessList where description contains UPDATED_DESCRIPTION
        defaultScAccessShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScAccessesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        // Get all the scAccessList where description does not contain DEFAULT_DESCRIPTION
        defaultScAccessShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the scAccessList where description does not contain UPDATED_DESCRIPTION
        defaultScAccessShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllScAccessesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = scAccess.getAdOrganization();
        scAccessRepository.saveAndFlush(scAccess);
        Long adOrganizationId = adOrganization.getId();

        // Get all the scAccessList where adOrganization equals to adOrganizationId
        defaultScAccessShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the scAccessList where adOrganization equals to adOrganizationId + 1
        defaultScAccessShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllScAccessesByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        ScAccessType type = scAccess.getType();
        scAccessRepository.saveAndFlush(scAccess);
        Long typeId = type.getId();

        // Get all the scAccessList where type equals to typeId
        defaultScAccessShouldBeFound("typeId.equals=" + typeId);

        // Get all the scAccessList where type equals to typeId + 1
        defaultScAccessShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllScAccessesByWindowIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);
        ADWindow window = ADWindowResourceIT.createEntity(em);
        em.persist(window);
        em.flush();
        scAccess.setWindow(window);
        scAccessRepository.saveAndFlush(scAccess);
        Long windowId = window.getId();

        // Get all the scAccessList where window equals to windowId
        defaultScAccessShouldBeFound("windowId.equals=" + windowId);

        // Get all the scAccessList where window equals to windowId + 1
        defaultScAccessShouldNotBeFound("windowId.equals=" + (windowId + 1));
    }


    @Test
    @Transactional
    public void getAllScAccessesByFormIsEqualToSomething() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);
        AdForm form = AdFormResourceIT.createEntity(em);
        em.persist(form);
        em.flush();
        scAccess.setForm(form);
        scAccessRepository.saveAndFlush(scAccess);
        Long formId = form.getId();

        // Get all the scAccessList where form equals to formId
        defaultScAccessShouldBeFound("formId.equals=" + formId);

        // Get all the scAccessList where form equals to formId + 1
        defaultScAccessShouldNotBeFound("formId.equals=" + (formId + 1));
    }


    @Test
    @Transactional
    public void getAllScAccessesByAuthorityIsEqualToSomething() throws Exception {
        // Get already existing entity
        ScAuthority authority = scAccess.getAuthority();
        scAccessRepository.saveAndFlush(scAccess);
        Long authorityId = authority.getId();

        // Get all the scAccessList where authority equals to authorityId
        defaultScAccessShouldBeFound("authorityId.equals=" + authorityId);

        // Get all the scAccessList where authority equals to authorityId + 1
        defaultScAccessShouldNotBeFound("authorityId.equals=" + (authorityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScAccessShouldBeFound(String filter) throws Exception {
        restScAccessMockMvc.perform(get("/api/sc-accesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restScAccessMockMvc.perform(get("/api/sc-accesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScAccessShouldNotBeFound(String filter) throws Exception {
        restScAccessMockMvc.perform(get("/api/sc-accesses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScAccessMockMvc.perform(get("/api/sc-accesses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScAccess() throws Exception {
        // Get the scAccess
        restScAccessMockMvc.perform(get("/api/sc-accesses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScAccess() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        int databaseSizeBeforeUpdate = scAccessRepository.findAll().size();

        // Update the scAccess
        ScAccess updatedScAccess = scAccessRepository.findById(scAccess.getId()).get();
        // Disconnect from session so that the updates on updatedScAccess are not directly saved in db
        em.detach(updatedScAccess);
        updatedScAccess
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ScAccessDTO scAccessDTO = scAccessMapper.toDto(updatedScAccess);

        restScAccessMockMvc.perform(put("/api/sc-accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessDTO)))
            .andExpect(status().isOk());

        // Validate the ScAccess in the database
        List<ScAccess> scAccessList = scAccessRepository.findAll();
        assertThat(scAccessList).hasSize(databaseSizeBeforeUpdate);
        ScAccess testScAccess = scAccessList.get(scAccessList.size() - 1);
        assertThat(testScAccess.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testScAccess.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testScAccess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScAccess.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingScAccess() throws Exception {
        int databaseSizeBeforeUpdate = scAccessRepository.findAll().size();

        // Create the ScAccess
        ScAccessDTO scAccessDTO = scAccessMapper.toDto(scAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScAccessMockMvc.perform(put("/api/sc-accesses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScAccess in the database
        List<ScAccess> scAccessList = scAccessRepository.findAll();
        assertThat(scAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScAccess() throws Exception {
        // Initialize the database
        scAccessRepository.saveAndFlush(scAccess);

        int databaseSizeBeforeDelete = scAccessRepository.findAll().size();

        // Delete the scAccess
        restScAccessMockMvc.perform(delete("/api/sc-accesses/{id}", scAccess.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScAccess> scAccessList = scAccessRepository.findAll();
        assertThat(scAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
