package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationEvent;
import com.bhp.opusb.repository.MPrequalificationEventRepository;
import com.bhp.opusb.service.dto.MPrequalificationEventDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalificationEvent}.
 */
@Service
@Transactional
public class MPrequalificationEventService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEventService.class);

    private final MPrequalificationEventRepository mPrequalificationEventRepository;

    private final MPrequalificationEventMapper mPrequalificationEventMapper;

    public MPrequalificationEventService(MPrequalificationEventRepository mPrequalificationEventRepository, MPrequalificationEventMapper mPrequalificationEventMapper) {
        this.mPrequalificationEventRepository = mPrequalificationEventRepository;
        this.mPrequalificationEventMapper = mPrequalificationEventMapper;
    }

    /**
     * Save a mPrequalificationEvent.
     *
     * @param mPrequalificationEventDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationEventDTO save(MPrequalificationEventDTO mPrequalificationEventDTO) {
        log.debug("Request to save MPrequalificationEvent : {}", mPrequalificationEventDTO);
        MPrequalificationEvent mPrequalificationEvent = mPrequalificationEventMapper.toEntity(mPrequalificationEventDTO);
        mPrequalificationEvent = mPrequalificationEventRepository.save(mPrequalificationEvent);
        return mPrequalificationEventMapper.toDto(mPrequalificationEvent);
    }

    /**
     * Get all the mPrequalificationEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationEvents");
        return mPrequalificationEventRepository.findAll(pageable)
            .map(mPrequalificationEventMapper::toDto);
    }

    /**
     * Get one mPrequalificationEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationEventDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationEvent : {}", id);
        return mPrequalificationEventRepository.findById(id)
            .map(mPrequalificationEventMapper::toDto);
    }

    /**
     * Delete the mPrequalificationEvent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationEvent : {}", id);
        mPrequalificationEventRepository.deleteById(id);
    }
}
