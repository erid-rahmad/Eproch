package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvent;
import com.bhp.opusb.repository.CEventRepository;
import com.bhp.opusb.service.dto.CEventDTO;
import com.bhp.opusb.service.mapper.CEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvent}.
 */
@Service
@Transactional
public class CEventService {

    private final Logger log = LoggerFactory.getLogger(CEventService.class);

    private final CEventRepository cEventRepository;

    private final CEventMapper cEventMapper;

    public CEventService(CEventRepository cEventRepository, CEventMapper cEventMapper) {
        this.cEventRepository = cEventRepository;
        this.cEventMapper = cEventMapper;
    }

    /**
     * Save a cEvent.
     *
     * @param cEventDTO the entity to save.
     * @return the persisted entity.
     */
    public CEventDTO save(CEventDTO cEventDTO) {
        log.debug("Request to save CEvent : {}", cEventDTO);
        CEvent cEvent = cEventMapper.toEntity(cEventDTO);
        cEvent = cEventRepository.save(cEvent);
        return cEventMapper.toDto(cEvent);
    }

    /**
     * Get all the cEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvents");
        return cEventRepository.findAll(pageable)
            .map(cEventMapper::toDto);
    }

    /**
     * Get one cEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEventDTO> findOne(Long id) {
        log.debug("Request to get CEvent : {}", id);
        return cEventRepository.findById(id)
            .map(cEventMapper::toDto);
    }

    /**
     * Delete the cEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvent : {}", id);
        cEventRepository.deleteById(id);
    }
}
