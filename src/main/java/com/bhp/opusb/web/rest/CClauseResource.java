package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CClauseService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CClauseDTO;
import com.bhp.opusb.service.dto.CClauseCriteria;
import com.bhp.opusb.service.CClauseQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CClause}.
 */
@RestController
@RequestMapping("/api")
public class CClauseResource {

    private final Logger log = LoggerFactory.getLogger(CClauseResource.class);

    private static final String ENTITY_NAME = "cClause";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CClauseService cClauseService;

    private final CClauseQueryService cClauseQueryService;

    public CClauseResource(CClauseService cClauseService, CClauseQueryService cClauseQueryService) {
        this.cClauseService = cClauseService;
        this.cClauseQueryService = cClauseQueryService;
    }

    /**
     * {@code POST  /c-clauses} : Create a new cClause.
     *
     * @param cClauseDTO the cClauseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cClauseDTO, or with status {@code 400 (Bad Request)} if the cClause has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-clauses")
    public ResponseEntity<CClauseDTO> createCClause(@Valid @RequestBody CClauseDTO cClauseDTO) throws URISyntaxException {
        log.debug("REST request to save CClause : {}", cClauseDTO);
        if (cClauseDTO.getId() != null) {
            throw new BadRequestAlertException("A new cClause cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CClauseDTO result = cClauseService.save(cClauseDTO);
        return ResponseEntity.created(new URI("/api/c-clauses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-clauses} : Updates an existing cClause.
     *
     * @param cClauseDTO the cClauseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cClauseDTO,
     * or with status {@code 400 (Bad Request)} if the cClauseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cClauseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-clauses")
    public ResponseEntity<CClauseDTO> updateCClause(@Valid @RequestBody CClauseDTO cClauseDTO) throws URISyntaxException {
        log.debug("REST request to update CClause : {}", cClauseDTO);
        if (cClauseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CClauseDTO result = cClauseService.save(cClauseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cClauseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-clauses} : get all the cClauses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cClauses in body.
     */
    @GetMapping("/c-clauses")
    public ResponseEntity<List<CClauseDTO>> getAllCClauses(CClauseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CClauses by criteria: {}", criteria);
        Page<CClauseDTO> page = cClauseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-clauses/count} : count all the cClauses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-clauses/count")
    public ResponseEntity<Long> countCClauses(CClauseCriteria criteria) {
        log.debug("REST request to count CClauses by criteria: {}", criteria);
        return ResponseEntity.ok().body(cClauseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-clauses/:id} : get the "id" cClause.
     *
     * @param id the id of the cClauseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cClauseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-clauses/{id}")
    public ResponseEntity<CClauseDTO> getCClause(@PathVariable Long id) {
        log.debug("REST request to get CClause : {}", id);
        Optional<CClauseDTO> cClauseDTO = cClauseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cClauseDTO);
    }

    /**
     * {@code DELETE  /c-clauses/:id} : delete the "id" cClause.
     *
     * @param id the id of the cClauseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-clauses/{id}")
    public ResponseEntity<Void> deleteCClause(@PathVariable Long id) {
        log.debug("REST request to delete CClause : {}", id);
        cClauseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
