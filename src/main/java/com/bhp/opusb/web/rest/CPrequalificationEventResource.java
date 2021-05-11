package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalificationEventService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalificationEventDTO;
import com.bhp.opusb.service.dto.CPrequalificationEventCriteria;
import com.bhp.opusb.service.CPrequalificationEventQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalificationEvent}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalificationEventResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationEventResource.class);

    private static final String ENTITY_NAME = "cPrequalificationEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalificationEventService cPrequalificationEventService;

    private final CPrequalificationEventQueryService cPrequalificationEventQueryService;

    public CPrequalificationEventResource(CPrequalificationEventService cPrequalificationEventService, CPrequalificationEventQueryService cPrequalificationEventQueryService) {
        this.cPrequalificationEventService = cPrequalificationEventService;
        this.cPrequalificationEventQueryService = cPrequalificationEventQueryService;
    }

    /**
     * {@code POST  /c-prequalification-events} : Create a new cPrequalificationEvent.
     *
     * @param cPrequalificationEventDTO the cPrequalificationEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalificationEventDTO, or with status {@code 400 (Bad Request)} if the cPrequalificationEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequalification-events")
    public ResponseEntity<CPrequalificationEventDTO> createCPrequalificationEvent(@Valid @RequestBody CPrequalificationEventDTO cPrequalificationEventDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalificationEvent : {}", cPrequalificationEventDTO);
        if (cPrequalificationEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalificationEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalificationEventDTO result = cPrequalificationEventService.save(cPrequalificationEventDTO);
        return ResponseEntity.created(new URI("/api/c-prequalification-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequalification-events} : Updates an existing cPrequalificationEvent.
     *
     * @param cPrequalificationEventDTO the cPrequalificationEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalificationEventDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalificationEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalificationEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequalification-events")
    public ResponseEntity<CPrequalificationEventDTO> updateCPrequalificationEvent(@Valid @RequestBody CPrequalificationEventDTO cPrequalificationEventDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalificationEvent : {}", cPrequalificationEventDTO);
        if (cPrequalificationEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalificationEventDTO result = cPrequalificationEventService.save(cPrequalificationEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalificationEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequalification-events} : get all the cPrequalificationEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalificationEvents in body.
     */
    @GetMapping("/c-prequalification-events")
    public ResponseEntity<List<CPrequalificationEventDTO>> getAllCPrequalificationEvents(CPrequalificationEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalificationEvents by criteria: {}", criteria);
        Page<CPrequalificationEventDTO> page = cPrequalificationEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequalification-events/count} : count all the cPrequalificationEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequalification-events/count")
    public ResponseEntity<Long> countCPrequalificationEvents(CPrequalificationEventCriteria criteria) {
        log.debug("REST request to count CPrequalificationEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalificationEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequalification-events/:id} : get the "id" cPrequalificationEvent.
     *
     * @param id the id of the cPrequalificationEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalificationEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequalification-events/{id}")
    public ResponseEntity<CPrequalificationEventDTO> getCPrequalificationEvent(@PathVariable Long id) {
        log.debug("REST request to get CPrequalificationEvent : {}", id);
        Optional<CPrequalificationEventDTO> cPrequalificationEventDTO = cPrequalificationEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalificationEventDTO);
    }

    /**
     * {@code DELETE  /c-prequalification-events/:id} : delete the "id" cPrequalificationEvent.
     *
     * @param id the id of the cPrequalificationEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequalification-events/{id}")
    public ResponseEntity<Void> deleteCPrequalificationEvent(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalificationEvent : {}", id);
        cPrequalificationEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
