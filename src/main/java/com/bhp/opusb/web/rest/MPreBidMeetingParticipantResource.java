package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPreBidMeetingParticipantService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantDTO;
import com.bhp.opusb.service.dto.MPreBidMeetingParticipantCriteria;
import com.bhp.opusb.service.MPreBidMeetingParticipantQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPreBidMeetingParticipant}.
 */
@RestController
@RequestMapping("/api")
public class MPreBidMeetingParticipantResource {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingParticipantResource.class);

    private static final String ENTITY_NAME = "mPreBidMeetingParticipant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPreBidMeetingParticipantService mPreBidMeetingParticipantService;

    private final MPreBidMeetingParticipantQueryService mPreBidMeetingParticipantQueryService;

    public MPreBidMeetingParticipantResource(MPreBidMeetingParticipantService mPreBidMeetingParticipantService, MPreBidMeetingParticipantQueryService mPreBidMeetingParticipantQueryService) {
        this.mPreBidMeetingParticipantService = mPreBidMeetingParticipantService;
        this.mPreBidMeetingParticipantQueryService = mPreBidMeetingParticipantQueryService;
    }

    /**
     * {@code POST  /m-pre-bid-meeting-participants} : Create a new mPreBidMeetingParticipant.
     *
     * @param mPreBidMeetingParticipantDTO the mPreBidMeetingParticipantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPreBidMeetingParticipantDTO, or with status {@code 400 (Bad Request)} if the mPreBidMeetingParticipant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-pre-bid-meeting-participants")
    public ResponseEntity<MPreBidMeetingParticipantDTO> createMPreBidMeetingParticipant(@Valid @RequestBody MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO) throws URISyntaxException {
        log.debug("REST request to save MPreBidMeetingParticipant : {}", mPreBidMeetingParticipantDTO);
        if (mPreBidMeetingParticipantDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPreBidMeetingParticipant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPreBidMeetingParticipantDTO result = mPreBidMeetingParticipantService.save(mPreBidMeetingParticipantDTO);
        return ResponseEntity.created(new URI("/api/m-pre-bid-meeting-participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-pre-bid-meeting-participants} : Updates an existing mPreBidMeetingParticipant.
     *
     * @param mPreBidMeetingParticipantDTO the mPreBidMeetingParticipantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPreBidMeetingParticipantDTO,
     * or with status {@code 400 (Bad Request)} if the mPreBidMeetingParticipantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPreBidMeetingParticipantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-pre-bid-meeting-participants")
    public ResponseEntity<MPreBidMeetingParticipantDTO> updateMPreBidMeetingParticipant(@Valid @RequestBody MPreBidMeetingParticipantDTO mPreBidMeetingParticipantDTO) throws URISyntaxException {
        log.debug("REST request to update MPreBidMeetingParticipant : {}", mPreBidMeetingParticipantDTO);
        if (mPreBidMeetingParticipantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPreBidMeetingParticipantDTO result = mPreBidMeetingParticipantService.save(mPreBidMeetingParticipantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPreBidMeetingParticipantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-pre-bid-meeting-participants} : get all the mPreBidMeetingParticipants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPreBidMeetingParticipants in body.
     */
    @GetMapping("/m-pre-bid-meeting-participants")
    public ResponseEntity<List<MPreBidMeetingParticipantDTO>> getAllMPreBidMeetingParticipants(MPreBidMeetingParticipantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPreBidMeetingParticipants by criteria: {}", criteria);
        Page<MPreBidMeetingParticipantDTO> page = mPreBidMeetingParticipantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-pre-bid-meeting-participants/count} : count all the mPreBidMeetingParticipants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-pre-bid-meeting-participants/count")
    public ResponseEntity<Long> countMPreBidMeetingParticipants(MPreBidMeetingParticipantCriteria criteria) {
        log.debug("REST request to count MPreBidMeetingParticipants by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPreBidMeetingParticipantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-pre-bid-meeting-participants/:id} : get the "id" mPreBidMeetingParticipant.
     *
     * @param id the id of the mPreBidMeetingParticipantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPreBidMeetingParticipantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-pre-bid-meeting-participants/{id}")
    public ResponseEntity<MPreBidMeetingParticipantDTO> getMPreBidMeetingParticipant(@PathVariable Long id) {
        log.debug("REST request to get MPreBidMeetingParticipant : {}", id);
        Optional<MPreBidMeetingParticipantDTO> mPreBidMeetingParticipantDTO = mPreBidMeetingParticipantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPreBidMeetingParticipantDTO);
    }

    /**
     * {@code DELETE  /m-pre-bid-meeting-participants/:id} : delete the "id" mPreBidMeetingParticipant.
     *
     * @param id the id of the mPreBidMeetingParticipantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-pre-bid-meeting-participants/{id}")
    public ResponseEntity<Void> deleteMPreBidMeetingParticipant(@PathVariable Long id) {
        log.debug("REST request to delete MPreBidMeetingParticipant : {}", id);
        mPreBidMeetingParticipantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
