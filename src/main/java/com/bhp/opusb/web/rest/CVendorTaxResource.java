package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CVendorTaxService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CVendorTaxDTO;
import com.bhp.opusb.service.dto.CVendorTaxCriteria;
import com.bhp.opusb.service.CVendorTaxQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorTax}.
 */
@RestController
@RequestMapping("/api")
public class CVendorTaxResource {

    private final Logger log = LoggerFactory.getLogger(CVendorTaxResource.class);

    private static final String ENTITY_NAME = "cVendorTax";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorTaxService cVendorTaxService;

    private final CVendorTaxQueryService cVendorTaxQueryService;

    public CVendorTaxResource(CVendorTaxService cVendorTaxService, CVendorTaxQueryService cVendorTaxQueryService) {
        this.cVendorTaxService = cVendorTaxService;
        this.cVendorTaxQueryService = cVendorTaxQueryService;
    }

    /**
     * {@code POST  /c-vendor-taxes} : Create a new cVendorTax.
     *
     * @param cVendorTaxDTO the cVendorTaxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorTaxDTO, or with status {@code 400 (Bad Request)} if the cVendorTax has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-taxes")
    public ResponseEntity<CVendorTaxDTO> createCVendorTax(@Valid @RequestBody CVendorTaxDTO cVendorTaxDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorTax : {}", cVendorTaxDTO);
        if (cVendorTaxDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorTaxDTO result = cVendorTaxService.save(cVendorTaxDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-taxes} : Updates an existing cVendorTax.
     *
     * @param cVendorTaxDTO the cVendorTaxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorTaxDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorTaxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorTaxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-taxes")
    public ResponseEntity<CVendorTaxDTO> updateCVendorTax(@Valid @RequestBody CVendorTaxDTO cVendorTaxDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorTax : {}", cVendorTaxDTO);
        if (cVendorTaxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorTaxDTO result = cVendorTaxService.save(cVendorTaxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorTaxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-taxes} : get all the cVendorTaxes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorTaxes in body.
     */
    @GetMapping("/c-vendor-taxes")
    public ResponseEntity<List<CVendorTaxDTO>> getAllCVendorTaxes(CVendorTaxCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorTaxes by criteria: {}", criteria);
        Page<CVendorTaxDTO> page = cVendorTaxQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-taxes/count} : count all the cVendorTaxes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-taxes/count")
    public ResponseEntity<Long> countCVendorTaxes(CVendorTaxCriteria criteria) {
        log.debug("REST request to count CVendorTaxes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorTaxQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-taxes/:id} : get the "id" cVendorTax.
     *
     * @param id the id of the cVendorTaxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorTaxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-taxes/{id}")
    public ResponseEntity<CVendorTaxDTO> getCVendorTax(@PathVariable Long id) {
        log.debug("REST request to get CVendorTax : {}", id);
        Optional<CVendorTaxDTO> cVendorTaxDTO = cVendorTaxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorTaxDTO);
    }

    /**
     * {@code DELETE  /c-vendor-taxes/:id} : delete the "id" cVendorTax.
     *
     * @param id the id of the cVendorTaxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-taxes/{id}")
    public ResponseEntity<Void> deleteCVendorTax(@PathVariable Long id) {
        log.debug("REST request to delete CVendorTax : {}", id);
        cVendorTaxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
