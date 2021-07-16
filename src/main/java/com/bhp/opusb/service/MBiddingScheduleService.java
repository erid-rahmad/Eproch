package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.repository.MBiddingScheduleRepository;
import com.bhp.opusb.service.dto.CEventTypelineDTO;
import com.bhp.opusb.service.dto.MBiddingScheduleCriteria;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.mapper.CEventTypelineMapper;
import com.bhp.opusb.service.mapper.MBiddingScheduleMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.filter.LongFilter;

/**
 * Service Implementation for managing {@link MBiddingSchedule}.
 */
@Service
@Transactional
public class MBiddingScheduleService {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleService.class);

    private final CEventTypelineService cEventTypelineService;
    private final MBiddingScheduleQueryService mBiddingScheduleQueryService;
    private final MBiddingScheduleRepository mBiddingScheduleRepository;

    private final CEventTypelineMapper cEventTypelineMapper;
    private final MBiddingScheduleMapper mBiddingScheduleMapper;

    public MBiddingScheduleService(CEventTypelineService cEventTypelineService,
            MBiddingScheduleQueryService mBiddingScheduleQueryService,
            MBiddingScheduleRepository mBiddingScheduleRepository, CEventTypelineMapper cEventTypelineMapper,
            MBiddingScheduleMapper mBiddingScheduleMapper) {
        this.cEventTypelineService = cEventTypelineService;
        this.mBiddingScheduleQueryService = mBiddingScheduleQueryService;
        this.mBiddingScheduleRepository = mBiddingScheduleRepository;
        this.cEventTypelineMapper = cEventTypelineMapper;
        this.mBiddingScheduleMapper = mBiddingScheduleMapper;
    }

    /**
     * Save a mBiddingSchedule.
     *
     * @param mBiddingScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingScheduleDTO save(MBiddingScheduleDTO mBiddingScheduleDTO) {
        log.debug("Request to save MBiddingSchedule : {}", mBiddingScheduleDTO);
        MBiddingSchedule mBiddingSchedule = mBiddingScheduleMapper.toEntity(mBiddingScheduleDTO);
        mBiddingSchedule = mBiddingScheduleRepository.save(mBiddingSchedule);
        return mBiddingScheduleMapper.toDto(mBiddingSchedule);
    }

    /**
     * Initialize bidding schedules for the first time, or if the event type has been changed.
     * @param mBidding
     */
    public void initBiddingSchedules(MBidding mBidding) {
        MBiddingScheduleCriteria scheduleCriteria = new MBiddingScheduleCriteria();
        LongFilter biddingIdFilter = new LongFilter();
        biddingIdFilter.setEquals(mBidding.getId());
        scheduleCriteria.setBiddingId(biddingIdFilter);

        long count = mBiddingScheduleQueryService.countByCriteria(scheduleCriteria);
        List<CEventTypelineDTO> cEventTypelineDTOs = cEventTypelineService
                .findByEventTypeId(mBidding.getEventType().getId());

        if (count == 0) {
            // Create new bidding schedules.
            List<MBiddingSchedule> schedules = cEventTypelineDTOs.stream()
                .map(line -> new MBiddingSchedule()
                    .active(true)
                    .adOrganization(mBidding.getAdOrganization())
                    .bidding(mBidding)
                    .eventTypeLine(cEventTypelineMapper.toEntity(line)))
                .collect(Collectors.toList());

            mBiddingScheduleRepository.saveAll(schedules);
        } else {
            // Delete unrelated schedules.
            final List<Long> lineIds = cEventTypelineDTOs.stream()
                .map(CEventTypelineDTO::getId)
                .collect(Collectors.toList());

            mBiddingScheduleRepository.deleteByEventTypeLineIdNotIn(lineIds, mBidding.getId());

            // post deletion: fetch remaining existing schedules
            List<MBiddingScheduleDTO> getCurrentList = mBiddingScheduleQueryService.findByCriteria(scheduleCriteria);
            List<Long> currentListLines = getCurrentList.stream().map(MBiddingScheduleDTO::getEventTypeLineId).collect(Collectors.toList());

            // filter the missing event lines, create and save them
            List<CEventTypelineDTO> nonExistant = cEventTypelineDTOs.stream()
                .filter(elem -> !currentListLines.contains(elem.getId())).collect(Collectors.toList());

            List<MBiddingSchedule> nonExistantSchedules = nonExistant.stream()
                .map(line -> new MBiddingSchedule()
                    .active(true)
                    .adOrganization(mBidding.getAdOrganization())
                    .bidding(mBidding)
                    .eventTypeLine(cEventTypelineMapper.toEntity(line)))
                .collect(Collectors.toList());

            mBiddingScheduleRepository.saveAll(nonExistantSchedules);
        }
    }

    /**
     * Save all mBiddingSchedules.
     *
     * @param mBiddingScheduleDTOs the entity to save.
     * @return the persisted entities.
     */
    public List<MBiddingScheduleDTO> saveAll(List<MBiddingScheduleDTO> mBiddingScheduleDTOs) {
        log.debug("Request to save MBiddingSchedules. size : {}", mBiddingScheduleDTOs.size());

        List<MBiddingSchedule> mBiddingSchedules = mBiddingScheduleMapper.toEntity(mBiddingScheduleDTOs);
        mBiddingSchedules = mBiddingScheduleRepository.saveAll(mBiddingSchedules);

        return mBiddingScheduleMapper.toDto(mBiddingSchedules);
    }

    /**
     * Get all the mBiddingSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSchedules");
        return mBiddingScheduleRepository.findAll(pageable)
            .map(mBiddingScheduleMapper::toDto);
    }

    /**
     * Get one mBiddingSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingScheduleDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSchedule : {}", id);
        return mBiddingScheduleRepository.findById(id)
            .map(mBiddingScheduleMapper::toDto);
    }

    /**
     * Delete the mBiddingSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSchedule : {}", id);
        mBiddingScheduleRepository.deleteById(id);
    }

    /**
     * Delete the list ofmBiddingSchedules.
     *
     * @param id the id of the entity.
     */
    public void deleteAll(List<MBiddingScheduleDTO> mBiddingScheduleDTOs) {
        log.debug("Request to delete MBiddingSchedule. count : {}", mBiddingScheduleDTOs.size());
        mBiddingScheduleRepository.deleteAll(mBiddingScheduleMapper.toEntity(mBiddingScheduleDTOs));
    }
}
