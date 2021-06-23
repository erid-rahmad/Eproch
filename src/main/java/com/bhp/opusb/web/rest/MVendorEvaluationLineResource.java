package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorEvaluationLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorEvaluationLineDTO;
import com.bhp.opusb.service.dto.MVendorEvaluationLineCriteria;
import com.bhp.opusb.service.MVendorEvaluationLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorEvaluationLine}.
 */
@RestController
@RequestMapping("/api")
public class MVendorEvaluationLineResource {

    private final Logger log = LoggerFactory.getLogger(MVendorEvaluationLineResource.class);

    private static final String ENTITY_NAME = "mVendorEvaluationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorEvaluationLineService mVendorEvaluationLineService;

    private final MVendorEvaluationLineQueryService mVendorEvaluationLineQueryService;

    public MVendorEvaluationLineResource(MVendorEvaluationLineService mVendorEvaluationLineService, MVendorEvaluationLineQueryService mVendorEvaluationLineQueryService) {
        this.mVendorEvaluationLineService = mVendorEvaluationLineService;
        this.mVendorEvaluationLineQueryService = mVendorEvaluationLineQueryService;
    }

    /**
     * {@code POST  /m-vendor-evaluation-lines} : Create a new mVendorEvaluationLine.
     *
     * @param mVendorEvaluationLineDTO the mVendorEvaluationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorEvaluationLineDTO, or with status {@code 400 (Bad Request)} if the mVendorEvaluationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-evaluation-lines")
    public ResponseEntity<MVendorEvaluationLineDTO> createMVendorEvaluationLine(@Valid @RequestBody MVendorEvaluationLineDTO mVendorEvaluationLineDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorEvaluationLine : {}", mVendorEvaluationLineDTO);
        if (mVendorEvaluationLineDTO.getId() != null) {
            updateMVendorEvaluationLine(mVendorEvaluationLineDTO);
            return ResponseEntity.ok(mVendorEvaluationLineDTO);
        }
        MVendorEvaluationLineDTO result = mVendorEvaluationLineService.save(mVendorEvaluationLineDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-evaluation-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-evaluation-lines} : Updates an existing mVendorEvaluationLine.
     *
     * @param mVendorEvaluationLineDTO the mVendorEvaluationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorEvaluationLineDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorEvaluationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorEvaluationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-evaluation-lines")
    public ResponseEntity<MVendorEvaluationLineDTO> updateMVendorEvaluationLine(@Valid @RequestBody MVendorEvaluationLineDTO mVendorEvaluationLineDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorEvaluationLine : {}", mVendorEvaluationLineDTO);
        if (mVendorEvaluationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorEvaluationLineDTO result = mVendorEvaluationLineService.save(mVendorEvaluationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorEvaluationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-evaluation-lines} : get all the mVendorEvaluationLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorEvaluationLines in body.
     */
    @GetMapping("/m-vendor-evaluation-lines")
    public ResponseEntity<List<MVendorEvaluationLineDTO>> getAllMVendorEvaluationLines(MVendorEvaluationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorEvaluationLines by criteria: {}", criteria);
        Page<MVendorEvaluationLineDTO> page = mVendorEvaluationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-evaluation-lines/count} : count all the mVendorEvaluationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-evaluation-lines/count")
    public ResponseEntity<Long> countMVendorEvaluationLines(MVendorEvaluationLineCriteria criteria) {
        log.debug("REST request to count MVendorEvaluationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorEvaluationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-evaluation-lines/:id} : get the "id" mVendorEvaluationLine.
     *
     * @param id the id of the mVendorEvaluationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorEvaluationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-evaluation-lines/{id}")
    public ResponseEntity<MVendorEvaluationLineDTO> getMVendorEvaluationLine(@PathVariable Long id) {
        log.debug("REST request to get MVendorEvaluationLine : {}", id);
        Optional<MVendorEvaluationLineDTO> mVendorEvaluationLineDTO = mVendorEvaluationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorEvaluationLineDTO);
    }

    /**
     * {@code DELETE  /m-vendor-evaluation-lines/:id} : delete the "id" mVendorEvaluationLine.
     *
     * @param id the id of the mVendorEvaluationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-evaluation-lines/{id}")
    public ResponseEntity<Void> deleteMVendorEvaluationLine(@PathVariable Long id) {
        log.debug("REST request to delete MVendorEvaluationLine : {}", id);
        mVendorEvaluationLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
