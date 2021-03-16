package com.bhp.opusb.service;

import com.bhp.opusb.domain.CPaymentSchedule;
import com.bhp.opusb.repository.CPaymentScheduleRepository;
import com.bhp.opusb.service.dto.CPaymentScheduleDTO;
import com.bhp.opusb.service.mapper.CPaymentScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CPaymentSchedule}.
 */
@Service
@Transactional
public class CPaymentScheduleService {

    private final Logger log = LoggerFactory.getLogger(CPaymentScheduleService.class);

    private final CPaymentScheduleRepository cPaymentScheduleRepository;

    private final CPaymentScheduleMapper cPaymentScheduleMapper;

    public CPaymentScheduleService(CPaymentScheduleRepository cPaymentScheduleRepository, CPaymentScheduleMapper cPaymentScheduleMapper) {
        this.cPaymentScheduleRepository = cPaymentScheduleRepository;
        this.cPaymentScheduleMapper = cPaymentScheduleMapper;
    }

    /**
     * Save a cPaymentSchedule.
     *
     * @param cPaymentScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public CPaymentScheduleDTO save(CPaymentScheduleDTO cPaymentScheduleDTO) {
        log.debug("Request to save CPaymentSchedule : {}", cPaymentScheduleDTO);
        CPaymentSchedule cPaymentSchedule = cPaymentScheduleMapper.toEntity(cPaymentScheduleDTO);
        cPaymentSchedule = cPaymentScheduleRepository.save(cPaymentSchedule);
        return cPaymentScheduleMapper.toDto(cPaymentSchedule);
    }

    /**
     * Get all the cPaymentSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CPaymentScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CPaymentSchedules");
        return cPaymentScheduleRepository.findAll(pageable)
            .map(cPaymentScheduleMapper::toDto);
    }

    /**
     * Get one cPaymentSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CPaymentScheduleDTO> findOne(Long id) {
        log.debug("Request to get CPaymentSchedule : {}", id);
        return cPaymentScheduleRepository.findById(id)
            .map(cPaymentScheduleMapper::toDto);
    }

    /**
     * Delete the cPaymentSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CPaymentSchedule : {}", id);
        cPaymentScheduleRepository.deleteById(id);
    }
}
