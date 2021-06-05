package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBidNegoPriceLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBidNegoPriceLineDTO;
import com.bhp.opusb.service.dto.MBidNegoPriceLineCriteria;
import com.bhp.opusb.service.MBidNegoPriceLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBidNegoPriceLine}.
 */
@RestController
@RequestMapping("/api")
public class MBidNegoPriceLineResource {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceLineResource.class);

    private static final String ENTITY_NAME = "mBidNegoPriceLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBidNegoPriceLineService mBidNegoPriceLineService;

    private final MBidNegoPriceLineQueryService mBidNegoPriceLineQueryService;

    public MBidNegoPriceLineResource(MBidNegoPriceLineService mBidNegoPriceLineService, MBidNegoPriceLineQueryService mBidNegoPriceLineQueryService) {
        this.mBidNegoPriceLineService = mBidNegoPriceLineService;
        this.mBidNegoPriceLineQueryService = mBidNegoPriceLineQueryService;
    }

    /**
     * {@code POST  /m-bid-nego-price-lines} : Create a new mBidNegoPriceLine.
     *
     * @param mBidNegoPriceLineDTO the mBidNegoPriceLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBidNegoPriceLineDTO, or with status {@code 400 (Bad Request)} if the mBidNegoPriceLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bid-nego-price-lines")
    public ResponseEntity<MBidNegoPriceLineDTO> createMBidNegoPriceLine(@Valid @RequestBody MBidNegoPriceLineDTO mBidNegoPriceLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBidNegoPriceLine : {}", mBidNegoPriceLineDTO);
        if (mBidNegoPriceLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBidNegoPriceLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBidNegoPriceLineDTO result = mBidNegoPriceLineService.save(mBidNegoPriceLineDTO);
        return ResponseEntity.created(new URI("/api/m-bid-nego-price-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bid-nego-price-lines} : Updates an existing mBidNegoPriceLine.
     *
     * @param mBidNegoPriceLineDTO the mBidNegoPriceLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBidNegoPriceLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBidNegoPriceLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBidNegoPriceLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bid-nego-price-lines")
    public ResponseEntity<MBidNegoPriceLineDTO> updateMBidNegoPriceLine(@Valid @RequestBody MBidNegoPriceLineDTO mBidNegoPriceLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBidNegoPriceLine : {}", mBidNegoPriceLineDTO);
        if (mBidNegoPriceLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBidNegoPriceLineDTO result = mBidNegoPriceLineService.save(mBidNegoPriceLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBidNegoPriceLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bid-nego-price-lines} : get all the mBidNegoPriceLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBidNegoPriceLines in body.
     */
    @GetMapping("/m-bid-nego-price-lines")
    public ResponseEntity<List<MBidNegoPriceLineDTO>> getAllMBidNegoPriceLines(MBidNegoPriceLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBidNegoPriceLines by criteria: {}", criteria);
        Page<MBidNegoPriceLineDTO> page = mBidNegoPriceLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bid-nego-price-lines/count} : count all the mBidNegoPriceLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bid-nego-price-lines/count")
    public ResponseEntity<Long> countMBidNegoPriceLines(MBidNegoPriceLineCriteria criteria) {
        log.debug("REST request to count MBidNegoPriceLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBidNegoPriceLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bid-nego-price-lines/:id} : get the "id" mBidNegoPriceLine.
     *
     * @param id the id of the mBidNegoPriceLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBidNegoPriceLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bid-nego-price-lines/{id}")
    public ResponseEntity<MBidNegoPriceLineDTO> getMBidNegoPriceLine(@PathVariable Long id) {
        log.debug("REST request to get MBidNegoPriceLine : {}", id);
        Optional<MBidNegoPriceLineDTO> mBidNegoPriceLineDTO = mBidNegoPriceLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBidNegoPriceLineDTO);
    }

    /**
     * {@code DELETE  /m-bid-nego-price-lines/:id} : delete the "id" mBidNegoPriceLine.
     *
     * @param id the id of the mBidNegoPriceLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bid-nego-price-lines/{id}")
    public ResponseEntity<Void> deleteMBidNegoPriceLine(@PathVariable Long id) {
        log.debug("REST request to delete MBidNegoPriceLine : {}", id);
        mBidNegoPriceLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
