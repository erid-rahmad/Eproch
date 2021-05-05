package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalPriceSubItemService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalPriceSubItemDTO;
import com.bhp.opusb.service.dto.MProposalPriceSubItemCriteria;
import com.bhp.opusb.service.MProposalPriceSubItemQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalPriceSubItem}.
 */
@RestController
@RequestMapping("/api")
public class MProposalPriceSubItemResource {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceSubItemResource.class);

    private static final String ENTITY_NAME = "mProposalPriceSubItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalPriceSubItemService mProposalPriceSubItemService;

    private final MProposalPriceSubItemQueryService mProposalPriceSubItemQueryService;

    public MProposalPriceSubItemResource(MProposalPriceSubItemService mProposalPriceSubItemService, MProposalPriceSubItemQueryService mProposalPriceSubItemQueryService) {
        this.mProposalPriceSubItemService = mProposalPriceSubItemService;
        this.mProposalPriceSubItemQueryService = mProposalPriceSubItemQueryService;
    }

    /**
     * {@code POST  /m-proposal-price-sub-items} : Create a new mProposalPriceSubItem.
     *
     * @param mProposalPriceSubItemDTO the mProposalPriceSubItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalPriceSubItemDTO, or with status {@code 400 (Bad Request)} if the mProposalPriceSubItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-price-sub-items")
    public ResponseEntity<MProposalPriceSubItemDTO> createMProposalPriceSubItem(@Valid @RequestBody MProposalPriceSubItemDTO mProposalPriceSubItemDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalPriceSubItem : {}", mProposalPriceSubItemDTO);
        if (mProposalPriceSubItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalPriceSubItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalPriceSubItemDTO result = mProposalPriceSubItemService.save(mProposalPriceSubItemDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-price-sub-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-proposal-price-sub-items} : Updates an existing mProposalPriceSubItem.
     *
     * @param mProposalPriceSubItemDTO the mProposalPriceSubItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalPriceSubItemDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalPriceSubItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalPriceSubItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-price-sub-items")
    public ResponseEntity<MProposalPriceSubItemDTO> updateMProposalPriceSubItem(@Valid @RequestBody MProposalPriceSubItemDTO mProposalPriceSubItemDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalPriceSubItem : {}", mProposalPriceSubItemDTO);
        if (mProposalPriceSubItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalPriceSubItemDTO result = mProposalPriceSubItemService.save(mProposalPriceSubItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalPriceSubItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-price-sub-items} : get all the mProposalPriceSubItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalPriceSubItems in body.
     */
    @GetMapping("/m-proposal-price-sub-items")
    public ResponseEntity<List<MProposalPriceSubItemDTO>> getAllMProposalPriceSubItems(MProposalPriceSubItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalPriceSubItems by criteria: {}", criteria);
        Page<MProposalPriceSubItemDTO> page = mProposalPriceSubItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-price-sub-items/count} : count all the mProposalPriceSubItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-price-sub-items/count")
    public ResponseEntity<Long> countMProposalPriceSubItems(MProposalPriceSubItemCriteria criteria) {
        log.debug("REST request to count MProposalPriceSubItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalPriceSubItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-price-sub-items/:id} : get the "id" mProposalPriceSubItem.
     *
     * @param id the id of the mProposalPriceSubItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalPriceSubItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-price-sub-items/{id}")
    public ResponseEntity<MProposalPriceSubItemDTO> getMProposalPriceSubItem(@PathVariable Long id) {
        log.debug("REST request to get MProposalPriceSubItem : {}", id);
        Optional<MProposalPriceSubItemDTO> mProposalPriceSubItemDTO = mProposalPriceSubItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalPriceSubItemDTO);
    }

    /**
     * {@code DELETE  /m-proposal-price-sub-items/:id} : delete the "id" mProposalPriceSubItem.
     *
     * @param id the id of the mProposalPriceSubItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-price-sub-items/{id}")
    public ResponseEntity<Void> deleteMProposalPriceSubItem(@PathVariable Long id) {
        log.debug("REST request to delete MProposalPriceSubItem : {}", id);
        mProposalPriceSubItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
