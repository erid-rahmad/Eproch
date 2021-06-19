package com.bhp.opusb.service;

import com.bhp.opusb.domain.MContractDocument;
import com.bhp.opusb.repository.MContractDocumentRepository;
import com.bhp.opusb.service.dto.MContractDocumentDTO;
import com.bhp.opusb.service.mapper.MContractDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractDocument}.
 */
@Service
@Transactional
public class MContractDocumentService {

    private final Logger log = LoggerFactory.getLogger(MContractDocumentService.class);

    private final MContractDocumentRepository mContractDocumentRepository;

    private final MContractDocumentMapper mContractDocumentMapper;

    public MContractDocumentService(MContractDocumentRepository mContractDocumentRepository, MContractDocumentMapper mContractDocumentMapper) {
        this.mContractDocumentRepository = mContractDocumentRepository;
        this.mContractDocumentMapper = mContractDocumentMapper;
    }

    /**
     * Save a mContractDocument.
     *
     * @param mContractDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractDocumentDTO save(MContractDocumentDTO mContractDocumentDTO) {
        log.debug("Request to save MContractDocument : {}", mContractDocumentDTO);
        MContractDocument mContractDocument = mContractDocumentMapper.toEntity(mContractDocumentDTO);
        mContractDocument = mContractDocumentRepository.save(mContractDocument);
        return mContractDocumentMapper.toDto(mContractDocument);
    }

    /**
     * Get all the mContractDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractDocuments");
        return mContractDocumentRepository.findAll(pageable)
            .map(mContractDocumentMapper::toDto);
    }

    /**
     * Get one mContractDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractDocumentDTO> findOne(Long id) {
        log.debug("Request to get MContractDocument : {}", id);
        return mContractDocumentRepository.findById(id)
            .map(mContractDocumentMapper::toDto);
    }

    /**
     * Delete the mContractDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractDocument : {}", id);
        mContractDocumentRepository.deleteById(id);
    }
}
