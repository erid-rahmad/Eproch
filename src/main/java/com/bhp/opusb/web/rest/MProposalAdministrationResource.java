package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalAdministrationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalAdministrationDTO;
import com.bhp.opusb.service.dto.MProposalAdministrationCriteria;
import com.bhp.opusb.service.MProposalAdministrationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalAdministration}.
 */
@RestController
@RequestMapping("/api")
public class MProposalAdministrationResource {

    private final Logger log = LoggerFactory.getLogger(MProposalAdministrationResource.class);

    private static final String ENTITY_NAME = "mProposalAdministration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalAdministrationService mProposalAdministrationService;

    private final MProposalAdministrationQueryService mProposalAdministrationQueryService;

    public MProposalAdministrationResource(MProposalAdministrationService mProposalAdministrationService, MProposalAdministrationQueryService mProposalAdministrationQueryService) {
        this.mProposalAdministrationService = mProposalAdministrationService;
        this.mProposalAdministrationQueryService = mProposalAdministrationQueryService;
    }

    /**
     * {@code POST  /m-proposal-administrations} : Create a new mProposalAdministration.
     *
     * @param mProposalAdministrationDTO the mProposalAdministrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalAdministrationDTO, or with status {@code 400 (Bad Request)} if the mProposalAdministration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-administrations")
    public ResponseEntity<MProposalAdministrationDTO> createMProposalAdministration(@Valid @RequestBody MProposalAdministrationDTO mProposalAdministrationDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalAdministration : {}", mProposalAdministrationDTO);
        if (mProposalAdministrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalAdministration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalAdministrationDTO result = mProposalAdministrationService.save(mProposalAdministrationDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-administrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-proposal-administrations} : Updates an existing mProposalAdministration.
     *
     * @param mProposalAdministrationDTO the mProposalAdministrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalAdministrationDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalAdministrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalAdministrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-administrations")
    public ResponseEntity<MProposalAdministrationDTO> updateMProposalAdministration(@Valid @RequestBody MProposalAdministrationDTO mProposalAdministrationDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalAdministration : {}", mProposalAdministrationDTO);
        if (mProposalAdministrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalAdministrationDTO result = mProposalAdministrationService.save(mProposalAdministrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalAdministrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-administrations} : get all the mProposalAdministrations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalAdministrations in body.
     */
    @GetMapping("/m-proposal-administrations")
    public ResponseEntity<List<MProposalAdministrationDTO>> getAllMProposalAdministrations(MProposalAdministrationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalAdministrations by criteria: {}", criteria);
        Page<MProposalAdministrationDTO> page = mProposalAdministrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-administrations/count} : count all the mProposalAdministrations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-administrations/count")
    public ResponseEntity<Long> countMProposalAdministrations(MProposalAdministrationCriteria criteria) {
        log.debug("REST request to count MProposalAdministrations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalAdministrationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-administrations/:id} : get the "id" mProposalAdministration.
     *
     * @param id the id of the mProposalAdministrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalAdministrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-administrations/{id}")
    public ResponseEntity<MProposalAdministrationDTO> getMProposalAdministration(@PathVariable Long id) {
        log.debug("REST request to get MProposalAdministration : {}", id);
        Optional<MProposalAdministrationDTO> mProposalAdministrationDTO = mProposalAdministrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalAdministrationDTO);
    }

    /**
     * {@code DELETE  /m-proposal-administrations/:id} : delete the "id" mProposalAdministration.
     *
     * @param id the id of the mProposalAdministrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-administrations/{id}")
    public ResponseEntity<Void> deleteMProposalAdministration(@PathVariable Long id) {
        log.debug("REST request to delete MProposalAdministration : {}", id);
        mProposalAdministrationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
