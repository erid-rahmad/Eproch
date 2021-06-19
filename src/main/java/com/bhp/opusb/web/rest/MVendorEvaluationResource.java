package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorEvaluationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorEvaluationDTO;
import com.bhp.opusb.service.dto.MVendorEvaluationCriteria;
import com.bhp.opusb.service.MVendorEvaluationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class MVendorEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(MVendorEvaluationResource.class);

    private static final String ENTITY_NAME = "mVendorEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorEvaluationService mVendorEvaluationService;

    private final MVendorEvaluationQueryService mVendorEvaluationQueryService;

    public MVendorEvaluationResource(MVendorEvaluationService mVendorEvaluationService, MVendorEvaluationQueryService mVendorEvaluationQueryService) {
        this.mVendorEvaluationService = mVendorEvaluationService;
        this.mVendorEvaluationQueryService = mVendorEvaluationQueryService;
    }

    /**
     * {@code POST  /m-vendor-evaluations} : Create a new mVendorEvaluation.
     *
     * @param mVendorEvaluationDTO the mVendorEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorEvaluationDTO, or with status {@code 400 (Bad Request)} if the mVendorEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-evaluations")
    public ResponseEntity<MVendorEvaluationDTO> createMVendorEvaluation(@Valid @RequestBody MVendorEvaluationDTO mVendorEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorEvaluation : {}", mVendorEvaluationDTO);
        if (mVendorEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorEvaluationDTO result = mVendorEvaluationService.save(mVendorEvaluationDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-evaluations} : Updates an existing mVendorEvaluation.
     *
     * @param mVendorEvaluationDTO the mVendorEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorEvaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-evaluations")
    public ResponseEntity<MVendorEvaluationDTO> updateMVendorEvaluation(@Valid @RequestBody MVendorEvaluationDTO mVendorEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorEvaluation : {}", mVendorEvaluationDTO);
        if (mVendorEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorEvaluationDTO result = mVendorEvaluationService.save(mVendorEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-evaluations} : get all the mVendorEvaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorEvaluations in body.
     */
    @GetMapping("/m-vendor-evaluations")
    public ResponseEntity<List<MVendorEvaluationDTO>> getAllMVendorEvaluations(MVendorEvaluationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorEvaluations by criteria: {}", criteria);
        Page<MVendorEvaluationDTO> page = mVendorEvaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-evaluations/count} : count all the mVendorEvaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-evaluations/count")
    public ResponseEntity<Long> countMVendorEvaluations(MVendorEvaluationCriteria criteria) {
        log.debug("REST request to count MVendorEvaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorEvaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-evaluations/:id} : get the "id" mVendorEvaluation.
     *
     * @param id the id of the mVendorEvaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorEvaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-evaluations/{id}")
    public ResponseEntity<MVendorEvaluationDTO> getMVendorEvaluation(@PathVariable Long id) {
        log.debug("REST request to get MVendorEvaluation : {}", id);
        Optional<MVendorEvaluationDTO> mVendorEvaluationDTO = mVendorEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorEvaluationDTO);
    }

    /**
     * {@code DELETE  /m-vendor-evaluations/:id} : delete the "id" mVendorEvaluation.
     *
     * @param id the id of the mVendorEvaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-evaluations/{id}")
    public ResponseEntity<Void> deleteMVendorEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete MVendorEvaluation : {}", id);
        mVendorEvaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
