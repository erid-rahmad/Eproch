package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationEvent;
import com.bhp.opusb.domain.MPrequalificationSchedule;
import com.bhp.opusb.repository.MPrequalificationScheduleRepository;
import com.bhp.opusb.service.dto.CPrequalificationEventLineDTO;
import com.bhp.opusb.service.dto.MPrequalificationScheduleCriteria;
import com.bhp.opusb.service.dto.MPrequalificationScheduleDTO;
import com.bhp.opusb.service.mapper.CPrequalificationEventLineMapper;
import com.bhp.opusb.service.mapper.MPrequalificationScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.filter.LongFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MPrequalificationSchedule}.
 */
@Service
@Transactional
public class MPrequalificationScheduleService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationScheduleService.class);

    private final CPrequalificationEventLineService cPrequalificationEventLineService;

    private final MPrequalificationScheduleQueryService mPrequalificationScheduleQueryService;
    private final MPrequalificationScheduleRepository mPrequalificationScheduleRepository;

    private final MPrequalificationScheduleMapper mPrequalificationScheduleMapper;
    private final CPrequalificationEventLineMapper cPrequalificationEventLineMapper;

    public MPrequalificationScheduleService(MPrequalificationScheduleRepository mPrequalificationScheduleRepository, 
        MPrequalificationScheduleMapper mPrequalificationScheduleMapper, 
        CPrequalificationEventLineService cPrequalificationEventLineService,
        MPrequalificationScheduleQueryService mPrequalificationScheduleQueryService,
        CPrequalificationEventLineMapper cPrequalificationEventLineMapper) {
        this.mPrequalificationScheduleRepository = mPrequalificationScheduleRepository;
        this.mPrequalificationScheduleMapper = mPrequalificationScheduleMapper;
        this.cPrequalificationEventLineService = cPrequalificationEventLineService;
        this.mPrequalificationScheduleQueryService = mPrequalificationScheduleQueryService;
        this.cPrequalificationEventLineMapper = cPrequalificationEventLineMapper;
    }

    /**
     * Save a mPrequalificationSchedule.
     *
     * @param mPrequalificationScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationScheduleDTO save(MPrequalificationScheduleDTO mPrequalificationScheduleDTO) {
        log.debug("Request to save MPrequalificationSchedule : {}", mPrequalificationScheduleDTO);
        MPrequalificationSchedule mPrequalificationSchedule = mPrequalificationScheduleMapper.toEntity(mPrequalificationScheduleDTO);
        mPrequalificationSchedule = mPrequalificationScheduleRepository.save(mPrequalificationSchedule);
        return mPrequalificationScheduleMapper.toDto(mPrequalificationSchedule);
    }

    /**
     * Save all mPrequalificationScheduleDTOs.
     *
     * @param mPrequalificationScheduleDTOs the entity to save.
     * @return the persisted entities.
     */
    public List<MPrequalificationScheduleDTO> saveAll(List<MPrequalificationScheduleDTO> mPrequalificationScheduleDTO) {
        log.debug("Request to save MBiddingSchedules. size : {}", mPrequalificationScheduleDTO.size());

        List<MPrequalificationSchedule> mBiddingSchedules = mPrequalificationScheduleMapper.toEntity(mPrequalificationScheduleDTO);
        mBiddingSchedules = mPrequalificationScheduleRepository.saveAll(mBiddingSchedules);

        return mPrequalificationScheduleMapper.toDto(mBiddingSchedules);
    }

    /**
     * Get all the mPrequalificationSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationSchedules");
        return mPrequalificationScheduleRepository.findAll(pageable)
            .map(mPrequalificationScheduleMapper::toDto);
    }

    /**
     * Get one mPrequalificationSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationScheduleDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationSchedule : {}", id);
        return mPrequalificationScheduleRepository.findById(id)
            .map(mPrequalificationScheduleMapper::toDto);
    }

    /**
     * Delete the mPrequalificationSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationSchedule : {}", id);
        mPrequalificationScheduleRepository.deleteById(id);
    }

    /**
     * Initialize prequalification schedules for the first time, or if the event type has been changed.
     * @param mPrequalification
     */
    public void initSchedules(MPrequalificationEvent mPreqEvent) {
        MPrequalificationScheduleCriteria scheduleCriteria = new MPrequalificationScheduleCriteria();
        LongFilter preqIdFilter = new LongFilter();
        preqIdFilter.setEquals(mPreqEvent.getPrequalification().getId());
        scheduleCriteria.setPrequalificationId(preqIdFilter);

        long count = mPrequalificationScheduleQueryService.countByCriteria(scheduleCriteria);
        
        List<CPrequalificationEventLineDTO> cPrequalificationEventLine = cPrequalificationEventLineService
                .findByEventTypeId(mPreqEvent.getEvent().getId());

        if (count == 0) {
            // Create new bidding schedules.
            List<MPrequalificationSchedule> schedules = cPrequalificationEventLine.stream()
                .map(line -> new MPrequalificationSchedule()
                    .active(true)
                    .adOrganization(mPreqEvent.getAdOrganization())
                    .prequalification(mPreqEvent.getPrequalification())
                    .eventLine(cPrequalificationEventLineMapper.toEntity(line)))
                .collect(Collectors.toList());

            mPrequalificationScheduleRepository.saveAll(schedules);
        } else {
            // Delete unrelated schedules.
            final List<Long> lineIds = cPrequalificationEventLine.stream()
                .map(CPrequalificationEventLineDTO::getId)
                .collect(Collectors.toList());

            mPrequalificationScheduleRepository.deleteByEventTypeLineIdNotIn(lineIds, mPreqEvent.getPrequalification().getId());

            // post deletion: fetch remaining existing schedules
            List<MPrequalificationScheduleDTO> getCurrentList = mPrequalificationScheduleQueryService.findByCriteria(scheduleCriteria);
            List<Long> currentListLines = getCurrentList.stream().map(MPrequalificationScheduleDTO::getEventLineId).collect(Collectors.toList());

            // filter the missing event lines, create and save them
            List<CPrequalificationEventLineDTO> nonExistant = cPrequalificationEventLine.stream()
                .filter(elem -> !currentListLines.contains(elem.getId())).collect(Collectors.toList());

            List<MPrequalificationSchedule> nonExistantSchedules = nonExistant.stream()
                .map(line -> new MPrequalificationSchedule()
                    .active(true)
                    .adOrganization(mPreqEvent.getAdOrganization())
                    .prequalification(mPreqEvent.getPrequalification())
                    .eventLine(cPrequalificationEventLineMapper.toEntity(line)))
                .collect(Collectors.toList());

            mPrequalificationScheduleRepository.saveAll(nonExistantSchedules);
        }
    }
}
