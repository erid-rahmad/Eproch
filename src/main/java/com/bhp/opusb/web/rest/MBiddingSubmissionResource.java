package com.bhp.opusb.web.rest;

import com.bhp.opusb.domain.MBiddingSubmission;
import com.bhp.opusb.service.MBiddingSubmissionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.dto.MBiddingSubmissionCriteria;
import com.bhp.opusb.service.MBiddingSubmissionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingSubmission}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionResource.class);

    private static final String ENTITY_NAME = "mBiddingSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingSubmissionService mBiddingSubmissionService;

    private final MBiddingSubmissionQueryService mBiddingSubmissionQueryService;

    public MBiddingSubmissionResource(MBiddingSubmissionService mBiddingSubmissionService, MBiddingSubmissionQueryService mBiddingSubmissionQueryService) {
        this.mBiddingSubmissionService = mBiddingSubmissionService;
        this.mBiddingSubmissionQueryService = mBiddingSubmissionQueryService;
    }

    /**
     * {@code POST  /m-bidding-submissions} : Create a new mBiddingSubmission.
     *
     * @param mBiddingSubmissionDTO the mBiddingSubmissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingSubmissionDTO, or with status {@code 400 (Bad Request)} if the mBiddingSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-submissions")
    public ResponseEntity<MBiddingSubmissionDTO> createMBiddingSubmission(@Valid @RequestBody MBiddingSubmissionDTO mBiddingSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingSubmission : {}", mBiddingSubmissionDTO);
        if (mBiddingSubmissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingSubmissionDTO result = mBiddingSubmissionService.save(mBiddingSubmissionDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-submissions} : Updates an existing mBiddingSubmission.
     *
     * @param mBiddingSubmissionDTO the mBiddingSubmissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingSubmissionDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingSubmissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingSubmissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-submissions")
    public ResponseEntity<MBiddingSubmissionDTO> updateMBiddingSubmission(@Valid @RequestBody MBiddingSubmissionDTO mBiddingSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingSubmission : {}", mBiddingSubmissionDTO);
        if (mBiddingSubmissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingSubmissionDTO result = mBiddingSubmissionService.save(mBiddingSubmissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingSubmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-submissions} : get all the mBiddingSubmissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingSubmissions in body.
     */
    @GetMapping("/m-bidding-submissions")
    public ResponseEntity<List<MBiddingSubmissionDTO>> getAllMBiddingSubmissions(MBiddingSubmissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingSubmissions by criteria: {}", criteria);
        Page<MBiddingSubmissionDTO> page = mBiddingSubmissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-bidding-submissions-nested/{id}")
    public ResponseEntity<Optional<MBiddingSubmission>> getAllMBiddingSubmissions(@PathVariable Long id) {
        log.debug("REST request to get MBiddingSubmissions by criteria: {}");
        return ResponseEntity.ok().body(mBiddingSubmissionService.findAllnested(id));
    }

    /**
     * {@code GET  /m-bidding-submissions/count} : count all the mBiddingSubmissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-submissions/count")
    public ResponseEntity<Long> countMBiddingSubmissions(MBiddingSubmissionCriteria criteria) {
        log.debug("REST request to count MBiddingSubmissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingSubmissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-submissions/:id} : get the "id" mBiddingSubmission.
     *
     * @param id the id of the mBiddingSubmissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingSubmissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-submissions/{id}")
    public ResponseEntity<MBiddingSubmissionDTO> getMBiddingSubmission(@PathVariable Long id) {
        log.debug("REST request to get MBiddingSubmission : {}", id);
        Optional<MBiddingSubmissionDTO> mBiddingSubmissionDTO = mBiddingSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingSubmissionDTO);
    }

    /**
     * {@code DELETE  /m-bidding-submissions/:id} : delete the "id" mBiddingSubmission.
     *
     * @param id the id of the mBiddingSubmissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-submissions/{id}")
    public ResponseEntity<Void> deleteMBiddingSubmission(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingSubmission : {}", id);
        mBiddingSubmissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
