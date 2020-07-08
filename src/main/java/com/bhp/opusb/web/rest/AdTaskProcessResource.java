package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdTaskProcessService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdTaskProcessDTO;
import com.bhp.opusb.service.dto.AdTaskProcessCriteria;
import com.bhp.opusb.service.AdTaskProcessQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTaskProcess}.
 */
@RestController
@RequestMapping("/api")
public class AdTaskProcessResource {

    private final Logger log = LoggerFactory.getLogger(AdTaskProcessResource.class);

    private static final String ENTITY_NAME = "adTaskProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTaskProcessService adTaskProcessService;

    private final AdTaskProcessQueryService adTaskProcessQueryService;

    public AdTaskProcessResource(AdTaskProcessService adTaskProcessService, AdTaskProcessQueryService adTaskProcessQueryService) {
        this.adTaskProcessService = adTaskProcessService;
        this.adTaskProcessQueryService = adTaskProcessQueryService;
    }

    /**
     * {@code POST  /ad-task-processes} : Create a new adTaskProcess.
     *
     * @param adTaskProcessDTO the adTaskProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTaskProcessDTO, or with status {@code 400 (Bad Request)} if the adTaskProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-task-processes")
    public ResponseEntity<AdTaskProcessDTO> createAdTaskProcess(@Valid @RequestBody AdTaskProcessDTO adTaskProcessDTO) throws URISyntaxException {
        log.debug("REST request to save AdTaskProcess : {}", adTaskProcessDTO);
        if (adTaskProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTaskProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTaskProcessDTO result = adTaskProcessService.save(adTaskProcessDTO);
        return ResponseEntity.created(new URI("/api/ad-task-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-task-processes} : Updates an existing adTaskProcess.
     *
     * @param adTaskProcessDTO the adTaskProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTaskProcessDTO,
     * or with status {@code 400 (Bad Request)} if the adTaskProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTaskProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-task-processes")
    public ResponseEntity<AdTaskProcessDTO> updateAdTaskProcess(@Valid @RequestBody AdTaskProcessDTO adTaskProcessDTO) throws URISyntaxException {
        log.debug("REST request to update AdTaskProcess : {}", adTaskProcessDTO);
        if (adTaskProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTaskProcessDTO result = adTaskProcessService.save(adTaskProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTaskProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-task-processes} : get all the adTaskProcesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTaskProcesses in body.
     */
    @GetMapping("/ad-task-processes")
    public ResponseEntity<List<AdTaskProcessDTO>> getAllAdTaskProcesses(AdTaskProcessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTaskProcesses by criteria: {}", criteria);
        Page<AdTaskProcessDTO> page = adTaskProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-task-processes/count} : count all the adTaskProcesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-task-processes/count")
    public ResponseEntity<Long> countAdTaskProcesses(AdTaskProcessCriteria criteria) {
        log.debug("REST request to count AdTaskProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTaskProcessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-task-processes/:id} : get the "id" adTaskProcess.
     *
     * @param id the id of the adTaskProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTaskProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-task-processes/{id}")
    public ResponseEntity<AdTaskProcessDTO> getAdTaskProcess(@PathVariable Long id) {
        log.debug("REST request to get AdTaskProcess : {}", id);
        Optional<AdTaskProcessDTO> adTaskProcessDTO = adTaskProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTaskProcessDTO);
    }

    /**
     * {@code DELETE  /ad-task-processes/:id} : delete the "id" adTaskProcess.
     *
     * @param id the id of the adTaskProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-task-processes/{id}")
    public ResponseEntity<Void> deleteAdTaskProcess(@PathVariable Long id) {
        log.debug("REST request to delete AdTaskProcess : {}", id);
        adTaskProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
