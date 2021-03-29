package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CVendorEvaluationQueryService;
import com.bhp.opusb.service.CVendorEvaluationService;
import com.bhp.opusb.service.dto.CVendorEvaluationCriteria;
import com.bhp.opusb.service.dto.CVendorEvaluationDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class CVendorEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(CVendorEvaluationResource.class);

    private static final String ENTITY_NAME = "cVendorEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorEvaluationService cVendorEvaluationService;

    private final CVendorEvaluationQueryService cVendorEvaluationQueryService;

    public CVendorEvaluationResource(CVendorEvaluationService cVendorEvaluationService, CVendorEvaluationQueryService cVendorEvaluationQueryService) {
        this.cVendorEvaluationService = cVendorEvaluationService;
        this.cVendorEvaluationQueryService = cVendorEvaluationQueryService;
    }

    /**
     * {@code POST  /c-vendor-evaluations} : Create a new cVendorEvaluation.
     *
     * @param cVendorEvaluationDTO the cVendorEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorEvaluationDTO, or with status {@code 400 (Bad Request)} if the cVendorEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-evaluations")
    public ResponseEntity<CVendorEvaluationDTO> createCVendorEvaluation(@Valid @RequestBody CVendorEvaluationDTO cVendorEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorEvaluation : {}", cVendorEvaluationDTO);
        if (cVendorEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorEvaluationDTO result = cVendorEvaluationService.save(cVendorEvaluationDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-evaluations} : Updates an existing cVendorEvaluation.
     *
     * @param cVendorEvaluationDTO the cVendorEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorEvaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-evaluations")
    public ResponseEntity<CVendorEvaluationDTO> updateCVendorEvaluation(@Valid @RequestBody CVendorEvaluationDTO cVendorEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorEvaluation : {}", cVendorEvaluationDTO);
        if (cVendorEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorEvaluationDTO result = cVendorEvaluationService.save(cVendorEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-evaluations} : get all the cVendorEvaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorEvaluations in body.
     */
    @GetMapping("/c-vendor-evaluations")
    public ResponseEntity<List<CVendorEvaluationDTO>> getAllCVendorEvaluations(CVendorEvaluationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorEvaluations by criteria: {}", criteria);
        Page<CVendorEvaluationDTO> page = cVendorEvaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-evaluations/count} : count all the cVendorEvaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-evaluations/count")
    public ResponseEntity<Long> countCVendorEvaluations(CVendorEvaluationCriteria criteria) {
        log.debug("REST request to count CVendorEvaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorEvaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-evaluations/:id} : get the "id" cVendorEvaluation.
     *
     * @param id the id of the cVendorEvaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorEvaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-evaluations/{id}")
    public ResponseEntity<CVendorEvaluationDTO> getCVendorEvaluation(@PathVariable Long id) {
        log.debug("REST request to get CVendorEvaluation : {}", id);
        Optional<CVendorEvaluationDTO> cVendorEvaluationDTO = cVendorEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorEvaluationDTO);
    }

    /**
     * {@code DELETE  /c-vendor-evaluations/:id} : delete the "id" cVendorEvaluation.
     *
     * @param id the id of the cVendorEvaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-evaluations/{id}")
    public ResponseEntity<Void> deleteCVendorEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete CVendorEvaluation : {}", id);
        cVendorEvaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
