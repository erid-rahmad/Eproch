package com.bhp.opusb.service;

import com.bhp.opusb.domain.CNonBusinessDay;
import com.bhp.opusb.repository.CNonBusinessDayRepository;
import com.bhp.opusb.service.dto.CNonBusinessDayDTO;
import com.bhp.opusb.service.mapper.CNonBusinessDayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CNonBusinessDay}.
 */
@Service
@Transactional
public class CNonBusinessDayService {

    private final Logger log = LoggerFactory.getLogger(CNonBusinessDayService.class);

    private final CNonBusinessDayRepository cNonBusinessDayRepository;

    private final CNonBusinessDayMapper cNonBusinessDayMapper;

    public CNonBusinessDayService(CNonBusinessDayRepository cNonBusinessDayRepository, CNonBusinessDayMapper cNonBusinessDayMapper) {
        this.cNonBusinessDayRepository = cNonBusinessDayRepository;
        this.cNonBusinessDayMapper = cNonBusinessDayMapper;
    }

    /**
     * Save a cNonBusinessDay.
     *
     * @param cNonBusinessDayDTO the entity to save.
     * @return the persisted entity.
     */
    public CNonBusinessDayDTO save(CNonBusinessDayDTO cNonBusinessDayDTO) {
        log.debug("Request to save CNonBusinessDay : {}", cNonBusinessDayDTO);
        CNonBusinessDay cNonBusinessDay = cNonBusinessDayMapper.toEntity(cNonBusinessDayDTO);
        cNonBusinessDay = cNonBusinessDayRepository.save(cNonBusinessDay);
        return cNonBusinessDayMapper.toDto(cNonBusinessDay);
    }

    /**
     * Get all the cNonBusinessDays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CNonBusinessDayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CNonBusinessDays");
        return cNonBusinessDayRepository.findAll(pageable)
            .map(cNonBusinessDayMapper::toDto);
    }

    /**
     * Get one cNonBusinessDay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CNonBusinessDayDTO> findOne(Long id) {
        log.debug("Request to get CNonBusinessDay : {}", id);
        return cNonBusinessDayRepository.findById(id)
            .map(cNonBusinessDayMapper::toDto);
    }

    /**
     * Delete the cNonBusinessDay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CNonBusinessDay : {}", id);
        cNonBusinessDayRepository.deleteById(id);
    }
}
