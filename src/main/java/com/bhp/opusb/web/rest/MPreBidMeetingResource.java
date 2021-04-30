package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPreBidMeetingService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPreBidMeetingDTO;
import com.bhp.opusb.service.dto.MPreBidMeetingCriteria;
import com.bhp.opusb.service.MPreBidMeetingQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPreBidMeeting}.
 */
@RestController
@RequestMapping("/api")
public class MPreBidMeetingResource {

    private final Logger log = LoggerFactory.getLogger(MPreBidMeetingResource.class);

    private static final String ENTITY_NAME = "mPreBidMeeting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPreBidMeetingService mPreBidMeetingService;

    private final MPreBidMeetingQueryService mPreBidMeetingQueryService;

    public MPreBidMeetingResource(MPreBidMeetingService mPreBidMeetingService, MPreBidMeetingQueryService mPreBidMeetingQueryService) {
        this.mPreBidMeetingService = mPreBidMeetingService;
        this.mPreBidMeetingQueryService = mPreBidMeetingQueryService;
    }

    /**
     * {@code POST  /m-pre-bid-meetings} : Create a new mPreBidMeeting.
     *
     * @param mPreBidMeetingDTO the mPreBidMeetingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPreBidMeetingDTO, or with status {@code 400 (Bad Request)} if the mPreBidMeeting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-pre-bid-meetings")
    public ResponseEntity<MPreBidMeetingDTO> createMPreBidMeeting(@Valid @RequestBody MPreBidMeetingDTO mPreBidMeetingDTO) throws URISyntaxException {
        log.debug("REST request to save MPreBidMeeting : {}", mPreBidMeetingDTO);
        if (mPreBidMeetingDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPreBidMeeting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPreBidMeetingDTO result = mPreBidMeetingService.save(mPreBidMeetingDTO);
        return ResponseEntity.created(new URI("/api/m-pre-bid-meetings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-pre-bid-meetings} : Updates an existing mPreBidMeeting.
     *
     * @param mPreBidMeetingDTO the mPreBidMeetingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPreBidMeetingDTO,
     * or with status {@code 400 (Bad Request)} if the mPreBidMeetingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPreBidMeetingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-pre-bid-meetings")
    public ResponseEntity<MPreBidMeetingDTO> updateMPreBidMeeting(@Valid @RequestBody MPreBidMeetingDTO mPreBidMeetingDTO) throws URISyntaxException {
        log.debug("REST request to update MPreBidMeeting : {}", mPreBidMeetingDTO);
        if (mPreBidMeetingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPreBidMeetingDTO result = mPreBidMeetingService.save(mPreBidMeetingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPreBidMeetingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-pre-bid-meetings} : get all the mPreBidMeetings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPreBidMeetings in body.
     */
    @GetMapping("/m-pre-bid-meetings")
    public ResponseEntity<List<MPreBidMeetingDTO>> getAllMPreBidMeetings(MPreBidMeetingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPreBidMeetings by criteria: {}", criteria);
        Page<MPreBidMeetingDTO> page = mPreBidMeetingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-pre-bid-meetings/count} : count all the mPreBidMeetings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-pre-bid-meetings/count")
    public ResponseEntity<Long> countMPreBidMeetings(MPreBidMeetingCriteria criteria) {
        log.debug("REST request to count MPreBidMeetings by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPreBidMeetingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-pre-bid-meetings/:id} : get the "id" mPreBidMeeting.
     *
     * @param id the id of the mPreBidMeetingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPreBidMeetingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-pre-bid-meetings/{id}")
    public ResponseEntity<MPreBidMeetingDTO> getMPreBidMeeting(@PathVariable Long id) {
        log.debug("REST request to get MPreBidMeeting : {}", id);
        Optional<MPreBidMeetingDTO> mPreBidMeetingDTO = mPreBidMeetingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPreBidMeetingDTO);
    }

    /**
     * {@code DELETE  /m-pre-bid-meetings/:id} : delete the "id" mPreBidMeeting.
     *
     * @param id the id of the mPreBidMeetingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-pre-bid-meetings/{id}")
    public ResponseEntity<Void> deleteMPreBidMeeting(@PathVariable Long id) {
        log.debug("REST request to delete MPreBidMeeting : {}", id);
        mPreBidMeetingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
