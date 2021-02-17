package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.dto.MBiddingLineCriteria;
import com.bhp.opusb.service.MBiddingLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingLineResource.class);

    private static final String ENTITY_NAME = "mBiddingLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingLineService mBiddingLineService;

    private final MBiddingLineQueryService mBiddingLineQueryService;

    public MBiddingLineResource(MBiddingLineService mBiddingLineService, MBiddingLineQueryService mBiddingLineQueryService) {
        this.mBiddingLineService = mBiddingLineService;
        this.mBiddingLineQueryService = mBiddingLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-lines} : Create a new mBiddingLine.
     *
     * @param mBiddingLineDTO the mBiddingLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-lines")
    public ResponseEntity<MBiddingLineDTO> createMBiddingLine(@Valid @RequestBody MBiddingLineDTO mBiddingLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingLine : {}", mBiddingLineDTO);
        if (mBiddingLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingLineDTO result = mBiddingLineService.save(mBiddingLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-lines} : Updates an existing mBiddingLine.
     *
     * @param mBiddingLineDTO the mBiddingLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-lines")
    public ResponseEntity<MBiddingLineDTO> updateMBiddingLine(@Valid @RequestBody MBiddingLineDTO mBiddingLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingLine : {}", mBiddingLineDTO);
        if (mBiddingLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingLineDTO result = mBiddingLineService.save(mBiddingLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-lines} : get all the mBiddingLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingLines in body.
     */
    @GetMapping("/m-bidding-lines")
    public ResponseEntity<List<MBiddingLineDTO>> getAllMBiddingLines(MBiddingLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingLines by criteria: {}", criteria);
        Page<MBiddingLineDTO> page = mBiddingLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-lines/count} : count all the mBiddingLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-lines/count")
    public ResponseEntity<Long> countMBiddingLines(MBiddingLineCriteria criteria) {
        log.debug("REST request to count MBiddingLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-lines/:id} : get the "id" mBiddingLine.
     *
     * @param id the id of the mBiddingLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-lines/{id}")
    public ResponseEntity<MBiddingLineDTO> getMBiddingLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingLine : {}", id);
        Optional<MBiddingLineDTO> mBiddingLineDTO = mBiddingLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-lines/:id} : delete the "id" mBiddingLine.
     *
     * @param id the id of the mBiddingLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingLine : {}", id);
        mBiddingLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
