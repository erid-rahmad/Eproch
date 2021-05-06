package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalAdministrationFile;
import com.bhp.opusb.repository.MProposalAdministrationFileRepository;
import com.bhp.opusb.service.dto.MProposalAdministrationFileDTO;
import com.bhp.opusb.service.mapper.MProposalAdministrationFileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalAdministrationFile}.
 */
@Service
@Transactional
public class MProposalAdministrationFileService {

    private final Logger log = LoggerFactory.getLogger(MProposalAdministrationFileService.class);

    private final MProposalAdministrationFileRepository mProposalAdministrationFileRepository;

    private final MProposalAdministrationFileMapper mProposalAdministrationFileMapper;

    public MProposalAdministrationFileService(MProposalAdministrationFileRepository mProposalAdministrationFileRepository, MProposalAdministrationFileMapper mProposalAdministrationFileMapper) {
        this.mProposalAdministrationFileRepository = mProposalAdministrationFileRepository;
        this.mProposalAdministrationFileMapper = mProposalAdministrationFileMapper;
    }

    /**
     * Save a mProposalAdministrationFile.
     *
     * @param mProposalAdministrationFileDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalAdministrationFileDTO save(MProposalAdministrationFileDTO mProposalAdministrationFileDTO) {
        log.debug("Request to save MProposalAdministrationFile : {}", mProposalAdministrationFileDTO);
        MProposalAdministrationFile mProposalAdministrationFile = mProposalAdministrationFileMapper.toEntity(mProposalAdministrationFileDTO);
        mProposalAdministrationFile = mProposalAdministrationFileRepository.save(mProposalAdministrationFile);
        return mProposalAdministrationFileMapper.toDto(mProposalAdministrationFile);
    }

    /**
     * Get all the mProposalAdministrationFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalAdministrationFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalAdministrationFiles");
        return mProposalAdministrationFileRepository.findAll(pageable)
            .map(mProposalAdministrationFileMapper::toDto);
    }

    /**
     * Get one mProposalAdministrationFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalAdministrationFileDTO> findOne(Long id) {
        log.debug("Request to get MProposalAdministrationFile : {}", id);
        return mProposalAdministrationFileRepository.findById(id)
            .map(mProposalAdministrationFileMapper::toDto);
    }

    /**
     * Delete the mProposalAdministrationFile by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalAdministrationFile : {}", id);
        mProposalAdministrationFileRepository.deleteById(id);
    }
}
