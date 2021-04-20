package com.bhp.opusb.service;

import com.bhp.opusb.domain.CBiddingSubCriteriaLine;
import com.bhp.opusb.repository.CBiddingSubCriteriaLineRepository;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineDTO;
import com.bhp.opusb.service.mapper.CBiddingSubCriteriaLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CBiddingSubCriteriaLine}.
 */
@Service
@Transactional
public class CBiddingSubCriteriaLineService {

    private final Logger log = LoggerFactory.getLogger(CBiddingSubCriteriaLineService.class);

    private final CBiddingSubCriteriaLineRepository cBiddingSubCriteriaLineRepository;

    private final CBiddingSubCriteriaLineMapper cBiddingSubCriteriaLineMapper;

    public CBiddingSubCriteriaLineService(CBiddingSubCriteriaLineRepository cBiddingSubCriteriaLineRepository, CBiddingSubCriteriaLineMapper cBiddingSubCriteriaLineMapper) {
        this.cBiddingSubCriteriaLineRepository = cBiddingSubCriteriaLineRepository;
        this.cBiddingSubCriteriaLineMapper = cBiddingSubCriteriaLineMapper;
    }

    /**
     * Save a cBiddingSubCriteriaLine.
     *
     * @param cBiddingSubCriteriaLineDTO the entity to save.
     * @return the persisted entity.
     */
    public CBiddingSubCriteriaLineDTO save(CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO) {
        log.debug("Request to save CBiddingSubCriteriaLine : {}", cBiddingSubCriteriaLineDTO);
        CBiddingSubCriteriaLine cBiddingSubCriteriaLine = cBiddingSubCriteriaLineMapper.toEntity(cBiddingSubCriteriaLineDTO);
        cBiddingSubCriteriaLine = cBiddingSubCriteriaLineRepository.save(cBiddingSubCriteriaLine);
        return cBiddingSubCriteriaLineMapper.toDto(cBiddingSubCriteriaLine);
    }

    /**
     * Get all the cBiddingSubCriteriaLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CBiddingSubCriteriaLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CBiddingSubCriteriaLines");
        return cBiddingSubCriteriaLineRepository.findAll(pageable)
            .map(cBiddingSubCriteriaLineMapper::toDto);
    }

    /**
     * Get one cBiddingSubCriteriaLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CBiddingSubCriteriaLineDTO> findOne(Long id) {
        log.debug("Request to get CBiddingSubCriteriaLine : {}", id);
        return cBiddingSubCriteriaLineRepository.findById(id)
            .map(cBiddingSubCriteriaLineMapper::toDto);
    }

    /**
     * Delete the cBiddingSubCriteriaLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CBiddingSubCriteriaLine : {}", id);
        cBiddingSubCriteriaLineRepository.deleteById(id);
    }
}
