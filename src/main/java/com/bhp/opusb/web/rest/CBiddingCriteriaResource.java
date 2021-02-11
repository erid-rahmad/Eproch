package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CBiddingCriteriaService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CBiddingCriteriaDTO;
import com.bhp.opusb.service.dto.CBiddingCriteriaCriteria;
import com.bhp.opusb.service.CBiddingCriteriaQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CBiddingCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CBiddingCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CBiddingCriteriaResource.class);

    private static final String ENTITY_NAME = "cBiddingCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBiddingCriteriaService cBiddingCriteriaService;

    private final CBiddingCriteriaQueryService cBiddingCriteriaQueryService;

    public CBiddingCriteriaResource(CBiddingCriteriaService cBiddingCriteriaService, CBiddingCriteriaQueryService cBiddingCriteriaQueryService) {
        this.cBiddingCriteriaService = cBiddingCriteriaService;
        this.cBiddingCriteriaQueryService = cBiddingCriteriaQueryService;
    }

    /**
     * {@code POST  /c-bidding-criteria} : Create a new cBiddingCriteria.
     *
     * @param cBiddingCriteriaDTO the cBiddingCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBiddingCriteriaDTO, or with status {@code 400 (Bad Request)} if the cBiddingCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-bidding-criteria")
    public ResponseEntity<CBiddingCriteriaDTO> createCBiddingCriteria(@Valid @RequestBody CBiddingCriteriaDTO cBiddingCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CBiddingCriteria : {}", cBiddingCriteriaDTO);
        if (cBiddingCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBiddingCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBiddingCriteriaDTO result = cBiddingCriteriaService.save(cBiddingCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-bidding-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-bidding-criteria} : Updates an existing cBiddingCriteria.
     *
     * @param cBiddingCriteriaDTO the cBiddingCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBiddingCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cBiddingCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBiddingCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-bidding-criteria")
    public ResponseEntity<CBiddingCriteriaDTO> updateCBiddingCriteria(@Valid @RequestBody CBiddingCriteriaDTO cBiddingCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CBiddingCriteria : {}", cBiddingCriteriaDTO);
        if (cBiddingCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBiddingCriteriaDTO result = cBiddingCriteriaService.save(cBiddingCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBiddingCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-bidding-criteria} : get all the cBiddingCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBiddingCriteria in body.
     */
    @GetMapping("/c-bidding-criteria")
    public ResponseEntity<List<CBiddingCriteriaDTO>> getAllCBiddingCriteria(CBiddingCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBiddingCriteria by criteria: {}", criteria);
        Page<CBiddingCriteriaDTO> page = cBiddingCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-bidding-criteria/count} : count all the cBiddingCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-bidding-criteria/count")
    public ResponseEntity<Long> countCBiddingCriteria(CBiddingCriteriaCriteria criteria) {
        log.debug("REST request to count CBiddingCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBiddingCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-bidding-criteria/:id} : get the "id" cBiddingCriteria.
     *
     * @param id the id of the cBiddingCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBiddingCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-bidding-criteria/{id}")
    public ResponseEntity<CBiddingCriteriaDTO> getCBiddingCriteria(@PathVariable Long id) {
        log.debug("REST request to get CBiddingCriteria : {}", id);
        Optional<CBiddingCriteriaDTO> cBiddingCriteriaDTO = cBiddingCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBiddingCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-bidding-criteria/:id} : delete the "id" cBiddingCriteria.
     *
     * @param id the id of the cBiddingCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-bidding-criteria/{id}")
    public ResponseEntity<Void> deleteCBiddingCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CBiddingCriteria : {}", id);
        cBiddingCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
