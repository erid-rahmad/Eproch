package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.repository.MBiddingScheduleRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingSchedule}.
 */
@Service
@Transactional
public class MBiddingScheduleService {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleService.class);

    private final MBiddingScheduleRepository mBiddingScheduleRepository;

    private final MBiddingScheduleMapper mBiddingScheduleMapper;

    public MBiddingScheduleService(MBiddingScheduleRepository mBiddingScheduleRepository, MBiddingScheduleMapper mBiddingScheduleMapper) {
        this.mBiddingScheduleRepository = mBiddingScheduleRepository;
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
     * Save all mBiddingSchedules.
     *
     * @param mBiddingScheduleDTOs the entity to save.
     * @return the persisted entities.
     */
    public List<MBiddingScheduleDTO> saveAll(List<MBiddingScheduleDTO> mBiddingScheduleDTOs, MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MBiddingSchedules. size : {}", mBiddingScheduleDTOs.size());

        mBiddingScheduleDTOs.forEach(line -> {
            line.setBiddingId(mBiddingDTO.getId());
            line.setAdOrganizationId(mBiddingDTO.getAdOrganizationId());
        });

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
