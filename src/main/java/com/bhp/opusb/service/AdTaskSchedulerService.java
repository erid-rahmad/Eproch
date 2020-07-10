package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdTaskScheduler;
import com.bhp.opusb.repository.AdTaskSchedulerRepository;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;
import com.bhp.opusb.service.mapper.AdTaskSchedulerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdTaskScheduler}.
 */
@Service
@Transactional
public class AdTaskSchedulerService {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerService.class);

    private final AdTaskSchedulerRepository adTaskSchedulerRepository;

    private final AdTaskSchedulerMapper adTaskSchedulerMapper;

    public AdTaskSchedulerService(AdTaskSchedulerRepository adTaskSchedulerRepository, AdTaskSchedulerMapper adTaskSchedulerMapper) {
        this.adTaskSchedulerRepository = adTaskSchedulerRepository;
        this.adTaskSchedulerMapper = adTaskSchedulerMapper;
    }

    /**
     * Save a adTaskScheduler.
     *
     * @param adTaskSchedulerDTO the entity to save.
     * @return the persisted entity.
     */
    public AdTaskSchedulerDTO save(AdTaskSchedulerDTO adTaskSchedulerDTO) {
        log.debug("Request to save AdTaskScheduler : {}", adTaskSchedulerDTO);
        AdTaskScheduler adTaskScheduler = adTaskSchedulerMapper.toEntity(adTaskSchedulerDTO);
        adTaskScheduler = adTaskSchedulerRepository.save(adTaskScheduler);
        return adTaskSchedulerMapper.toDto(adTaskScheduler);
    }

    /**
     * Get all the adTaskSchedulers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskSchedulerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTaskSchedulers");
        return adTaskSchedulerRepository.findAll(pageable)
            .map(adTaskSchedulerMapper::toDto);
    }

    /**
     * Get one adTaskScheduler by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTaskSchedulerDTO> findOne(Long id) {
        log.debug("Request to get AdTaskScheduler : {}", id);
        return adTaskSchedulerRepository.findById(id)
            .map(adTaskSchedulerMapper::toDto);
    }

    /**
     * Delete the adTaskScheduler by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdTaskScheduler : {}", id);
        adTaskSchedulerRepository.deleteById(id);
    }
}
