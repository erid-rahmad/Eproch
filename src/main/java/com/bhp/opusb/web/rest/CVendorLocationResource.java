package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CVendorLocationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CVendorLocationDTO;
import com.bhp.opusb.service.dto.CVendorLocationCriteria;
import com.bhp.opusb.service.CVendorLocationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorLocation}.
 */
@RestController
@RequestMapping("/api")
public class CVendorLocationResource {

    private final Logger log = LoggerFactory.getLogger(CVendorLocationResource.class);

    private static final String ENTITY_NAME = "cVendorLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorLocationService cVendorLocationService;

    private final CVendorLocationQueryService cVendorLocationQueryService;

    public CVendorLocationResource(CVendorLocationService cVendorLocationService, CVendorLocationQueryService cVendorLocationQueryService) {
        this.cVendorLocationService = cVendorLocationService;
        this.cVendorLocationQueryService = cVendorLocationQueryService;
    }

    /**
     * {@code POST  /c-vendor-locations} : Create a new cVendorLocation.
     *
     * @param cVendorLocationDTO the cVendorLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorLocationDTO, or with status {@code 400 (Bad Request)} if the cVendorLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-locations")
    public ResponseEntity<CVendorLocationDTO> createCVendorLocation(@Valid @RequestBody CVendorLocationDTO cVendorLocationDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorLocation : {}", cVendorLocationDTO);
        if (cVendorLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorLocationDTO result = cVendorLocationService.save(cVendorLocationDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-locations} : Updates an existing cVendorLocation.
     *
     * @param cVendorLocationDTO the cVendorLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorLocationDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-locations")
    public ResponseEntity<CVendorLocationDTO> updateCVendorLocation(@Valid @RequestBody CVendorLocationDTO cVendorLocationDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorLocation : {}", cVendorLocationDTO);
        if (cVendorLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorLocationDTO result = cVendorLocationService.save(cVendorLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorLocationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-locations} : get all the cVendorLocations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorLocations in body.
     */
    @GetMapping("/c-vendor-locations")
    public ResponseEntity<List<CVendorLocationDTO>> getAllCVendorLocations(CVendorLocationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorLocations by criteria: {}", criteria);
        Page<CVendorLocationDTO> page = cVendorLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-locations/count} : count all the cVendorLocations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-locations/count")
    public ResponseEntity<Long> countCVendorLocations(CVendorLocationCriteria criteria) {
        log.debug("REST request to count CVendorLocations by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorLocationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-locations/:id} : get the "id" cVendorLocation.
     *
     * @param id the id of the cVendorLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-locations/{id}")
    public ResponseEntity<CVendorLocationDTO> getCVendorLocation(@PathVariable Long id) {
        log.debug("REST request to get CVendorLocation : {}", id);
        Optional<CVendorLocationDTO> cVendorLocationDTO = cVendorLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorLocationDTO);
    }

    /**
     * {@code DELETE  /c-vendor-locations/:id} : delete the "id" cVendorLocation.
     *
     * @param id the id of the cVendorLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-locations/{id}")
    public ResponseEntity<Void> deleteCVendorLocation(@PathVariable Long id) {
        log.debug("REST request to delete CVendorLocation : {}", id);
        cVendorLocationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
