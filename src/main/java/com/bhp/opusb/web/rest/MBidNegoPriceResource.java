package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBidNegoPriceService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;
import com.bhp.opusb.service.dto.MBidNegoPriceCriteria;
import com.bhp.opusb.service.MBidNegoPriceQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBidNegoPrice}.
 */
@RestController
@RequestMapping("/api")
public class MBidNegoPriceResource {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceResource.class);

    private static final String ENTITY_NAME = "mBidNegoPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBidNegoPriceService mBidNegoPriceService;

    private final MBidNegoPriceQueryService mBidNegoPriceQueryService;

    public MBidNegoPriceResource(MBidNegoPriceService mBidNegoPriceService, MBidNegoPriceQueryService mBidNegoPriceQueryService) {
        this.mBidNegoPriceService = mBidNegoPriceService;
        this.mBidNegoPriceQueryService = mBidNegoPriceQueryService;
    }

    /**
     * {@code POST  /m-bid-nego-prices} : Create a new mBidNegoPrice.
     *
     * @param mBidNegoPriceDTO the mBidNegoPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBidNegoPriceDTO, or with status {@code 400 (Bad Request)} if the mBidNegoPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bid-nego-prices")
    public ResponseEntity<MBidNegoPriceDTO> createMBidNegoPrice(@Valid @RequestBody MBidNegoPriceDTO mBidNegoPriceDTO) throws URISyntaxException {
        log.debug("REST request to save MBidNegoPrice : {}", mBidNegoPriceDTO);
        if (mBidNegoPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBidNegoPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBidNegoPriceDTO result = mBidNegoPriceService.save(mBidNegoPriceDTO);
        return ResponseEntity.created(new URI("/api/m-bid-nego-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bid-nego-prices} : Updates an existing mBidNegoPrice.
     *
     * @param mBidNegoPriceDTO the mBidNegoPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBidNegoPriceDTO,
     * or with status {@code 400 (Bad Request)} if the mBidNegoPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBidNegoPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bid-nego-prices")
    public ResponseEntity<MBidNegoPriceDTO> updateMBidNegoPrice(@Valid @RequestBody MBidNegoPriceDTO mBidNegoPriceDTO) throws URISyntaxException {
        log.debug("REST request to update MBidNegoPrice : {}", mBidNegoPriceDTO);
        if (mBidNegoPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBidNegoPriceDTO result = mBidNegoPriceService.save(mBidNegoPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBidNegoPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bid-nego-prices} : get all the mBidNegoPrices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBidNegoPrices in body.
     */
    @GetMapping("/m-bid-nego-prices")
    public ResponseEntity<List<MBidNegoPriceDTO>> getAllMBidNegoPrices(MBidNegoPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBidNegoPrices by criteria: {}", criteria);
        Page<MBidNegoPriceDTO> page = mBidNegoPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bid-nego-prices/count} : count all the mBidNegoPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bid-nego-prices/count")
    public ResponseEntity<Long> countMBidNegoPrices(MBidNegoPriceCriteria criteria) {
        log.debug("REST request to count MBidNegoPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBidNegoPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bid-nego-prices/:id} : get the "id" mBidNegoPrice.
     *
     * @param id the id of the mBidNegoPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBidNegoPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bid-nego-prices/{id}")
    public ResponseEntity<MBidNegoPriceDTO> getMBidNegoPrice(@PathVariable Long id) {
        log.debug("REST request to get MBidNegoPrice : {}", id);
        Optional<MBidNegoPriceDTO> mBidNegoPriceDTO = mBidNegoPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBidNegoPriceDTO);
    }

    /**
     * {@code DELETE  /m-bid-nego-prices/:id} : delete the "id" mBidNegoPrice.
     *
     * @param id the id of the mBidNegoPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bid-nego-prices/{id}")
    public ResponseEntity<Void> deleteMBidNegoPrice(@PathVariable Long id) {
        log.debug("REST request to delete MBidNegoPrice : {}", id);
        mBidNegoPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
