package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdTaskSchedulerService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdTaskSchedulerDTO;
import com.bhp.opusb.service.dto.AdTaskSchedulerCriteria;
import com.bhp.opusb.service.AdTaskSchedulerQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTaskScheduler}.
 */
@RestController
@RequestMapping("/api")
public class AdTaskSchedulerResource {

    private final Logger log = LoggerFactory.getLogger(AdTaskSchedulerResource.class);

    private static final String ENTITY_NAME = "adTaskScheduler";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTaskSchedulerService adTaskSchedulerService;

    private final AdTaskSchedulerQueryService adTaskSchedulerQueryService;

    public AdTaskSchedulerResource(AdTaskSchedulerService adTaskSchedulerService, AdTaskSchedulerQueryService adTaskSchedulerQueryService) {
        this.adTaskSchedulerService = adTaskSchedulerService;
        this.adTaskSchedulerQueryService = adTaskSchedulerQueryService;
    }

    /**
     * {@code POST  /ad-task-schedulers} : Create a new adTaskScheduler.
     *
     * @param adTaskSchedulerDTO the adTaskSchedulerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTaskSchedulerDTO, or with status {@code 400 (Bad Request)} if the adTaskScheduler has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-task-schedulers")
    public ResponseEntity<AdTaskSchedulerDTO> createAdTaskScheduler(@Valid @RequestBody AdTaskSchedulerDTO adTaskSchedulerDTO) throws URISyntaxException {
        log.debug("REST request to save AdTaskScheduler : {}", adTaskSchedulerDTO);
        if (adTaskSchedulerDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTaskScheduler cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTaskSchedulerDTO result = adTaskSchedulerService.save(adTaskSchedulerDTO);
        return ResponseEntity.created(new URI("/api/ad-task-schedulers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-task-schedulers} : Updates an existing adTaskScheduler.
     *
     * @param adTaskSchedulerDTO the adTaskSchedulerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTaskSchedulerDTO,
     * or with status {@code 400 (Bad Request)} if the adTaskSchedulerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTaskSchedulerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-task-schedulers")
    public ResponseEntity<AdTaskSchedulerDTO> updateAdTaskScheduler(@Valid @RequestBody AdTaskSchedulerDTO adTaskSchedulerDTO) throws URISyntaxException {
        log.debug("REST request to update AdTaskScheduler : {}", adTaskSchedulerDTO);
        if (adTaskSchedulerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTaskSchedulerDTO result = adTaskSchedulerService.save(adTaskSchedulerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTaskSchedulerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-task-schedulers} : get all the adTaskSchedulers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTaskSchedulers in body.
     */
    @GetMapping("/ad-task-schedulers")
    public ResponseEntity<List<AdTaskSchedulerDTO>> getAllAdTaskSchedulers(AdTaskSchedulerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTaskSchedulers by criteria: {}", criteria);
        Page<AdTaskSchedulerDTO> page = adTaskSchedulerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-task-schedulers/count} : count all the adTaskSchedulers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-task-schedulers/count")
    public ResponseEntity<Long> countAdTaskSchedulers(AdTaskSchedulerCriteria criteria) {
        log.debug("REST request to count AdTaskSchedulers by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTaskSchedulerQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-task-schedulers/:id} : get the "id" adTaskScheduler.
     *
     * @param id the id of the adTaskSchedulerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTaskSchedulerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-task-schedulers/{id}")
    public ResponseEntity<AdTaskSchedulerDTO> getAdTaskScheduler(@PathVariable Long id) {
        log.debug("REST request to get AdTaskScheduler : {}", id);
        Optional<AdTaskSchedulerDTO> adTaskSchedulerDTO = adTaskSchedulerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTaskSchedulerDTO);
    }

    /**
     * {@code DELETE  /ad-task-schedulers/:id} : delete the "id" adTaskScheduler.
     *
     * @param id the id of the adTaskSchedulerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-task-schedulers/{id}")
    public ResponseEntity<Void> deleteAdTaskScheduler(@PathVariable Long id) {
        log.debug("REST request to delete AdTaskScheduler : {}", id);
        adTaskSchedulerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
