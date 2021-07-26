package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.MContractTeamLine;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.MContractTeam;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.repository.MContractTeamLineRepository;
import com.bhp.opusb.service.MContractTeamLineService;
import com.bhp.opusb.service.dto.MContractTeamLineDTO;
import com.bhp.opusb.service.mapper.MContractTeamLineMapper;
import com.bhp.opusb.service.dto.MContractTeamLineCriteria;
import com.bhp.opusb.service.MContractTeamLineQueryService;

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
 * Integration tests for the {@link MContractTeamLineResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MContractTeamLineResourceIT {

    private static final UUID DEFAULT_UID = UUID.randomUUID();
    private static final UUID UPDATED_UID = UUID.randomUUID();

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    @Autowired
    private MContractTeamLineRepository mContractTeamLineRepository;

    @Autowired
    private MContractTeamLineMapper mContractTeamLineMapper;

    @Autowired
    private MContractTeamLineService mContractTeamLineService;

    @Autowired
    private MContractTeamLineQueryService mContractTeamLineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMContractTeamLineMockMvc;

    private MContractTeamLine mContractTeamLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTeamLine createEntity(EntityManager em) {
        MContractTeamLine mContractTeamLine = new MContractTeamLine()
            .uid(DEFAULT_UID)
            .active(DEFAULT_ACTIVE)
            .position(DEFAULT_POSITION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mContractTeamLine.setAdOrganization(aDOrganization);
        // Add required entity
        MContractTeam mContractTeam;
        if (TestUtil.findAll(em, MContractTeam.class).isEmpty()) {
            mContractTeam = MContractTeamResourceIT.createEntity(em);
            em.persist(mContractTeam);
            em.flush();
        } else {
            mContractTeam = TestUtil.findAll(em, MContractTeam.class).get(0);
        }
        mContractTeamLine.setContractTeam(mContractTeam);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mContractTeamLine.setAdUser(adUser);
        return mContractTeamLine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MContractTeamLine createUpdatedEntity(EntityManager em) {
        MContractTeamLine mContractTeamLine = new MContractTeamLine()
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION);
        // Add required entity
        ADOrganization aDOrganization;
        if (TestUtil.findAll(em, ADOrganization.class).isEmpty()) {
            aDOrganization = ADOrganizationResourceIT.createUpdatedEntity(em);
            em.persist(aDOrganization);
            em.flush();
        } else {
            aDOrganization = TestUtil.findAll(em, ADOrganization.class).get(0);
        }
        mContractTeamLine.setAdOrganization(aDOrganization);
        // Add required entity
        MContractTeam mContractTeam;
        if (TestUtil.findAll(em, MContractTeam.class).isEmpty()) {
            mContractTeam = MContractTeamResourceIT.createUpdatedEntity(em);
            em.persist(mContractTeam);
            em.flush();
        } else {
            mContractTeam = TestUtil.findAll(em, MContractTeam.class).get(0);
        }
        mContractTeamLine.setContractTeam(mContractTeam);
        // Add required entity
        AdUser adUser;
        if (TestUtil.findAll(em, AdUser.class).isEmpty()) {
            adUser = AdUserResourceIT.createUpdatedEntity(em);
            em.persist(adUser);
            em.flush();
        } else {
            adUser = TestUtil.findAll(em, AdUser.class).get(0);
        }
        mContractTeamLine.setAdUser(adUser);
        return mContractTeamLine;
    }

    @BeforeEach
    public void initTest() {
        mContractTeamLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMContractTeamLine() throws Exception {
        int databaseSizeBeforeCreate = mContractTeamLineRepository.findAll().size();

        // Create the MContractTeamLine
        MContractTeamLineDTO mContractTeamLineDTO = mContractTeamLineMapper.toDto(mContractTeamLine);
        restMContractTeamLineMockMvc.perform(post("/api/m-contract-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamLineDTO)))
            .andExpect(status().isCreated());

        // Validate the MContractTeamLine in the database
        List<MContractTeamLine> mContractTeamLineList = mContractTeamLineRepository.findAll();
        assertThat(mContractTeamLineList).hasSize(databaseSizeBeforeCreate + 1);
        MContractTeamLine testMContractTeamLine = mContractTeamLineList.get(mContractTeamLineList.size() - 1);
        assertThat(testMContractTeamLine.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testMContractTeamLine.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testMContractTeamLine.getPosition()).isEqualTo(DEFAULT_POSITION);
    }

    @Test
    @Transactional
    public void createMContractTeamLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mContractTeamLineRepository.findAll().size();

        // Create the MContractTeamLine with an existing ID
        mContractTeamLine.setId(1L);
        MContractTeamLineDTO mContractTeamLineDTO = mContractTeamLineMapper.toDto(mContractTeamLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMContractTeamLineMockMvc.perform(post("/api/m-contract-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTeamLine in the database
        List<MContractTeamLine> mContractTeamLineList = mContractTeamLineRepository.findAll();
        assertThat(mContractTeamLineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = mContractTeamLineRepository.findAll().size();
        // set the field null
        mContractTeamLine.setPosition(null);

        // Create the MContractTeamLine, which fails.
        MContractTeamLineDTO mContractTeamLineDTO = mContractTeamLineMapper.toDto(mContractTeamLine);

        restMContractTeamLineMockMvc.perform(post("/api/m-contract-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamLineDTO)))
            .andExpect(status().isBadRequest());

        List<MContractTeamLine> mContractTeamLineList = mContractTeamLineRepository.findAll();
        assertThat(mContractTeamLineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLines() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTeamLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));
    }
    
    @Test
    @Transactional
    public void getMContractTeamLine() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get the mContractTeamLine
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines/{id}", mContractTeamLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mContractTeamLine.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION));
    }


    @Test
    @Transactional
    public void getMContractTeamLinesByIdFiltering() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        Long id = mContractTeamLine.getId();

        defaultMContractTeamLineShouldBeFound("id.equals=" + id);
        defaultMContractTeamLineShouldNotBeFound("id.notEquals=" + id);

        defaultMContractTeamLineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMContractTeamLineShouldNotBeFound("id.greaterThan=" + id);

        defaultMContractTeamLineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMContractTeamLineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMContractTeamLinesByUidIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where uid equals to DEFAULT_UID
        defaultMContractTeamLineShouldBeFound("uid.equals=" + DEFAULT_UID);

        // Get all the mContractTeamLineList where uid equals to UPDATED_UID
        defaultMContractTeamLineShouldNotBeFound("uid.equals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByUidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where uid not equals to DEFAULT_UID
        defaultMContractTeamLineShouldNotBeFound("uid.notEquals=" + DEFAULT_UID);

        // Get all the mContractTeamLineList where uid not equals to UPDATED_UID
        defaultMContractTeamLineShouldBeFound("uid.notEquals=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByUidIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where uid in DEFAULT_UID or UPDATED_UID
        defaultMContractTeamLineShouldBeFound("uid.in=" + DEFAULT_UID + "," + UPDATED_UID);

        // Get all the mContractTeamLineList where uid equals to UPDATED_UID
        defaultMContractTeamLineShouldNotBeFound("uid.in=" + UPDATED_UID);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByUidIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where uid is not null
        defaultMContractTeamLineShouldBeFound("uid.specified=true");

        // Get all the mContractTeamLineList where uid is null
        defaultMContractTeamLineShouldNotBeFound("uid.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where active equals to DEFAULT_ACTIVE
        defaultMContractTeamLineShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the mContractTeamLineList where active equals to UPDATED_ACTIVE
        defaultMContractTeamLineShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where active not equals to DEFAULT_ACTIVE
        defaultMContractTeamLineShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the mContractTeamLineList where active not equals to UPDATED_ACTIVE
        defaultMContractTeamLineShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultMContractTeamLineShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the mContractTeamLineList where active equals to UPDATED_ACTIVE
        defaultMContractTeamLineShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where active is not null
        defaultMContractTeamLineShouldBeFound("active.specified=true");

        // Get all the mContractTeamLineList where active is null
        defaultMContractTeamLineShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where position equals to DEFAULT_POSITION
        defaultMContractTeamLineShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the mContractTeamLineList where position equals to UPDATED_POSITION
        defaultMContractTeamLineShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where position not equals to DEFAULT_POSITION
        defaultMContractTeamLineShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the mContractTeamLineList where position not equals to UPDATED_POSITION
        defaultMContractTeamLineShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultMContractTeamLineShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the mContractTeamLineList where position equals to UPDATED_POSITION
        defaultMContractTeamLineShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where position is not null
        defaultMContractTeamLineShouldBeFound("position.specified=true");

        // Get all the mContractTeamLineList where position is null
        defaultMContractTeamLineShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllMContractTeamLinesByPositionContainsSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where position contains DEFAULT_POSITION
        defaultMContractTeamLineShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the mContractTeamLineList where position contains UPDATED_POSITION
        defaultMContractTeamLineShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllMContractTeamLinesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        // Get all the mContractTeamLineList where position does not contain DEFAULT_POSITION
        defaultMContractTeamLineShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the mContractTeamLineList where position does not contain UPDATED_POSITION
        defaultMContractTeamLineShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllMContractTeamLinesByAdOrganizationIsEqualToSomething() throws Exception {
        // Get already existing entity
        ADOrganization adOrganization = mContractTeamLine.getAdOrganization();
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);
        Long adOrganizationId = adOrganization.getId();

        // Get all the mContractTeamLineList where adOrganization equals to adOrganizationId
        defaultMContractTeamLineShouldBeFound("adOrganizationId.equals=" + adOrganizationId);

        // Get all the mContractTeamLineList where adOrganization equals to adOrganizationId + 1
        defaultMContractTeamLineShouldNotBeFound("adOrganizationId.equals=" + (adOrganizationId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTeamLinesByContractTeamIsEqualToSomething() throws Exception {
        // Get already existing entity
        MContractTeam contractTeam = mContractTeamLine.getContractTeam();
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);
        Long contractTeamId = contractTeam.getId();

        // Get all the mContractTeamLineList where contractTeam equals to contractTeamId
        defaultMContractTeamLineShouldBeFound("contractTeamId.equals=" + contractTeamId);

        // Get all the mContractTeamLineList where contractTeam equals to contractTeamId + 1
        defaultMContractTeamLineShouldNotBeFound("contractTeamId.equals=" + (contractTeamId + 1));
    }


    @Test
    @Transactional
    public void getAllMContractTeamLinesByAdUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        AdUser adUser = mContractTeamLine.getAdUser();
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);
        Long adUserId = adUser.getId();

        // Get all the mContractTeamLineList where adUser equals to adUserId
        defaultMContractTeamLineShouldBeFound("adUserId.equals=" + adUserId);

        // Get all the mContractTeamLineList where adUser equals to adUserId + 1
        defaultMContractTeamLineShouldNotBeFound("adUserId.equals=" + (adUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMContractTeamLineShouldBeFound(String filter) throws Exception {
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mContractTeamLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)));

        // Check, that the count call also returns 1
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMContractTeamLineShouldNotBeFound(String filter) throws Exception {
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMContractTeamLine() throws Exception {
        // Get the mContractTeamLine
        restMContractTeamLineMockMvc.perform(get("/api/m-contract-team-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMContractTeamLine() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        int databaseSizeBeforeUpdate = mContractTeamLineRepository.findAll().size();

        // Update the mContractTeamLine
        MContractTeamLine updatedMContractTeamLine = mContractTeamLineRepository.findById(mContractTeamLine.getId()).get();
        // Disconnect from session so that the updates on updatedMContractTeamLine are not directly saved in db
        em.detach(updatedMContractTeamLine);
        updatedMContractTeamLine
            .uid(UPDATED_UID)
            .active(UPDATED_ACTIVE)
            .position(UPDATED_POSITION);
        MContractTeamLineDTO mContractTeamLineDTO = mContractTeamLineMapper.toDto(updatedMContractTeamLine);

        restMContractTeamLineMockMvc.perform(put("/api/m-contract-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamLineDTO)))
            .andExpect(status().isOk());

        // Validate the MContractTeamLine in the database
        List<MContractTeamLine> mContractTeamLineList = mContractTeamLineRepository.findAll();
        assertThat(mContractTeamLineList).hasSize(databaseSizeBeforeUpdate);
        MContractTeamLine testMContractTeamLine = mContractTeamLineList.get(mContractTeamLineList.size() - 1);
        assertThat(testMContractTeamLine.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testMContractTeamLine.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testMContractTeamLine.getPosition()).isEqualTo(UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void updateNonExistingMContractTeamLine() throws Exception {
        int databaseSizeBeforeUpdate = mContractTeamLineRepository.findAll().size();

        // Create the MContractTeamLine
        MContractTeamLineDTO mContractTeamLineDTO = mContractTeamLineMapper.toDto(mContractTeamLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMContractTeamLineMockMvc.perform(put("/api/m-contract-team-lines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mContractTeamLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MContractTeamLine in the database
        List<MContractTeamLine> mContractTeamLineList = mContractTeamLineRepository.findAll();
        assertThat(mContractTeamLineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMContractTeamLine() throws Exception {
        // Initialize the database
        mContractTeamLineRepository.saveAndFlush(mContractTeamLine);

        int databaseSizeBeforeDelete = mContractTeamLineRepository.findAll().size();

        // Delete the mContractTeamLine
        restMContractTeamLineMockMvc.perform(delete("/api/m-contract-team-lines/{id}", mContractTeamLine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MContractTeamLine> mContractTeamLineList = mContractTeamLineRepository.findAll();
        assertThat(mContractTeamLineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
