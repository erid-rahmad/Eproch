package com.bhp.opusb.service;

import com.bhp.opusb.domain.CConvertionRate;
import com.bhp.opusb.repository.CConvertionRateRepository;
import com.bhp.opusb.service.dto.CConvertionRateDTO;
import com.bhp.opusb.service.mapper.CConvertionRateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CConvertionRate}.
 */
@Service
@Transactional
public class CConvertionRateService {

    private final Logger log = LoggerFactory.getLogger(CConvertionRateService.class);

    private final CConvertionRateRepository cConvertionRateRepository;

    private final CConvertionRateMapper cConvertionRateMapper;

    public CConvertionRateService(CConvertionRateRepository cConvertionRateRepository, CConvertionRateMapper cConvertionRateMapper) {
        this.cConvertionRateRepository = cConvertionRateRepository;
        this.cConvertionRateMapper = cConvertionRateMapper;
    }

    /**
     * Save a cConvertionRate.
     *
     * @param cConvertionRateDTO the entity to save.
     * @return the persisted entity.
     */
    public CConvertionRateDTO save(CConvertionRateDTO cConvertionRateDTO) {
        log.debug("Request to save CConvertionRate : {}", cConvertionRateDTO);
        CConvertionRate cConvertionRate = cConvertionRateMapper.toEntity(cConvertionRateDTO);
        cConvertionRate = cConvertionRateRepository.save(cConvertionRate);
        return cConvertionRateMapper.toDto(cConvertionRate);
    }

    /**
     * Get all the cConvertionRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CConvertionRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CConvertionRates");
        return cConvertionRateRepository.findAll(pageable)
            .map(cConvertionRateMapper::toDto);
    }

    /**
     * Get one cConvertionRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CConvertionRateDTO> findOne(Long id) {
        log.debug("Request to get CConvertionRate : {}", id);
        return cConvertionRateRepository.findById(id)
            .map(cConvertionRateMapper::toDto);
    }

    /**
     * Delete the cConvertionRate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CConvertionRate : {}", id);
        cConvertionRateRepository.deleteById(id);
    }
}
