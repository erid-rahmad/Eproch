package com.bhp.opusb.service;

import java.util.Optional;

import com.bhp.opusb.domain.ADField;
import com.bhp.opusb.repository.ADFieldRepository;
import com.bhp.opusb.service.dto.ADFieldDTO;
import com.bhp.opusb.service.mapper.ADFieldMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ADField}.
 */
@Service
@Transactional
public class ADFieldService {

    private final Logger log = LoggerFactory.getLogger(ADFieldService.class);

    private final ADFieldRepository aDFieldRepository;

    private final ADFieldMapper aDFieldMapper;

    public ADFieldService(ADFieldRepository aDFieldRepository, ADFieldMapper aDFieldMapper) {
        this.aDFieldRepository = aDFieldRepository;
        this.aDFieldMapper = aDFieldMapper;
    }

    /**
     * Save a aDField.
     *
     * @param aDFieldDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTab.aDFields", allEntries = true)
    public ADFieldDTO save(ADFieldDTO aDFieldDTO) {
        log.debug("Request to save ADField : {}", aDFieldDTO);
        ADField aDField = aDFieldMapper.toEntity(aDFieldDTO);
        aDField = aDFieldRepository.save(aDField);
        return aDFieldMapper.toDto(aDField);
    }

    /**
     * Get all the aDFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADFieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADFields");
        return aDFieldRepository.findAll(pageable)
            .map(aDFieldMapper::toDto);
    }

    /**
     * Get one aDField by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADFieldDTO> findOne(Long id) {
        log.debug("Request to get ADField : {}", id);
        return aDFieldRepository.findById(id)
            .map(aDFieldMapper::toDto);
    }

    /**
     * Delete the aDField by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.ADTab.aDFields", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete ADField : {}", id);
        aDFieldRepository.deleteById(id);
    }
}
