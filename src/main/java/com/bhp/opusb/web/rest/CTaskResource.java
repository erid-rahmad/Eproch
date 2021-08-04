package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CTaskService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CTaskDTO;
import com.bhp.opusb.service.dto.CTaskCriteria;
import com.bhp.opusb.service.CTaskQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CTask}.
 */
@RestController
@RequestMapping("/api")
public class CTaskResource {

    private final Logger log = LoggerFactory.getLogger(CTaskResource.class);

    private static final String ENTITY_NAME = "cTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CTaskService cTaskService;

    private final CTaskQueryService cTaskQueryService;

    public CTaskResource(CTaskService cTaskService, CTaskQueryService cTaskQueryService) {
        this.cTaskService = cTaskService;
        this.cTaskQueryService = cTaskQueryService;
    }

    /**
     * {@code POST  /c-tasks} : Create a new cTask.
     *
     * @param cTaskDTO the cTaskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cTaskDTO, or with status {@code 400 (Bad Request)} if the cTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-tasks")
    public ResponseEntity<CTaskDTO> createCTask(@Valid @RequestBody CTaskDTO cTaskDTO) throws URISyntaxException {
        log.debug("REST request to save CTask : {}", cTaskDTO);
        if (cTaskDTO.getId() != null) {
            throw new BadRequestAlertException("A new cTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CTaskDTO result = cTaskService.save(cTaskDTO);
        return ResponseEntity.created(new URI("/api/c-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-tasks} : Updates an existing cTask.
     *
     * @param cTaskDTO the cTaskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cTaskDTO,
     * or with status {@code 400 (Bad Request)} if the cTaskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cTaskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-tasks")
    public ResponseEntity<CTaskDTO> updateCTask(@Valid @RequestBody CTaskDTO cTaskDTO) throws URISyntaxException {
        log.debug("REST request to update CTask : {}", cTaskDTO);
        if (cTaskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CTaskDTO result = cTaskService.save(cTaskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cTaskDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-tasks} : get all the cTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cTasks in body.
     */
    @GetMapping("/c-tasks")
    public ResponseEntity<List<CTaskDTO>> getAllCTasks(CTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CTasks by criteria: {}", criteria);
        Page<CTaskDTO> page = cTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-tasks/count} : count all the cTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-tasks/count")
    public ResponseEntity<Long> countCTasks(CTaskCriteria criteria) {
        log.debug("REST request to count CTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(cTaskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-tasks/:id} : get the "id" cTask.
     *
     * @param id the id of the cTaskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cTaskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-tasks/{id}")
    public ResponseEntity<CTaskDTO> getCTask(@PathVariable Long id) {
        log.debug("REST request to get CTask : {}", id);
        Optional<CTaskDTO> cTaskDTO = cTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cTaskDTO);
    }

    /**
     * {@code DELETE  /c-tasks/:id} : delete the "id" cTask.
     *
     * @param id the id of the cTaskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-tasks/{id}")
    public ResponseEntity<Void> deleteCTask(@PathVariable Long id) {
        log.debug("REST request to delete CTask : {}", id);
        cTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
