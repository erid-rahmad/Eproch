package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingSubItemService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;
import com.bhp.opusb.service.dto.MBiddingSubItemCriteria;
import com.bhp.opusb.service.MBiddingSubItemQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingSubItem}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingSubItemResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubItemResource.class);

    private static final String ENTITY_NAME = "mBiddingSubItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingSubItemService mBiddingSubItemService;

    private final MBiddingSubItemQueryService mBiddingSubItemQueryService;

    public MBiddingSubItemResource(MBiddingSubItemService mBiddingSubItemService, MBiddingSubItemQueryService mBiddingSubItemQueryService) {
        this.mBiddingSubItemService = mBiddingSubItemService;
        this.mBiddingSubItemQueryService = mBiddingSubItemQueryService;
    }

    /**
     * {@code POST  /m-bidding-sub-items} : Create a new mBiddingSubItem.
     *
     * @param mBiddingSubItemDTO the mBiddingSubItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingSubItemDTO, or with status {@code 400 (Bad Request)} if the mBiddingSubItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-sub-items")
    public ResponseEntity<MBiddingSubItemDTO> createMBiddingSubItem(@Valid @RequestBody MBiddingSubItemDTO mBiddingSubItemDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingSubItem : {}", mBiddingSubItemDTO);
        if (mBiddingSubItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingSubItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingSubItemDTO result = mBiddingSubItemService.save(mBiddingSubItemDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-sub-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-sub-items} : Updates an existing mBiddingSubItem.
     *
     * @param mBiddingSubItemDTO the mBiddingSubItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingSubItemDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingSubItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingSubItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-sub-items")
    public ResponseEntity<MBiddingSubItemDTO> updateMBiddingSubItem(@Valid @RequestBody MBiddingSubItemDTO mBiddingSubItemDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingSubItem : {}", mBiddingSubItemDTO);
        if (mBiddingSubItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingSubItemDTO result = mBiddingSubItemService.save(mBiddingSubItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingSubItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-sub-items} : get all the mBiddingSubItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingSubItems in body.
     */
    @GetMapping("/m-bidding-sub-items")
    public ResponseEntity<List<MBiddingSubItemDTO>> getAllMBiddingSubItems(MBiddingSubItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingSubItems by criteria: {}", criteria);
        Page<MBiddingSubItemDTO> page = mBiddingSubItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-sub-items/count} : count all the mBiddingSubItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-sub-items/count")
    public ResponseEntity<Long> countMBiddingSubItems(MBiddingSubItemCriteria criteria) {
        log.debug("REST request to count MBiddingSubItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingSubItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-sub-items/:id} : get the "id" mBiddingSubItem.
     *
     * @param id the id of the mBiddingSubItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingSubItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-sub-items/{id}")
    public ResponseEntity<MBiddingSubItemDTO> getMBiddingSubItem(@PathVariable Long id) {
        log.debug("REST request to get MBiddingSubItem : {}", id);
        Optional<MBiddingSubItemDTO> mBiddingSubItemDTO = mBiddingSubItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingSubItemDTO);
    }

    /**
     * {@code DELETE  /m-bidding-sub-items/:id} : delete the "id" mBiddingSubItem.
     *
     * @param id the id of the mBiddingSubItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-sub-items/{id}")
    public ResponseEntity<Void> deleteMBiddingSubItem(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingSubItem : {}", id);
        mBiddingSubItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
