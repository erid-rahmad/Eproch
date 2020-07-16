package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.AdTaskApplication;
import com.bhp.opusb.repository.AdTaskApplicationRepository;
import com.bhp.opusb.service.dto.AdTaskApplicationDTO;
import com.bhp.opusb.service.mapper.AdTaskApplicationMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AdTaskApplication}.
 */
@Service
@Transactional
public class AdTaskApplicationService {

    private final Logger log = LoggerFactory.getLogger(AdTaskApplicationService.class);

    private final AdTaskApplicationRepository adTaskApplicationRepository;

    private final AdTaskApplicationMapper adTaskApplicationMapper;

    public AdTaskApplicationService(AdTaskApplicationRepository adTaskApplicationRepository, AdTaskApplicationMapper adTaskApplicationMapper) {
        this.adTaskApplicationRepository = adTaskApplicationRepository;
        this.adTaskApplicationMapper = adTaskApplicationMapper;
    }

    /**
     * Save a adTaskApplication.
     *
     * @param adTaskApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTaskProcess.adTaskApplication", allEntries = true)
    public AdTaskApplicationDTO save(AdTaskApplicationDTO adTaskApplicationDTO) {
        log.debug("Request to save AdTaskApplication : {}", adTaskApplicationDTO);
        AdTaskApplication adTaskApplication = adTaskApplicationMapper.toEntity(adTaskApplicationDTO);
        adTaskApplication = adTaskApplicationRepository.save(adTaskApplication);
        return adTaskApplicationMapper.toDto(adTaskApplication);
    }

    /**
     * Get all the adTaskApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTaskApplications");
        return adTaskApplicationRepository.findAll(pageable)
            .map(adTaskApplicationMapper::toDto);
    }

    /**
     * Get one adTaskApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTaskApplicationDTO> findOne(Long id) {
        log.debug("Request to get AdTaskApplication : {}", id);
        return adTaskApplicationRepository.findById(id)
            .map(adTaskApplicationMapper::toDto);
    }

    /**
     * Delete the adTaskApplication by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTaskProcess.adTaskApplication", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete AdTaskApplication : {}", id);
        adTaskApplicationRepository.deleteById(id);
    }
}
