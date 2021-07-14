package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalMethodCriteriaService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaDTO;
import com.bhp.opusb.service.dto.CPrequalMethodCriteriaCriteria;
import com.bhp.opusb.service.CPrequalMethodCriteriaQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalMethodCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalMethodCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodCriteriaResource.class);

    private static final String ENTITY_NAME = "cPrequalMethodCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalMethodCriteriaService cPrequalMethodCriteriaService;

    private final CPrequalMethodCriteriaQueryService cPrequalMethodCriteriaQueryService;

    public CPrequalMethodCriteriaResource(CPrequalMethodCriteriaService cPrequalMethodCriteriaService, CPrequalMethodCriteriaQueryService cPrequalMethodCriteriaQueryService) {
        this.cPrequalMethodCriteriaService = cPrequalMethodCriteriaService;
        this.cPrequalMethodCriteriaQueryService = cPrequalMethodCriteriaQueryService;
    }

    /**
     * {@code POST  /c-prequal-method-criteria} : Create a new cPrequalMethodCriteria.
     *
     * @param cPrequalMethodCriteriaDTO the cPrequalMethodCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalMethodCriteriaDTO, or with status {@code 400 (Bad Request)} if the cPrequalMethodCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequal-method-criteria")
    public ResponseEntity<CPrequalMethodCriteriaDTO> createCPrequalMethodCriteria(@Valid @RequestBody CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalMethodCriteria : {}", cPrequalMethodCriteriaDTO);
        if (cPrequalMethodCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalMethodCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalMethodCriteriaDTO result = cPrequalMethodCriteriaService.save(cPrequalMethodCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-prequal-method-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequal-method-criteria} : Updates an existing cPrequalMethodCriteria.
     *
     * @param cPrequalMethodCriteriaDTO the cPrequalMethodCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalMethodCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalMethodCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalMethodCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequal-method-criteria")
    public ResponseEntity<CPrequalMethodCriteriaDTO> updateCPrequalMethodCriteria(@Valid @RequestBody CPrequalMethodCriteriaDTO cPrequalMethodCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalMethodCriteria : {}", cPrequalMethodCriteriaDTO);
        if (cPrequalMethodCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalMethodCriteriaDTO result = cPrequalMethodCriteriaService.save(cPrequalMethodCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalMethodCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequal-method-criteria} : get all the cPrequalMethodCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalMethodCriteria in body.
     */
    @GetMapping("/c-prequal-method-criteria")
    public ResponseEntity<List<CPrequalMethodCriteriaDTO>> getAllCPrequalMethodCriteria(CPrequalMethodCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalMethodCriteria by criteria: {}", criteria);
        Page<CPrequalMethodCriteriaDTO> page = cPrequalMethodCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequal-method-criteria/count} : count all the cPrequalMethodCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequal-method-criteria/count")
    public ResponseEntity<Long> countCPrequalMethodCriteria(CPrequalMethodCriteriaCriteria criteria) {
        log.debug("REST request to count CPrequalMethodCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalMethodCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequal-method-criteria/:id} : get the "id" cPrequalMethodCriteria.
     *
     * @param id the id of the cPrequalMethodCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalMethodCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequal-method-criteria/{id}")
    public ResponseEntity<CPrequalMethodCriteriaDTO> getCPrequalMethodCriteria(@PathVariable Long id) {
        log.debug("REST request to get CPrequalMethodCriteria : {}", id);
        Optional<CPrequalMethodCriteriaDTO> cPrequalMethodCriteriaDTO = cPrequalMethodCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalMethodCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-prequal-method-criteria/:id} : delete the "id" cPrequalMethodCriteria.
     *
     * @param id the id of the cPrequalMethodCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequal-method-criteria/{id}")
    public ResponseEntity<Void> deleteCPrequalMethodCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalMethodCriteria : {}", id);
        cPrequalMethodCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
