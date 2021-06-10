package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalPriceService;
import com.bhp.opusb.service.dto.MProposalPriceVM;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalPriceDTO;
import com.bhp.opusb.service.dto.MProposalPriceCriteria;
import com.bhp.opusb.service.MProposalPriceQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalPrice}.
 */
@RestController
@RequestMapping("/api")
public class MProposalPriceResource {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceResource.class);

    private static final String ENTITY_NAME = "mProposalPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalPriceService mProposalPriceService;

    private final MProposalPriceQueryService mProposalPriceQueryService;

    public MProposalPriceResource(MProposalPriceService mProposalPriceService, MProposalPriceQueryService mProposalPriceQueryService) {
        this.mProposalPriceService = mProposalPriceService;
        this.mProposalPriceQueryService = mProposalPriceQueryService;
    }

    /**
     * {@code POST  /m-proposal-prices} : Create a new mProposalPrice.
     *
     * @param mProposalPriceDTO the mProposalPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalPriceDTO, or with status {@code 400 (Bad Request)} if the mProposalPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-prices")
    public ResponseEntity<MProposalPriceDTO> createMProposalPrice(@Valid @RequestBody MProposalPriceDTO mProposalPriceDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalPrice : {}", mProposalPriceDTO);
        if (mProposalPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalPriceDTO result = mProposalPriceService.save(mProposalPriceDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-proposal-prices/form} : Create/update a mProposalPrice form.
     *
     * @param mProposalPriceVM the mProposalPriceDTO to save.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalPriceDTO, or with status {@code 400 (Bad Request)} if the mProposalPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-prices/form")
    public ResponseEntity<MProposalPriceDTO> createMProposalPrice(@Valid @RequestBody MProposalPriceVM mProposalPriceVM) throws URISyntaxException {
        log.debug("REST request to save MProposalPrice form -- : {}", mProposalPriceVM);
        MProposalPriceDTO result = mProposalPriceService.saveForm(mProposalPriceVM);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-proposal-prices} : Updates an existing mProposalPrice.
     *
     * @param mProposalPriceDTO the mProposalPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalPriceDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-prices")
    public ResponseEntity<MProposalPriceDTO> updateMProposalPrice(@Valid @RequestBody MProposalPriceDTO mProposalPriceDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalPrice : {}", mProposalPriceDTO);
        if (mProposalPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalPriceDTO result = mProposalPriceService.save(mProposalPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-prices} : get all the mProposalPrices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalPrices in body.
     */
    @GetMapping("/m-proposal-prices")
    public ResponseEntity<List<MProposalPriceDTO>> getAllMProposalPrices(MProposalPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalPrices by criteria: {}", criteria);
        Page<MProposalPriceDTO> page = mProposalPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-prices/count} : count all the mProposalPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-prices/count")
    public ResponseEntity<Long> countMProposalPrices(MProposalPriceCriteria criteria) {
        log.debug("REST request to count MProposalPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-prices/:id} : get the "id" mProposalPrice.
     *
     * @param id the id of the mProposalPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-prices/{id}")
    public ResponseEntity<MProposalPriceDTO> getMProposalPrice(@PathVariable Long id) {
        log.debug("REST request to get MProposalPrice : {}", id);
        Optional<MProposalPriceDTO> mProposalPriceDTO = mProposalPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalPriceDTO);
    }

    /**
     * {@code DELETE  /m-proposal-prices/:id} : delete the "id" mProposalPrice.
     *
     * @param id the id of the mProposalPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-prices/{id}")
    public ResponseEntity<Void> deleteMProposalPrice(@PathVariable Long id) {
        log.debug("REST request to delete MProposalPrice : {}", id);
        mProposalPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
