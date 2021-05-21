package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.repository.MBiddingEvalResultRepository;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingEvalResult}.
 */
@Service
@Transactional
public class MBiddingEvalResultService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultService.class);

    private final MBiddingEvalResultRepository mBiddingEvalResultRepository;

    private final MBiddingEvalResultMapper mBiddingEvalResultMapper;

    public MBiddingEvalResultService(MBiddingEvalResultRepository mBiddingEvalResultRepository, MBiddingEvalResultMapper mBiddingEvalResultMapper) {
        this.mBiddingEvalResultRepository = mBiddingEvalResultRepository;
        this.mBiddingEvalResultMapper = mBiddingEvalResultMapper;
    }

    /**
     * Save a mBiddingEvalResult.
     *
     * @param mBiddingEvalResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvalResultDTO save(MBiddingEvalResultDTO mBiddingEvalResultDTO) {
        log.debug("Request to save MBiddingEvalResult : {}", mBiddingEvalResultDTO);
        MBiddingEvalResult mBiddingEvalResult = mBiddingEvalResultMapper.toEntity(mBiddingEvalResultDTO);
        mBiddingEvalResult = mBiddingEvalResultRepository.save(mBiddingEvalResult);
        return mBiddingEvalResultMapper.toDto(mBiddingEvalResult);
    }

    /**
     * Get all the mBiddingEvalResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvalResults");
        return mBiddingEvalResultRepository.findAll(pageable)
            .map(mBiddingEvalResultMapper::toDto);
    }

    /**
     * Get one mBiddingEvalResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvalResultDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvalResult : {}", id);
        return mBiddingEvalResultRepository.findById(id)
            .map(mBiddingEvalResultMapper::toDto);
    }

    /**
     * Delete the mBiddingEvalResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvalResult : {}", id);
        mBiddingEvalResultRepository.deleteById(id);
    }
}
