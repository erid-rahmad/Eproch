package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingSubItemLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingSubItemLineDTO;
import com.bhp.opusb.service.dto.MBiddingSubItemLineCriteria;
import com.bhp.opusb.service.MBiddingSubItemLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingSubItemLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingSubItemLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubItemLineResource.class);

    private static final String ENTITY_NAME = "mBiddingSubItemLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingSubItemLineService mBiddingSubItemLineService;

    private final MBiddingSubItemLineQueryService mBiddingSubItemLineQueryService;

    public MBiddingSubItemLineResource(MBiddingSubItemLineService mBiddingSubItemLineService, MBiddingSubItemLineQueryService mBiddingSubItemLineQueryService) {
        this.mBiddingSubItemLineService = mBiddingSubItemLineService;
        this.mBiddingSubItemLineQueryService = mBiddingSubItemLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-sub-item-lines} : Create a new mBiddingSubItemLine.
     *
     * @param mBiddingSubItemLineDTO the mBiddingSubItemLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingSubItemLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingSubItemLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-sub-item-lines")
    public ResponseEntity<MBiddingSubItemLineDTO> createMBiddingSubItemLine(@Valid @RequestBody MBiddingSubItemLineDTO mBiddingSubItemLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingSubItemLine : {}", mBiddingSubItemLineDTO);
        if (mBiddingSubItemLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingSubItemLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingSubItemLineDTO result = mBiddingSubItemLineService.save(mBiddingSubItemLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-sub-item-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-sub-item-lines} : Updates an existing mBiddingSubItemLine.
     *
     * @param mBiddingSubItemLineDTO the mBiddingSubItemLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingSubItemLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingSubItemLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingSubItemLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-sub-item-lines")
    public ResponseEntity<MBiddingSubItemLineDTO> updateMBiddingSubItemLine(@Valid @RequestBody MBiddingSubItemLineDTO mBiddingSubItemLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingSubItemLine : {}", mBiddingSubItemLineDTO);
        if (mBiddingSubItemLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingSubItemLineDTO result = mBiddingSubItemLineService.save(mBiddingSubItemLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingSubItemLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-sub-item-lines} : get all the mBiddingSubItemLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingSubItemLines in body.
     */
    @GetMapping("/m-bidding-sub-item-lines")
    public ResponseEntity<List<MBiddingSubItemLineDTO>> getAllMBiddingSubItemLines(MBiddingSubItemLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingSubItemLines by criteria: {}", criteria);
        Page<MBiddingSubItemLineDTO> page = mBiddingSubItemLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-sub-item-lines/count} : count all the mBiddingSubItemLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-sub-item-lines/count")
    public ResponseEntity<Long> countMBiddingSubItemLines(MBiddingSubItemLineCriteria criteria) {
        log.debug("REST request to count MBiddingSubItemLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingSubItemLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-sub-item-lines/:id} : get the "id" mBiddingSubItemLine.
     *
     * @param id the id of the mBiddingSubItemLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingSubItemLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-sub-item-lines/{id}")
    public ResponseEntity<MBiddingSubItemLineDTO> getMBiddingSubItemLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingSubItemLine : {}", id);
        Optional<MBiddingSubItemLineDTO> mBiddingSubItemLineDTO = mBiddingSubItemLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingSubItemLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-sub-item-lines/:id} : delete the "id" mBiddingSubItemLine.
     *
     * @param id the id of the mBiddingSubItemLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-sub-item-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingSubItemLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingSubItemLine : {}", id);
        mBiddingSubItemLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
