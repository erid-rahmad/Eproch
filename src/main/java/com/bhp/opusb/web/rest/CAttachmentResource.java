package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CAttachmentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CAttachmentDTO;
import com.bhp.opusb.service.dto.CAttachmentCriteria;
import com.bhp.opusb.service.CAttachmentQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CAttachment}.
 */
@RestController
@RequestMapping("/api")
public class CAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(CAttachmentResource.class);

    private static final String ENTITY_NAME = "cAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CAttachmentService cAttachmentService;

    private final CAttachmentQueryService cAttachmentQueryService;

    public CAttachmentResource(CAttachmentService cAttachmentService, CAttachmentQueryService cAttachmentQueryService) {
        this.cAttachmentService = cAttachmentService;
        this.cAttachmentQueryService = cAttachmentQueryService;
    }

    /**
     * {@code POST  /c-attachments} : Create a new cAttachment.
     *
     * @param cAttachmentDTO the cAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAttachmentDTO, or with status {@code 400 (Bad Request)} if the cAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-attachments")
    public ResponseEntity<CAttachmentDTO> createCAttachment(@Valid @RequestBody CAttachmentDTO cAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save CAttachment : {}", cAttachmentDTO);
        if (cAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new cAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CAttachmentDTO result = cAttachmentService.save(cAttachmentDTO);
        return ResponseEntity.created(new URI("/api/c-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-attachments} : Updates an existing cAttachment.
     *
     * @param cAttachmentDTO the cAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the cAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-attachments")
    public ResponseEntity<CAttachmentDTO> updateCAttachment(@Valid @RequestBody CAttachmentDTO cAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update CAttachment : {}", cAttachmentDTO);
        if (cAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CAttachmentDTO result = cAttachmentService.save(cAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-attachments} : get all the cAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cAttachments in body.
     */
    @GetMapping("/c-attachments")
    public ResponseEntity<List<CAttachmentDTO>> getAllCAttachments(CAttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CAttachments by criteria: {}", criteria);
        Page<CAttachmentDTO> page = cAttachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-attachments/count} : count all the cAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-attachments/count")
    public ResponseEntity<Long> countCAttachments(CAttachmentCriteria criteria) {
        log.debug("REST request to count CAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(cAttachmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-attachments/:id} : get the "id" cAttachment.
     *
     * @param id the id of the cAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-attachments/{id}")
    public ResponseEntity<CAttachmentDTO> getCAttachment(@PathVariable Long id) {
        log.debug("REST request to get CAttachment : {}", id);
        Optional<CAttachmentDTO> cAttachmentDTO = cAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cAttachmentDTO);
    }

    /**
     * {@code DELETE  /c-attachments/:id} : delete the "id" cAttachment.
     *
     * @param id the id of the cAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-attachments/{id}")
    public ResponseEntity<Void> deleteCAttachment(@PathVariable Long id) {
        log.debug("REST request to delete CAttachment : {}", id);
        cAttachmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
