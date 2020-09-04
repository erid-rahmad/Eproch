package com.bhp.opusb.service;

import com.bhp.opusb.domain.CCalendar;
import com.bhp.opusb.repository.CCalendarRepository;
import com.bhp.opusb.service.dto.CCalendarDTO;
import com.bhp.opusb.service.mapper.CCalendarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCalendar}.
 */
@Service
@Transactional
public class CCalendarService {

    private final Logger log = LoggerFactory.getLogger(CCalendarService.class);

    private final CCalendarRepository cCalendarRepository;

    private final CCalendarMapper cCalendarMapper;

    public CCalendarService(CCalendarRepository cCalendarRepository, CCalendarMapper cCalendarMapper) {
        this.cCalendarRepository = cCalendarRepository;
        this.cCalendarMapper = cCalendarMapper;
    }

    /**
     * Save a cCalendar.
     *
     * @param cCalendarDTO the entity to save.
     * @return the persisted entity.
     */
    public CCalendarDTO save(CCalendarDTO cCalendarDTO) {
        log.debug("Request to save CCalendar : {}", cCalendarDTO);
        CCalendar cCalendar = cCalendarMapper.toEntity(cCalendarDTO);
        cCalendar = cCalendarRepository.save(cCalendar);
        return cCalendarMapper.toDto(cCalendar);
    }

    /**
     * Get all the cCalendars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CCalendarDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CCalendars");
        return cCalendarRepository.findAll(pageable)
            .map(cCalendarMapper::toDto);
    }

    /**
     * Get one cCalendar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CCalendarDTO> findOne(Long id) {
        log.debug("Request to get CCalendar : {}", id);
        return cCalendarRepository.findById(id)
            .map(cCalendarMapper::toDto);
    }

    /**
     * Delete the cCalendar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CCalendar : {}", id);
        cCalendarRepository.deleteById(id);
    }
}
