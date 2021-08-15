package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MQuoteSupplierService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MQuoteSupplierDTO;
import com.bhp.opusb.service.dto.MQuoteSupplierCriteria;
import com.bhp.opusb.service.MQuoteSupplierQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MQuoteSupplier}.
 */
@RestController
@RequestMapping("/api")
public class MQuoteSupplierResource {

    private final Logger log = LoggerFactory.getLogger(MQuoteSupplierResource.class);

    private static final String ENTITY_NAME = "mQuoteSupplier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MQuoteSupplierService mQuoteSupplierService;

    private final MQuoteSupplierQueryService mQuoteSupplierQueryService;

    public MQuoteSupplierResource(MQuoteSupplierService mQuoteSupplierService, MQuoteSupplierQueryService mQuoteSupplierQueryService) {
        this.mQuoteSupplierService = mQuoteSupplierService;
        this.mQuoteSupplierQueryService = mQuoteSupplierQueryService;
    }

    /**
     * {@code POST  /m-quote-suppliers} : Create a new mQuoteSupplier.
     *
     * @param mQuoteSupplierDTO the mQuoteSupplierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mQuoteSupplierDTO, or with status {@code 400 (Bad Request)} if the mQuoteSupplier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-quote-suppliers")
    public ResponseEntity<MQuoteSupplierDTO> createMQuoteSupplier(@Valid @RequestBody MQuoteSupplierDTO mQuoteSupplierDTO) throws URISyntaxException {
        log.debug("REST request to save MQuoteSupplier : {}", mQuoteSupplierDTO);
        if (mQuoteSupplierDTO.getId() != null) {
            throw new BadRequestAlertException("A new mQuoteSupplier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MQuoteSupplierDTO result = mQuoteSupplierService.save(mQuoteSupplierDTO);
        return ResponseEntity.created(new URI("/api/m-quote-suppliers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-quote-suppliers} : Create a new mQuoteSupplier.
     *
     * @param mQuoteSupplierDTO the mQuoteSupplierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mQuoteSupplierDTO, or with status {@code 400 (Bad Request)} if the mQuoteSupplier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-quote-suppliers/save-all")
    public ResponseEntity<List<MQuoteSupplierDTO>> createMQuoteSuppliers(@RequestBody List<MQuoteSupplierDTO> mQuoteSupplierDTOs) throws URISyntaxException {
        log.debug("REST request to save MQuoteSuppliers : {}", mQuoteSupplierDTOs);
        if (mQuoteSupplierDTOs.size() == 0) {
            throw new BadRequestAlertException("Cannot save an empty list", ENTITY_NAME, "idexists");
        }
        List<MQuoteSupplierDTO> result = mQuoteSupplierService.saveAll(mQuoteSupplierDTOs);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-quote-suppliers} : Updates an existing mQuoteSupplier.
     *
     * @param mQuoteSupplierDTO the mQuoteSupplierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mQuoteSupplierDTO,
     * or with status {@code 400 (Bad Request)} if the mQuoteSupplierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mQuoteSupplierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-quote-suppliers")
    public ResponseEntity<MQuoteSupplierDTO> updateMQuoteSupplier(@Valid @RequestBody MQuoteSupplierDTO mQuoteSupplierDTO) throws URISyntaxException {
        log.debug("REST request to update MQuoteSupplier : {}", mQuoteSupplierDTO);
        if (mQuoteSupplierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MQuoteSupplierDTO result = mQuoteSupplierService.save(mQuoteSupplierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mQuoteSupplierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-quote-suppliers} : get all the mQuoteSuppliers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mQuoteSuppliers in body.
     */
    @GetMapping("/m-quote-suppliers")
    public ResponseEntity<List<MQuoteSupplierDTO>> getAllMQuoteSuppliers(MQuoteSupplierCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MQuoteSuppliers by criteria: {}", criteria);
        Page<MQuoteSupplierDTO> page = mQuoteSupplierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-quote-suppliers/count} : count all the mQuoteSuppliers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-quote-suppliers/count")
    public ResponseEntity<Long> countMQuoteSuppliers(MQuoteSupplierCriteria criteria) {
        log.debug("REST request to count MQuoteSuppliers by criteria: {}", criteria);
        return ResponseEntity.ok().body(mQuoteSupplierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-quote-suppliers/:id} : get the "id" mQuoteSupplier.
     *
     * @param id the id of the mQuoteSupplierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mQuoteSupplierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-quote-suppliers/{id}")
    public ResponseEntity<MQuoteSupplierDTO> getMQuoteSupplier(@PathVariable Long id) {
        log.debug("REST request to get MQuoteSupplier : {}", id);
        Optional<MQuoteSupplierDTO> mQuoteSupplierDTO = mQuoteSupplierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mQuoteSupplierDTO);
    }

    /**
     * {@code DELETE  /m-quote-suppliers/:id} : delete the "id" mQuoteSupplier.
     *
     * @param id the id of the mQuoteSupplierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-quote-suppliers/{id}")
    public ResponseEntity<Void> deleteMQuoteSupplier(@PathVariable Long id) {
        log.debug("REST request to delete MQuoteSupplier : {}", id);
        mQuoteSupplierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
