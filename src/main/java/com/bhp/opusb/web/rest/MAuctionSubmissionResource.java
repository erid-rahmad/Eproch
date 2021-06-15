package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionSubmissionQueryService;
import com.bhp.opusb.service.MAuctionSubmissionService;
import com.bhp.opusb.service.dto.MAuctionSubmissionCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionSubmission}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionResource.class);

    private static final String ENTITY_NAME = "mAuctionSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionSubmissionService mAuctionSubmissionService;

    private final MAuctionSubmissionQueryService mAuctionSubmissionQueryService;

    public MAuctionSubmissionResource(MAuctionSubmissionService mAuctionSubmissionService, MAuctionSubmissionQueryService mAuctionSubmissionQueryService) {
        this.mAuctionSubmissionService = mAuctionSubmissionService;
        this.mAuctionSubmissionQueryService = mAuctionSubmissionQueryService;
    }

    /**
     * {@code POST  /m-auction-submissions} : Create a new mAuctionSubmission.
     *
     * @param mAuctionSubmissionDTO the mAuctionSubmissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionSubmissionDTO, or with status {@code 400 (Bad Request)} if the mAuctionSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-submissions")
    public ResponseEntity<MAuctionSubmissionDTO> createMAuctionSubmission(@Valid @RequestBody MAuctionSubmissionDTO mAuctionSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionSubmission : {}", mAuctionSubmissionDTO);
        if (mAuctionSubmissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionSubmissionDTO result = mAuctionSubmissionService.save(mAuctionSubmissionDTO);
        return ResponseEntity.created(new URI("/api/m-auction-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-auction-submissions/follow} : Follow the auction items.
     * This will create new MAuctionSubmission records based on the selected Auction Items.
     *
     * @param mAuctionSubmissionDTOs the list of mAuctionSubmissionDTOs to create.
     * @return the {@link ResponseEntity} with status {@code 200 (Okay)} and with body the list of mAuctionSubmissionDTOs.
     */
    @PostMapping("/m-auction-submissions/attend")
    public ResponseEntity<List<MAuctionSubmissionDTO>> createMAuctionSubmissions(@RequestBody List<MAuctionSubmissionDTO> mAuctionSubmissionDTOs) {
        log.debug("REST request to save MAuctionSubmissions. count : {}", mAuctionSubmissionDTOs.size());
        List<MAuctionSubmissionDTO> result = mAuctionSubmissionService.attend(mAuctionSubmissionDTOs);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-auction-submissions} : Updates an existing mAuctionSubmission.
     *
     * @param mAuctionSubmissionDTO the mAuctionSubmissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionSubmissionDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionSubmissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionSubmissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-submissions")
    public ResponseEntity<MAuctionSubmissionDTO> updateMAuctionSubmission(@Valid @RequestBody MAuctionSubmissionDTO mAuctionSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionSubmission : {}", mAuctionSubmissionDTO);
        if (mAuctionSubmissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionSubmissionDTO result = mAuctionSubmissionService.save(mAuctionSubmissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionSubmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-submissions} : get all the mAuctionSubmissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionSubmissions in body.
     */
    @GetMapping("/m-auction-submissions")
    public ResponseEntity<List<MAuctionSubmissionDTO>> getAllMAuctionSubmissions(MAuctionSubmissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionSubmissions by criteria: {}", criteria);
        Page<MAuctionSubmissionDTO> page = mAuctionSubmissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-submissions/count} : count all the mAuctionSubmissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-submissions/count")
    public ResponseEntity<Long> countMAuctionSubmissions(MAuctionSubmissionCriteria criteria) {
        log.debug("REST request to count MAuctionSubmissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionSubmissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-submissions/:id} : get the "id" mAuctionSubmission.
     *
     * @param id the id of the mAuctionSubmissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionSubmissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-submissions/{id}")
    public ResponseEntity<MAuctionSubmissionDTO> getMAuctionSubmission(@PathVariable Long id) {
        log.debug("REST request to get MAuctionSubmission : {}", id);
        Optional<MAuctionSubmissionDTO> mAuctionSubmissionDTO = mAuctionSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionSubmissionDTO);
    }

    /**
     * {@code DELETE  /m-auction-submissions/:id} : delete the "id" mAuctionSubmission.
     *
     * @param id the id of the mAuctionSubmissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-submissions/{id}")
    public ResponseEntity<Void> deleteMAuctionSubmission(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionSubmission : {}", id);
        mAuctionSubmissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
