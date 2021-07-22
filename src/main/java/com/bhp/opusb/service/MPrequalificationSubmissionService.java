package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationSubmission;
import com.bhp.opusb.repository.MPrequalificationSubmissionRepository;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionDTO;
import com.bhp.opusb.service.mapper.MPrequalificationSubmissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalificationSubmission}.
 */
@Service
@Transactional
public class MPrequalificationSubmissionService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationSubmissionService.class);

    private final MPrequalificationSubmissionRepository mPrequalificationSubmissionRepository;

    private final MPrequalificationSubmissionMapper mPrequalificationSubmissionMapper;

    public MPrequalificationSubmissionService(MPrequalificationSubmissionRepository mPrequalificationSubmissionRepository, MPrequalificationSubmissionMapper mPrequalificationSubmissionMapper) {
        this.mPrequalificationSubmissionRepository = mPrequalificationSubmissionRepository;
        this.mPrequalificationSubmissionMapper = mPrequalificationSubmissionMapper;
    }

    /**
     * Save a mPrequalificationSubmission.
     *
     * @param mPrequalificationSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationSubmissionDTO save(MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO) {
        log.debug("Request to save MPrequalificationSubmission : {}", mPrequalificationSubmissionDTO);
        MPrequalificationSubmission mPrequalificationSubmission = mPrequalificationSubmissionMapper.toEntity(mPrequalificationSubmissionDTO);
        mPrequalificationSubmission = mPrequalificationSubmissionRepository.save(mPrequalificationSubmission);
        return mPrequalificationSubmissionMapper.toDto(mPrequalificationSubmission);
    }

    /**
     * Get all the mPrequalificationSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationSubmissions");
        return mPrequalificationSubmissionRepository.findAll(pageable)
            .map(mPrequalificationSubmissionMapper::toDto);
    }

    /**
     * Get one mPrequalificationSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationSubmissionDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationSubmission : {}", id);
        return mPrequalificationSubmissionRepository.findById(id)
            .map(mPrequalificationSubmissionMapper::toDto);
    }

    /**
     * Delete the mPrequalificationSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationSubmission : {}", id);
        mPrequalificationSubmissionRepository.deleteById(id);
    }
}
