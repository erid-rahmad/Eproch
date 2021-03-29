package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CVendorEvaluationLineQueryService;
import com.bhp.opusb.service.CVendorEvaluationLineService;
import com.bhp.opusb.service.dto.CVendorEvaluationLineCriteria;
import com.bhp.opusb.service.dto.CVendorEvaluationLineDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorEvaluationLine}.
 */
@RestController
@RequestMapping("/api")
public class CVendorEvaluationLineResource {

    private final Logger log = LoggerFactory.getLogger(CVendorEvaluationLineResource.class);

    private static final String ENTITY_NAME = "cVendorEvaluationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorEvaluationLineService cVendorEvaluationLineService;

    private final CVendorEvaluationLineQueryService cVendorEvaluationLineQueryService;

    public CVendorEvaluationLineResource(CVendorEvaluationLineService cVendorEvaluationLineService, CVendorEvaluationLineQueryService cVendorEvaluationLineQueryService) {
        this.cVendorEvaluationLineService = cVendorEvaluationLineService;
        this.cVendorEvaluationLineQueryService = cVendorEvaluationLineQueryService;
    }

    /**
     * {@code POST  /c-vendor-evaluation-lines} : Create a new cVendorEvaluationLine.
     *
     * @param cVendorEvaluationLineDTO the cVendorEvaluationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorEvaluationLineDTO, or with status {@code 400 (Bad Request)} if the cVendorEvaluationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-evaluation-lines")
    public ResponseEntity<CVendorEvaluationLineDTO> createCVendorEvaluationLine(@Valid @RequestBody CVendorEvaluationLineDTO cVendorEvaluationLineDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorEvaluationLine : {}", cVendorEvaluationLineDTO);
        if (cVendorEvaluationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorEvaluationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorEvaluationLineDTO result = cVendorEvaluationLineService.save(cVendorEvaluationLineDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-evaluation-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-evaluation-lines} : Updates an existing cVendorEvaluationLine.
     *
     * @param cVendorEvaluationLineDTO the cVendorEvaluationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorEvaluationLineDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorEvaluationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorEvaluationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-evaluation-lines")
    public ResponseEntity<CVendorEvaluationLineDTO> updateCVendorEvaluationLine(@Valid @RequestBody CVendorEvaluationLineDTO cVendorEvaluationLineDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorEvaluationLine : {}", cVendorEvaluationLineDTO);
        if (cVendorEvaluationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorEvaluationLineDTO result = cVendorEvaluationLineService.save(cVendorEvaluationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorEvaluationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-evaluation-lines} : get all the cVendorEvaluationLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorEvaluationLines in body.
     */
    @GetMapping("/c-vendor-evaluation-lines")
    public ResponseEntity<List<CVendorEvaluationLineDTO>> getAllCVendorEvaluationLines(CVendorEvaluationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorEvaluationLines by criteria: {}", criteria);
        Page<CVendorEvaluationLineDTO> page = cVendorEvaluationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-evaluation-lines/count} : count all the cVendorEvaluationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-evaluation-lines/count")
    public ResponseEntity<Long> countCVendorEvaluationLines(CVendorEvaluationLineCriteria criteria) {
        log.debug("REST request to count CVendorEvaluationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorEvaluationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-evaluation-lines/:id} : get the "id" cVendorEvaluationLine.
     *
     * @param id the id of the cVendorEvaluationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorEvaluationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-evaluation-lines/{id}")
    public ResponseEntity<CVendorEvaluationLineDTO> getCVendorEvaluationLine(@PathVariable Long id) {
        log.debug("REST request to get CVendorEvaluationLine : {}", id);
        Optional<CVendorEvaluationLineDTO> cVendorEvaluationLineDTO = cVendorEvaluationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorEvaluationLineDTO);
    }

    /**
     * {@code DELETE  /c-vendor-evaluation-lines/:id} : delete the "id" cVendorEvaluationLine.
     *
     * @param id the id of the cVendorEvaluationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-evaluation-lines/{id}")
    public ResponseEntity<Void> deleteCVendorEvaluationLine(@PathVariable Long id) {
        log.debug("REST request to delete CVendorEvaluationLine : {}", id);
        cVendorEvaluationLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
