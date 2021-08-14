package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MRequisitionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.bhp.opusb.service.dto.MRequisitionDTO;
import com.bhp.opusb.service.dto.MRequisitionCriteria;
import com.bhp.opusb.service.MRequisitionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MRequisition}.
 */
@RestController
@RequestMapping("/api")
public class MRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(MRequisitionResource.class);

    private static final String ENTITY_NAME = "mRequisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MRequisitionService mRequisitionService;

    private final MRequisitionQueryService mRequisitionQueryService;

    public MRequisitionResource(MRequisitionService mRequisitionService, MRequisitionQueryService mRequisitionQueryService) {
        this.mRequisitionService = mRequisitionService;
        this.mRequisitionQueryService = mRequisitionQueryService;
    }

    /**
     * {@code POST  /m-requisitions} : Create a new mRequisition.
     *
     * @param mRequisitionDTO the mRequisitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRequisitionDTO, or with status {@code 400 (Bad Request)} if the mRequisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-requisitions")
    public ResponseEntity<MRequisitionDTO> createMRequisition(@Valid @RequestBody MRequisitionDTO mRequisitionDTO) throws URISyntaxException {
        log.debug("REST request to save MRequisition : {}", mRequisitionDTO);
        if (mRequisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRequisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MRequisitionDTO result = mRequisitionService.save(mRequisitionDTO);
        return ResponseEntity.created(new URI("/api/m-requisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-requisitions} : Updates an existing mRequisition.
     *
     * @param mRequisitionDTO the mRequisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRequisitionDTO,
     * or with status {@code 400 (Bad Request)} if the mRequisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRequisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-requisitions")
    public ResponseEntity<MRequisitionDTO> updateMRequisition(@Valid @RequestBody MRequisitionDTO mRequisitionDTO) throws URISyntaxException {
        log.debug("REST request to update MRequisition : {}", mRequisitionDTO);
        if (mRequisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MRequisitionDTO result = mRequisitionService.save(mRequisitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mRequisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-requisitions} : get all the mRequisitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRequisitions in body.
     */
    @GetMapping("/m-requisitions")
    public ResponseEntity<List<MRequisitionDTO>> getAllMRequisitions(MRequisitionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRequisitions by criteria: {}", criteria);
        Page<MRequisitionDTO> page = mRequisitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-requisitions/count} : count all the mRequisitions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-requisitions/count")
    public ResponseEntity<Long> countMRequisitions(MRequisitionCriteria criteria) {
        log.debug("REST request to count MRequisitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRequisitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-requisitions/:id} : get the "id" mRequisition.
     *
     * @param id the id of the mRequisitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRequisitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-requisitions/{id}")
    public ResponseEntity<MRequisitionDTO> getMRequisition(@PathVariable Long id) {
        log.debug("REST request to get MRequisition : {}", id);
        Optional<MRequisitionDTO> mRequisitionDTO = mRequisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRequisitionDTO);
    }

    /**
     * {@code DELETE  /m-requisitions/:id} : delete the "id" mRequisition.
     *
     * @param id the id of the mRequisitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-requisitions/{id}")
    public ResponseEntity<Void> deleteMRequisition(@PathVariable Long id) {
        log.debug("REST request to delete MRequisition : {}", id);
        mRequisitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * TODO Make a single endpoint for document status update.
     * {@code PUT  /c-requisitions} : Apply the document action to an existing mRequisitions.
     *
     * @param mRequisitionDTO the mRequisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRequisitionDTO,
     * or with status {@code 400 (Bad Request)} if the mRequisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRequisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-requisitions/update-doc-status")
    @ResponseStatus(HttpStatus.OK)
    public void applyDocumentAction(@Valid @RequestBody MRequisitionDTO mRequisitionDTO) throws URISyntaxException {
        log.debug("REST request to apply MRequisition's document action : {}", mRequisitionDTO);
        if (mRequisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        mRequisitionService.updateDocumentStatus(mRequisitionDTO);
    }

    /**
     * {@code POST  /m-match-pos/synchronize} : Synchronize MMatchPO with the external source (BHp EBS).
     *
     * @param message the JSON formatted message representing XXEPROC_PREQ_HEADERS_V record.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the created/updated MRequisitionDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(
        path = "/m-requisitions/synchronize",
        consumes = {
            "application/octet-stream;charset=UTF-8",
            "application/json;charset=UTF-8"
        },
        produces = {
            "application/json;charset=UTF-8"
        })
    public ResponseEntity<MRequisitionDTO> syncReceiverFile(@RequestBody byte[] message) throws URISyntaxException, JsonProcessingException {
        final String input = new String(message);
        log.debug("REST request to synchronize MMatchPO : {}", input);

        MRequisitionDTO result = mRequisitionService.synchronize(input);

        return ResponseEntity.ok().body(result);
    }
}
