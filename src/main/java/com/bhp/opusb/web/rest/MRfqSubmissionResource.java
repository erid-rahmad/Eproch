package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MRfqSubmissionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MRfqSubmissionDTO;
import com.bhp.opusb.service.dto.MRfqSubmissionCriteria;
import com.bhp.opusb.service.MRfqSubmissionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MRfqSubmission}.
 */
@RestController
@RequestMapping("/api")
public class MRfqSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(MRfqSubmissionResource.class);

    private static final String ENTITY_NAME = "mRfqSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MRfqSubmissionService mRfqSubmissionService;

    private final MRfqSubmissionQueryService mRfqSubmissionQueryService;

    public MRfqSubmissionResource(MRfqSubmissionService mRfqSubmissionService, MRfqSubmissionQueryService mRfqSubmissionQueryService) {
        this.mRfqSubmissionService = mRfqSubmissionService;
        this.mRfqSubmissionQueryService = mRfqSubmissionQueryService;
    }

    /**
     * {@code POST  /m-rfq-submissions} : Create a new mRfqSubmission.
     *
     * @param mRfqSubmissionDTO the mRfqSubmissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRfqSubmissionDTO, or with status {@code 400 (Bad Request)} if the mRfqSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-rfq-submissions")
    public ResponseEntity<MRfqSubmissionDTO> createMRfqSubmission(@Valid @RequestBody MRfqSubmissionDTO mRfqSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to save MRfqSubmission : {}", mRfqSubmissionDTO);
        if (mRfqSubmissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRfqSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MRfqSubmissionDTO result = mRfqSubmissionService.save(mRfqSubmissionDTO);
        return ResponseEntity.created(new URI("/api/m-rfq-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-rfq-submissions} : Updates an existing mRfqSubmission.
     *
     * @param mRfqSubmissionDTO the mRfqSubmissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRfqSubmissionDTO,
     * or with status {@code 400 (Bad Request)} if the mRfqSubmissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRfqSubmissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-rfq-submissions")
    public ResponseEntity<MRfqSubmissionDTO> updateMRfqSubmission(@Valid @RequestBody MRfqSubmissionDTO mRfqSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to update MRfqSubmission : {}", mRfqSubmissionDTO);
        if (mRfqSubmissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MRfqSubmissionDTO result = mRfqSubmissionService.save(mRfqSubmissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mRfqSubmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-rfq-submissions} : get all the mRfqSubmissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRfqSubmissions in body.
     */
    @GetMapping("/m-rfq-submissions")
    public ResponseEntity<List<MRfqSubmissionDTO>> getAllMRfqSubmissions(MRfqSubmissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRfqSubmissions by criteria: {}", criteria);
        Page<MRfqSubmissionDTO> page = mRfqSubmissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-rfq-submissions/count} : count all the mRfqSubmissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-rfq-submissions/count")
    public ResponseEntity<Long> countMRfqSubmissions(MRfqSubmissionCriteria criteria) {
        log.debug("REST request to count MRfqSubmissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRfqSubmissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-rfq-submissions/:id} : get the "id" mRfqSubmission.
     *
     * @param id the id of the mRfqSubmissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRfqSubmissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-rfq-submissions/{id}")
    public ResponseEntity<MRfqSubmissionDTO> getMRfqSubmission(@PathVariable Long id) {
        log.debug("REST request to get MRfqSubmission : {}", id);
        Optional<MRfqSubmissionDTO> mRfqSubmissionDTO = mRfqSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRfqSubmissionDTO);
    }

    /**
     * {@code DELETE  /m-rfq-submissions/:id} : delete the "id" mRfqSubmission.
     *
     * @param id the id of the mRfqSubmissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-rfq-submissions/{id}")
    public ResponseEntity<Void> deleteMRfqSubmission(@PathVariable Long id) {
        log.debug("REST request to delete MRfqSubmission : {}", id);
        mRfqSubmissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
