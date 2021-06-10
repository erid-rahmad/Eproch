package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionInvitationQueryService;
import com.bhp.opusb.service.MAuctionInvitationService;
import com.bhp.opusb.service.dto.MAuctionInvitationCriteria;
import com.bhp.opusb.service.dto.MAuctionInvitationDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionInvitation}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionInvitationResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionInvitationResource.class);

    private static final String ENTITY_NAME = "mAuctionInvitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionInvitationService mAuctionInvitationService;

    private final MAuctionInvitationQueryService mAuctionInvitationQueryService;

    public MAuctionInvitationResource(MAuctionInvitationService mAuctionInvitationService, MAuctionInvitationQueryService mAuctionInvitationQueryService) {
        this.mAuctionInvitationService = mAuctionInvitationService;
        this.mAuctionInvitationQueryService = mAuctionInvitationQueryService;
    }

    /**
     * {@code POST  /m-auction-invitations} : Create a new mAuctionInvitation.
     *
     * @param mAuctionInvitationDTO the mAuctionInvitationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionInvitationDTO, or with status {@code 400 (Bad Request)} if the mAuctionInvitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-invitations")
    public ResponseEntity<MAuctionInvitationDTO> createMAuctionInvitation(@Valid @RequestBody MAuctionInvitationDTO mAuctionInvitationDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionInvitation : {}", mAuctionInvitationDTO);
        if (mAuctionInvitationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionInvitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionInvitationDTO result = mAuctionInvitationService.save(mAuctionInvitationDTO);
        return ResponseEntity.created(new URI("/api/m-auction-invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-invitations} : Updates an existing mAuctionInvitation.
     *
     * @param mAuctionInvitationDTO the mAuctionInvitationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionInvitationDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionInvitationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionInvitationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-invitations")
    public ResponseEntity<MAuctionInvitationDTO> updateMAuctionInvitation(@Valid @RequestBody MAuctionInvitationDTO mAuctionInvitationDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionInvitation : {}", mAuctionInvitationDTO);
        if (mAuctionInvitationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionInvitationDTO result = mAuctionInvitationService.save(mAuctionInvitationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionInvitationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-invitations} : get all the mAuctionInvitations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionInvitations in body.
     */
    @GetMapping("/m-auction-invitations")
    public ResponseEntity<List<MAuctionInvitationDTO>> getAllMAuctionInvitations(MAuctionInvitationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionInvitations by criteria: {}", criteria);
        Page<MAuctionInvitationDTO> page = mAuctionInvitationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-invitations/count} : count all the mAuctionInvitations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-invitations/count")
    public ResponseEntity<Long> countMAuctionInvitations(MAuctionInvitationCriteria criteria) {
        log.debug("REST request to count MAuctionInvitations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionInvitationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-invitations/:id} : get the "id" mAuctionInvitation.
     *
     * @param id the id of the mAuctionInvitationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionInvitationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-invitations/{id}")
    public ResponseEntity<MAuctionInvitationDTO> getMAuctionInvitation(@PathVariable Long id) {
        log.debug("REST request to get MAuctionInvitation : {}", id);
        Optional<MAuctionInvitationDTO> mAuctionInvitationDTO = mAuctionInvitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionInvitationDTO);
    }

    /**
     * {@code DELETE  /m-auction-invitations/:id} : delete the "id" mAuctionInvitation.
     *
     * @param id the id of the mAuctionInvitationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-invitations/{id}")
    public ResponseEntity<Void> deleteMAuctionInvitation(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionInvitation : {}", id);
        mAuctionInvitationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
