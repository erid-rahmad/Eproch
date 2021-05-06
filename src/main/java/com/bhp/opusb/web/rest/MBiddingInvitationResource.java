package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingInvitationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingInvitationDTO;
import com.bhp.opusb.service.dto.MBiddingInvitationCriteria;
import com.bhp.opusb.service.MBiddingInvitationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingInvitation}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingInvitationResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingInvitationResource.class);

    private static final String ENTITY_NAME = "mBiddingInvitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingInvitationService mBiddingInvitationService;

    private final MBiddingInvitationQueryService mBiddingInvitationQueryService;

    public MBiddingInvitationResource(MBiddingInvitationService mBiddingInvitationService, MBiddingInvitationQueryService mBiddingInvitationQueryService) {
        this.mBiddingInvitationService = mBiddingInvitationService;
        this.mBiddingInvitationQueryService = mBiddingInvitationQueryService;
    }

    /**
     * {@code POST  /m-bidding-invitations} : Create a new mBiddingInvitation.
     *
     * @param mBiddingInvitationDTO the mBiddingInvitationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingInvitationDTO, or with status {@code 400 (Bad Request)} if the mBiddingInvitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-invitations")
    public ResponseEntity<MBiddingInvitationDTO> createMBiddingInvitation(@Valid @RequestBody MBiddingInvitationDTO mBiddingInvitationDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingInvitation : {}", mBiddingInvitationDTO);
        if (mBiddingInvitationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingInvitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingInvitationDTO result = mBiddingInvitationService.save(mBiddingInvitationDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-invitations} : Updates an existing mBiddingInvitation.
     *
     * @param mBiddingInvitationDTO the mBiddingInvitationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingInvitationDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingInvitationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingInvitationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-invitations")
    public ResponseEntity<MBiddingInvitationDTO> updateMBiddingInvitation(@Valid @RequestBody MBiddingInvitationDTO mBiddingInvitationDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingInvitation : {}", mBiddingInvitationDTO);
        if (mBiddingInvitationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingInvitationDTO result = mBiddingInvitationService.save(mBiddingInvitationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingInvitationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-invitations} : get all the mBiddingInvitations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingInvitations in body.
     */
    @GetMapping("/m-bidding-invitations")
    public ResponseEntity<List<MBiddingInvitationDTO>> getAllMBiddingInvitations(MBiddingInvitationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingInvitations by criteria: {}", criteria);
        Page<MBiddingInvitationDTO> page = mBiddingInvitationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-invitations/count} : count all the mBiddingInvitations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-invitations/count")
    public ResponseEntity<Long> countMBiddingInvitations(MBiddingInvitationCriteria criteria) {
        log.debug("REST request to count MBiddingInvitations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingInvitationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-invitations/:id} : get the "id" mBiddingInvitation.
     *
     * @param id the id of the mBiddingInvitationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingInvitationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-invitations/{id}")
    public ResponseEntity<MBiddingInvitationDTO> getMBiddingInvitation(@PathVariable Long id) {
        log.debug("REST request to get MBiddingInvitation : {}", id);
        Optional<MBiddingInvitationDTO> mBiddingInvitationDTO = mBiddingInvitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingInvitationDTO);
    }

    /**
     * {@code DELETE  /m-bidding-invitations/:id} : delete the "id" mBiddingInvitation.
     *
     * @param id the id of the mBiddingInvitationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-invitations/{id}")
    public ResponseEntity<Void> deleteMBiddingInvitation(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingInvitation : {}", id);
        mBiddingInvitationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
