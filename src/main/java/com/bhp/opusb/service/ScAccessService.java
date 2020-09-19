package com.bhp.opusb.service;

import com.bhp.opusb.domain.ScAccess;
import com.bhp.opusb.repository.ScAccessRepository;
import com.bhp.opusb.service.dto.ScAccessDTO;
import com.bhp.opusb.service.mapper.ScAccessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScAccess}.
 */
@Service
@Transactional
public class ScAccessService {

    private final Logger log = LoggerFactory.getLogger(ScAccessService.class);

    private final ScAccessRepository scAccessRepository;

    private final ScAccessMapper scAccessMapper;

    public ScAccessService(ScAccessRepository scAccessRepository, ScAccessMapper scAccessMapper) {
        this.scAccessRepository = scAccessRepository;
        this.scAccessMapper = scAccessMapper;
    }

    /**
     * Save a scAccess.
     *
     * @param scAccessDTO the entity to save.
     * @return the persisted entity.
     */
    public ScAccessDTO save(ScAccessDTO scAccessDTO) {
        log.debug("Request to save ScAccess : {}", scAccessDTO);
        ScAccess scAccess = scAccessMapper.toEntity(scAccessDTO);
        scAccess = scAccessRepository.save(scAccess);
        return scAccessMapper.toDto(scAccess);
    }

    /**
     * Get all the scAccesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAccessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScAccesses");
        return scAccessRepository.findAll(pageable)
            .map(scAccessMapper::toDto);
    }

    /**
     * Get one scAccess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScAccessDTO> findOne(Long id) {
        log.debug("Request to get ScAccess : {}", id);
        return scAccessRepository.findById(id)
            .map(scAccessMapper::toDto);
    }

    /**
     * Delete the scAccess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ScAccess : {}", id);
        scAccessRepository.deleteById(id);
    }
}
