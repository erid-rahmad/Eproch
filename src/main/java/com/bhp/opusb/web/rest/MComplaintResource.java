package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MComplaintService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MComplaintDTO;
import com.bhp.opusb.service.dto.MComplaintCriteria;
import com.bhp.opusb.service.MComplaintQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MComplaint}.
 */
@RestController
@RequestMapping("/api")
public class MComplaintResource {

    private final Logger log = LoggerFactory.getLogger(MComplaintResource.class);

    private static final String ENTITY_NAME = "mComplaint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MComplaintService mComplaintService;

    private final MComplaintQueryService mComplaintQueryService;

    public MComplaintResource(MComplaintService mComplaintService, MComplaintQueryService mComplaintQueryService) {
        this.mComplaintService = mComplaintService;
        this.mComplaintQueryService = mComplaintQueryService;
    }

    /**
     * {@code POST  /m-complaints} : Create a new mComplaint.
     *
     * @param mComplaintDTO the mComplaintDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mComplaintDTO, or with status {@code 400 (Bad Request)} if the mComplaint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-complaints")
    public ResponseEntity<MComplaintDTO> createMComplaint(@Valid @RequestBody MComplaintDTO mComplaintDTO) throws URISyntaxException {
        log.debug("REST request to save MComplaint : {}", mComplaintDTO);
        if (mComplaintDTO.getId() != null) {
            throw new BadRequestAlertException("A new mComplaint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MComplaintDTO result = mComplaintService.save(mComplaintDTO);
        return ResponseEntity.created(new URI("/api/m-complaints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-complaints} : Updates an existing mComplaint.
     *
     * @param mComplaintDTO the mComplaintDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mComplaintDTO,
     * or with status {@code 400 (Bad Request)} if the mComplaintDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mComplaintDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-complaints")
    public ResponseEntity<MComplaintDTO> updateMComplaint(@Valid @RequestBody MComplaintDTO mComplaintDTO) throws URISyntaxException {
        log.debug("REST request to update MComplaint : {}", mComplaintDTO);
        if (mComplaintDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MComplaintDTO result = mComplaintService.save(mComplaintDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mComplaintDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-complaints} : get all the mComplaints.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mComplaints in body.
     */
    @GetMapping("/m-complaints")
    public ResponseEntity<List<MComplaintDTO>> getAllMComplaints(MComplaintCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MComplaints by criteria: {}", criteria);
        Page<MComplaintDTO> page = mComplaintQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-complaints/count} : count all the mComplaints.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-complaints/count")
    public ResponseEntity<Long> countMComplaints(MComplaintCriteria criteria) {
        log.debug("REST request to count MComplaints by criteria: {}", criteria);
        return ResponseEntity.ok().body(mComplaintQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-complaints/:id} : get the "id" mComplaint.
     *
     * @param id the id of the mComplaintDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mComplaintDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-complaints/{id}")
    public ResponseEntity<MComplaintDTO> getMComplaint(@PathVariable Long id) {
        log.debug("REST request to get MComplaint : {}", id);
        Optional<MComplaintDTO> mComplaintDTO = mComplaintService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mComplaintDTO);
    }

    /**
     * {@code DELETE  /m-complaints/:id} : delete the "id" mComplaint.
     *
     * @param id the id of the mComplaintDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-complaints/{id}")
    public ResponseEntity<Void> deleteMComplaint(@PathVariable Long id) {
        log.debug("REST request to delete MComplaint : {}", id);
        mComplaintService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
