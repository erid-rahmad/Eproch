package com.bhp.opusb.service;

import com.bhp.opusb.domain.CTaxRate;
import com.bhp.opusb.repository.CTaxRateRepository;
import com.bhp.opusb.service.dto.CTaxRateDTO;
import com.bhp.opusb.service.mapper.CTaxRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CTaxRate}.
 */
@Service
@Transactional
public class CTaxRateService {

    private final Logger log = LoggerFactory.getLogger(CTaxRateService.class);

    private final CTaxRateRepository cTaxRateRepository;

    private final CTaxRateMapper cTaxRateMapper;

    public CTaxRateService(CTaxRateRepository cTaxRateRepository, CTaxRateMapper cTaxRateMapper) {
        this.cTaxRateRepository = cTaxRateRepository;
        this.cTaxRateMapper = cTaxRateMapper;
    }

    /**
     * Save a cTaxRate.
     *
     * @param cTaxRateDTO the entity to save.
     * @return the persisted entity.
     */
    public CTaxRateDTO save(CTaxRateDTO cTaxRateDTO) {
        log.debug("Request to save CTaxRate : {}", cTaxRateDTO);
        CTaxRate cTaxRate = cTaxRateMapper.toEntity(cTaxRateDTO);
        cTaxRate = cTaxRateRepository.save(cTaxRate);
        return cTaxRateMapper.toDto(cTaxRate);
    }

    /**
     * Get all the cTaxRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CTaxRates");
        return cTaxRateRepository.findAll(pageable)
            .map(cTaxRateMapper::toDto);
    }

    /**
     * Get one cTaxRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CTaxRateDTO> findOne(Long id) {
        log.debug("Request to get CTaxRate : {}", id);
        return cTaxRateRepository.findById(id)
            .map(cTaxRateMapper::toDto);
    }

    /**
     * Delete the cTaxRate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CTaxRate : {}", id);
        cTaxRateRepository.deleteById(id);
    }
}
