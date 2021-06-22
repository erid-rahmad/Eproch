package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MRfqService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MRfqDTO;
import com.bhp.opusb.service.dto.MRfqCriteria;
import com.bhp.opusb.service.MRfqQueryService;

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
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MRfq}.
 */
@RestController
@RequestMapping("/api")
public class MRfqResource {

    private final Logger log = LoggerFactory.getLogger(MRfqResource.class);

    private static final String ENTITY_NAME = "mRfq";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MRfqService mRfqService;

    private final MRfqQueryService mRfqQueryService;

    public MRfqResource(MRfqService mRfqService, MRfqQueryService mRfqQueryService) {
        this.mRfqService = mRfqService;
        this.mRfqQueryService = mRfqQueryService;
    }

    /**
     * {@code POST  /m-rfqs} : Create a new mRfq.
     *
     * @param mRfqDTO the mRfqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRfqDTO, or with status {@code 400 (Bad Request)} if the mRfq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-rfqs")
    public ResponseEntity<MRfqDTO> createMRfq(@Valid @RequestBody MRfqDTO mRfqDTO) throws URISyntaxException {
        log.debug("REST request to save MRfq : {}", mRfqDTO);
        if (mRfqDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRfq cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MRfqDTO result = mRfqService.save(mRfqDTO);
        return ResponseEntity.created(new URI("/api/m-rfqs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-rfqs} : Updates an existing mRfq.
     *
     * @param mRfqDTO the mRfqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRfqDTO,
     * or with status {@code 400 (Bad Request)} if the mRfqDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRfqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-rfqs")
    public ResponseEntity<MRfqDTO> updateMRfq(@Valid @RequestBody MRfqDTO mRfqDTO) throws URISyntaxException {
        log.debug("REST request to update MRfq : {}", mRfqDTO);
        if (mRfqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MRfqDTO result = mRfqService.save(mRfqDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mRfqDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-rfqs} : get all the mRfqs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRfqs in body.
     */
    @GetMapping("/m-rfqs")
    public ResponseEntity<List<MRfqDTO>> getAllMRfqs(MRfqCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRfqs by criteria: {}", criteria);
        Page<MRfqDTO> page = mRfqQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-rfqs/count} : count all the mRfqs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-rfqs/count")
    public ResponseEntity<Long> countMRfqs(MRfqCriteria criteria) {
        log.debug("REST request to count MRfqs by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRfqQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-rfqs/:id} : get the "id" mRfq.
     *
     * @param id the id of the mRfqDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRfqDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-rfqs/{id}")
    public ResponseEntity<MRfqDTO> getMRfq(@PathVariable Long id) {
        log.debug("REST request to get MRfq : {}", id);
        Optional<MRfqDTO> mRfqDTO = mRfqService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRfqDTO);
    }

    /**
     * {@code DELETE  /m-rfqs/:id} : delete the "id" mRfq.
     *
     * @param id the id of the mRfqDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-rfqs/{id}")
    public ResponseEntity<Void> deleteMRfq(@PathVariable Long id) {
        log.debug("REST request to delete MRfq : {}", id);
        mRfqService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code POST  /m-rfqs} : Create a new mRfq.
     *
     * @param mRfqDTO the mRfqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRfqDTO, or with status {@code 400 (Bad Request)} if the mRfq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-rfqs/generate")
    public ResponseEntity<List<MRfqDTO>> generateRfqFromRequisition(@Valid @RequestBody MRfqDTO mRfqDTO) throws URISyntaxException {
        log.debug("REST request to generate MRfq : {}", mRfqDTO);
        if (mRfqDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRfq cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (mRfqDTO.getRequisitionLines() == null) {
            throw new BadRequestAlertException("The submitted quotation has no lines", ENTITY_NAME, "poNoLines");
        }
        List<MRfqDTO> result = mRfqService.saveFromRequisition(mRfqDTO);
        String queryString = result.stream().map(po -> "id.equals=" + po.getId()).collect(Collectors.joining("&"));
        return ResponseEntity.created(new URI("/api/m-rfqs/" + queryString))
            .body(result);
    }

    /**
     * TODO Make a single endpoint for document status update.
     * {@code PUT  /c-rfqs/update-doc-status} : Apply the document action to an existing mRequisitions.
     *
     * @param mRfqDTO the mRfqDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRfqDTO,
     * or with status {@code 400 (Bad Request)} if the mRfqDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRfqDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-rfqs/update-doc-status")
    @ResponseStatus(HttpStatus.OK)
    public void applyDocumentAction(@Valid @RequestBody MRfqDTO mRfqDTO) {
        log.debug("REST request to apply MRfqDTO's document action : {}", mRfqDTO);
        if (mRfqDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        mRfqService.updateDocumentStatus(mRfqDTO);
    }
}
