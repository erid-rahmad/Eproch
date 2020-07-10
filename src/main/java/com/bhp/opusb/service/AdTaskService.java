package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdTask;
import com.bhp.opusb.repository.AdTaskRepository;
import com.bhp.opusb.service.dto.AdTaskDTO;
import com.bhp.opusb.service.mapper.AdTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdTask}.
 */
@Service
@Transactional
public class AdTaskService {

    private final Logger log = LoggerFactory.getLogger(AdTaskService.class);

    private final AdTaskRepository adTaskRepository;

    private final AdTaskMapper adTaskMapper;

    public AdTaskService(AdTaskRepository adTaskRepository, AdTaskMapper adTaskMapper) {
        this.adTaskRepository = adTaskRepository;
        this.adTaskMapper = adTaskMapper;
    }

    /**
     * Save a adTask.
     *
     * @param adTaskDTO the entity to save.
     * @return the persisted entity.
     */
    public AdTaskDTO save(AdTaskDTO adTaskDTO) {
        log.debug("Request to save AdTask : {}", adTaskDTO);
        AdTask adTask = adTaskMapper.toEntity(adTaskDTO);
        adTask = adTaskRepository.save(adTask);
        return adTaskMapper.toDto(adTask);
    }

    /**
     * Get all the adTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AdTaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdTasks");
        return adTaskRepository.findAll(pageable)
            .map(adTaskMapper::toDto);
    }

    /**
     * Get one adTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdTaskDTO> findOne(Long id) {
        log.debug("Request to get AdTask : {}", id);
        return adTaskRepository.findById(id)
            .map(adTaskMapper::toDto);
    }

    /**
     * Delete the adTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdTask : {}", id);
        adTaskRepository.deleteById(id);
    }
}
