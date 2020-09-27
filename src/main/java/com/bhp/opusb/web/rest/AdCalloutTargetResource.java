package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdCalloutTargetService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdCalloutTargetDTO;
import com.bhp.opusb.service.dto.AdCalloutTargetCriteria;
import com.bhp.opusb.service.AdCalloutTargetQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdCalloutTarget}.
 */
@RestController
@RequestMapping("/api")
public class AdCalloutTargetResource {

    private final Logger log = LoggerFactory.getLogger(AdCalloutTargetResource.class);

    private static final String ENTITY_NAME = "adCalloutTarget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdCalloutTargetService adCalloutTargetService;

    private final AdCalloutTargetQueryService adCalloutTargetQueryService;

    public AdCalloutTargetResource(AdCalloutTargetService adCalloutTargetService, AdCalloutTargetQueryService adCalloutTargetQueryService) {
        this.adCalloutTargetService = adCalloutTargetService;
        this.adCalloutTargetQueryService = adCalloutTargetQueryService;
    }

    /**
     * {@code POST  /ad-callout-targets} : Create a new adCalloutTarget.
     *
     * @param adCalloutTargetDTO the adCalloutTargetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adCalloutTargetDTO, or with status {@code 400 (Bad Request)} if the adCalloutTarget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-callout-targets")
    public ResponseEntity<AdCalloutTargetDTO> createAdCalloutTarget(@Valid @RequestBody AdCalloutTargetDTO adCalloutTargetDTO) throws URISyntaxException {
        log.debug("REST request to save AdCalloutTarget : {}", adCalloutTargetDTO);
        if (adCalloutTargetDTO.getId() != null) {
            throw new BadRequestAlertException("A new adCalloutTarget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdCalloutTargetDTO result = adCalloutTargetService.save(adCalloutTargetDTO);
        return ResponseEntity.created(new URI("/api/ad-callout-targets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-callout-targets} : Updates an existing adCalloutTarget.
     *
     * @param adCalloutTargetDTO the adCalloutTargetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adCalloutTargetDTO,
     * or with status {@code 400 (Bad Request)} if the adCalloutTargetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adCalloutTargetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-callout-targets")
    public ResponseEntity<AdCalloutTargetDTO> updateAdCalloutTarget(@Valid @RequestBody AdCalloutTargetDTO adCalloutTargetDTO) throws URISyntaxException {
        log.debug("REST request to update AdCalloutTarget : {}", adCalloutTargetDTO);
        if (adCalloutTargetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdCalloutTargetDTO result = adCalloutTargetService.save(adCalloutTargetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adCalloutTargetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-callout-targets} : get all the adCalloutTargets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adCalloutTargets in body.
     */
    @GetMapping("/ad-callout-targets")
    public ResponseEntity<List<AdCalloutTargetDTO>> getAllAdCalloutTargets(AdCalloutTargetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdCalloutTargets by criteria: {}", criteria);
        Page<AdCalloutTargetDTO> page = adCalloutTargetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-callout-targets/count} : count all the adCalloutTargets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-callout-targets/count")
    public ResponseEntity<Long> countAdCalloutTargets(AdCalloutTargetCriteria criteria) {
        log.debug("REST request to count AdCalloutTargets by criteria: {}", criteria);
        return ResponseEntity.ok().body(adCalloutTargetQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-callout-targets/:id} : get the "id" adCalloutTarget.
     *
     * @param id the id of the adCalloutTargetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adCalloutTargetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-callout-targets/{id}")
    public ResponseEntity<AdCalloutTargetDTO> getAdCalloutTarget(@PathVariable Long id) {
        log.debug("REST request to get AdCalloutTarget : {}", id);
        Optional<AdCalloutTargetDTO> adCalloutTargetDTO = adCalloutTargetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adCalloutTargetDTO);
    }

    /**
     * {@code DELETE  /ad-callout-targets/:id} : delete the "id" adCalloutTarget.
     *
     * @param id the id of the adCalloutTargetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-callout-targets/{id}")
    public ResponseEntity<Void> deleteAdCalloutTarget(@PathVariable Long id) {
        log.debug("REST request to delete AdCalloutTarget : {}", id);
        adCalloutTargetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
