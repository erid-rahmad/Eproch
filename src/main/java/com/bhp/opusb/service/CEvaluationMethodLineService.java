package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvaluationMethodLine;
import com.bhp.opusb.repository.CEvaluationMethodLineRepository;
import com.bhp.opusb.service.dto.CEvaluationMethodLineDTO;
import com.bhp.opusb.service.mapper.CEvaluationMethodLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvaluationMethodLine}.
 */
@Service
@Transactional
public class CEvaluationMethodLineService {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodLineService.class);

    private final CEvaluationMethodLineRepository cEvaluationMethodLineRepository;

    private final CEvaluationMethodLineMapper cEvaluationMethodLineMapper;

    public CEvaluationMethodLineService(CEvaluationMethodLineRepository cEvaluationMethodLineRepository, CEvaluationMethodLineMapper cEvaluationMethodLineMapper) {
        this.cEvaluationMethodLineRepository = cEvaluationMethodLineRepository;
        this.cEvaluationMethodLineMapper = cEvaluationMethodLineMapper;
    }

    /**
     * Save a cEvaluationMethodLine.
     *
     * @param cEvaluationMethodLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CEvaluationMethodLineDTO save(CEvaluationMethodLineDTO cEvaluationMethodLineDTO) {
        log.debug("Request to save CEvaluationMethodLine : {}", cEvaluationMethodLineDTO);
        CEvaluationMethodLine cEvaluationMethodLine = cEvaluationMethodLineMapper.toEntity(cEvaluationMethodLineDTO);
        cEvaluationMethodLine = cEvaluationMethodLineRepository.save(cEvaluationMethodLine);
        return cEvaluationMethodLineMapper.toDto(cEvaluationMethodLine);
    }

    /**
     * Get all the cEvaluationMethodLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvaluationMethodLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvaluationMethodLines");
        return cEvaluationMethodLineRepository.findAll(pageable)
            .map(cEvaluationMethodLineMapper::toDto);
    }

    /**
     * Get one cEvaluationMethodLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEvaluationMethodLineDTO> findOne(Long id) {
        log.debug("Request to get CEvaluationMethodLine : {}", id);
        return cEvaluationMethodLineRepository.findById(id)
            .map(cEvaluationMethodLineMapper::toDto);
    }

    /**
     * Delete the cEvaluationMethodLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvaluationMethodLine : {}", id);
        cEvaluationMethodLineRepository.deleteById(id);
    }
}
