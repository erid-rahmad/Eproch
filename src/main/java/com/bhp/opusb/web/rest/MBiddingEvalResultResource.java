package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingEvalResultService;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;
import com.bhp.opusb.service.dto.MBiddingEvalResultCriteria;
import com.bhp.opusb.service.MBiddingEvalResultQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingEvalResult}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingEvalResultResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultResource.class);

    private static final String ENTITY_NAME = "mBiddingEvalResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingEvalResultService mBiddingEvalResultService;

    private final MBiddingEvalResultQueryService mBiddingEvalResultQueryService;

    public MBiddingEvalResultResource(MBiddingEvalResultService mBiddingEvalResultService, MBiddingEvalResultQueryService mBiddingEvalResultQueryService) {
        this.mBiddingEvalResultService = mBiddingEvalResultService;
        this.mBiddingEvalResultQueryService = mBiddingEvalResultQueryService;
    }

    /**
     * {@code POST  /m-bidding-eval-results} : Create a new mBiddingEvalResult.
     *
     * @param mBiddingEvalResultDTO the mBiddingEvalResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingEvalResultDTO, or with status {@code 400 (Bad Request)} if the mBiddingEvalResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-eval-results")
    public ResponseEntity<MBiddingEvalResultDTO> createMBiddingEvalResult(@Valid @RequestBody MBiddingEvalResultDTO mBiddingEvalResultDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingEvalResult : {}", mBiddingEvalResultDTO);
        if (mBiddingEvalResultDTO.getId() != null) {
            return updateMBiddingEvalResult(mBiddingEvalResultDTO);
        }
        MBiddingEvalResultDTO result = mBiddingEvalResultService.save(mBiddingEvalResultDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-eval-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-eval-results} : Updates an existing mBiddingEvalResult.
     *
     * @param mBiddingEvalResultDTO the mBiddingEvalResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingEvalResultDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingEvalResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingEvalResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-eval-results")
    public ResponseEntity<MBiddingEvalResultDTO> updateMBiddingEvalResult(@Valid @RequestBody MBiddingEvalResultDTO mBiddingEvalResultDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingEvalResult : {}", mBiddingEvalResultDTO);
        if (mBiddingEvalResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingEvalResultDTO result = mBiddingEvalResultService.save(mBiddingEvalResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingEvalResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-eval-results} : get all the mBiddingEvalResults.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingEvalResults in body.
     */
    @GetMapping("/m-bidding-eval-results")
    public ResponseEntity<List<MBiddingEvalResultDTO>> getAllMBiddingEvalResults(MBiddingEvalResultCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingEvalResults by criteria: {}", criteria);
        Page<MBiddingEvalResultDTO> page = mBiddingEvalResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-bidding-eval-results/grid")
    public ResponseEntity<List<MBiddingDTO>> grid() {
        return ResponseEntity.ok().body(mBiddingEvalResultService.getGrid());
    }

    /**
     * {@code GET  /m-bidding-eval-results/count} : count all the mBiddingEvalResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-eval-results/count")
    public ResponseEntity<Long> countMBiddingEvalResults(MBiddingEvalResultCriteria criteria) {
        log.debug("REST request to count MBiddingEvalResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingEvalResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-eval-results/:id} : get the "id" mBiddingEvalResult.
     *
     * @param id the id of the mBiddingEvalResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingEvalResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-eval-results/{id}")
    public ResponseEntity<MBiddingEvalResultDTO> getMBiddingEvalResult(@PathVariable Long id) {
        log.debug("REST request to get MBiddingEvalResult : {}", id);
        Optional<MBiddingEvalResultDTO> mBiddingEvalResultDTO = mBiddingEvalResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingEvalResultDTO);
    }

    /**
     * {@code DELETE  /m-bidding-eval-results/:id} : delete the "id" mBiddingEvalResult.
     *
     * @param id the id of the mBiddingEvalResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-eval-results/{id}")
    public ResponseEntity<Void> deleteMBiddingEvalResult(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingEvalResult : {}", id);
        mBiddingEvalResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
