package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ReferenceListService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ReferenceListDTO;
import com.bhp.opusb.service.dto.ReferenceListCriteria;
import com.bhp.opusb.service.ReferenceListQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.ReferenceList}.
 */
@RestController
@RequestMapping("/api")
public class ReferenceListResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceListResource.class);

    private static final String ENTITY_NAME = "referenceList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReferenceListService referenceListService;

    private final ReferenceListQueryService referenceListQueryService;

    public ReferenceListResource(ReferenceListService referenceListService, ReferenceListQueryService referenceListQueryService) {
        this.referenceListService = referenceListService;
        this.referenceListQueryService = referenceListQueryService;
    }

    /**
     * {@code POST  /reference-lists} : Create a new referenceList.
     *
     * @param referenceListDTO the referenceListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new referenceListDTO, or with status {@code 400 (Bad Request)} if the referenceList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reference-lists")
    public ResponseEntity<ReferenceListDTO> createReferenceList(@RequestBody ReferenceListDTO referenceListDTO) throws URISyntaxException {
        log.debug("REST request to save ReferenceList : {}", referenceListDTO);
        if (referenceListDTO.getId() != null) {
            throw new BadRequestAlertException("A new referenceList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReferenceListDTO result = referenceListService.save(referenceListDTO);
        return ResponseEntity.created(new URI("/api/reference-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reference-lists} : Updates an existing referenceList.
     *
     * @param referenceListDTO the referenceListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated referenceListDTO,
     * or with status {@code 400 (Bad Request)} if the referenceListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the referenceListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reference-lists")
    public ResponseEntity<ReferenceListDTO> updateReferenceList(@RequestBody ReferenceListDTO referenceListDTO) throws URISyntaxException {
        log.debug("REST request to update ReferenceList : {}", referenceListDTO);
        if (referenceListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReferenceListDTO result = referenceListService.save(referenceListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, referenceListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reference-lists} : get all the referenceLists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of referenceLists in body.
     */
    @GetMapping("/reference-lists")
    public ResponseEntity<List<ReferenceListDTO>> getAllReferenceLists(ReferenceListCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ReferenceLists by criteria: {}", criteria);
        Page<ReferenceListDTO> page = referenceListQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reference-lists/count} : count all the referenceLists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/reference-lists/count")
    public ResponseEntity<Long> countReferenceLists(ReferenceListCriteria criteria) {
        log.debug("REST request to count ReferenceLists by criteria: {}", criteria);
        return ResponseEntity.ok().body(referenceListQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /reference-lists/:id} : get the "id" referenceList.
     *
     * @param id the id of the referenceListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the referenceListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reference-lists/{id}")
    public ResponseEntity<ReferenceListDTO> getReferenceList(@PathVariable Long id) {
        log.debug("REST request to get ReferenceList : {}", id);
        Optional<ReferenceListDTO> referenceListDTO = referenceListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(referenceListDTO);
    }

    /**
     * {@code DELETE  /reference-lists/:id} : delete the "id" referenceList.
     *
     * @param id the id of the referenceListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reference-lists/{id}")
    public ResponseEntity<Void> deleteReferenceList(@PathVariable Long id) {
        log.debug("REST request to delete ReferenceList : {}", id);
        referenceListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
