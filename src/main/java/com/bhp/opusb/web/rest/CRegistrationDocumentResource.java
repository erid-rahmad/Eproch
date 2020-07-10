package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CRegistrationDocumentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CRegistrationDocumentDTO;
import com.bhp.opusb.service.dto.CRegistrationDocumentCriteria;
import com.bhp.opusb.service.CRegistrationDocumentQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CRegistrationDocument}.
 */
@RestController
@RequestMapping("/api")
public class CRegistrationDocumentResource {

    private final Logger log = LoggerFactory.getLogger(CRegistrationDocumentResource.class);

    private static final String ENTITY_NAME = "cRegistrationDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRegistrationDocumentService cRegistrationDocumentService;

    private final CRegistrationDocumentQueryService cRegistrationDocumentQueryService;

    public CRegistrationDocumentResource(CRegistrationDocumentService cRegistrationDocumentService, CRegistrationDocumentQueryService cRegistrationDocumentQueryService) {
        this.cRegistrationDocumentService = cRegistrationDocumentService;
        this.cRegistrationDocumentQueryService = cRegistrationDocumentQueryService;
    }

    /**
     * {@code POST  /c-registration-documents} : Create a new cRegistrationDocument.
     *
     * @param cRegistrationDocumentDTO the cRegistrationDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cRegistrationDocumentDTO, or with status {@code 400 (Bad Request)} if the cRegistrationDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-registration-documents")
    public ResponseEntity<CRegistrationDocumentDTO> createCRegistrationDocument(@Valid @RequestBody CRegistrationDocumentDTO cRegistrationDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save CRegistrationDocument : {}", cRegistrationDocumentDTO);
        if (cRegistrationDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new cRegistrationDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CRegistrationDocumentDTO result = cRegistrationDocumentService.save(cRegistrationDocumentDTO);
        return ResponseEntity.created(new URI("/api/c-registration-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-registration-documents} : Updates an existing cRegistrationDocument.
     *
     * @param cRegistrationDocumentDTO the cRegistrationDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cRegistrationDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the cRegistrationDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cRegistrationDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-registration-documents")
    public ResponseEntity<CRegistrationDocumentDTO> updateCRegistrationDocument(@Valid @RequestBody CRegistrationDocumentDTO cRegistrationDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update CRegistrationDocument : {}", cRegistrationDocumentDTO);
        if (cRegistrationDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CRegistrationDocumentDTO result = cRegistrationDocumentService.save(cRegistrationDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cRegistrationDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-registration-documents} : get all the cRegistrationDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cRegistrationDocuments in body.
     */
    @GetMapping("/c-registration-documents")
    public ResponseEntity<List<CRegistrationDocumentDTO>> getAllCRegistrationDocuments(CRegistrationDocumentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CRegistrationDocuments by criteria: {}", criteria);
        Page<CRegistrationDocumentDTO> page = cRegistrationDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-registration-documents/count} : count all the cRegistrationDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-registration-documents/count")
    public ResponseEntity<Long> countCRegistrationDocuments(CRegistrationDocumentCriteria criteria) {
        log.debug("REST request to count CRegistrationDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(cRegistrationDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-registration-documents/:id} : get the "id" cRegistrationDocument.
     *
     * @param id the id of the cRegistrationDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cRegistrationDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-registration-documents/{id}")
    public ResponseEntity<CRegistrationDocumentDTO> getCRegistrationDocument(@PathVariable Long id) {
        log.debug("REST request to get CRegistrationDocument : {}", id);
        Optional<CRegistrationDocumentDTO> cRegistrationDocumentDTO = cRegistrationDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cRegistrationDocumentDTO);
    }

    /**
     * {@code DELETE  /c-registration-documents/:id} : delete the "id" cRegistrationDocument.
     *
     * @param id the id of the cRegistrationDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-registration-documents/{id}")
    public ResponseEntity<Void> deleteCRegistrationDocument(@PathVariable Long id) {
        log.debug("REST request to delete CRegistrationDocument : {}", id);
        cRegistrationDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
