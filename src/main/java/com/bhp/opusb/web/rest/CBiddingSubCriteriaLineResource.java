package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CBiddingSubCriteriaLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineDTO;
import com.bhp.opusb.service.dto.CBiddingSubCriteriaLineCriteria;
import com.bhp.opusb.service.CBiddingSubCriteriaLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CBiddingSubCriteriaLine}.
 */
@RestController
@RequestMapping("/api")
public class CBiddingSubCriteriaLineResource {

    private final Logger log = LoggerFactory.getLogger(CBiddingSubCriteriaLineResource.class);

    private static final String ENTITY_NAME = "cBiddingSubCriteriaLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBiddingSubCriteriaLineService cBiddingSubCriteriaLineService;

    private final CBiddingSubCriteriaLineQueryService cBiddingSubCriteriaLineQueryService;

    public CBiddingSubCriteriaLineResource(CBiddingSubCriteriaLineService cBiddingSubCriteriaLineService, CBiddingSubCriteriaLineQueryService cBiddingSubCriteriaLineQueryService) {
        this.cBiddingSubCriteriaLineService = cBiddingSubCriteriaLineService;
        this.cBiddingSubCriteriaLineQueryService = cBiddingSubCriteriaLineQueryService;
    }

    /**
     * {@code POST  /c-bidding-sub-criteria-lines} : Create a new cBiddingSubCriteriaLine.
     *
     * @param cBiddingSubCriteriaLineDTO the cBiddingSubCriteriaLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBiddingSubCriteriaLineDTO, or with status {@code 400 (Bad Request)} if the cBiddingSubCriteriaLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-bidding-sub-criteria-lines")
    public ResponseEntity<CBiddingSubCriteriaLineDTO> createCBiddingSubCriteriaLine(@Valid @RequestBody CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO) throws URISyntaxException {
        log.debug("REST request to save CBiddingSubCriteriaLine : {}", cBiddingSubCriteriaLineDTO);
        if (cBiddingSubCriteriaLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBiddingSubCriteriaLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBiddingSubCriteriaLineDTO result = cBiddingSubCriteriaLineService.save(cBiddingSubCriteriaLineDTO);
        return ResponseEntity.created(new URI("/api/c-bidding-sub-criteria-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-bidding-sub-criteria-lines} : Updates an existing cBiddingSubCriteriaLine.
     *
     * @param cBiddingSubCriteriaLineDTO the cBiddingSubCriteriaLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBiddingSubCriteriaLineDTO,
     * or with status {@code 400 (Bad Request)} if the cBiddingSubCriteriaLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBiddingSubCriteriaLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-bidding-sub-criteria-lines")
    public ResponseEntity<CBiddingSubCriteriaLineDTO> updateCBiddingSubCriteriaLine(@Valid @RequestBody CBiddingSubCriteriaLineDTO cBiddingSubCriteriaLineDTO) throws URISyntaxException {
        log.debug("REST request to update CBiddingSubCriteriaLine : {}", cBiddingSubCriteriaLineDTO);
        if (cBiddingSubCriteriaLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBiddingSubCriteriaLineDTO result = cBiddingSubCriteriaLineService.save(cBiddingSubCriteriaLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBiddingSubCriteriaLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-bidding-sub-criteria-lines} : get all the cBiddingSubCriteriaLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBiddingSubCriteriaLines in body.
     */
    @GetMapping("/c-bidding-sub-criteria-lines")
    public ResponseEntity<List<CBiddingSubCriteriaLineDTO>> getAllCBiddingSubCriteriaLines(CBiddingSubCriteriaLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBiddingSubCriteriaLines by criteria: {}", criteria);
        Page<CBiddingSubCriteriaLineDTO> page = cBiddingSubCriteriaLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-bidding-sub-criteria-lines/count} : count all the cBiddingSubCriteriaLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-bidding-sub-criteria-lines/count")
    public ResponseEntity<Long> countCBiddingSubCriteriaLines(CBiddingSubCriteriaLineCriteria criteria) {
        log.debug("REST request to count CBiddingSubCriteriaLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBiddingSubCriteriaLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-bidding-sub-criteria-lines/:id} : get the "id" cBiddingSubCriteriaLine.
     *
     * @param id the id of the cBiddingSubCriteriaLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBiddingSubCriteriaLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-bidding-sub-criteria-lines/{id}")
    public ResponseEntity<CBiddingSubCriteriaLineDTO> getCBiddingSubCriteriaLine(@PathVariable Long id) {
        log.debug("REST request to get CBiddingSubCriteriaLine : {}", id);
        Optional<CBiddingSubCriteriaLineDTO> cBiddingSubCriteriaLineDTO = cBiddingSubCriteriaLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBiddingSubCriteriaLineDTO);
    }

    /**
     * {@code DELETE  /c-bidding-sub-criteria-lines/:id} : delete the "id" cBiddingSubCriteriaLine.
     *
     * @param id the id of the cBiddingSubCriteriaLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-bidding-sub-criteria-lines/{id}")
    public ResponseEntity<Void> deleteCBiddingSubCriteriaLine(@PathVariable Long id) {
        log.debug("REST request to delete CBiddingSubCriteriaLine : {}", id);
        cBiddingSubCriteriaLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
