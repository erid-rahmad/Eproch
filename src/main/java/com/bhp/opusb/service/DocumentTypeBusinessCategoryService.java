package com.bhp.opusb.service;

import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.repository.DocumentTypeBusinessCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DocumentTypeBusinessCategory}.
 */
@Service
@Transactional
public class DocumentTypeBusinessCategoryService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeBusinessCategoryService.class);

    private final DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository;

    public DocumentTypeBusinessCategoryService(DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository) {
        this.documentTypeBusinessCategoryRepository = documentTypeBusinessCategoryRepository;
    }

    /**
     * Save a documentTypeBusinessCategory.
     *
     * @param documentTypeBusinessCategory the entity to save.
     * @return the persisted entity.
     */
    public DocumentTypeBusinessCategory save(DocumentTypeBusinessCategory documentTypeBusinessCategory) {
        log.debug("Request to save DocumentTypeBusinessCategory : {}", documentTypeBusinessCategory);
        return documentTypeBusinessCategoryRepository.save(documentTypeBusinessCategory);
    }

    /**
     * Get all the documentTypeBusinessCategories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentTypeBusinessCategory> findAll() {
        log.debug("Request to get all DocumentTypeBusinessCategories");
        return documentTypeBusinessCategoryRepository.findAll();
    }

    /**
     * Get one documentTypeBusinessCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentTypeBusinessCategory> findOne(Long id) {
        log.debug("Request to get DocumentTypeBusinessCategory : {}", id);
        return documentTypeBusinessCategoryRepository.findById(id);
    }

    /**
     * Delete the documentTypeBusinessCategory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentTypeBusinessCategory : {}", id);
        documentTypeBusinessCategoryRepository.deleteById(id);
    }
}
