package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingNegotiationLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineDTO;
import com.bhp.opusb.service.dto.MBiddingNegotiationLineCriteria;
import com.bhp.opusb.service.MBiddingNegotiationLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingNegotiationLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingNegotiationLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationLineResource.class);

    private static final String ENTITY_NAME = "mBiddingNegotiationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingNegotiationLineService mBiddingNegotiationLineService;

    private final MBiddingNegotiationLineQueryService mBiddingNegotiationLineQueryService;

    public MBiddingNegotiationLineResource(MBiddingNegotiationLineService mBiddingNegotiationLineService, MBiddingNegotiationLineQueryService mBiddingNegotiationLineQueryService) {
        this.mBiddingNegotiationLineService = mBiddingNegotiationLineService;
        this.mBiddingNegotiationLineQueryService = mBiddingNegotiationLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-negotiation-lines} : Create a new mBiddingNegotiationLine.
     *
     * @param mBiddingNegotiationLineDTO the mBiddingNegotiationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingNegotiationLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingNegotiationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-negotiation-lines")
    public ResponseEntity<MBiddingNegotiationLineDTO> createMBiddingNegotiationLine(@Valid @RequestBody MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingNegotiationLine : {}", mBiddingNegotiationLineDTO);
        if (mBiddingNegotiationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingNegotiationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingNegotiationLineDTO result = mBiddingNegotiationLineService.save(mBiddingNegotiationLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-negotiation-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-bidding-negotiation-lines} : Finalize a mBiddingNegotiationLine.
     *
     * @param mBiddingNegotiationLineDTO the mBiddingNegotiationLineDTO to finalize.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingNegotiationLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingNegotiationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-negotiation-lines/finalize")
    public ResponseEntity<MBiddingNegotiationLineDTO> finalizeMBiddingNegotiationLine(@Valid @RequestBody MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO) throws URISyntaxException {
        log.debug("REST request to finalize MBiddingNegotiationLine : {}", mBiddingNegotiationLineDTO);
        if (mBiddingNegotiationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingNegotiationLineDTO result = mBiddingNegotiationLineService.finalize(mBiddingNegotiationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingNegotiationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-negotiation-lines} : Updates an existing mBiddingNegotiationLine.
     *
     * @param mBiddingNegotiationLineDTO the mBiddingNegotiationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingNegotiationLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingNegotiationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingNegotiationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-negotiation-lines")
    public ResponseEntity<MBiddingNegotiationLineDTO> updateMBiddingNegotiationLine(@Valid @RequestBody MBiddingNegotiationLineDTO mBiddingNegotiationLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingNegotiationLine : {}", mBiddingNegotiationLineDTO);
        if (mBiddingNegotiationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingNegotiationLineDTO result = mBiddingNegotiationLineService.save(mBiddingNegotiationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingNegotiationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-negotiation-lines} : get all the mBiddingNegotiationLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingNegotiationLines in body.
     */
    @GetMapping("/m-bidding-negotiation-lines")
    public ResponseEntity<List<MBiddingNegotiationLineDTO>> getAllMBiddingNegotiationLines(MBiddingNegotiationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingNegotiationLines by criteria: {}", criteria);
        Page<MBiddingNegotiationLineDTO> page = mBiddingNegotiationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-negotiation-lines/count} : count all the mBiddingNegotiationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-negotiation-lines/count")
    public ResponseEntity<Long> countMBiddingNegotiationLines(MBiddingNegotiationLineCriteria criteria) {
        log.debug("REST request to count MBiddingNegotiationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingNegotiationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-negotiation-lines/:id} : get the "id" mBiddingNegotiationLine.
     *
     * @param id the id of the mBiddingNegotiationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingNegotiationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-negotiation-lines/{id}")
    public ResponseEntity<MBiddingNegotiationLineDTO> getMBiddingNegotiationLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingNegotiationLine : {}", id);
        Optional<MBiddingNegotiationLineDTO> mBiddingNegotiationLineDTO = mBiddingNegotiationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingNegotiationLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-negotiation-lines/:id} : delete the "id" mBiddingNegotiationLine.
     *
     * @param id the id of the mBiddingNegotiationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-negotiation-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingNegotiationLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingNegotiationLine : {}", id);
        mBiddingNegotiationLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
