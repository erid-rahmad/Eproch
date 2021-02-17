package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.mapper.MBiddingLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingLine}.
 */
@Service
@Transactional
public class MBiddingLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingLineService.class);

    private final MBiddingLineRepository mBiddingLineRepository;

    private final MBiddingLineMapper mBiddingLineMapper;

    public MBiddingLineService(MBiddingLineRepository mBiddingLineRepository, MBiddingLineMapper mBiddingLineMapper) {
        this.mBiddingLineRepository = mBiddingLineRepository;
        this.mBiddingLineMapper = mBiddingLineMapper;
    }

    /**
     * Save a mBiddingLine.
     *
     * @param mBiddingLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingLineDTO save(MBiddingLineDTO mBiddingLineDTO) {
        log.debug("Request to save MBiddingLine : {}", mBiddingLineDTO);
        MBiddingLine mBiddingLine = mBiddingLineMapper.toEntity(mBiddingLineDTO);
        mBiddingLine = mBiddingLineRepository.save(mBiddingLine);
        return mBiddingLineMapper.toDto(mBiddingLine);
    }

    /**
     * Get all the mBiddingLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingLines");
        return mBiddingLineRepository.findAll(pageable)
            .map(mBiddingLineMapper::toDto);
    }

    /**
     * Get one mBiddingLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingLine : {}", id);
        return mBiddingLineRepository.findById(id)
            .map(mBiddingLineMapper::toDto);
    }

    /**
     * Delete the mBiddingLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingLine : {}", id);
        mBiddingLineRepository.deleteById(id);
    }
}
