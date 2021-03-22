package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvaluationCriteria;
import com.bhp.opusb.repository.CEvaluationCriteriaRepository;
import com.bhp.opusb.service.dto.CEvaluationCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvaluationCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvaluationCriteria}.
 */
@Service
@Transactional
public class CEvaluationCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CEvaluationCriteriaService.class);

    private final CEvaluationCriteriaRepository cEvaluationCriteriaRepository;

    private final CEvaluationCriteriaMapper cEvaluationCriteriaMapper;

    public CEvaluationCriteriaService(CEvaluationCriteriaRepository cEvaluationCriteriaRepository, CEvaluationCriteriaMapper cEvaluationCriteriaMapper) {
        this.cEvaluationCriteriaRepository = cEvaluationCriteriaRepository;
        this.cEvaluationCriteriaMapper = cEvaluationCriteriaMapper;
    }

    /**
     * Save a cEvaluationCriteria.
     *
     * @param cEvaluationCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CEvaluationCriteriaDTO save(CEvaluationCriteriaDTO cEvaluationCriteriaDTO) {
        log.debug("Request to save CEvaluationCriteria : {}", cEvaluationCriteriaDTO);
        CEvaluationCriteria cEvaluationCriteria = cEvaluationCriteriaMapper.toEntity(cEvaluationCriteriaDTO);
        cEvaluationCriteria = cEvaluationCriteriaRepository.save(cEvaluationCriteria);
        return cEvaluationCriteriaMapper.toDto(cEvaluationCriteria);
    }

    /**
     * Get all the cEvaluationCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvaluationCriteria");
        return cEvaluationCriteriaRepository.findAll(pageable)
            .map(cEvaluationCriteriaMapper::toDto);
    }

    /**
     * Get one cEvaluationCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEvaluationCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CEvaluationCriteria : {}", id);
        return cEvaluationCriteriaRepository.findById(id)
            .map(cEvaluationCriteriaMapper::toDto);
    }

    /**
     * Delete the cEvaluationCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvaluationCriteria : {}", id);
        cEvaluationCriteriaRepository.deleteById(id);
    }
}
