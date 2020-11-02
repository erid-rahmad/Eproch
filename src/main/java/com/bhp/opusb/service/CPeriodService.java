package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPeriod;
import com.bhp.opusb.repository.CPeriodRepository;
import com.bhp.opusb.service.dto.CPeriodDTO;
import com.bhp.opusb.service.mapper.CPeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPeriod}.
 */
@Service
@Transactional
public class CPeriodService {

    private final Logger log = LoggerFactory.getLogger(CPeriodService.class);

    private final CPeriodRepository cPeriodRepository;

    private final CPeriodMapper cPeriodMapper;

    public CPeriodService(CPeriodRepository cPeriodRepository, CPeriodMapper cPeriodMapper) {
        this.cPeriodRepository = cPeriodRepository;
        this.cPeriodMapper = cPeriodMapper;
    }

    /**
     * Save a cPeriod.
     *
     * @param cPeriodDTO the entity to save.
     * @return the persisted entity.
     */
    public CPeriodDTO save(CPeriodDTO cPeriodDTO) {
        log.debug("Request to save CPeriod : {}", cPeriodDTO);
        CPeriod cPeriod = cPeriodMapper.toEntity(cPeriodDTO);
        cPeriod = cPeriodRepository.save(cPeriod);
        return cPeriodMapper.toDto(cPeriod);
    }

    /**
     * Get all the cPeriods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPeriods");
        return cPeriodRepository.findAll(pageable)
            .map(cPeriodMapper::toDto);
    }

    /**
     * Get one cPeriod by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPeriodDTO> findOne(Long id) {
        log.debug("Request to get CPeriod : {}", id);
        return cPeriodRepository.findById(id)
            .map(cPeriodMapper::toDto);
    }

    /**
     * Delete the cPeriod by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPeriod : {}", id);
        cPeriodRepository.deleteById(id);
    }
}
