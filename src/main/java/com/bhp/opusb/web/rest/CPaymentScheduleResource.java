package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CPaymentScheduleQueryService;
import com.bhp.opusb.service.CPaymentScheduleService;
import com.bhp.opusb.service.dto.CPaymentScheduleCriteria;
import com.bhp.opusb.service.dto.CPaymentScheduleDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CPaymentSchedule}.
 */
@RestController
@RequestMapping("/api")
public class CPaymentScheduleResource {

    private final Logger log = LoggerFactory.getLogger(CPaymentScheduleResource.class);

    private static final String ENTITY_NAME = "cPaymentSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPaymentScheduleService cPaymentScheduleService;

    private final CPaymentScheduleQueryService cPaymentScheduleQueryService;

    public CPaymentScheduleResource(CPaymentScheduleService cPaymentScheduleService, CPaymentScheduleQueryService cPaymentScheduleQueryService) {
        this.cPaymentScheduleService = cPaymentScheduleService;
        this.cPaymentScheduleQueryService = cPaymentScheduleQueryService;
    }

    /**
     * {@code POST  /c-payment-schedules} : Create a new cPaymentSchedule.
     *
     * @param cPaymentScheduleDTO the cPaymentScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPaymentScheduleDTO, or with status {@code 400 (Bad Request)} if the cPaymentSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-payment-schedules")
    public ResponseEntity<CPaymentScheduleDTO> createCPaymentSchedule(@Valid @RequestBody CPaymentScheduleDTO cPaymentScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save CPaymentSchedule : {}", cPaymentScheduleDTO);
        if (cPaymentScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPaymentSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPaymentScheduleDTO result = cPaymentScheduleService.save(cPaymentScheduleDTO);
        return ResponseEntity.created(new URI("/api/c-payment-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-payment-schedules} : Updates an existing cPaymentSchedule.
     *
     * @param cPaymentScheduleDTO the cPaymentScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPaymentScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the cPaymentScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPaymentScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-payment-schedules")
    public ResponseEntity<CPaymentScheduleDTO> updateCPaymentSchedule(@Valid @RequestBody CPaymentScheduleDTO cPaymentScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update CPaymentSchedule : {}", cPaymentScheduleDTO);
        if (cPaymentScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPaymentScheduleDTO result = cPaymentScheduleService.save(cPaymentScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPaymentScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-payment-schedules} : get all the cPaymentSchedules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPaymentSchedules in body.
     */
    @GetMapping("/c-payment-schedules")
    public ResponseEntity<List<CPaymentScheduleDTO>> getAllCPaymentSchedules(CPaymentScheduleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPaymentSchedules by criteria: {}", criteria);
        Page<CPaymentScheduleDTO> page = cPaymentScheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-payment-schedules/count} : count all the cPaymentSchedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-payment-schedules/count")
    public ResponseEntity<Long> countCPaymentSchedules(CPaymentScheduleCriteria criteria) {
        log.debug("REST request to count CPaymentSchedules by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPaymentScheduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-payment-schedules/:id} : get the "id" cPaymentSchedule.
     *
     * @param id the id of the cPaymentScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPaymentScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-payment-schedules/{id}")
    public ResponseEntity<CPaymentScheduleDTO> getCPaymentSchedule(@PathVariable Long id) {
        log.debug("REST request to get CPaymentSchedule : {}", id);
        Optional<CPaymentScheduleDTO> cPaymentScheduleDTO = cPaymentScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPaymentScheduleDTO);
    }

    /**
     * {@code DELETE  /c-payment-schedules/:id} : delete the "id" cPaymentSchedule.
     *
     * @param id the id of the cPaymentScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-payment-schedules/{id}")
    public ResponseEntity<Void> deleteCPaymentSchedule(@PathVariable Long id) {
        log.debug("REST request to delete CPaymentSchedule : {}", id);
        cPaymentScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
