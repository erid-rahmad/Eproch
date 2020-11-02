package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CTaxCategoryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CTaxCategoryDTO;
import com.bhp.opusb.service.dto.CTaxCategoryCriteria;
import com.bhp.opusb.service.CTaxCategoryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CTaxCategory}.
 */
@RestController
@RequestMapping("/api")
public class CTaxCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CTaxCategoryResource.class);

    private static final String ENTITY_NAME = "cTaxCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CTaxCategoryService cTaxCategoryService;

    private final CTaxCategoryQueryService cTaxCategoryQueryService;

    public CTaxCategoryResource(CTaxCategoryService cTaxCategoryService, CTaxCategoryQueryService cTaxCategoryQueryService) {
        this.cTaxCategoryService = cTaxCategoryService;
        this.cTaxCategoryQueryService = cTaxCategoryQueryService;
    }

    /**
     * {@code POST  /c-tax-categories} : Create a new cTaxCategory.
     *
     * @param cTaxCategoryDTO the cTaxCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cTaxCategoryDTO, or with status {@code 400 (Bad Request)} if the cTaxCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-tax-categories")
    public ResponseEntity<CTaxCategoryDTO> createCTaxCategory(@Valid @RequestBody CTaxCategoryDTO cTaxCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save CTaxCategory : {}", cTaxCategoryDTO);
        if (cTaxCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cTaxCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CTaxCategoryDTO result = cTaxCategoryService.save(cTaxCategoryDTO);
        return ResponseEntity.created(new URI("/api/c-tax-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-tax-categories} : Updates an existing cTaxCategory.
     *
     * @param cTaxCategoryDTO the cTaxCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cTaxCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the cTaxCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cTaxCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-tax-categories")
    public ResponseEntity<CTaxCategoryDTO> updateCTaxCategory(@Valid @RequestBody CTaxCategoryDTO cTaxCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update CTaxCategory : {}", cTaxCategoryDTO);
        if (cTaxCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CTaxCategoryDTO result = cTaxCategoryService.save(cTaxCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cTaxCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-tax-categories} : get all the cTaxCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cTaxCategories in body.
     */
    @GetMapping("/c-tax-categories")
    public ResponseEntity<List<CTaxCategoryDTO>> getAllCTaxCategories(CTaxCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CTaxCategories by criteria: {}", criteria);
        Page<CTaxCategoryDTO> page = cTaxCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-tax-categories/count} : count all the cTaxCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-tax-categories/count")
    public ResponseEntity<Long> countCTaxCategories(CTaxCategoryCriteria criteria) {
        log.debug("REST request to count CTaxCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(cTaxCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-tax-categories/:id} : get the "id" cTaxCategory.
     *
     * @param id the id of the cTaxCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cTaxCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-tax-categories/{id}")
    public ResponseEntity<CTaxCategoryDTO> getCTaxCategory(@PathVariable Long id) {
        log.debug("REST request to get CTaxCategory : {}", id);
        Optional<CTaxCategoryDTO> cTaxCategoryDTO = cTaxCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cTaxCategoryDTO);
    }

    /**
     * {@code DELETE  /c-tax-categories/:id} : delete the "id" cTaxCategory.
     *
     * @param id the id of the cTaxCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-tax-categories/{id}")
    public ResponseEntity<Void> deleteCTaxCategory(@PathVariable Long id) {
        log.debug("REST request to delete CTaxCategory : {}", id);
        cTaxCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
