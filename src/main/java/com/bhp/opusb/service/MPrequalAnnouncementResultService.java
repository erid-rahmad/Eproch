package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalAnnouncementResult;
import com.bhp.opusb.repository.MPrequalAnnouncementResultRepository;
import com.bhp.opusb.service.dto.MPrequalAnnouncementResultDTO;
import com.bhp.opusb.service.mapper.MPrequalAnnouncementResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalAnnouncementResult}.
 */
@Service
@Transactional
public class MPrequalAnnouncementResultService {

    private final Logger log = LoggerFactory.getLogger(MPrequalAnnouncementResultService.class);

    private final MPrequalAnnouncementResultRepository mPrequalAnnouncementResultRepository;

    private final MPrequalAnnouncementResultMapper mPrequalAnnouncementResultMapper;

    public MPrequalAnnouncementResultService(MPrequalAnnouncementResultRepository mPrequalAnnouncementResultRepository, MPrequalAnnouncementResultMapper mPrequalAnnouncementResultMapper) {
        this.mPrequalAnnouncementResultRepository = mPrequalAnnouncementResultRepository;
        this.mPrequalAnnouncementResultMapper = mPrequalAnnouncementResultMapper;
    }

    /**
     * Save a mPrequalAnnouncementResult.
     *
     * @param mPrequalAnnouncementResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalAnnouncementResultDTO save(MPrequalAnnouncementResultDTO mPrequalAnnouncementResultDTO) {
        log.debug("Request to save MPrequalAnnouncementResult : {}", mPrequalAnnouncementResultDTO);
        MPrequalAnnouncementResult mPrequalAnnouncementResult = mPrequalAnnouncementResultMapper.toEntity(mPrequalAnnouncementResultDTO);
        mPrequalAnnouncementResult = mPrequalAnnouncementResultRepository.save(mPrequalAnnouncementResult);
        return mPrequalAnnouncementResultMapper.toDto(mPrequalAnnouncementResult);
    }

    /**
     * Get all the mPrequalAnnouncementResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalAnnouncementResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalAnnouncementResults");
        return mPrequalAnnouncementResultRepository.findAll(pageable)
            .map(mPrequalAnnouncementResultMapper::toDto);
    }

    /**
     * Get one mPrequalAnnouncementResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalAnnouncementResultDTO> findOne(Long id) {
        log.debug("Request to get MPrequalAnnouncementResult : {}", id);
        return mPrequalAnnouncementResultRepository.findById(id)
            .map(mPrequalAnnouncementResultMapper::toDto);
    }

    /**
     * Delete the mPrequalAnnouncementResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalAnnouncementResult : {}", id);
        mPrequalAnnouncementResultRepository.deleteById(id);
    }
}
