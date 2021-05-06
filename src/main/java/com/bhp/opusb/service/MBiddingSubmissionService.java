package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
