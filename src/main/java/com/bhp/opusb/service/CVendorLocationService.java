package com.bhp.opusb.service;

import com.bhp.opusb.domain.CVendorLocation;
import com.bhp.opusb.repository.CVendorLocationRepository;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.mapper.CVendorLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CVendorLocation}.
 */
@Service
@Transactional
public class CVendorLocationService {

    private final Logger log = LoggerFactory.getLogger(CVendorLocationService.class);

    private final CVendorLocationRepository cVendorLocationRepository;

    private final CVendorLocationMapper cVendorLocationMapper;

    public CVendorLocationService(CVendorLocationRepository cVendorLocationRepository, CVendorLocationMapper cVendorLocationMapper) {
        this.cVendorLocationRepository = cVendorLocationRepository;
        this.cVendorLocationMapper = cVendorLocationMapper;
    }

    /**
     * Save a cVendorLocation.
     *
     * @param cVendorLocationDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorLocationDTO save(CVendorLocationDTO cVendorLocationDTO) {
        log.debug("Request to save CVendorLocation : {}", cVendorLocationDTO);
        CVendorLocation cVendorLocation = cVendorLocationMapper.toEntity(cVendorLocationDTO);
        cVendorLocation = cVendorLocationRepository.save(cVendorLocation);
        return cVendorLocationMapper.toDto(cVendorLocation);
    }

    /**
     * Get all the cVendorLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorLocations");
        return cVendorLocationRepository.findAll(pageable)
            .map(cVendorLocationMapper::toDto);
    }

    /**
     * Get one cVendorLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorLocationDTO> findOne(Long id) {
        log.debug("Request to get CVendorLocation : {}", id);
        return cVendorLocationRepository.findById(id)
            .map(cVendorLocationMapper::toDto);
    }

    /**
     * Delete the cVendorLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorLocation : {}", id);
        cVendorLocationRepository.deleteById(id);
    }
}
