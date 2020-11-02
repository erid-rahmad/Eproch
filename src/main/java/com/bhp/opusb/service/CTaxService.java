package com.bhp.opusb.service;

import com.bhp.opusb.domain.CTax;
import com.bhp.opusb.repository.CTaxRepository;
import com.bhp.opusb.service.dto.CTaxDTO;
import com.bhp.opusb.service.mapper.CTaxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CTax}.
 */
@Service
@Transactional
public class CTaxService {

    private final Logger log = LoggerFactory.getLogger(CTaxService.class);

    private final CTaxRepository cTaxRepository;

    private final CTaxMapper cTaxMapper;

    public CTaxService(CTaxRepository cTaxRepository, CTaxMapper cTaxMapper) {
        this.cTaxRepository = cTaxRepository;
        this.cTaxMapper = cTaxMapper;
    }

    /**
     * Save a cTax.
     *
     * @param cTaxDTO the entity to save.
     * @return the persisted entity.
     */
    public CTaxDTO save(CTaxDTO cTaxDTO) {
        log.debug("Request to save CTax : {}", cTaxDTO);
        CTax cTax = cTaxMapper.toEntity(cTaxDTO);
        cTax = cTaxRepository.save(cTax);
        return cTaxMapper.toDto(cTax);
    }

    /**
     * Get all the cTaxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CTaxes");
        return cTaxRepository.findAll(pageable)
            .map(cTaxMapper::toDto);
    }

    /**
     * Get one cTax by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CTaxDTO> findOne(Long id) {
        log.debug("Request to get CTax : {}", id);
        return cTaxRepository.findById(id)
            .map(cTaxMapper::toDto);
    }

    /**
     * Delete the cTax by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CTax : {}", id);
        cTaxRepository.deleteById(id);
    }
}
