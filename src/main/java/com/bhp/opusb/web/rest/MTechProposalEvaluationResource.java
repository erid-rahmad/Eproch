package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MTechProposalEvaluationService;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaDTO;
import com.bhp.opusb.util.MapperJSONUtil;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MTechProposalEvaluationDTO;
import com.bhp.opusb.service.dto.MTechProposalEvaluationCriteria;
import com.bhp.opusb.service.MTechProposalEvaluationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MTechProposalEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class MTechProposalEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(MTechProposalEvaluationResource.class);

    private static final String ENTITY_NAME = "mTechProposalEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MTechProposalEvaluationService mTechProposalEvaluationService;

    private final MTechProposalEvaluationQueryService mTechProposalEvaluationQueryService;

    public MTechProposalEvaluationResource(MTechProposalEvaluationService mTechProposalEvaluationService, MTechProposalEvaluationQueryService mTechProposalEvaluationQueryService) {
        this.mTechProposalEvaluationService = mTechProposalEvaluationService;
        this.mTechProposalEvaluationQueryService = mTechProposalEvaluationQueryService;
    }

    /**
     * {@code POST  /m-tech-proposal-evaluations} : Create a new mTechProposalEvaluation.
     *
     * @param mTechProposalEvaluationDTO the mTechProposalEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mTechProposalEvaluationDTO, or with status {@code 400 (Bad Request)} if the mTechProposalEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-tech-proposal-evaluations")
    public ResponseEntity<MTechProposalEvaluationDTO> createMTechProposalEvaluation(@Valid @RequestBody MTechProposalEvaluationDTO mTechProposalEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save MTechProposalEvaluation : {}", mTechProposalEvaluationDTO);
        if (mTechProposalEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mTechProposalEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MTechProposalEvaluationDTO result = mTechProposalEvaluationService.save(mTechProposalEvaluationDTO);
        return ResponseEntity.created(new URI("/api/m-tech-proposal-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/m-tech-proposal-evaluations/evaluation")
    public ResponseEntity<List<MTechProposalEvaluationDTO>> postMTechProposalEvaluation(@Valid @RequestBody List<MTechProposalEvaluationDTO> cEvaluationMethodCriteriaDTOS) throws URISyntaxException {
       mTechProposalEvaluationService.evaluation(cEvaluationMethodCriteriaDTOS);
        log.info(MapperJSONUtil.prettyLog(cEvaluationMethodCriteriaDTOS));
        return ResponseEntity.ok(cEvaluationMethodCriteriaDTOS);
    }

    /**
     * {@code PUT  /m-tech-proposal-evaluations} : Updates an existing mTechProposalEvaluation.
     *
     * @param mTechProposalEvaluationDTO the mTechProposalEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mTechProposalEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the mTechProposalEvaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mTechProposalEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-tech-proposal-evaluations")
    public ResponseEntity<MTechProposalEvaluationDTO> updateMTechProposalEvaluation(@Valid @RequestBody MTechProposalEvaluationDTO mTechProposalEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update MTechProposalEvaluation : {}", mTechProposalEvaluationDTO);
        if (mTechProposalEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MTechProposalEvaluationDTO result = mTechProposalEvaluationService.save(mTechProposalEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mTechProposalEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-tech-proposal-evaluations} : get all the mTechProposalEvaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mTechProposalEvaluations in body.
     */
    @GetMapping("/m-tech-proposal-evaluations")
    public ResponseEntity<List<MTechProposalEvaluationDTO>> getAllMTechProposalEvaluations(MTechProposalEvaluationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MTechProposalEvaluations by criteria: {}", criteria);
        Page<MTechProposalEvaluationDTO> page = mTechProposalEvaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-tech-proposal-evaluations/count} : count all the mTechProposalEvaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-tech-proposal-evaluations/count")
    public ResponseEntity<Long> countMTechProposalEvaluations(MTechProposalEvaluationCriteria criteria) {
        log.debug("REST request to count MTechProposalEvaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mTechProposalEvaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-tech-proposal-evaluations/:id} : get the "id" mTechProposalEvaluation.
     *
     * @param id the id of the mTechProposalEvaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mTechProposalEvaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-tech-proposal-evaluations/{id}")
    public ResponseEntity<MTechProposalEvaluationDTO> getMTechProposalEvaluation(@PathVariable Long id) {
        log.debug("REST request to get MTechProposalEvaluation : {}", id);
        Optional<MTechProposalEvaluationDTO> mTechProposalEvaluationDTO = mTechProposalEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mTechProposalEvaluationDTO);
    }

    /**
     * {@code DELETE  /m-tech-proposal-evaluations/:id} : delete the "id" mTechProposalEvaluation.
     *
     * @param id the id of the mTechProposalEvaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-tech-proposal-evaluations/{id}")
    public ResponseEntity<Void> deleteMTechProposalEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete MTechProposalEvaluation : {}", id);
        mTechProposalEvaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
