package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBidNegoPrice;
import com.bhp.opusb.repository.MBidNegoPriceRepository;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBidNegoPrice}.
 */
@Service
@Transactional
public class MBidNegoPriceService {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceService.class);

    private final MBidNegoPriceRepository mBidNegoPriceRepository;

    private final MBidNegoPriceMapper mBidNegoPriceMapper;

    public MBidNegoPriceService(MBidNegoPriceRepository mBidNegoPriceRepository, MBidNegoPriceMapper mBidNegoPriceMapper) {
        this.mBidNegoPriceRepository = mBidNegoPriceRepository;
        this.mBidNegoPriceMapper = mBidNegoPriceMapper;
    }

    /**
     * Save a mBidNegoPrice.
     *
     * @param mBidNegoPriceDTO the entity to save.
     * @return the persisted entity.
     */
    public MBidNegoPriceDTO save(MBidNegoPriceDTO mBidNegoPriceDTO) {
        log.debug("Request to save MBidNegoPrice : {}", mBidNegoPriceDTO);
        MBidNegoPrice mBidNegoPrice = mBidNegoPriceMapper.toEntity(mBidNegoPriceDTO);
        mBidNegoPrice = mBidNegoPriceRepository.save(mBidNegoPrice);
        return mBidNegoPriceMapper.toDto(mBidNegoPrice);
    }

    /**
     * Get all the mBidNegoPrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBidNegoPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBidNegoPrices");
        return mBidNegoPriceRepository.findAll(pageable)
            .map(mBidNegoPriceMapper::toDto);
    }

    /**
     * Get one mBidNegoPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBidNegoPriceDTO> findOne(Long id) {
        log.debug("Request to get MBidNegoPrice : {}", id);
        return mBidNegoPriceRepository.findById(id)
            .map(mBidNegoPriceMapper::toDto);
    }

    /**
     * Delete the mBidNegoPrice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBidNegoPrice : {}", id);
        mBidNegoPriceRepository.deleteById(id);
    }
}
