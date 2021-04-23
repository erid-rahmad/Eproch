package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CEvalMethodSubCriteriaQueryService;
import com.bhp.opusb.service.CEvalMethodSubCriteriaService;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaCriteria;
import com.bhp.opusb.service.dto.CEvalMethodSubCriteriaDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.CEvalMethodSubCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CEvalMethodSubCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CEvalMethodSubCriteriaResource.class);

    private static final String ENTITY_NAME = "cEvalMethodSubCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEvalMethodSubCriteriaService cEvalMethodSubCriteriaService;

    private final CEvalMethodSubCriteriaQueryService cEvalMethodSubCriteriaQueryService;

    public CEvalMethodSubCriteriaResource(CEvalMethodSubCriteriaService cEvalMethodSubCriteriaService, CEvalMethodSubCriteriaQueryService cEvalMethodSubCriteriaQueryService) {
        this.cEvalMethodSubCriteriaService = cEvalMethodSubCriteriaService;
        this.cEvalMethodSubCriteriaQueryService = cEvalMethodSubCriteriaQueryService;
    }

    /**
     * {@code POST  /c-eval-method-sub-criteria} : Create a new cEvalMethodSubCriteria.
     *
     * @param cEvalMethodSubCriteriaDTO the cEvalMethodSubCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEvalMethodSubCriteriaDTO, or with status {@code 400 (Bad Request)} if the cEvalMethodSubCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-eval-method-sub-criteria")
    public ResponseEntity<CEvalMethodSubCriteriaDTO> createCEvalMethodSubCriteria(@Valid @RequestBody CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CEvalMethodSubCriteria : {}", cEvalMethodSubCriteriaDTO);
        if (cEvalMethodSubCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEvalMethodSubCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEvalMethodSubCriteriaDTO result = cEvalMethodSubCriteriaService.save(cEvalMethodSubCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-eval-method-sub-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-eval-method-sub-criteria} : Updates an existing cEvalMethodSubCriteria.
     *
     * @param cEvalMethodSubCriteriaDTO the cEvalMethodSubCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEvalMethodSubCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cEvalMethodSubCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEvalMethodSubCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-eval-method-sub-criteria")
    public ResponseEntity<CEvalMethodSubCriteriaDTO> updateCEvalMethodSubCriteria(@Valid @RequestBody CEvalMethodSubCriteriaDTO cEvalMethodSubCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CEvalMethodSubCriteria : {}", cEvalMethodSubCriteriaDTO);
        if (cEvalMethodSubCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEvalMethodSubCriteriaDTO result = cEvalMethodSubCriteriaService.save(cEvalMethodSubCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEvalMethodSubCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-eval-method-sub-criteria} : get all the cEvalMethodSubCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEvalMethodSubCriteria in body.
     */
    @GetMapping("/c-eval-method-sub-criteria")
    public ResponseEntity<List<CEvalMethodSubCriteriaDTO>> getAllCEvalMethodSubCriteria(CEvalMethodSubCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEvalMethodSubCriteria by criteria: {}", criteria);
        Page<CEvalMethodSubCriteriaDTO> page = cEvalMethodSubCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-eval-method-sub-criteria/count} : count all the cEvalMethodSubCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-eval-method-sub-criteria/count")
    public ResponseEntity<Long> countCEvalMethodSubCriteria(CEvalMethodSubCriteriaCriteria criteria) {
        log.debug("REST request to count CEvalMethodSubCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEvalMethodSubCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-eval-method-sub-criteria/:id} : get the "id" cEvalMethodSubCriteria.
     *
     * @param id the id of the cEvalMethodSubCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEvalMethodSubCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-eval-method-sub-criteria/{id}")
    public ResponseEntity<CEvalMethodSubCriteriaDTO> getCEvalMethodSubCriteria(@PathVariable Long id) {
        log.debug("REST request to get CEvalMethodSubCriteria : {}", id);
        Optional<CEvalMethodSubCriteriaDTO> cEvalMethodSubCriteriaDTO = cEvalMethodSubCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEvalMethodSubCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-eval-method-sub-criteria/:id} : delete the "id" cEvalMethodSubCriteria.
     *
     * @param id the id of the cEvalMethodSubCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-eval-method-sub-criteria/{id}")
    public ResponseEntity<Void> deleteCEvalMethodSubCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CEvalMethodSubCriteria : {}", id);
        cEvalMethodSubCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
