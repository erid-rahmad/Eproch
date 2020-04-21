package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ADOrganizationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ADOrganizationDTO;
import com.bhp.opusb.service.dto.ADOrganizationCriteria;
import com.bhp.opusb.service.ADOrganizationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.ADOrganization}.
 */
@RestController
@RequestMapping("/api")
public class ADOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(ADOrganizationResource.class);

    private static final String ENTITY_NAME = "aDOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADOrganizationService aDOrganizationService;

    private final ADOrganizationQueryService aDOrganizationQueryService;

    public ADOrganizationResource(ADOrganizationService aDOrganizationService, ADOrganizationQueryService aDOrganizationQueryService) {
        this.aDOrganizationService = aDOrganizationService;
        this.aDOrganizationQueryService = aDOrganizationQueryService;
    }

    /**
     * {@code POST  /ad-organizations} : Create a new aDOrganization.
     *
     * @param aDOrganizationDTO the aDOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDOrganizationDTO, or with status {@code 400 (Bad Request)} if the aDOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-organizations")
    public ResponseEntity<ADOrganizationDTO> createADOrganization(@Valid @RequestBody ADOrganizationDTO aDOrganizationDTO) throws URISyntaxException {
        log.debug("REST request to save ADOrganization : {}", aDOrganizationDTO);
        if (aDOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADOrganizationDTO result = aDOrganizationService.save(aDOrganizationDTO);
        return ResponseEntity.created(new URI("/api/ad-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-organizations} : Updates an existing aDOrganization.
     *
     * @param aDOrganizationDTO the aDOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the aDOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-organizations")
    public ResponseEntity<ADOrganizationDTO> updateADOrganization(@Valid @RequestBody ADOrganizationDTO aDOrganizationDTO) throws URISyntaxException {
        log.debug("REST request to update ADOrganization : {}", aDOrganizationDTO);
        if (aDOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADOrganizationDTO result = aDOrganizationService.save(aDOrganizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-organizations} : get all the aDOrganizations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDOrganizations in body.
     */
    @GetMapping("/ad-organizations")
    public ResponseEntity<List<ADOrganizationDTO>> getAllADOrganizations(ADOrganizationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADOrganizations by criteria: {}", criteria);
        Page<ADOrganizationDTO> page = aDOrganizationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-organizations/count} : count all the aDOrganizations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-organizations/count")
    public ResponseEntity<Long> countADOrganizations(ADOrganizationCriteria criteria) {
        log.debug("REST request to count ADOrganizations by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDOrganizationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-organizations/:id} : get the "id" aDOrganization.
     *
     * @param id the id of the aDOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-organizations/{id}")
    public ResponseEntity<ADOrganizationDTO> getADOrganization(@PathVariable Long id) {
        log.debug("REST request to get ADOrganization : {}", id);
        Optional<ADOrganizationDTO> aDOrganizationDTO = aDOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDOrganizationDTO);
    }

    /**
     * {@code DELETE  /ad-organizations/:id} : delete the "id" aDOrganization.
     *
     * @param id the id of the aDOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-organizations/{id}")
    public ResponseEntity<Void> deleteADOrganization(@PathVariable Long id) {
        log.debug("REST request to delete ADOrganization : {}", id);
        aDOrganizationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
