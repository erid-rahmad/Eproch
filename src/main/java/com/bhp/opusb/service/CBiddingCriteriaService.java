package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBiddingCriteria;
import com.bhp.opusb.repository.CBiddingCriteriaRepository;
import com.bhp.opusb.service.dto.CBiddingCriteriaDTO;
import com.bhp.opusb.service.mapper.CBiddingCriteriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBiddingCriteria}.
 */
@Service
@Transactional
public class CBiddingCriteriaService {

    private final Logger log = LoggerFactory.getLogger(CBiddingCriteriaService.class);

    private final CBiddingCriteriaRepository cBiddingCriteriaRepository;

    private final CBiddingCriteriaMapper cBiddingCriteriaMapper;

    public CBiddingCriteriaService(CBiddingCriteriaRepository cBiddingCriteriaRepository, CBiddingCriteriaMapper cBiddingCriteriaMapper) {
        this.cBiddingCriteriaRepository = cBiddingCriteriaRepository;
        this.cBiddingCriteriaMapper = cBiddingCriteriaMapper;
    }

    /**
     * Save a cBiddingCriteria.
     *
     * @param cBiddingCriteriaDTO the entity to save.
     * @return the persisted entity.
     */
    public CBiddingCriteriaDTO save(CBiddingCriteriaDTO cBiddingCriteriaDTO) {
        log.debug("Request to save CBiddingCriteria : {}", cBiddingCriteriaDTO);
        CBiddingCriteria cBiddingCriteria = cBiddingCriteriaMapper.toEntity(cBiddingCriteriaDTO);
        cBiddingCriteria = cBiddingCriteriaRepository.save(cBiddingCriteria);
        return cBiddingCriteriaMapper.toDto(cBiddingCriteria);
    }

    /**
     * Get all the cBiddingCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingCriteriaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBiddingCriteria");
        return cBiddingCriteriaRepository.findAll(pageable)
            .map(cBiddingCriteriaMapper::toDto);
    }

    /**
     * Get one cBiddingCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBiddingCriteriaDTO> findOne(Long id) {
        log.debug("Request to get CBiddingCriteria : {}", id);
        return cBiddingCriteriaRepository.findById(id)
            .map(cBiddingCriteriaMapper::toDto);
    }

    /**
     * Delete the cBiddingCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBiddingCriteria : {}", id);
        cBiddingCriteriaRepository.deleteById(id);
    }
}
