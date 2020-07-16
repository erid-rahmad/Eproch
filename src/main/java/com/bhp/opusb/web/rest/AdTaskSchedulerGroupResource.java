package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.AdTaskSchedulerGroupQueryService;
import com.bhp.opusb.service.AdTaskSchedulerGroupService;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupCriteria;
import com.bhp.opusb.service.dto.AdTaskSchedulerGroupDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTaskSchedulerGroup}.
 */
@RestController
@RequestMapping("/api")
public class AdTaskSchedulerGroupResource {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerGroupResource.class);

    private static final String ENTITY_NAME = "adTaskSchedulerGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTaskSchedulerGroupService adTaskSchedulerGroupService;

    private final AdTaskSchedulerGroupQueryService adTaskSchedulerGroupQueryService;

    public AdTaskSchedulerGroupResource(AdTaskSchedulerGroupService adTaskSchedulerGroupService, AdTaskSchedulerGroupQueryService adTaskSchedulerGroupQueryService) {
        this.adTaskSchedulerGroupService = adTaskSchedulerGroupService;
        this.adTaskSchedulerGroupQueryService = adTaskSchedulerGroupQueryService;
    }

    /**
     * {@code POST  /ad-task-scheduler-groups} : Create a new adTaskSchedulerGroup.
     *
     * @param adTaskSchedulerGroupDTO the adTaskSchedulerGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTaskSchedulerGroupDTO, or with status {@code 400 (Bad Request)} if the adTaskSchedulerGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-task-scheduler-groups")
    public ResponseEntity<AdTaskSchedulerGroupDTO> createAdTaskSchedulerGroup(@Valid @RequestBody AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO) throws URISyntaxException {
        log.debug("REST request to save AdTaskSchedulerGroup : {}", adTaskSchedulerGroupDTO);
        if (adTaskSchedulerGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTaskSchedulerGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTaskSchedulerGroupDTO result = adTaskSchedulerGroupService.save(adTaskSchedulerGroupDTO);
        return ResponseEntity.created(new URI("/api/ad-task-scheduler-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-task-scheduler-groups} : Updates an existing adTaskSchedulerGroup.
     *
     * @param adTaskSchedulerGroupDTO the adTaskSchedulerGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTaskSchedulerGroupDTO,
     * or with status {@code 400 (Bad Request)} if the adTaskSchedulerGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTaskSchedulerGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-task-scheduler-groups")
    public ResponseEntity<AdTaskSchedulerGroupDTO> updateAdTaskSchedulerGroup(@Valid @RequestBody AdTaskSchedulerGroupDTO adTaskSchedulerGroupDTO) throws URISyntaxException {
        log.debug("REST request to update AdTaskSchedulerGroup : {}", adTaskSchedulerGroupDTO);
        if (adTaskSchedulerGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTaskSchedulerGroupDTO result = adTaskSchedulerGroupService.save(adTaskSchedulerGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTaskSchedulerGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-task-scheduler-groups} : get all the adTaskSchedulerGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTaskSchedulerGroups in body.
     */
    @GetMapping("/ad-task-scheduler-groups")
    public ResponseEntity<List<AdTaskSchedulerGroupDTO>> getAllAdTaskSchedulerGroups(AdTaskSchedulerGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTaskSchedulerGroups by criteria: {}", criteria);
        Page<AdTaskSchedulerGroupDTO> page = adTaskSchedulerGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-task-scheduler-groups/count} : count all the adTaskSchedulerGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-task-scheduler-groups/count")
    public ResponseEntity<Long> countAdTaskSchedulerGroups(AdTaskSchedulerGroupCriteria criteria) {
        log.debug("REST request to count AdTaskSchedulerGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTaskSchedulerGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-task-scheduler-groups/:id} : get the "id" adTaskSchedulerGroup.
     *
     * @param id the id of the adTaskSchedulerGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTaskSchedulerGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-task-scheduler-groups/{id}")
    public ResponseEntity<AdTaskSchedulerGroupDTO> getAdTaskSchedulerGroup(@PathVariable Long id) {
        log.debug("REST request to get AdTaskSchedulerGroup : {}", id);
        Optional<AdTaskSchedulerGroupDTO> adTaskSchedulerGroupDTO = adTaskSchedulerGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTaskSchedulerGroupDTO);
    }

    /**
     * {@code DELETE  /ad-task-scheduler-groups/:id} : delete the "id" adTaskSchedulerGroup.
     *
     * @param id the id of the adTaskSchedulerGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-task-scheduler-groups/{id}")
    public ResponseEntity<Void> deleteAdTaskSchedulerGroup(@PathVariable Long id) {
        log.debug("REST request to delete AdTaskSchedulerGroup : {}", id);
        adTaskSchedulerGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
