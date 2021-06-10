package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionSubmissionLogQueryService;
import com.bhp.opusb.service.MAuctionSubmissionLogService;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionLogDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionSubmissionLog}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionSubmissionLogResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionLogResource.class);

    private static final String ENTITY_NAME = "mAuctionSubmissionLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionSubmissionLogService mAuctionSubmissionLogService;

    private final MAuctionSubmissionLogQueryService mAuctionSubmissionLogQueryService;

    public MAuctionSubmissionLogResource(MAuctionSubmissionLogService mAuctionSubmissionLogService, MAuctionSubmissionLogQueryService mAuctionSubmissionLogQueryService) {
        this.mAuctionSubmissionLogService = mAuctionSubmissionLogService;
        this.mAuctionSubmissionLogQueryService = mAuctionSubmissionLogQueryService;
    }

    /**
     * {@code POST  /m-auction-submission-logs} : Create a new mAuctionSubmissionLog.
     *
     * @param mAuctionSubmissionLogDTO the mAuctionSubmissionLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionSubmissionLogDTO, or with status {@code 400 (Bad Request)} if the mAuctionSubmissionLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-submission-logs")
    public ResponseEntity<MAuctionSubmissionLogDTO> createMAuctionSubmissionLog(@Valid @RequestBody MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionSubmissionLog : {}", mAuctionSubmissionLogDTO);
        if (mAuctionSubmissionLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionSubmissionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionSubmissionLogDTO result = mAuctionSubmissionLogService.save(mAuctionSubmissionLogDTO);
        return ResponseEntity.created(new URI("/api/m-auction-submission-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-submission-logs} : Updates an existing mAuctionSubmissionLog.
     *
     * @param mAuctionSubmissionLogDTO the mAuctionSubmissionLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionSubmissionLogDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionSubmissionLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionSubmissionLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-submission-logs")
    public ResponseEntity<MAuctionSubmissionLogDTO> updateMAuctionSubmissionLog(@Valid @RequestBody MAuctionSubmissionLogDTO mAuctionSubmissionLogDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionSubmissionLog : {}", mAuctionSubmissionLogDTO);
        if (mAuctionSubmissionLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionSubmissionLogDTO result = mAuctionSubmissionLogService.save(mAuctionSubmissionLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionSubmissionLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-submission-logs} : get all the mAuctionSubmissionLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionSubmissionLogs in body.
     */
    @GetMapping("/m-auction-submission-logs")
    public ResponseEntity<List<MAuctionSubmissionLogDTO>> getAllMAuctionSubmissionLogs(MAuctionSubmissionLogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionSubmissionLogs by criteria: {}", criteria);
        Page<MAuctionSubmissionLogDTO> page = mAuctionSubmissionLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-submission-logs/count} : count all the mAuctionSubmissionLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-submission-logs/count")
    public ResponseEntity<Long> countMAuctionSubmissionLogs(MAuctionSubmissionLogCriteria criteria) {
        log.debug("REST request to count MAuctionSubmissionLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionSubmissionLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-submission-logs/:id} : get the "id" mAuctionSubmissionLog.
     *
     * @param id the id of the mAuctionSubmissionLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionSubmissionLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-submission-logs/{id}")
    public ResponseEntity<MAuctionSubmissionLogDTO> getMAuctionSubmissionLog(@PathVariable Long id) {
        log.debug("REST request to get MAuctionSubmissionLog : {}", id);
        Optional<MAuctionSubmissionLogDTO> mAuctionSubmissionLogDTO = mAuctionSubmissionLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionSubmissionLogDTO);
    }

    /**
     * {@code DELETE  /m-auction-submission-logs/:id} : delete the "id" mAuctionSubmissionLog.
     *
     * @param id the id of the mAuctionSubmissionLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-submission-logs/{id}")
    public ResponseEntity<Void> deleteMAuctionSubmissionLog(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionSubmissionLog : {}", id);
        mAuctionSubmissionLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
