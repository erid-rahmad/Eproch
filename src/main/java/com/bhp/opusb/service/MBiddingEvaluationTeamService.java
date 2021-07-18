package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvaluationTeam;
import com.bhp.opusb.repository.MBiddingEvaluationTeamRepository;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamDTO;
import com.bhp.opusb.service.mapper.MBiddingEvaluationTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingEvaluationTeam}.
 */
@Service
@Transactional
public class MBiddingEvaluationTeamService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationTeamService.class);

    private final MBiddingEvaluationTeamRepository mBiddingEvaluationTeamRepository;

    private final MBiddingEvaluationTeamMapper mBiddingEvaluationTeamMapper;

    public MBiddingEvaluationTeamService(MBiddingEvaluationTeamRepository mBiddingEvaluationTeamRepository, MBiddingEvaluationTeamMapper mBiddingEvaluationTeamMapper) {
        this.mBiddingEvaluationTeamRepository = mBiddingEvaluationTeamRepository;
        this.mBiddingEvaluationTeamMapper = mBiddingEvaluationTeamMapper;
    }

    /**
     * Save a mBiddingEvaluationTeam.
     *
     * @param mBiddingEvaluationTeamDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvaluationTeamDTO save(MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO) {
        log.debug("Request to save MBiddingEvaluationTeam : {}", mBiddingEvaluationTeamDTO);
        MBiddingEvaluationTeam mBiddingEvaluationTeam = mBiddingEvaluationTeamMapper.toEntity(mBiddingEvaluationTeamDTO);
        mBiddingEvaluationTeam = mBiddingEvaluationTeamRepository.save(mBiddingEvaluationTeam);
        return mBiddingEvaluationTeamMapper.toDto(mBiddingEvaluationTeam);
    }

    /**
     * Get all the mBiddingEvaluationTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvaluationTeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvaluationTeams");
        return mBiddingEvaluationTeamRepository.findAll(pageable)
            .map(mBiddingEvaluationTeamMapper::toDto);
    }

    /**
     * Get one mBiddingEvaluationTeam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvaluationTeamDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvaluationTeam : {}", id);
        return mBiddingEvaluationTeamRepository.findById(id)
            .map(mBiddingEvaluationTeamMapper::toDto);
    }

    /**
     * Delete the mBiddingEvaluationTeam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvaluationTeam : {}", id);
        mBiddingEvaluationTeamRepository.deleteById(id);
    }

    public MBiddingEvaluationTeamDTO findByPrequalificationId(Long id) {
        MBiddingEvaluationTeam mbet = mBiddingEvaluationTeamRepository.findByPrequalification_Id(id);
        if(mbet==null) return null;
        return mBiddingEvaluationTeamMapper.toDto(mbet);
    }
}
