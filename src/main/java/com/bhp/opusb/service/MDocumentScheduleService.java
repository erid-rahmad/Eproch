package com.bhp.opusb.service;

import com.bhp.opusb.domain.MDocumentSchedule;
import com.bhp.opusb.repository.MDocumentScheduleRepository;
import com.bhp.opusb.service.dto.MDocumentScheduleDTO;
import com.bhp.opusb.service.mapper.MDocumentScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MDocumentSchedule}.
 */
@Service
@Transactional
public class MDocumentScheduleService {

    private final Logger log = LoggerFactory.getLogger(MDocumentScheduleService.class);

    private final MDocumentScheduleRepository mDocumentScheduleRepository;

    private final MDocumentScheduleMapper mDocumentScheduleMapper;

    public MDocumentScheduleService(MDocumentScheduleRepository mDocumentScheduleRepository, MDocumentScheduleMapper mDocumentScheduleMapper) {
        this.mDocumentScheduleRepository = mDocumentScheduleRepository;
        this.mDocumentScheduleMapper = mDocumentScheduleMapper;
    }

    /**
     * Save a mDocumentSchedule.
     *
     * @param mDocumentScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public MDocumentScheduleDTO save(MDocumentScheduleDTO mDocumentScheduleDTO) {
        log.debug("Request to save MDocumentSchedule : {}", mDocumentScheduleDTO);
        MDocumentSchedule mDocumentSchedule = mDocumentScheduleMapper.toEntity(mDocumentScheduleDTO);
        mDocumentSchedule = mDocumentScheduleRepository.save(mDocumentSchedule);
        return mDocumentScheduleMapper.toDto(mDocumentSchedule);
    }

    /**
     * Get all the mDocumentSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MDocumentScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MDocumentSchedules");
        return mDocumentScheduleRepository.findAll(pageable)
            .map(mDocumentScheduleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MDocumentSchedule> findbyidbidskjl(Long id) {
        return mDocumentScheduleRepository.findbyidbidbidskejul(id);
    }

    /**
     * Get one mDocumentSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MDocumentScheduleDTO> findOne(Long id) {
        log.debug("Request to get MDocumentSchedule : {}", id);
        return mDocumentScheduleRepository.findById(id)
            .map(mDocumentScheduleMapper::toDto);
    }


    /**
     * Delete the mDocumentSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MDocumentSchedule : {}", id);
        mDocumentScheduleRepository.deleteById(id);
    }
}
