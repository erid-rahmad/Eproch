package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPersonInChargeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPersonInChargeDTO;
import com.bhp.opusb.service.dto.CPersonInChargeCriteria;
import com.bhp.opusb.service.CPersonInChargeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPersonInCharge}.
 */
@RestController
@RequestMapping("/api")
public class CPersonInChargeResource {

    private final Logger log = LoggerFactory.getLogger(CPersonInChargeResource.class);

    private static final String ENTITY_NAME = "cPersonInCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPersonInChargeService cPersonInChargeService;

    private final CPersonInChargeQueryService cPersonInChargeQueryService;

    public CPersonInChargeResource(CPersonInChargeService cPersonInChargeService, CPersonInChargeQueryService cPersonInChargeQueryService) {
        this.cPersonInChargeService = cPersonInChargeService;
        this.cPersonInChargeQueryService = cPersonInChargeQueryService;
    }

    /**
     * {@code POST  /c-person-in-charges} : Create a new cPersonInCharge.
     *
     * @param cPersonInChargeDTO the cPersonInChargeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPersonInChargeDTO, or with status {@code 400 (Bad Request)} if the cPersonInCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-person-in-charges")
    public ResponseEntity<CPersonInChargeDTO> createCPersonInCharge(@Valid @RequestBody CPersonInChargeDTO cPersonInChargeDTO) throws URISyntaxException {
        log.debug("REST request to save CPersonInCharge : {}", cPersonInChargeDTO);
        if (cPersonInChargeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPersonInCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPersonInChargeDTO result = cPersonInChargeService.save(cPersonInChargeDTO);
        return ResponseEntity.created(new URI("/api/c-person-in-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-person-in-charges} : Updates an existing cPersonInCharge.
     *
     * @param cPersonInChargeDTO the cPersonInChargeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPersonInChargeDTO,
     * or with status {@code 400 (Bad Request)} if the cPersonInChargeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPersonInChargeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-person-in-charges")
    public ResponseEntity<CPersonInChargeDTO> updateCPersonInCharge(@Valid @RequestBody CPersonInChargeDTO cPersonInChargeDTO) throws URISyntaxException {
        log.debug("REST request to update CPersonInCharge : {}", cPersonInChargeDTO);
        if (cPersonInChargeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPersonInChargeDTO result = cPersonInChargeService.save(cPersonInChargeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPersonInChargeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-person-in-charges} : get all the cPersonInCharges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPersonInCharges in body.
     */
    @GetMapping("/c-person-in-charges")
    public ResponseEntity<List<CPersonInChargeDTO>> getAllCPersonInCharges(CPersonInChargeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPersonInCharges by criteria: {}", criteria);
        Page<CPersonInChargeDTO> page = cPersonInChargeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-person-in-charges/count} : count all the cPersonInCharges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-person-in-charges/count")
    public ResponseEntity<Long> countCPersonInCharges(CPersonInChargeCriteria criteria) {
        log.debug("REST request to count CPersonInCharges by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPersonInChargeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-person-in-charges/:id} : get the "id" cPersonInCharge.
     *
     * @param id the id of the cPersonInChargeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPersonInChargeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-person-in-charges/{id}")
    public ResponseEntity<CPersonInChargeDTO> getCPersonInCharge(@PathVariable Long id) {
        log.debug("REST request to get CPersonInCharge : {}", id);
        Optional<CPersonInChargeDTO> cPersonInChargeDTO = cPersonInChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPersonInChargeDTO);
    }

    /**
     * {@code DELETE  /c-person-in-charges/:id} : delete the "id" cPersonInCharge.
     *
     * @param id the id of the cPersonInChargeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-person-in-charges/{id}")
    public ResponseEntity<Void> deleteCPersonInCharge(@PathVariable Long id) {
        log.debug("REST request to delete CPersonInCharge : {}", id);
        cPersonInChargeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
