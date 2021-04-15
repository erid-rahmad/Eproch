package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvaluationMethodCriteria;
import com.bhp.opusb.repository.CEvaluationMethodCriteriaRepository;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvaluationMethodCriteria}.
 */
@Service
@Transactional
public class CEvaluationMethodCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodCriteriaService.class);

    private final CEvaluationMethodCriteriaRepository cEvaluationMethodCriteriaRepository;

    private final CEvaluationMethodCriteriaMapper cEvaluationMethodCriteriaMapper;

    public CEvaluationMethodCriteriaService(CEvaluationMethodCriteriaRepository cEvaluationMethodCriteriaRepository, CEvaluationMethodCriteriaMapper cEvaluationMethodCriteriaMapper) {
        this.cEvaluationMethodCriteriaRepository = cEvaluationMethodCriteriaRepository;
        this.cEvaluationMethodCriteriaMapper = cEvaluationMethodCriteriaMapper;
    }

    /**
     * Save a cEvaluationMethodCriteria.
     *
     * @param cEvaluationMethodCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CEvaluationMethodCriteriaDTO save(CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO) {
        log.debug("Request to save CEvaluationMethodCriteria : {}", cEvaluationMethodCriteriaDTO);
        CEvaluationMethodCriteria cEvaluationMethodCriteria = cEvaluationMethodCriteriaMapper.toEntity(cEvaluationMethodCriteriaDTO);
        cEvaluationMethodCriteria = cEvaluationMethodCriteriaRepository.save(cEvaluationMethodCriteria);
        return cEvaluationMethodCriteriaMapper.toDto(cEvaluationMethodCriteria);
    }

    /**
     * Get all the cEvaluationMethodCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationMethodCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvaluationMethodCriteria");
        return cEvaluationMethodCriteriaRepository.findAll(pageable)
            .map(cEvaluationMethodCriteriaMapper::toDto);
    }

    /**
     * Get one cEvaluationMethodCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEvaluationMethodCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CEvaluationMethodCriteria : {}", id);
        return cEvaluationMethodCriteriaRepository.findById(id)
            .map(cEvaluationMethodCriteriaMapper::toDto);
    }

    /**
     * Delete the cEvaluationMethodCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvaluationMethodCriteria : {}", id);
        cEvaluationMethodCriteriaRepository.deleteById(id);
    }
}
