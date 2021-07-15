package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalMethodCriteria;
import com.bhp.opusb.repository.CPrequalMethodCriteriaRepository;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalMethodCriteria}.
 */
@Service
@Transactional
public class CPrequalMethodCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodCriteriaService.class);

    private final CPrequalMethodCriteriaRepository cPrequalMethodCriteriaRepository;

    private final CPrequalMethodCriteriaMapper cPrequalMethodCriteriaMapper;

    public CPrequalMethodCriteriaService(CPrequalMethodCriteriaRepository cPrequalMethodCriteriaRepository, CPrequalMethodCriteriaMapper cPrequalMethodCriteriaMapper) {
        this.cPrequalMethodCriteriaRepository = cPrequalMethodCriteriaRepository;
        this.cPrequalMethodCriteriaMapper = cPrequalMethodCriteriaMapper;
    }

    /**
     * Save a cPrequalMethodCriteria.
     *
     * @param cPrequalMethodCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalMethodCriteriaDTO save(CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO) {
        log.debug("Request to save CPrequalMethodCriteria : {}", cPrequalMethodCriteriaDTO);
        CPrequalMethodCriteria cPrequalMethodCriteria = cPrequalMethodCriteriaMapper.toEntity(cPrequalMethodCriteriaDTO);
        cPrequalMethodCriteria = cPrequalMethodCriteriaRepository.save(cPrequalMethodCriteria);
        return cPrequalMethodCriteriaMapper.toDto(cPrequalMethodCriteria);
    }

    /**
     * Get all the cPrequalMethodCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalMethodCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalMethodCriteria");
        return cPrequalMethodCriteriaRepository.findAll(pageable)
            .map(cPrequalMethodCriteriaMapper::toDto);
    }

    /**
     * Get one cPrequalMethodCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalMethodCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CPrequalMethodCriteria : {}", id);
        return cPrequalMethodCriteriaRepository.findById(id)
            .map(cPrequalMethodCriteriaMapper::toDto);
    }

    /**
     * Delete the cPrequalMethodCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalMethodCriteria : {}", id);
        cPrequalMethodCriteriaRepository.deleteById(id);
    }
}
