package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MProductPriceQueryService;
import com.bhp.opusb.service.MProductPriceService;
import com.bhp.opusb.service.dto.MProductPriceCriteria;
import com.bhp.opusb.service.dto.MProductPriceDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MProductPrice}.
 */
@RestController
@RequestMapping("/api")
public class MProductPriceResource {

    private final Logger log = LoggerFactory.getLogger(MProductPriceResource.class);

    private static final String ENTITY_NAME = "mProductPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProductPriceService mProductPriceService;

    private final MProductPriceQueryService mProductPriceQueryService;

    public MProductPriceResource(MProductPriceService mProductPriceService, MProductPriceQueryService mProductPriceQueryService) {
        this.mProductPriceService = mProductPriceService;
        this.mProductPriceQueryService = mProductPriceQueryService;
    }

    /**
     * {@code POST  /m-product-prices} : Create a new mProductPrice.
     *
     * @param mProductPriceDTO the mProductPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProductPriceDTO, or with status {@code 400 (Bad Request)} if the mProductPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-product-prices")
    public ResponseEntity<MProductPriceDTO> createMProductPrice(@Valid @RequestBody MProductPriceDTO mProductPriceDTO) throws URISyntaxException {
        log.debug("REST request to save MProductPrice : {}", mProductPriceDTO);
        if (mProductPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProductPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProductPriceDTO result = mProductPriceService.save(mProductPriceDTO);
        return ResponseEntity.created(new URI("/api/m-product-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-product-prices} : Updates an existing mProductPrice.
     *
     * @param mProductPriceDTO the mProductPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProductPriceDTO,
     * or with status {@code 400 (Bad Request)} if the mProductPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProductPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-product-prices")
    public ResponseEntity<MProductPriceDTO> updateMProductPrice(@Valid @RequestBody MProductPriceDTO mProductPriceDTO) throws URISyntaxException {
        log.debug("REST request to update MProductPrice : {}", mProductPriceDTO);
        if (mProductPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProductPriceDTO result = mProductPriceService.save(mProductPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProductPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-product-prices} : get all the mProductPrices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProductPrices in body.
     */
    @GetMapping("/m-product-prices")
    public ResponseEntity<List<MProductPriceDTO>> getAllMProductPrices(MProductPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProductPrices by criteria: {}", criteria);
        Page<MProductPriceDTO> page = mProductPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-product-prices/count} : count all the mProductPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-product-prices/count")
    public ResponseEntity<Long> countMProductPrices(MProductPriceCriteria criteria) {
        log.debug("REST request to count MProductPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProductPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-product-prices/:id} : get the "id" mProductPrice.
     *
     * @param id the id of the mProductPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProductPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-product-prices/{id}")
    public ResponseEntity<MProductPriceDTO> getMProductPrice(@PathVariable Long id) {
        log.debug("REST request to get MProductPrice : {}", id);
        Optional<MProductPriceDTO> mProductPriceDTO = mProductPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProductPriceDTO);
    }

    /**
     * {@code DELETE  /m-product-prices/:id} : delete the "id" mProductPrice.
     *
     * @param id the id of the mProductPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-product-prices/{id}")
    public ResponseEntity<Void> deleteMProductPrice(@PathVariable Long id) {
        log.debug("REST request to delete MProductPrice : {}", id);
        mProductPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
