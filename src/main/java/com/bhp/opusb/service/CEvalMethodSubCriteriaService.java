package com.bhp.opusb.service;

import com.bhp.opusb.domain.CEvalMethodSubCriteria;
import com.bhp.opusb.repository.CEvalMethodSubCriteriaRepository;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CEvalMethodSubCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CEvalMethodSubCriteria}.
 */
@Service
@Transactional
public class CEvalMethodSubCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CEvalMethodSubCriteriaService.class);

    private final CEvalMethodSubCriteriaRepository cEvalMethodSubCriteriaRepository;

    private final CEvalMethodSubCriteriaMapper cEvalMethodSubCriteriaMapper;

    public CEvalMethodSubCriteriaService(CEvalMethodSubCriteriaRepository cEvalMethodSubCriteriaRepository, CEvalMethodSubCriteriaMapper cEvalMethodSubCriteriaMapper) {
        this.cEvalMethodSubCriteriaRepository = cEvalMethodSubCriteriaRepository;
        this.cEvalMethodSubCriteriaMapper = cEvalMethodSubCriteriaMapper;
    }

    /**
     * Save a cEvalMethodSubCriteria.
     *
     * @param cEvalMethodSubCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CEvalMethodSubCriteriaDTO save(CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO) {
        log.debug("Request to save CEvalMethodSubCriteria : {}", cEvalMethodSubCriteriaDTO);
        CEvalMethodSubCriteria cEvalMethodSubCriteria = cEvalMethodSubCriteriaMapper.toEntity(cEvalMethodSubCriteriaDTO);
        cEvalMethodSubCriteria = cEvalMethodSubCriteriaRepository.save(cEvalMethodSubCriteria);
        return cEvalMethodSubCriteriaMapper.toDto(cEvalMethodSubCriteria);
    }

    /**
     * Get all the cEvalMethodSubCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CEvalMethodSubCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CEvalMethodSubCriteria");
        return cEvalMethodSubCriteriaRepository.findAll(pageable)
            .map(cEvalMethodSubCriteriaMapper::toDto);
    }

    /**
     * Get one cEvalMethodSubCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CEvalMethodSubCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CEvalMethodSubCriteria : {}", id);
        return cEvalMethodSubCriteriaRepository.findById(id)
            .map(cEvalMethodSubCriteriaMapper::toDto);
    }

    /**
     * Delete the cEvalMethodSubCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CEvalMethodSubCriteria : {}", id);
        cEvalMethodSubCriteriaRepository.deleteById(id);
    }
}
