package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProductPrice;
import com.bhp.opusb.repository.MProductPriceRepository;
import com.bhp.opusb.service.dto.MProductPriceDTO;
import com.bhp.opusb.service.mapper.MProductPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MProductPrice}.
 */
@Service
@Transactional
public class MProductPriceService {

    private final Logger log = LoggerFactory.getLogger(MProductPriceService.class);

    private final MProductPriceRepository mProductPriceRepository;

    private final MProductPriceMapper mProductPriceMapper;

    public MProductPriceService(MProductPriceRepository mProductPriceRepository, MProductPriceMapper mProductPriceMapper) {
        this.mProductPriceRepository = mProductPriceRepository;
        this.mProductPriceMapper = mProductPriceMapper;
    }

    /**
     * Save a mProductPrice.
     *
     * @param mProductPriceDTO the entity to save.
     * @return the persisted entity.
     */
    public MProductPriceDTO save(MProductPriceDTO mProductPriceDTO) {
        log.debug("Request to save MProductPrice : {}", mProductPriceDTO);
        MProductPrice mProductPrice = mProductPriceMapper.toEntity(mProductPriceDTO);
        mProductPrice = mProductPriceRepository.save(mProductPrice);
        return mProductPriceMapper.toDto(mProductPrice);
    }

    /**
     * Get all the mProductPrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProductPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProductPrices");
        return mProductPriceRepository.findAll(pageable)
            .map(mProductPriceMapper::toDto);
    }


    /**
     * Get one mProductPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProductPriceDTO> findOne(Long id) {
        log.debug("Request to get MProductPrice : {}", id);
        return mProductPriceRepository.findById(id)
            .map(mProductPriceMapper::toDto);
    }

    /**
     * Delete the mProductPrice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProductPrice : {}", id);
        mProductPriceRepository.deleteById(id);
    }
}
