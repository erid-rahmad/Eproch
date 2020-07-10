package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdTaskService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdTaskDTO;
import com.bhp.opusb.service.dto.AdTaskCriteria;
import com.bhp.opusb.service.AdTaskQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTask}.
 */
@RestController
@RequestMapping("/api")
public class AdTaskResource {

    private final Logger log = LoggerFactory.getLogger(AdTaskResource.class);

    private static final String ENTITY_NAME = "adTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTaskService adTaskService;

    private final AdTaskQueryService adTaskQueryService;

    public AdTaskResource(AdTaskService adTaskService, AdTaskQueryService adTaskQueryService) {
        this.adTaskService = adTaskService;
        this.adTaskQueryService = adTaskQueryService;
    }

    /**
     * {@code POST  /ad-tasks} : Create a new adTask.
     *
     * @param adTaskDTO the adTaskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTaskDTO, or with status {@code 400 (Bad Request)} if the adTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-tasks")
    public ResponseEntity<AdTaskDTO> createAdTask(@Valid @RequestBody AdTaskDTO adTaskDTO) throws URISyntaxException {
        log.debug("REST request to save AdTask : {}", adTaskDTO);
        if (adTaskDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTaskDTO result = adTaskService.save(adTaskDTO);
        return ResponseEntity.created(new URI("/api/ad-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-tasks} : Updates an existing adTask.
     *
     * @param adTaskDTO the adTaskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTaskDTO,
     * or with status {@code 400 (Bad Request)} if the adTaskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTaskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-tasks")
    public ResponseEntity<AdTaskDTO> updateAdTask(@Valid @RequestBody AdTaskDTO adTaskDTO) throws URISyntaxException {
        log.debug("REST request to update AdTask : {}", adTaskDTO);
        if (adTaskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTaskDTO result = adTaskService.save(adTaskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTaskDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-tasks} : get all the adTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTasks in body.
     */
    @GetMapping("/ad-tasks")
    public ResponseEntity<List<AdTaskDTO>> getAllAdTasks(AdTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTasks by criteria: {}", criteria);
        Page<AdTaskDTO> page = adTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-tasks/count} : count all the adTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-tasks/count")
    public ResponseEntity<Long> countAdTasks(AdTaskCriteria criteria) {
        log.debug("REST request to count AdTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTaskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-tasks/:id} : get the "id" adTask.
     *
     * @param id the id of the adTaskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTaskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-tasks/{id}")
    public ResponseEntity<AdTaskDTO> getAdTask(@PathVariable Long id) {
        log.debug("REST request to get AdTask : {}", id);
        Optional<AdTaskDTO> adTaskDTO = adTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTaskDTO);
    }

    /**
     * {@code DELETE  /ad-tasks/:id} : delete the "id" adTask.
     *
     * @param id the id of the adTaskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-tasks/{id}")
    public ResponseEntity<Void> deleteAdTask(@PathVariable Long id) {
        log.debug("REST request to delete AdTask : {}", id);
        adTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
