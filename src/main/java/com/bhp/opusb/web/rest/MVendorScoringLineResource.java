package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorScoringLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorScoringLineDTO;
import com.bhp.opusb.service.dto.MVendorScoringLineCriteria;
import com.bhp.opusb.service.MVendorScoringLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorScoringLine}.
 */
@RestController
@RequestMapping("/api")
public class MVendorScoringLineResource {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringLineResource.class);

    private static final String ENTITY_NAME = "mVendorScoringLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorScoringLineService mVendorScoringLineService;

    private final MVendorScoringLineQueryService mVendorScoringLineQueryService;

    public MVendorScoringLineResource(MVendorScoringLineService mVendorScoringLineService, MVendorScoringLineQueryService mVendorScoringLineQueryService) {
        this.mVendorScoringLineService = mVendorScoringLineService;
        this.mVendorScoringLineQueryService = mVendorScoringLineQueryService;
    }

    /**
     * {@code POST  /m-vendor-scoring-lines} : Create a new mVendorScoringLine.
     *
     * @param mVendorScoringLineDTO the mVendorScoringLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorScoringLineDTO, or with status {@code 400 (Bad Request)} if the mVendorScoringLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-scoring-lines")
    public ResponseEntity<MVendorScoringLineDTO> createMVendorScoringLine(@Valid @RequestBody MVendorScoringLineDTO mVendorScoringLineDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorScoringLine : {}", mVendorScoringLineDTO);
        if (mVendorScoringLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorScoringLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorScoringLineDTO result = mVendorScoringLineService.save(mVendorScoringLineDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-scoring-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-scoring-lines} : Updates an existing mVendorScoringLine.
     *
     * @param mVendorScoringLineDTO the mVendorScoringLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorScoringLineDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorScoringLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorScoringLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-scoring-lines")
    public ResponseEntity<MVendorScoringLineDTO> updateMVendorScoringLine(@Valid @RequestBody MVendorScoringLineDTO mVendorScoringLineDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorScoringLine : {}", mVendorScoringLineDTO);
        if (mVendorScoringLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorScoringLineDTO result = mVendorScoringLineService.save(mVendorScoringLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorScoringLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-scoring-lines} : get all the mVendorScoringLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorScoringLines in body.
     */
    @GetMapping("/m-vendor-scoring-lines")
    public ResponseEntity<List<MVendorScoringLineDTO>> getAllMVendorScoringLines(MVendorScoringLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorScoringLines by criteria: {}", criteria);
        Page<MVendorScoringLineDTO> page = mVendorScoringLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-scoring-lines/count} : count all the mVendorScoringLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-scoring-lines/count")
    public ResponseEntity<Long> countMVendorScoringLines(MVendorScoringLineCriteria criteria) {
        log.debug("REST request to count MVendorScoringLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorScoringLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-scoring-lines/:id} : get the "id" mVendorScoringLine.
     *
     * @param id the id of the mVendorScoringLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorScoringLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-scoring-lines/{id}")
    public ResponseEntity<MVendorScoringLineDTO> getMVendorScoringLine(@PathVariable Long id) {
        log.debug("REST request to get MVendorScoringLine : {}", id);
        Optional<MVendorScoringLineDTO> mVendorScoringLineDTO = mVendorScoringLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorScoringLineDTO);
    }

    /**
     * {@code DELETE  /m-vendor-scoring-lines/:id} : delete the "id" mVendorScoringLine.
     *
     * @param id the id of the mVendorScoringLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-scoring-lines/{id}")
    public ResponseEntity<Void> deleteMVendorScoringLine(@PathVariable Long id) {
        log.debug("REST request to delete MVendorScoringLine : {}", id);
        mVendorScoringLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
