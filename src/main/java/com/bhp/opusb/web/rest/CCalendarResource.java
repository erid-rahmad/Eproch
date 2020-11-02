package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CCalendarService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CCalendarDTO;
import com.bhp.opusb.service.dto.CCalendarCriteria;
import com.bhp.opusb.service.CCalendarQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CCalendar}.
 */
@RestController
@RequestMapping("/api")
public class CCalendarResource {

    private final Logger log = LoggerFactory.getLogger(CCalendarResource.class);

    private static final String ENTITY_NAME = "cCalendar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCalendarService cCalendarService;

    private final CCalendarQueryService cCalendarQueryService;

    public CCalendarResource(CCalendarService cCalendarService, CCalendarQueryService cCalendarQueryService) {
        this.cCalendarService = cCalendarService;
        this.cCalendarQueryService = cCalendarQueryService;
    }

    /**
     * {@code POST  /c-calendars} : Create a new cCalendar.
     *
     * @param cCalendarDTO the cCalendarDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCalendarDTO, or with status {@code 400 (Bad Request)} if the cCalendar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-calendars")
    public ResponseEntity<CCalendarDTO> createCCalendar(@Valid @RequestBody CCalendarDTO cCalendarDTO) throws URISyntaxException {
        log.debug("REST request to save CCalendar : {}", cCalendarDTO);
        if (cCalendarDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCalendar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCalendarDTO result = cCalendarService.save(cCalendarDTO);
        return ResponseEntity.created(new URI("/api/c-calendars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-calendars} : Updates an existing cCalendar.
     *
     * @param cCalendarDTO the cCalendarDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCalendarDTO,
     * or with status {@code 400 (Bad Request)} if the cCalendarDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCalendarDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-calendars")
    public ResponseEntity<CCalendarDTO> updateCCalendar(@Valid @RequestBody CCalendarDTO cCalendarDTO) throws URISyntaxException {
        log.debug("REST request to update CCalendar : {}", cCalendarDTO);
        if (cCalendarDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCalendarDTO result = cCalendarService.save(cCalendarDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCalendarDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-calendars} : get all the cCalendars.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCalendars in body.
     */
    @GetMapping("/c-calendars")
    public ResponseEntity<List<CCalendarDTO>> getAllCCalendars(CCalendarCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CCalendars by criteria: {}", criteria);
        Page<CCalendarDTO> page = cCalendarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-calendars/count} : count all the cCalendars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-calendars/count")
    public ResponseEntity<Long> countCCalendars(CCalendarCriteria criteria) {
        log.debug("REST request to count CCalendars by criteria: {}", criteria);
        return ResponseEntity.ok().body(cCalendarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-calendars/:id} : get the "id" cCalendar.
     *
     * @param id the id of the cCalendarDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCalendarDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-calendars/{id}")
    public ResponseEntity<CCalendarDTO> getCCalendar(@PathVariable Long id) {
        log.debug("REST request to get CCalendar : {}", id);
        Optional<CCalendarDTO> cCalendarDTO = cCalendarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCalendarDTO);
    }

    /**
     * {@code DELETE  /c-calendars/:id} : delete the "id" cCalendar.
     *
     * @param id the id of the cCalendarDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-calendars/{id}")
    public ResponseEntity<Void> deleteCCalendar(@PathVariable Long id) {
        log.debug("REST request to delete CCalendar : {}", id);
        cCalendarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
