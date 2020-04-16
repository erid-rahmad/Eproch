package com.bhp.opusb.service;

import com.bhp.opusb.domain.DocumentType;
import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.repository.DocumentTypeBusinessCategoryRepository;
import com.bhp.opusb.repository.DocumentTypeRepository;
import com.bhp.opusb.service.dto.DocumentTypeDTO;
import com.bhp.opusb.service.mapper.DocumentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DocumentType}.
 */
@Service
@Transactional
public class DocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeService.class);

    private final DocumentTypeRepository documentTypeRepository;
    private final DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository;

    private final DocumentTypeMapper documentTypeMapper;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository, DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository, DocumentTypeMapper documentTypeMapper) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeBusinessCategoryRepository = documentTypeBusinessCategoryRepository;
        this.documentTypeMapper = documentTypeMapper;
    }

    /**
     * Save a documentType.
     *
     * @param documentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO) {
        log.debug("Request to save DocumentType : {}", documentTypeDTO);
        DocumentType documentType = documentTypeMapper.toEntity(documentTypeDTO);
        Set<DocumentTypeBusinessCategory> businessCategories = documentType.getDocumentTypeBusinessCategories();
        Set<DocumentTypeBusinessCategory> removedBusinessCategories = businessCategories.stream().
            filter((item) -> item.isRemoved()).collect(Collectors.toSet());
        boolean isNew = documentType.getId() == null;
        
        documentType.setDocumentTypeBusinessCategories(null);
        documentType = documentTypeRepository.save(documentType);
        businessCategories = businessCategories.stream()
            .filter((item) -> !item.isRemoved()).collect(Collectors.toSet());
            
        if (isNew) {
            for (DocumentTypeBusinessCategory category : businessCategories) {
                category.setDocumentType(documentType);
            }
        }

        documentTypeBusinessCategoryRepository.saveAll(businessCategories);
        documentTypeBusinessCategoryRepository.deleteInBatch(removedBusinessCategories);
        documentType.setDocumentTypeBusinessCategories(businessCategories);
        return documentTypeMapper.toDto(documentType);
    }

    /**
     * Get all the documentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypes");
        return documentTypeRepository.findAll(pageable)
            .map(documentTypeMapper::toDto);
    }

    public List<DocumentTypeDTO> findByBusinessCategories(boolean mandatory, Set<Long> businessCategoryIds) {
        log.debug("Request to get all DocumentTypes with the specific business categories");
        List<DocumentType> result;

        if (mandatory) {
            result = documentTypeRepository.findMandatoryByBusinessCategories(businessCategoryIds);
        } else {
            result = documentTypeRepository.findAdditionalByBusinessCategories(businessCategoryIds);
        }
        return result.stream().map(documentTypeMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get one documentType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentTypeDTO> findOne(Long id) {
        log.debug("Request to get DocumentType : {}", id);
        return documentTypeRepository.findById(id)
            .map(documentTypeMapper::toDto);
    }

    /**
     * Delete the documentType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentType : {}", id);
        documentTypeBusinessCategoryRepository.deleteByDocumentType_Id(id);
        documentTypeRepository.deleteById(id);
    }
}
