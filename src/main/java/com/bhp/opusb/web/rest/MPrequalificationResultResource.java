package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationResultService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationResultDTO;
import com.bhp.opusb.service.dto.MPrequalResultPublish;
import com.bhp.opusb.service.dto.MPrequalificationResultCriteria;
import com.bhp.opusb.service.MPrequalificationResultQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationResult}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationResultResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationResultResource.class);

    private static final String ENTITY_NAME = "mPrequalificationResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationResultService mPrequalificationResultService;

    private final MPrequalificationResultQueryService mPrequalificationResultQueryService;

    public MPrequalificationResultResource(MPrequalificationResultService mPrequalificationResultService, MPrequalificationResultQueryService mPrequalificationResultQueryService) {
        this.mPrequalificationResultService = mPrequalificationResultService;
        this.mPrequalificationResultQueryService = mPrequalificationResultQueryService;
    }

    /**
     * {@code POST  /m-prequalification-results} : Create a new mPrequalificationResult.
     *
     * @param mPrequalificationResultDTO the mPrequalificationResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationResultDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-results")
    public ResponseEntity<MPrequalificationResultDTO> createMPrequalificationResult(@Valid @RequestBody MPrequalificationResultDTO mPrequalificationResultDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationResult : {}", mPrequalificationResultDTO);
        if (mPrequalificationResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationResultDTO result = mPrequalificationResultService.save(mPrequalificationResultDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/m-prequalification-results/publish")
    public ResponseEntity<MPrequalResultPublish> publishMPrequalificationResult(@Valid @RequestBody MPrequalResultPublish mPrequalResultPublish) throws URISyntaxException {
        log.debug("this mPrequalResultPublish {}",mPrequalResultPublish);
        mPrequalificationResultService.publish(mPrequalResultPublish);
        return ResponseEntity.ok(mPrequalResultPublish);
    }

    /**
     * {@code PUT  /m-prequalification-results} : Updates an existing mPrequalificationResult.
     *
     * @param mPrequalificationResultDTO the mPrequalificationResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationResultDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-results")
    public ResponseEntity<MPrequalificationResultDTO> updateMPrequalificationResult(@Valid @RequestBody MPrequalificationResultDTO mPrequalificationResultDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationResult : {}", mPrequalificationResultDTO);
        if (mPrequalificationResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationResultDTO result = mPrequalificationResultService.save(mPrequalificationResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-results} : get all the mPrequalificationResults.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationResults in body.
     */
    @GetMapping("/m-prequalification-results")
    public ResponseEntity<List<MPrequalificationResultDTO>> getAllMPrequalificationResults(MPrequalificationResultCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationResults by criteria: {}", criteria);
        Page<MPrequalificationResultDTO> page = mPrequalificationResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-results/count} : count all the mPrequalificationResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-results/count")
    public ResponseEntity<Long> countMPrequalificationResults(MPrequalificationResultCriteria criteria) {
        log.debug("REST request to count MPrequalificationResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-results/:id} : get the "id" mPrequalificationResult.
     *
     * @param id the id of the mPrequalificationResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-results/{id}")
    public ResponseEntity<MPrequalificationResultDTO> getMPrequalificationResult(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationResult : {}", id);
        Optional<MPrequalificationResultDTO> mPrequalificationResultDTO = mPrequalificationResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationResultDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-results/:id} : delete the "id" mPrequalificationResult.
     *
     * @param id the id of the mPrequalificationResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-results/{id}")
    public ResponseEntity<Void> deleteMPrequalificationResult(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationResult : {}", id);
        mPrequalificationResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
