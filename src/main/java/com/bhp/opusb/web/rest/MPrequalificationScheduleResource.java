package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationScheduleService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationScheduleDTO;
import com.bhp.opusb.service.dto.MPrequalificationScheduleCriteria;
import com.bhp.opusb.service.MPrequalificationScheduleQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationSchedule}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationScheduleResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationScheduleResource.class);

    private static final String ENTITY_NAME = "mPrequalificationSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationScheduleService mPrequalificationScheduleService;

    private final MPrequalificationScheduleQueryService mPrequalificationScheduleQueryService;

    public MPrequalificationScheduleResource(MPrequalificationScheduleService mPrequalificationScheduleService, MPrequalificationScheduleQueryService mPrequalificationScheduleQueryService) {
        this.mPrequalificationScheduleService = mPrequalificationScheduleService;
        this.mPrequalificationScheduleQueryService = mPrequalificationScheduleQueryService;
    }

    /**
     * {@code POST  /m-prequalification-schedules} : Create a new mPrequalificationSchedule.
     *
     * @param mPrequalificationScheduleDTO the mPrequalificationScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationScheduleDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-schedules")
    public ResponseEntity<MPrequalificationScheduleDTO> createMPrequalificationSchedule(@Valid @RequestBody MPrequalificationScheduleDTO mPrequalificationScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationSchedule : {}", mPrequalificationScheduleDTO);
        if (mPrequalificationScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationScheduleDTO result = mPrequalificationScheduleService.save(mPrequalificationScheduleDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-schedules} : Updates an existing mPrequalificationSchedule.
     *
     * @param mPrequalificationScheduleDTO the mPrequalificationScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-schedules")
    public ResponseEntity<MPrequalificationScheduleDTO> updateMPrequalificationSchedule(@Valid @RequestBody MPrequalificationScheduleDTO mPrequalificationScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationSchedule : {}", mPrequalificationScheduleDTO);
        if (mPrequalificationScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationScheduleDTO result = mPrequalificationScheduleService.save(mPrequalificationScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-schedules} : get all the mPrequalificationSchedules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationSchedules in body.
     */
    @GetMapping("/m-prequalification-schedules")
    public ResponseEntity<List<MPrequalificationScheduleDTO>> getAllMPrequalificationSchedules(MPrequalificationScheduleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationSchedules by criteria: {}", criteria);
        Page<MPrequalificationScheduleDTO> page = mPrequalificationScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-schedules/count} : count all the mPrequalificationSchedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-schedules/count")
    public ResponseEntity<Long> countMPrequalificationSchedules(MPrequalificationScheduleCriteria criteria) {
        log.debug("REST request to count MPrequalificationSchedules by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationScheduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-schedules/:id} : get the "id" mPrequalificationSchedule.
     *
     * @param id the id of the mPrequalificationScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-schedules/{id}")
    public ResponseEntity<MPrequalificationScheduleDTO> getMPrequalificationSchedule(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationSchedule : {}", id);
        Optional<MPrequalificationScheduleDTO> mPrequalificationScheduleDTO = mPrequalificationScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationScheduleDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-schedules/:id} : delete the "id" mPrequalificationSchedule.
     *
     * @param id the id of the mPrequalificationScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-schedules/{id}")
    public ResponseEntity<Void> deleteMPrequalificationSchedule(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationSchedule : {}", id);
        mPrequalificationScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
