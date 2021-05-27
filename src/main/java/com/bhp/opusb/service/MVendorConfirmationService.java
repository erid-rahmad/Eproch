package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVendorConfirmation;
import com.bhp.opusb.repository.MVendorConfirmationRepository;
import com.bhp.opusb.service.dto.MVendorConfirmationDTO;
import com.bhp.opusb.service.mapper.MVendorConfirmationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVendorConfirmation}.
 */
@Service
@Transactional
public class MVendorConfirmationService {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationService.class);

    private final MVendorConfirmationRepository mVendorConfirmationRepository;

    private final MVendorConfirmationMapper mVendorConfirmationMapper;

    public MVendorConfirmationService(MVendorConfirmationRepository mVendorConfirmationRepository, MVendorConfirmationMapper mVendorConfirmationMapper) {
        this.mVendorConfirmationRepository = mVendorConfirmationRepository;
        this.mVendorConfirmationMapper = mVendorConfirmationMapper;
    }

    /**
     * Save a mVendorConfirmation.
     *
     * @param mVendorConfirmationDTO the entity to save.
     * @return the persisted entity.
     */
    public MVendorConfirmationDTO save(MVendorConfirmationDTO mVendorConfirmationDTO) {
        log.debug("Request to save MVendorConfirmation : {}", mVendorConfirmationDTO);
        MVendorConfirmation mVendorConfirmation = mVendorConfirmationMapper.toEntity(mVendorConfirmationDTO);
        mVendorConfirmation = mVendorConfirmationRepository.save(mVendorConfirmation);
        return mVendorConfirmationMapper.toDto(mVendorConfirmation);
    }

    /**
     * Get all the mVendorConfirmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVendorConfirmationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVendorConfirmations");
        return mVendorConfirmationRepository.findAll(pageable)
            .map(mVendorConfirmationMapper::toDto);
    }

    /**
     * Get one mVendorConfirmation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVendorConfirmationDTO> findOne(Long id) {
        log.debug("Request to get MVendorConfirmation : {}", id);
        return mVendorConfirmationRepository.findById(id)
            .map(mVendorConfirmationMapper::toDto);
    }

    /**
     * Delete the mVendorConfirmation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVendorConfirmation : {}", id);
        mVendorConfirmationRepository.deleteById(id);
    }
}
