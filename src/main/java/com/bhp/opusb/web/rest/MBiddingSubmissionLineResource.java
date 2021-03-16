package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingSubmissionLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineDTO;
import com.bhp.opusb.service.dto.MBiddingSubmissionLineCriteria;
import com.bhp.opusb.service.MBiddingSubmissionLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingSubmissionLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingSubmissionLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionLineResource.class);

    private static final String ENTITY_NAME = "mBiddingSubmissionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingSubmissionLineService mBiddingSubmissionLineService;

    private final MBiddingSubmissionLineQueryService mBiddingSubmissionLineQueryService;

    public MBiddingSubmissionLineResource(MBiddingSubmissionLineService mBiddingSubmissionLineService, MBiddingSubmissionLineQueryService mBiddingSubmissionLineQueryService) {
        this.mBiddingSubmissionLineService = mBiddingSubmissionLineService;
        this.mBiddingSubmissionLineQueryService = mBiddingSubmissionLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-submission-lines} : Create a new mBiddingSubmissionLine.
     *
     * @param mBiddingSubmissionLineDTO the mBiddingSubmissionLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingSubmissionLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingSubmissionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-submission-lines")
    public ResponseEntity<MBiddingSubmissionLineDTO> createMBiddingSubmissionLine(@Valid @RequestBody MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingSubmissionLine : {}", mBiddingSubmissionLineDTO);
        if (mBiddingSubmissionLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingSubmissionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingSubmissionLineDTO result = mBiddingSubmissionLineService.save(mBiddingSubmissionLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-submission-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-submission-lines} : Updates an existing mBiddingSubmissionLine.
     *
     * @param mBiddingSubmissionLineDTO the mBiddingSubmissionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingSubmissionLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingSubmissionLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingSubmissionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-submission-lines")
    public ResponseEntity<MBiddingSubmissionLineDTO> updateMBiddingSubmissionLine(@Valid @RequestBody MBiddingSubmissionLineDTO mBiddingSubmissionLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingSubmissionLine : {}", mBiddingSubmissionLineDTO);
        if (mBiddingSubmissionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingSubmissionLineDTO result = mBiddingSubmissionLineService.save(mBiddingSubmissionLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingSubmissionLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-submission-lines} : get all the mBiddingSubmissionLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingSubmissionLines in body.
     */
    @GetMapping("/m-bidding-submission-lines")
    public ResponseEntity<List<MBiddingSubmissionLineDTO>> getAllMBiddingSubmissionLines(MBiddingSubmissionLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingSubmissionLines by criteria: {}", criteria);
        Page<MBiddingSubmissionLineDTO> page = mBiddingSubmissionLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-submission-lines/count} : count all the mBiddingSubmissionLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-submission-lines/count")
    public ResponseEntity<Long> countMBiddingSubmissionLines(MBiddingSubmissionLineCriteria criteria) {
        log.debug("REST request to count MBiddingSubmissionLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingSubmissionLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-submission-lines/:id} : get the "id" mBiddingSubmissionLine.
     *
     * @param id the id of the mBiddingSubmissionLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingSubmissionLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-submission-lines/{id}")
    public ResponseEntity<MBiddingSubmissionLineDTO> getMBiddingSubmissionLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingSubmissionLine : {}", id);
        Optional<MBiddingSubmissionLineDTO> mBiddingSubmissionLineDTO = mBiddingSubmissionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingSubmissionLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-submission-lines/:id} : delete the "id" mBiddingSubmissionLine.
     *
     * @param id the id of the mBiddingSubmissionLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-submission-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingSubmissionLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingSubmissionLine : {}", id);
        mBiddingSubmissionLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
