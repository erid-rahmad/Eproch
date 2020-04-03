package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.SupportingDocumentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.SupportingDocumentDTO;
import com.bhp.opusb.service.dto.SupportingDocumentCriteria;
import com.bhp.opusb.service.SupportingDocumentQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.SupportingDocument}.
 */
@RestController
@RequestMapping("/api")
public class SupportingDocumentResource {

    private final Logger log = LoggerFactory.getLogger(SupportingDocumentResource.class);

    private static final String ENTITY_NAME = "supportingDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupportingDocumentService supportingDocumentService;

    private final SupportingDocumentQueryService supportingDocumentQueryService;

    public SupportingDocumentResource(SupportingDocumentService supportingDocumentService, SupportingDocumentQueryService supportingDocumentQueryService) {
        this.supportingDocumentService = supportingDocumentService;
        this.supportingDocumentQueryService = supportingDocumentQueryService;
    }

    /**
     * {@code POST  /supporting-documents} : Create a new supportingDocument.
     *
     * @param supportingDocumentDTO the supportingDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supportingDocumentDTO, or with status {@code 400 (Bad Request)} if the supportingDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supporting-documents")
    public ResponseEntity<SupportingDocumentDTO> createSupportingDocument(@Valid @RequestBody SupportingDocumentDTO supportingDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save SupportingDocument : {}", supportingDocumentDTO);
        if (supportingDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new supportingDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupportingDocumentDTO result = supportingDocumentService.save(supportingDocumentDTO);
        return ResponseEntity.created(new URI("/api/supporting-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supporting-documents} : Updates an existing supportingDocument.
     *
     * @param supportingDocumentDTO the supportingDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supportingDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the supportingDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supportingDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supporting-documents")
    public ResponseEntity<SupportingDocumentDTO> updateSupportingDocument(@Valid @RequestBody SupportingDocumentDTO supportingDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update SupportingDocument : {}", supportingDocumentDTO);
        if (supportingDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupportingDocumentDTO result = supportingDocumentService.save(supportingDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supportingDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supporting-documents} : get all the supportingDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supportingDocuments in body.
     */
    @GetMapping("/supporting-documents")
    public ResponseEntity<List<SupportingDocumentDTO>> getAllSupportingDocuments(SupportingDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupportingDocuments by criteria: {}", criteria);
        Page<SupportingDocumentDTO> page = supportingDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supporting-documents/count} : count all the supportingDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/supporting-documents/count")
    public ResponseEntity<Long> countSupportingDocuments(SupportingDocumentCriteria criteria) {
        log.debug("REST request to count SupportingDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(supportingDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supporting-documents/:id} : get the "id" supportingDocument.
     *
     * @param id the id of the supportingDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supportingDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supporting-documents/{id}")
    public ResponseEntity<SupportingDocumentDTO> getSupportingDocument(@PathVariable Long id) {
        log.debug("REST request to get SupportingDocument : {}", id);
        Optional<SupportingDocumentDTO> supportingDocumentDTO = supportingDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supportingDocumentDTO);
    }

    /**
     * {@code DELETE  /supporting-documents/:id} : delete the "id" supportingDocument.
     *
     * @param id the id of the supportingDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supporting-documents/{id}")
    public ResponseEntity<Void> deleteSupportingDocument(@PathVariable Long id) {
        log.debug("REST request to delete SupportingDocument : {}", id);
        supportingDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
