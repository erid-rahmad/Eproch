package com.bhp.opusb.service;

import com.bhp.opusb.domain.CProductClassification;
import com.bhp.opusb.repository.CProductClassificationRepository;
import com.bhp.opusb.service.dto.CProductClassificationDTO;
import com.bhp.opusb.service.mapper.CProductClassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CProductClassification}.
 */
@Service
@Transactional
public class CProductClassificationService {

    private final Logger log = LoggerFactory.getLogger(CProductClassificationService.class);

    private final CProductClassificationRepository cProductClassificationRepository;

    private final CProductClassificationMapper cProductClassificationMapper;

    public CProductClassificationService(CProductClassificationRepository cProductClassificationRepository, CProductClassificationMapper cProductClassificationMapper) {
        this.cProductClassificationRepository = cProductClassificationRepository;
        this.cProductClassificationMapper = cProductClassificationMapper;
    }

    /**
     * Save a cProductClassification.
     *
     * @param cProductClassificationDTO the entity to save.
     * @return the persisted entity.
     */
    public CProductClassificationDTO save(CProductClassificationDTO cProductClassificationDTO) {
        log.debug("Request to save CProductClassification : {}", cProductClassificationDTO);
        CProductClassification cProductClassification = cProductClassificationMapper.toEntity(cProductClassificationDTO);
        cProductClassification = cProductClassificationRepository.save(cProductClassification);
        return cProductClassificationMapper.toDto(cProductClassification);
    }

    /**
     * Get all the cProductClassifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CProductClassificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CProductClassifications");
        return cProductClassificationRepository.findAll(pageable)
            .map(cProductClassificationMapper::toDto);
    }

    /**
     * Get one cProductClassification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CProductClassificationDTO> findOne(Long id) {
        log.debug("Request to get CProductClassification : {}", id);
        return cProductClassificationRepository.findById(id)
            .map(cProductClassificationMapper::toDto);
    }

    /**
     * Delete the cProductClassification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CProductClassification : {}", id);
        cProductClassificationRepository.deleteById(id);
    }
}
