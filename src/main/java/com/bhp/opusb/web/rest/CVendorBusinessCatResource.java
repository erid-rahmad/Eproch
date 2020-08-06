package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CVendorBusinessCatService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CVendorBusinessCatDTO;
import com.bhp.opusb.service.dto.CVendorBusinessCatCriteria;
import com.bhp.opusb.service.CVendorBusinessCatQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorBusinessCat}.
 */
@RestController
@RequestMapping("/api")
public class CVendorBusinessCatResource {

    private final Logger log = LoggerFactory.getLogger(CVendorBusinessCatResource.class);

    private static final String ENTITY_NAME = "cVendorBusinessCat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorBusinessCatService cVendorBusinessCatService;

    private final CVendorBusinessCatQueryService cVendorBusinessCatQueryService;

    public CVendorBusinessCatResource(CVendorBusinessCatService cVendorBusinessCatService, CVendorBusinessCatQueryService cVendorBusinessCatQueryService) {
        this.cVendorBusinessCatService = cVendorBusinessCatService;
        this.cVendorBusinessCatQueryService = cVendorBusinessCatQueryService;
    }

    /**
     * {@code POST  /c-vendor-business-cats} : Create a new cVendorBusinessCat.
     *
     * @param cVendorBusinessCatDTO the cVendorBusinessCatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorBusinessCatDTO, or with status {@code 400 (Bad Request)} if the cVendorBusinessCat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-business-cats")
    public ResponseEntity<CVendorBusinessCatDTO> createCVendorBusinessCat(@Valid @RequestBody CVendorBusinessCatDTO cVendorBusinessCatDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorBusinessCat : {}", cVendorBusinessCatDTO);
        if (cVendorBusinessCatDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorBusinessCat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorBusinessCatDTO result = cVendorBusinessCatService.save(cVendorBusinessCatDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-business-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-business-cats} : Updates an existing cVendorBusinessCat.
     *
     * @param cVendorBusinessCatDTO the cVendorBusinessCatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorBusinessCatDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorBusinessCatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorBusinessCatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-business-cats")
    public ResponseEntity<CVendorBusinessCatDTO> updateCVendorBusinessCat(@Valid @RequestBody CVendorBusinessCatDTO cVendorBusinessCatDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorBusinessCat : {}", cVendorBusinessCatDTO);
        if (cVendorBusinessCatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorBusinessCatDTO result = cVendorBusinessCatService.save(cVendorBusinessCatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorBusinessCatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-business-cats} : get all the cVendorBusinessCats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorBusinessCats in body.
     */
    @GetMapping("/c-vendor-business-cats")
    public ResponseEntity<List<CVendorBusinessCatDTO>> getAllCVendorBusinessCats(CVendorBusinessCatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorBusinessCats by criteria: {}", criteria);
        Page<CVendorBusinessCatDTO> page = cVendorBusinessCatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-business-cats/count} : count all the cVendorBusinessCats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-business-cats/count")
    public ResponseEntity<Long> countCVendorBusinessCats(CVendorBusinessCatCriteria criteria) {
        log.debug("REST request to count CVendorBusinessCats by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorBusinessCatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-business-cats/:id} : get the "id" cVendorBusinessCat.
     *
     * @param id the id of the cVendorBusinessCatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorBusinessCatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-business-cats/{id}")
    public ResponseEntity<CVendorBusinessCatDTO> getCVendorBusinessCat(@PathVariable Long id) {
        log.debug("REST request to get CVendorBusinessCat : {}", id);
        Optional<CVendorBusinessCatDTO> cVendorBusinessCatDTO = cVendorBusinessCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorBusinessCatDTO);
    }

    /**
     * {@code DELETE  /c-vendor-business-cats/:id} : delete the "id" cVendorBusinessCat.
     *
     * @param id the id of the cVendorBusinessCatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-business-cats/{id}")
    public ResponseEntity<Void> deleteCVendorBusinessCat(@PathVariable Long id) {
        log.debug("REST request to delete CVendorBusinessCat : {}", id);
        cVendorBusinessCatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
