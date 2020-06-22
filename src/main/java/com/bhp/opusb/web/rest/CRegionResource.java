package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CRegionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CRegionDTO;
import com.bhp.opusb.service.dto.CRegionCriteria;
import com.bhp.opusb.service.CRegionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CRegion}.
 */
@RestController
@RequestMapping("/api")
public class CRegionResource {

    private final Logger log = LoggerFactory.getLogger(CRegionResource.class);

    private static final String ENTITY_NAME = "cRegion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRegionService cRegionService;

    private final CRegionQueryService cRegionQueryService;

    public CRegionResource(CRegionService cRegionService, CRegionQueryService cRegionQueryService) {
        this.cRegionService = cRegionService;
        this.cRegionQueryService = cRegionQueryService;
    }

    /**
     * {@code POST  /c-regions} : Create a new cRegion.
     *
     * @param cRegionDTO the cRegionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cRegionDTO, or with status {@code 400 (Bad Request)} if the cRegion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-regions")
    public ResponseEntity<CRegionDTO> createCRegion(@Valid @RequestBody CRegionDTO cRegionDTO) throws URISyntaxException {
        log.debug("REST request to save CRegion : {}", cRegionDTO);
        if (cRegionDTO.getId() != null) {
            throw new BadRequestAlertException("A new cRegion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CRegionDTO result = cRegionService.save(cRegionDTO);
        return ResponseEntity.created(new URI("/api/c-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-regions} : Updates an existing cRegion.
     *
     * @param cRegionDTO the cRegionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cRegionDTO,
     * or with status {@code 400 (Bad Request)} if the cRegionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cRegionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-regions")
    public ResponseEntity<CRegionDTO> updateCRegion(@Valid @RequestBody CRegionDTO cRegionDTO) throws URISyntaxException {
        log.debug("REST request to update CRegion : {}", cRegionDTO);
        if (cRegionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CRegionDTO result = cRegionService.save(cRegionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cRegionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-regions} : get all the cRegions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cRegions in body.
     */
    @GetMapping("/c-regions")
    public ResponseEntity<List<CRegionDTO>> getAllCRegions(CRegionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CRegions by criteria: {}", criteria);
        Page<CRegionDTO> page = cRegionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-regions/count} : count all the cRegions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-regions/count")
    public ResponseEntity<Long> countCRegions(CRegionCriteria criteria) {
        log.debug("REST request to count CRegions by criteria: {}", criteria);
        return ResponseEntity.ok().body(cRegionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-regions/:id} : get the "id" cRegion.
     *
     * @param id the id of the cRegionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cRegionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-regions/{id}")
    public ResponseEntity<CRegionDTO> getCRegion(@PathVariable Long id) {
        log.debug("REST request to get CRegion : {}", id);
        Optional<CRegionDTO> cRegionDTO = cRegionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cRegionDTO);
    }

    /**
     * {@code DELETE  /c-regions/:id} : delete the "id" cRegion.
     *
     * @param id the id of the cRegionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-regions/{id}")
    public ResponseEntity<Void> deleteCRegion(@PathVariable Long id) {
        log.debug("REST request to delete CRegion : {}", id);
        cRegionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
