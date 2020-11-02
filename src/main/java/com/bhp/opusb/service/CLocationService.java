package com.bhp.opusb.service;

import com.bhp.opusb.domain.CLocation;
import com.bhp.opusb.repository.CLocationRepository;
import com.bhp.opusb.service.dto.CLocationDTO;
import com.bhp.opusb.service.mapper.CLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CLocation}.
 */
@Service
@Transactional
public class CLocationService {

    private final Logger log = LoggerFactory.getLogger(CLocationService.class);

    private final CLocationRepository cLocationRepository;

    private final CLocationMapper cLocationMapper;

    public CLocationService(CLocationRepository cLocationRepository, CLocationMapper cLocationMapper) {
        this.cLocationRepository = cLocationRepository;
        this.cLocationMapper = cLocationMapper;
    }

    /**
     * Save a cLocation.
     *
     * @param cLocationDTO the entity to save.
     * @return the persisted entity.
     */
    public CLocationDTO save(CLocationDTO cLocationDTO) {
        log.debug("Request to save CLocation : {}", cLocationDTO);
        CLocation cLocation = cLocationMapper.toEntity(cLocationDTO);
        cLocation = cLocationRepository.save(cLocation);
        return cLocationMapper.toDto(cLocation);
    }

    /**
     * Get all the cLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CLocations");
        return cLocationRepository.findAll(pageable)
            .map(cLocationMapper::toDto);
    }

    /**
     * Get one cLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CLocationDTO> findOne(Long id) {
        log.debug("Request to get CLocation : {}", id);
        return cLocationRepository.findById(id)
            .map(cLocationMapper::toDto);
    }

    /**
     * Delete the cLocation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CLocation : {}", id);
        cLocationRepository.deleteById(id);
    }
}
