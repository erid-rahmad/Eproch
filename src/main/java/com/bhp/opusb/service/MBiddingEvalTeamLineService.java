package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvalTeamLine;
import com.bhp.opusb.repository.MBiddingEvalTeamLineRepository;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalTeamLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingEvalTeamLine}.
 */
@Service
@Transactional
public class MBiddingEvalTeamLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalTeamLineService.class);

    private final MBiddingEvalTeamLineRepository mBiddingEvalTeamLineRepository;

    private final MBiddingEvalTeamLineMapper mBiddingEvalTeamLineMapper;

    public MBiddingEvalTeamLineService(MBiddingEvalTeamLineRepository mBiddingEvalTeamLineRepository, MBiddingEvalTeamLineMapper mBiddingEvalTeamLineMapper) {
        this.mBiddingEvalTeamLineRepository = mBiddingEvalTeamLineRepository;
        this.mBiddingEvalTeamLineMapper = mBiddingEvalTeamLineMapper;
    }

    /**
     * Save a mBiddingEvalTeamLine.
     *
     * @param mBiddingEvalTeamLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvalTeamLineDTO save(MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO) {
        log.debug("Request to save MBiddingEvalTeamLine : {}", mBiddingEvalTeamLineDTO);
        MBiddingEvalTeamLine mBiddingEvalTeamLine = mBiddingEvalTeamLineMapper.toEntity(mBiddingEvalTeamLineDTO);
        mBiddingEvalTeamLine = mBiddingEvalTeamLineRepository.save(mBiddingEvalTeamLine);
        return mBiddingEvalTeamLineMapper.toDto(mBiddingEvalTeamLine);
    }

    /**
     * Get all the mBiddingEvalTeamLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalTeamLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvalTeamLines");
        return mBiddingEvalTeamLineRepository.findAll(pageable)
            .map(mBiddingEvalTeamLineMapper::toDto);
    }

    /**
     * Get one mBiddingEvalTeamLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvalTeamLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvalTeamLine : {}", id);
        return mBiddingEvalTeamLineRepository.findById(id)
            .map(mBiddingEvalTeamLineMapper::toDto);
    }

    /**
     * Delete the mBiddingEvalTeamLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvalTeamLine : {}", id);
        mBiddingEvalTeamLineRepository.deleteById(id);
    }
}
