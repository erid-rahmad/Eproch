package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingScheduleService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.dto.MBiddingScheduleCriteria;
import com.bhp.opusb.service.MBiddingScheduleQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingSchedule}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingScheduleResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingScheduleResource.class);

    private static final String ENTITY_NAME = "mBiddingSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingScheduleService mBiddingScheduleService;

    private final MBiddingScheduleQueryService mBiddingScheduleQueryService;

    public MBiddingScheduleResource(MBiddingScheduleService mBiddingScheduleService, MBiddingScheduleQueryService mBiddingScheduleQueryService) {
        this.mBiddingScheduleService = mBiddingScheduleService;
        this.mBiddingScheduleQueryService = mBiddingScheduleQueryService;
    }

    /**
     * {@code POST  /m-bidding-schedules} : Create a new mBiddingSchedule.
     *
     * @param mBiddingScheduleDTO the mBiddingScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingScheduleDTO, or with status {@code 400 (Bad Request)} if the mBiddingSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-schedules")
    public ResponseEntity<MBiddingScheduleDTO> createMBiddingSchedule(@Valid @RequestBody MBiddingScheduleDTO mBiddingScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingSchedule : {}", mBiddingScheduleDTO);
        if (mBiddingScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingScheduleDTO result = mBiddingScheduleService.save(mBiddingScheduleDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-schedules} : Updates an existing mBiddingSchedule.
     *
     * @param mBiddingScheduleDTO the mBiddingScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-schedules")
    public ResponseEntity<MBiddingScheduleDTO> updateMBiddingSchedule(@Valid @RequestBody MBiddingScheduleDTO mBiddingScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingSchedule : {}", mBiddingScheduleDTO);
        if (mBiddingScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingScheduleDTO result = mBiddingScheduleService.save(mBiddingScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-schedules} : get all the mBiddingSchedules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingSchedules in body.
     */
    @GetMapping("/m-bidding-schedules")
    public ResponseEntity<List<MBiddingScheduleDTO>> getAllMBiddingSchedules(MBiddingScheduleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingSchedules by criteria: {}", criteria);
        Page<MBiddingScheduleDTO> page = mBiddingScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-schedules/count} : count all the mBiddingSchedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-schedules/count")
    public ResponseEntity<Long> countMBiddingSchedules(MBiddingScheduleCriteria criteria) {
        log.debug("REST request to count MBiddingSchedules by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingScheduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-schedules/:id} : get the "id" mBiddingSchedule.
     *
     * @param id the id of the mBiddingScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-schedules/{id}")
    public ResponseEntity<MBiddingScheduleDTO> getMBiddingSchedule(@PathVariable Long id) {
        log.debug("REST request to get MBiddingSchedule : {}", id);
        Optional<MBiddingScheduleDTO> mBiddingScheduleDTO = mBiddingScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingScheduleDTO);
    }

    /**
     * {@code DELETE  /m-bidding-schedules/:id} : delete the "id" mBiddingSchedule.
     *
     * @param id the id of the mBiddingScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-schedules/{id}")
    public ResponseEntity<Void> deleteMBiddingSchedule(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingSchedule : {}", id);
        mBiddingScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
