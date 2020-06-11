package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADReference;
import com.bhp.opusb.repository.ADReferenceRepository;
import com.bhp.opusb.service.dto.ADReferenceDTO;
import com.bhp.opusb.service.mapper.ADReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADReference}.
 */
@Service
@Transactional
public class ADReferenceService {

    private final Logger log = LoggerFactory.getLogger(ADReferenceService.class);

    private final ADReferenceRepository aDReferenceRepository;

    private final ADReferenceMapper aDReferenceMapper;

    public ADReferenceService(ADReferenceRepository aDReferenceRepository, ADReferenceMapper aDReferenceMapper) {
        this.aDReferenceRepository = aDReferenceRepository;
        this.aDReferenceMapper = aDReferenceMapper;
    }

    /**
     * Save a aDReference.
     *
     * @param aDReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public ADReferenceDTO save(ADReferenceDTO aDReferenceDTO) {
        log.debug("Request to save ADReference : {}", aDReferenceDTO);
        ADReference aDReference = aDReferenceMapper.toEntity(aDReferenceDTO);
        aDReference = aDReferenceRepository.save(aDReference);
        return aDReferenceMapper.toDto(aDReference);
    }

    /**
     * Get all the aDReferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADReferences");
        return aDReferenceRepository.findAll(pageable)
            .map(aDReferenceMapper::toDto);
    }

    /**
     * Get one aDReference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADReferenceDTO> findOne(Long id) {
        log.debug("Request to get ADReference : {}", id);
        return aDReferenceRepository.findById(id)
            .map(aDReferenceMapper::toDto);
    }

    /**
     * Delete the aDReference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADReference : {}", id);
        aDReferenceRepository.deleteById(id);
    }
}
