package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBiddingSubCriteria;
import com.bhp.opusb.repository.CBiddingSubCriteriaRepository;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaDTO;
import com.bhp.opusb.service.mapper.CBiddingSubCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBiddingSubCriteria}.
 */
@Service
@Transactional
public class CBiddingSubCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CBiddingSubCriteriaService.class);

    private final CBiddingSubCriteriaRepository cBiddingSubCriteriaRepository;

    private final CBiddingSubCriteriaMapper cBiddingSubCriteriaMapper;

    public CBiddingSubCriteriaService(CBiddingSubCriteriaRepository cBiddingSubCriteriaRepository, CBiddingSubCriteriaMapper cBiddingSubCriteriaMapper) {
        this.cBiddingSubCriteriaRepository = cBiddingSubCriteriaRepository;
        this.cBiddingSubCriteriaMapper = cBiddingSubCriteriaMapper;
    }

    /**
     * Save a cBiddingSubCriteria.
     *
     * @param cBiddingSubCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CBiddingSubCriteriaDTO save(CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO) {
        log.debug("Request to save CBiddingSubCriteria : {}", cBiddingSubCriteriaDTO);
        CBiddingSubCriteria cBiddingSubCriteria = cBiddingSubCriteriaMapper.toEntity(cBiddingSubCriteriaDTO);
        cBiddingSubCriteria = cBiddingSubCriteriaRepository.save(cBiddingSubCriteria);
        return cBiddingSubCriteriaMapper.toDto(cBiddingSubCriteria);
    }

    /**
     * Get all the cBiddingSubCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingSubCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBiddingSubCriteria");
        return cBiddingSubCriteriaRepository.findAll(pageable)
            .map(cBiddingSubCriteriaMapper::toDto);
    }

    /**
     * Get one cBiddingSubCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBiddingSubCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CBiddingSubCriteria : {}", id);
        return cBiddingSubCriteriaRepository.findById(id)
            .map(cBiddingSubCriteriaMapper::toDto);
    }

    /**
     * Delete the cBiddingSubCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBiddingSubCriteria : {}", id);
        cBiddingSubCriteriaRepository.deleteById(id);
    }
}
