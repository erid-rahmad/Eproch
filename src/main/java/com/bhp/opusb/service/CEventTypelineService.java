package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEventTypeline;
import com.bhp.opusb.repository.CEventTypelineRepository;
import com.bhp.opusb.service.dto.CEventTypelineDTO;
import com.bhp.opusb.service.mapper.CEventTypelineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEventTypeline}.
 */
@Service
@Transactional
public class CEventTypelineService {

    private final Logger log = LoggerFactory.getLogger(CEventTypelineService.class);

    private final CEventTypelineRepository cEventTypelineRepository;

    private final CEventTypelineMapper cEventTypelineMapper;

    public CEventTypelineService(CEventTypelineRepository cEventTypelineRepository, CEventTypelineMapper cEventTypelineMapper) {
        this.cEventTypelineRepository = cEventTypelineRepository;
        this.cEventTypelineMapper = cEventTypelineMapper;
    }

    /**
     * Save a cEventTypeline.
     *
     * @param cEventTypelineDTO the entity to save.
     * @return the persisted entity.
     */
    public CEventTypelineDTO save(CEventTypelineDTO cEventTypelineDTO) {
        log.debug("Request to save CEventTypeline : {}", cEventTypelineDTO);
        CEventTypeline cEventTypeline = cEventTypelineMapper.toEntity(cEventTypelineDTO);
        cEventTypeline = cEventTypelineRepository.save(cEventTypeline);
        return cEventTypelineMapper.toDto(cEventTypeline);
    }

    /**
     * Get all the cEventTypelines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEventTypelineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEventTypelines");
        return cEventTypelineRepository.findAll(pageable)
            .map(cEventTypelineMapper::toDto);
    }

    /**
     * Get one cEventTypeline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEventTypelineDTO> findOne(Long id) {
        log.debug("Request to get CEventTypeline : {}", id);
        return cEventTypelineRepository.findById(id)
            .map(cEventTypelineMapper::toDto);
    }

    /**
     * Delete the cEventTypeline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEventTypeline : {}", id);
        cEventTypelineRepository.deleteById(id);
    }
}
