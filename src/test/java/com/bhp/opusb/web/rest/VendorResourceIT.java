package com.bhp.opusb.web.rest;

import com.bhp.opusb.OpusWebApp;
import com.bhp.opusb.domain.Vendor;
import com.bhp.opusb.domain.CompanyFunctionary;
import com.bhp.opusb.domain.Location;
import com.bhp.opusb.domain.PersonInCharge;
import com.bhp.opusb.domain.SupportingDocument;
import com.bhp.opusb.domain.BusinessCategory;
import com.bhp.opusb.repository.VendorRepository;
import com.bhp.opusb.service.VendorService;
import com.bhp.opusb.service.dto.VendorDTO;
import com.bhp.opusb.service.mapper.VendorMapper;
import com.bhp.opusb.service.dto.VendorCriteria;
import com.bhp.opusb.service.VendorQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bhp.opusb.domain.enumeration.VendorType;
import com.bhp.opusb.domain.enumeration.PaymentCategory;
import com.bhp.opusb.domain.enumeration.VendorApprovalStatus;
/**
 * Integration tests for the {@link VendorResource} REST controller.
 */
@SpringBootTest(classes = OpusWebApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VendorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_NPWP = 1L;
    private static final Long UPDATED_NPWP = 2L;
    private static final Long SMALLER_NPWP = 1L - 1L;

    private static final Boolean DEFAULT_BRANCH = false;
    private static final Boolean UPDATED_BRANCH = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

    private static final VendorType DEFAULT_TYPE = VendorType.COMPANY;
    private static final VendorType UPDATED_TYPE = VendorType.PROFESSIONAL;

    private static final PaymentCategory DEFAULT_PAYMENT_CATEGORY = PaymentCategory.RED;
    private static final PaymentCategory UPDATED_PAYMENT_CATEGORY = PaymentCategory.GREEN;

    private static final VendorApprovalStatus DEFAULT_APPROVAL_STATUS = VendorApprovalStatus.PENDING;
    private static final VendorApprovalStatus UPDATED_APPROVAL_STATUS = VendorApprovalStatus.REJECTED;

    @Autowired
    private VendorRepository vendorRepository;

    @Mock
    private VendorRepository vendorRepositoryMock;

    @Autowired
    private VendorMapper vendorMapper;

    @Mock
    private VendorService vendorServiceMock;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorQueryService vendorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendorMockMvc;

    private Vendor vendor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendor createEntity(EntityManager em) {
        Vendor vendor = new Vendor()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .npwp(DEFAULT_NPWP)
            .branch(DEFAULT_BRANCH)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .website(DEFAULT_WEBSITE)
            .type(DEFAULT_TYPE)
            .paymentCategory(DEFAULT_PAYMENT_CATEGORY)
            .approvalStatus(DEFAULT_APPROVAL_STATUS);
        return vendor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vendor createUpdatedEntity(EntityManager em) {
        Vendor vendor = new Vendor()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .npwp(UPDATED_NPWP)
            .branch(UPDATED_BRANCH)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .website(UPDATED_WEBSITE)
            .type(UPDATED_TYPE)
            .paymentCategory(UPDATED_PAYMENT_CATEGORY)
            .approvalStatus(UPDATED_APPROVAL_STATUS);
        return vendor;
    }

    @BeforeEach
    public void initTest() {
        vendor = createEntity(em);
    }

    @Test
    @Transactional
    public void createVendor() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isCreated());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate + 1);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVendor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVendor.getNpwp()).isEqualTo(DEFAULT_NPWP);
        assertThat(testVendor.isBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testVendor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVendor.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testVendor.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testVendor.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testVendor.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testVendor.getPaymentCategory()).isEqualTo(DEFAULT_PAYMENT_CATEGORY);
        assertThat(testVendor.getApprovalStatus()).isEqualTo(DEFAULT_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    public void createVendorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendorRepository.findAll().size();

        // Create the Vendor with an existing ID
        vendor.setId(1L);
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setName(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNpwpIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setNpwp(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setEmail(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setPhone(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setType(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setPaymentCategory(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApprovalStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendorRepository.findAll().size();
        // set the field null
        vendor.setApprovalStatus(null);

        // Create the Vendor, which fails.
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        restVendorMockMvc.perform(post("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendors() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].npwp").value(hasItem(DEFAULT_NPWP.intValue())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllVendorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(vendorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendorMockMvc.perform(get("/api/vendors?eagerload=true"))
            .andExpect(status().isOk());

        verify(vendorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllVendorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vendorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVendorMockMvc.perform(get("/api/vendors?eagerload=true"))
            .andExpect(status().isOk());

        verify(vendorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", vendor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vendor.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.npwp").value(DEFAULT_NPWP.intValue()))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.paymentCategory").value(DEFAULT_PAYMENT_CATEGORY.toString()))
            .andExpect(jsonPath("$.approvalStatus").value(DEFAULT_APPROVAL_STATUS.toString()));
    }


    @Test
    @Transactional
    public void getVendorsByIdFiltering() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        Long id = vendor.getId();

        defaultVendorShouldBeFound("id.equals=" + id);
        defaultVendorShouldNotBeFound("id.notEquals=" + id);

        defaultVendorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendorShouldNotBeFound("id.greaterThan=" + id);

        defaultVendorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVendorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where code equals to DEFAULT_CODE
        defaultVendorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the vendorList where code equals to UPDATED_CODE
        defaultVendorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllVendorsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where code not equals to DEFAULT_CODE
        defaultVendorShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the vendorList where code not equals to UPDATED_CODE
        defaultVendorShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllVendorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultVendorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the vendorList where code equals to UPDATED_CODE
        defaultVendorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllVendorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where code is not null
        defaultVendorShouldBeFound("code.specified=true");

        // Get all the vendorList where code is null
        defaultVendorShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendorsByCodeContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where code contains DEFAULT_CODE
        defaultVendorShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the vendorList where code contains UPDATED_CODE
        defaultVendorShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllVendorsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where code does not contain DEFAULT_CODE
        defaultVendorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the vendorList where code does not contain UPDATED_CODE
        defaultVendorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllVendorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where name equals to DEFAULT_NAME
        defaultVendorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vendorList where name equals to UPDATED_NAME
        defaultVendorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where name not equals to DEFAULT_NAME
        defaultVendorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the vendorList where name not equals to UPDATED_NAME
        defaultVendorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVendorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vendorList where name equals to UPDATED_NAME
        defaultVendorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where name is not null
        defaultVendorShouldBeFound("name.specified=true");

        // Get all the vendorList where name is null
        defaultVendorShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendorsByNameContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where name contains DEFAULT_NAME
        defaultVendorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the vendorList where name contains UPDATED_NAME
        defaultVendorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllVendorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where name does not contain DEFAULT_NAME
        defaultVendorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the vendorList where name does not contain UPDATED_NAME
        defaultVendorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllVendorsByNpwpIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp equals to DEFAULT_NPWP
        defaultVendorShouldBeFound("npwp.equals=" + DEFAULT_NPWP);

        // Get all the vendorList where npwp equals to UPDATED_NPWP
        defaultVendorShouldNotBeFound("npwp.equals=" + UPDATED_NPWP);
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp not equals to DEFAULT_NPWP
        defaultVendorShouldNotBeFound("npwp.notEquals=" + DEFAULT_NPWP);

        // Get all the vendorList where npwp not equals to UPDATED_NPWP
        defaultVendorShouldBeFound("npwp.notEquals=" + UPDATED_NPWP);
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp in DEFAULT_NPWP or UPDATED_NPWP
        defaultVendorShouldBeFound("npwp.in=" + DEFAULT_NPWP + "," + UPDATED_NPWP);

        // Get all the vendorList where npwp equals to UPDATED_NPWP
        defaultVendorShouldNotBeFound("npwp.in=" + UPDATED_NPWP);
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp is not null
        defaultVendorShouldBeFound("npwp.specified=true");

        // Get all the vendorList where npwp is null
        defaultVendorShouldNotBeFound("npwp.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp is greater than or equal to DEFAULT_NPWP
        defaultVendorShouldBeFound("npwp.greaterThanOrEqual=" + DEFAULT_NPWP);

        // Get all the vendorList where npwp is greater than or equal to UPDATED_NPWP
        defaultVendorShouldNotBeFound("npwp.greaterThanOrEqual=" + UPDATED_NPWP);
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp is less than or equal to DEFAULT_NPWP
        defaultVendorShouldBeFound("npwp.lessThanOrEqual=" + DEFAULT_NPWP);

        // Get all the vendorList where npwp is less than or equal to SMALLER_NPWP
        defaultVendorShouldNotBeFound("npwp.lessThanOrEqual=" + SMALLER_NPWP);
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsLessThanSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp is less than DEFAULT_NPWP
        defaultVendorShouldNotBeFound("npwp.lessThan=" + DEFAULT_NPWP);

        // Get all the vendorList where npwp is less than UPDATED_NPWP
        defaultVendorShouldBeFound("npwp.lessThan=" + UPDATED_NPWP);
    }

    @Test
    @Transactional
    public void getAllVendorsByNpwpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where npwp is greater than DEFAULT_NPWP
        defaultVendorShouldNotBeFound("npwp.greaterThan=" + DEFAULT_NPWP);

        // Get all the vendorList where npwp is greater than SMALLER_NPWP
        defaultVendorShouldBeFound("npwp.greaterThan=" + SMALLER_NPWP);
    }


    @Test
    @Transactional
    public void getAllVendorsByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where branch equals to DEFAULT_BRANCH
        defaultVendorShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the vendorList where branch equals to UPDATED_BRANCH
        defaultVendorShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllVendorsByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where branch not equals to DEFAULT_BRANCH
        defaultVendorShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the vendorList where branch not equals to UPDATED_BRANCH
        defaultVendorShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllVendorsByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultVendorShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the vendorList where branch equals to UPDATED_BRANCH
        defaultVendorShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void getAllVendorsByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where branch is not null
        defaultVendorShouldBeFound("branch.specified=true");

        // Get all the vendorList where branch is null
        defaultVendorShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where email equals to DEFAULT_EMAIL
        defaultVendorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the vendorList where email equals to UPDATED_EMAIL
        defaultVendorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllVendorsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where email not equals to DEFAULT_EMAIL
        defaultVendorShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the vendorList where email not equals to UPDATED_EMAIL
        defaultVendorShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllVendorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultVendorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the vendorList where email equals to UPDATED_EMAIL
        defaultVendorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllVendorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where email is not null
        defaultVendorShouldBeFound("email.specified=true");

        // Get all the vendorList where email is null
        defaultVendorShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where email contains DEFAULT_EMAIL
        defaultVendorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the vendorList where email contains UPDATED_EMAIL
        defaultVendorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllVendorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where email does not contain DEFAULT_EMAIL
        defaultVendorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the vendorList where email does not contain UPDATED_EMAIL
        defaultVendorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllVendorsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where phone equals to DEFAULT_PHONE
        defaultVendorShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the vendorList where phone equals to UPDATED_PHONE
        defaultVendorShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllVendorsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where phone not equals to DEFAULT_PHONE
        defaultVendorShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the vendorList where phone not equals to UPDATED_PHONE
        defaultVendorShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllVendorsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultVendorShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the vendorList where phone equals to UPDATED_PHONE
        defaultVendorShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllVendorsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where phone is not null
        defaultVendorShouldBeFound("phone.specified=true");

        // Get all the vendorList where phone is null
        defaultVendorShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendorsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where phone contains DEFAULT_PHONE
        defaultVendorShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the vendorList where phone contains UPDATED_PHONE
        defaultVendorShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllVendorsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where phone does not contain DEFAULT_PHONE
        defaultVendorShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the vendorList where phone does not contain UPDATED_PHONE
        defaultVendorShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllVendorsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where fax equals to DEFAULT_FAX
        defaultVendorShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the vendorList where fax equals to UPDATED_FAX
        defaultVendorShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllVendorsByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where fax not equals to DEFAULT_FAX
        defaultVendorShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the vendorList where fax not equals to UPDATED_FAX
        defaultVendorShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllVendorsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultVendorShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the vendorList where fax equals to UPDATED_FAX
        defaultVendorShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllVendorsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where fax is not null
        defaultVendorShouldBeFound("fax.specified=true");

        // Get all the vendorList where fax is null
        defaultVendorShouldNotBeFound("fax.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendorsByFaxContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where fax contains DEFAULT_FAX
        defaultVendorShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the vendorList where fax contains UPDATED_FAX
        defaultVendorShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllVendorsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where fax does not contain DEFAULT_FAX
        defaultVendorShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the vendorList where fax does not contain UPDATED_FAX
        defaultVendorShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }


    @Test
    @Transactional
    public void getAllVendorsByWebsiteIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where website equals to DEFAULT_WEBSITE
        defaultVendorShouldBeFound("website.equals=" + DEFAULT_WEBSITE);

        // Get all the vendorList where website equals to UPDATED_WEBSITE
        defaultVendorShouldNotBeFound("website.equals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllVendorsByWebsiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where website not equals to DEFAULT_WEBSITE
        defaultVendorShouldNotBeFound("website.notEquals=" + DEFAULT_WEBSITE);

        // Get all the vendorList where website not equals to UPDATED_WEBSITE
        defaultVendorShouldBeFound("website.notEquals=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllVendorsByWebsiteIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where website in DEFAULT_WEBSITE or UPDATED_WEBSITE
        defaultVendorShouldBeFound("website.in=" + DEFAULT_WEBSITE + "," + UPDATED_WEBSITE);

        // Get all the vendorList where website equals to UPDATED_WEBSITE
        defaultVendorShouldNotBeFound("website.in=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllVendorsByWebsiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where website is not null
        defaultVendorShouldBeFound("website.specified=true");

        // Get all the vendorList where website is null
        defaultVendorShouldNotBeFound("website.specified=false");
    }
                @Test
    @Transactional
    public void getAllVendorsByWebsiteContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where website contains DEFAULT_WEBSITE
        defaultVendorShouldBeFound("website.contains=" + DEFAULT_WEBSITE);

        // Get all the vendorList where website contains UPDATED_WEBSITE
        defaultVendorShouldNotBeFound("website.contains=" + UPDATED_WEBSITE);
    }

    @Test
    @Transactional
    public void getAllVendorsByWebsiteNotContainsSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where website does not contain DEFAULT_WEBSITE
        defaultVendorShouldNotBeFound("website.doesNotContain=" + DEFAULT_WEBSITE);

        // Get all the vendorList where website does not contain UPDATED_WEBSITE
        defaultVendorShouldBeFound("website.doesNotContain=" + UPDATED_WEBSITE);
    }


    @Test
    @Transactional
    public void getAllVendorsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where type equals to DEFAULT_TYPE
        defaultVendorShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the vendorList where type equals to UPDATED_TYPE
        defaultVendorShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVendorsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where type not equals to DEFAULT_TYPE
        defaultVendorShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the vendorList where type not equals to UPDATED_TYPE
        defaultVendorShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVendorsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultVendorShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the vendorList where type equals to UPDATED_TYPE
        defaultVendorShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllVendorsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where type is not null
        defaultVendorShouldBeFound("type.specified=true");

        // Get all the vendorList where type is null
        defaultVendorShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByPaymentCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where paymentCategory equals to DEFAULT_PAYMENT_CATEGORY
        defaultVendorShouldBeFound("paymentCategory.equals=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the vendorList where paymentCategory equals to UPDATED_PAYMENT_CATEGORY
        defaultVendorShouldNotBeFound("paymentCategory.equals=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllVendorsByPaymentCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where paymentCategory not equals to DEFAULT_PAYMENT_CATEGORY
        defaultVendorShouldNotBeFound("paymentCategory.notEquals=" + DEFAULT_PAYMENT_CATEGORY);

        // Get all the vendorList where paymentCategory not equals to UPDATED_PAYMENT_CATEGORY
        defaultVendorShouldBeFound("paymentCategory.notEquals=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllVendorsByPaymentCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where paymentCategory in DEFAULT_PAYMENT_CATEGORY or UPDATED_PAYMENT_CATEGORY
        defaultVendorShouldBeFound("paymentCategory.in=" + DEFAULT_PAYMENT_CATEGORY + "," + UPDATED_PAYMENT_CATEGORY);

        // Get all the vendorList where paymentCategory equals to UPDATED_PAYMENT_CATEGORY
        defaultVendorShouldNotBeFound("paymentCategory.in=" + UPDATED_PAYMENT_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllVendorsByPaymentCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where paymentCategory is not null
        defaultVendorShouldBeFound("paymentCategory.specified=true");

        // Get all the vendorList where paymentCategory is null
        defaultVendorShouldNotBeFound("paymentCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByApprovalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where approvalStatus equals to DEFAULT_APPROVAL_STATUS
        defaultVendorShouldBeFound("approvalStatus.equals=" + DEFAULT_APPROVAL_STATUS);

        // Get all the vendorList where approvalStatus equals to UPDATED_APPROVAL_STATUS
        defaultVendorShouldNotBeFound("approvalStatus.equals=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllVendorsByApprovalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where approvalStatus not equals to DEFAULT_APPROVAL_STATUS
        defaultVendorShouldNotBeFound("approvalStatus.notEquals=" + DEFAULT_APPROVAL_STATUS);

        // Get all the vendorList where approvalStatus not equals to UPDATED_APPROVAL_STATUS
        defaultVendorShouldBeFound("approvalStatus.notEquals=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllVendorsByApprovalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where approvalStatus in DEFAULT_APPROVAL_STATUS or UPDATED_APPROVAL_STATUS
        defaultVendorShouldBeFound("approvalStatus.in=" + DEFAULT_APPROVAL_STATUS + "," + UPDATED_APPROVAL_STATUS);

        // Get all the vendorList where approvalStatus equals to UPDATED_APPROVAL_STATUS
        defaultVendorShouldNotBeFound("approvalStatus.in=" + UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    public void getAllVendorsByApprovalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        // Get all the vendorList where approvalStatus is not null
        defaultVendorShouldBeFound("approvalStatus.specified=true");

        // Get all the vendorList where approvalStatus is null
        defaultVendorShouldNotBeFound("approvalStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendorsByCompanyFunctionaryIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        CompanyFunctionary companyFunctionary = CompanyFunctionaryResourceIT.createEntity(em);
        em.persist(companyFunctionary);
        em.flush();
        vendor.addCompanyFunctionary(companyFunctionary);
        vendorRepository.saveAndFlush(vendor);
        Long companyFunctionaryId = companyFunctionary.getId();

        // Get all the vendorList where companyFunctionary equals to companyFunctionaryId
        defaultVendorShouldBeFound("companyFunctionaryId.equals=" + companyFunctionaryId);

        // Get all the vendorList where companyFunctionary equals to companyFunctionaryId + 1
        defaultVendorShouldNotBeFound("companyFunctionaryId.equals=" + (companyFunctionaryId + 1));
    }


    @Test
    @Transactional
    public void getAllVendorsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        Location location = LocationResourceIT.createEntity(em);
        em.persist(location);
        em.flush();
        vendor.addLocation(location);
        vendorRepository.saveAndFlush(vendor);
        Long locationId = location.getId();

        // Get all the vendorList where location equals to locationId
        defaultVendorShouldBeFound("locationId.equals=" + locationId);

        // Get all the vendorList where location equals to locationId + 1
        defaultVendorShouldNotBeFound("locationId.equals=" + (locationId + 1));
    }


    @Test
    @Transactional
    public void getAllVendorsByPersonInChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        PersonInCharge personInCharge = PersonInChargeResourceIT.createEntity(em);
        em.persist(personInCharge);
        em.flush();
        vendor.addPersonInCharge(personInCharge);
        vendorRepository.saveAndFlush(vendor);
        Long personInChargeId = personInCharge.getId();

        // Get all the vendorList where personInCharge equals to personInChargeId
        defaultVendorShouldBeFound("personInChargeId.equals=" + personInChargeId);

        // Get all the vendorList where personInCharge equals to personInChargeId + 1
        defaultVendorShouldNotBeFound("personInChargeId.equals=" + (personInChargeId + 1));
    }


    @Test
    @Transactional
    public void getAllVendorsBySupportingDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        SupportingDocument supportingDocument = SupportingDocumentResourceIT.createEntity(em);
        em.persist(supportingDocument);
        em.flush();
        vendor.addSupportingDocument(supportingDocument);
        vendorRepository.saveAndFlush(vendor);
        Long supportingDocumentId = supportingDocument.getId();

        // Get all the vendorList where supportingDocument equals to supportingDocumentId
        defaultVendorShouldBeFound("supportingDocumentId.equals=" + supportingDocumentId);

        // Get all the vendorList where supportingDocument equals to supportingDocumentId + 1
        defaultVendorShouldNotBeFound("supportingDocumentId.equals=" + (supportingDocumentId + 1));
    }


    @Test
    @Transactional
    public void getAllVendorsByBusinessCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);
        BusinessCategory businessCategory = BusinessCategoryResourceIT.createEntity(em);
        em.persist(businessCategory);
        em.flush();
        vendor.addBusinessCategory(businessCategory);
        vendorRepository.saveAndFlush(vendor);
        Long businessCategoryId = businessCategory.getId();

        // Get all the vendorList where businessCategory equals to businessCategoryId
        defaultVendorShouldBeFound("businessCategoryId.equals=" + businessCategoryId);

        // Get all the vendorList where businessCategory equals to businessCategoryId + 1
        defaultVendorShouldNotBeFound("businessCategoryId.equals=" + (businessCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendorShouldBeFound(String filter) throws Exception {
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vendor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].npwp").value(hasItem(DEFAULT_NPWP.intValue())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentCategory").value(hasItem(DEFAULT_PAYMENT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].approvalStatus").value(hasItem(DEFAULT_APPROVAL_STATUS.toString())));

        // Check, that the count call also returns 1
        restVendorMockMvc.perform(get("/api/vendors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendorShouldNotBeFound(String filter) throws Exception {
        restVendorMockMvc.perform(get("/api/vendors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendorMockMvc.perform(get("/api/vendors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingVendor() throws Exception {
        // Get the vendor
        restVendorMockMvc.perform(get("/api/vendors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Update the vendor
        Vendor updatedVendor = vendorRepository.findById(vendor.getId()).get();
        // Disconnect from session so that the updates on updatedVendor are not directly saved in db
        em.detach(updatedVendor);
        updatedVendor
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .npwp(UPDATED_NPWP)
            .branch(UPDATED_BRANCH)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .website(UPDATED_WEBSITE)
            .type(UPDATED_TYPE)
            .paymentCategory(UPDATED_PAYMENT_CATEGORY)
            .approvalStatus(UPDATED_APPROVAL_STATUS);
        VendorDTO vendorDTO = vendorMapper.toDto(updatedVendor);

        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isOk());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
        Vendor testVendor = vendorList.get(vendorList.size() - 1);
        assertThat(testVendor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVendor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVendor.getNpwp()).isEqualTo(UPDATED_NPWP);
        assertThat(testVendor.isBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testVendor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVendor.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testVendor.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testVendor.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testVendor.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testVendor.getPaymentCategory()).isEqualTo(UPDATED_PAYMENT_CATEGORY);
        assertThat(testVendor.getApprovalStatus()).isEqualTo(UPDATED_APPROVAL_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingVendor() throws Exception {
        int databaseSizeBeforeUpdate = vendorRepository.findAll().size();

        // Create the Vendor
        VendorDTO vendorDTO = vendorMapper.toDto(vendor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendorMockMvc.perform(put("/api/vendors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vendor in the database
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVendor() throws Exception {
        // Initialize the database
        vendorRepository.saveAndFlush(vendor);

        int databaseSizeBeforeDelete = vendorRepository.findAll().size();

        // Delete the vendor
        restVendorMockMvc.perform(delete("/api/vendors/{id}", vendor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vendor> vendorList = vendorRepository.findAll();
        assertThat(vendorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
