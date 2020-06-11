package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADFieldGroupQueryService;
import com.bhp.opusb.service.ADFieldGroupService;
import com.bhp.opusb.service.dto.ADFieldGroupCriteria;
import com.bhp.opusb.service.dto.ADFieldGroupDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADFieldGroup}.
 */
@RestController
@RequestMapping("/api")
public class ADFieldGroupResource {

    private final Logger log = LoggerFactory.getLogger(ADFieldGroupResource.class);

    private static final String ENTITY_NAME = "aDFieldGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADFieldGroupService aDFieldGroupService;

    private final ADFieldGroupQueryService aDFieldGroupQueryService;

    public ADFieldGroupResource(ADFieldGroupService aDFieldGroupService, ADFieldGroupQueryService aDFieldGroupQueryService) {
        this.aDFieldGroupService = aDFieldGroupService;
        this.aDFieldGroupQueryService = aDFieldGroupQueryService;
    }

    /**
     * {@code POST  /ad-field-groups} : Create a new aDFieldGroup.
     *
     * @param aDFieldGroupDTO the aDFieldGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDFieldGroupDTO, or with status {@code 400 (Bad Request)} if the aDFieldGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-field-groups")
    public ResponseEntity<ADFieldGroupDTO> createADFieldGroup(@Valid @RequestBody ADFieldGroupDTO aDFieldGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ADFieldGroup : {}", aDFieldGroupDTO);
        if (aDFieldGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDFieldGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADFieldGroupDTO result = aDFieldGroupService.save(aDFieldGroupDTO);
        return ResponseEntity.created(new URI("/api/ad-field-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-field-groups} : Updates an existing aDFieldGroup.
     *
     * @param aDFieldGroupDTO the aDFieldGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDFieldGroupDTO,
     * or with status {@code 400 (Bad Request)} if the aDFieldGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDFieldGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-field-groups")
    public ResponseEntity<ADFieldGroupDTO> updateADFieldGroup(@Valid @RequestBody ADFieldGroupDTO aDFieldGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ADFieldGroup : {}", aDFieldGroupDTO);
        if (aDFieldGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADFieldGroupDTO result = aDFieldGroupService.save(aDFieldGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDFieldGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-field-groups} : get all the aDFieldGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDFieldGroups in body.
     */
    @GetMapping("/ad-field-groups")
    public ResponseEntity<List<ADFieldGroupDTO>> getAllADFieldGroups(ADFieldGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADFieldGroups by criteria: {}", criteria);
        Page<ADFieldGroupDTO> page = aDFieldGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-field-groups/count} : count all the aDFieldGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-field-groups/count")
    public ResponseEntity<Long> countADFieldGroups(ADFieldGroupCriteria criteria) {
        log.debug("REST request to count ADFieldGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDFieldGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-field-groups/:id} : get the "id" aDFieldGroup.
     *
     * @param id the id of the aDFieldGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDFieldGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-field-groups/{id}")
    public ResponseEntity<ADFieldGroupDTO> getADFieldGroup(@PathVariable Long id) {
        log.debug("REST request to get ADFieldGroup : {}", id);
        Optional<ADFieldGroupDTO> aDFieldGroupDTO = aDFieldGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDFieldGroupDTO);
    }

    /**
     * {@code DELETE  /ad-field-groups/:id} : delete the "id" aDFieldGroup.
     *
     * @param id the id of the aDFieldGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-field-groups/{id}")
    public ResponseEntity<Void> deleteADFieldGroup(@PathVariable Long id) {
        log.debug("REST request to delete ADFieldGroup : {}", id);
        aDFieldGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
