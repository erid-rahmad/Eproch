package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CVendorBankAcctService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;
import com.bhp.opusb.service.dto.CVendorBankAcctCriteria;
import com.bhp.opusb.service.CVendorBankAcctQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorBankAcct}.
 */
@RestController
@RequestMapping("/api")
public class CVendorBankAcctResource {

    private final Logger log = LoggerFactory.getLogger(CVendorBankAcctResource.class);

    private static final String ENTITY_NAME = "cVendorBankAcct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorBankAcctService cVendorBankAcctService;

    private final CVendorBankAcctQueryService cVendorBankAcctQueryService;

    public CVendorBankAcctResource(CVendorBankAcctService cVendorBankAcctService, CVendorBankAcctQueryService cVendorBankAcctQueryService) {
        this.cVendorBankAcctService = cVendorBankAcctService;
        this.cVendorBankAcctQueryService = cVendorBankAcctQueryService;
    }

    /**
     * {@code POST  /c-vendor-bank-accts} : Create a new cVendorBankAcct.
     *
     * @param cVendorBankAcctDTO the cVendorBankAcctDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorBankAcctDTO, or with status {@code 400 (Bad Request)} if the cVendorBankAcct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-bank-accts")
    public ResponseEntity<CVendorBankAcctDTO> createCVendorBankAcct(@Valid @RequestBody CVendorBankAcctDTO cVendorBankAcctDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorBankAcct : {}", cVendorBankAcctDTO);
        if (cVendorBankAcctDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorBankAcct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorBankAcctDTO result = cVendorBankAcctService.save(cVendorBankAcctDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-bank-accts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-bank-accts} : Updates an existing cVendorBankAcct.
     *
     * @param cVendorBankAcctDTO the cVendorBankAcctDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorBankAcctDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorBankAcctDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorBankAcctDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-bank-accts")
    public ResponseEntity<CVendorBankAcctDTO> updateCVendorBankAcct(@Valid @RequestBody CVendorBankAcctDTO cVendorBankAcctDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorBankAcct : {}", cVendorBankAcctDTO);
        if (cVendorBankAcctDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorBankAcctDTO result = cVendorBankAcctService.save(cVendorBankAcctDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorBankAcctDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-bank-accts} : get all the cVendorBankAccts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorBankAccts in body.
     */
    @GetMapping("/c-vendor-bank-accts")
    public ResponseEntity<List<CVendorBankAcctDTO>> getAllCVendorBankAccts(CVendorBankAcctCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorBankAccts by criteria: {}", criteria);
        Page<CVendorBankAcctDTO> page = cVendorBankAcctQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-bank-accts/count} : count all the cVendorBankAccts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-bank-accts/count")
    public ResponseEntity<Long> countCVendorBankAccts(CVendorBankAcctCriteria criteria) {
        log.debug("REST request to count CVendorBankAccts by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorBankAcctQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-bank-accts/:id} : get the "id" cVendorBankAcct.
     *
     * @param id the id of the cVendorBankAcctDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorBankAcctDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-bank-accts/{id}")
    public ResponseEntity<CVendorBankAcctDTO> getCVendorBankAcct(@PathVariable Long id) {
        log.debug("REST request to get CVendorBankAcct : {}", id);
        Optional<CVendorBankAcctDTO> cVendorBankAcctDTO = cVendorBankAcctService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorBankAcctDTO);
    }

    /**
     * {@code DELETE  /c-vendor-bank-accts/:id} : delete the "id" cVendorBankAcct.
     *
     * @param id the id of the cVendorBankAcctDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-bank-accts/{id}")
    public ResponseEntity<Void> deleteCVendorBankAcct(@PathVariable Long id) {
        log.debug("REST request to delete CVendorBankAcct : {}", id);
        cVendorBankAcctService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
