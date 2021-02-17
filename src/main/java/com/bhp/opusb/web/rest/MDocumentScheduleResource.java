package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MDocumentScheduleService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MDocumentScheduleDTO;
import com.bhp.opusb.service.dto.MDocumentScheduleCriteria;
import com.bhp.opusb.service.MDocumentScheduleQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MDocumentSchedule}.
 */
@RestController
@RequestMapping("/api")
public class MDocumentScheduleResource {

    private final Logger log = LoggerFactory.getLogger(MDocumentScheduleResource.class);

    private static final String ENTITY_NAME = "mDocumentSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MDocumentScheduleService mDocumentScheduleService;

    private final MDocumentScheduleQueryService mDocumentScheduleQueryService;

    public MDocumentScheduleResource(MDocumentScheduleService mDocumentScheduleService, MDocumentScheduleQueryService mDocumentScheduleQueryService) {
        this.mDocumentScheduleService = mDocumentScheduleService;
        this.mDocumentScheduleQueryService = mDocumentScheduleQueryService;
    }

    /**
     * {@code POST  /m-document-schedules} : Create a new mDocumentSchedule.
     *
     * @param mDocumentScheduleDTO the mDocumentScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mDocumentScheduleDTO, or with status {@code 400 (Bad Request)} if the mDocumentSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-document-schedules")
    public ResponseEntity<MDocumentScheduleDTO> createMDocumentSchedule(@Valid @RequestBody MDocumentScheduleDTO mDocumentScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save MDocumentSchedule : {}", mDocumentScheduleDTO);
        if (mDocumentScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new mDocumentSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MDocumentScheduleDTO result = mDocumentScheduleService.save(mDocumentScheduleDTO);
        return ResponseEntity.created(new URI("/api/m-document-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-document-schedules} : Updates an existing mDocumentSchedule.
     *
     * @param mDocumentScheduleDTO the mDocumentScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mDocumentScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the mDocumentScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mDocumentScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-document-schedules")
    public ResponseEntity<MDocumentScheduleDTO> updateMDocumentSchedule(@Valid @RequestBody MDocumentScheduleDTO mDocumentScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update MDocumentSchedule : {}", mDocumentScheduleDTO);
        if (mDocumentScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MDocumentScheduleDTO result = mDocumentScheduleService.save(mDocumentScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mDocumentScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-document-schedules} : get all the mDocumentSchedules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mDocumentSchedules in body.
     */
    @GetMapping("/m-document-schedules")
    public ResponseEntity<List<MDocumentScheduleDTO>> getAllMDocumentSchedules(MDocumentScheduleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MDocumentSchedules by criteria: {}", criteria);
        Page<MDocumentScheduleDTO> page = mDocumentScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-document-schedules/count} : count all the mDocumentSchedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-document-schedules/count")
    public ResponseEntity<Long> countMDocumentSchedules(MDocumentScheduleCriteria criteria) {
        log.debug("REST request to count MDocumentSchedules by criteria: {}", criteria);
        return ResponseEntity.ok().body(mDocumentScheduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-document-schedules/:id} : get the "id" mDocumentSchedule.
     *
     * @param id the id of the mDocumentScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mDocumentScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-document-schedules/{id}")
    public ResponseEntity<MDocumentScheduleDTO> getMDocumentSchedule(@PathVariable Long id) {
        log.debug("REST request to get MDocumentSchedule : {}", id);
        Optional<MDocumentScheduleDTO> mDocumentScheduleDTO = mDocumentScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mDocumentScheduleDTO);
    }

    /**
     * {@code DELETE  /m-document-schedules/:id} : delete the "id" mDocumentSchedule.
     *
     * @param id the id of the mDocumentScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-document-schedules/{id}")
    public ResponseEntity<Void> deleteMDocumentSchedule(@PathVariable Long id) {
        log.debug("REST request to delete MDocumentSchedule : {}", id);
        mDocumentScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
