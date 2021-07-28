package com.bhp.opusb.service;

import com.bhp.opusb.domain.CTask;
import com.bhp.opusb.repository.CTaskRepository;
import com.bhp.opusb.service.dto.CTaskDTO;
import com.bhp.opusb.service.mapper.CTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CTask}.
 */
@Service
@Transactional
public class CTaskService {

    private final Logger log = LoggerFactory.getLogger(CTaskService.class);

    private final CTaskRepository cTaskRepository;

    private final CTaskMapper cTaskMapper;

    public CTaskService(CTaskRepository cTaskRepository, CTaskMapper cTaskMapper) {
        this.cTaskRepository = cTaskRepository;
        this.cTaskMapper = cTaskMapper;
    }

    /**
     * Save a cTask.
     *
     * @param cTaskDTO the entity to save.
     * @return the persisted entity.
     */
    public CTaskDTO save(CTaskDTO cTaskDTO) {
        log.debug("Request to save CTask : {}", cTaskDTO);
        CTask cTask = cTaskMapper.toEntity(cTaskDTO);
        cTask = cTaskRepository.save(cTask);
        return cTaskMapper.toDto(cTask);
    }

    /**
     * Get all the cTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CTasks");
        return cTaskRepository.findAll(pageable)
            .map(cTaskMapper::toDto);
    }

    /**
     * Get one cTask by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CTaskDTO> findOne(Long id) {
        log.debug("Request to get CTask : {}", id);
        return cTaskRepository.findById(id)
            .map(cTaskMapper::toDto);
    }

    /**
     * Delete the cTask by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CTask : {}", id);
        cTaskRepository.deleteById(id);
    }
}
