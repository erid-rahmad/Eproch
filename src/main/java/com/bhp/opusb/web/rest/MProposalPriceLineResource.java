package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalPriceLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalPriceLineDTO;
import com.bhp.opusb.service.dto.MProposalPriceLineCriteria;
import com.bhp.opusb.service.MProposalPriceLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalPriceLine}.
 */
@RestController
@RequestMapping("/api")
public class MProposalPriceLineResource {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceLineResource.class);

    private static final String ENTITY_NAME = "mProposalPriceLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalPriceLineService mProposalPriceLineService;

    private final MProposalPriceLineQueryService mProposalPriceLineQueryService;

    public MProposalPriceLineResource(MProposalPriceLineService mProposalPriceLineService, MProposalPriceLineQueryService mProposalPriceLineQueryService) {
        this.mProposalPriceLineService = mProposalPriceLineService;
        this.mProposalPriceLineQueryService = mProposalPriceLineQueryService;
    }

    /**
     * {@code POST  /m-proposal-price-lines} : Create a new mProposalPriceLine.
     *
     * @param mProposalPriceLineDTO the mProposalPriceLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalPriceLineDTO, or with status {@code 400 (Bad Request)} if the mProposalPriceLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-price-lines")
    public ResponseEntity<MProposalPriceLineDTO> createMProposalPriceLine(@Valid @RequestBody MProposalPriceLineDTO mProposalPriceLineDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalPriceLine : {}", mProposalPriceLineDTO);
        if (mProposalPriceLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalPriceLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalPriceLineDTO result = mProposalPriceLineService.save(mProposalPriceLineDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-price-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-proposal-price-lines} : Updates an existing mProposalPriceLine.
     *
     * @param mProposalPriceLineDTO the mProposalPriceLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalPriceLineDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalPriceLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalPriceLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-price-lines")
    public ResponseEntity<MProposalPriceLineDTO> updateMProposalPriceLine(@Valid @RequestBody MProposalPriceLineDTO mProposalPriceLineDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalPriceLine : {}", mProposalPriceLineDTO);
        if (mProposalPriceLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalPriceLineDTO result = mProposalPriceLineService.save(mProposalPriceLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalPriceLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-price-lines} : get all the mProposalPriceLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalPriceLines in body.
     */
    @GetMapping("/m-proposal-price-lines")
    public ResponseEntity<List<MProposalPriceLineDTO>> getAllMProposalPriceLines(MProposalPriceLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalPriceLines by criteria: {}", criteria);
        Page<MProposalPriceLineDTO> page = mProposalPriceLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-price-lines/count} : count all the mProposalPriceLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-price-lines/count")
    public ResponseEntity<Long> countMProposalPriceLines(MProposalPriceLineCriteria criteria) {
        log.debug("REST request to count MProposalPriceLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalPriceLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-price-lines/:id} : get the "id" mProposalPriceLine.
     *
     * @param id the id of the mProposalPriceLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalPriceLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-price-lines/{id}")
    public ResponseEntity<MProposalPriceLineDTO> getMProposalPriceLine(@PathVariable Long id) {
        log.debug("REST request to get MProposalPriceLine : {}", id);
        Optional<MProposalPriceLineDTO> mProposalPriceLineDTO = mProposalPriceLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalPriceLineDTO);
    }

    /**
     * {@code DELETE  /m-proposal-price-lines/:id} : delete the "id" mProposalPriceLine.
     *
     * @param id the id of the mProposalPriceLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-price-lines/{id}")
    public ResponseEntity<Void> deleteMProposalPriceLine(@PathVariable Long id) {
        log.debug("REST request to delete MProposalPriceLine : {}", id);
        mProposalPriceLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
