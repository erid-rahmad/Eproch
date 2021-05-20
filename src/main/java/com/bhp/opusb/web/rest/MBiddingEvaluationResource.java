package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingEvaluationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingEvaluationDTO;
import com.bhp.opusb.service.dto.MBiddingEvaluationCriteria;
import com.bhp.opusb.service.MBiddingEvaluationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationResource.class);

    private static final String ENTITY_NAME = "mBiddingEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingEvaluationService mBiddingEvaluationService;

    private final MBiddingEvaluationQueryService mBiddingEvaluationQueryService;

    public MBiddingEvaluationResource(MBiddingEvaluationService mBiddingEvaluationService, MBiddingEvaluationQueryService mBiddingEvaluationQueryService) {
        this.mBiddingEvaluationService = mBiddingEvaluationService;
        this.mBiddingEvaluationQueryService = mBiddingEvaluationQueryService;
    }

    /**
     * {@code POST  /m-bidding-evaluations} : Create a new mBiddingEvaluation.
     *
     * @param mBiddingEvaluationDTO the mBiddingEvaluationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingEvaluationDTO, or with status {@code 400 (Bad Request)} if the mBiddingEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-evaluations")
    public ResponseEntity<MBiddingEvaluationDTO> createMBiddingEvaluation(@Valid @RequestBody MBiddingEvaluationDTO mBiddingEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingEvaluation : {}", mBiddingEvaluationDTO);
        if (mBiddingEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingEvaluationDTO result = mBiddingEvaluationService.save(mBiddingEvaluationDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-evaluations} : Updates an existing mBiddingEvaluation.
     *
     * @param mBiddingEvaluationDTO the mBiddingEvaluationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingEvaluationDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingEvaluationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingEvaluationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-evaluations")
    public ResponseEntity<MBiddingEvaluationDTO> updateMBiddingEvaluation(@Valid @RequestBody MBiddingEvaluationDTO mBiddingEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingEvaluation : {}", mBiddingEvaluationDTO);
        if (mBiddingEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingEvaluationDTO result = mBiddingEvaluationService.save(mBiddingEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-evaluations} : get all the mBiddingEvaluations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingEvaluations in body.
     */
    @GetMapping("/m-bidding-evaluations")
    public ResponseEntity<List<MBiddingEvaluationDTO>> getAllMBiddingEvaluations(MBiddingEvaluationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingEvaluations by criteria: {}", criteria);
        Page<MBiddingEvaluationDTO> page = mBiddingEvaluationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-evaluations/count} : count all the mBiddingEvaluations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-evaluations/count")
    public ResponseEntity<Long> countMBiddingEvaluations(MBiddingEvaluationCriteria criteria) {
        log.debug("REST request to count MBiddingEvaluations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingEvaluationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-evaluations/:id} : get the "id" mBiddingEvaluation.
     *
     * @param id the id of the mBiddingEvaluationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingEvaluationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-evaluations/{id}")
    public ResponseEntity<MBiddingEvaluationDTO> getMBiddingEvaluation(@PathVariable Long id) {
        log.debug("REST request to get MBiddingEvaluation : {}", id);
        Optional<MBiddingEvaluationDTO> mBiddingEvaluationDTO = mBiddingEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingEvaluationDTO);
    }

    /**
     * {@code DELETE  /m-bidding-evaluations/:id} : delete the "id" mBiddingEvaluation.
     *
     * @param id the id of the mBiddingEvaluationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-evaluations/{id}")
    public ResponseEntity<Void> deleteMBiddingEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingEvaluation : {}", id);
        mBiddingEvaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
