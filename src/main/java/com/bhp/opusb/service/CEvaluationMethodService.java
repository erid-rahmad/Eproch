package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvaluationMethod;
import com.bhp.opusb.repository.CEvaluationMethodRepository;
import com.bhp.opusb.service.dto.CEvaluationMethodDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvaluationMethod}.
 */
@Service
@Transactional
public class CEvaluationMethodService {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodService.class);

    private final CEvaluationMethodRepository cEvaluationMethodRepository;

    private final CEvaluationMethodMapper cEvaluationMethodMapper;

    public CEvaluationMethodService(CEvaluationMethodRepository cEvaluationMethodRepository, CEvaluationMethodMapper cEvaluationMethodMapper) {
        this.cEvaluationMethodRepository = cEvaluationMethodRepository;
        this.cEvaluationMethodMapper = cEvaluationMethodMapper;
    }

    /**
     * Save a cEvaluationMethod.
     *
     * @param cEvaluationMethodDTO the entity to save.
     * @return the persisted entity.
     */
    public CEvaluationMethodDTO save(CEvaluationMethodDTO cEvaluationMethodDTO) {
        log.debug("Request to save CEvaluationMethod : {}", cEvaluationMethodDTO);
        CEvaluationMethod cEvaluationMethod = cEvaluationMethodMapper.toEntity(cEvaluationMethodDTO);
        cEvaluationMethod = cEvaluationMethodRepository.save(cEvaluationMethod);
        return cEvaluationMethodMapper.toDto(cEvaluationMethod);
    }

    /**
     * Get all the cEvaluationMethods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationMethodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvaluationMethods");
        return cEvaluationMethodRepository.findAll(pageable)
            .map(cEvaluationMethodMapper::toDto);
    }

    /**
     * Get one cEvaluationMethod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEvaluationMethodDTO> findOne(Long id) {
        log.debug("Request to get CEvaluationMethod : {}", id);
        return cEvaluationMethodRepository.findById(id)
            .map(cEvaluationMethodMapper::toDto);
    }

    /**
     * Delete the cEvaluationMethod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvaluationMethod : {}", id);
        cEvaluationMethodRepository.deleteById(id);
    }
}
