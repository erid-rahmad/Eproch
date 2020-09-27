package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.AdCalloutTarget;
import com.bhp.opusb.repository.AdCalloutTargetRepository;
import com.bhp.opusb.service.dto.AdCalloutTargetDTO;
import com.bhp.opusb.service.mapper.AdCalloutTargetMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdCalloutTarget}.
 */
@Service
@Transactional
public class AdCalloutTargetService {

    private final Logger log = LoggerFactory.getLogger(AdCalloutTargetService.class);

    private final AdCalloutTargetRepository adCalloutTargetRepository;

    private final AdCalloutTargetMapper adCalloutTargetMapper;

    public AdCalloutTargetService(AdCalloutTargetRepository adCalloutTargetRepository, AdCalloutTargetMapper adCalloutTargetMapper) {
        this.adCalloutTargetRepository = adCalloutTargetRepository;
        this.adCalloutTargetMapper = adCalloutTargetMapper;
    }

    /**
     * Save a adCalloutTarget.
     *
     * @param adCalloutTargetDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdCallout.adCalloutTargets", allEntries = true)
    public AdCalloutTargetDTO save(AdCalloutTargetDTO adCalloutTargetDTO) {
        log.debug("Request to save AdCalloutTarget : {}", adCalloutTargetDTO);
        AdCalloutTarget adCalloutTarget = adCalloutTargetMapper.toEntity(adCalloutTargetDTO);
        adCalloutTarget = adCalloutTargetRepository.save(adCalloutTarget);
        return adCalloutTargetMapper.toDto(adCalloutTarget);
    }

    /**
     * Get all the adCalloutTargets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdCalloutTargetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdCalloutTargets");
        return adCalloutTargetRepository.findAll(pageable)
            .map(adCalloutTargetMapper::toDto);
    }

    /**
     * Get one adCalloutTarget by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdCalloutTargetDTO> findOne(Long id) {
        log.debug("Request to get AdCalloutTarget : {}", id);
        return adCalloutTargetRepository.findById(id)
            .map(adCalloutTargetMapper::toDto);
    }

    /**
     * Delete the adCalloutTarget by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.AdCallout.adCalloutTargets", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete AdCalloutTarget : {}", id);
        adCalloutTargetRepository.deleteById(id);
    }
}
