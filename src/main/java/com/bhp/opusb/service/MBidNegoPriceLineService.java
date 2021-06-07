package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBidNegoPriceLine;
import com.bhp.opusb.repository.MBidNegoPriceLineRepository;
import com.bhp.opusb.service.dto.MBidNegoPriceLineDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBidNegoPriceLine}.
 */
@Service
@Transactional
public class MBidNegoPriceLineService {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceLineService.class);

    private final MBidNegoPriceLineRepository mBidNegoPriceLineRepository;

    private final MBidNegoPriceLineMapper mBidNegoPriceLineMapper;

    public MBidNegoPriceLineService(MBidNegoPriceLineRepository mBidNegoPriceLineRepository, MBidNegoPriceLineMapper mBidNegoPriceLineMapper) {
        this.mBidNegoPriceLineRepository = mBidNegoPriceLineRepository;
        this.mBidNegoPriceLineMapper = mBidNegoPriceLineMapper;
    }

    /**
     * Save a mBidNegoPriceLine.
     *
     * @param mBidNegoPriceLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBidNegoPriceLineDTO save(MBidNegoPriceLineDTO mBidNegoPriceLineDTO) {
        log.debug("Request to save MBidNegoPriceLine : {}", mBidNegoPriceLineDTO);
        MBidNegoPriceLine mBidNegoPriceLine = mBidNegoPriceLineMapper.toEntity(mBidNegoPriceLineDTO);
        mBidNegoPriceLine = mBidNegoPriceLineRepository.save(mBidNegoPriceLine);
        return mBidNegoPriceLineMapper.toDto(mBidNegoPriceLine);
    }

    /**
     * Get all the mBidNegoPriceLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBidNegoPriceLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBidNegoPriceLines");
        return mBidNegoPriceLineRepository.findAll(pageable)
            .map(mBidNegoPriceLineMapper::toDto);
    }

    /**
     * Get one mBidNegoPriceLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBidNegoPriceLineDTO> findOne(Long id) {
        log.debug("Request to get MBidNegoPriceLine : {}", id);
        return mBidNegoPriceLineRepository.findById(id)
            .map(mBidNegoPriceLineMapper::toDto);
    }

    /**
     * Delete the mBidNegoPriceLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBidNegoPriceLine : {}", id);
        mBidNegoPriceLineRepository.deleteById(id);
    }
}
