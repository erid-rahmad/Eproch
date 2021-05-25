package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionParticipantQueryService;
import com.bhp.opusb.service.MAuctionParticipantService;
import com.bhp.opusb.service.dto.MAuctionParticipantCriteria;
import com.bhp.opusb.service.dto.MAuctionParticipantDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionParticipant}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionParticipantResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionParticipantResource.class);

    private static final String ENTITY_NAME = "mAuctionParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionParticipantService mAuctionParticipantService;

    private final MAuctionParticipantQueryService mAuctionParticipantQueryService;

    public MAuctionParticipantResource(MAuctionParticipantService mAuctionParticipantService, MAuctionParticipantQueryService mAuctionParticipantQueryService) {
        this.mAuctionParticipantService = mAuctionParticipantService;
        this.mAuctionParticipantQueryService = mAuctionParticipantQueryService;
    }

    /**
     * {@code POST  /m-auction-participants} : Create a new mAuctionParticipant.
     *
     * @param mAuctionParticipantDTO the mAuctionParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionParticipantDTO, or with status {@code 400 (Bad Request)} if the mAuctionParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-participants")
    public ResponseEntity<MAuctionParticipantDTO> createMAuctionParticipant(@Valid @RequestBody MAuctionParticipantDTO mAuctionParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionParticipant : {}", mAuctionParticipantDTO);
        if (mAuctionParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionParticipantDTO result = mAuctionParticipantService.save(mAuctionParticipantDTO);
        return ResponseEntity.created(new URI("/api/m-auction-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-participants} : Updates an existing mAuctionParticipant.
     *
     * @param mAuctionParticipantDTO the mAuctionParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-participants")
    public ResponseEntity<MAuctionParticipantDTO> updateMAuctionParticipant(@Valid @RequestBody MAuctionParticipantDTO mAuctionParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionParticipant : {}", mAuctionParticipantDTO);
        if (mAuctionParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionParticipantDTO result = mAuctionParticipantService.save(mAuctionParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-participants} : get all the mAuctionParticipants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionParticipants in body.
     */
    @GetMapping("/m-auction-participants")
    public ResponseEntity<List<MAuctionParticipantDTO>> getAllMAuctionParticipants(MAuctionParticipantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionParticipants by criteria: {}", criteria);
        Page<MAuctionParticipantDTO> page = mAuctionParticipantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-participants/count} : count all the mAuctionParticipants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-participants/count")
    public ResponseEntity<Long> countMAuctionParticipants(MAuctionParticipantCriteria criteria) {
        log.debug("REST request to count MAuctionParticipants by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionParticipantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-participants/:id} : get the "id" mAuctionParticipant.
     *
     * @param id the id of the mAuctionParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-participants/{id}")
    public ResponseEntity<MAuctionParticipantDTO> getMAuctionParticipant(@PathVariable Long id) {
        log.debug("REST request to get MAuctionParticipant : {}", id);
        Optional<MAuctionParticipantDTO> mAuctionParticipantDTO = mAuctionParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionParticipantDTO);
    }

    /**
     * {@code DELETE  /m-auction-participants/:id} : delete the "id" mAuctionParticipant.
     *
     * @param id the id of the mAuctionParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-participants/{id}")
    public ResponseEntity<Void> deleteMAuctionParticipant(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionParticipant : {}", id);
        mAuctionParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
