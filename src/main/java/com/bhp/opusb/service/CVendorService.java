package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorLocation;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.repository.CLocationRepository;
import com.bhp.opusb.repository.CVendorLocationRepository;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.dto.RegistrationDTO;
import com.bhp.opusb.service.mapper.CVendorMapper;
import com.bhp.opusb.service.mapper.RegistrationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CVendor}.
 */
@Service
@Transactional
public class CVendorService {

    private final Logger log = LoggerFactory.getLogger(CVendorService.class);

    private final ADOrganization organization;
    private final CVendorRepository cVendorRepository;
    private final CLocationRepository cLocationRepository;
    private final CVendorLocationRepository cVendorLocationRepository;
    private final CVendorBusinessCatService cVendorBusinessCatService;
    private final CRegistrationDocumentService cRegistrationDocumentService;
    private final CFunctionaryService cFunctionaryService;
    private final CVendorBankAcctService cVendorBankAcctService;
    private final CVendorTaxService cVendorTaxService;
    private final CVendorMapper cVendorMapper;
    private final RegistrationMapper registrationMapper;

    private final UserService userService;

    public CVendorService(
        CVendorRepository cVendorRepository,
        CVendorMapper cVendorMapper,
        CLocationRepository cLocationRepository,
        CVendorLocationRepository cVendorLocationRepository,
        CVendorBusinessCatService cVendorBusinessCatService,
        CRegistrationDocumentService cRegistrationDocumentService,
        CFunctionaryService cFunctionaryService,
        CVendorBankAcctService cVendorBankAcctService,
        CVendorTaxService cVendorTaxService,
        UserService userService
    ) {

        this.cVendorRepository = cVendorRepository;
        this.cLocationRepository = cLocationRepository;
        this.cVendorLocationRepository = cVendorLocationRepository;
        this.cVendorBusinessCatService = cVendorBusinessCatService;
        this.cRegistrationDocumentService = cRegistrationDocumentService;
        this.cFunctionaryService = cFunctionaryService;
        this.cVendorBankAcctService = cVendorBankAcctService;
        this.cVendorTaxService = cVendorTaxService;
        this.cVendorMapper = cVendorMapper;
        this.userService = userService;

        organization = new ADOrganization();
        organization.setId(1L);
        registrationMapper = new RegistrationMapper(organization);
    }

    public  List<User> registerVendor(RegistrationDTO registrationDTO){
        // Ensure vendor has generated ID.
        CVendor vendor = registrationMapper.toVendor(registrationDTO.getCompanyProfile());
        cVendorRepository.save(vendor);

        // Batch save locations.
        CLocation location = registrationMapper.toLocation(registrationDTO.getCompanyProfile());
        CLocation taxLocation = registrationMapper.toTaxLocation(registrationDTO.getCompanyProfile());
        cLocationRepository.saveAll(Arrays.asList(location, taxLocation));

        // Make links between vendor and locations.
        CVendorLocation vendorLocation = pairVendorLocation(vendor, location, false);
        CVendorLocation vendorTaxLocation = pairVendorLocation(vendor, taxLocation, true);
        cVendorLocationRepository.saveAll(Arrays.asList(vendorLocation, vendorTaxLocation));

        // Make links between vendor and business categories.
        cVendorBusinessCatService.saveAll(registrationDTO.getBusinesses(), vendor, organization);

        // Batch save registration documents.
        cRegistrationDocumentService.saveAll(registrationDTO.getMainDocuments(), vendor, organization);
        cRegistrationDocumentService.saveAll(registrationDTO.getAdditionalDocuments(), vendor, organization);

        // Save persons and map each person with the user entity.
        List<User> users = new ArrayList<>();
        for (CPersonInChargeDTO contact : registrationDTO.getContacts()) {
            users.add(userService.registerUser(contact, vendor, organization));
        }

        // Batch save company functionaries.
        cFunctionaryService.saveAll(registrationDTO.getFunctionaries(), vendor, organization);

        // Batch save bank account and payment informations.
        cVendorBankAcctService.saveAll(registrationDTO.getPayments(), vendor, organization);

        cVendorTaxService.saveAll(registrationDTO.getTaxes(), vendor, organization);
        return users;
    }

    /**
     * Save a cVendor.
     *
     * @param cVendorDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorDTO save(CVendorDTO cVendorDTO) {
        log.debug("Request to save CVendor : {}", cVendorDTO);
        CVendor cVendor = cVendorMapper.toEntity(cVendorDTO);
        cVendor = cVendorRepository.save(cVendor);
        return cVendorMapper.toDto(cVendor);
    }

    /**
     * Get all the cVendors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendors");
        return cVendorRepository.findAll(pageable)
            .map(cVendorMapper::toDto);
    }

    /**
     * Get one cVendor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorDTO> findOne(Long id) {
        log.debug("Request to get CVendor : {}", id);
        return cVendorRepository.findById(id)
            .map(cVendorMapper::toDto);
    }

    /**
     * Delete the cVendor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendor : {}", id);
        cVendorRepository.deleteById(id);
    }

    private CVendorLocation pairVendorLocation(CVendor vendor, CLocation location, boolean taxAddress) {
        CVendorLocation vendorLocation = new CVendorLocation();
        vendorLocation.active(true)
            .adOrganization(organization)
            .vendor(vendor)
            .location(location)
            .taxInvoiceAddress(taxAddress);

        return vendorLocation;
    }
}
