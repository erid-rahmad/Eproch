package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingEvalTeamLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineDTO;
import com.bhp.opusb.service.dto.MBiddingEvalTeamLineCriteria;
import com.bhp.opusb.service.MBiddingEvalTeamLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingEvalTeamLine}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingEvalTeamLineResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalTeamLineResource.class);

    private static final String ENTITY_NAME = "mBiddingEvalTeamLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingEvalTeamLineService mBiddingEvalTeamLineService;

    private final MBiddingEvalTeamLineQueryService mBiddingEvalTeamLineQueryService;

    public MBiddingEvalTeamLineResource(MBiddingEvalTeamLineService mBiddingEvalTeamLineService, MBiddingEvalTeamLineQueryService mBiddingEvalTeamLineQueryService) {
        this.mBiddingEvalTeamLineService = mBiddingEvalTeamLineService;
        this.mBiddingEvalTeamLineQueryService = mBiddingEvalTeamLineQueryService;
    }

    /**
     * {@code POST  /m-bidding-eval-team-lines} : Create a new mBiddingEvalTeamLine.
     *
     * @param mBiddingEvalTeamLineDTO the mBiddingEvalTeamLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingEvalTeamLineDTO, or with status {@code 400 (Bad Request)} if the mBiddingEvalTeamLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-eval-team-lines")
    public ResponseEntity<MBiddingEvalTeamLineDTO> createMBiddingEvalTeamLine(@Valid @RequestBody MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingEvalTeamLine : {}", mBiddingEvalTeamLineDTO);
        if (mBiddingEvalTeamLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingEvalTeamLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingEvalTeamLineDTO result = mBiddingEvalTeamLineService.save(mBiddingEvalTeamLineDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-eval-team-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-eval-team-lines} : Updates an existing mBiddingEvalTeamLine.
     *
     * @param mBiddingEvalTeamLineDTO the mBiddingEvalTeamLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingEvalTeamLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingEvalTeamLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingEvalTeamLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-eval-team-lines")
    public ResponseEntity<MBiddingEvalTeamLineDTO> updateMBiddingEvalTeamLine(@Valid @RequestBody MBiddingEvalTeamLineDTO mBiddingEvalTeamLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingEvalTeamLine : {}", mBiddingEvalTeamLineDTO);
        if (mBiddingEvalTeamLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingEvalTeamLineDTO result = mBiddingEvalTeamLineService.save(mBiddingEvalTeamLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingEvalTeamLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-eval-team-lines} : get all the mBiddingEvalTeamLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingEvalTeamLines in body.
     */
    @GetMapping("/m-bidding-eval-team-lines")
    public ResponseEntity<List<MBiddingEvalTeamLineDTO>> getAllMBiddingEvalTeamLines(MBiddingEvalTeamLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingEvalTeamLines by criteria: {}", criteria);
        Page<MBiddingEvalTeamLineDTO> page = mBiddingEvalTeamLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-eval-team-lines/count} : count all the mBiddingEvalTeamLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-eval-team-lines/count")
    public ResponseEntity<Long> countMBiddingEvalTeamLines(MBiddingEvalTeamLineCriteria criteria) {
        log.debug("REST request to count MBiddingEvalTeamLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingEvalTeamLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-eval-team-lines/:id} : get the "id" mBiddingEvalTeamLine.
     *
     * @param id the id of the mBiddingEvalTeamLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingEvalTeamLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-eval-team-lines/{id}")
    public ResponseEntity<MBiddingEvalTeamLineDTO> getMBiddingEvalTeamLine(@PathVariable Long id) {
        log.debug("REST request to get MBiddingEvalTeamLine : {}", id);
        Optional<MBiddingEvalTeamLineDTO> mBiddingEvalTeamLineDTO = mBiddingEvalTeamLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingEvalTeamLineDTO);
    }

    /**
     * {@code DELETE  /m-bidding-eval-team-lines/:id} : delete the "id" mBiddingEvalTeamLine.
     *
     * @param id the id of the mBiddingEvalTeamLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-eval-team-lines/{id}")
    public ResponseEntity<Void> deleteMBiddingEvalTeamLine(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingEvalTeamLine : {}", id);
        mBiddingEvalTeamLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
