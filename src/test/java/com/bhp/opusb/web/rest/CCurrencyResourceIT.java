package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.CCurrency;
import com.bhp.opusb.repository.CCurrencyRepository;
import com.bhp.opusb.service.CCurrencyService;
import com.bhp.opusb.service.dto.CCurrencyDTO;
import com.bhp.opusb.service.mapper.CCurrencyMapper;
import com.bhp.opusb.service.dto.CCurrencyCriteria;
import com.bhp.opusb.service.CCurrencyQueryService;

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
 * Integration tests for the {@link CCurrencyResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CCurrencyResourceIT {

    private static final String DEFAULT_CODE = "ZEB";
    private static final String UPDATED_CODE = "UCO";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CCurrencyRepository cCurrencyRepository;

    @Autowired
    private CCurrencyMapper cCurrencyMapper;

    @Autowired
    private CCurrencyService cCurrencyService;

    @Autowired
    private CCurrencyQueryService cCurrencyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCCurrencyMockMvc;

    private CCurrency cCurrency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCurrency createEntity(EntityManager em) {
        CCurrency cCurrency = new CCurrency()
            .code(DEFAULT_CODE)
            .symbol(DEFAULT_SYMBOL)
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE);
        return cCurrency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCurrency createUpdatedEntity(EntityManager em) {
        CCurrency cCurrency = new CCurrency()
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);
        return cCurrency;
    }

    @BeforeEach
    public void initTest() {
        cCurrency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCurrency() throws Exception {
        int databaseSizeBeforeCreate = cCurrencyRepository.findAll().size();

        // Create the CCurrency
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(cCurrency);
        restCCurrencyMockMvc.perform(post("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isCreated());

        // Validate the CCurrency in the database
        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeCreate + 1);
        CCurrency testCCurrency = cCurrencyList.get(cCurrencyList.size() - 1);
        assertThat(testCCurrency.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCCurrency.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testCCurrency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCCurrency.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCurrencyRepository.findAll().size();

        // Create the CCurrency with an existing ID
        cCurrency.setId(1L);
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(cCurrency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCurrencyMockMvc.perform(post("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCurrency in the database
        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCurrencyRepository.findAll().size();
        // set the field null
        cCurrency.setCode(null);

        // Create the CCurrency, which fails.
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(cCurrency);

        restCCurrencyMockMvc.perform(post("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isBadRequest());

        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSymbolIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCurrencyRepository.findAll().size();
        // set the field null
        cCurrency.setSymbol(null);

        // Create the CCurrency, which fails.
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(cCurrency);

        restCCurrencyMockMvc.perform(post("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isBadRequest());

        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cCurrencyRepository.findAll().size();
        // set the field null
        cCurrency.setName(null);

        // Create the CCurrency, which fails.
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(cCurrency);

        restCCurrencyMockMvc.perform(post("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isBadRequest());

        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCCurrencies() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList
        restCCurrencyMockMvc.perform(get("/api/c-currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCurrency.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCCurrency() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get the cCurrency
        restCCurrencyMockMvc.perform(get("/api/c-currencies/{id}", cCurrency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cCurrency.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCCurrenciesByIdFiltering() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        Long id = cCurrency.getId();

        defaultCCurrencyShouldBeFound("id.equals=" + id);
        defaultCCurrencyShouldNotBeFound("id.notEquals=" + id);

        defaultCCurrencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCCurrencyShouldNotBeFound("id.greaterThan=" + id);

        defaultCCurrencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCCurrencyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCCurrenciesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where code equals to DEFAULT_CODE
        defaultCCurrencyShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the cCurrencyList where code equals to UPDATED_CODE
        defaultCCurrencyShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where code not equals to DEFAULT_CODE
        defaultCCurrencyShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the cCurrencyList where code not equals to UPDATED_CODE
        defaultCCurrencyShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCCurrencyShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the cCurrencyList where code equals to UPDATED_CODE
        defaultCCurrencyShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where code is not null
        defaultCCurrencyShouldBeFound("code.specified=true");

        // Get all the cCurrencyList where code is null
        defaultCCurrencyShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCurrenciesByCodeContainsSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where code contains DEFAULT_CODE
        defaultCCurrencyShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the cCurrencyList where code contains UPDATED_CODE
        defaultCCurrencyShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where code does not contain DEFAULT_CODE
        defaultCCurrencyShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the cCurrencyList where code does not contain UPDATED_CODE
        defaultCCurrencyShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllCCurrenciesBySymbolIsEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where symbol equals to DEFAULT_SYMBOL
        defaultCCurrencyShouldBeFound("symbol.equals=" + DEFAULT_SYMBOL);

        // Get all the cCurrencyList where symbol equals to UPDATED_SYMBOL
        defaultCCurrencyShouldNotBeFound("symbol.equals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesBySymbolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where symbol not equals to DEFAULT_SYMBOL
        defaultCCurrencyShouldNotBeFound("symbol.notEquals=" + DEFAULT_SYMBOL);

        // Get all the cCurrencyList where symbol not equals to UPDATED_SYMBOL
        defaultCCurrencyShouldBeFound("symbol.notEquals=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesBySymbolIsInShouldWork() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where symbol in DEFAULT_SYMBOL or UPDATED_SYMBOL
        defaultCCurrencyShouldBeFound("symbol.in=" + DEFAULT_SYMBOL + "," + UPDATED_SYMBOL);

        // Get all the cCurrencyList where symbol equals to UPDATED_SYMBOL
        defaultCCurrencyShouldNotBeFound("symbol.in=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesBySymbolIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where symbol is not null
        defaultCCurrencyShouldBeFound("symbol.specified=true");

        // Get all the cCurrencyList where symbol is null
        defaultCCurrencyShouldNotBeFound("symbol.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCurrenciesBySymbolContainsSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where symbol contains DEFAULT_SYMBOL
        defaultCCurrencyShouldBeFound("symbol.contains=" + DEFAULT_SYMBOL);

        // Get all the cCurrencyList where symbol contains UPDATED_SYMBOL
        defaultCCurrencyShouldNotBeFound("symbol.contains=" + UPDATED_SYMBOL);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesBySymbolNotContainsSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where symbol does not contain DEFAULT_SYMBOL
        defaultCCurrencyShouldNotBeFound("symbol.doesNotContain=" + DEFAULT_SYMBOL);

        // Get all the cCurrencyList where symbol does not contain UPDATED_SYMBOL
        defaultCCurrencyShouldBeFound("symbol.doesNotContain=" + UPDATED_SYMBOL);
    }


    @Test
    @Transactional
    public void getAllCCurrenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where name equals to DEFAULT_NAME
        defaultCCurrencyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the cCurrencyList where name equals to UPDATED_NAME
        defaultCCurrencyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where name not equals to DEFAULT_NAME
        defaultCCurrencyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the cCurrencyList where name not equals to UPDATED_NAME
        defaultCCurrencyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCCurrencyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the cCurrencyList where name equals to UPDATED_NAME
        defaultCCurrencyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where name is not null
        defaultCCurrencyShouldBeFound("name.specified=true");

        // Get all the cCurrencyList where name is null
        defaultCCurrencyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCCurrenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where name contains DEFAULT_NAME
        defaultCCurrencyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the cCurrencyList where name contains UPDATED_NAME
        defaultCCurrencyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where name does not contain DEFAULT_NAME
        defaultCCurrencyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the cCurrencyList where name does not contain UPDATED_NAME
        defaultCCurrencyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCCurrenciesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where active equals to DEFAULT_ACTIVE
        defaultCCurrencyShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the cCurrencyList where active equals to UPDATED_ACTIVE
        defaultCCurrencyShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where active not equals to DEFAULT_ACTIVE
        defaultCCurrencyShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the cCurrencyList where active not equals to UPDATED_ACTIVE
        defaultCCurrencyShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultCCurrencyShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the cCurrencyList where active equals to UPDATED_ACTIVE
        defaultCCurrencyShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllCCurrenciesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        // Get all the cCurrencyList where active is not null
        defaultCCurrencyShouldBeFound("active.specified=true");

        // Get all the cCurrencyList where active is null
        defaultCCurrencyShouldNotBeFound("active.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCCurrencyShouldBeFound(String filter) throws Exception {
        restCCurrencyMockMvc.perform(get("/api/c-currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCurrency.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restCCurrencyMockMvc.perform(get("/api/c-currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCCurrencyShouldNotBeFound(String filter) throws Exception {
        restCCurrencyMockMvc.perform(get("/api/c-currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCCurrencyMockMvc.perform(get("/api/c-currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCCurrency() throws Exception {
        // Get the cCurrency
        restCCurrencyMockMvc.perform(get("/api/c-currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCurrency() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        int databaseSizeBeforeUpdate = cCurrencyRepository.findAll().size();

        // Update the cCurrency
        CCurrency updatedCCurrency = cCurrencyRepository.findById(cCurrency.getId()).get();
        // Disconnect from session so that the updates on updatedCCurrency are not directly saved in db
        em.detach(updatedCCurrency);
        updatedCCurrency
            .code(UPDATED_CODE)
            .symbol(UPDATED_SYMBOL)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE);
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(updatedCCurrency);

        restCCurrencyMockMvc.perform(put("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isOk());

        // Validate the CCurrency in the database
        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeUpdate);
        CCurrency testCCurrency = cCurrencyList.get(cCurrencyList.size() - 1);
        assertThat(testCCurrency.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCCurrency.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testCCurrency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCCurrency.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCurrency() throws Exception {
        int databaseSizeBeforeUpdate = cCurrencyRepository.findAll().size();

        // Create the CCurrency
        CCurrencyDTO cCurrencyDTO = cCurrencyMapper.toDto(cCurrency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCurrencyMockMvc.perform(put("/api/c-currencies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cCurrencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CCurrency in the database
        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCurrency() throws Exception {
        // Initialize the database
        cCurrencyRepository.saveAndFlush(cCurrency);

        int databaseSizeBeforeDelete = cCurrencyRepository.findAll().size();

        // Delete the cCurrency
        restCCurrencyMockMvc.perform(delete("/api/c-currencies/{id}", cCurrency.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CCurrency> cCurrencyList = cCurrencyRepository.findAll();
        assertThat(cCurrencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
