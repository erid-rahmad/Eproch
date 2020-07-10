package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CRegDocTypeBusinessCategoryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryDTO;
import com.bhp.opusb.service.dto.CRegDocTypeBusinessCategoryCriteria;
import com.bhp.opusb.service.CRegDocTypeBusinessCategoryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CRegDocTypeBusinessCategory}.
 */
@RestController
@RequestMapping("/api")
public class CRegDocTypeBusinessCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CRegDocTypeBusinessCategoryResource.class);

    private static final String ENTITY_NAME = "cRegDocTypeBusinessCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRegDocTypeBusinessCategoryService cRegDocTypeBusinessCategoryService;

    private final CRegDocTypeBusinessCategoryQueryService cRegDocTypeBusinessCategoryQueryService;

    public CRegDocTypeBusinessCategoryResource(CRegDocTypeBusinessCategoryService cRegDocTypeBusinessCategoryService, CRegDocTypeBusinessCategoryQueryService cRegDocTypeBusinessCategoryQueryService) {
        this.cRegDocTypeBusinessCategoryService = cRegDocTypeBusinessCategoryService;
        this.cRegDocTypeBusinessCategoryQueryService = cRegDocTypeBusinessCategoryQueryService;
    }

    /**
     * {@code POST  /c-reg-doc-type-business-categories} : Create a new cRegDocTypeBusinessCategory.
     *
     * @param cRegDocTypeBusinessCategoryDTO the cRegDocTypeBusinessCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cRegDocTypeBusinessCategoryDTO, or with status {@code 400 (Bad Request)} if the cRegDocTypeBusinessCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-reg-doc-type-business-categories")
    public ResponseEntity<CRegDocTypeBusinessCategoryDTO> createCRegDocTypeBusinessCategory(@Valid @RequestBody CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save CRegDocTypeBusinessCategory : {}", cRegDocTypeBusinessCategoryDTO);
        if (cRegDocTypeBusinessCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cRegDocTypeBusinessCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CRegDocTypeBusinessCategoryDTO result = cRegDocTypeBusinessCategoryService.save(cRegDocTypeBusinessCategoryDTO);
        return ResponseEntity.created(new URI("/api/c-reg-doc-type-business-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-reg-doc-type-business-categories} : Updates an existing cRegDocTypeBusinessCategory.
     *
     * @param cRegDocTypeBusinessCategoryDTO the cRegDocTypeBusinessCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cRegDocTypeBusinessCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the cRegDocTypeBusinessCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cRegDocTypeBusinessCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-reg-doc-type-business-categories")
    public ResponseEntity<CRegDocTypeBusinessCategoryDTO> updateCRegDocTypeBusinessCategory(@Valid @RequestBody CRegDocTypeBusinessCategoryDTO cRegDocTypeBusinessCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update CRegDocTypeBusinessCategory : {}", cRegDocTypeBusinessCategoryDTO);
        if (cRegDocTypeBusinessCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CRegDocTypeBusinessCategoryDTO result = cRegDocTypeBusinessCategoryService.save(cRegDocTypeBusinessCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cRegDocTypeBusinessCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-reg-doc-type-business-categories} : get all the cRegDocTypeBusinessCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cRegDocTypeBusinessCategories in body.
     */
    @GetMapping("/c-reg-doc-type-business-categories")
    public ResponseEntity<List<CRegDocTypeBusinessCategoryDTO>> getAllCRegDocTypeBusinessCategories(CRegDocTypeBusinessCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CRegDocTypeBusinessCategories by criteria: {}", criteria);
        Page<CRegDocTypeBusinessCategoryDTO> page = cRegDocTypeBusinessCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-reg-doc-type-business-categories/count} : count all the cRegDocTypeBusinessCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-reg-doc-type-business-categories/count")
    public ResponseEntity<Long> countCRegDocTypeBusinessCategories(CRegDocTypeBusinessCategoryCriteria criteria) {
        log.debug("REST request to count CRegDocTypeBusinessCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(cRegDocTypeBusinessCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-reg-doc-type-business-categories/:id} : get the "id" cRegDocTypeBusinessCategory.
     *
     * @param id the id of the cRegDocTypeBusinessCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cRegDocTypeBusinessCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-reg-doc-type-business-categories/{id}")
    public ResponseEntity<CRegDocTypeBusinessCategoryDTO> getCRegDocTypeBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to get CRegDocTypeBusinessCategory : {}", id);
        Optional<CRegDocTypeBusinessCategoryDTO> cRegDocTypeBusinessCategoryDTO = cRegDocTypeBusinessCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cRegDocTypeBusinessCategoryDTO);
    }

    /**
     * {@code DELETE  /c-reg-doc-type-business-categories/:id} : delete the "id" cRegDocTypeBusinessCategory.
     *
     * @param id the id of the cRegDocTypeBusinessCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-reg-doc-type-business-categories/{id}")
    public ResponseEntity<Void> deleteCRegDocTypeBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to delete CRegDocTypeBusinessCategory : {}", id);
        cRegDocTypeBusinessCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
