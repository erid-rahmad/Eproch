package com.bhp.opusb.service;

import com.bhp.opusb.domain.SupportingDocument;
import com.bhp.opusb.repository.SupportingDocumentRepository;
import com.bhp.opusb.service.dto.SupportingDocumentDTO;
import com.bhp.opusb.service.mapper.SupportingDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SupportingDocument}.
 */
@Service
@Transactional
public class SupportingDocumentService {

    private final Logger log = LoggerFactory.getLogger(SupportingDocumentService.class);

    private final SupportingDocumentRepository supportingDocumentRepository;

    private final SupportingDocumentMapper supportingDocumentMapper;

    public SupportingDocumentService(SupportingDocumentRepository supportingDocumentRepository, SupportingDocumentMapper supportingDocumentMapper) {
        this.supportingDocumentRepository = supportingDocumentRepository;
        this.supportingDocumentMapper = supportingDocumentMapper;
    }

    /**
     * Save a supportingDocument.
     *
     * @param supportingDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public SupportingDocumentDTO save(SupportingDocumentDTO supportingDocumentDTO) {
        log.debug("Request to save SupportingDocument : {}", supportingDocumentDTO);
        SupportingDocument supportingDocument = supportingDocumentMapper.toEntity(supportingDocumentDTO);
        supportingDocument = supportingDocumentRepository.save(supportingDocument);
        return supportingDocumentMapper.toDto(supportingDocument);
    }

    /**
     * Get all the supportingDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SupportingDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupportingDocuments");
        return supportingDocumentRepository.findAll(pageable)
            .map(supportingDocumentMapper::toDto);
    }

    /**
     * Get one supportingDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SupportingDocumentDTO> findOne(Long id) {
        log.debug("Request to get SupportingDocument : {}", id);
        return supportingDocumentRepository.findById(id)
            .map(supportingDocumentMapper::toDto);
    }

    /**
     * Delete the supportingDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SupportingDocument : {}", id);
        supportingDocumentRepository.deleteById(id);
    }
}
