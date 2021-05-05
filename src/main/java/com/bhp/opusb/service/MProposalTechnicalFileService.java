package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalTechnicalFile;
import com.bhp.opusb.repository.MProposalTechnicalFileRepository;
import com.bhp.opusb.service.dto.MProposalTechnicalFileDTO;
import com.bhp.opusb.service.mapper.MProposalTechnicalFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalTechnicalFile}.
 */
@Service
@Transactional
public class MProposalTechnicalFileService {

    private final Logger log = LoggerFactory.getLogger(MProposalTechnicalFileService.class);

    private final MProposalTechnicalFileRepository mProposalTechnicalFileRepository;

    private final MProposalTechnicalFileMapper mProposalTechnicalFileMapper;

    public MProposalTechnicalFileService(MProposalTechnicalFileRepository mProposalTechnicalFileRepository, MProposalTechnicalFileMapper mProposalTechnicalFileMapper) {
        this.mProposalTechnicalFileRepository = mProposalTechnicalFileRepository;
        this.mProposalTechnicalFileMapper = mProposalTechnicalFileMapper;
    }

    /**
     * Save a mProposalTechnicalFile.
     *
     * @param mProposalTechnicalFileDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalTechnicalFileDTO save(MProposalTechnicalFileDTO mProposalTechnicalFileDTO) {
        log.debug("Request to save MProposalTechnicalFile : {}", mProposalTechnicalFileDTO);
        MProposalTechnicalFile mProposalTechnicalFile = mProposalTechnicalFileMapper.toEntity(mProposalTechnicalFileDTO);
        mProposalTechnicalFile = mProposalTechnicalFileRepository.save(mProposalTechnicalFile);
        return mProposalTechnicalFileMapper.toDto(mProposalTechnicalFile);
    }

    /**
     * Get all the mProposalTechnicalFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalTechnicalFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalTechnicalFiles");
        return mProposalTechnicalFileRepository.findAll(pageable)
            .map(mProposalTechnicalFileMapper::toDto);
    }

    /**
     * Get one mProposalTechnicalFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalTechnicalFileDTO> findOne(Long id) {
        log.debug("Request to get MProposalTechnicalFile : {}", id);
        return mProposalTechnicalFileRepository.findById(id)
            .map(mProposalTechnicalFileMapper::toDto);
    }

    /**
     * Delete the mProposalTechnicalFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalTechnicalFile : {}", id);
        mProposalTechnicalFileRepository.deleteById(id);
    }
}
