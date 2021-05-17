package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalAdministration;
import com.bhp.opusb.repository.MProposalAdministrationRepository;
import com.bhp.opusb.service.dto.MProposalAdministrationDTO;
import com.bhp.opusb.service.mapper.MProposalAdministrationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalAdministration}.
 */
@Service
@Transactional
public class MProposalAdministrationService {

    private final Logger log = LoggerFactory.getLogger(MProposalAdministrationService.class);

    private final MProposalAdministrationRepository mProposalAdministrationRepository;

    private final MProposalAdministrationMapper mProposalAdministrationMapper;

    public MProposalAdministrationService(MProposalAdministrationRepository mProposalAdministrationRepository, MProposalAdministrationMapper mProposalAdministrationMapper) {
        this.mProposalAdministrationRepository = mProposalAdministrationRepository;
        this.mProposalAdministrationMapper = mProposalAdministrationMapper;
    }

    /**
     * Save a mProposalAdministration.
     *
     * @param mProposalAdministrationDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalAdministrationDTO save(MProposalAdministrationDTO mProposalAdministrationDTO) {
        log.debug("Request to save MProposalAdministration : {}", mProposalAdministrationDTO);
        MProposalAdministration mProposalAdministration = mProposalAdministrationMapper.toEntity(mProposalAdministrationDTO);
        mProposalAdministration = mProposalAdministrationRepository.save(mProposalAdministration);
        return mProposalAdministrationMapper.toDto(mProposalAdministration);
    }

    /**
     * Save mProposalAdministrations.
     *
     * @param mProposalAdministrationDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MProposalAdministrationDTO> saveRequirements(List<MProposalAdministrationDTO> mProposalAdministrationDTOs) {
        log.debug("Request to save MProposalAdministrations. size : {}", mProposalAdministrationDTOs.size());
        List<MProposalAdministration> mProposalAdministrations = mProposalAdministrationMapper.toEntity(mProposalAdministrationDTOs);
        mProposalAdministrations = mProposalAdministrationRepository.saveAll(mProposalAdministrations);
        return mProposalAdministrationMapper.toDto(mProposalAdministrations);
    }

    /**
     * Get all the mProposalAdministrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalAdministrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalAdministrations");
        return mProposalAdministrationRepository.findAll(pageable)
            .map(mProposalAdministrationMapper::toDto);
    }

    /**
     * Get one mProposalAdministration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalAdministrationDTO> findOne(Long id) {
        log.debug("Request to get MProposalAdministration : {}", id);
        return mProposalAdministrationRepository.findById(id)
            .map(mProposalAdministrationMapper::toDto);
    }

    /**
     * Delete the mProposalAdministration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalAdministration : {}", id);
        mProposalAdministrationRepository.deleteById(id);
    }
}
