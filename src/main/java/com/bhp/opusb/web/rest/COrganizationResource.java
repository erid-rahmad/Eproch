package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.COrganizationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.COrganizationDTO;
import com.bhp.opusb.service.dto.COrganizationCriteria;
import com.bhp.opusb.service.COrganizationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.COrganization}.
 */
@RestController
@RequestMapping("/api")
public class COrganizationResource {

    private final Logger log = LoggerFactory.getLogger(COrganizationResource.class);

    private static final String ENTITY_NAME = "cOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final COrganizationService cOrganizationService;

    private final COrganizationQueryService cOrganizationQueryService;

    public COrganizationResource(COrganizationService cOrganizationService, COrganizationQueryService cOrganizationQueryService) {
        this.cOrganizationService = cOrganizationService;
        this.cOrganizationQueryService = cOrganizationQueryService;
    }

    /**
     * {@code POST  /c-organizations} : Create a new cOrganization.
     *
     * @param cOrganizationDTO the cOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cOrganizationDTO, or with status {@code 400 (Bad Request)} if the cOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-organizations")
    public ResponseEntity<COrganizationDTO> createCOrganization(@Valid @RequestBody COrganizationDTO cOrganizationDTO) throws URISyntaxException {
        log.debug("REST request to save COrganization : {}", cOrganizationDTO);
        if (cOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new cOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        COrganizationDTO result = cOrganizationService.save(cOrganizationDTO);
        return ResponseEntity.created(new URI("/api/c-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-organizations} : Updates an existing cOrganization.
     *
     * @param cOrganizationDTO the cOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the cOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-organizations")
    public ResponseEntity<COrganizationDTO> updateCOrganization(@Valid @RequestBody COrganizationDTO cOrganizationDTO) throws URISyntaxException {
        log.debug("REST request to update COrganization : {}", cOrganizationDTO);
        if (cOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        COrganizationDTO result = cOrganizationService.save(cOrganizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-organizations} : get all the cOrganizations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cOrganizations in body.
     */
    @GetMapping("/c-organizations")
    public ResponseEntity<List<COrganizationDTO>> getAllCOrganizations(COrganizationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get COrganizations by criteria: {}", criteria);
        Page<COrganizationDTO> page = cOrganizationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-organizations/count} : count all the cOrganizations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-organizations/count")
    public ResponseEntity<Long> countCOrganizations(COrganizationCriteria criteria) {
        log.debug("REST request to count COrganizations by criteria: {}", criteria);
        return ResponseEntity.ok().body(cOrganizationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-organizations/:id} : get the "id" cOrganization.
     *
     * @param id the id of the cOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-organizations/{id}")
    public ResponseEntity<COrganizationDTO> getCOrganization(@PathVariable Long id) {
        log.debug("REST request to get COrganization : {}", id);
        Optional<COrganizationDTO> cOrganizationDTO = cOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cOrganizationDTO);
    }

    /**
     * {@code DELETE  /c-organizations/:id} : delete the "id" cOrganization.
     *
     * @param id the id of the cOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-organizations/{id}")
    public ResponseEntity<Void> deleteCOrganization(@PathVariable Long id) {
        log.debug("REST request to delete COrganization : {}", id);
        cOrganizationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
