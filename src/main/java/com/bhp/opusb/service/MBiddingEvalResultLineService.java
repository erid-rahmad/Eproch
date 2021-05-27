package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvalResultLine;
import com.bhp.opusb.repository.MBiddingEvalResultLineRepository;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingEvalResultLine}.
 */
@Service
@Transactional
public class MBiddingEvalResultLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultLineService.class);

    private final MBiddingEvalResultLineRepository mBiddingEvalResultLineRepository;

    private final MBiddingEvalResultLineMapper mBiddingEvalResultLineMapper;

    public MBiddingEvalResultLineService(MBiddingEvalResultLineRepository mBiddingEvalResultLineRepository, MBiddingEvalResultLineMapper mBiddingEvalResultLineMapper) {
        this.mBiddingEvalResultLineRepository = mBiddingEvalResultLineRepository;
        this.mBiddingEvalResultLineMapper = mBiddingEvalResultLineMapper;
    }

    /**
     * Save a mBiddingEvalResultLine.
     *
     * @param mBiddingEvalResultLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvalResultLineDTO save(MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO) {
        log.debug("Request to save MBiddingEvalResultLine : {}", mBiddingEvalResultLineDTO);
        MBiddingEvalResultLine mBiddingEvalResultLine = mBiddingEvalResultLineMapper.toEntity(mBiddingEvalResultLineDTO);
        mBiddingEvalResultLine = mBiddingEvalResultLineRepository.save(mBiddingEvalResultLine);
        return mBiddingEvalResultLineMapper.toDto(mBiddingEvalResultLine);
    }

    /**
     * Get all the mBiddingEvalResultLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalResultLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvalResultLines");
        return mBiddingEvalResultLineRepository.findAll(pageable)
            .map(mBiddingEvalResultLineMapper::toDto);
    }

    /**
     * Get one mBiddingEvalResultLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvalResultLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvalResultLine : {}", id);
        return mBiddingEvalResultLineRepository.findById(id)
            .map(mBiddingEvalResultLineMapper::toDto);
    }

    /**
     * Delete the mBiddingEvalResultLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvalResultLine : {}", id);
        mBiddingEvalResultLineRepository.deleteById(id);
    }
}
