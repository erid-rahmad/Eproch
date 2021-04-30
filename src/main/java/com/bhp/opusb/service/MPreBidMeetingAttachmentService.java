package com.bhp.opusb.service;

import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.domain.MPreBidMeetingAttachment;
import com.bhp.opusb.repository.CAttachmentRepository;
import com.bhp.opusb.repository.MPreBidMeetingAttachmentRepository;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentDTO;
import com.bhp.opusb.service.mapper.MPreBidMeetingAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPreBidMeetingAttachment}.
 */
@Service
@Transactional
public class MPreBidMeetingAttachmentService {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingAttachmentService.class);

    private final MPreBidMeetingAttachmentRepository mPreBidMeetingAttachmentRepository;
    private final CAttachmentRepository cAttachmentRepository;

    private final MPreBidMeetingAttachmentMapper mPreBidMeetingAttachmentMapper;

    public MPreBidMeetingAttachmentService(MPreBidMeetingAttachmentRepository mPreBidMeetingAttachmentRepository,
            CAttachmentRepository cAttachmentRepository,
            MPreBidMeetingAttachmentMapper mPreBidMeetingAttachmentMapper) {
        this.mPreBidMeetingAttachmentRepository = mPreBidMeetingAttachmentRepository;
        this.cAttachmentRepository = cAttachmentRepository;
        this.mPreBidMeetingAttachmentMapper = mPreBidMeetingAttachmentMapper;
    }

    /**
     * Save a mPreBidMeetingAttachment.
     *
     * @param mPreBidMeetingAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.MPreBidMeeting.mPreBidMeetingAttachments", allEntries = true)
    public MPreBidMeetingAttachmentDTO save(MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO) {
        log.debug("Request to save MPreBidMeetingAttachment : {}", mPreBidMeetingAttachmentDTO);
        MPreBidMeetingAttachment mPreBidMeetingAttachment = mPreBidMeetingAttachmentMapper.toEntity(mPreBidMeetingAttachmentDTO);
        mPreBidMeetingAttachment = mPreBidMeetingAttachmentRepository.save(mPreBidMeetingAttachment);
        return mPreBidMeetingAttachmentMapper.toDto(mPreBidMeetingAttachment);
    }

    /**
     * Get all the mPreBidMeetingAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPreBidMeetingAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPreBidMeetingAttachments");
        return mPreBidMeetingAttachmentRepository.findAll(pageable)
            .map(mPreBidMeetingAttachmentMapper::toDto);
    }

    /**
     * Get one mPreBidMeetingAttachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPreBidMeetingAttachmentDTO> findOne(Long id) {
        log.debug("Request to get MPreBidMeetingAttachment : {}", id);
        return mPreBidMeetingAttachmentRepository.findById(id)
            .map(mPreBidMeetingAttachmentMapper::toDto);
    }

    /**
     * Delete the mPreBidMeetingAttachment by id.
     *
     * @param id the id of the entity.
     */
    @CacheEvict(cacheNames = "com.bhp.opusb.domain.MPreBidMeeting.mPreBidMeetingAttachments", allEntries = true)
    public void delete(Long id) {
        log.debug("Request to delete MPreBidMeetingAttachment : {}", id);
        mPreBidMeetingAttachmentRepository.findById(id)
            .ifPresent(record -> {
                CAttachment attachment = record.getCAttachment();
                mPreBidMeetingAttachmentRepository.delete(record);
                cAttachmentRepository.delete(attachment);
            });
    }
}
