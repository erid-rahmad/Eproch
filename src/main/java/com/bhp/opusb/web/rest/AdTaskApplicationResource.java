package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdTaskApplicationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdTaskApplicationDTO;
import com.bhp.opusb.service.dto.AdTaskApplicationCriteria;
import com.bhp.opusb.service.AdTaskApplicationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdTaskApplication}.
 */
@RestController
@RequestMapping("/api")
public class AdTaskApplicationResource {

    private final Logger log = LoggerFactory.getLogger(AdTaskApplicationResource.class);

    private static final String ENTITY_NAME = "adTaskApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdTaskApplicationService adTaskApplicationService;

    private final AdTaskApplicationQueryService adTaskApplicationQueryService;

    public AdTaskApplicationResource(AdTaskApplicationService adTaskApplicationService, AdTaskApplicationQueryService adTaskApplicationQueryService) {
        this.adTaskApplicationService = adTaskApplicationService;
        this.adTaskApplicationQueryService = adTaskApplicationQueryService;
    }

    /**
     * {@code POST  /ad-task-applications} : Create a new adTaskApplication.
     *
     * @param adTaskApplicationDTO the adTaskApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adTaskApplicationDTO, or with status {@code 400 (Bad Request)} if the adTaskApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-task-applications")
    public ResponseEntity<AdTaskApplicationDTO> createAdTaskApplication(@Valid @RequestBody AdTaskApplicationDTO adTaskApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save AdTaskApplication : {}", adTaskApplicationDTO);
        if (adTaskApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new adTaskApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdTaskApplicationDTO result = adTaskApplicationService.save(adTaskApplicationDTO);
        return ResponseEntity.created(new URI("/api/ad-task-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-task-applications} : Updates an existing adTaskApplication.
     *
     * @param adTaskApplicationDTO the adTaskApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adTaskApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the adTaskApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adTaskApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-task-applications")
    public ResponseEntity<AdTaskApplicationDTO> updateAdTaskApplication(@Valid @RequestBody AdTaskApplicationDTO adTaskApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update AdTaskApplication : {}", adTaskApplicationDTO);
        if (adTaskApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdTaskApplicationDTO result = adTaskApplicationService.save(adTaskApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adTaskApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-task-applications} : get all the adTaskApplications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adTaskApplications in body.
     */
    @GetMapping("/ad-task-applications")
    public ResponseEntity<List<AdTaskApplicationDTO>> getAllAdTaskApplications(AdTaskApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdTaskApplications by criteria: {}", criteria);
        Page<AdTaskApplicationDTO> page = adTaskApplicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-task-applications/count} : count all the adTaskApplications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-task-applications/count")
    public ResponseEntity<Long> countAdTaskApplications(AdTaskApplicationCriteria criteria) {
        log.debug("REST request to count AdTaskApplications by criteria: {}", criteria);
        return ResponseEntity.ok().body(adTaskApplicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-task-applications/:id} : get the "id" adTaskApplication.
     *
     * @param id the id of the adTaskApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adTaskApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-task-applications/{id}")
    public ResponseEntity<AdTaskApplicationDTO> getAdTaskApplication(@PathVariable Long id) {
        log.debug("REST request to get AdTaskApplication : {}", id);
        Optional<AdTaskApplicationDTO> adTaskApplicationDTO = adTaskApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adTaskApplicationDTO);
    }

    /**
     * {@code DELETE  /ad-task-applications/:id} : delete the "id" adTaskApplication.
     *
     * @param id the id of the adTaskApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-task-applications/{id}")
    public ResponseEntity<Void> deleteAdTaskApplication(@PathVariable Long id) {
        log.debug("REST request to delete AdTaskApplication : {}", id);
        adTaskApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
