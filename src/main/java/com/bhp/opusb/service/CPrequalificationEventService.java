package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalificationEvent;
import com.bhp.opusb.repository.CPrequalificationEventRepository;
import com.bhp.opusb.service.dto.CPrequalificationEventDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalificationEvent}.
 */
@Service
@Transactional
public class CPrequalificationEventService {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationEventService.class);

    private final CPrequalificationEventRepository cPrequalificationEventRepository;

    private final CPrequalificationEventMapper cPrequalificationEventMapper;

    public CPrequalificationEventService(CPrequalificationEventRepository cPrequalificationEventRepository, CPrequalificationEventMapper cPrequalificationEventMapper) {
        this.cPrequalificationEventRepository = cPrequalificationEventRepository;
        this.cPrequalificationEventMapper = cPrequalificationEventMapper;
    }

    /**
     * Save a cPrequalificationEvent.
     *
     * @param cPrequalificationEventDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalificationEventDTO save(CPrequalificationEventDTO cPrequalificationEventDTO) {
        log.debug("Request to save CPrequalificationEvent : {}", cPrequalificationEventDTO);
        CPrequalificationEvent cPrequalificationEvent = cPrequalificationEventMapper.toEntity(cPrequalificationEventDTO);
        cPrequalificationEvent = cPrequalificationEventRepository.save(cPrequalificationEvent);
        return cPrequalificationEventMapper.toDto(cPrequalificationEvent);
    }

    /**
     * Get all the cPrequalificationEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalificationEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalificationEvents");
        return cPrequalificationEventRepository.findAll(pageable)
            .map(cPrequalificationEventMapper::toDto);
    }

    /**
     * Get one cPrequalificationEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalificationEventDTO> findOne(Long id) {
        log.debug("Request to get CPrequalificationEvent : {}", id);
        return cPrequalificationEventRepository.findById(id)
            .map(cPrequalificationEventMapper::toDto);
    }

    /**
     * Delete the cPrequalificationEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalificationEvent : {}", id);
        cPrequalificationEventRepository.deleteById(id);
    }
}
