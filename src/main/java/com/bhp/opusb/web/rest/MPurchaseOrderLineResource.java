package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPurchaseOrderLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.dto.MPurchaseOrderLineCriteria;
import com.bhp.opusb.service.MPurchaseOrderLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPurchaseOrderLine}.
 */
@RestController
@RequestMapping("/api")
public class MPurchaseOrderLineResource {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderLineResource.class);

    private static final String ENTITY_NAME = "mPurchaseOrderLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPurchaseOrderLineService mPurchaseOrderLineService;

    private final MPurchaseOrderLineQueryService mPurchaseOrderLineQueryService;

    public MPurchaseOrderLineResource(MPurchaseOrderLineService mPurchaseOrderLineService, MPurchaseOrderLineQueryService mPurchaseOrderLineQueryService) {
        this.mPurchaseOrderLineService = mPurchaseOrderLineService;
        this.mPurchaseOrderLineQueryService = mPurchaseOrderLineQueryService;
    }

    /**
     * {@code POST  /m-purchase-order-lines} : Create a new mPurchaseOrderLine.
     *
     * @param mPurchaseOrderLineDTO the mPurchaseOrderLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPurchaseOrderLineDTO, or with status {@code 400 (Bad Request)} if the mPurchaseOrderLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-purchase-order-lines")
    public ResponseEntity<MPurchaseOrderLineDTO> createMPurchaseOrderLine(@Valid @RequestBody MPurchaseOrderLineDTO mPurchaseOrderLineDTO) throws URISyntaxException {
        log.debug("REST request to save MPurchaseOrderLine : {}", mPurchaseOrderLineDTO);
        if (mPurchaseOrderLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPurchaseOrderLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPurchaseOrderLineDTO result = mPurchaseOrderLineService.save(mPurchaseOrderLineDTO);
        return ResponseEntity.created(new URI("/api/m-purchase-order-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-purchase-order-lines} : Updates an existing mPurchaseOrderLine.
     *
     * @param mPurchaseOrderLineDTO the mPurchaseOrderLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPurchaseOrderLineDTO,
     * or with status {@code 400 (Bad Request)} if the mPurchaseOrderLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPurchaseOrderLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-purchase-order-lines")
    public ResponseEntity<MPurchaseOrderLineDTO> updateMPurchaseOrderLine(@Valid @RequestBody MPurchaseOrderLineDTO mPurchaseOrderLineDTO) throws URISyntaxException {
        log.debug("REST request to update MPurchaseOrderLine : {}", mPurchaseOrderLineDTO);
        if (mPurchaseOrderLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPurchaseOrderLineDTO result = mPurchaseOrderLineService.save(mPurchaseOrderLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPurchaseOrderLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-purchase-order-lines} : get all the mPurchaseOrderLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPurchaseOrderLines in body.
     */
    @GetMapping("/m-purchase-order-lines")
    public ResponseEntity<List<MPurchaseOrderLineDTO>> getAllMPurchaseOrderLines(MPurchaseOrderLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPurchaseOrderLines by criteria: {}", criteria);
        Page<MPurchaseOrderLineDTO> page = mPurchaseOrderLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-purchase-order-lines/count} : count all the mPurchaseOrderLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-purchase-order-lines/count")
    public ResponseEntity<Long> countMPurchaseOrderLines(MPurchaseOrderLineCriteria criteria) {
        log.debug("REST request to count MPurchaseOrderLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPurchaseOrderLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-purchase-order-lines/:id} : get the "id" mPurchaseOrderLine.
     *
     * @param id the id of the mPurchaseOrderLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPurchaseOrderLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-purchase-order-lines/{id}")
    public ResponseEntity<MPurchaseOrderLineDTO> getMPurchaseOrderLine(@PathVariable Long id) {
        log.debug("REST request to get MPurchaseOrderLine : {}", id);
        Optional<MPurchaseOrderLineDTO> mPurchaseOrderLineDTO = mPurchaseOrderLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPurchaseOrderLineDTO);
    }

    /**
     * {@code DELETE  /m-purchase-order-lines/:id} : delete the "id" mPurchaseOrderLine.
     *
     * @param id the id of the mPurchaseOrderLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-purchase-order-lines/{id}")
    public ResponseEntity<Void> deleteMPurchaseOrderLine(@PathVariable Long id) {
        log.debug("REST request to delete MPurchaseOrderLine : {}", id);
        mPurchaseOrderLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
