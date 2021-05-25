package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionTeam;
import com.bhp.opusb.repository.MAuctionTeamRepository;
import com.bhp.opusb.service.dto.MAuctionTeamDTO;
import com.bhp.opusb.service.mapper.MAuctionTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionTeam}.
 */
@Service
@Transactional
public class MAuctionTeamService {

    private final Logger log = LoggerFactory.getLogger(MAuctionTeamService.class);

    private final MAuctionTeamRepository mAuctionTeamRepository;

    private final MAuctionTeamMapper mAuctionTeamMapper;

    public MAuctionTeamService(MAuctionTeamRepository mAuctionTeamRepository, MAuctionTeamMapper mAuctionTeamMapper) {
        this.mAuctionTeamRepository = mAuctionTeamRepository;
        this.mAuctionTeamMapper = mAuctionTeamMapper;
    }

    /**
     * Save a mAuctionTeam.
     *
     * @param mAuctionTeamDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionTeamDTO save(MAuctionTeamDTO mAuctionTeamDTO) {
        log.debug("Request to save MAuctionTeam : {}", mAuctionTeamDTO);
        MAuctionTeam mAuctionTeam = mAuctionTeamMapper.toEntity(mAuctionTeamDTO);
        mAuctionTeam = mAuctionTeamRepository.save(mAuctionTeam);
        return mAuctionTeamMapper.toDto(mAuctionTeam);
    }

    /**
     * Get all the mAuctionTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionTeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionTeams");
        return mAuctionTeamRepository.findAll(pageable)
            .map(mAuctionTeamMapper::toDto);
    }

    /**
     * Get one mAuctionTeam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionTeamDTO> findOne(Long id) {
        log.debug("Request to get MAuctionTeam : {}", id);
        return mAuctionTeamRepository.findById(id)
            .map(mAuctionTeamMapper::toDto);
    }

    /**
     * Delete the mAuctionTeam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionTeam : {}", id);
        mAuctionTeamRepository.deleteById(id);
    }
}
