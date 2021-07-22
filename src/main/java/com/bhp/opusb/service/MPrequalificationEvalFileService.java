package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPrequalificationEvalFile;
import com.bhp.opusb.repository.MPrequalificationEvalFileRepository;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileDTO;
import com.bhp.opusb.service.mapper.MPrequalificationEvalFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MPrequalificationEvalFile}.
 */
@Service
@Transactional
public class MPrequalificationEvalFileService {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEvalFileService.class);

    private final MPrequalificationEvalFileRepository mPrequalificationEvalFileRepository;

    private final MPrequalificationEvalFileMapper mPrequalificationEvalFileMapper;

    public MPrequalificationEvalFileService(MPrequalificationEvalFileRepository mPrequalificationEvalFileRepository, MPrequalificationEvalFileMapper mPrequalificationEvalFileMapper) {
        this.mPrequalificationEvalFileRepository = mPrequalificationEvalFileRepository;
        this.mPrequalificationEvalFileMapper = mPrequalificationEvalFileMapper;
    }

    /**
     * Save a mPrequalificationEvalFile.
     *
     * @param mPrequalificationEvalFileDTO the entity to save.
     * @return the persisted entity.
     */
    public MPrequalificationEvalFileDTO save(MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO) {
        log.debug("Request to save MPrequalificationEvalFile : {}", mPrequalificationEvalFileDTO);
        MPrequalificationEvalFile mPrequalificationEvalFile = mPrequalificationEvalFileMapper.toEntity(mPrequalificationEvalFileDTO);
        mPrequalificationEvalFile = mPrequalificationEvalFileRepository.save(mPrequalificationEvalFile);
        return mPrequalificationEvalFileMapper.toDto(mPrequalificationEvalFile);
    }

    /**
     * Get all the mPrequalificationEvalFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPrequalificationEvalFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPrequalificationEvalFiles");
        return mPrequalificationEvalFileRepository.findAll(pageable)
            .map(mPrequalificationEvalFileMapper::toDto);
    }

    /**
     * Get one mPrequalificationEvalFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPrequalificationEvalFileDTO> findOne(Long id) {
        log.debug("Request to get MPrequalificationEvalFile : {}", id);
        return mPrequalificationEvalFileRepository.findById(id)
            .map(mPrequalificationEvalFileMapper::toDto);
    }

    /**
     * Delete the mPrequalificationEvalFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPrequalificationEvalFile : {}", id);
        mPrequalificationEvalFileRepository.deleteById(id);
    }
}
