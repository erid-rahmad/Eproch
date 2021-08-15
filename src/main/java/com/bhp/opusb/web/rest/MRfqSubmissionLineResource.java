package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MRfqSubmissionLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MRfqSubmissionLineDTO;
import com.bhp.opusb.service.dto.MRfqSubmissionLineCriteria;
import com.bhp.opusb.service.MRfqSubmissionLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MRfqSubmissionLine}.
 */
@RestController
@RequestMapping("/api")
public class MRfqSubmissionLineResource {

    private final Logger log = LoggerFactory.getLogger(MRfqSubmissionLineResource.class);

    private static final String ENTITY_NAME = "mRfqSubmissionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MRfqSubmissionLineService mRfqSubmissionLineService;

    private final MRfqSubmissionLineQueryService mRfqSubmissionLineQueryService;

    public MRfqSubmissionLineResource(MRfqSubmissionLineService mRfqSubmissionLineService, MRfqSubmissionLineQueryService mRfqSubmissionLineQueryService) {
        this.mRfqSubmissionLineService = mRfqSubmissionLineService;
        this.mRfqSubmissionLineQueryService = mRfqSubmissionLineQueryService;
    }

    /**
     * {@code POST  /m-rfq-submission-lines} : Create a new mRfqSubmissionLine.
     *
     * @param mRfqSubmissionLineDTO the mRfqSubmissionLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRfqSubmissionLineDTO, or with status {@code 400 (Bad Request)} if the mRfqSubmissionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-rfq-submission-lines")
    public ResponseEntity<MRfqSubmissionLineDTO> createMRfqSubmissionLine(@Valid @RequestBody MRfqSubmissionLineDTO mRfqSubmissionLineDTO) throws URISyntaxException {
        log.debug("REST request to save MRfqSubmissionLine : {}", mRfqSubmissionLineDTO);
        if (mRfqSubmissionLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRfqSubmissionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MRfqSubmissionLineDTO result = mRfqSubmissionLineService.save(mRfqSubmissionLineDTO);
        return ResponseEntity.created(new URI("/api/m-rfq-submission-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-rfq-submission-lines} : Updates an existing mRfqSubmissionLine.
     *
     * @param mRfqSubmissionLineDTO the mRfqSubmissionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRfqSubmissionLineDTO,
     * or with status {@code 400 (Bad Request)} if the mRfqSubmissionLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRfqSubmissionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-rfq-submission-lines")
    public ResponseEntity<MRfqSubmissionLineDTO> updateMRfqSubmissionLine(@Valid @RequestBody MRfqSubmissionLineDTO mRfqSubmissionLineDTO) throws URISyntaxException {
        log.debug("REST request to update MRfqSubmissionLine : {}", mRfqSubmissionLineDTO);
        if (mRfqSubmissionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MRfqSubmissionLineDTO result = mRfqSubmissionLineService.save(mRfqSubmissionLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mRfqSubmissionLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-rfq-submission-lines} : get all the mRfqSubmissionLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRfqSubmissionLines in body.
     */
    @GetMapping("/m-rfq-submission-lines")
    public ResponseEntity<List<MRfqSubmissionLineDTO>> getAllMRfqSubmissionLines(MRfqSubmissionLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRfqSubmissionLines by criteria: {}", criteria);
        Page<MRfqSubmissionLineDTO> page = mRfqSubmissionLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-rfq-submission-lines/count} : count all the mRfqSubmissionLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-rfq-submission-lines/count")
    public ResponseEntity<Long> countMRfqSubmissionLines(MRfqSubmissionLineCriteria criteria) {
        log.debug("REST request to count MRfqSubmissionLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRfqSubmissionLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-rfq-submission-lines/:id} : get the "id" mRfqSubmissionLine.
     *
     * @param id the id of the mRfqSubmissionLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRfqSubmissionLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-rfq-submission-lines/{id}")
    public ResponseEntity<MRfqSubmissionLineDTO> getMRfqSubmissionLine(@PathVariable Long id) {
        log.debug("REST request to get MRfqSubmissionLine : {}", id);
        Optional<MRfqSubmissionLineDTO> mRfqSubmissionLineDTO = mRfqSubmissionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRfqSubmissionLineDTO);
    }

    /**
     * {@code DELETE  /m-rfq-submission-lines/:id} : delete the "id" mRfqSubmissionLine.
     *
     * @param id the id of the mRfqSubmissionLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-rfq-submission-lines/{id}")
    public ResponseEntity<Void> deleteMRfqSubmissionLine(@PathVariable Long id) {
        log.debug("REST request to delete MRfqSubmissionLine : {}", id);
        mRfqSubmissionLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
