package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingScheduleAttachment;
import com.bhp.opusb.repository.MBiddingScheduleAttachmentRepository;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentDTO;
import com.bhp.opusb.service.mapper.MBiddingScheduleAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingScheduleAttachment}.
 */
@Service
@Transactional
public class MBiddingScheduleAttachmentService {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleAttachmentService.class);

    private final MBiddingScheduleAttachmentRepository mBiddingScheduleAttachmentRepository;

    private final MBiddingScheduleAttachmentMapper mBiddingScheduleAttachmentMapper;

    public MBiddingScheduleAttachmentService(MBiddingScheduleAttachmentRepository mBiddingScheduleAttachmentRepository, MBiddingScheduleAttachmentMapper mBiddingScheduleAttachmentMapper) {
        this.mBiddingScheduleAttachmentRepository = mBiddingScheduleAttachmentRepository;
        this.mBiddingScheduleAttachmentMapper = mBiddingScheduleAttachmentMapper;
    }

    /**
     * Save a mBiddingScheduleAttachment.
     *
     * @param mBiddingScheduleAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingScheduleAttachmentDTO save(MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO) {
        log.debug("Request to save MBiddingScheduleAttachment : {}", mBiddingScheduleAttachmentDTO);
        MBiddingScheduleAttachment mBiddingScheduleAttachment = mBiddingScheduleAttachmentMapper.toEntity(mBiddingScheduleAttachmentDTO);
        mBiddingScheduleAttachment = mBiddingScheduleAttachmentRepository.save(mBiddingScheduleAttachment);
        return mBiddingScheduleAttachmentMapper.toDto(mBiddingScheduleAttachment);
    }

    /**
     * Get all the mBiddingScheduleAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingScheduleAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingScheduleAttachments");
        return mBiddingScheduleAttachmentRepository.findAll(pageable)
            .map(mBiddingScheduleAttachmentMapper::toDto);
    }

    /**
     * Get one mBiddingScheduleAttachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingScheduleAttachmentDTO> findOne(Long id) {
        log.debug("Request to get MBiddingScheduleAttachment : {}", id);
        return mBiddingScheduleAttachmentRepository.findById(id)
            .map(mBiddingScheduleAttachmentMapper::toDto);
    }

    /**
     * Delete the mBiddingScheduleAttachment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingScheduleAttachment : {}", id);
        mBiddingScheduleAttachmentRepository.deleteById(id);
    }
}
