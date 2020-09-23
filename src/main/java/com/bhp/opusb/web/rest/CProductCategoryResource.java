package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CProductCategoryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CProductCategoryDTO;
import com.bhp.opusb.service.dto.CProductCategoryCriteria;
import com.bhp.opusb.service.CProductCategoryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CProductCategory}.
 */
@RestController
@RequestMapping("/api")
public class CProductCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CProductCategoryResource.class);

    private static final String ENTITY_NAME = "cProductCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CProductCategoryService cProductCategoryService;

    private final CProductCategoryQueryService cProductCategoryQueryService;

    public CProductCategoryResource(CProductCategoryService cProductCategoryService, CProductCategoryQueryService cProductCategoryQueryService) {
        this.cProductCategoryService = cProductCategoryService;
        this.cProductCategoryQueryService = cProductCategoryQueryService;
    }

    /**
     * {@code POST  /c-product-categories} : Create a new cProductCategory.
     *
     * @param cProductCategoryDTO the cProductCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductCategoryDTO, or with status {@code 400 (Bad Request)} if the cProductCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-product-categories")
    public ResponseEntity<CProductCategoryDTO> createCProductCategory(@Valid @RequestBody CProductCategoryDTO cProductCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save CProductCategory : {}", cProductCategoryDTO);
        if (cProductCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cProductCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CProductCategoryDTO result = cProductCategoryService.save(cProductCategoryDTO);
        return ResponseEntity.created(new URI("/api/c-product-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-product-categories} : Updates an existing cProductCategory.
     *
     * @param cProductCategoryDTO the cProductCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cProductCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the cProductCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cProductCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-product-categories")
    public ResponseEntity<CProductCategoryDTO> updateCProductCategory(@Valid @RequestBody CProductCategoryDTO cProductCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update CProductCategory : {}", cProductCategoryDTO);
        if (cProductCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CProductCategoryDTO result = cProductCategoryService.save(cProductCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cProductCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-product-categories} : get all the cProductCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cProductCategories in body.
     */
    @GetMapping("/c-product-categories")
    public ResponseEntity<List<CProductCategoryDTO>> getAllCProductCategories(CProductCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CProductCategories by criteria: {}", criteria);
        Page<CProductCategoryDTO> page = cProductCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-product-categories/count} : count all the cProductCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-product-categories/count")
    public ResponseEntity<Long> countCProductCategories(CProductCategoryCriteria criteria) {
        log.debug("REST request to count CProductCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(cProductCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-product-categories/:id} : get the "id" cProductCategory.
     *
     * @param id the id of the cProductCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cProductCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-product-categories/{id}")
    public ResponseEntity<CProductCategoryDTO> getCProductCategory(@PathVariable Long id) {
        log.debug("REST request to get CProductCategory : {}", id);
        Optional<CProductCategoryDTO> cProductCategoryDTO = cProductCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cProductCategoryDTO);
    }

    /**
     * {@code DELETE  /c-product-categories/:id} : delete the "id" cProductCategory.
     *
     * @param id the id of the cProductCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-product-categories/{id}")
    public ResponseEntity<Void> deleteCProductCategory(@PathVariable Long id) {
        log.debug("REST request to delete CProductCategory : {}", id);
        cProductCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
