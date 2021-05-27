package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingEvalResultLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineDTO;
import com.bhp.opusb.service.dto.MBiddingEvalResultLineCriteria;
import com.bhp.opusb.service.MBiddingEvalResultLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingEvalResultLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingEvalResultLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultLineResource.class);

    private static final String ENTITY_NAME = "mBiddingEvalResultLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingEvalResultLineService mBiddingEvalResultLineService;

    private final MBiddingEvalResultLineQueryService mBiddingEvalResultLineQueryService;

    public MBiddingEvalResultLineResource(MBiddingEvalResultLineService mBiddingEvalResultLineService, MBiddingEvalResultLineQueryService mBiddingEvalResultLineQueryService) {
        this.mBiddingEvalResultLineService = mBiddingEvalResultLineService;
        this.mBiddingEvalResultLineQueryService = mBiddingEvalResultLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-eval-result-lines} : Create a new mBiddingEvalResultLine.
     *
     * @param mBiddingEvalResultLineDTO the mBiddingEvalResultLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingEvalResultLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingEvalResultLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-eval-result-lines")
    public ResponseEntity<MBiddingEvalResultLineDTO> createMBiddingEvalResultLine(@Valid @RequestBody MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingEvalResultLine : {}", mBiddingEvalResultLineDTO);
        if (mBiddingEvalResultLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingEvalResultLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingEvalResultLineDTO result = mBiddingEvalResultLineService.save(mBiddingEvalResultLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-eval-result-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-eval-result-lines} : Updates an existing mBiddingEvalResultLine.
     *
     * @param mBiddingEvalResultLineDTO the mBiddingEvalResultLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingEvalResultLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingEvalResultLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingEvalResultLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-eval-result-lines")
    public ResponseEntity<MBiddingEvalResultLineDTO> updateMBiddingEvalResultLine(@Valid @RequestBody MBiddingEvalResultLineDTO mBiddingEvalResultLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingEvalResultLine : {}", mBiddingEvalResultLineDTO);
        if (mBiddingEvalResultLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingEvalResultLineDTO result = mBiddingEvalResultLineService.save(mBiddingEvalResultLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingEvalResultLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-eval-result-lines} : get all the mBiddingEvalResultLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingEvalResultLines in body.
     */
    @GetMapping("/m-bidding-eval-result-lines")
    public ResponseEntity<List<MBiddingEvalResultLineDTO>> getAllMBiddingEvalResultLines(MBiddingEvalResultLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingEvalResultLines by criteria: {}", criteria);
        Page<MBiddingEvalResultLineDTO> page = mBiddingEvalResultLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-eval-result-lines/count} : count all the mBiddingEvalResultLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-eval-result-lines/count")
    public ResponseEntity<Long> countMBiddingEvalResultLines(MBiddingEvalResultLineCriteria criteria) {
        log.debug("REST request to count MBiddingEvalResultLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingEvalResultLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-eval-result-lines/:id} : get the "id" mBiddingEvalResultLine.
     *
     * @param id the id of the mBiddingEvalResultLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingEvalResultLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-eval-result-lines/{id}")
    public ResponseEntity<MBiddingEvalResultLineDTO> getMBiddingEvalResultLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingEvalResultLine : {}", id);
        Optional<MBiddingEvalResultLineDTO> mBiddingEvalResultLineDTO = mBiddingEvalResultLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingEvalResultLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-eval-result-lines/:id} : delete the "id" mBiddingEvalResultLine.
     *
     * @param id the id of the mBiddingEvalResultLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-eval-result-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingEvalResultLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingEvalResultLine : {}", id);
        mBiddingEvalResultLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
