package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorInvitationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorInvitationDTO;
import com.bhp.opusb.service.dto.MVendorInvitationCriteria;
import com.bhp.opusb.service.MVendorInvitationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorInvitation}.
 */
@RestController
@RequestMapping("/api")
public class MVendorInvitationResource {

    private final Logger log = LoggerFactory.getLogger(MVendorInvitationResource.class);

    private static final String ENTITY_NAME = "mVendorInvitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorInvitationService mVendorInvitationService;

    private final MVendorInvitationQueryService mVendorInvitationQueryService;

    public MVendorInvitationResource(MVendorInvitationService mVendorInvitationService, MVendorInvitationQueryService mVendorInvitationQueryService) {
        this.mVendorInvitationService = mVendorInvitationService;
        this.mVendorInvitationQueryService = mVendorInvitationQueryService;
    }

    /**
     * {@code POST  /m-vendor-invitations} : Create a new mVendorInvitation.
     *
     * @param mVendorInvitationDTO the mVendorInvitationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorInvitationDTO, or with status {@code 400 (Bad Request)} if the mVendorInvitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-invitations")
    public ResponseEntity<MVendorInvitationDTO> createMVendorInvitation(@Valid @RequestBody MVendorInvitationDTO mVendorInvitationDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorInvitation : {}", mVendorInvitationDTO);
        if (mVendorInvitationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorInvitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorInvitationDTO result = mVendorInvitationService.save(mVendorInvitationDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-invitations} : Updates an existing mVendorInvitation.
     *
     * @param mVendorInvitationDTO the mVendorInvitationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorInvitationDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorInvitationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorInvitationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-invitations")
    public ResponseEntity<MVendorInvitationDTO> updateMVendorInvitation(@Valid @RequestBody MVendorInvitationDTO mVendorInvitationDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorInvitation : {}", mVendorInvitationDTO);
        if (mVendorInvitationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorInvitationDTO result = mVendorInvitationService.save(mVendorInvitationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorInvitationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-invitations} : get all the mVendorInvitations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorInvitations in body.
     */
    @GetMapping("/m-vendor-invitations")
    public ResponseEntity<List<MVendorInvitationDTO>> getAllMVendorInvitations(MVendorInvitationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorInvitations by criteria: {}", criteria);
        Page<MVendorInvitationDTO> page = mVendorInvitationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-invitations/count} : count all the mVendorInvitations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-invitations/count")
    public ResponseEntity<Long> countMVendorInvitations(MVendorInvitationCriteria criteria) {
        log.debug("REST request to count MVendorInvitations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorInvitationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-invitations/:id} : get the "id" mVendorInvitation.
     *
     * @param id the id of the mVendorInvitationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorInvitationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-invitations/{id}")
    public ResponseEntity<MVendorInvitationDTO> getMVendorInvitation(@PathVariable Long id) {
        log.debug("REST request to get MVendorInvitation : {}", id);
        Optional<MVendorInvitationDTO> mVendorInvitationDTO = mVendorInvitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorInvitationDTO);
    }

    /**
     * {@code DELETE  /m-vendor-invitations/:id} : delete the "id" mVendorInvitation.
     *
     * @param id the id of the mVendorInvitationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-invitations/{id}")
    public ResponseEntity<Void> deleteMVendorInvitation(@PathVariable Long id) {
        log.debug("REST request to delete MVendorInvitation : {}", id);
        mVendorInvitationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
