package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.repository.MBiddingScheduleRepository;
import com.bhp.opusb.repository.MDocumentScheduleRepository;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    MDocumentScheduleService mDocumentScheduleService;

    @Autowired
    MDocumentScheduleRepository mDocumentScheduleRepository;

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
        mDocumentScheduleRepository.saveAll(mBiddingScheduleDTO.getmDocumentScheduleList());
        return mBiddingScheduleMapper.toDto(mBiddingSchedule);
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
        Optional<MBiddingScheduleDTO> mBiddingScheduleDTO = mBiddingScheduleRepository.findById(id)
            .map(mBiddingScheduleMapper::toDto);
        mBiddingScheduleDTO.get().setmDocumentScheduleList(mDocumentScheduleService.findbyidbidskjl(mBiddingScheduleDTO.get().getId()));
        return mBiddingScheduleDTO;
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
}
