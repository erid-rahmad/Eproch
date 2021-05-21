package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingSubmission}.
 */
@Service
@Transactional
public class MBiddingSubmissionService {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionService.class);

    private final MBiddingSubmissionRepository mBiddingSubmissionRepository;

    private final MBiddingSubmissionMapper mBiddingSubmissionMapper;

    public MBiddingSubmissionService(MBiddingSubmissionRepository mBiddingSubmissionRepository, MBiddingSubmissionMapper mBiddingSubmissionMapper) {
        this.mBiddingSubmissionRepository = mBiddingSubmissionRepository;
        this.mBiddingSubmissionMapper = mBiddingSubmissionMapper;
    }

    /**
     * Save a mBiddingSubmission.
     *
     * @param mBiddingSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingSubmissionDTO save(MBiddingSubmissionDTO mBiddingSubmissionDTO) {
        log.debug("Request to save MBiddingSubmission : {}", mBiddingSubmissionDTO);
        MBiddingSubmission mBiddingSubmission = mBiddingSubmissionMapper.toEntity(mBiddingSubmissionDTO);
        mBiddingSubmission = mBiddingSubmissionRepository.save(mBiddingSubmission);
        return mBiddingSubmissionMapper.toDto(mBiddingSubmission);
    }

    /**
     * TODO Use a generic method to update the document status for every entities.
     * TODO Use the workflow engine for maintaining the flow state.
     */
    public void updateDocumentStatus(MBiddingSubmissionDTO mBiddingSubmissionDTO) {
        log.debug("Request to update CVendor's document status : {}", mBiddingSubmissionDTO);
        MBiddingSubmission mBiddingSubmission = mBiddingSubmissionMapper.toEntity(mBiddingSubmissionDTO);
        String action = mBiddingSubmission.getDocumentAction();

        // TODO The following process is less-efficient. Make this more efficient.
        if (mBiddingSubmission.getDateSubmit() == null && DocumentUtil.isSubmit(action)) {
            mBiddingSubmission.setDateSubmit(ZonedDateTime.now());
            mBiddingSubmissionRepository.save(mBiddingSubmission);
        }

        mBiddingSubmissionRepository.updateDocumentStatus(mBiddingSubmission.getId(), action, action, false, false);
    }

    /**
     * Get all the mBiddingSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSubmissions");
        return mBiddingSubmissionRepository.findAll(pageable)
            .map(mBiddingSubmissionMapper::toDto);
    }

    /**
     * Get one mBiddingSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingSubmissionDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSubmission : {}", id);
        return mBiddingSubmissionRepository.findById(id)
            .map(mBiddingSubmissionMapper::toDto);
    }

    /**
     * Delete the mBiddingSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSubmission : {}", id);
        mBiddingSubmissionRepository.deleteById(id);
    }
}
