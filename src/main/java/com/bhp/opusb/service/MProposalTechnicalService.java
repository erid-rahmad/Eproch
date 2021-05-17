package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalTechnical;
import com.bhp.opusb.repository.MProposalTechnicalRepository;
import com.bhp.opusb.service.dto.MProposalTechnicalDTO;
import com.bhp.opusb.service.mapper.MProposalTechnicalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalTechnical}.
 */
@Service
@Transactional
public class MProposalTechnicalService {

    private final Logger log = LoggerFactory.getLogger(MProposalTechnicalService.class);

    private final MProposalTechnicalRepository mProposalTechnicalRepository;

    private final MProposalTechnicalMapper mProposalTechnicalMapper;

    public MProposalTechnicalService(MProposalTechnicalRepository mProposalTechnicalRepository, MProposalTechnicalMapper mProposalTechnicalMapper) {
        this.mProposalTechnicalRepository = mProposalTechnicalRepository;
        this.mProposalTechnicalMapper = mProposalTechnicalMapper;
    }

    /**
     * Save a mProposalTechnical.
     *
     * @param mProposalTechnicalDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalTechnicalDTO save(MProposalTechnicalDTO mProposalTechnicalDTO) {
        log.debug("Request to save MProposalTechnical : {}", mProposalTechnicalDTO);
        MProposalTechnical mProposalTechnical = mProposalTechnicalMapper.toEntity(mProposalTechnicalDTO);
        mProposalTechnical = mProposalTechnicalRepository.save(mProposalTechnical);
        return mProposalTechnicalMapper.toDto(mProposalTechnical);
    }

    /**
     * Save mProposalTechnicals.
     *
     * @param mProposalTechnicalDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MProposalTechnicalDTO> saveRequirements(List<MProposalTechnicalDTO> mProposalTechnicalDTOs) {
        log.debug("Request to save MProposalTechnicals. size : {}", mProposalTechnicalDTOs.size());
        List<MProposalTechnical> mProposalTechnicals = mProposalTechnicalMapper.toEntity(mProposalTechnicalDTOs);
        mProposalTechnicals = mProposalTechnicalRepository.saveAll(mProposalTechnicals);
        return mProposalTechnicalMapper.toDto(mProposalTechnicals);
    }

    /**
     * Get all the mProposalTechnicals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalTechnicalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalTechnicals");
        return mProposalTechnicalRepository.findAll(pageable)
            .map(mProposalTechnicalMapper::toDto);
    }

    /**
     * Get one mProposalTechnical by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalTechnicalDTO> findOne(Long id) {
        log.debug("Request to get MProposalTechnical : {}", id);
        return mProposalTechnicalRepository.findById(id)
            .map(mProposalTechnicalMapper::toDto);
    }

    /**
     * Delete the mProposalTechnical by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalTechnical : {}", id);
        mProposalTechnicalRepository.deleteById(id);
    }
}
