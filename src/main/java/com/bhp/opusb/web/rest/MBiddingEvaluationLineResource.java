package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingEvaluationLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineDTO;
import com.bhp.opusb.service.dto.MBiddingEvaluationLineCriteria;
import com.bhp.opusb.service.MBiddingEvaluationLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingEvaluationLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingEvaluationLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationLineResource.class);

    private static final String ENTITY_NAME = "mBiddingEvaluationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingEvaluationLineService mBiddingEvaluationLineService;

    private final MBiddingEvaluationLineQueryService mBiddingEvaluationLineQueryService;

    public MBiddingEvaluationLineResource(MBiddingEvaluationLineService mBiddingEvaluationLineService, MBiddingEvaluationLineQueryService mBiddingEvaluationLineQueryService) {
        this.mBiddingEvaluationLineService = mBiddingEvaluationLineService;
        this.mBiddingEvaluationLineQueryService = mBiddingEvaluationLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-evaluation-lines} : Create a new mBiddingEvaluationLine.
     *
     * @param mBiddingEvaluationLineDTO the mBiddingEvaluationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingEvaluationLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingEvaluationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-evaluation-lines")
    public ResponseEntity<MBiddingEvaluationLineDTO> createMBiddingEvaluationLine(@Valid @RequestBody MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingEvaluationLine : {}", mBiddingEvaluationLineDTO);
        if (mBiddingEvaluationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingEvaluationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingEvaluationLineDTO result = mBiddingEvaluationLineService.save(mBiddingEvaluationLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-evaluation-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-evaluation-lines} : Updates an existing mBiddingEvaluationLine.
     *
     * @param mBiddingEvaluationLineDTO the mBiddingEvaluationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingEvaluationLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingEvaluationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingEvaluationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-evaluation-lines")
    public ResponseEntity<MBiddingEvaluationLineDTO> updateMBiddingEvaluationLine(@Valid @RequestBody MBiddingEvaluationLineDTO mBiddingEvaluationLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingEvaluationLine : {}", mBiddingEvaluationLineDTO);
        if (mBiddingEvaluationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingEvaluationLineDTO result = mBiddingEvaluationLineService.save(mBiddingEvaluationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingEvaluationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-evaluation-lines} : get all the mBiddingEvaluationLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingEvaluationLines in body.
     */
    @GetMapping("/m-bidding-evaluation-lines")
    public ResponseEntity<List<MBiddingEvaluationLineDTO>> getAllMBiddingEvaluationLines(MBiddingEvaluationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingEvaluationLines by criteria: {}", criteria);
        Page<MBiddingEvaluationLineDTO> page = mBiddingEvaluationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-evaluation-lines/count} : count all the mBiddingEvaluationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-evaluation-lines/count")
    public ResponseEntity<Long> countMBiddingEvaluationLines(MBiddingEvaluationLineCriteria criteria) {
        log.debug("REST request to count MBiddingEvaluationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingEvaluationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-evaluation-lines/:id} : get the "id" mBiddingEvaluationLine.
     *
     * @param id the id of the mBiddingEvaluationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingEvaluationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-evaluation-lines/{id}")
    public ResponseEntity<MBiddingEvaluationLineDTO> getMBiddingEvaluationLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingEvaluationLine : {}", id);
        Optional<MBiddingEvaluationLineDTO> mBiddingEvaluationLineDTO = mBiddingEvaluationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingEvaluationLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-evaluation-lines/:id} : delete the "id" mBiddingEvaluationLine.
     *
     * @param id the id of the mBiddingEvaluationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-evaluation-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingEvaluationLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingEvaluationLine : {}", id);
        mBiddingEvaluationLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
