package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MBiddingScheduleAttachmentQueryService;
import com.bhp.opusb.service.MBiddingScheduleAttachmentService;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentCriteria;
import com.bhp.opusb.service.dto.MBiddingScheduleAttachmentDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingScheduleAttachment}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingScheduleAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleAttachmentResource.class);

    private static final String ENTITY_NAME = "mBiddingScheduleAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingScheduleAttachmentService mBiddingScheduleAttachmentService;

    private final MBiddingScheduleAttachmentQueryService mBiddingScheduleAttachmentQueryService;

    public MBiddingScheduleAttachmentResource(MBiddingScheduleAttachmentService mBiddingScheduleAttachmentService, MBiddingScheduleAttachmentQueryService mBiddingScheduleAttachmentQueryService) {
        this.mBiddingScheduleAttachmentService = mBiddingScheduleAttachmentService;
        this.mBiddingScheduleAttachmentQueryService = mBiddingScheduleAttachmentQueryService;
    }

    /**
     * {@code POST  /m-bidding-schedule-attachments} : Create a new mBiddingScheduleAttachment.
     *
     * @param mBiddingScheduleAttachmentDTO the mBiddingScheduleAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingScheduleAttachmentDTO, or with status {@code 400 (Bad Request)} if the mBiddingScheduleAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-schedule-attachments")
    public ResponseEntity<MBiddingScheduleAttachmentDTO> createMBiddingScheduleAttachment(@Valid @RequestBody MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingScheduleAttachment : {}", mBiddingScheduleAttachmentDTO);
        if (mBiddingScheduleAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingScheduleAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingScheduleAttachmentDTO result = mBiddingScheduleAttachmentService.save(mBiddingScheduleAttachmentDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-schedule-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-schedule-attachments} : Updates an existing mBiddingScheduleAttachment.
     *
     * @param mBiddingScheduleAttachmentDTO the mBiddingScheduleAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingScheduleAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingScheduleAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingScheduleAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-schedule-attachments")
    public ResponseEntity<MBiddingScheduleAttachmentDTO> updateMBiddingScheduleAttachment(@Valid @RequestBody MBiddingScheduleAttachmentDTO mBiddingScheduleAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingScheduleAttachment : {}", mBiddingScheduleAttachmentDTO);
        if (mBiddingScheduleAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingScheduleAttachmentDTO result = mBiddingScheduleAttachmentService.save(mBiddingScheduleAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingScheduleAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-schedule-attachments} : get all the mBiddingScheduleAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingScheduleAttachments in body.
     */
    @GetMapping("/m-bidding-schedule-attachments")
    public ResponseEntity<List<MBiddingScheduleAttachmentDTO>> getAllMBiddingScheduleAttachments(MBiddingScheduleAttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingScheduleAttachments by criteria: {}", criteria);
        Page<MBiddingScheduleAttachmentDTO> page = mBiddingScheduleAttachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-schedule-attachments/count} : count all the mBiddingScheduleAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-schedule-attachments/count")
    public ResponseEntity<Long> countMBiddingScheduleAttachments(MBiddingScheduleAttachmentCriteria criteria) {
        log.debug("REST request to count MBiddingScheduleAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingScheduleAttachmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-schedule-attachments/:id} : get the "id" mBiddingScheduleAttachment.
     *
     * @param id the id of the mBiddingScheduleAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingScheduleAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-schedule-attachments/{id}")
    public ResponseEntity<MBiddingScheduleAttachmentDTO> getMBiddingScheduleAttachment(@PathVariable Long id) {
        log.debug("REST request to get MBiddingScheduleAttachment : {}", id);
        Optional<MBiddingScheduleAttachmentDTO> mBiddingScheduleAttachmentDTO = mBiddingScheduleAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingScheduleAttachmentDTO);
    }

    /**
     * {@code DELETE  /m-bidding-schedule-attachments/:id} : delete the "id" mBiddingScheduleAttachment.
     *
     * @param id the id of the mBiddingScheduleAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-schedule-attachments/{id}")
    public ResponseEntity<Void> deleteMBiddingScheduleAttachment(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingScheduleAttachment : {}", id);
        mBiddingScheduleAttachmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
