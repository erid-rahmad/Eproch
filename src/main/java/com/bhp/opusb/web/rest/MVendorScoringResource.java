package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorScoringService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorScoringDTO;
import com.bhp.opusb.service.dto.MVendorScoringCriteria;
import com.bhp.opusb.service.MVendorScoringQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorScoring}.
 */
@RestController
@RequestMapping("/api")
public class MVendorScoringResource {

    private final Logger log = LoggerFactory.getLogger(MVendorScoringResource.class);

    private static final String ENTITY_NAME = "mVendorScoring";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorScoringService mVendorScoringService;

    private final MVendorScoringQueryService mVendorScoringQueryService;

    public MVendorScoringResource(MVendorScoringService mVendorScoringService, MVendorScoringQueryService mVendorScoringQueryService) {
        this.mVendorScoringService = mVendorScoringService;
        this.mVendorScoringQueryService = mVendorScoringQueryService;
    }

    /**
     * {@code POST  /m-vendor-scorings} : Create a new mVendorScoring.
     *
     * @param mVendorScoringDTO the mVendorScoringDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorScoringDTO, or with status {@code 400 (Bad Request)} if the mVendorScoring has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-scorings")
    public ResponseEntity<MVendorScoringDTO> createMVendorScoring(@Valid @RequestBody MVendorScoringDTO mVendorScoringDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorScoring : {}", mVendorScoringDTO);
        if (mVendorScoringDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorScoring cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorScoringDTO result = mVendorScoringService.save(mVendorScoringDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-scorings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-scorings} : Updates an existing mVendorScoring.
     *
     * @param mVendorScoringDTO the mVendorScoringDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorScoringDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorScoringDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorScoringDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-scorings")
    public ResponseEntity<MVendorScoringDTO> updateMVendorScoring(@Valid @RequestBody MVendorScoringDTO mVendorScoringDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorScoring : {}", mVendorScoringDTO);
        if (mVendorScoringDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorScoringDTO result = mVendorScoringService.save(mVendorScoringDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorScoringDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-scorings} : get all the mVendorScorings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorScorings in body.
     */
    @GetMapping("/m-vendor-scorings")
    public ResponseEntity<List<MVendorScoringDTO>> getAllMVendorScorings(MVendorScoringCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorScorings by criteria: {}", criteria);
        Page<MVendorScoringDTO> page = mVendorScoringQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-scorings/count} : count all the mVendorScorings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-scorings/count")
    public ResponseEntity<Long> countMVendorScorings(MVendorScoringCriteria criteria) {
        log.debug("REST request to count MVendorScorings by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorScoringQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-scorings/:id} : get the "id" mVendorScoring.
     *
     * @param id the id of the mVendorScoringDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorScoringDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-scorings/{id}")
    public ResponseEntity<MVendorScoringDTO> getMVendorScoring(@PathVariable Long id) {
        log.debug("REST request to get MVendorScoring : {}", id);
        Optional<MVendorScoringDTO> mVendorScoringDTO = mVendorScoringService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorScoringDTO);
    }

    /**
     * {@code DELETE  /m-vendor-scorings/:id} : delete the "id" mVendorScoring.
     *
     * @param id the id of the mVendorScoringDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-scorings/{id}")
    public ResponseEntity<Void> deleteMVendorScoring(@PathVariable Long id) {
        log.debug("REST request to delete MVendorScoring : {}", id);
        mVendorScoringService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
