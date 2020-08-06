package com.bhp.opusb.service;

import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.repository.AuthorityRepository;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.security.AuthoritiesConstants;
import com.bhp.opusb.service.dto.CLocationDTO;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.dto.CVendorTaxDTO;
import com.bhp.opusb.service.dto.RegistrationDTO;
import com.bhp.opusb.service.dto.UserDTO;
import com.bhp.opusb.service.dto.RegistrationDTO.CompanyProfile;
import com.bhp.opusb.service.dto.RegistrationDTO.LoginDetail;
import com.bhp.opusb.service.mapper.CVendorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.security.RandomUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CVendor}.
 */
@Service
@Transactional
public class CVendorService {

    private final Logger log = LoggerFactory.getLogger(CVendorService.class);

    private final CVendorRepository cVendorRepository;
    private final AuthorityRepository authorityRepository;

    private final UserService userService;
    private final CLocationService cLocationService;
    private final CVendorLocationService cVendorLocationService;
    private final CVendorBusinessCatService cVendorBusinessCatService;
    private final CPersonInChargeService cPersonInChargeService;
    private final CVendorBankAcctService cVendorBankAcctService;
    private final CVendorTaxService cVendorTaxService;
    private final CPicBusinessCatService cPicBusinessCatService;

    private final CVendorMapper cVendorMapper;

    public CVendorService(
        CVendorRepository cVendorRepository, 
        AuthorityRepository authorityRepository, 
        UserService userService,
        CLocationService cLocationService, 
        CVendorLocationService cVendorLocationService,
        CVendorBusinessCatService cVendorBusinessCatService,
        CPersonInChargeService cPersonInChargeService,
        CVendorBankAcctService cVendorBankAcctService,
        CVendorTaxService cVendorTaxService,
        CPicBusinessCatService cPicBusinessCatService,

        CVendorMapper cVendorMapper) {

            this.cVendorRepository = cVendorRepository;
            this.authorityRepository = authorityRepository;
            this.userService = userService;
            this.cLocationService = cLocationService;
            this.cVendorLocationService = cVendorLocationService;
            this.cVendorBusinessCatService = cVendorBusinessCatService;
            this.cPersonInChargeService = cPersonInChargeService;
            this.cVendorBankAcctService = cVendorBankAcctService;
            this.cVendorTaxService = cVendorTaxService;
            this.cPicBusinessCatService = cPicBusinessCatService;

            this.cVendorMapper = cVendorMapper;
    }

    public CVendor registerVendor(RegistrationDTO registrationDTO){
        CVendor vendor = new CVendor();

        UserDTO userDto = new UserDTO();
        CPersonInChargeDTO cPersonInChargeDto = new CPersonInChargeDTO();
        CVendorDTO vendorDto = new CVendorDTO();
        CLocationDTO locationDto = new CLocationDTO();
        CLocationDTO locationNpwpDto = new CLocationDTO();
        CVendorLocationDTO vendorLocationDto = new CVendorLocationDTO();
        CVendorLocationDTO vendorLocationNpwpDto = new CVendorLocationDTO();
        
        LoginDetail loginDetail = registrationDTO.getLoginDetail();
        CompanyProfile company = registrationDTO.getCompanyProfile();
        Map<String, Boolean> taxInformations = registrationDTO.getTaxInformations();
        
        vendorDto.setName(company.getName());
        vendorDto.setType(company.getType());
        vendorDto.setBranch(company.getBranch());
        vendorDto.setPhone(company.getPhone());
        vendorDto.setFax(company.getFax());
        vendorDto.setEmail(company.getEmail());
        vendorDto.setWebsite(company.getWebsite());
        vendorDto.setTaxIdNo(company.getNpwp());
        vendorDto.setTaxIdName(company.getNpwpName());
        vendorDto.setPaymentCategory("red");
        vendorDto.setApprovalStatus("PENDING");
        vendorDto.setAdOrganizationId((long) 1);
        vendorDto.setActive(false);
        vendorDto = save(vendorDto);
        
        locationDto.setStreetAddress(company.getAddress());
        locationDto.setPostalCode(company.getPostalCode());
        locationDto.setCityId(company.getCity());
        locationDto.setAdOrganizationId((long) 1);
        locationDto.setActive(true);
        locationDto = cLocationService.save(locationDto);

        locationNpwpDto.setStreetAddress(company.getNpwpAddress());
        locationNpwpDto.setPostalCode(company.getNpwpPostalCode());
        locationNpwpDto.setCityId(company.getNpwpCity());
        locationNpwpDto.setAdOrganizationId((long) 1);
        locationNpwpDto.setActive(true);
        locationNpwpDto = cLocationService.save(locationNpwpDto);

        vendorLocationDto.setVendorId(vendorDto.getId());
        vendorLocationDto.setLocationId(locationDto.getId());
        vendorLocationDto.setTaxInvoiceAddress(false);
        vendorLocationDto.setAdOrganizationId((long) 1);
        vendorLocationDto.setActive(true);
        vendorLocationDto = cVendorLocationService.save(vendorLocationDto);

        vendorLocationNpwpDto.setVendorId(vendorDto.getId());
        vendorLocationNpwpDto.setLocationId(locationNpwpDto.getId());
        vendorLocationNpwpDto.setTaxInvoiceAddress(true);
        vendorLocationNpwpDto.setAdOrganizationId((long) 1);
        vendorLocationNpwpDto.setActive(true);
        vendorLocationNpwpDto = cVendorLocationService.save(vendorLocationNpwpDto);
        
        long vendorId = vendorDto.getId();
        //business category
        List<CVendorBusinessCatDTO> vendorBusinesses = registrationDTO.getBusinesses().stream().map((id) -> {
            CVendorBusinessCatDTO data = new CVendorBusinessCatDTO();
            data.setVendorId(vendorId);
            data.setBusinessCategoryId(id);
            data.setAdOrganizationId((long) 1);
            data.setActive(true);
            return data;
        }).collect(Collectors.toList());
        cVendorBusinessCatService.saveAll(vendorBusinesses);
/*
        // cek lagi
        for (CRegistrationDocumentDTO document : registrationDTO.getMainDocument()) {
            document.setVendorId(vendorId);
            document.setAdOrganizationId((long) 1);
            document.setActive(true);
        }
        cRegistrationDocumentService.saveAll(registrationDTO.getMainDocument());

        for (CRegistrationDocumentDTO document : registrationDTO.getAdditionalDocument()) {
            document.setVendorId(vendorId);
            document.setAdOrganizationId((long) 1);
            document.setActive(true);
        }
        cRegistrationDocumentService.saveAll(registrationDTO.getAdditionalDocument());
*/

        

        //pic
/*
        User user = userService
            .createUser(
                managedUserVM.getLogin(), 
                managedUserVM.getPassword(),
                managedUserVM.getFirstName(), 
                managedUserVM.getLastName(),
                managedUserVM.getEmail().toLowerCase(), 
                managedUserVM.getLangKey(),
                managedUserVM.getPhone()
            );
            mailService.sendActivationEmail(user);
*/
/*
        List<CPersonInChargeDTO> contacts = registrationDTO.getContacts().stream().map((login) -> {
            CPersonInChargeDTO data = new CPersonInChargeDTO();

            data.setUserId(login.getId());
            data.setUserLogin(loginDetail.getLogin());
            data.setPassword(loginDetail.getPassword());     // new user gets initially a generated password
            //data.setFirstName("firstName");
            //data.setLastName("lastName");
            //data.setEmail(loginDetail.getEmail());
            //data.setLangKey("en");
            //data.setActivated(false);

            data.setFunctionary(false);
            data.setVendorId(vendorId);
            data.setAdOrganizationId((long) 1);
            data.setActive(true);
            return data;
        }).collect(Collectors.toList());
*/
        
        for (CPersonInChargeDTO picContact : registrationDTO.getContacts()) {
            picContact.setFunctionary(false);
            //picContact.setUserLogin("user123");
            picContact.setVendorId(vendorId);
            //picContact.setId((long) 74563);
            picContact.setAdOrganizationId((long) 1);
            picContact.setActive(true);
            User user = userService.registerUser(picContact);
            for(Long id : picContact.getBusinessCategories() ){
                CPicBusinessCatDTO picBusinessCategory = new  CPicBusinessCatDTO();
                picBusinessCategory.setBusinessCategoryId(id);
                picBusinessCategory.setContactId(user.getId());
                picBusinessCategory.setAdOrganizationId((long) 1);
                picBusinessCategory.setActive(true);
                cPicBusinessCatService.save(picBusinessCategory);
            }
            
        }




        /*User user = userService
        .registerUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
            managedUserVM.getFirstName(), managedUserVM.getLastName(),
            managedUserVM.getEmail().toLowerCase(), managedUserVM.getLangKey(),
            managedUserVM.getPhone());
*/


/*
        for (CPersonInChargeDTO picContact : registrationDTO.getContacts()) {
            picContact.setFunctionary(false);
            picContact.setVendorId(vendorId);
            picContact.setAdOrganizationId((long) 1);
            picContact.setActive(true);
        }
        cPersonInChargeService.saveAll(registrationDTO.getContacts());

        for (CPersonInChargeDTO picFunctionary : registrationDTO.getFunctionaries()) {
            picFunctionary.setFunctionary(true);
            picFunctionary.setVendorId(vendorId);
            picFunctionary.setAdOrganizationId((long) 1);
            picFunctionary.setActive(true);
        }
        cPersonInChargeService.saveAll(registrationDTO.getFunctionaries());
*/
/*
        //payment information
        for (CVendorBankAcctDTO document : registrationDTO.getPayments()) {
            document.setVendorId(vendorId);
            document.setAdOrganizationId((long) 1);
            document.setActive(true);
        }
        cVendorBankAcctService.saveAll(registrationDTO.getPayments());
*/
        //tax
        for (CVendorTaxDTO tax : registrationDTO.getTaxRates()) {
            tax.setIsFaktur(taxInformations.get("efaktur"));
            tax.setIsPkp(taxInformations.get("pkp"));
            tax.setVendorId(vendorId);
            tax.setAdOrganizationId((long) 1);
            tax.setActive(true);
        }
        cVendorTaxService.saveAll(registrationDTO.getTaxRates());
        
        return vendor;
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
}
