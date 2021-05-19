package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvaluationLine;
import com.bhp.opusb.repository.MBiddingEvaluationLineRepository;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingEvaluationLine}.
 */
@Service
@Transactional
public class MBiddingEvaluationLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationLineService.class);

    private final MBiddingEvaluationLineRepository mBiddingEvaluationLineRepository;

    private final MBiddingEvaluationLineMapper mBiddingEvaluationLineMapper;

    public MBiddingEvaluationLineService(MBiddingEvaluationLineRepository mBiddingEvaluationLineRepository, MBiddingEvaluationLineMapper mBiddingEvaluationLineMapper) {
        this.mBiddingEvaluationLineRepository = mBiddingEvaluationLineRepository;
        this.mBiddingEvaluationLineMapper = mBiddingEvaluationLineMapper;
    }

    /**
     * Save a mBiddingEvaluationLine.
     *
     * @param mBiddingEvaluationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvaluationLineDTO save(MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO) {
        log.debug("Request to save MBiddingEvaluationLine : {}", mBiddingEvaluationLineDTO);
        MBiddingEvaluationLine mBiddingEvaluationLine = mBiddingEvaluationLineMapper.toEntity(mBiddingEvaluationLineDTO);
        mBiddingEvaluationLine = mBiddingEvaluationLineRepository.save(mBiddingEvaluationLine);
        return mBiddingEvaluationLineMapper.toDto(mBiddingEvaluationLine);
    }

    /**
     * Get all the mBiddingEvaluationLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvaluationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvaluationLines");
        return mBiddingEvaluationLineRepository.findAll(pageable)
            .map(mBiddingEvaluationLineMapper::toDto);
    }

    /**
     * Get one mBiddingEvaluationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvaluationLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvaluationLine : {}", id);
        return mBiddingEvaluationLineRepository.findById(id)
            .map(mBiddingEvaluationLineMapper::toDto);
    }

    /**
     * Delete the mBiddingEvaluationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvaluationLine : {}", id);
        mBiddingEvaluationLineRepository.deleteById(id);
    }
}
