package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CVendorGroupService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CVendorGroupDTO;
import com.bhp.opusb.service.dto.CVendorGroupCriteria;
import com.bhp.opusb.service.CVendorGroupQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CVendorGroup}.
 */
@RestController
@RequestMapping("/api")
public class CVendorGroupResource {

    private final Logger log = LoggerFactory.getLogger(CVendorGroupResource.class);

    private static final String ENTITY_NAME = "cVendorGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CVendorGroupService cVendorGroupService;

    private final CVendorGroupQueryService cVendorGroupQueryService;

    public CVendorGroupResource(CVendorGroupService cVendorGroupService, CVendorGroupQueryService cVendorGroupQueryService) {
        this.cVendorGroupService = cVendorGroupService;
        this.cVendorGroupQueryService = cVendorGroupQueryService;
    }

    /**
     * {@code POST  /c-vendor-groups} : Create a new cVendorGroup.
     *
     * @param cVendorGroupDTO the cVendorGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cVendorGroupDTO, or with status {@code 400 (Bad Request)} if the cVendorGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-vendor-groups")
    public ResponseEntity<CVendorGroupDTO> createCVendorGroup(@Valid @RequestBody CVendorGroupDTO cVendorGroupDTO) throws URISyntaxException {
        log.debug("REST request to save CVendorGroup : {}", cVendorGroupDTO);
        if (cVendorGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new cVendorGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CVendorGroupDTO result = cVendorGroupService.save(cVendorGroupDTO);
        return ResponseEntity.created(new URI("/api/c-vendor-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-vendor-groups} : Updates an existing cVendorGroup.
     *
     * @param cVendorGroupDTO the cVendorGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cVendorGroupDTO,
     * or with status {@code 400 (Bad Request)} if the cVendorGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cVendorGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-vendor-groups")
    public ResponseEntity<CVendorGroupDTO> updateCVendorGroup(@Valid @RequestBody CVendorGroupDTO cVendorGroupDTO) throws URISyntaxException {
        log.debug("REST request to update CVendorGroup : {}", cVendorGroupDTO);
        if (cVendorGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CVendorGroupDTO result = cVendorGroupService.save(cVendorGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cVendorGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-vendor-groups} : get all the cVendorGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cVendorGroups in body.
     */
    @GetMapping("/c-vendor-groups")
    public ResponseEntity<List<CVendorGroupDTO>> getAllCVendorGroups(CVendorGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CVendorGroups by criteria: {}", criteria);
        Page<CVendorGroupDTO> page = cVendorGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-vendor-groups/count} : count all the cVendorGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-vendor-groups/count")
    public ResponseEntity<Long> countCVendorGroups(CVendorGroupCriteria criteria) {
        log.debug("REST request to count CVendorGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(cVendorGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-vendor-groups/:id} : get the "id" cVendorGroup.
     *
     * @param id the id of the cVendorGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cVendorGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-vendor-groups/{id}")
    public ResponseEntity<CVendorGroupDTO> getCVendorGroup(@PathVariable Long id) {
        log.debug("REST request to get CVendorGroup : {}", id);
        Optional<CVendorGroupDTO> cVendorGroupDTO = cVendorGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cVendorGroupDTO);
    }

    /**
     * {@code DELETE  /c-vendor-groups/:id} : delete the "id" cVendorGroup.
     *
     * @param id the id of the cVendorGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-vendor-groups/{id}")
    public ResponseEntity<Void> deleteCVendorGroup(@PathVariable Long id) {
        log.debug("REST request to delete CVendorGroup : {}", id);
        cVendorGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
