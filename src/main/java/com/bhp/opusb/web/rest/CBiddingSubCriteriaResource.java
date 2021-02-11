package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CBiddingSubCriteriaService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaDTO;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaCriteria;
import com.bhp.opusb.service.CBiddingSubCriteriaQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CBiddingSubCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CBiddingSubCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CBiddingSubCriteriaResource.class);

    private static final String ENTITY_NAME = "cBiddingSubCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBiddingSubCriteriaService cBiddingSubCriteriaService;

    private final CBiddingSubCriteriaQueryService cBiddingSubCriteriaQueryService;

    public CBiddingSubCriteriaResource(CBiddingSubCriteriaService cBiddingSubCriteriaService, CBiddingSubCriteriaQueryService cBiddingSubCriteriaQueryService) {
        this.cBiddingSubCriteriaService = cBiddingSubCriteriaService;
        this.cBiddingSubCriteriaQueryService = cBiddingSubCriteriaQueryService;
    }

    /**
     * {@code POST  /c-bidding-sub-criteria} : Create a new cBiddingSubCriteria.
     *
     * @param cBiddingSubCriteriaDTO the cBiddingSubCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBiddingSubCriteriaDTO, or with status {@code 400 (Bad Request)} if the cBiddingSubCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-bidding-sub-criteria")
    public ResponseEntity<CBiddingSubCriteriaDTO> createCBiddingSubCriteria(@Valid @RequestBody CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CBiddingSubCriteria : {}", cBiddingSubCriteriaDTO);
        if (cBiddingSubCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBiddingSubCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBiddingSubCriteriaDTO result = cBiddingSubCriteriaService.save(cBiddingSubCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-bidding-sub-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-bidding-sub-criteria} : Updates an existing cBiddingSubCriteria.
     *
     * @param cBiddingSubCriteriaDTO the cBiddingSubCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBiddingSubCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cBiddingSubCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBiddingSubCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-bidding-sub-criteria")
    public ResponseEntity<CBiddingSubCriteriaDTO> updateCBiddingSubCriteria(@Valid @RequestBody CBiddingSubCriteriaDTO cBiddingSubCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CBiddingSubCriteria : {}", cBiddingSubCriteriaDTO);
        if (cBiddingSubCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBiddingSubCriteriaDTO result = cBiddingSubCriteriaService.save(cBiddingSubCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBiddingSubCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-bidding-sub-criteria} : get all the cBiddingSubCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBiddingSubCriteria in body.
     */
    @GetMapping("/c-bidding-sub-criteria")
    public ResponseEntity<List<CBiddingSubCriteriaDTO>> getAllCBiddingSubCriteria(CBiddingSubCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBiddingSubCriteria by criteria: {}", criteria);
        Page<CBiddingSubCriteriaDTO> page = cBiddingSubCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-bidding-sub-criteria/count} : count all the cBiddingSubCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-bidding-sub-criteria/count")
    public ResponseEntity<Long> countCBiddingSubCriteria(CBiddingSubCriteriaCriteria criteria) {
        log.debug("REST request to count CBiddingSubCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBiddingSubCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-bidding-sub-criteria/:id} : get the "id" cBiddingSubCriteria.
     *
     * @param id the id of the cBiddingSubCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBiddingSubCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-bidding-sub-criteria/{id}")
    public ResponseEntity<CBiddingSubCriteriaDTO> getCBiddingSubCriteria(@PathVariable Long id) {
        log.debug("REST request to get CBiddingSubCriteria : {}", id);
        Optional<CBiddingSubCriteriaDTO> cBiddingSubCriteriaDTO = cBiddingSubCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBiddingSubCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-bidding-sub-criteria/:id} : delete the "id" cBiddingSubCriteria.
     *
     * @param id the id of the cBiddingSubCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-bidding-sub-criteria/{id}")
    public ResponseEntity<Void> deleteCBiddingSubCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CBiddingSubCriteria : {}", id);
        cBiddingSubCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
