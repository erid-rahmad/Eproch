package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPreBidMeetingAttachmentService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentDTO;
import com.bhp.opusb.service.dto.MPreBidMeetingAttachmentCriteria;
import com.bhp.opusb.service.MPreBidMeetingAttachmentQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPreBidMeetingAttachment}.
 */
@RestController
@RequestMapping("/api")
public class MPreBidMeetingAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingAttachmentResource.class);

    private static final String ENTITY_NAME = "mPreBidMeetingAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPreBidMeetingAttachmentService mPreBidMeetingAttachmentService;

    private final MPreBidMeetingAttachmentQueryService mPreBidMeetingAttachmentQueryService;

    public MPreBidMeetingAttachmentResource(MPreBidMeetingAttachmentService mPreBidMeetingAttachmentService, MPreBidMeetingAttachmentQueryService mPreBidMeetingAttachmentQueryService) {
        this.mPreBidMeetingAttachmentService = mPreBidMeetingAttachmentService;
        this.mPreBidMeetingAttachmentQueryService = mPreBidMeetingAttachmentQueryService;
    }

    /**
     * {@code POST  /m-pre-bid-meeting-attachments} : Create a new mPreBidMeetingAttachment.
     *
     * @param mPreBidMeetingAttachmentDTO the mPreBidMeetingAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPreBidMeetingAttachmentDTO, or with status {@code 400 (Bad Request)} if the mPreBidMeetingAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-pre-bid-meeting-attachments")
    public ResponseEntity<MPreBidMeetingAttachmentDTO> createMPreBidMeetingAttachment(@Valid @RequestBody MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save MPreBidMeetingAttachment : {}", mPreBidMeetingAttachmentDTO);
        if (mPreBidMeetingAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPreBidMeetingAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPreBidMeetingAttachmentDTO result = mPreBidMeetingAttachmentService.save(mPreBidMeetingAttachmentDTO);
        return ResponseEntity.created(new URI("/api/m-pre-bid-meeting-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-pre-bid-meeting-attachments} : Updates an existing mPreBidMeetingAttachment.
     *
     * @param mPreBidMeetingAttachmentDTO the mPreBidMeetingAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPreBidMeetingAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the mPreBidMeetingAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPreBidMeetingAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-pre-bid-meeting-attachments")
    public ResponseEntity<MPreBidMeetingAttachmentDTO> updateMPreBidMeetingAttachment(@Valid @RequestBody MPreBidMeetingAttachmentDTO mPreBidMeetingAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to update MPreBidMeetingAttachment : {}", mPreBidMeetingAttachmentDTO);
        if (mPreBidMeetingAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPreBidMeetingAttachmentDTO result = mPreBidMeetingAttachmentService.save(mPreBidMeetingAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPreBidMeetingAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-pre-bid-meeting-attachments} : get all the mPreBidMeetingAttachments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPreBidMeetingAttachments in body.
     */
    @GetMapping("/m-pre-bid-meeting-attachments")
    public ResponseEntity<List<MPreBidMeetingAttachmentDTO>> getAllMPreBidMeetingAttachments(MPreBidMeetingAttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPreBidMeetingAttachments by criteria: {}", criteria);
        Page<MPreBidMeetingAttachmentDTO> page = mPreBidMeetingAttachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-pre-bid-meeting-attachments/count} : count all the mPreBidMeetingAttachments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-pre-bid-meeting-attachments/count")
    public ResponseEntity<Long> countMPreBidMeetingAttachments(MPreBidMeetingAttachmentCriteria criteria) {
        log.debug("REST request to count MPreBidMeetingAttachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPreBidMeetingAttachmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-pre-bid-meeting-attachments/:id} : get the "id" mPreBidMeetingAttachment.
     *
     * @param id the id of the mPreBidMeetingAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPreBidMeetingAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-pre-bid-meeting-attachments/{id}")
    public ResponseEntity<MPreBidMeetingAttachmentDTO> getMPreBidMeetingAttachment(@PathVariable Long id) {
        log.debug("REST request to get MPreBidMeetingAttachment : {}", id);
        Optional<MPreBidMeetingAttachmentDTO> mPreBidMeetingAttachmentDTO = mPreBidMeetingAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPreBidMeetingAttachmentDTO);
    }

    /**
     * {@code DELETE  /m-pre-bid-meeting-attachments/:id} : delete the "id" mPreBidMeetingAttachment.
     *
     * @param id the id of the mPreBidMeetingAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-pre-bid-meeting-attachments/{id}")
    public ResponseEntity<Void> deleteMPreBidMeetingAttachment(@PathVariable Long id) {
        log.debug("REST request to delete MPreBidMeetingAttachment : {}", id);
        mPreBidMeetingAttachmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
