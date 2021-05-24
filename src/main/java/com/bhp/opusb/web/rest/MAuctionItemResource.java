package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionItemQueryService;
import com.bhp.opusb.service.MAuctionItemService;
import com.bhp.opusb.service.dto.MAuctionItemCriteria;
import com.bhp.opusb.service.dto.MAuctionItemDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionItem}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionItemResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionItemResource.class);

    private static final String ENTITY_NAME = "mAuctionItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionItemService mAuctionItemService;

    private final MAuctionItemQueryService mAuctionItemQueryService;

    public MAuctionItemResource(MAuctionItemService mAuctionItemService, MAuctionItemQueryService mAuctionItemQueryService) {
        this.mAuctionItemService = mAuctionItemService;
        this.mAuctionItemQueryService = mAuctionItemQueryService;
    }

    /**
     * {@code POST  /m-auction-items} : Create a new mAuctionItem.
     *
     * @param mAuctionItemDTO the mAuctionItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionItemDTO, or with status {@code 400 (Bad Request)} if the mAuctionItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-items")
    public ResponseEntity<MAuctionItemDTO> createMAuctionItem(@Valid @RequestBody MAuctionItemDTO mAuctionItemDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionItem : {}", mAuctionItemDTO);
        if (mAuctionItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionItemDTO result = mAuctionItemService.save(mAuctionItemDTO);
        return ResponseEntity.created(new URI("/api/m-auction-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-items} : Updates an existing mAuctionItem.
     *
     * @param mAuctionItemDTO the mAuctionItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionItemDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-items")
    public ResponseEntity<MAuctionItemDTO> updateMAuctionItem(@Valid @RequestBody MAuctionItemDTO mAuctionItemDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionItem : {}", mAuctionItemDTO);
        if (mAuctionItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionItemDTO result = mAuctionItemService.save(mAuctionItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-items} : get all the mAuctionItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionItems in body.
     */
    @GetMapping("/m-auction-items")
    public ResponseEntity<List<MAuctionItemDTO>> getAllMAuctionItems(MAuctionItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionItems by criteria: {}", criteria);
        Page<MAuctionItemDTO> page = mAuctionItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-items/count} : count all the mAuctionItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-items/count")
    public ResponseEntity<Long> countMAuctionItems(MAuctionItemCriteria criteria) {
        log.debug("REST request to count MAuctionItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-items/:id} : get the "id" mAuctionItem.
     *
     * @param id the id of the mAuctionItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-items/{id}")
    public ResponseEntity<MAuctionItemDTO> getMAuctionItem(@PathVariable Long id) {
        log.debug("REST request to get MAuctionItem : {}", id);
        Optional<MAuctionItemDTO> mAuctionItemDTO = mAuctionItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionItemDTO);
    }

    /**
     * {@code DELETE  /m-auction-items/:id} : delete the "id" mAuctionItem.
     *
     * @param id the id of the mAuctionItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-items/{id}")
    public ResponseEntity<Void> deleteMAuctionItem(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionItem : {}", id);
        mAuctionItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
