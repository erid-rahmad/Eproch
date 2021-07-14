package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalMethodSubCriteriaService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaDTO;
import com.bhp.opusb.service.dto.CPrequalMethodSubCriteriaCriteria;
import com.bhp.opusb.service.CPrequalMethodSubCriteriaQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalMethodSubCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalMethodSubCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodSubCriteriaResource.class);

    private static final String ENTITY_NAME = "cPrequalMethodSubCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalMethodSubCriteriaService cPrequalMethodSubCriteriaService;

    private final CPrequalMethodSubCriteriaQueryService cPrequalMethodSubCriteriaQueryService;

    public CPrequalMethodSubCriteriaResource(CPrequalMethodSubCriteriaService cPrequalMethodSubCriteriaService, CPrequalMethodSubCriteriaQueryService cPrequalMethodSubCriteriaQueryService) {
        this.cPrequalMethodSubCriteriaService = cPrequalMethodSubCriteriaService;
        this.cPrequalMethodSubCriteriaQueryService = cPrequalMethodSubCriteriaQueryService;
    }

    /**
     * {@code POST  /c-prequal-method-sub-criteria} : Create a new cPrequalMethodSubCriteria.
     *
     * @param cPrequalMethodSubCriteriaDTO the cPrequalMethodSubCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalMethodSubCriteriaDTO, or with status {@code 400 (Bad Request)} if the cPrequalMethodSubCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequal-method-sub-criteria")
    public ResponseEntity<CPrequalMethodSubCriteriaDTO> createCPrequalMethodSubCriteria(@Valid @RequestBody CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalMethodSubCriteria : {}", cPrequalMethodSubCriteriaDTO);
        if (cPrequalMethodSubCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalMethodSubCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalMethodSubCriteriaDTO result = cPrequalMethodSubCriteriaService.save(cPrequalMethodSubCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-prequal-method-sub-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequal-method-sub-criteria} : Updates an existing cPrequalMethodSubCriteria.
     *
     * @param cPrequalMethodSubCriteriaDTO the cPrequalMethodSubCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalMethodSubCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalMethodSubCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalMethodSubCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequal-method-sub-criteria")
    public ResponseEntity<CPrequalMethodSubCriteriaDTO> updateCPrequalMethodSubCriteria(@Valid @RequestBody CPrequalMethodSubCriteriaDTO cPrequalMethodSubCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalMethodSubCriteria : {}", cPrequalMethodSubCriteriaDTO);
        if (cPrequalMethodSubCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalMethodSubCriteriaDTO result = cPrequalMethodSubCriteriaService.save(cPrequalMethodSubCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalMethodSubCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequal-method-sub-criteria} : get all the cPrequalMethodSubCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalMethodSubCriteria in body.
     */
    @GetMapping("/c-prequal-method-sub-criteria")
    public ResponseEntity<List<CPrequalMethodSubCriteriaDTO>> getAllCPrequalMethodSubCriteria(CPrequalMethodSubCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalMethodSubCriteria by criteria: {}", criteria);
        Page<CPrequalMethodSubCriteriaDTO> page = cPrequalMethodSubCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequal-method-sub-criteria/count} : count all the cPrequalMethodSubCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequal-method-sub-criteria/count")
    public ResponseEntity<Long> countCPrequalMethodSubCriteria(CPrequalMethodSubCriteriaCriteria criteria) {
        log.debug("REST request to count CPrequalMethodSubCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalMethodSubCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequal-method-sub-criteria/:id} : get the "id" cPrequalMethodSubCriteria.
     *
     * @param id the id of the cPrequalMethodSubCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalMethodSubCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequal-method-sub-criteria/{id}")
    public ResponseEntity<CPrequalMethodSubCriteriaDTO> getCPrequalMethodSubCriteria(@PathVariable Long id) {
        log.debug("REST request to get CPrequalMethodSubCriteria : {}", id);
        Optional<CPrequalMethodSubCriteriaDTO> cPrequalMethodSubCriteriaDTO = cPrequalMethodSubCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalMethodSubCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-prequal-method-sub-criteria/:id} : delete the "id" cPrequalMethodSubCriteria.
     *
     * @param id the id of the cPrequalMethodSubCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequal-method-sub-criteria/{id}")
    public ResponseEntity<Void> deleteCPrequalMethodSubCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalMethodSubCriteria : {}", id);
        cPrequalMethodSubCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
