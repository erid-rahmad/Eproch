package com.bhp.opusb.web.rest;

import com.bhp.opusb.domain.MRequisitionLine;
import com.bhp.opusb.service.MRequisitionLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MRequisitionLineDTO;
import com.bhp.opusb.service.dto.MRequisitionLineCriteria;
import com.bhp.opusb.service.MRequisitionLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MRequisitionLine}.
 */
@RestController
@RequestMapping("/api")
public class MRequisitionLineResource {

    private final Logger log = LoggerFactory.getLogger(MRequisitionLineResource.class);

    private static final String ENTITY_NAME = "mRequisitionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MRequisitionLineService mRequisitionLineService;

    private final MRequisitionLineQueryService mRequisitionLineQueryService;

    public MRequisitionLineResource(MRequisitionLineService mRequisitionLineService, MRequisitionLineQueryService mRequisitionLineQueryService) {
        this.mRequisitionLineService = mRequisitionLineService;
        this.mRequisitionLineQueryService = mRequisitionLineQueryService;
    }

    /**
     * {@code POST  /m-requisition-lines} : Create a new mRequisitionLine.
     *
     * @param mRequisitionLineDTO the mRequisitionLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRequisitionLineDTO, or with status {@code 400 (Bad Request)} if the mRequisitionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-requisition-lines")
    public ResponseEntity<MRequisitionLineDTO> createMRequisitionLine(@Valid @RequestBody MRequisitionLineDTO mRequisitionLineDTO) throws URISyntaxException {
        log.debug("REST request to save MRequisitionLine : {}", mRequisitionLineDTO);
        if (mRequisitionLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRequisitionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MRequisitionLineDTO result = mRequisitionLineService.save(mRequisitionLineDTO);
        return ResponseEntity.created(new URI("/api/m-requisition-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-requisition-lines} : Updates an existing mRequisitionLine.
     *
     * @param mRequisitionLineDTO the mRequisitionLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRequisitionLineDTO,
     * or with status {@code 400 (Bad Request)} if the mRequisitionLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRequisitionLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-requisition-lines")
    public ResponseEntity<MRequisitionLineDTO> updateMRequisitionLine(@Valid @RequestBody MRequisitionLineDTO mRequisitionLineDTO) throws URISyntaxException {
        log.debug("REST request to update MRequisitionLine : {}", mRequisitionLineDTO);
        if (mRequisitionLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MRequisitionLineDTO result = mRequisitionLineService.save(mRequisitionLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mRequisitionLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-requisition-lines} : get all the mRequisitionLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRequisitionLines in body.
     */
    @GetMapping("/m-requisition-lines")
    public ResponseEntity<List<MRequisitionLineDTO>> getAllMRequisitionLines(MRequisitionLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRequisitionLines by criteria: {}", criteria);
        Page<MRequisitionLineDTO> page = mRequisitionLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-requisition-lines-nested")
    public ResponseEntity<List<MRequisitionLine>> getAllMRequisitionLinesnested() {
//        log.debug("REST request to get MRequisitionLines by criteria: {}", criteria);
        List<MRequisitionLine> mRequisitionLineList = mRequisitionLineService.findAlle();
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest();
        return ResponseEntity.ok().body(mRequisitionLineList);
    }

    /**
     * {@code GET  /m-requisition-lines/count} : count all the mRequisitionLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-requisition-lines/count")
    public ResponseEntity<Long> countMRequisitionLines(MRequisitionLineCriteria criteria) {
        log.debug("REST request to count MRequisitionLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRequisitionLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-requisition-lines/:id} : get the "id" mRequisitionLine.
     *
     * @param id the id of the mRequisitionLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRequisitionLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-requisition-lines/{id}")
    public ResponseEntity<MRequisitionLineDTO> getMRequisitionLine(@PathVariable Long id) {
        log.debug("REST request to get MRequisitionLine : {}", id);
        Optional<MRequisitionLineDTO> mRequisitionLineDTO = mRequisitionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRequisitionLineDTO);
    }

    /**
     * {@code DELETE  /m-requisition-lines/:id} : delete the "id" mRequisitionLine.
     *
     * @param id the id of the mRequisitionLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-requisition-lines/{id}")
    public ResponseEntity<Void> deleteMRequisitionLine(@PathVariable Long id) {
        log.debug("REST request to delete MRequisitionLine : {}", id);
        mRequisitionLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
