package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPreBidMeeting;
import com.bhp.opusb.repository.MPreBidMeetingRepository;
import com.bhp.opusb.service.dto.MPreBidMeetingDTO;
import com.bhp.opusb.service.dto.MPreBidMeetingVM;
import com.bhp.opusb.service.mapper.MPreBidMeetingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPreBidMeeting}.
 */
@Service
@Transactional
public class MPreBidMeetingService {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingService.class);

    private final MPreBidMeetingParticipantService mPreBidMeetingParticipantService;

    private final MPreBidMeetingRepository mPreBidMeetingRepository;

    private final MPreBidMeetingMapper mPreBidMeetingMapper;

    public MPreBidMeetingService(MPreBidMeetingParticipantService mPreBidMeetingParticipantService,
            MPreBidMeetingRepository mPreBidMeetingRepository, MPreBidMeetingMapper mPreBidMeetingMapper) {
        this.mPreBidMeetingParticipantService = mPreBidMeetingParticipantService;
        this.mPreBidMeetingRepository = mPreBidMeetingRepository;
        this.mPreBidMeetingMapper = mPreBidMeetingMapper;
    }

    /**
     * Save a mPreBidMeeting.
     *
     * @param mPreBidMeetingDTO the entity to save.
     * @return the persisted entity.
     */
    public MPreBidMeetingDTO save(MPreBidMeetingDTO mPreBidMeetingDTO) {
        log.debug("Request to save MPreBidMeeting : {}", mPreBidMeetingDTO);
        MPreBidMeeting mPreBidMeeting = mPreBidMeetingMapper.toEntity(mPreBidMeetingDTO);
        mPreBidMeeting = mPreBidMeetingRepository.save(mPreBidMeeting);
        return mPreBidMeetingMapper.toDto(mPreBidMeeting);
    }

    /**
     * Save a mPreBidMeetingVM.
     *
     * @param mPreBidMeetingVM the entity to save.
     * @return the persisted entity.
     */
    public MPreBidMeetingVM updateAttendees(MPreBidMeetingVM mPreBidMeetingVM) {
        log.debug("Request to update attendees of MPreBidMeeting : {}", mPreBidMeetingVM);
        mPreBidMeetingParticipantService.saveAll(mPreBidMeetingVM.getAddedAttendees());
        mPreBidMeetingParticipantService.deleteByVendorIds(mPreBidMeetingVM.getId(), mPreBidMeetingVM.getRemovedAttendees());
        return mPreBidMeetingVM;
    }

    /**
     * Get all the mPreBidMeetings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreBidMeetingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPreBidMeetings");
        return mPreBidMeetingRepository.findAll(pageable)
            .map(mPreBidMeetingMapper::toDto);
    }

    /**
     * Get one mPreBidMeeting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPreBidMeetingDTO> findOne(Long id) {
        log.debug("Request to get MPreBidMeeting : {}", id);
        return mPreBidMeetingRepository.findById(id)
            .map(mPreBidMeetingMapper::toDto);
    }

    /**
     * Delete the mPreBidMeeting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPreBidMeeting : {}", id);
        mPreBidMeetingRepository.deleteById(id);
    }
}
