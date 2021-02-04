package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdCallout;
import com.bhp.opusb.repository.AdCalloutRepository;
import com.bhp.opusb.service.dto.AdCalloutDTO;
import com.bhp.opusb.service.mapper.AdCalloutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdCallout}.
 */
@Service
@Transactional
public class AdCalloutService {

    private final Logger log = LoggerFactory.getLogger(AdCalloutService.class);

    private final AdCalloutRepository adCalloutRepository;

    private final AdCalloutMapper adCalloutMapper;

    public AdCalloutService(AdCalloutRepository adCalloutRepository, AdCalloutMapper adCalloutMapper) {
        this.adCalloutRepository = adCalloutRepository;
        this.adCalloutMapper = adCalloutMapper;
    }

    /**
     * Save a adCallout.
     *
     * @param adCalloutDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADField.adCallouts", allEntries = true)
    public AdCalloutDTO save(AdCalloutDTO adCalloutDTO) {
        log.debug("Request to save AdCallout : {}", adCalloutDTO);
        AdCallout adCallout = adCalloutMapper.toEntity(adCalloutDTO);
        adCallout = adCalloutRepository.save(adCallout);
        return adCalloutMapper.toDto(adCallout);
    }

    /**
     * Get all the adCallouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdCalloutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdCallouts");
        return adCalloutRepository.findAll(pageable)
            .map(adCalloutMapper::toDto);
    }

    /**
     * Get one adCallout by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdCalloutDTO> findOne(Long id) {
        log.debug("Request to get AdCallout : {}", id);
        return adCalloutRepository.findById(id)
            .map(adCalloutMapper::toDto);
    }

    /**
     * Delete the adCallout by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADField.adCallouts", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete AdCallout : {}", id);
        adCalloutRepository.deleteById(id);
    }
}
