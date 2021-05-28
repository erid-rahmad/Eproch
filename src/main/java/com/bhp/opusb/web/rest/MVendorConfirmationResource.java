package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorConfirmationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorConfirmationDTO;
import com.bhp.opusb.service.dto.MVendorConfirmationCriteria;
import com.bhp.opusb.service.MVendorConfirmationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorConfirmation}.
 */
@RestController
@RequestMapping("/api")
public class MVendorConfirmationResource {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationResource.class);

    private static final String ENTITY_NAME = "mVendorConfirmation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorConfirmationService mVendorConfirmationService;

    private final MVendorConfirmationQueryService mVendorConfirmationQueryService;

    public MVendorConfirmationResource(MVendorConfirmationService mVendorConfirmationService, MVendorConfirmationQueryService mVendorConfirmationQueryService) {
        this.mVendorConfirmationService = mVendorConfirmationService;
        this.mVendorConfirmationQueryService = mVendorConfirmationQueryService;
    }

    /**
     * {@code POST  /m-vendor-confirmations} : Create a new mVendorConfirmation.
     *
     * @param mVendorConfirmationDTO the mVendorConfirmationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorConfirmationDTO, or with status {@code 400 (Bad Request)} if the mVendorConfirmation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-confirmations")
    public ResponseEntity<MVendorConfirmationDTO> createMVendorConfirmation(@Valid @RequestBody MVendorConfirmationDTO mVendorConfirmationDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorConfirmation : {}", mVendorConfirmationDTO);
        if (mVendorConfirmationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorConfirmation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorConfirmationDTO result = mVendorConfirmationService.save(mVendorConfirmationDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-confirmations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-confirmations} : Updates an existing mVendorConfirmation.
     *
     * @param mVendorConfirmationDTO the mVendorConfirmationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorConfirmationDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorConfirmationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorConfirmationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-confirmations")
    public ResponseEntity<MVendorConfirmationDTO> updateMVendorConfirmation(@Valid @RequestBody MVendorConfirmationDTO mVendorConfirmationDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorConfirmation : {}", mVendorConfirmationDTO);
        if (mVendorConfirmationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorConfirmationDTO result = mVendorConfirmationService.save(mVendorConfirmationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorConfirmationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-confirmations} : get all the mVendorConfirmations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorConfirmations in body.
     */
    @GetMapping("/m-vendor-confirmations")
    public ResponseEntity<List<MVendorConfirmationDTO>> getAllMVendorConfirmations(MVendorConfirmationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorConfirmations by criteria: {}", criteria);
        Page<MVendorConfirmationDTO> page = mVendorConfirmationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-confirmations/count} : count all the mVendorConfirmations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-confirmations/count")
    public ResponseEntity<Long> countMVendorConfirmations(MVendorConfirmationCriteria criteria) {
        log.debug("REST request to count MVendorConfirmations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorConfirmationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-confirmations/:id} : get the "id" mVendorConfirmation.
     *
     * @param id the id of the mVendorConfirmationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorConfirmationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-confirmations/{id}")
    public ResponseEntity<MVendorConfirmationDTO> getMVendorConfirmation(@PathVariable Long id) {
        log.debug("REST request to get MVendorConfirmation : {}", id);
        Optional<MVendorConfirmationDTO> mVendorConfirmationDTO = mVendorConfirmationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorConfirmationDTO);
    }

    /**
     * {@code DELETE  /m-vendor-confirmations/:id} : delete the "id" mVendorConfirmation.
     *
     * @param id the id of the mVendorConfirmationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-confirmations/{id}")
    public ResponseEntity<Void> deleteMVendorConfirmation(@PathVariable Long id) {
        log.debug("REST request to delete MVendorConfirmation : {}", id);
        mVendorConfirmationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
