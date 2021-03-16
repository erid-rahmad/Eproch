package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MSubmissionSubItemService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MSubmissionSubItemDTO;
import com.bhp.opusb.service.dto.MSubmissionSubItemCriteria;
import com.bhp.opusb.service.MSubmissionSubItemQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MSubmissionSubItem}.
 */
@RestController
@RequestMapping("/api")
public class MSubmissionSubItemResource {

    private final Logger log = LoggerFactory.getLogger(MSubmissionSubItemResource.class);

    private static final String ENTITY_NAME = "mSubmissionSubItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MSubmissionSubItemService mSubmissionSubItemService;

    private final MSubmissionSubItemQueryService mSubmissionSubItemQueryService;

    public MSubmissionSubItemResource(MSubmissionSubItemService mSubmissionSubItemService, MSubmissionSubItemQueryService mSubmissionSubItemQueryService) {
        this.mSubmissionSubItemService = mSubmissionSubItemService;
        this.mSubmissionSubItemQueryService = mSubmissionSubItemQueryService;
    }

    /**
     * {@code POST  /m-submission-sub-items} : Create a new mSubmissionSubItem.
     *
     * @param mSubmissionSubItemDTO the mSubmissionSubItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mSubmissionSubItemDTO, or with status {@code 400 (Bad Request)} if the mSubmissionSubItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-submission-sub-items")
    public ResponseEntity<MSubmissionSubItemDTO> createMSubmissionSubItem(@Valid @RequestBody MSubmissionSubItemDTO mSubmissionSubItemDTO) throws URISyntaxException {
        log.debug("REST request to save MSubmissionSubItem : {}", mSubmissionSubItemDTO);
        if (mSubmissionSubItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mSubmissionSubItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MSubmissionSubItemDTO result = mSubmissionSubItemService.save(mSubmissionSubItemDTO);
        return ResponseEntity.created(new URI("/api/m-submission-sub-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-submission-sub-items} : Updates an existing mSubmissionSubItem.
     *
     * @param mSubmissionSubItemDTO the mSubmissionSubItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mSubmissionSubItemDTO,
     * or with status {@code 400 (Bad Request)} if the mSubmissionSubItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mSubmissionSubItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-submission-sub-items")
    public ResponseEntity<MSubmissionSubItemDTO> updateMSubmissionSubItem(@Valid @RequestBody MSubmissionSubItemDTO mSubmissionSubItemDTO) throws URISyntaxException {
        log.debug("REST request to update MSubmissionSubItem : {}", mSubmissionSubItemDTO);
        if (mSubmissionSubItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MSubmissionSubItemDTO result = mSubmissionSubItemService.save(mSubmissionSubItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mSubmissionSubItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-submission-sub-items} : get all the mSubmissionSubItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mSubmissionSubItems in body.
     */
    @GetMapping("/m-submission-sub-items")
    public ResponseEntity<List<MSubmissionSubItemDTO>> getAllMSubmissionSubItems(MSubmissionSubItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MSubmissionSubItems by criteria: {}", criteria);
        Page<MSubmissionSubItemDTO> page = mSubmissionSubItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-submission-sub-items/count} : count all the mSubmissionSubItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-submission-sub-items/count")
    public ResponseEntity<Long> countMSubmissionSubItems(MSubmissionSubItemCriteria criteria) {
        log.debug("REST request to count MSubmissionSubItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(mSubmissionSubItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-submission-sub-items/:id} : get the "id" mSubmissionSubItem.
     *
     * @param id the id of the mSubmissionSubItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mSubmissionSubItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-submission-sub-items/{id}")
    public ResponseEntity<MSubmissionSubItemDTO> getMSubmissionSubItem(@PathVariable Long id) {
        log.debug("REST request to get MSubmissionSubItem : {}", id);
        Optional<MSubmissionSubItemDTO> mSubmissionSubItemDTO = mSubmissionSubItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mSubmissionSubItemDTO);
    }

    /**
     * {@code DELETE  /m-submission-sub-items/:id} : delete the "id" mSubmissionSubItem.
     *
     * @param id the id of the mSubmissionSubItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-submission-sub-items/{id}")
    public ResponseEntity<Void> deleteMSubmissionSubItem(@PathVariable Long id) {
        log.debug("REST request to delete MSubmissionSubItem : {}", id);
        mSubmissionSubItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
