package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CQuestionCategoryQueryService;
import com.bhp.opusb.service.CQuestionCategoryService;
import com.bhp.opusb.service.dto.CQuestionCategoryCriteria;
import com.bhp.opusb.service.dto.CQuestionCategoryDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.CQuestionCategory}.
 */
@RestController
@RequestMapping("/api")
public class CQuestionCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CQuestionCategoryResource.class);

    private static final String ENTITY_NAME = "cQuestionCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CQuestionCategoryService cQuestionCategoryService;

    private final CQuestionCategoryQueryService cQuestionCategoryQueryService;

    public CQuestionCategoryResource(CQuestionCategoryService cQuestionCategoryService, CQuestionCategoryQueryService cQuestionCategoryQueryService) {
        this.cQuestionCategoryService = cQuestionCategoryService;
        this.cQuestionCategoryQueryService = cQuestionCategoryQueryService;
    }

    /**
     * {@code POST  /c-question-categories} : Create a new cQuestionCategory.
     *
     * @param cQuestionCategoryDTO the cQuestionCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cQuestionCategoryDTO, or with status {@code 400 (Bad Request)} if the cQuestionCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-question-categories")
    public ResponseEntity<CQuestionCategoryDTO> createCQuestionCategory(@Valid @RequestBody CQuestionCategoryDTO cQuestionCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save CQuestionCategory : {}", cQuestionCategoryDTO);
        if (cQuestionCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cQuestionCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CQuestionCategoryDTO result = cQuestionCategoryService.save(cQuestionCategoryDTO);
        return ResponseEntity.created(new URI("/api/c-question-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-question-categories} : Updates an existing cQuestionCategory.
     *
     * @param cQuestionCategoryDTO the cQuestionCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cQuestionCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the cQuestionCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cQuestionCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-question-categories")
    public ResponseEntity<CQuestionCategoryDTO> updateCQuestionCategory(@Valid @RequestBody CQuestionCategoryDTO cQuestionCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update CQuestionCategory : {}", cQuestionCategoryDTO);
        if (cQuestionCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CQuestionCategoryDTO result = cQuestionCategoryService.save(cQuestionCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cQuestionCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-question-categories} : get all the cQuestionCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cQuestionCategories in body.
     */
    @GetMapping("/c-question-categories")
    public ResponseEntity<List<CQuestionCategoryDTO>> getAllCQuestionCategories(CQuestionCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CQuestionCategories by criteria: {}", criteria);
        Page<CQuestionCategoryDTO> page = cQuestionCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-question-categories/count} : count all the cQuestionCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-question-categories/count")
    public ResponseEntity<Long> countCQuestionCategories(CQuestionCategoryCriteria criteria) {
        log.debug("REST request to count CQuestionCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(cQuestionCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-question-categories/:id} : get the "id" cQuestionCategory.
     *
     * @param id the id of the cQuestionCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cQuestionCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-question-categories/{id}")
    public ResponseEntity<CQuestionCategoryDTO> getCQuestionCategory(@PathVariable Long id) {
        log.debug("REST request to get CQuestionCategory : {}", id);
        Optional<CQuestionCategoryDTO> cQuestionCategoryDTO = cQuestionCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cQuestionCategoryDTO);
    }

    /**
     * {@code DELETE  /c-question-categories/:id} : delete the "id" cQuestionCategory.
     *
     * @param id the id of the cQuestionCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-question-categories/{id}")
    public ResponseEntity<Void> deleteCQuestionCategory(@PathVariable Long id) {
        log.debug("REST request to delete CQuestionCategory : {}", id);
        cQuestionCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
