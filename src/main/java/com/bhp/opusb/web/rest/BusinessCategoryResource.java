package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.BusinessCategoryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.BusinessCategoryDTO;
import com.bhp.opusb.service.dto.BusinessCategoryCriteria;
import com.bhp.opusb.service.BusinessCategoryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.BusinessCategory}.
 */
@RestController
@RequestMapping("/api")
public class BusinessCategoryResource {

    private final Logger log = LoggerFactory.getLogger(BusinessCategoryResource.class);

    private static final String ENTITY_NAME = "businessCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessCategoryService businessCategoryService;

    private final BusinessCategoryQueryService businessCategoryQueryService;

    public BusinessCategoryResource(BusinessCategoryService businessCategoryService, BusinessCategoryQueryService businessCategoryQueryService) {
        this.businessCategoryService = businessCategoryService;
        this.businessCategoryQueryService = businessCategoryQueryService;
    }

    /**
     * {@code POST  /business-categories} : Create a new businessCategory.
     *
     * @param businessCategoryDTO the businessCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessCategoryDTO, or with status {@code 400 (Bad Request)} if the businessCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-categories")
    public ResponseEntity<BusinessCategoryDTO> createBusinessCategory(@Valid @RequestBody BusinessCategoryDTO businessCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessCategory : {}", businessCategoryDTO);
        if (businessCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessCategoryDTO result = businessCategoryService.save(businessCategoryDTO);
        return ResponseEntity.created(new URI("/api/business-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-categories} : Updates an existing businessCategory.
     *
     * @param businessCategoryDTO the businessCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the businessCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-categories")
    public ResponseEntity<BusinessCategoryDTO> updateBusinessCategory(@Valid @RequestBody BusinessCategoryDTO businessCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessCategory : {}", businessCategoryDTO);
        if (businessCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessCategoryDTO result = businessCategoryService.save(businessCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-categories} : get all the businessCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessCategories in body.
     */
    @GetMapping("/business-categories")
    public ResponseEntity<List<BusinessCategoryDTO>> getAllBusinessCategories(BusinessCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BusinessCategories by criteria: {}", criteria);
        Page<BusinessCategoryDTO> page = businessCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /business-categories/count} : count all the businessCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/business-categories/count")
    public ResponseEntity<Long> countBusinessCategories(BusinessCategoryCriteria criteria) {
        log.debug("REST request to count BusinessCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /business-categories/:id} : get the "id" businessCategory.
     *
     * @param id the id of the businessCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-categories/{id}")
    public ResponseEntity<BusinessCategoryDTO> getBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to get BusinessCategory : {}", id);
        Optional<BusinessCategoryDTO> businessCategoryDTO = businessCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessCategoryDTO);
    }

    /**
     * {@code DELETE  /business-categories/:id} : delete the "id" businessCategory.
     *
     * @param id the id of the businessCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-categories/{id}")
    public ResponseEntity<Void> deleteBusinessCategory(@PathVariable Long id) {
        log.debug("REST request to delete BusinessCategory : {}", id);
        businessCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
