package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalificationEventLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalificationEventLineDTO;
import com.bhp.opusb.service.dto.CPrequalificationEventLineCriteria;
import com.bhp.opusb.service.CPrequalificationEventLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalificationEventLine}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalificationEventLineResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationEventLineResource.class);

    private static final String ENTITY_NAME = "cPrequalificationEventLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalificationEventLineService cPrequalificationEventLineService;

    private final CPrequalificationEventLineQueryService cPrequalificationEventLineQueryService;

    public CPrequalificationEventLineResource(CPrequalificationEventLineService cPrequalificationEventLineService, CPrequalificationEventLineQueryService cPrequalificationEventLineQueryService) {
        this.cPrequalificationEventLineService = cPrequalificationEventLineService;
        this.cPrequalificationEventLineQueryService = cPrequalificationEventLineQueryService;
    }

    /**
     * {@code POST  /c-prequalification-event-lines} : Create a new cPrequalificationEventLine.
     *
     * @param cPrequalificationEventLineDTO the cPrequalificationEventLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalificationEventLineDTO, or with status {@code 400 (Bad Request)} if the cPrequalificationEventLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequalification-event-lines")
    public ResponseEntity<CPrequalificationEventLineDTO> createCPrequalificationEventLine(@Valid @RequestBody CPrequalificationEventLineDTO cPrequalificationEventLineDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalificationEventLine : {}", cPrequalificationEventLineDTO);
        if (cPrequalificationEventLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalificationEventLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalificationEventLineDTO result = cPrequalificationEventLineService.save(cPrequalificationEventLineDTO);
        return ResponseEntity.created(new URI("/api/c-prequalification-event-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequalification-event-lines} : Updates an existing cPrequalificationEventLine.
     *
     * @param cPrequalificationEventLineDTO the cPrequalificationEventLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalificationEventLineDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalificationEventLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalificationEventLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequalification-event-lines")
    public ResponseEntity<CPrequalificationEventLineDTO> updateCPrequalificationEventLine(@Valid @RequestBody CPrequalificationEventLineDTO cPrequalificationEventLineDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalificationEventLine : {}", cPrequalificationEventLineDTO);
        if (cPrequalificationEventLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalificationEventLineDTO result = cPrequalificationEventLineService.save(cPrequalificationEventLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalificationEventLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequalification-event-lines} : get all the cPrequalificationEventLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalificationEventLines in body.
     */
    @GetMapping("/c-prequalification-event-lines")
    public ResponseEntity<List<CPrequalificationEventLineDTO>> getAllCPrequalificationEventLines(CPrequalificationEventLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalificationEventLines by criteria: {}", criteria);
        Page<CPrequalificationEventLineDTO> page = cPrequalificationEventLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequalification-event-lines/count} : count all the cPrequalificationEventLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequalification-event-lines/count")
    public ResponseEntity<Long> countCPrequalificationEventLines(CPrequalificationEventLineCriteria criteria) {
        log.debug("REST request to count CPrequalificationEventLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalificationEventLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequalification-event-lines/:id} : get the "id" cPrequalificationEventLine.
     *
     * @param id the id of the cPrequalificationEventLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalificationEventLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequalification-event-lines/{id}")
    public ResponseEntity<CPrequalificationEventLineDTO> getCPrequalificationEventLine(@PathVariable Long id) {
        log.debug("REST request to get CPrequalificationEventLine : {}", id);
        Optional<CPrequalificationEventLineDTO> cPrequalificationEventLineDTO = cPrequalificationEventLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalificationEventLineDTO);
    }

    /**
     * {@code DELETE  /c-prequalification-event-lines/:id} : delete the "id" cPrequalificationEventLine.
     *
     * @param id the id of the cPrequalificationEventLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequalification-event-lines/{id}")
    public ResponseEntity<Void> deleteCPrequalificationEventLine(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalificationEventLine : {}", id);
        cPrequalificationEventLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
