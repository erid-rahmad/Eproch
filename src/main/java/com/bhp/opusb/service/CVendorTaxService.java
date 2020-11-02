package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorTax;
import com.bhp.opusb.repository.CVendorTaxRepository;
import com.bhp.opusb.service.dto.CVendorTaxDTO;
import com.bhp.opusb.service.mapper.CVendorTaxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CVendorTax}.
 */
@Service
@Transactional
public class CVendorTaxService {

    private final Logger log = LoggerFactory.getLogger(CVendorTaxService.class);

    private final CVendorTaxRepository cVendorTaxRepository;

    private final CVendorTaxMapper cVendorTaxMapper;

    public CVendorTaxService(CVendorTaxRepository cVendorTaxRepository, CVendorTaxMapper cVendorTaxMapper) {
        this.cVendorTaxRepository = cVendorTaxRepository;
        this.cVendorTaxMapper = cVendorTaxMapper;
    }

    /**
     * Save a cVendorTax.
     *
     * @param cVendorTaxDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorTaxDTO save(CVendorTaxDTO cVendorTaxDTO) {
        log.debug("Request to save CVendorTax : {}", cVendorTaxDTO);
        CVendorTax cVendorTax = cVendorTaxMapper.toEntity(cVendorTaxDTO);
        cVendorTax = cVendorTaxRepository.save(cVendorTax);
        return cVendorTaxMapper.toDto(cVendorTax);
    }

    public List<CVendorTaxDTO> saveAll(List<CVendorTaxDTO> cVendorTaxDTOs, boolean eInvoice, boolean taxableEmployers,
            CVendor vendor, ADOrganization organization) {
        log.debug("Request to save CVendorTaxes. List size: {}", cVendorTaxDTOs.size());
        List<CVendorTax> vendorTaxes = cVendorTaxDTOs.stream()
            .map(vendorTax
                -> cVendorTaxMapper.toEntity(vendorTax)
                    .active(true)
                    .adOrganization(organization)
                    .eInvoice(eInvoice)
                    .taxableEmployers(taxableEmployers)
                    .vendor(vendor)
            ).collect(Collectors.toList());

        return cVendorTaxRepository.saveAll(vendorTaxes)
            .stream()
            .map(cVendorTaxMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all the cVendorTaxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorTaxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorTaxes");
        return cVendorTaxRepository.findAll(pageable)
            .map(cVendorTaxMapper::toDto);
    }

    /**
     * Get one cVendorTax by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorTaxDTO> findOne(Long id) {
        log.debug("Request to get CVendorTax : {}", id);
        return cVendorTaxRepository.findById(id)
            .map(cVendorTaxMapper::toDto);
    }

    /**
     * Delete the cVendorTax by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorTax : {}", id);
        cVendorTaxRepository.deleteById(id);
    }
}
