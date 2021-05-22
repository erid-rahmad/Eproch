package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorScoringCriteriaService;
import com.bhp.opusb.service.MVendorScoringService;
import com.bhp.opusb.service.dto.*;
import com.bhp.opusb.util.MapperJSONUtil;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.MVendorScoringCriteriaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorScoringCriteria}.
 */
@RestController
@RequestMapping("/api")
public class MVendorScoringCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringCriteriaResource.class);

    private static final String ENTITY_NAME = "mVendorScoringCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorScoringCriteriaService mVendorScoringCriteriaService;

    private final MVendorScoringCriteriaQueryService mVendorScoringCriteriaQueryService;

    public MVendorScoringCriteriaResource(MVendorScoringCriteriaService mVendorScoringCriteriaService, MVendorScoringCriteriaQueryService mVendorScoringCriteriaQueryService) {
        this.mVendorScoringCriteriaService = mVendorScoringCriteriaService;
        this.mVendorScoringCriteriaQueryService = mVendorScoringCriteriaQueryService;
    }

    @Autowired
    MVendorScoringService mVendorScoringService;

    /**
     * {@code POST  /m-vendor-scoring-criteria} : Create a new mVendorScoringCriteria.
     *
     * @param mVendorScoringCriteriaDTO the mVendorScoringCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorScoringCriteriaDTO, or with status {@code 400 (Bad Request)} if the mVendorScoringCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-scoring-criteria")
    public ResponseEntity<MVendorScoringCriteriaDTO> createMVendorScoringCriteria(@Valid @RequestBody MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorScoringCriteria : {}", mVendorScoringCriteriaDTO);
        if (mVendorScoringCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorScoringCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorScoringCriteriaDTO result = mVendorScoringCriteriaService.save(mVendorScoringCriteriaDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-scoring-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/m-vendor-scoring-criteria-answer")
    @Transactional
    public ResponseEntity<MVendorScoringNestedDTO> createMVendorScoringCriteria(@Valid @RequestBody MVendorScoringNestedDTO answer) throws URISyntaxException {
//        log.debug("REST request to save MVendorScoringCriteria : {}", MapperJSONUtil.prettyLog(answer));
        List<CEvaluationMethodCriteriaDTO> result2 =mVendorScoringCriteriaService.vendorScoringAnswer(answer.getEvaluationMethodCriteriaNested(),answer.getEvaluationMethodLineId());
        log.info("ok {}",result2);
        return ResponseEntity.ok(answer);
    }

    /**
     * {@code PUT  /m-vendor-scoring-criteria} : Updates an existing mVendorScoringCriteria.
     *
     * @param mVendorScoringCriteriaDTO the mVendorScoringCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorScoringCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorScoringCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorScoringCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-scoring-criteria")
    public ResponseEntity<MVendorScoringCriteriaDTO> updateMVendorScoringCriteria(@Valid @RequestBody MVendorScoringCriteriaDTO mVendorScoringCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorScoringCriteria : {}", mVendorScoringCriteriaDTO);
        if (mVendorScoringCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorScoringCriteriaDTO result = mVendorScoringCriteriaService.save(mVendorScoringCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorScoringCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-scoring-criteria} : get all the mVendorScoringCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorScoringCriteria in body.
     */
    @GetMapping("/m-vendor-scoring-criteria")
    public ResponseEntity<List<MVendorScoringCriteriaDTO>> getAllMVendorScoringCriteria(MVendorScoringCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorScoringCriteria by criteria: {}", criteria);
        Page<MVendorScoringCriteriaDTO> page = mVendorScoringCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-scoring-criteria/count} : count all the mVendorScoringCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-scoring-criteria/count")
    public ResponseEntity<Long> countMVendorScoringCriteria(MVendorScoringCriteriaCriteria criteria) {
        log.debug("REST request to count MVendorScoringCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorScoringCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-scoring-criteria/:id} : get the "id" mVendorScoringCriteria.
     *
     * @param id the id of the mVendorScoringCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorScoringCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-scoring-criteria/{id}")
    public ResponseEntity<MVendorScoringCriteriaDTO> getMVendorScoringCriteria(@PathVariable Long id) {
        log.debug("REST request to get MVendorScoringCriteria : {}", id);
        Optional<MVendorScoringCriteriaDTO> mVendorScoringCriteriaDTO = mVendorScoringCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorScoringCriteriaDTO);
    }

    /**
     * {@code DELETE  /m-vendor-scoring-criteria/:id} : delete the "id" mVendorScoringCriteria.
     *
     * @param id the id of the mVendorScoringCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-scoring-criteria/{id}")
    public ResponseEntity<Void> deleteMVendorScoringCriteria(@PathVariable Long id) {
        log.debug("REST request to delete MVendorScoringCriteria : {}", id);
        mVendorScoringCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
