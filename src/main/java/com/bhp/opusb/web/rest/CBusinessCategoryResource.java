package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CBusinessCategoryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CBusinessCategoryDTO;
import com.bhp.opusb.service.dto.CBusinessCategoryCriteria;
import com.bhp.opusb.service.CBusinessCategoryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CBusinessCategory}.
 */
@RestController
@RequestMapping("/api")
public class CBusinessCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CBusinessCategoryResource.class);

    private static final String ENTITY_NAME = "cBusinessCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBusinessCategoryService cBusinessCategoryService;

    private final CBusinessCategoryQueryService cBusinessCategoryQueryService;

    public CBusinessCategoryResource(CBusinessCategoryService cBusinessCategoryService, CBusinessCategoryQueryService cBusinessCategoryQueryService) {
        this.cBusinessCategoryService = cBusinessCategoryService;
        this.cBusinessCategoryQueryService = cBusinessCategoryQueryService;
    }

    /**
     * {@code POST  /c-business-categories} : Create a new cBusinessCategory.
     *
     * @param cBusinessCategoryDTO the cBusinessCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBusinessCategoryDTO, or with status {@code 400 (Bad Request)} if the cBusinessCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-business-categories")
    public ResponseEntity<CBusinessCategoryDTO> createCBusinessCategory(@Valid @RequestBody CBusinessCategoryDTO cBusinessCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save CBusinessCategory : {}", cBusinessCategoryDTO);
        if (cBusinessCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBusinessCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBusinessCategoryDTO result = cBusinessCategoryService.save(cBusinessCategoryDTO);
        return ResponseEntity.created(new URI("/api/c-business-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-business-categories} : Updates an existing cBusinessCategory.
     *
     * @param cBusinessCategoryDTO the cBusinessCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBusinessCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the cBusinessCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBusinessCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-business-categories")
    public ResponseEntity<CBusinessCategoryDTO> updateCBusinessCategory(@Valid @RequestBody CBusinessCategoryDTO cBusinessCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update CBusinessCategory : {}", cBusinessCategoryDTO);
        if (cBusinessCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBusinessCategoryDTO result = cBusinessCategoryService.save(cBusinessCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBusinessCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-business-categories} : get all the cBusinessCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBusinessCategories in body.
     */
    @GetMapping("/c-business-categories")
    public ResponseEntity<List<CBusinessCategoryDTO>> getAllCBusinessCategories(CBusinessCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBusinessCategories by criteria: {}", criteria);
        Page<CBusinessCategoryDTO> page = cBusinessCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-business-categories/count} : count all the cBusinessCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-business-categories/count")
    public ResponseEntity<Long> countCBusinessCategories(CBusinessCategoryCriteria criteria) {
        log.debug("REST request to count CBusinessCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBusinessCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-business-categories/:id} : get the "id" cBusinessCategory.
     *
     * @param id the id of the cBusinessCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBusinessCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-business-categories/{id}")
    public ResponseEntity<CBusinessCategoryDTO> getCBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to get CBusinessCategory : {}", id);
        Optional<CBusinessCategoryDTO> cBusinessCategoryDTO = cBusinessCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBusinessCategoryDTO);
    }

    /**
     * {@code DELETE  /c-business-categories/:id} : delete the "id" cBusinessCategory.
     *
     * @param id the id of the cBusinessCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-business-categories/{id}")
    public ResponseEntity<Void> deleteCBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to delete CBusinessCategory : {}", id);
        cBusinessCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
