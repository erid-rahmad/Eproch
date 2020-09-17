package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ScAccessService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ScAccessDTO;
import com.bhp.opusb.service.dto.ScAccessCriteria;
import com.bhp.opusb.service.ScAccessQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.ScAccess}.
 */
@RestController
@RequestMapping("/api")
public class ScAccessResource {

    private final Logger log = LoggerFactory.getLogger(ScAccessResource.class);

    private static final String ENTITY_NAME = "scAccess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScAccessService scAccessService;

    private final ScAccessQueryService scAccessQueryService;

    public ScAccessResource(ScAccessService scAccessService, ScAccessQueryService scAccessQueryService) {
        this.scAccessService = scAccessService;
        this.scAccessQueryService = scAccessQueryService;
    }

    /**
     * {@code POST  /sc-accesses} : Create a new scAccess.
     *
     * @param scAccessDTO the scAccessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scAccessDTO, or with status {@code 400 (Bad Request)} if the scAccess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-accesses")
    public ResponseEntity<ScAccessDTO> createScAccess(@Valid @RequestBody ScAccessDTO scAccessDTO) throws URISyntaxException {
        log.debug("REST request to save ScAccess : {}", scAccessDTO);
        if (scAccessDTO.getId() != null) {
            throw new BadRequestAlertException("A new scAccess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScAccessDTO result = scAccessService.save(scAccessDTO);
        return ResponseEntity.created(new URI("/api/sc-accesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-accesses} : Updates an existing scAccess.
     *
     * @param scAccessDTO the scAccessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scAccessDTO,
     * or with status {@code 400 (Bad Request)} if the scAccessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scAccessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-accesses")
    public ResponseEntity<ScAccessDTO> updateScAccess(@Valid @RequestBody ScAccessDTO scAccessDTO) throws URISyntaxException {
        log.debug("REST request to update ScAccess : {}", scAccessDTO);
        if (scAccessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScAccessDTO result = scAccessService.save(scAccessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scAccessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sc-accesses} : get all the scAccesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scAccesses in body.
     */
    @GetMapping("/sc-accesses")
    public ResponseEntity<List<ScAccessDTO>> getAllScAccesses(ScAccessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ScAccesses by criteria: {}", criteria);
        Page<ScAccessDTO> page = scAccessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sc-accesses/count} : count all the scAccesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sc-accesses/count")
    public ResponseEntity<Long> countScAccesses(ScAccessCriteria criteria) {
        log.debug("REST request to count ScAccesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(scAccessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sc-accesses/:id} : get the "id" scAccess.
     *
     * @param id the id of the scAccessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scAccessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-accesses/{id}")
    public ResponseEntity<ScAccessDTO> getScAccess(@PathVariable Long id) {
        log.debug("REST request to get ScAccess : {}", id);
        Optional<ScAccessDTO> scAccessDTO = scAccessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scAccessDTO);
    }

    /**
     * {@code DELETE  /sc-accesses/:id} : delete the "id" scAccess.
     *
     * @param id the id of the scAccessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-accesses/{id}")
    public ResponseEntity<Void> deleteScAccess(@PathVariable Long id) {
        log.debug("REST request to delete ScAccess : {}", id);
        scAccessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
