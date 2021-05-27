package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorConfirmationLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorConfirmationLineDTO;
import com.bhp.opusb.service.dto.MVendorConfirmationLineCriteria;
import com.bhp.opusb.service.MVendorConfirmationLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorConfirmationLine}.
 */
@RestController
@RequestMapping("/api")
public class MVendorConfirmationLineResource {

    private final Logger log = LoggerFactory.getLogger(MVendorConfirmationLineResource.class);

    private static final String ENTITY_NAME = "mVendorConfirmationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorConfirmationLineService mVendorConfirmationLineService;

    private final MVendorConfirmationLineQueryService mVendorConfirmationLineQueryService;

    public MVendorConfirmationLineResource(MVendorConfirmationLineService mVendorConfirmationLineService, MVendorConfirmationLineQueryService mVendorConfirmationLineQueryService) {
        this.mVendorConfirmationLineService = mVendorConfirmationLineService;
        this.mVendorConfirmationLineQueryService = mVendorConfirmationLineQueryService;
    }

    /**
     * {@code POST  /m-vendor-confirmation-lines} : Create a new mVendorConfirmationLine.
     *
     * @param mVendorConfirmationLineDTO the mVendorConfirmationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorConfirmationLineDTO, or with status {@code 400 (Bad Request)} if the mVendorConfirmationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-confirmation-lines")
    public ResponseEntity<MVendorConfirmationLineDTO> createMVendorConfirmationLine(@Valid @RequestBody MVendorConfirmationLineDTO mVendorConfirmationLineDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorConfirmationLine : {}", mVendorConfirmationLineDTO);
        if (mVendorConfirmationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorConfirmationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorConfirmationLineDTO result = mVendorConfirmationLineService.save(mVendorConfirmationLineDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-confirmation-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-confirmation-lines} : Updates an existing mVendorConfirmationLine.
     *
     * @param mVendorConfirmationLineDTO the mVendorConfirmationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorConfirmationLineDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorConfirmationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorConfirmationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-confirmation-lines")
    public ResponseEntity<MVendorConfirmationLineDTO> updateMVendorConfirmationLine(@Valid @RequestBody MVendorConfirmationLineDTO mVendorConfirmationLineDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorConfirmationLine : {}", mVendorConfirmationLineDTO);
        if (mVendorConfirmationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorConfirmationLineDTO result = mVendorConfirmationLineService.save(mVendorConfirmationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorConfirmationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-confirmation-lines} : get all the mVendorConfirmationLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorConfirmationLines in body.
     */
    @GetMapping("/m-vendor-confirmation-lines")
    public ResponseEntity<List<MVendorConfirmationLineDTO>> getAllMVendorConfirmationLines(MVendorConfirmationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorConfirmationLines by criteria: {}", criteria);
        Page<MVendorConfirmationLineDTO> page = mVendorConfirmationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-confirmation-lines/count} : count all the mVendorConfirmationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-confirmation-lines/count")
    public ResponseEntity<Long> countMVendorConfirmationLines(MVendorConfirmationLineCriteria criteria) {
        log.debug("REST request to count MVendorConfirmationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorConfirmationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-confirmation-lines/:id} : get the "id" mVendorConfirmationLine.
     *
     * @param id the id of the mVendorConfirmationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorConfirmationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-confirmation-lines/{id}")
    public ResponseEntity<MVendorConfirmationLineDTO> getMVendorConfirmationLine(@PathVariable Long id) {
        log.debug("REST request to get MVendorConfirmationLine : {}", id);
        Optional<MVendorConfirmationLineDTO> mVendorConfirmationLineDTO = mVendorConfirmationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorConfirmationLineDTO);
    }

    /**
     * {@code DELETE  /m-vendor-confirmation-lines/:id} : delete the "id" mVendorConfirmationLine.
     *
     * @param id the id of the mVendorConfirmationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-confirmation-lines/{id}")
    public ResponseEntity<Void> deleteMVendorConfirmationLine(@PathVariable Long id) {
        log.debug("REST request to delete MVendorConfirmationLine : {}", id);
        mVendorConfirmationLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
