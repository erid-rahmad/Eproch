package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationInvitationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationInvitationDTO;
import com.bhp.opusb.service.dto.MPrequalificationInvitationCriteria;
import com.bhp.opusb.service.MPrequalificationInvitationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationInvitation}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationInvitationResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationInvitationResource.class);

    private static final String ENTITY_NAME = "mPrequalificationInvitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationInvitationService mPrequalificationInvitationService;

    private final MPrequalificationInvitationQueryService mPrequalificationInvitationQueryService;

    public MPrequalificationInvitationResource(MPrequalificationInvitationService mPrequalificationInvitationService, MPrequalificationInvitationQueryService mPrequalificationInvitationQueryService) {
        this.mPrequalificationInvitationService = mPrequalificationInvitationService;
        this.mPrequalificationInvitationQueryService = mPrequalificationInvitationQueryService;
    }

    /**
     * {@code POST  /m-prequalification-invitations} : Create a new mPrequalificationInvitation.
     *
     * @param mPrequalificationInvitationDTO the mPrequalificationInvitationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationInvitationDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationInvitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-invitations")
    public ResponseEntity<MPrequalificationInvitationDTO> createMPrequalificationInvitation(@Valid @RequestBody MPrequalificationInvitationDTO mPrequalificationInvitationDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationInvitation : {}", mPrequalificationInvitationDTO);
        if (mPrequalificationInvitationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationInvitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationInvitationDTO result = mPrequalificationInvitationService.save(mPrequalificationInvitationDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-invitations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-invitations} : Updates an existing mPrequalificationInvitation.
     *
     * @param mPrequalificationInvitationDTO the mPrequalificationInvitationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationInvitationDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationInvitationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationInvitationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-invitations")
    public ResponseEntity<MPrequalificationInvitationDTO> updateMPrequalificationInvitation(@Valid @RequestBody MPrequalificationInvitationDTO mPrequalificationInvitationDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationInvitation : {}", mPrequalificationInvitationDTO);
        if (mPrequalificationInvitationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationInvitationDTO result = mPrequalificationInvitationService.save(mPrequalificationInvitationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationInvitationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-invitations} : get all the mPrequalificationInvitations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationInvitations in body.
     */
    @GetMapping("/m-prequalification-invitations")
    public ResponseEntity<List<MPrequalificationInvitationDTO>> getAllMPrequalificationInvitations(MPrequalificationInvitationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationInvitations by criteria: {}", criteria);
        Page<MPrequalificationInvitationDTO> page = mPrequalificationInvitationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-invitations/count} : count all the mPrequalificationInvitations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-invitations/count")
    public ResponseEntity<Long> countMPrequalificationInvitations(MPrequalificationInvitationCriteria criteria) {
        log.debug("REST request to count MPrequalificationInvitations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationInvitationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-invitations/:id} : get the "id" mPrequalificationInvitation.
     *
     * @param id the id of the mPrequalificationInvitationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationInvitationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-invitations/{id}")
    public ResponseEntity<MPrequalificationInvitationDTO> getMPrequalificationInvitation(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationInvitation : {}", id);
        Optional<MPrequalificationInvitationDTO> mPrequalificationInvitationDTO = mPrequalificationInvitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationInvitationDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-invitations/:id} : delete the "id" mPrequalificationInvitation.
     *
     * @param id the id of the mPrequalificationInvitationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-invitations/{id}")
    public ResponseEntity<Void> deleteMPrequalificationInvitation(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationInvitation : {}", id);
        mPrequalificationInvitationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
