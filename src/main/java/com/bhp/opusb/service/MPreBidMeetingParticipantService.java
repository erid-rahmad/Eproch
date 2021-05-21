package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;

import com.bhp.opusb.domain.MPreBidMeetingParticipant;
import com.bhp.opusb.repository.MPreBidMeetingParticipantRepository;
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

    private final MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository;

    private final MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper;

    public MPreBidMeetingParticipantService(MPreBidMeetingParticipantRepository mPreBidMeetingParticipantRepository, MPreBidMeetingParticipantMapper mPreBidMeetingParticipantMapper) {
        this.mPreBidMeetingParticipantRepository = mPreBidMeetingParticipantRepository;
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
        MPreBidMeetingParticipant mPreBidMeetingParticipant = mPreBidMeetingParticipantMapper.toEntity(mPreBidMeetingParticipantDTO);
        mPreBidMeetingParticipant = mPreBidMeetingParticipantRepository.save(mPreBidMeetingParticipant);
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
