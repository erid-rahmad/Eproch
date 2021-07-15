package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPrequalMethodSubCriteria;
import com.bhp.opusb.repository.CPrequalMethodSubCriteriaRepository;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CPrequalMethodSubCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPrequalMethodSubCriteria}.
 */
@Service
@Transactional
public class CPrequalMethodSubCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodSubCriteriaService.class);

    private final CPrequalMethodSubCriteriaRepository cPrequalMethodSubCriteriaRepository;

    private final CPrequalMethodSubCriteriaMapper cPrequalMethodSubCriteriaMapper;

    public CPrequalMethodSubCriteriaService(CPrequalMethodSubCriteriaRepository cPrequalMethodSubCriteriaRepository, CPrequalMethodSubCriteriaMapper cPrequalMethodSubCriteriaMapper) {
        this.cPrequalMethodSubCriteriaRepository = cPrequalMethodSubCriteriaRepository;
        this.cPrequalMethodSubCriteriaMapper = cPrequalMethodSubCriteriaMapper;
    }

    /**
     * Save a cPrequalMethodSubCriteria.
     *
     * @param cPrequalMethodSubCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CPrequalMethodSubCriteriaDTO save(CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO) {
        log.debug("Request to save CPrequalMethodSubCriteria : {}", cPrequalMethodSubCriteriaDTO);
        CPrequalMethodSubCriteria cPrequalMethodSubCriteria = cPrequalMethodSubCriteriaMapper.toEntity(cPrequalMethodSubCriteriaDTO);
        cPrequalMethodSubCriteria = cPrequalMethodSubCriteriaRepository.save(cPrequalMethodSubCriteria);
        return cPrequalMethodSubCriteriaMapper.toDto(cPrequalMethodSubCriteria);
    }

    /**
     * Get all the cPrequalMethodSubCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPrequalMethodSubCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPrequalMethodSubCriteria");
        return cPrequalMethodSubCriteriaRepository.findAll(pageable)
            .map(cPrequalMethodSubCriteriaMapper::toDto);
    }

    /**
     * Get one cPrequalMethodSubCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPrequalMethodSubCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CPrequalMethodSubCriteria : {}", id);
        return cPrequalMethodSubCriteriaRepository.findById(id)
            .map(cPrequalMethodSubCriteriaMapper::toDto);
    }

    /**
     * Delete the cPrequalMethodSubCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPrequalMethodSubCriteria : {}", id);
        cPrequalMethodSubCriteriaRepository.deleteById(id);
    }
}
