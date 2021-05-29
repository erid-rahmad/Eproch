package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorConfirmationResponseService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseDTO;
import com.bhp.opusb.service.dto.MVendorConfirmationResponseCriteria;
import com.bhp.opusb.service.MVendorConfirmationResponseQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorConfirmationResponse}.
 */
@RestController
@RequestMapping("/api")
public class MVendorConfirmationResponseResource {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationResponseResource.class);

    private static final String ENTITY_NAME = "mVendorConfirmationResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorConfirmationResponseService mVendorConfirmationResponseService;

    private final MVendorConfirmationResponseQueryService mVendorConfirmationResponseQueryService;

    public MVendorConfirmationResponseResource(MVendorConfirmationResponseService mVendorConfirmationResponseService, MVendorConfirmationResponseQueryService mVendorConfirmationResponseQueryService) {
        this.mVendorConfirmationResponseService = mVendorConfirmationResponseService;
        this.mVendorConfirmationResponseQueryService = mVendorConfirmationResponseQueryService;
    }

    /**
     * {@code POST  /m-vendor-confirmation-responses} : Create a new mVendorConfirmationResponse.
     *
     * @param mVendorConfirmationResponseDTO the mVendorConfirmationResponseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorConfirmationResponseDTO, or with status {@code 400 (Bad Request)} if the mVendorConfirmationResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-confirmation-responses")
    public ResponseEntity<MVendorConfirmationResponseDTO> createMVendorConfirmationResponse(@Valid @RequestBody MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorConfirmationResponse : {}", mVendorConfirmationResponseDTO);
        if (mVendorConfirmationResponseDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorConfirmationResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorConfirmationResponseDTO result = mVendorConfirmationResponseService.save(mVendorConfirmationResponseDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-confirmation-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-confirmation-responses} : Updates an existing mVendorConfirmationResponse.
     *
     * @param mVendorConfirmationResponseDTO the mVendorConfirmationResponseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorConfirmationResponseDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorConfirmationResponseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorConfirmationResponseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-confirmation-responses")
    public ResponseEntity<MVendorConfirmationResponseDTO> updateMVendorConfirmationResponse(@Valid @RequestBody MVendorConfirmationResponseDTO mVendorConfirmationResponseDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorConfirmationResponse : {}", mVendorConfirmationResponseDTO);
        if (mVendorConfirmationResponseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorConfirmationResponseDTO result = mVendorConfirmationResponseService.save(mVendorConfirmationResponseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorConfirmationResponseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-confirmation-responses} : get all the mVendorConfirmationResponses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorConfirmationResponses in body.
     */
    @GetMapping("/m-vendor-confirmation-responses")
    public ResponseEntity<List<MVendorConfirmationResponseDTO>> getAllMVendorConfirmationResponses(MVendorConfirmationResponseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorConfirmationResponses by criteria: {}", criteria);
        Page<MVendorConfirmationResponseDTO> page = mVendorConfirmationResponseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-confirmation-responses/count} : count all the mVendorConfirmationResponses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-confirmation-responses/count")
    public ResponseEntity<Long> countMVendorConfirmationResponses(MVendorConfirmationResponseCriteria criteria) {
        log.debug("REST request to count MVendorConfirmationResponses by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorConfirmationResponseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-confirmation-responses/:id} : get the "id" mVendorConfirmationResponse.
     *
     * @param id the id of the mVendorConfirmationResponseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorConfirmationResponseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-confirmation-responses/{id}")
    public ResponseEntity<MVendorConfirmationResponseDTO> getMVendorConfirmationResponse(@PathVariable Long id) {
        log.debug("REST request to get MVendorConfirmationResponse : {}", id);
        Optional<MVendorConfirmationResponseDTO> mVendorConfirmationResponseDTO = mVendorConfirmationResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorConfirmationResponseDTO);
    }

    /**
     * {@code DELETE  /m-vendor-confirmation-responses/:id} : delete the "id" mVendorConfirmationResponse.
     *
     * @param id the id of the mVendorConfirmationResponseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-confirmation-responses/{id}")
    public ResponseEntity<Void> deleteMVendorConfirmationResponse(@PathVariable Long id) {
        log.debug("REST request to delete MVendorConfirmationResponse : {}", id);
        mVendorConfirmationResponseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
