package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVendorSuggestionService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;
import com.bhp.opusb.service.dto.MVendorSuggestionCriteria;
import com.bhp.opusb.service.MVendorSuggestionQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVendorSuggestion}.
 */
@RestController
@RequestMapping("/api")
public class MVendorSuggestionResource {

    private final Logger log = LoggerFactory.getLogger(MVendorSuggestionResource.class);

    private static final String ENTITY_NAME = "mVendorSuggestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVendorSuggestionService mVendorSuggestionService;

    private final MVendorSuggestionQueryService mVendorSuggestionQueryService;

    public MVendorSuggestionResource(MVendorSuggestionService mVendorSuggestionService, MVendorSuggestionQueryService mVendorSuggestionQueryService) {
        this.mVendorSuggestionService = mVendorSuggestionService;
        this.mVendorSuggestionQueryService = mVendorSuggestionQueryService;
    }

    /**
     * {@code POST  /m-vendor-suggestions} : Create a new mVendorSuggestion.
     *
     * @param mVendorSuggestionDTO the mVendorSuggestionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVendorSuggestionDTO, or with status {@code 400 (Bad Request)} if the mVendorSuggestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-vendor-suggestions")
    public ResponseEntity<MVendorSuggestionDTO> createMVendorSuggestion(@Valid @RequestBody MVendorSuggestionDTO mVendorSuggestionDTO) throws URISyntaxException {
        log.debug("REST request to save MVendorSuggestion : {}", mVendorSuggestionDTO);
        if (mVendorSuggestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVendorSuggestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVendorSuggestionDTO result = mVendorSuggestionService.save(mVendorSuggestionDTO);
        return ResponseEntity.created(new URI("/api/m-vendor-suggestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-vendor-suggestions} : Updates an existing mVendorSuggestion.
     *
     * @param mVendorSuggestionDTO the mVendorSuggestionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVendorSuggestionDTO,
     * or with status {@code 400 (Bad Request)} if the mVendorSuggestionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVendorSuggestionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-vendor-suggestions")
    public ResponseEntity<MVendorSuggestionDTO> updateMVendorSuggestion(@Valid @RequestBody MVendorSuggestionDTO mVendorSuggestionDTO) throws URISyntaxException {
        log.debug("REST request to update MVendorSuggestion : {}", mVendorSuggestionDTO);
        if (mVendorSuggestionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVendorSuggestionDTO result = mVendorSuggestionService.save(mVendorSuggestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVendorSuggestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-vendor-suggestions} : get all the mVendorSuggestions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVendorSuggestions in body.
     */
    @GetMapping("/m-vendor-suggestions")
    public ResponseEntity<List<MVendorSuggestionDTO>> getAllMVendorSuggestions(MVendorSuggestionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVendorSuggestions by criteria: {}", criteria);
        Page<MVendorSuggestionDTO> page = mVendorSuggestionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-vendor-suggestions/count} : count all the mVendorSuggestions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-vendor-suggestions/count")
    public ResponseEntity<Long> countMVendorSuggestions(MVendorSuggestionCriteria criteria) {
        log.debug("REST request to count MVendorSuggestions by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVendorSuggestionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-vendor-suggestions/:id} : get the "id" mVendorSuggestion.
     *
     * @param id the id of the mVendorSuggestionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVendorSuggestionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-vendor-suggestions/{id}")
    public ResponseEntity<MVendorSuggestionDTO> getMVendorSuggestion(@PathVariable Long id) {
        log.debug("REST request to get MVendorSuggestion : {}", id);
        Optional<MVendorSuggestionDTO> mVendorSuggestionDTO = mVendorSuggestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVendorSuggestionDTO);
    }

    /**
     * {@code DELETE  /m-vendor-suggestions/:id} : delete the "id" mVendorSuggestion.
     *
     * @param id the id of the mVendorSuggestionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-vendor-suggestions/{id}")
    public ResponseEntity<Void> deleteMVendorSuggestion(@PathVariable Long id) {
        log.debug("REST request to delete MVendorSuggestion : {}", id);
        mVendorSuggestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
