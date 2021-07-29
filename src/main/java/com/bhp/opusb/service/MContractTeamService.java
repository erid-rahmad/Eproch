package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractTeam;
import com.bhp.opusb.domain.MContractTeamLine;
import com.bhp.opusb.repository.MContractTeamLineRepository;
import com.bhp.opusb.repository.MContractTeamRepository;
import com.bhp.opusb.service.dto.MContractTeamDTO;
import com.bhp.opusb.service.mapper.MContractTeamLineMapper;
import com.bhp.opusb.service.mapper.MContractTeamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractTeam}.
 */
@Service
@Transactional
public class MContractTeamService {

    private final Logger log = LoggerFactory.getLogger(MContractTeamService.class);

    private final MContractTeamRepository mContractTeamRepository;
    private final MContractTeamLineRepository mContractTeamLineRepository;

    private final MContractTeamMapper mContractTeamMapper;
    private final MContractTeamLineMapper mContractTeamLineMapper;

    public MContractTeamService(MContractTeamRepository mContractTeamRepository, MContractTeamMapper mContractTeamMapper,
        MContractTeamLineMapper mContractTeamLineMapper, MContractTeamLineRepository mContractTeamLineRepository) {
        this.mContractTeamRepository = mContractTeamRepository;
        this.mContractTeamMapper = mContractTeamMapper;
        this.mContractTeamLineMapper = mContractTeamLineMapper;
        this.mContractTeamLineRepository = mContractTeamLineRepository;
    }

    /**
     * Save a mContractTeam.
     *
     * @param mContractTeamDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractTeamDTO save(MContractTeamDTO mContractTeamDTO) {
        log.debug("Request to save MContractTeam : {}", mContractTeamDTO);
        MContractTeam mContractTeam = mContractTeamMapper.toEntity(mContractTeamDTO);
        mContractTeam.setStatus(mContractTeamDTO.getMembers().size()>0?"A":"U");
        mContractTeam = mContractTeamRepository.save(mContractTeam);

        List<MContractTeamLine> lines = mContractTeamLineMapper.toEntity(mContractTeamDTO.getMembers());

        for(MContractTeamLine mctl: lines){
            mctl.setAdOrganization(mContractTeam.getAdOrganization());
            mctl.setContractTeam(mContractTeam);
        }

        mContractTeamLineRepository.saveAll(lines);
        mContractTeamLineRepository.deleteAll(mContractTeamLineMapper.toEntity(mContractTeamDTO.getDeletedLines()));

        return mContractTeamMapper.toDto(mContractTeam);
    }

    /**
     * Get all the mContractTeams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTeamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractTeams");
        return mContractTeamRepository.findAll(pageable)
            .map(mContractTeamMapper::toDto);
    }

    /**
     * Get one mContractTeam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractTeamDTO> findOne(Long id) {
        log.debug("Request to get MContractTeam : {}", id);
        return mContractTeamRepository.findById(id)
            .map(mContractTeamMapper::toDto);
    }

    /**
     * Delete the mContractTeam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractTeam : {}", id);
        mContractTeamRepository.deleteById(id);
    }

    public MContractTeamDTO findByContractId(Long id) {
        MContractTeam mct = mContractTeamRepository.findByContract_Id(id);
        if(mct==null) return null;
        return mContractTeamMapper.toDto(mct);
    }
}
