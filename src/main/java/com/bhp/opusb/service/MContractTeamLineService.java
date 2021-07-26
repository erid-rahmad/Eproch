package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractTeamLine;
import com.bhp.opusb.repository.MContractTeamLineRepository;
import com.bhp.opusb.service.dto.MContractTeamLineDTO;
import com.bhp.opusb.service.mapper.MContractTeamLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractTeamLine}.
 */
@Service
@Transactional
public class MContractTeamLineService {

    private final Logger log = LoggerFactory.getLogger(MContractTeamLineService.class);

    private final MContractTeamLineRepository mContractTeamLineRepository;

    private final MContractTeamLineMapper mContractTeamLineMapper;

    public MContractTeamLineService(MContractTeamLineRepository mContractTeamLineRepository, MContractTeamLineMapper mContractTeamLineMapper) {
        this.mContractTeamLineRepository = mContractTeamLineRepository;
        this.mContractTeamLineMapper = mContractTeamLineMapper;
    }

    /**
     * Save a mContractTeamLine.
     *
     * @param mContractTeamLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractTeamLineDTO save(MContractTeamLineDTO mContractTeamLineDTO) {
        log.debug("Request to save MContractTeamLine : {}", mContractTeamLineDTO);
        MContractTeamLine mContractTeamLine = mContractTeamLineMapper.toEntity(mContractTeamLineDTO);
        mContractTeamLine = mContractTeamLineRepository.save(mContractTeamLine);
        return mContractTeamLineMapper.toDto(mContractTeamLine);
    }

    /**
     * Get all the mContractTeamLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTeamLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractTeamLines");
        return mContractTeamLineRepository.findAll(pageable)
            .map(mContractTeamLineMapper::toDto);
    }

    /**
     * Get one mContractTeamLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractTeamLineDTO> findOne(Long id) {
        log.debug("Request to get MContractTeamLine : {}", id);
        return mContractTeamLineRepository.findById(id)
            .map(mContractTeamLineMapper::toDto);
    }

    /**
     * Delete the mContractTeamLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractTeamLine : {}", id);
        mContractTeamLineRepository.deleteById(id);
    }
}
