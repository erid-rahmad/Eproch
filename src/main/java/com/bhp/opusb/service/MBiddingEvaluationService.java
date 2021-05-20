package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvaluation;
import com.bhp.opusb.repository.MBiddingEvaluationRepository;
import com.bhp.opusb.service.dto.MBiddingEvaluationDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingEvaluation}.
 */
@Service
@Transactional
public class MBiddingEvaluationService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationService.class);

    private final MBiddingEvaluationRepository mBiddingEvaluationRepository;

    private final MBiddingEvaluationMapper mBiddingEvaluationMapper;

    public MBiddingEvaluationService(MBiddingEvaluationRepository mBiddingEvaluationRepository, MBiddingEvaluationMapper mBiddingEvaluationMapper) {
        this.mBiddingEvaluationRepository = mBiddingEvaluationRepository;
        this.mBiddingEvaluationMapper = mBiddingEvaluationMapper;
    }

    /**
     * Save a mBiddingEvaluation.
     *
     * @param mBiddingEvaluationDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvaluationDTO save(MBiddingEvaluationDTO mBiddingEvaluationDTO) {
        log.debug("Request to save MBiddingEvaluation : {}", mBiddingEvaluationDTO);
        MBiddingEvaluation mBiddingEvaluation = mBiddingEvaluationMapper.toEntity(mBiddingEvaluationDTO);
        mBiddingEvaluation = mBiddingEvaluationRepository.save(mBiddingEvaluation);
        return mBiddingEvaluationMapper.toDto(mBiddingEvaluation);
    }

    /**
     * Get all the mBiddingEvaluations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvaluations");
        return mBiddingEvaluationRepository.findAll(pageable)
            .map(mBiddingEvaluationMapper::toDto);
    }

    /**
     * Get one mBiddingEvaluation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvaluationDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvaluation : {}", id);
        return mBiddingEvaluationRepository.findById(id)
            .map(mBiddingEvaluationMapper::toDto);
    }

    /**
     * Delete the mBiddingEvaluation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvaluation : {}", id);
        mBiddingEvaluationRepository.deleteById(id);
    }
}
