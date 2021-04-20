package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvalMethodCriteriaLine;
import com.bhp.opusb.repository.CEvalMethodCriteriaLineRepository;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineDTO;
import com.bhp.opusb.service.mapper.CEvalMethodCriteriaLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvalMethodCriteriaLine}.
 */
@Service
@Transactional
public class CEvalMethodCriteriaLineService {

    private final Logger log = LoggerFactory.getLogger(CEvalMethodCriteriaLineService.class);

    private final CEvalMethodCriteriaLineRepository cEvalMethodCriteriaLineRepository;

    private final CEvalMethodCriteriaLineMapper cEvalMethodCriteriaLineMapper;

    public CEvalMethodCriteriaLineService(CEvalMethodCriteriaLineRepository cEvalMethodCriteriaLineRepository, CEvalMethodCriteriaLineMapper cEvalMethodCriteriaLineMapper) {
        this.cEvalMethodCriteriaLineRepository = cEvalMethodCriteriaLineRepository;
        this.cEvalMethodCriteriaLineMapper = cEvalMethodCriteriaLineMapper;
    }

    /**
     * Save a cEvalMethodCriteriaLine.
     *
     * @param cEvalMethodCriteriaLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CEvalMethodCriteriaLineDTO save(CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO) {
        log.debug("Request to save CEvalMethodCriteriaLine : {}", cEvalMethodCriteriaLineDTO);
        CEvalMethodCriteriaLine cEvalMethodCriteriaLine = cEvalMethodCriteriaLineMapper.toEntity(cEvalMethodCriteriaLineDTO);
        cEvalMethodCriteriaLine = cEvalMethodCriteriaLineRepository.save(cEvalMethodCriteriaLine);
        return cEvalMethodCriteriaLineMapper.toDto(cEvalMethodCriteriaLine);
    }

    /**
     * Get all the cEvalMethodCriteriaLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvalMethodCriteriaLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvalMethodCriteriaLines");
        return cEvalMethodCriteriaLineRepository.findAll(pageable)
            .map(cEvalMethodCriteriaLineMapper::toDto);
    }

    /**
     * Get one cEvalMethodCriteriaLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEvalMethodCriteriaLineDTO> findOne(Long id) {
        log.debug("Request to get CEvalMethodCriteriaLine : {}", id);
        return cEvalMethodCriteriaLineRepository.findById(id)
            .map(cEvalMethodCriteriaLineMapper::toDto);
    }

    /**
     * Delete the cEvalMethodCriteriaLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvalMethodCriteriaLine : {}", id);
        cEvalMethodCriteriaLineRepository.deleteById(id);
    }
}
