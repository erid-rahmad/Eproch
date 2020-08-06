package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPersonInCharge;
import com.bhp.opusb.repository.CPersonInChargeRepository;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;
import com.bhp.opusb.service.mapper.CPersonInChargeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CPersonInCharge}.
 */
@Service
@Transactional
public class CPersonInChargeService {

    private final Logger log = LoggerFactory.getLogger(CPersonInChargeService.class);

    private final CPersonInChargeRepository cPersonInChargeRepository;

    private final CPersonInChargeMapper cPersonInChargeMapper;

    public CPersonInChargeService(CPersonInChargeRepository cPersonInChargeRepository, CPersonInChargeMapper cPersonInChargeMapper) {
        this.cPersonInChargeRepository = cPersonInChargeRepository;
        this.cPersonInChargeMapper = cPersonInChargeMapper;
    }

    /**
     * Save a cPersonInCharge.
     *
     * @param cPersonInChargeDTO the entity to save.
     * @return the persisted entity.
     */
    public CPersonInChargeDTO save(CPersonInChargeDTO cPersonInChargeDTO) {
        log.debug("Request to save CPersonInCharge : {}", cPersonInChargeDTO);
        CPersonInCharge cPersonInCharge = cPersonInChargeMapper.toEntity(cPersonInChargeDTO);
        cPersonInCharge = cPersonInChargeRepository.save(cPersonInCharge);
        return cPersonInChargeMapper.toDto(cPersonInCharge);
    }

    /**
     * Get all the cPersonInCharges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPersonInChargeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPersonInCharges");
        return cPersonInChargeRepository.findAll(pageable)
            .map(cPersonInChargeMapper::toDto);
    }

    /**
     * Get one cPersonInCharge by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPersonInChargeDTO> findOne(Long id) {
        log.debug("Request to get CPersonInCharge : {}", id);
        return cPersonInChargeRepository.findById(id)
            .map(cPersonInChargeMapper::toDto);
    }

    /**
     * Delete the cPersonInCharge by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPersonInCharge : {}", id);
        cPersonInChargeRepository.deleteById(id);
    }

    public void saveAll(List<CPersonInChargeDTO> cPersonInChargeDTO) {
        log.debug("Request to save CPersonInCharge : {}", cPersonInChargeDTO);
        List<CPersonInCharge> cPersonInCharge = cPersonInChargeMapper.toEntity(cPersonInChargeDTO);
        cPersonInCharge = cPersonInChargeRepository.saveAll(cPersonInCharge);
    }
    
}
