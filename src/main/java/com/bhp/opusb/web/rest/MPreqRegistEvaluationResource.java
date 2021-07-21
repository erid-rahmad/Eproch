package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPreqRegistEvaluationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationDTO;
import com.bhp.opusb.service.dto.MPreqRegistEvaluationCriteria;
import com.bhp.opusb.service.MPreqRegistEvaluationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPreqRegistEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class MPreqRegistEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(MPreqRegistEvaluationResource.class);

    private static final String ENTITY_NAME = "mPreqRegistEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPreqRegistEvaluationService mPreqRegistEvaluationService;

    private final MPreqRegistEvaluationQueryService mPreqRegistEvaluationQueryService;

    public MPreqRegistEvaluationResource(MPreqRegistEvaluationService mPreqRegistEvaluationService, MPreqRegistEvaluationQueryService mPreqRegistEvaluationQueryService) {
        this.mPreqRegistEvaluationService = mPreqRegistEvaluationService;
        this.mPreqRegistEvaluationQueryService = mPreqRegistEvaluationQueryService;
    }

    /**
     * {@code POST  /m-preq-regist-evaluations} : Create a new mPreqRegistEvaluation.
     *
     * @param mPreqRegistEvaluationDTO the mPreqRegistEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPreqRegistEvaluationDTO, or with status {@code 400 (Bad Request)} if the mPreqRegistEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-preq-regist-evaluations")
    public ResponseEntity<MPreqRegistEvaluationDTO> createMPreqRegistEvaluation(@Valid @RequestBody MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save MPreqRegistEvaluation : {}", mPreqRegistEvaluationDTO);
        if (mPreqRegistEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPreqRegistEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPreqRegistEvaluationDTO result = mPreqRegistEvaluationService.save(mPreqRegistEvaluationDTO);
        return ResponseEntity.created(new URI("/api/m-preq-regist-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-preq-regist-evaluations} : Create a new mPreqRegistEvaluation.
     *
     * @param mPreqRegistEvaluationDTO the mPreqRegistEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPreqRegistEvaluationDTO, or with status {@code 400 (Bad Request)} if the mPreqRegistEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-preq-regist-evaluations/submit")
    public ResponseEntity<List<MPreqRegistEvaluationDTO>> processEvaluations(
            @Valid @RequestBody List<MPreqRegistEvaluationDTO> dtos) throws URISyntaxException {
        log.debug("REST request to save MPreqRegistEvaluations : {}", dtos);
        if (dtos.size() == 0) {
            throw new BadRequestAlertException("No mPreqRegistEvaluation to process", ENTITY_NAME, "nodata");
        }
        List<MPreqRegistEvaluationDTO> result = mPreqRegistEvaluationService.processAll(dtos);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-preq-regist-evaluations} : Updates an existing mPreqRegistEvaluation.
     *
     * @param mPreqRegistEvaluationDTO the mPreqRegistEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPreqRegistEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the mPreqRegistEvaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPreqRegistEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-preq-regist-evaluations")
    public ResponseEntity<MPreqRegistEvaluationDTO> updateMPreqRegistEvaluation(@Valid @RequestBody MPreqRegistEvaluationDTO mPreqRegistEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update MPreqRegistEvaluation : {}", mPreqRegistEvaluationDTO);
        if (mPreqRegistEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPreqRegistEvaluationDTO result = mPreqRegistEvaluationService.save(mPreqRegistEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPreqRegistEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-preq-regist-evaluations} : get all the mPreqRegistEvaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPreqRegistEvaluations in body.
     */
    @GetMapping("/m-preq-regist-evaluations")
    public ResponseEntity<List<MPreqRegistEvaluationDTO>> getAllMPreqRegistEvaluations(MPreqRegistEvaluationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPreqRegistEvaluations by criteria: {}", criteria);
        Page<MPreqRegistEvaluationDTO> page = mPreqRegistEvaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-preq-regist-evaluations/count} : count all the mPreqRegistEvaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-preq-regist-evaluations/count")
    public ResponseEntity<Long> countMPreqRegistEvaluations(MPreqRegistEvaluationCriteria criteria) {
        log.debug("REST request to count MPreqRegistEvaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPreqRegistEvaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-preq-regist-evaluations/:id} : get the "id" mPreqRegistEvaluation.
     *
     * @param id the id of the mPreqRegistEvaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPreqRegistEvaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-preq-regist-evaluations/{id}")
    public ResponseEntity<MPreqRegistEvaluationDTO> getMPreqRegistEvaluation(@PathVariable Long id) {
        log.debug("REST request to get MPreqRegistEvaluation : {}", id);
        Optional<MPreqRegistEvaluationDTO> mPreqRegistEvaluationDTO = mPreqRegistEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPreqRegistEvaluationDTO);
    }

    /**
     * {@code DELETE  /m-preq-regist-evaluations/:id} : delete the "id" mPreqRegistEvaluation.
     *
     * @param id the id of the mPreqRegistEvaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-preq-regist-evaluations/{id}")
    public ResponseEntity<Void> deleteMPreqRegistEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete MPreqRegistEvaluation : {}", id);
        mPreqRegistEvaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
