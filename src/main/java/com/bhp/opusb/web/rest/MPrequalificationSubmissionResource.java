package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationSubmissionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionDTO;
import com.bhp.opusb.service.dto.MPrequalificationSubmissionCriteria;
import com.bhp.opusb.service.MPrequalificationSubmissionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationSubmission}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationSubmissionResource.class);

    private static final String ENTITY_NAME = "mPrequalificationSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationSubmissionService mPrequalificationSubmissionService;

    private final MPrequalificationSubmissionQueryService mPrequalificationSubmissionQueryService;

    public MPrequalificationSubmissionResource(MPrequalificationSubmissionService mPrequalificationSubmissionService, MPrequalificationSubmissionQueryService mPrequalificationSubmissionQueryService) {
        this.mPrequalificationSubmissionService = mPrequalificationSubmissionService;
        this.mPrequalificationSubmissionQueryService = mPrequalificationSubmissionQueryService;
    }

    /**
     * {@code POST  /m-prequalification-submissions} : Create a new mPrequalificationSubmission.
     *
     * @param mPrequalificationSubmissionDTO the mPrequalificationSubmissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationSubmissionDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-submissions")
    public ResponseEntity<MPrequalificationSubmissionDTO> createMPrequalificationSubmission(@Valid @RequestBody MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationSubmission : {}", mPrequalificationSubmissionDTO);
        if (mPrequalificationSubmissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationSubmissionDTO result = mPrequalificationSubmissionService.save(mPrequalificationSubmissionDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-submissions} : Updates an existing mPrequalificationSubmission.
     *
     * @param mPrequalificationSubmissionDTO the mPrequalificationSubmissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationSubmissionDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationSubmissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationSubmissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-submissions")
    public ResponseEntity<MPrequalificationSubmissionDTO> updateMPrequalificationSubmission(@Valid @RequestBody MPrequalificationSubmissionDTO mPrequalificationSubmissionDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationSubmission : {}", mPrequalificationSubmissionDTO);
        if (mPrequalificationSubmissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationSubmissionDTO result = mPrequalificationSubmissionService.save(mPrequalificationSubmissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationSubmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-submissions} : get all the mPrequalificationSubmissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationSubmissions in body.
     */
    @GetMapping("/m-prequalification-submissions")
    public ResponseEntity<List<MPrequalificationSubmissionDTO>> getAllMPrequalificationSubmissions(MPrequalificationSubmissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationSubmissions by criteria: {}", criteria);
        Page<MPrequalificationSubmissionDTO> page = mPrequalificationSubmissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-submissions/count} : count all the mPrequalificationSubmissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-submissions/count")
    public ResponseEntity<Long> countMPrequalificationSubmissions(MPrequalificationSubmissionCriteria criteria) {
        log.debug("REST request to count MPrequalificationSubmissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationSubmissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-submissions/:id} : get the "id" mPrequalificationSubmission.
     *
     * @param id the id of the mPrequalificationSubmissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationSubmissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-submissions/{id}")
    public ResponseEntity<MPrequalificationSubmissionDTO> getMPrequalificationSubmission(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationSubmission : {}", id);
        Optional<MPrequalificationSubmissionDTO> mPrequalificationSubmissionDTO = mPrequalificationSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationSubmissionDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-submissions/:id} : delete the "id" mPrequalificationSubmission.
     *
     * @param id the id of the mPrequalificationSubmissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-submissions/{id}")
    public ResponseEntity<Void> deleteMPrequalificationSubmission(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationSubmission : {}", id);
        mPrequalificationSubmissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
