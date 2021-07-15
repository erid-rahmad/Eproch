package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationEventService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationEventDTO;
import com.bhp.opusb.service.dto.MPrequalificationEventCriteria;
import com.bhp.opusb.service.MPrequalificationEventQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationEvent}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationEventResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEventResource.class);

    private static final String ENTITY_NAME = "mPrequalificationEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationEventService mPrequalificationEventService;

    private final MPrequalificationEventQueryService mPrequalificationEventQueryService;

    public MPrequalificationEventResource(MPrequalificationEventService mPrequalificationEventService, MPrequalificationEventQueryService mPrequalificationEventQueryService) {
        this.mPrequalificationEventService = mPrequalificationEventService;
        this.mPrequalificationEventQueryService = mPrequalificationEventQueryService;
    }

    /**
     * {@code POST  /m-prequalification-events} : Create a new mPrequalificationEvent.
     *
     * @param mPrequalificationEventDTO the mPrequalificationEventDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationEventDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationEvent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-events")
    public ResponseEntity<MPrequalificationEventDTO> createMPrequalificationEvent(@Valid @RequestBody MPrequalificationEventDTO mPrequalificationEventDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationEvent : {}", mPrequalificationEventDTO);
        if (mPrequalificationEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationEventDTO result = mPrequalificationEventService.save(mPrequalificationEventDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-events} : Updates an existing mPrequalificationEvent.
     *
     * @param mPrequalificationEventDTO the mPrequalificationEventDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationEventDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationEventDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationEventDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-events")
    public ResponseEntity<MPrequalificationEventDTO> updateMPrequalificationEvent(@Valid @RequestBody MPrequalificationEventDTO mPrequalificationEventDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationEvent : {}", mPrequalificationEventDTO);
        if (mPrequalificationEventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationEventDTO result = mPrequalificationEventService.save(mPrequalificationEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-events} : get all the mPrequalificationEvents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationEvents in body.
     */
    @GetMapping("/m-prequalification-events")
    public ResponseEntity<List<MPrequalificationEventDTO>> getAllMPrequalificationEvents(MPrequalificationEventCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationEvents by criteria: {}", criteria);
        Page<MPrequalificationEventDTO> page = mPrequalificationEventQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-events/count} : count all the mPrequalificationEvents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-events/count")
    public ResponseEntity<Long> countMPrequalificationEvents(MPrequalificationEventCriteria criteria) {
        log.debug("REST request to count MPrequalificationEvents by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationEventQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-events/:id} : get the "id" mPrequalificationEvent.
     *
     * @param id the id of the mPrequalificationEventDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationEventDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-events/{id}")
    public ResponseEntity<MPrequalificationEventDTO> getMPrequalificationEvent(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationEvent : {}", id);
        Optional<MPrequalificationEventDTO> mPrequalificationEventDTO = mPrequalificationEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationEventDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-events/:id} : delete the "id" mPrequalificationEvent.
     *
     * @param id the id of the mPrequalificationEventDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-events/{id}")
    public ResponseEntity<Void> deleteMPrequalificationEvent(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationEvent : {}", id);
        mPrequalificationEventService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
