package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionQueryService;
import com.bhp.opusb.service.MAuctionService;
import com.bhp.opusb.service.dto.MAuctionCriteria;
import com.bhp.opusb.service.dto.MAuctionDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuction}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionResource.class);

    private static final String ENTITY_NAME = "mAuction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionService mAuctionService;

    private final MAuctionQueryService mAuctionQueryService;

    public MAuctionResource(MAuctionService mAuctionService, MAuctionQueryService mAuctionQueryService) {
        this.mAuctionService = mAuctionService;
        this.mAuctionQueryService = mAuctionQueryService;
    }

    /**
     * {@code POST  /m-auctions} : Create a new mAuction.
     *
     * @param mAuctionDTO the mAuctionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionDTO, or with status {@code 400 (Bad Request)} if the mAuction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auctions")
    public ResponseEntity<MAuctionDTO> createMAuction(@Valid @RequestBody MAuctionDTO mAuctionDTO) throws URISyntaxException {
        log.debug("REST request to save MAuction : {}", mAuctionDTO);
        if (mAuctionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionDTO result = mAuctionService.save(mAuctionDTO);
        return ResponseEntity.created(new URI("/api/m-auctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auctions} : Updates an existing mAuction.
     *
     * @param mAuctionDTO the mAuctionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auctions")
    public ResponseEntity<MAuctionDTO> updateMAuction(@Valid @RequestBody MAuctionDTO mAuctionDTO) throws URISyntaxException {
        log.debug("REST request to update MAuction : {}", mAuctionDTO);
        if (mAuctionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionDTO result = mAuctionService.save(mAuctionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auctions} : get all the mAuctions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctions in body.
     */
    @GetMapping("/m-auctions")
    public ResponseEntity<List<MAuctionDTO>> getAllMAuctions(MAuctionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctions by criteria: {}", criteria);
        Page<MAuctionDTO> page = mAuctionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auctions/count} : count all the mAuctions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auctions/count")
    public ResponseEntity<Long> countMAuctions(MAuctionCriteria criteria) {
        log.debug("REST request to count MAuctions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auctions/:id} : get the "id" mAuction.
     *
     * @param id the id of the mAuctionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auctions/{id}")
    public ResponseEntity<MAuctionDTO> getMAuction(@PathVariable Long id) {
        log.debug("REST request to get MAuction : {}", id);
        Optional<MAuctionDTO> mAuctionDTO = mAuctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionDTO);
    }

    /**
     * {@code DELETE  /m-auctions/:id} : delete the "id" mAuction.
     *
     * @param id the id of the mAuctionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auctions/{id}")
    public ResponseEntity<Void> deleteMAuction(@PathVariable Long id) {
        log.debug("REST request to delete MAuction : {}", id);
        mAuctionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
