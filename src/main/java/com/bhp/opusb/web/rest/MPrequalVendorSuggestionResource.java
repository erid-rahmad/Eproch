package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalVendorSuggestionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionDTO;
import com.bhp.opusb.service.dto.MPrequalVendorSuggestionCriteria;
import com.bhp.opusb.domain.MPrequalVendorSuggestion;
import com.bhp.opusb.service.MPrequalVendorSuggestionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalVendorSuggestion}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalVendorSuggestionResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalVendorSuggestionResource.class);

    private static final String ENTITY_NAME = "mPrequalVendorSuggestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalVendorSuggestionService mPrequalVendorSuggestionService;

    private final MPrequalVendorSuggestionQueryService mPrequalVendorSuggestionQueryService;

    public MPrequalVendorSuggestionResource(MPrequalVendorSuggestionService mPrequalVendorSuggestionService, MPrequalVendorSuggestionQueryService mPrequalVendorSuggestionQueryService) {
        this.mPrequalVendorSuggestionService = mPrequalVendorSuggestionService;
        this.mPrequalVendorSuggestionQueryService = mPrequalVendorSuggestionQueryService;
    }

    /**
     * {@code POST  /m-prequal-vendor-suggestions} : Create a new mPrequalVendorSuggestion.
     *
     * @param mPrequalVendorSuggestionDTO the mPrequalVendorSuggestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalVendorSuggestionDTO, or with status {@code 400 (Bad Request)} if the mPrequalVendorSuggestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequal-vendor-suggestions")
    public ResponseEntity<MPrequalVendorSuggestionDTO> createMPrequalVendorSuggestion(@Valid @RequestBody MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalVendorSuggestion : {}", mPrequalVendorSuggestionDTO);
        if (mPrequalVendorSuggestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalVendorSuggestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalVendorSuggestionDTO result = mPrequalVendorSuggestionService.save(mPrequalVendorSuggestionDTO);
        return ResponseEntity.created(new URI("/api/m-prequal-vendor-suggestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequal-vendor-suggestions} : Updates an existing mPrequalVendorSuggestion.
     *
     * @param mPrequalVendorSuggestionDTO the mPrequalVendorSuggestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalVendorSuggestionDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalVendorSuggestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalVendorSuggestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequal-vendor-suggestions")
    public ResponseEntity<MPrequalVendorSuggestionDTO> updateMPrequalVendorSuggestion(@Valid @RequestBody MPrequalVendorSuggestionDTO mPrequalVendorSuggestionDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalVendorSuggestion : {}", mPrequalVendorSuggestionDTO);
        if (mPrequalVendorSuggestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalVendorSuggestionDTO result = mPrequalVendorSuggestionService.save(mPrequalVendorSuggestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalVendorSuggestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequal-vendor-suggestions} : get all the mPrequalVendorSuggestions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalVendorSuggestions in body.
     */
    @GetMapping("/m-prequal-vendor-suggestions")
    public ResponseEntity<List<MPrequalVendorSuggestionDTO>> getAllMPrequalVendorSuggestions(MPrequalVendorSuggestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalVendorSuggestions by criteria: {}", criteria);
        Page<MPrequalVendorSuggestionDTO> page = mPrequalVendorSuggestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/m-prequal-vendor-suggestions-nested")
    public ResponseEntity<List<MPrequalVendorSuggestion>> getAllMVendorSuggestions() {
        log.debug("REST request to get MVendorSuggestions by criteria: {}");
        return ResponseEntity.ok().body(mPrequalVendorSuggestionService.findallnested());
    }

    @GetMapping("/m-prequal-vendor-suggestions-nested/{id}")
    public ResponseEntity<Optional<MPrequalVendorSuggestion>> getMVendorSuggestions(@PathVariable Long id) {
        log.debug("REST request to get MVendorSuggestions by criteria: {}");
        return ResponseEntity.ok().body(mPrequalVendorSuggestionService.findanested(id));
    }

    /**
     * {@code GET  /m-prequal-vendor-suggestions/count} : count all the mPrequalVendorSuggestions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequal-vendor-suggestions/count")
    public ResponseEntity<Long> countMPrequalVendorSuggestions(MPrequalVendorSuggestionCriteria criteria) {
        log.debug("REST request to count MPrequalVendorSuggestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalVendorSuggestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequal-vendor-suggestions/:id} : get the "id" mPrequalVendorSuggestion.
     *
     * @param id the id of the mPrequalVendorSuggestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalVendorSuggestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequal-vendor-suggestions/{id}")
    public ResponseEntity<MPrequalVendorSuggestionDTO> getMPrequalVendorSuggestion(@PathVariable Long id) {
        log.debug("REST request to get MPrequalVendorSuggestion : {}", id);
        Optional<MPrequalVendorSuggestionDTO> mPrequalVendorSuggestionDTO = mPrequalVendorSuggestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalVendorSuggestionDTO);
    }

    /**
     * {@code DELETE  /m-prequal-vendor-suggestions/:id} : delete the "id" mPrequalVendorSuggestion.
     *
     * @param id the id of the mPrequalVendorSuggestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequal-vendor-suggestions/{id}")
    public ResponseEntity<Void> deleteMPrequalVendorSuggestion(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalVendorSuggestion : {}", id);
        mPrequalVendorSuggestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
