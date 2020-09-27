package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdCalloutService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdCalloutDTO;
import com.bhp.opusb.service.dto.AdCalloutCriteria;
import com.bhp.opusb.service.AdCalloutQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdCallout}.
 */
@RestController
@RequestMapping("/api")
public class AdCalloutResource {

    private final Logger log = LoggerFactory.getLogger(AdCalloutResource.class);

    private static final String ENTITY_NAME = "adCallout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdCalloutService adCalloutService;

    private final AdCalloutQueryService adCalloutQueryService;

    public AdCalloutResource(AdCalloutService adCalloutService, AdCalloutQueryService adCalloutQueryService) {
        this.adCalloutService = adCalloutService;
        this.adCalloutQueryService = adCalloutQueryService;
    }

    /**
     * {@code POST  /ad-callouts} : Create a new adCallout.
     *
     * @param adCalloutDTO the adCalloutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adCalloutDTO, or with status {@code 400 (Bad Request)} if the adCallout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-callouts")
    public ResponseEntity<AdCalloutDTO> createAdCallout(@Valid @RequestBody AdCalloutDTO adCalloutDTO) throws URISyntaxException {
        log.debug("REST request to save AdCallout : {}", adCalloutDTO);
        if (adCalloutDTO.getId() != null) {
            throw new BadRequestAlertException("A new adCallout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdCalloutDTO result = adCalloutService.save(adCalloutDTO);
        return ResponseEntity.created(new URI("/api/ad-callouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-callouts} : Updates an existing adCallout.
     *
     * @param adCalloutDTO the adCalloutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adCalloutDTO,
     * or with status {@code 400 (Bad Request)} if the adCalloutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adCalloutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-callouts")
    public ResponseEntity<AdCalloutDTO> updateAdCallout(@Valid @RequestBody AdCalloutDTO adCalloutDTO) throws URISyntaxException {
        log.debug("REST request to update AdCallout : {}", adCalloutDTO);
        if (adCalloutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdCalloutDTO result = adCalloutService.save(adCalloutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adCalloutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-callouts} : get all the adCallouts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adCallouts in body.
     */
    @GetMapping("/ad-callouts")
    public ResponseEntity<List<AdCalloutDTO>> getAllAdCallouts(AdCalloutCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdCallouts by criteria: {}", criteria);
        Page<AdCalloutDTO> page = adCalloutQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-callouts/count} : count all the adCallouts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-callouts/count")
    public ResponseEntity<Long> countAdCallouts(AdCalloutCriteria criteria) {
        log.debug("REST request to count AdCallouts by criteria: {}", criteria);
        return ResponseEntity.ok().body(adCalloutQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-callouts/:id} : get the "id" adCallout.
     *
     * @param id the id of the adCalloutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adCalloutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-callouts/{id}")
    public ResponseEntity<AdCalloutDTO> getAdCallout(@PathVariable Long id) {
        log.debug("REST request to get AdCallout : {}", id);
        Optional<AdCalloutDTO> adCalloutDTO = adCalloutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adCalloutDTO);
    }

    /**
     * {@code DELETE  /ad-callouts/:id} : delete the "id" adCallout.
     *
     * @param id the id of the adCalloutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-callouts/{id}")
    public ResponseEntity<Void> deleteAdCallout(@PathVariable Long id) {
        log.debug("REST request to delete AdCallout : {}", id);
        adCalloutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
