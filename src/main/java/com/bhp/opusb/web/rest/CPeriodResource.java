package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPeriodService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPeriodDTO;
import com.bhp.opusb.service.dto.CPeriodCriteria;
import com.bhp.opusb.service.CPeriodQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPeriod}.
 */
@RestController
@RequestMapping("/api")
public class CPeriodResource {

    private final Logger log = LoggerFactory.getLogger(CPeriodResource.class);

    private static final String ENTITY_NAME = "cPeriod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPeriodService cPeriodService;

    private final CPeriodQueryService cPeriodQueryService;

    public CPeriodResource(CPeriodService cPeriodService, CPeriodQueryService cPeriodQueryService) {
        this.cPeriodService = cPeriodService;
        this.cPeriodQueryService = cPeriodQueryService;
    }

    /**
     * {@code POST  /c-periods} : Create a new cPeriod.
     *
     * @param cPeriodDTO the cPeriodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPeriodDTO, or with status {@code 400 (Bad Request)} if the cPeriod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-periods")
    public ResponseEntity<CPeriodDTO> createCPeriod(@Valid @RequestBody CPeriodDTO cPeriodDTO) throws URISyntaxException {
        log.debug("REST request to save CPeriod : {}", cPeriodDTO);
        if (cPeriodDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPeriodDTO result = cPeriodService.save(cPeriodDTO);
        return ResponseEntity.created(new URI("/api/c-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-periods} : Updates an existing cPeriod.
     *
     * @param cPeriodDTO the cPeriodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPeriodDTO,
     * or with status {@code 400 (Bad Request)} if the cPeriodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPeriodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-periods")
    public ResponseEntity<CPeriodDTO> updateCPeriod(@Valid @RequestBody CPeriodDTO cPeriodDTO) throws URISyntaxException {
        log.debug("REST request to update CPeriod : {}", cPeriodDTO);
        if (cPeriodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPeriodDTO result = cPeriodService.save(cPeriodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPeriodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-periods} : get all the cPeriods.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPeriods in body.
     */
    @GetMapping("/c-periods")
    public ResponseEntity<List<CPeriodDTO>> getAllCPeriods(CPeriodCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPeriods by criteria: {}", criteria);
        Page<CPeriodDTO> page = cPeriodQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-periods/count} : count all the cPeriods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-periods/count")
    public ResponseEntity<Long> countCPeriods(CPeriodCriteria criteria) {
        log.debug("REST request to count CPeriods by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPeriodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-periods/:id} : get the "id" cPeriod.
     *
     * @param id the id of the cPeriodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPeriodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-periods/{id}")
    public ResponseEntity<CPeriodDTO> getCPeriod(@PathVariable Long id) {
        log.debug("REST request to get CPeriod : {}", id);
        Optional<CPeriodDTO> cPeriodDTO = cPeriodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPeriodDTO);
    }

    /**
     * {@code DELETE  /c-periods/:id} : delete the "id" cPeriod.
     *
     * @param id the id of the cPeriodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-periods/{id}")
    public ResponseEntity<Void> deleteCPeriod(@PathVariable Long id) {
        log.debug("REST request to delete CPeriod : {}", id);
        cPeriodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
