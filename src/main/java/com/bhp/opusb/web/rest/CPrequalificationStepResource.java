package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalificationStepService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalificationStepDTO;
import com.bhp.opusb.service.dto.CPrequalificationStepCriteria;
import com.bhp.opusb.service.CPrequalificationStepQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalificationStep}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalificationStepResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationStepResource.class);

    private static final String ENTITY_NAME = "cPrequalificationStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalificationStepService cPrequalificationStepService;

    private final CPrequalificationStepQueryService cPrequalificationStepQueryService;

    public CPrequalificationStepResource(CPrequalificationStepService cPrequalificationStepService, CPrequalificationStepQueryService cPrequalificationStepQueryService) {
        this.cPrequalificationStepService = cPrequalificationStepService;
        this.cPrequalificationStepQueryService = cPrequalificationStepQueryService;
    }

    /**
     * {@code POST  /c-prequalification-steps} : Create a new cPrequalificationStep.
     *
     * @param cPrequalificationStepDTO the cPrequalificationStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalificationStepDTO, or with status {@code 400 (Bad Request)} if the cPrequalificationStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequalification-steps")
    public ResponseEntity<CPrequalificationStepDTO> createCPrequalificationStep(@Valid @RequestBody CPrequalificationStepDTO cPrequalificationStepDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalificationStep : {}", cPrequalificationStepDTO);
        if (cPrequalificationStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalificationStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalificationStepDTO result = cPrequalificationStepService.save(cPrequalificationStepDTO);
        return ResponseEntity.created(new URI("/api/c-prequalification-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequalification-steps} : Updates an existing cPrequalificationStep.
     *
     * @param cPrequalificationStepDTO the cPrequalificationStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalificationStepDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalificationStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalificationStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequalification-steps")
    public ResponseEntity<CPrequalificationStepDTO> updateCPrequalificationStep(@Valid @RequestBody CPrequalificationStepDTO cPrequalificationStepDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalificationStep : {}", cPrequalificationStepDTO);
        if (cPrequalificationStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalificationStepDTO result = cPrequalificationStepService.save(cPrequalificationStepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalificationStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequalification-steps} : get all the cPrequalificationSteps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalificationSteps in body.
     */
    @GetMapping("/c-prequalification-steps")
    public ResponseEntity<List<CPrequalificationStepDTO>> getAllCPrequalificationSteps(CPrequalificationStepCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalificationSteps by criteria: {}", criteria);
        Page<CPrequalificationStepDTO> page = cPrequalificationStepQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequalification-steps/count} : count all the cPrequalificationSteps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequalification-steps/count")
    public ResponseEntity<Long> countCPrequalificationSteps(CPrequalificationStepCriteria criteria) {
        log.debug("REST request to count CPrequalificationSteps by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalificationStepQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequalification-steps/:id} : get the "id" cPrequalificationStep.
     *
     * @param id the id of the cPrequalificationStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalificationStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequalification-steps/{id}")
    public ResponseEntity<CPrequalificationStepDTO> getCPrequalificationStep(@PathVariable Long id) {
        log.debug("REST request to get CPrequalificationStep : {}", id);
        Optional<CPrequalificationStepDTO> cPrequalificationStepDTO = cPrequalificationStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalificationStepDTO);
    }

    /**
     * {@code DELETE  /c-prequalification-steps/:id} : delete the "id" cPrequalificationStep.
     *
     * @param id the id of the cPrequalificationStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequalification-steps/{id}")
    public ResponseEntity<Void> deleteCPrequalificationStep(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalificationStep : {}", id);
        cPrequalificationStepService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
