package com.bhp.opusb.service;

import com.bhp.opusb.domain.CRegion;
import com.bhp.opusb.repository.CRegionRepository;
import com.bhp.opusb.service.dto.CRegionDTO;
import com.bhp.opusb.service.mapper.CRegionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CRegion}.
 */
@Service
@Transactional
public class CRegionService {

    private final Logger log = LoggerFactory.getLogger(CRegionService.class);

    private final CRegionRepository cRegionRepository;

    private final CRegionMapper cRegionMapper;

    public CRegionService(CRegionRepository cRegionRepository, CRegionMapper cRegionMapper) {
        this.cRegionRepository = cRegionRepository;
        this.cRegionMapper = cRegionMapper;
    }

    /**
     * Save a cRegion.
     *
     * @param cRegionDTO the entity to save.
     * @return the persisted entity.
     */
    public CRegionDTO save(CRegionDTO cRegionDTO) {
        log.debug("Request to save CRegion : {}", cRegionDTO);
        CRegion cRegion = cRegionMapper.toEntity(cRegionDTO);
        cRegion = cRegionRepository.save(cRegion);
        return cRegionMapper.toDto(cRegion);
    }

    /**
     * Get all the cRegions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CRegions");
        return cRegionRepository.findAll(pageable)
            .map(cRegionMapper::toDto);
    }

    /**
     * Get one cRegion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CRegionDTO> findOne(Long id) {
        log.debug("Request to get CRegion : {}", id);
        return cRegionRepository.findById(id)
            .map(cRegionMapper::toDto);
    }

    /**
     * Delete the cRegion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CRegion : {}", id);
        cRegionRepository.deleteById(id);
    }
}
