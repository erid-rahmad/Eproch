package com.bhp.opusb.service;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CVendorRepository;
import com.bhp.opusb.service.dto.CVendorDTO;
import com.bhp.opusb.service.mapper.CVendorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CVendor}.
 */
@Service
@Transactional
public class CVendorService {

    private final Logger log = LoggerFactory.getLogger(CVendorService.class);

    private final CVendorRepository cVendorRepository;

    private final CVendorMapper cVendorMapper;

    public CVendorService(CVendorRepository cVendorRepository, CVendorMapper cVendorMapper) {
        this.cVendorRepository = cVendorRepository;
        this.cVendorMapper = cVendorMapper;
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
