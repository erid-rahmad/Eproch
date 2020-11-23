package com.bhp.opusb.service;

import com.bhp.opusb.domain.MVerificationTax;
import com.bhp.opusb.repository.MVerificationTaxRepository;
import com.bhp.opusb.service.dto.MVerificationTaxDTO;
import com.bhp.opusb.service.mapper.MVerificationTaxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MVerificationTax}.
 */
@Service
@Transactional
public class MVerificationTaxService {

    private final Logger log = LoggerFactory.getLogger(MVerificationTaxService.class);

    private final MVerificationTaxRepository mVerificationTaxRepository;

    private final MVerificationTaxMapper mVerificationTaxMapper;

    public MVerificationTaxService(MVerificationTaxRepository mVerificationTaxRepository, MVerificationTaxMapper mVerificationTaxMapper) {
        this.mVerificationTaxRepository = mVerificationTaxRepository;
        this.mVerificationTaxMapper = mVerificationTaxMapper;
    }

    /**
     * Save a mVerificationTax.
     *
     * @param mVerificationTaxDTO the entity to save.
     * @return the persisted entity.
     */
    public MVerificationTaxDTO save(MVerificationTaxDTO mVerificationTaxDTO) {
        log.debug("Request to save MVerificationTax : {}", mVerificationTaxDTO);
        MVerificationTax mVerificationTax = mVerificationTaxMapper.toEntity(mVerificationTaxDTO);
        mVerificationTax = mVerificationTaxRepository.save(mVerificationTax);
        return mVerificationTaxMapper.toDto(mVerificationTax);
    }

    /**
     * Get all the mVerificationTaxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MVerificationTaxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MVerificationTaxes");
        return mVerificationTaxRepository.findAll(pageable)
            .map(mVerificationTaxMapper::toDto);
    }

    /**
     * Get one mVerificationTax by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MVerificationTaxDTO> findOne(Long id) {
        log.debug("Request to get MVerificationTax : {}", id);
        return mVerificationTaxRepository.findById(id)
            .map(mVerificationTaxMapper::toDto);
    }

    /**
     * Delete the mVerificationTax by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MVerificationTax : {}", id);
        mVerificationTaxRepository.deleteById(id);
    }
}
