package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CUnitOfMeasureService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CUnitOfMeasureDTO;
import com.bhp.opusb.service.dto.CUnitOfMeasureCriteria;
import com.bhp.opusb.service.CUnitOfMeasureQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CUnitOfMeasure}.
 */
@RestController
@RequestMapping("/api")
public class CUnitOfMeasureResource {

    private final Logger log = LoggerFactory.getLogger(CUnitOfMeasureResource.class);

    private static final String ENTITY_NAME = "cUnitOfMeasure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CUnitOfMeasureService cUnitOfMeasureService;

    private final CUnitOfMeasureQueryService cUnitOfMeasureQueryService;

    public CUnitOfMeasureResource(CUnitOfMeasureService cUnitOfMeasureService, CUnitOfMeasureQueryService cUnitOfMeasureQueryService) {
        this.cUnitOfMeasureService = cUnitOfMeasureService;
        this.cUnitOfMeasureQueryService = cUnitOfMeasureQueryService;
    }

    /**
     * {@code POST  /c-unit-of-measures} : Create a new cUnitOfMeasure.
     *
     * @param cUnitOfMeasureDTO the cUnitOfMeasureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cUnitOfMeasureDTO, or with status {@code 400 (Bad Request)} if the cUnitOfMeasure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-unit-of-measures")
    public ResponseEntity<CUnitOfMeasureDTO> createCUnitOfMeasure(@Valid @RequestBody CUnitOfMeasureDTO cUnitOfMeasureDTO) throws URISyntaxException {
        log.debug("REST request to save CUnitOfMeasure : {}", cUnitOfMeasureDTO);
        if (cUnitOfMeasureDTO.getId() != null) {
            throw new BadRequestAlertException("A new cUnitOfMeasure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CUnitOfMeasureDTO result = cUnitOfMeasureService.save(cUnitOfMeasureDTO);
        return ResponseEntity.created(new URI("/api/c-unit-of-measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-unit-of-measures} : Updates an existing cUnitOfMeasure.
     *
     * @param cUnitOfMeasureDTO the cUnitOfMeasureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cUnitOfMeasureDTO,
     * or with status {@code 400 (Bad Request)} if the cUnitOfMeasureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cUnitOfMeasureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-unit-of-measures")
    public ResponseEntity<CUnitOfMeasureDTO> updateCUnitOfMeasure(@Valid @RequestBody CUnitOfMeasureDTO cUnitOfMeasureDTO) throws URISyntaxException {
        log.debug("REST request to update CUnitOfMeasure : {}", cUnitOfMeasureDTO);
        if (cUnitOfMeasureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CUnitOfMeasureDTO result = cUnitOfMeasureService.save(cUnitOfMeasureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cUnitOfMeasureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-unit-of-measures} : get all the cUnitOfMeasures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cUnitOfMeasures in body.
     */
    @GetMapping("/c-unit-of-measures")
    public ResponseEntity<List<CUnitOfMeasureDTO>> getAllCUnitOfMeasures(CUnitOfMeasureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CUnitOfMeasures by criteria: {}", criteria);
        Page<CUnitOfMeasureDTO> page = cUnitOfMeasureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-unit-of-measures/count} : count all the cUnitOfMeasures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-unit-of-measures/count")
    public ResponseEntity<Long> countCUnitOfMeasures(CUnitOfMeasureCriteria criteria) {
        log.debug("REST request to count CUnitOfMeasures by criteria: {}", criteria);
        return ResponseEntity.ok().body(cUnitOfMeasureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-unit-of-measures/:id} : get the "id" cUnitOfMeasure.
     *
     * @param id the id of the cUnitOfMeasureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cUnitOfMeasureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-unit-of-measures/{id}")
    public ResponseEntity<CUnitOfMeasureDTO> getCUnitOfMeasure(@PathVariable Long id) {
        log.debug("REST request to get CUnitOfMeasure : {}", id);
        Optional<CUnitOfMeasureDTO> cUnitOfMeasureDTO = cUnitOfMeasureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cUnitOfMeasureDTO);
    }

    /**
     * {@code DELETE  /c-unit-of-measures/:id} : delete the "id" cUnitOfMeasure.
     *
     * @param id the id of the cUnitOfMeasureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-unit-of-measures/{id}")
    public ResponseEntity<Void> deleteCUnitOfMeasure(@PathVariable Long id) {
        log.debug("REST request to delete CUnitOfMeasure : {}", id);
        cUnitOfMeasureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
