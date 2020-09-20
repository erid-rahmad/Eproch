package com.bhp.opusb.service;

import com.bhp.opusb.domain.ScAccessType;
import com.bhp.opusb.repository.ScAccessTypeRepository;
import com.bhp.opusb.service.dto.ScAccessTypeDTO;
import com.bhp.opusb.service.mapper.ScAccessTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScAccessType}.
 */
@Service
@Transactional
public class ScAccessTypeService {

    private final Logger log = LoggerFactory.getLogger(ScAccessTypeService.class);

    private final ScAccessTypeRepository scAccessTypeRepository;

    private final ScAccessTypeMapper scAccessTypeMapper;

    public ScAccessTypeService(ScAccessTypeRepository scAccessTypeRepository, ScAccessTypeMapper scAccessTypeMapper) {
        this.scAccessTypeRepository = scAccessTypeRepository;
        this.scAccessTypeMapper = scAccessTypeMapper;
    }

    /**
     * Save a scAccessType.
     *
     * @param scAccessTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ScAccessTypeDTO save(ScAccessTypeDTO scAccessTypeDTO) {
        log.debug("Request to save ScAccessType : {}", scAccessTypeDTO);
        ScAccessType scAccessType = scAccessTypeMapper.toEntity(scAccessTypeDTO);
        scAccessType = scAccessTypeRepository.save(scAccessType);
        return scAccessTypeMapper.toDto(scAccessType);
    }

    /**
     * Get all the scAccessTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScAccessTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScAccessTypes");
        return scAccessTypeRepository.findAll(pageable)
            .map(scAccessTypeMapper::toDto);
    }

    /**
     * Get one scAccessType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScAccessTypeDTO> findOne(Long id) {
        log.debug("Request to get ScAccessType : {}", id);
        return scAccessTypeRepository.findById(id)
            .map(scAccessTypeMapper::toDto);
    }

    /**
     * Delete the scAccessType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ScAccessType : {}", id);
        scAccessTypeRepository.deleteById(id);
    }
}
