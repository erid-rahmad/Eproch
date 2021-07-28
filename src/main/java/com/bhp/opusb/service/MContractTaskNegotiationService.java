package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractTaskNegotiation;
import com.bhp.opusb.repository.MContractTaskNegotiationRepository;
import com.bhp.opusb.service.dto.MContractTaskNegotiationDTO;
import com.bhp.opusb.service.mapper.MContractTaskNegotiationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractTaskNegotiation}.
 */
@Service
@Transactional
public class MContractTaskNegotiationService {

    private final Logger log = LoggerFactory.getLogger(MContractTaskNegotiationService.class);

    private final MContractTaskNegotiationRepository mContractTaskNegotiationRepository;

    private final MContractTaskNegotiationMapper mContractTaskNegotiationMapper;

    public MContractTaskNegotiationService(MContractTaskNegotiationRepository mContractTaskNegotiationRepository, MContractTaskNegotiationMapper mContractTaskNegotiationMapper) {
        this.mContractTaskNegotiationRepository = mContractTaskNegotiationRepository;
        this.mContractTaskNegotiationMapper = mContractTaskNegotiationMapper;
    }

    /**
     * Save a mContractTaskNegotiation.
     *
     * @param mContractTaskNegotiationDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractTaskNegotiationDTO save(MContractTaskNegotiationDTO mContractTaskNegotiationDTO) {
        log.debug("Request to save MContractTaskNegotiation : {}", mContractTaskNegotiationDTO);
        MContractTaskNegotiation mContractTaskNegotiation = mContractTaskNegotiationMapper.toEntity(mContractTaskNegotiationDTO);
        mContractTaskNegotiation = mContractTaskNegotiationRepository.save(mContractTaskNegotiation);
        return mContractTaskNegotiationMapper.toDto(mContractTaskNegotiation);
    }

    /**
     * Get all the mContractTaskNegotiations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractTaskNegotiationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractTaskNegotiations");
        return mContractTaskNegotiationRepository.findAll(pageable)
            .map(mContractTaskNegotiationMapper::toDto);
    }

    /**
     * Get one mContractTaskNegotiation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractTaskNegotiationDTO> findOne(Long id) {
        log.debug("Request to get MContractTaskNegotiation : {}", id);
        return mContractTaskNegotiationRepository.findById(id)
            .map(mContractTaskNegotiationMapper::toDto);
    }

    /**
     * Delete the mContractTaskNegotiation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractTaskNegotiation : {}", id);
        mContractTaskNegotiationRepository.deleteById(id);
    }
}
