package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CRegistrationDocument;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.repository.CRegistrationDocumentRepository;
import com.bhp.opusb.service.dto.CRegistrationDocumentDTO;
import com.bhp.opusb.service.mapper.CAttachmentMapper;
import com.bhp.opusb.service.mapper.CRegistrationDocumentMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CRegistrationDocument}.
 */
@Service
@Transactional
public class CRegistrationDocumentService {

    private final Logger log = LoggerFactory.getLogger(CRegistrationDocumentService.class);

    private final CRegistrationDocumentRepository cRegistrationDocumentRepository;

    private final CRegistrationDocumentMapper cRegistrationDocumentMapper;
    private final CAttachmentMapper cAttachmentMapper;

    public CRegistrationDocumentService(CRegistrationDocumentRepository cRegistrationDocumentRepository,
        CRegistrationDocumentMapper cRegistrationDocumentMapper,
        CAttachmentMapper cAttachmentMapper
    ) {
        this.cRegistrationDocumentRepository = cRegistrationDocumentRepository;
        this.cRegistrationDocumentMapper = cRegistrationDocumentMapper;
        this.cAttachmentMapper = cAttachmentMapper;
    }

    /**
     * Save a cRegistrationDocument.
     *
     * @param cRegistrationDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    public CRegistrationDocumentDTO save(CRegistrationDocumentDTO cRegistrationDocumentDTO) {
        log.debug("Request to save CRegistrationDocument : {}", cRegistrationDocumentDTO);
        CRegistrationDocument cRegistrationDocument = cRegistrationDocumentMapper.toEntity(cRegistrationDocumentDTO);
        cRegistrationDocument = cRegistrationDocumentRepository.save(cRegistrationDocument);
        return cRegistrationDocumentMapper.toDto(cRegistrationDocument);
    }

    public List<CRegistrationDocumentDTO> saveAll(List<CRegistrationDocumentDTO> documents, CVendor vendor, ADOrganization organization) {
        log.debug("Request to save all CRegistrationDocuments. List size: {}", documents.size());
        List<CRegistrationDocument> registrationDocuments = documents.stream()
            .map(document
                -> cRegistrationDocumentMapper.toEntity(document)
                    .active(true)
                    .adOrganization(organization)
                    .file(cAttachmentMapper.fromId(document.getFileId()))
                    .vendor(vendor)
            )
            .collect(Collectors.toList());

        return cRegistrationDocumentRepository.saveAll(registrationDocuments)
            .stream()
            .map(cRegistrationDocumentMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all the cRegistrationDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CRegistrationDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CRegistrationDocuments");
        return cRegistrationDocumentRepository.findAll(pageable)
            .map(cRegistrationDocumentMapper::toDto);
    }

    /**
     * Get one cRegistrationDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CRegistrationDocumentDTO> findOne(Long id) {
        log.debug("Request to get CRegistrationDocument : {}", id);
        return cRegistrationDocumentRepository.findById(id)
            .map(cRegistrationDocumentMapper::toDto);
    }

    /**
     * Delete the cRegistrationDocument by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CRegistrationDocument : {}", id);
        cRegistrationDocumentRepository.deleteById(id);
    }
}
