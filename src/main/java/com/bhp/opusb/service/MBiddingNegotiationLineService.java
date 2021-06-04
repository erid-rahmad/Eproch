package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.repository.MBiddingNegotiationLineRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingNegotiationLine}.
 */
@Service
@Transactional
public class MBiddingNegotiationLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationLineService.class);

    private final MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository;

    private final MBiddingNegotiationLineMapper mBiddingNegotiationLineMapper;

    public MBiddingNegotiationLineService(MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository, MBiddingNegotiationLineMapper mBiddingNegotiationLineMapper) {
        this.mBiddingNegotiationLineRepository = mBiddingNegotiationLineRepository;
        this.mBiddingNegotiationLineMapper = mBiddingNegotiationLineMapper;
    }

    /**
     * Save a mBiddingNegotiationLine.
     *
     * @param mBiddingNegotiationLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingNegotiationLineDTO save(MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO) {
        log.debug("Request to save MBiddingNegotiationLine : {}", mBiddingNegotiationLineDTO);
        MBiddingNegotiationLine mBiddingNegotiationLine = mBiddingNegotiationLineMapper.toEntity(mBiddingNegotiationLineDTO);
        mBiddingNegotiationLine = mBiddingNegotiationLineRepository.save(mBiddingNegotiationLine);
        return mBiddingNegotiationLineMapper.toDto(mBiddingNegotiationLine);
    }

    /**
     * Get all the mBiddingNegotiationLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingNegotiationLines");
        return mBiddingNegotiationLineRepository.findAll(pageable)
            .map(mBiddingNegotiationLineMapper::toDto);
    }

    /**
     * Get one mBiddingNegotiationLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingNegotiationLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingNegotiationLine : {}", id);
        return mBiddingNegotiationLineRepository.findById(id)
            .map(mBiddingNegotiationLineMapper::toDto);
    }

    /**
     * Delete the mBiddingNegotiationLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingNegotiationLine : {}", id);
        mBiddingNegotiationLineRepository.deleteById(id);
    }
}
