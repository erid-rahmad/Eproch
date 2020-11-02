package com.bhp.opusb.service;

import com.bhp.opusb.domain.CLocator;
import com.bhp.opusb.repository.CLocatorRepository;
import com.bhp.opusb.service.dto.CLocatorDTO;
import com.bhp.opusb.service.mapper.CLocatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CLocator}.
 */
@Service
@Transactional
public class CLocatorService {

    private final Logger log = LoggerFactory.getLogger(CLocatorService.class);

    private final CLocatorRepository cLocatorRepository;

    private final CLocatorMapper cLocatorMapper;

    public CLocatorService(CLocatorRepository cLocatorRepository, CLocatorMapper cLocatorMapper) {
        this.cLocatorRepository = cLocatorRepository;
        this.cLocatorMapper = cLocatorMapper;
    }

    /**
     * Save a cLocator.
     *
     * @param cLocatorDTO the entity to save.
     * @return the persisted entity.
     */
    public CLocatorDTO save(CLocatorDTO cLocatorDTO) {
        log.debug("Request to save CLocator : {}", cLocatorDTO);
        CLocator cLocator = cLocatorMapper.toEntity(cLocatorDTO);
        cLocator = cLocatorRepository.save(cLocator);
        return cLocatorMapper.toDto(cLocator);
    }

    /**
     * Get all the cLocators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CLocatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CLocators");
        return cLocatorRepository.findAll(pageable)
            .map(cLocatorMapper::toDto);
    }

    /**
     * Get one cLocator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CLocatorDTO> findOne(Long id) {
        log.debug("Request to get CLocator : {}", id);
        return cLocatorRepository.findById(id)
            .map(cLocatorMapper::toDto);
    }

    /**
     * Delete the cLocator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CLocator : {}", id);
        cLocatorRepository.deleteById(id);
    }
}
