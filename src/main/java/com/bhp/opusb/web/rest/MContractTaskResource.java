package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractTaskService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractTaskDTO;
import com.bhp.opusb.service.dto.MContractTaskCriteria;
import com.bhp.opusb.service.MContractTaskQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractTask}.
 */
@RestController
@RequestMapping("/api")
public class MContractTaskResource {

    private final Logger log = LoggerFactory.getLogger(MContractTaskResource.class);

    private static final String ENTITY_NAME = "mContractTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractTaskService mContractTaskService;

    private final MContractTaskQueryService mContractTaskQueryService;

    public MContractTaskResource(MContractTaskService mContractTaskService, MContractTaskQueryService mContractTaskQueryService) {
        this.mContractTaskService = mContractTaskService;
        this.mContractTaskQueryService = mContractTaskQueryService;
    }

    /**
     * {@code POST  /m-contract-tasks} : Create a new mContractTask.
     *
     * @param mContractTaskDTO the mContractTaskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractTaskDTO, or with status {@code 400 (Bad Request)} if the mContractTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-tasks")
    public ResponseEntity<MContractTaskDTO> createMContractTask(@Valid @RequestBody MContractTaskDTO mContractTaskDTO) throws URISyntaxException {
        log.debug("REST request to save MContractTask : {}", mContractTaskDTO);
        if (mContractTaskDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractTaskDTO result = mContractTaskService.save(mContractTaskDTO);
        return ResponseEntity.created(new URI("/api/m-contract-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-tasks} : Updates an existing mContractTask.
     *
     * @param mContractTaskDTO the mContractTaskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractTaskDTO,
     * or with status {@code 400 (Bad Request)} if the mContractTaskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractTaskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-tasks")
    public ResponseEntity<MContractTaskDTO> updateMContractTask(@Valid @RequestBody MContractTaskDTO mContractTaskDTO) throws URISyntaxException {
        log.debug("REST request to update MContractTask : {}", mContractTaskDTO);
        if (mContractTaskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractTaskDTO result = mContractTaskService.save(mContractTaskDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractTaskDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-tasks} : get all the mContractTasks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractTasks in body.
     */
    @GetMapping("/m-contract-tasks")
    public ResponseEntity<List<MContractTaskDTO>> getAllMContractTasks(MContractTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractTasks by criteria: {}", criteria);
        Page<MContractTaskDTO> page = mContractTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-tasks/count} : count all the mContractTasks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-tasks/count")
    public ResponseEntity<Long> countMContractTasks(MContractTaskCriteria criteria) {
        log.debug("REST request to count MContractTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractTaskQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-tasks/:id} : get the "id" mContractTask.
     *
     * @param id the id of the mContractTaskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractTaskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-tasks/{id}")
    public ResponseEntity<MContractTaskDTO> getMContractTask(@PathVariable Long id) {
        log.debug("REST request to get MContractTask : {}", id);
        Optional<MContractTaskDTO> mContractTaskDTO = mContractTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractTaskDTO);
    }

    /**
     * {@code DELETE  /m-contract-tasks/:id} : delete the "id" mContractTask.
     *
     * @param id the id of the mContractTaskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-tasks/{id}")
    public ResponseEntity<Void> deleteMContractTask(@PathVariable Long id) {
        log.debug("REST request to delete MContractTask : {}", id);
        mContractTaskService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
