package com.bhp.opusb.web.rest;

import com.bhp.opusb.domain.DocumentTypeBusinessCategory;
import com.bhp.opusb.repository.DocumentTypeBusinessCategoryRepository;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.DocumentTypeBusinessCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentTypeBusinessCategoryResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeBusinessCategoryResource.class);

    private static final String ENTITY_NAME = "documentTypeBusinessCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository;

    public DocumentTypeBusinessCategoryResource(DocumentTypeBusinessCategoryRepository documentTypeBusinessCategoryRepository) {
        this.documentTypeBusinessCategoryRepository = documentTypeBusinessCategoryRepository;
    }

    /**
     * {@code POST  /document-type-business-categories} : Create a new documentTypeBusinessCategory.
     *
     * @param documentTypeBusinessCategory the documentTypeBusinessCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentTypeBusinessCategory, or with status {@code 400 (Bad Request)} if the documentTypeBusinessCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/document-type-business-categories")
    public ResponseEntity<DocumentTypeBusinessCategory> createDocumentTypeBusinessCategory(@RequestBody DocumentTypeBusinessCategory documentTypeBusinessCategory) throws URISyntaxException {
        log.debug("REST request to save DocumentTypeBusinessCategory : {}", documentTypeBusinessCategory);
        if (documentTypeBusinessCategory.getId() != null) {
            throw new BadRequestAlertException("A new documentTypeBusinessCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTypeBusinessCategory result = documentTypeBusinessCategoryRepository.save(documentTypeBusinessCategory);
        return ResponseEntity.created(new URI("/api/document-type-business-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /document-type-business-categories} : Updates an existing documentTypeBusinessCategory.
     *
     * @param documentTypeBusinessCategory the documentTypeBusinessCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentTypeBusinessCategory,
     * or with status {@code 400 (Bad Request)} if the documentTypeBusinessCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentTypeBusinessCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/document-type-business-categories")
    public ResponseEntity<DocumentTypeBusinessCategory> updateDocumentTypeBusinessCategory(@RequestBody DocumentTypeBusinessCategory documentTypeBusinessCategory) throws URISyntaxException {
        log.debug("REST request to update DocumentTypeBusinessCategory : {}", documentTypeBusinessCategory);
        if (documentTypeBusinessCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTypeBusinessCategory result = documentTypeBusinessCategoryRepository.save(documentTypeBusinessCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentTypeBusinessCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /document-type-business-categories} : get all the documentTypeBusinessCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentTypeBusinessCategories in body.
     */
    @GetMapping("/document-type-business-categories")
    public List<DocumentTypeBusinessCategory> getAllDocumentTypeBusinessCategories() {
        log.debug("REST request to get all DocumentTypeBusinessCategories");
        return documentTypeBusinessCategoryRepository.findAll();
    }

    /**
     * {@code GET  /document-type-business-categories/:id} : get the "id" documentTypeBusinessCategory.
     *
     * @param id the id of the documentTypeBusinessCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentTypeBusinessCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-type-business-categories/{id}")
    public ResponseEntity<DocumentTypeBusinessCategory> getDocumentTypeBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to get DocumentTypeBusinessCategory : {}", id);
        Optional<DocumentTypeBusinessCategory> documentTypeBusinessCategory = documentTypeBusinessCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentTypeBusinessCategory);
    }

    /**
     * {@code DELETE  /document-type-business-categories/:id} : delete the "id" documentTypeBusinessCategory.
     *
     * @param id the id of the documentTypeBusinessCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-type-business-categories/{id}")
    public ResponseEntity<Void> deleteDocumentTypeBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTypeBusinessCategory : {}", id);
        documentTypeBusinessCategoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
