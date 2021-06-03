package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuction;
import com.bhp.opusb.repository.MAuctionRepository;
import com.bhp.opusb.service.dto.MAuctionDTO;
import com.bhp.opusb.service.mapper.MAuctionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuction}.
 */
@Service
@Transactional
public class MAuctionService {

    private final Logger log = LoggerFactory.getLogger(MAuctionService.class);

    private final MAuctionRepository mAuctionRepository;

    private final MAuctionMapper mAuctionMapper;

    public MAuctionService(MAuctionRepository mAuctionRepository, MAuctionMapper mAuctionMapper) {
        this.mAuctionRepository = mAuctionRepository;
        this.mAuctionMapper = mAuctionMapper;
    }

    /**
     * Save a mAuction.
     *
     * @param mAuctionDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionDTO save(MAuctionDTO mAuctionDTO) {
        log.debug("Request to save MAuction : {}", mAuctionDTO);
        MAuction mAuction = mAuctionMapper.toEntity(mAuctionDTO);
        mAuction = mAuctionRepository.save(mAuction);
        return mAuctionMapper.toDto(mAuction);
    }

    /**
     * Get all the mAuctions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctions");
        return mAuctionRepository.findAll(pageable)
            .map(mAuctionMapper::toDto);
    }

    /**
     * Get one mAuction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionDTO> findOne(Long id) {
        log.debug("Request to get MAuction : {}", id);
        return mAuctionRepository.findById(id)
            .map(mAuctionMapper::toDto);
    }

    /**
     * Delete the mAuction by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuction : {}", id);
        mAuctionRepository.deleteById(id);
    }
}
