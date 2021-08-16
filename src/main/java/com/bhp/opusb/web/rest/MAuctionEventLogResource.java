package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.domain.view.AmountView;
import com.bhp.opusb.service.MAuctionEventLogQueryService;
import com.bhp.opusb.service.MAuctionEventLogService;
import com.bhp.opusb.service.dto.MAuctionEventLogCriteria;
import com.bhp.opusb.service.dto.MAuctionEventLogDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionEventLog}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionEventLogResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionEventLogResource.class);

    private static final String ENTITY_NAME = "mAuctionEventLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionEventLogService mAuctionEventLogService;

    private final MAuctionEventLogQueryService mAuctionEventLogQueryService;

    public MAuctionEventLogResource(MAuctionEventLogService mAuctionEventLogService, MAuctionEventLogQueryService mAuctionEventLogQueryService) {
        this.mAuctionEventLogService = mAuctionEventLogService;
        this.mAuctionEventLogQueryService = mAuctionEventLogQueryService;
    }

    /**
     * {@code POST  /m-auction-event-logs} : Create a new mAuctionEventLog.
     *
     * @param mAuctionEventLogDTO the mAuctionEventLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionEventLogDTO, or with status {@code 400 (Bad Request)} if the mAuctionEventLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-event-logs")
    public ResponseEntity<MAuctionEventLogDTO> createMAuctionEventLog(@Valid @RequestBody MAuctionEventLogDTO mAuctionEventLogDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionEventLog : {}", mAuctionEventLogDTO);
        if (mAuctionEventLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionEventLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionEventLogDTO result = mAuctionEventLogService.save(mAuctionEventLogDTO);
        return ResponseEntity.created(new URI("/api/m-auction-event-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-event-logs} : Updates an existing mAuctionEventLog.
     *
     * @param mAuctionEventLogDTO the mAuctionEventLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionEventLogDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionEventLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionEventLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-event-logs")
    public ResponseEntity<MAuctionEventLogDTO> updateMAuctionEventLog(@Valid @RequestBody MAuctionEventLogDTO mAuctionEventLogDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionEventLog : {}", mAuctionEventLogDTO);
        if (mAuctionEventLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionEventLogDTO result = mAuctionEventLogService.save(mAuctionEventLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionEventLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-event-logs} : get all the mAuctionEventLogs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionEventLogs in body.
     */
    @GetMapping("/m-auction-event-logs")
    public ResponseEntity<List<MAuctionEventLogDTO>> getAllMAuctionEventLogs(MAuctionEventLogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionEventLogs by criteria: {}", criteria);
        Page<MAuctionEventLogDTO> page = mAuctionEventLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-event-logs/count} : count all the mAuctionEventLogs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-event-logs/count")
    public ResponseEntity<Long> countMAuctionEventLogs(MAuctionEventLogCriteria criteria) {
        log.debug("REST request to count MAuctionEventLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionEventLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-event-logs/:id} : get the "id" mAuctionEventLog.
     *
     * @param id the id of the mAuctionEventLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionEventLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-event-logs/{id}")
    public ResponseEntity<MAuctionEventLogDTO> getMAuctionEventLog(@PathVariable Long id) {
        log.debug("REST request to get MAuctionEventLog : {}", id);
        Optional<MAuctionEventLogDTO> mAuctionEventLogDTO = mAuctionEventLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionEventLogDTO);
    }

    /**
     * {@code GET  /m-auction-event-logs/min-price/:itemId} : get the minimum price of "itemId".
     *
     * @param itemId the id of the mAuctionEventItem.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and minimum price as the body.
     */
    @GetMapping("/m-auction-event-logs/min-price/{itemId}")
    public ResponseEntity<AmountView> getItemsMinPrice(@PathVariable Long itemId) {
        log.debug("REST request to get the minimum price of an auction item : {}", itemId);
        AmountView view = mAuctionEventLogQueryService.getItemsMinPrice(itemId);
        return ResponseEntity.ok().body(view);
    }

    /**
     * {@code DELETE  /m-auction-event-logs/:id} : delete the "id" mAuctionEventLog.
     *
     * @param id the id of the mAuctionEventLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-event-logs/{id}")
    public ResponseEntity<Void> deleteMAuctionEventLog(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionEventLog : {}", id);
        mAuctionEventLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
