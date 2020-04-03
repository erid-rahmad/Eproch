package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.PersonInCharge;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.domain.Vendor;
import com.bhp.opusb.repository.PersonInChargeRepository;
import com.bhp.opusb.service.PersonInChargeService;
import com.bhp.opusb.service.dto.PersonInChargeDTO;
import com.bhp.opusb.service.mapper.PersonInChargeMapper;
import com.bhp.opusb.service.dto.PersonInChargeCriteria;
import com.bhp.opusb.service.PersonInChargeQueryService;

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
 * Integration tests for the {@link PersonInChargeResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PersonInChargeResourceIT {

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private PersonInChargeRepository personInChargeRepository;

    @Autowired
    private PersonInChargeMapper personInChargeMapper;

    @Autowired
    private PersonInChargeService personInChargeService;

    @Autowired
    private PersonInChargeQueryService personInChargeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonInChargeMockMvc;

    private PersonInCharge personInCharge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInCharge createEntity(EntityManager em) {
        PersonInCharge personInCharge = new PersonInCharge()
            .position(DEFAULT_POSITION)
            .phone(DEFAULT_PHONE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        personInCharge.setUser(user);
        return personInCharge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonInCharge createUpdatedEntity(EntityManager em) {
        PersonInCharge personInCharge = new PersonInCharge()
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        personInCharge.setUser(user);
        return personInCharge;
    }

    @BeforeEach
    public void initTest() {
        personInCharge = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonInCharge() throws Exception {
        int databaseSizeBeforeCreate = personInChargeRepository.findAll().size();

        // Create the PersonInCharge
        PersonInChargeDTO personInChargeDTO = personInChargeMapper.toDto(personInCharge);
        restPersonInChargeMockMvc.perform(post("/api/person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInChargeDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonInCharge in the database
        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeCreate + 1);
        PersonInCharge testPersonInCharge = personInChargeList.get(personInChargeList.size() - 1);
        assertThat(testPersonInCharge.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testPersonInCharge.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createPersonInChargeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personInChargeRepository.findAll().size();

        // Create the PersonInCharge with an existing ID
        personInCharge.setId(1L);
        PersonInChargeDTO personInChargeDTO = personInChargeMapper.toDto(personInCharge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonInChargeMockMvc.perform(post("/api/person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInChargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInCharge in the database
        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInChargeRepository.findAll().size();
        // set the field null
        personInCharge.setPosition(null);

        // Create the PersonInCharge, which fails.
        PersonInChargeDTO personInChargeDTO = personInChargeMapper.toDto(personInCharge);

        restPersonInChargeMockMvc.perform(post("/api/person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInChargeDTO)))
            .andExpect(status().isBadRequest());

        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = personInChargeRepository.findAll().size();
        // set the field null
        personInCharge.setPhone(null);

        // Create the PersonInCharge, which fails.
        PersonInChargeDTO personInChargeDTO = personInChargeMapper.toDto(personInCharge);

        restPersonInChargeMockMvc.perform(post("/api/person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInChargeDTO)))
            .andExpect(status().isBadRequest());

        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonInCharges() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getPersonInCharge() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get the personInCharge
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges/{id}", personInCharge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personInCharge.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }


    @Test
    @Transactional
    public void getPersonInChargesByIdFiltering() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        Long id = personInCharge.getId();

        defaultPersonInChargeShouldBeFound("id.equals=" + id);
        defaultPersonInChargeShouldNotBeFound("id.notEquals=" + id);

        defaultPersonInChargeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonInChargeShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonInChargeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonInChargeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonInChargesByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where position equals to DEFAULT_POSITION
        defaultPersonInChargeShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the personInChargeList where position equals to UPDATED_POSITION
        defaultPersonInChargeShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where position not equals to DEFAULT_POSITION
        defaultPersonInChargeShouldNotBeFound("position.notEquals=" + DEFAULT_POSITION);

        // Get all the personInChargeList where position not equals to UPDATED_POSITION
        defaultPersonInChargeShouldBeFound("position.notEquals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultPersonInChargeShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the personInChargeList where position equals to UPDATED_POSITION
        defaultPersonInChargeShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where position is not null
        defaultPersonInChargeShouldBeFound("position.specified=true");

        // Get all the personInChargeList where position is null
        defaultPersonInChargeShouldNotBeFound("position.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonInChargesByPositionContainsSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where position contains DEFAULT_POSITION
        defaultPersonInChargeShouldBeFound("position.contains=" + DEFAULT_POSITION);

        // Get all the personInChargeList where position contains UPDATED_POSITION
        defaultPersonInChargeShouldNotBeFound("position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where position does not contain DEFAULT_POSITION
        defaultPersonInChargeShouldNotBeFound("position.doesNotContain=" + DEFAULT_POSITION);

        // Get all the personInChargeList where position does not contain UPDATED_POSITION
        defaultPersonInChargeShouldBeFound("position.doesNotContain=" + UPDATED_POSITION);
    }


    @Test
    @Transactional
    public void getAllPersonInChargesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where phone equals to DEFAULT_PHONE
        defaultPersonInChargeShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the personInChargeList where phone equals to UPDATED_PHONE
        defaultPersonInChargeShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where phone not equals to DEFAULT_PHONE
        defaultPersonInChargeShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the personInChargeList where phone not equals to UPDATED_PHONE
        defaultPersonInChargeShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultPersonInChargeShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the personInChargeList where phone equals to UPDATED_PHONE
        defaultPersonInChargeShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where phone is not null
        defaultPersonInChargeShouldBeFound("phone.specified=true");

        // Get all the personInChargeList where phone is null
        defaultPersonInChargeShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonInChargesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where phone contains DEFAULT_PHONE
        defaultPersonInChargeShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the personInChargeList where phone contains UPDATED_PHONE
        defaultPersonInChargeShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPersonInChargesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        // Get all the personInChargeList where phone does not contain DEFAULT_PHONE
        defaultPersonInChargeShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the personInChargeList where phone does not contain UPDATED_PHONE
        defaultPersonInChargeShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllPersonInChargesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = personInCharge.getUser();
        personInChargeRepository.saveAndFlush(personInCharge);
        Long userId = user.getId();

        // Get all the personInChargeList where user equals to userId
        defaultPersonInChargeShouldBeFound("userId.equals=" + userId);

        // Get all the personInChargeList where user equals to userId + 1
        defaultPersonInChargeShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonInChargesByVendorIsEqualToSomething() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);
        Vendor vendor = VendorResourceIT.createEntity(em);
        em.persist(vendor);
        em.flush();
        personInCharge.setVendor(vendor);
        personInChargeRepository.saveAndFlush(personInCharge);
        Long vendorId = vendor.getId();

        // Get all the personInChargeList where vendor equals to vendorId
        defaultPersonInChargeShouldBeFound("vendorId.equals=" + vendorId);

        // Get all the personInChargeList where vendor equals to vendorId + 1
        defaultPersonInChargeShouldNotBeFound("vendorId.equals=" + (vendorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonInChargeShouldBeFound(String filter) throws Exception {
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personInCharge.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonInChargeShouldNotBeFound(String filter) throws Exception {
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPersonInCharge() throws Exception {
        // Get the personInCharge
        restPersonInChargeMockMvc.perform(get("/api/person-in-charges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonInCharge() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        int databaseSizeBeforeUpdate = personInChargeRepository.findAll().size();

        // Update the personInCharge
        PersonInCharge updatedPersonInCharge = personInChargeRepository.findById(personInCharge.getId()).get();
        // Disconnect from session so that the updates on updatedPersonInCharge are not directly saved in db
        em.detach(updatedPersonInCharge);
        updatedPersonInCharge
            .position(UPDATED_POSITION)
            .phone(UPDATED_PHONE);
        PersonInChargeDTO personInChargeDTO = personInChargeMapper.toDto(updatedPersonInCharge);

        restPersonInChargeMockMvc.perform(put("/api/person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInChargeDTO)))
            .andExpect(status().isOk());

        // Validate the PersonInCharge in the database
        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeUpdate);
        PersonInCharge testPersonInCharge = personInChargeList.get(personInChargeList.size() - 1);
        assertThat(testPersonInCharge.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testPersonInCharge.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonInCharge() throws Exception {
        int databaseSizeBeforeUpdate = personInChargeRepository.findAll().size();

        // Create the PersonInCharge
        PersonInChargeDTO personInChargeDTO = personInChargeMapper.toDto(personInCharge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonInChargeMockMvc.perform(put("/api/person-in-charges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(personInChargeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonInCharge in the database
        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonInCharge() throws Exception {
        // Initialize the database
        personInChargeRepository.saveAndFlush(personInCharge);

        int databaseSizeBeforeDelete = personInChargeRepository.findAll().size();

        // Delete the personInCharge
        restPersonInChargeMockMvc.perform(delete("/api/person-in-charges/{id}", personInCharge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonInCharge> personInChargeList = personInChargeRepository.findAll();
        assertThat(personInChargeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
