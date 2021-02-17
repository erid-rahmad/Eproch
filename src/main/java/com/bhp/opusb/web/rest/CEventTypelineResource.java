package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CEventTypelineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CEventTypelineDTO;
import com.bhp.opusb.service.dto.CEventTypelineCriteria;
import com.bhp.opusb.service.CEventTypelineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CEventTypeline}.
 */
@RestController
@RequestMapping("/api")
public class CEventTypelineResource {

    private final Logger log = LoggerFactory.getLogger(CEventTypelineResource.class);

    private static final String ENTITY_NAME = "cEventTypeline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEventTypelineService cEventTypelineService;

    private final CEventTypelineQueryService cEventTypelineQueryService;

    public CEventTypelineResource(CEventTypelineService cEventTypelineService, CEventTypelineQueryService cEventTypelineQueryService) {
        this.cEventTypelineService = cEventTypelineService;
        this.cEventTypelineQueryService = cEventTypelineQueryService;
    }

    /**
     * {@code POST  /c-event-typelines} : Create a new cEventTypeline.
     *
     * @param cEventTypelineDTO the cEventTypelineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEventTypelineDTO, or with status {@code 400 (Bad Request)} if the cEventTypeline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-event-typelines")
    public ResponseEntity<CEventTypelineDTO> createCEventTypeline(@Valid @RequestBody CEventTypelineDTO cEventTypelineDTO) throws URISyntaxException {
        log.debug("REST request to save CEventTypeline : {}", cEventTypelineDTO);
        if (cEventTypelineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEventTypeline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEventTypelineDTO result = cEventTypelineService.save(cEventTypelineDTO);
        return ResponseEntity.created(new URI("/api/c-event-typelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-event-typelines} : Updates an existing cEventTypeline.
     *
     * @param cEventTypelineDTO the cEventTypelineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEventTypelineDTO,
     * or with status {@code 400 (Bad Request)} if the cEventTypelineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEventTypelineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-event-typelines")
    public ResponseEntity<CEventTypelineDTO> updateCEventTypeline(@Valid @RequestBody CEventTypelineDTO cEventTypelineDTO) throws URISyntaxException {
        log.debug("REST request to update CEventTypeline : {}", cEventTypelineDTO);
        if (cEventTypelineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEventTypelineDTO result = cEventTypelineService.save(cEventTypelineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEventTypelineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-event-typelines} : get all the cEventTypelines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEventTypelines in body.
     */
    @GetMapping("/c-event-typelines")
    public ResponseEntity<List<CEventTypelineDTO>> getAllCEventTypelines(CEventTypelineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEventTypelines by criteria: {}", criteria);
        Page<CEventTypelineDTO> page = cEventTypelineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-event-typelines/count} : count all the cEventTypelines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-event-typelines/count")
    public ResponseEntity<Long> countCEventTypelines(CEventTypelineCriteria criteria) {
        log.debug("REST request to count CEventTypelines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEventTypelineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-event-typelines/:id} : get the "id" cEventTypeline.
     *
     * @param id the id of the cEventTypelineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEventTypelineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-event-typelines/{id}")
    public ResponseEntity<CEventTypelineDTO> getCEventTypeline(@PathVariable Long id) {
        log.debug("REST request to get CEventTypeline : {}", id);
        Optional<CEventTypelineDTO> cEventTypelineDTO = cEventTypelineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEventTypelineDTO);
    }

    /**
     * {@code DELETE  /c-event-typelines/:id} : delete the "id" cEventTypeline.
     *
     * @param id the id of the cEventTypelineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-event-typelines/{id}")
    public ResponseEntity<Void> deleteCEventTypeline(@PathVariable Long id) {
        log.debug("REST request to delete CEventTypeline : {}", id);
        cEventTypelineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
