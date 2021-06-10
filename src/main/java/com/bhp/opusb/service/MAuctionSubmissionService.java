package com.bhp.opusb.service;

import com.bhp.opusb.domain.MAuctionSubmission;
import com.bhp.opusb.repository.MAuctionSubmissionRepository;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;
import com.bhp.opusb.service.mapper.MAuctionSubmissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MAuctionSubmission}.
 */
@Service
@Transactional
public class MAuctionSubmissionService {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionService.class);

    private final MAuctionSubmissionRepository mAuctionSubmissionRepository;

    private final MAuctionSubmissionMapper mAuctionSubmissionMapper;

    public MAuctionSubmissionService(MAuctionSubmissionRepository mAuctionSubmissionRepository, MAuctionSubmissionMapper mAuctionSubmissionMapper) {
        this.mAuctionSubmissionRepository = mAuctionSubmissionRepository;
        this.mAuctionSubmissionMapper = mAuctionSubmissionMapper;
    }

    /**
     * Save a mAuctionSubmission.
     *
     * @param mAuctionSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    public MAuctionSubmissionDTO save(MAuctionSubmissionDTO mAuctionSubmissionDTO) {
        log.debug("Request to save MAuctionSubmission : {}", mAuctionSubmissionDTO);
        MAuctionSubmission mAuctionSubmission = mAuctionSubmissionMapper.toEntity(mAuctionSubmissionDTO);
        mAuctionSubmission = mAuctionSubmissionRepository.save(mAuctionSubmission);
        return mAuctionSubmissionMapper.toDto(mAuctionSubmission);
    }

    /**
     * Get all the mAuctionSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MAuctionSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MAuctionSubmissions");
        return mAuctionSubmissionRepository.findAll(pageable)
            .map(mAuctionSubmissionMapper::toDto);
    }

    /**
     * Get one mAuctionSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MAuctionSubmissionDTO> findOne(Long id) {
        log.debug("Request to get MAuctionSubmission : {}", id);
        return mAuctionSubmissionRepository.findById(id)
            .map(mAuctionSubmissionMapper::toDto);
    }

    /**
     * Delete the mAuctionSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MAuctionSubmission : {}", id);
        mAuctionSubmissionRepository.deleteById(id);
    }
}
