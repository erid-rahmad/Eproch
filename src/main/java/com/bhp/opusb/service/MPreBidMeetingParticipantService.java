package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingSchedule;
import com.bhp.opusb.domain.MPreBidMeetingParticipant;
import com.bhp.opusb.repository.MPreBidMeetingParticipantRepository;
import com.bhp.opusb.repository.MPreBidMeetingRepository;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingParticipantMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MPreBidMeetingParticipant}.
 */
@Service
@Transactional
public class MPreBidMeetingParticipantService {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingParticipantService.class);

    private final MPreBidMeetingRepository mPreBidMeetingRepository;

    private final MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository;

    private final MBiddingSubmissionService mBiddingSubmissionService;

    private final MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper;

    public MPreBidMeetingParticipantService(MPreBidMeetingRepository mPreBidMeetingRepository,
            MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository,
            MBiddingSubmissionService mBiddingSubmissionService,
            MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper) {
        this.mPreBidMeetingRepository = mPreBidMeetingRepository;
        this.mPreBidMeetingParticipantRepository = mPreBidMeetingParticipantRepository;
        this.mBiddingSubmissionService = mBiddingSubmissionService;
        this.mPreBidMeetingParticipantMapper = mPreBidMeetingParticipantMapper;
    }

    /**
     * Save a mPreBidMeetingParticipant.
     *
     * @param mPreBidMeetingParticipantDTO the entity to save.
     * @return the persisted entity.
     */
    public MPreBidMeetingParticipantDTO save(MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO) {
        log.debug("Request to save MPreBidMeetingParticipant : {}", mPreBidMeetingParticipantDTO);
        MPreBidMeetingParticipant mPreBidMeetingParticipant = mPreBidMeetingParticipantMapper
                .toEntity(mPreBidMeetingParticipantDTO);
        mPreBidMeetingParticipant = mPreBidMeetingParticipantRepository.save(mPreBidMeetingParticipant);
        final CVendor cVendor = mPreBidMeetingParticipant.getVendor();

        if (mPreBidMeetingParticipant.isAttended()) {
            mPreBidMeetingRepository.findById(mPreBidMeetingParticipant.getPreBidMeeting().getId())
                .ifPresent(preBidMeeting -> {
                    final MBiddingSchedule biddingSchedule = preBidMeeting.getBiddingSchedule();
                    final Integer currentEventSequence = biddingSchedule.getEventTypeLine().getSequence();
                    final MBidding mBidding = biddingSchedule.getBidding();
                    mBiddingSubmissionService.createIfNotExists(mBidding, cVendor, currentEventSequence);
                });
        }
        return mPreBidMeetingParticipantMapper.toDto(mPreBidMeetingParticipant);
    }

    public List<MPreBidMeetingParticipantDTO> saveAll(List<MPreBidMeetingParticipantDTO> mPreBidMeetingParticipantDTOs) {
        List<MPreBidMeetingParticipant> attendees = mPreBidMeetingParticipantMapper.toEntity(mPreBidMeetingParticipantDTOs);
        return mPreBidMeetingParticipantMapper.toDto(mPreBidMeetingParticipantRepository.saveAll(attendees));
    }

    /**
     * Get all the mPreBidMeetingParticipants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreBidMeetingParticipantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPreBidMeetingParticipants");
        return mPreBidMeetingParticipantRepository.findAll(pageable)
            .map(mPreBidMeetingParticipantMapper::toDto);
    }

    /**
     * Get one mPreBidMeetingParticipant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPreBidMeetingParticipantDTO> findOne(Long id) {
        log.debug("Request to get MPreBidMeetingParticipant : {}", id);
        return mPreBidMeetingParticipantRepository.findById(id)
            .map(mPreBidMeetingParticipantMapper::toDto);
    }

    /**
     * Delete the mPreBidMeetingParticipant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPreBidMeetingParticipant : {}", id);
        mPreBidMeetingParticipantRepository.deleteById(id);
    }

    /**
     * Delete the mPreBidMeetingParticipants.
     *
     * @param ids the ids of the entities.
     */
    public void deleteByVendorIds(Long preBidMeetingId, List<Long> vendorIds) {
        log.debug("Request to delete MPreBidMeetingParticipants : {} of preBidMeeting : {}", vendorIds, preBidMeetingId);
        mPreBidMeetingParticipantRepository.deleteByVendorIds(preBidMeetingId, vendorIds);
    }
}
