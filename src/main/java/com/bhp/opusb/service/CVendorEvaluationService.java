package com.bhp.opusb.service;

import com.bhp.opusb.domain.CVendorEvaluation;
import com.bhp.opusb.repository.CVendorEvaluationRepository;
import com.bhp.opusb.service.dto.CVendorEvaluationDTO;
import com.bhp.opusb.service.mapper.CVendorEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CVendorEvaluation}.
 */
@Service
@Transactional
public class CVendorEvaluationService {

    private final Logger log = LoggerFactory.getLogger(CVendorEvaluationService.class);

    private final CVendorEvaluationRepository cVendorEvaluationRepository;

    private final CVendorEvaluationMapper cVendorEvaluationMapper;

    public CVendorEvaluationService(CVendorEvaluationRepository cVendorEvaluationRepository, CVendorEvaluationMapper cVendorEvaluationMapper) {
        this.cVendorEvaluationRepository = cVendorEvaluationRepository;
        this.cVendorEvaluationMapper = cVendorEvaluationMapper;
    }

    /**
     * Save a cVendorEvaluation.
     *
     * @param cVendorEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorEvaluationDTO save(CVendorEvaluationDTO cVendorEvaluationDTO) {
        log.debug("Request to save CVendorEvaluation : {}", cVendorEvaluationDTO);
        CVendorEvaluation cVendorEvaluation = cVendorEvaluationMapper.toEntity(cVendorEvaluationDTO);
        cVendorEvaluation = cVendorEvaluationRepository.save(cVendorEvaluation);
        return cVendorEvaluationMapper.toDto(cVendorEvaluation);
    }

    /**
     * Get all the cVendorEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorEvaluations");
        return cVendorEvaluationRepository.findAll(pageable)
            .map(cVendorEvaluationMapper::toDto);
    }

    /**
     * Get one cVendorEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorEvaluationDTO> findOne(Long id) {
        log.debug("Request to get CVendorEvaluation : {}", id);
        return cVendorEvaluationRepository.findById(id)
            .map(cVendorEvaluationMapper::toDto);
    }

    /**
     * Delete the cVendorEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorEvaluation : {}", id);
        cVendorEvaluationRepository.deleteById(id);
    }
}
