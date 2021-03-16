package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.repository.MBiddingSubmissionLineRepository;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingSubmissionLine}.
 */
@Service
@Transactional
public class MBiddingSubmissionLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionLineService.class);

    private final MBiddingSubmissionLineRepository mBiddingSubmissionLineRepository;

    private final MBiddingSubmissionLineMapper mBiddingSubmissionLineMapper;

    public MBiddingSubmissionLineService(MBiddingSubmissionLineRepository mBiddingSubmissionLineRepository, MBiddingSubmissionLineMapper mBiddingSubmissionLineMapper) {
        this.mBiddingSubmissionLineRepository = mBiddingSubmissionLineRepository;
        this.mBiddingSubmissionLineMapper = mBiddingSubmissionLineMapper;
    }

    /**
     * Save a mBiddingSubmissionLine.
     *
     * @param mBiddingSubmissionLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingSubmissionLineDTO save(MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO) {
        log.debug("Request to save MBiddingSubmissionLine : {}", mBiddingSubmissionLineDTO);
        MBiddingSubmissionLine mBiddingSubmissionLine = mBiddingSubmissionLineMapper.toEntity(mBiddingSubmissionLineDTO);
        mBiddingSubmissionLine = mBiddingSubmissionLineRepository.save(mBiddingSubmissionLine);
        return mBiddingSubmissionLineMapper.toDto(mBiddingSubmissionLine);
    }

    /**
     * Get all the mBiddingSubmissionLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubmissionLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSubmissionLines");
        return mBiddingSubmissionLineRepository.findAll(pageable)
            .map(mBiddingSubmissionLineMapper::toDto);
    }


    /**
     * Get one mBiddingSubmissionLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingSubmissionLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSubmissionLine : {}", id);
        return mBiddingSubmissionLineRepository.findById(id)
            .map(mBiddingSubmissionLineMapper::toDto);
    }

    /**
     * Delete the mBiddingSubmissionLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSubmissionLine : {}", id);
        mBiddingSubmissionLineRepository.deleteById(id);
    }
}
