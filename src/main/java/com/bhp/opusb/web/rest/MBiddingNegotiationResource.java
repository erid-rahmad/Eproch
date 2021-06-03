package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingNegotiationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingNegotiationDTO;
import com.bhp.opusb.service.dto.MBiddingNegotiationCriteria;
import com.bhp.opusb.service.MBiddingNegotiationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingNegotiation}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingNegotiationResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationResource.class);

    private static final String ENTITY_NAME = "mBiddingNegotiation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingNegotiationService mBiddingNegotiationService;

    private final MBiddingNegotiationQueryService mBiddingNegotiationQueryService;

    public MBiddingNegotiationResource(MBiddingNegotiationService mBiddingNegotiationService, MBiddingNegotiationQueryService mBiddingNegotiationQueryService) {
        this.mBiddingNegotiationService = mBiddingNegotiationService;
        this.mBiddingNegotiationQueryService = mBiddingNegotiationQueryService;
    }

    /**
     * {@code POST  /m-bidding-negotiations} : Create a new mBiddingNegotiation.
     *
     * @param mBiddingNegotiationDTO the mBiddingNegotiationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingNegotiationDTO, or with status {@code 400 (Bad Request)} if the mBiddingNegotiation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-negotiations")
    public ResponseEntity<MBiddingNegotiationDTO> createMBiddingNegotiation(@Valid @RequestBody MBiddingNegotiationDTO mBiddingNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingNegotiation : {}", mBiddingNegotiationDTO);
        if (mBiddingNegotiationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingNegotiation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingNegotiationDTO result = mBiddingNegotiationService.save(mBiddingNegotiationDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-negotiations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-negotiations} : Updates an existing mBiddingNegotiation.
     *
     * @param mBiddingNegotiationDTO the mBiddingNegotiationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingNegotiationDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingNegotiationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingNegotiationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-negotiations")
    public ResponseEntity<MBiddingNegotiationDTO> updateMBiddingNegotiation(@Valid @RequestBody MBiddingNegotiationDTO mBiddingNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingNegotiation : {}", mBiddingNegotiationDTO);
        if (mBiddingNegotiationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingNegotiationDTO result = mBiddingNegotiationService.save(mBiddingNegotiationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingNegotiationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-negotiations} : get all the mBiddingNegotiations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingNegotiations in body.
     */
    @GetMapping("/m-bidding-negotiations")
    public ResponseEntity<List<MBiddingNegotiationDTO>> getAllMBiddingNegotiations(MBiddingNegotiationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingNegotiations by criteria: {}", criteria);
        Page<MBiddingNegotiationDTO> page = mBiddingNegotiationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-negotiations/count} : count all the mBiddingNegotiations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-negotiations/count")
    public ResponseEntity<Long> countMBiddingNegotiations(MBiddingNegotiationCriteria criteria) {
        log.debug("REST request to count MBiddingNegotiations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingNegotiationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-negotiations/:id} : get the "id" mBiddingNegotiation.
     *
     * @param id the id of the mBiddingNegotiationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingNegotiationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-negotiations/{id}")
    public ResponseEntity<MBiddingNegotiationDTO> getMBiddingNegotiation(@PathVariable Long id) {
        log.debug("REST request to get MBiddingNegotiation : {}", id);
        Optional<MBiddingNegotiationDTO> mBiddingNegotiationDTO = mBiddingNegotiationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingNegotiationDTO);
    }

    /**
     * {@code DELETE  /m-bidding-negotiations/:id} : delete the "id" mBiddingNegotiation.
     *
     * @param id the id of the mBiddingNegotiationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-negotiations/{id}")
    public ResponseEntity<Void> deleteMBiddingNegotiation(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingNegotiation : {}", id);
        mBiddingNegotiationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
