package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingResultService;
import com.bhp.opusb.service.dto.MBiddingResultPublish;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingResultDTO;
import com.bhp.opusb.service.dto.MBiddingResultCriteria;
import com.bhp.opusb.service.MBiddingResultQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingResult}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingResultResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingResultResource.class);

    private static final String ENTITY_NAME = "mBiddingResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingResultService mBiddingResultService;

    private final MBiddingResultQueryService mBiddingResultQueryService;

    public MBiddingResultResource(MBiddingResultService mBiddingResultService, MBiddingResultQueryService mBiddingResultQueryService) {
        this.mBiddingResultService = mBiddingResultService;
        this.mBiddingResultQueryService = mBiddingResultQueryService;
    }

    /**
     * {@code POST  /m-bidding-results} : Create a new mBiddingResult.
     *
     * @param mBiddingResultDTO the mBiddingResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingResultDTO, or with status {@code 400 (Bad Request)} if the mBiddingResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-results")
    public ResponseEntity<MBiddingResultDTO> createMBiddingResult(@Valid @RequestBody MBiddingResultDTO mBiddingResultDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingResult : {}", mBiddingResultDTO);
        if (mBiddingResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingResultDTO result = mBiddingResultService.save(mBiddingResultDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/m-bidding-results/publish")
    public ResponseEntity<MBiddingResultPublish> publishMBiddingResult(@Valid @RequestBody MBiddingResultPublish mBiddingResultPublish) throws URISyntaxException {
        log.debug("this mBiddingResultPublish {}",mBiddingResultPublish);
        mBiddingResultService.publish(mBiddingResultPublish);
       return ResponseEntity.ok(mBiddingResultPublish);
    }

    /**
     * {@code PUT  /m-bidding-results} : Updates an existing mBiddingResult.
     *
     * @param mBiddingResultDTO the mBiddingResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingResultDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-results")
    public ResponseEntity<MBiddingResultDTO> updateMBiddingResult(@Valid @RequestBody MBiddingResultDTO mBiddingResultDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingResult : {}", mBiddingResultDTO);
        if (mBiddingResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingResultDTO result = mBiddingResultService.save(mBiddingResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-results} : get all the mBiddingResults.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingResults in body.
     */
    @GetMapping("/m-bidding-results")
    public ResponseEntity<List<MBiddingResultDTO>> getAllMBiddingResults(MBiddingResultCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingResults by criteria: {}", criteria);

        Page<MBiddingResultDTO> page = mBiddingResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-results/count} : count all the mBiddingResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-results/count")
    public ResponseEntity<Long> countMBiddingResults(MBiddingResultCriteria criteria) {
        log.debug("REST request to count MBiddingResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-results/:id} : get the "id" mBiddingResult.
     *
     * @param id the id of the mBiddingResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-results/{id}")
    public ResponseEntity<MBiddingResultDTO> getMBiddingResult(@PathVariable Long id) {
        log.debug("REST request to get MBiddingResult : {}", id);
        Optional<MBiddingResultDTO> mBiddingResultDTO = mBiddingResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingResultDTO);
    }

    /**
     * {@code DELETE  /m-bidding-results/:id} : delete the "id" mBiddingResult.
     *
     * @param id the id of the mBiddingResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-results/{id}")
    public ResponseEntity<Void> deleteMBiddingResult(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingResult : {}", id);
        mBiddingResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
