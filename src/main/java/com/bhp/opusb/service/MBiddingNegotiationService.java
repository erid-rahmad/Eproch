package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingNegotiation;
import com.bhp.opusb.repository.MBiddingNegotiationRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingNegotiation}.
 */
@Service
@Transactional
public class MBiddingNegotiationService {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationService.class);

    private final MBiddingNegotiationRepository mBiddingNegotiationRepository;

    private final MBiddingNegotiationMapper mBiddingNegotiationMapper;

    public MBiddingNegotiationService(MBiddingNegotiationRepository mBiddingNegotiationRepository, MBiddingNegotiationMapper mBiddingNegotiationMapper) {
        this.mBiddingNegotiationRepository = mBiddingNegotiationRepository;
        this.mBiddingNegotiationMapper = mBiddingNegotiationMapper;
    }

    /**
     * Save a mBiddingNegotiation.
     *
     * @param mBiddingNegotiationDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingNegotiationDTO save(MBiddingNegotiationDTO mBiddingNegotiationDTO) {
        log.debug("Request to save MBiddingNegotiation : {}", mBiddingNegotiationDTO);
        MBiddingNegotiation mBiddingNegotiation = mBiddingNegotiationMapper.toEntity(mBiddingNegotiationDTO);
        mBiddingNegotiation = mBiddingNegotiationRepository.save(mBiddingNegotiation);
        return mBiddingNegotiationMapper.toDto(mBiddingNegotiation);
    }

    /**
     * Get all the mBiddingNegotiations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingNegotiations");
        return mBiddingNegotiationRepository.findAll(pageable)
            .map(mBiddingNegotiationMapper::toDto);
    }

    /**
     * Get one mBiddingNegotiation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingNegotiationDTO> findOne(Long id) {
        log.debug("Request to get MBiddingNegotiation : {}", id);
        return mBiddingNegotiationRepository.findById(id)
            .map(mBiddingNegotiationMapper::toDto);
    }

    /**
     * Delete the mBiddingNegotiation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingNegotiation : {}", id);
        mBiddingNegotiationRepository.deleteById(id);
    }
}
