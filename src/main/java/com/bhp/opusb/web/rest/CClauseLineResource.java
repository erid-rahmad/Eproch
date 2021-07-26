package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CClauseLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CClauseLineDTO;
import com.bhp.opusb.service.dto.CClauseLineCriteria;
import com.bhp.opusb.service.CClauseLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CClauseLine}.
 */
@RestController
@RequestMapping("/api")
public class CClauseLineResource {

    private final Logger log = LoggerFactory.getLogger(CClauseLineResource.class);

    private static final String ENTITY_NAME = "cClauseLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CClauseLineService cClauseLineService;

    private final CClauseLineQueryService cClauseLineQueryService;

    public CClauseLineResource(CClauseLineService cClauseLineService, CClauseLineQueryService cClauseLineQueryService) {
        this.cClauseLineService = cClauseLineService;
        this.cClauseLineQueryService = cClauseLineQueryService;
    }

    /**
     * {@code POST  /c-clause-lines} : Create a new cClauseLine.
     *
     * @param cClauseLineDTO the cClauseLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cClauseLineDTO, or with status {@code 400 (Bad Request)} if the cClauseLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-clause-lines")
    public ResponseEntity<CClauseLineDTO> createCClauseLine(@Valid @RequestBody CClauseLineDTO cClauseLineDTO) throws URISyntaxException {
        log.debug("REST request to save CClauseLine : {}", cClauseLineDTO);
        if (cClauseLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cClauseLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CClauseLineDTO result = cClauseLineService.save(cClauseLineDTO);
        return ResponseEntity.created(new URI("/api/c-clause-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-clause-lines} : Updates an existing cClauseLine.
     *
     * @param cClauseLineDTO the cClauseLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cClauseLineDTO,
     * or with status {@code 400 (Bad Request)} if the cClauseLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cClauseLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-clause-lines")
    public ResponseEntity<CClauseLineDTO> updateCClauseLine(@Valid @RequestBody CClauseLineDTO cClauseLineDTO) throws URISyntaxException {
        log.debug("REST request to update CClauseLine : {}", cClauseLineDTO);
        if (cClauseLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CClauseLineDTO result = cClauseLineService.save(cClauseLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cClauseLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-clause-lines} : get all the cClauseLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cClauseLines in body.
     */
    @GetMapping("/c-clause-lines")
    public ResponseEntity<List<CClauseLineDTO>> getAllCClauseLines(CClauseLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CClauseLines by criteria: {}", criteria);
        Page<CClauseLineDTO> page = cClauseLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-clause-lines/count} : count all the cClauseLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-clause-lines/count")
    public ResponseEntity<Long> countCClauseLines(CClauseLineCriteria criteria) {
        log.debug("REST request to count CClauseLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cClauseLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-clause-lines/:id} : get the "id" cClauseLine.
     *
     * @param id the id of the cClauseLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cClauseLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-clause-lines/{id}")
    public ResponseEntity<CClauseLineDTO> getCClauseLine(@PathVariable Long id) {
        log.debug("REST request to get CClauseLine : {}", id);
        Optional<CClauseLineDTO> cClauseLineDTO = cClauseLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cClauseLineDTO);
    }

    /**
     * {@code DELETE  /c-clause-lines/:id} : delete the "id" cClauseLine.
     *
     * @param id the id of the cClauseLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-clause-lines/{id}")
    public ResponseEntity<Void> deleteCClauseLine(@PathVariable Long id) {
        log.debug("REST request to delete CClauseLine : {}", id);
        cClauseLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
