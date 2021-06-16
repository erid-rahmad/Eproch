package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionSubmissionItemQueryService;
import com.bhp.opusb.service.MAuctionSubmissionItemService;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemCriteria;
import com.bhp.opusb.service.dto.MAuctionSubmissionItemDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionSubmissionItem}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionSubmissionItemResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionSubmissionItemResource.class);

    private static final String ENTITY_NAME = "mAuctionSubmissionItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionSubmissionItemService mAuctionSubmissionItemService;

    private final MAuctionSubmissionItemQueryService mAuctionSubmissionItemQueryService;

    public MAuctionSubmissionItemResource(MAuctionSubmissionItemService mAuctionSubmissionItemService, MAuctionSubmissionItemQueryService mAuctionSubmissionItemQueryService) {
        this.mAuctionSubmissionItemService = mAuctionSubmissionItemService;
        this.mAuctionSubmissionItemQueryService = mAuctionSubmissionItemQueryService;
    }

    /**
     * {@code POST  /m-auction-submission-items} : Create a new mAuctionSubmissionItem.
     *
     * @param mAuctionSubmissionItemDTO the mAuctionSubmissionItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionSubmissionItemDTO, or with status {@code 400 (Bad Request)} if the mAuctionSubmissionItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-submission-items")
    public ResponseEntity<MAuctionSubmissionItemDTO> createMAuctionSubmissionItem(@Valid @RequestBody MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionSubmissionItem : {}", mAuctionSubmissionItemDTO);
        if (mAuctionSubmissionItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionSubmissionItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionSubmissionItemDTO result = mAuctionSubmissionItemService.save(mAuctionSubmissionItemDTO);
        return ResponseEntity.created(new URI("/api/m-auction-submission-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-submission-items} : Updates an existing mAuctionSubmissionItem.
     *
     * @param mAuctionSubmissionItemDTO the mAuctionSubmissionItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionSubmissionItemDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionSubmissionItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionSubmissionItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-submission-items")
    public ResponseEntity<MAuctionSubmissionItemDTO> updateMAuctionSubmissionItem(@Valid @RequestBody MAuctionSubmissionItemDTO mAuctionSubmissionItemDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionSubmissionItem : {}", mAuctionSubmissionItemDTO);
        if (mAuctionSubmissionItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionSubmissionItemDTO result = mAuctionSubmissionItemService.save(mAuctionSubmissionItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionSubmissionItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-submission-items} : get all the mAuctionSubmissionItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionSubmissionItems in body.
     */
    @GetMapping("/m-auction-submission-items")
    public ResponseEntity<List<MAuctionSubmissionItemDTO>> getAllMAuctionSubmissionItems(MAuctionSubmissionItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionSubmissionItems by criteria: {}", criteria);
        Page<MAuctionSubmissionItemDTO> page = mAuctionSubmissionItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-submission-items/count} : count all the mAuctionSubmissionItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-submission-items/count")
    public ResponseEntity<Long> countMAuctionSubmissionItems(MAuctionSubmissionItemCriteria criteria) {
        log.debug("REST request to count MAuctionSubmissionItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionSubmissionItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-submission-items/:id} : get the "id" mAuctionSubmissionItem.
     *
     * @param id the id of the mAuctionSubmissionItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionSubmissionItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-submission-items/{id}")
    public ResponseEntity<MAuctionSubmissionItemDTO> getMAuctionSubmissionItem(@PathVariable Long id) {
        log.debug("REST request to get MAuctionSubmissionItem : {}", id);
        Optional<MAuctionSubmissionItemDTO> mAuctionSubmissionItemDTO = mAuctionSubmissionItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionSubmissionItemDTO);
    }

    /**
     * {@code DELETE  /m-auction-submission-items/:id} : delete the "id" mAuctionSubmissionItem.
     *
     * @param id the id of the mAuctionSubmissionItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-submission-items/{id}")
    public ResponseEntity<Void> deleteMAuctionSubmissionItem(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionSubmissionItem : {}", id);
        mAuctionSubmissionItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
