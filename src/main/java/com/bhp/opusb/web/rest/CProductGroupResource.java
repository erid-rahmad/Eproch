package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CProductGroupService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CProductGroupDTO;
import com.bhp.opusb.service.dto.CProductGroupCriteria;
import com.bhp.opusb.service.CProductGroupQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CProductGroup}.
 */
@RestController
@RequestMapping("/api")
public class CProductGroupResource {

    private final Logger log = LoggerFactory.getLogger(CProductGroupResource.class);

    private static final String ENTITY_NAME = "cProductGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CProductGroupService cProductGroupService;

    private final CProductGroupQueryService cProductGroupQueryService;

    public CProductGroupResource(CProductGroupService cProductGroupService, CProductGroupQueryService cProductGroupQueryService) {
        this.cProductGroupService = cProductGroupService;
        this.cProductGroupQueryService = cProductGroupQueryService;
    }

    /**
     * {@code POST  /c-product-groups} : Create a new cProductGroup.
     *
     * @param cProductGroupDTO the cProductGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductGroupDTO, or with status {@code 400 (Bad Request)} if the cProductGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-product-groups")
    public ResponseEntity<CProductGroupDTO> createCProductGroup(@Valid @RequestBody CProductGroupDTO cProductGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CProductGroup : {}", cProductGroupDTO);
        if (cProductGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new cProductGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CProductGroupDTO result = cProductGroupService.save(cProductGroupDTO);
        return ResponseEntity.created(new URI("/api/c-product-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-product-groups} : Updates an existing cProductGroup.
     *
     * @param cProductGroupDTO the cProductGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cProductGroupDTO,
     * or with status {@code 400 (Bad Request)} if the cProductGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cProductGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-product-groups")
    public ResponseEntity<CProductGroupDTO> updateCProductGroup(@Valid @RequestBody CProductGroupDTO cProductGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CProductGroup : {}", cProductGroupDTO);
        if (cProductGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CProductGroupDTO result = cProductGroupService.save(cProductGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cProductGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-product-groups} : get all the cProductGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cProductGroups in body.
     */
    @GetMapping("/c-product-groups")
    public ResponseEntity<List<CProductGroupDTO>> getAllCProductGroups(CProductGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CProductGroups by criteria: {}", criteria);
        Page<CProductGroupDTO> page = cProductGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-product-groups/count} : count all the cProductGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-product-groups/count")
    public ResponseEntity<Long> countCProductGroups(CProductGroupCriteria criteria) {
        log.debug("REST request to count CProductGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(cProductGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-product-groups/:id} : get the "id" cProductGroup.
     *
     * @param id the id of the cProductGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cProductGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-product-groups/{id}")
    public ResponseEntity<CProductGroupDTO> getCProductGroup(@PathVariable Long id) {
        log.debug("REST request to get CProductGroup : {}", id);
        Optional<CProductGroupDTO> cProductGroupDTO = cProductGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cProductGroupDTO);
    }

    /**
     * {@code DELETE  /c-product-groups/:id} : delete the "id" cProductGroup.
     *
     * @param id the id of the cProductGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-product-groups/{id}")
    public ResponseEntity<Void> deleteCProductGroup(@PathVariable Long id) {
        log.debug("REST request to delete CProductGroup : {}", id);
        cProductGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
