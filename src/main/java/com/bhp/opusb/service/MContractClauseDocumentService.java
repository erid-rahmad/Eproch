package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractClauseDocument;
import com.bhp.opusb.repository.MContractClauseDocumentRepository;
import com.bhp.opusb.service.dto.MContractClauseDocumentDTO;
import com.bhp.opusb.service.mapper.MContractClauseDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractClauseDocument}.
 */
@Service
@Transactional
public class MContractClauseDocumentService {

    private final Logger log = LoggerFactory.getLogger(MContractClauseDocumentService.class);

    private final MContractClauseDocumentRepository mContractClauseDocumentRepository;

    private final MContractClauseDocumentMapper mContractClauseDocumentMapper;

    public MContractClauseDocumentService(MContractClauseDocumentRepository mContractClauseDocumentRepository, MContractClauseDocumentMapper mContractClauseDocumentMapper) {
        this.mContractClauseDocumentRepository = mContractClauseDocumentRepository;
        this.mContractClauseDocumentMapper = mContractClauseDocumentMapper;
    }

    /**
     * Save a mContractClauseDocument.
     *
     * @param mContractClauseDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractClauseDocumentDTO save(MContractClauseDocumentDTO mContractClauseDocumentDTO) {
        log.debug("Request to save MContractClauseDocument : {}", mContractClauseDocumentDTO);
        MContractClauseDocument mContractClauseDocument = mContractClauseDocumentMapper.toEntity(mContractClauseDocumentDTO);
        mContractClauseDocument = mContractClauseDocumentRepository.save(mContractClauseDocument);
        return mContractClauseDocumentMapper.toDto(mContractClauseDocument);
    }

    /**
     * Get all the mContractClauseDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractClauseDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractClauseDocuments");
        return mContractClauseDocumentRepository.findAll(pageable)
            .map(mContractClauseDocumentMapper::toDto);
    }

    /**
     * Get one mContractClauseDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractClauseDocumentDTO> findOne(Long id) {
        log.debug("Request to get MContractClauseDocument : {}", id);
        return mContractClauseDocumentRepository.findById(id)
            .map(mContractClauseDocumentMapper::toDto);
    }

    /**
     * Delete the mContractClauseDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractClauseDocument : {}", id);
        mContractClauseDocumentRepository.deleteById(id);
    }
}
