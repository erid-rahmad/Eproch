package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionParticipant;
import com.bhp.opusb.repository.MAuctionParticipantRepository;
import com.bhp.opusb.service.dto.MAuctionParticipantDTO;
import com.bhp.opusb.service.mapper.MAuctionParticipantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MAuctionParticipant}.
 */
@Service
@Transactional
public class MAuctionParticipantService {

    private final Logger log = LoggerFactory.getLogger(MAuctionParticipantService.class);

    private final MAuctionParticipantRepository mAuctionParticipantRepository;

    private final MAuctionParticipantMapper mAuctionParticipantMapper;

    public MAuctionParticipantService(MAuctionParticipantRepository mAuctionParticipantRepository, MAuctionParticipantMapper mAuctionParticipantMapper) {
        this.mAuctionParticipantRepository = mAuctionParticipantRepository;
        this.mAuctionParticipantMapper = mAuctionParticipantMapper;
    }

    /**
     * Save a mAuctionParticipant.
     *
     * @param mAuctionParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionParticipantDTO save(MAuctionParticipantDTO mAuctionParticipantDTO) {
        log.debug("Request to save MAuctionParticipant : {}", mAuctionParticipantDTO);
        MAuctionParticipant mAuctionParticipant = mAuctionParticipantMapper.toEntity(mAuctionParticipantDTO);
        mAuctionParticipant = mAuctionParticipantRepository.save(mAuctionParticipant);
        return mAuctionParticipantMapper.toDto(mAuctionParticipant);
    }

    /**
     * Get all the mAuctionParticipants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionParticipants");
        return mAuctionParticipantRepository.findAll(pageable)
            .map(mAuctionParticipantMapper::toDto);
    }

    /**
     * Get one mAuctionParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionParticipantDTO> findOne(Long id) {
        log.debug("Request to get MAuctionParticipant : {}", id);
        return mAuctionParticipantRepository.findById(id)
            .map(mAuctionParticipantMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MAuctionParticipantDTO> findByAuctionId(Long auctionId) {
        log.debug("Request to get MAuctionParticipants for Auction : {}", auctionId);
        return mAuctionParticipantRepository.findByAuction_Id(auctionId)
            .stream()
            .map(mAuctionParticipantMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Delete the mAuctionParticipant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionParticipant : {}", id);
        mAuctionParticipantRepository.deleteById(id);
    }
}
