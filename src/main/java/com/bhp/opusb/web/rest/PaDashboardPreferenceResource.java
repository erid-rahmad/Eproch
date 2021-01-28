package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.PaDashboardPreferenceService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.PaDashboardPreferenceDTO;
import com.bhp.opusb.service.dto.PaDashboardPreferenceCriteria;
import com.bhp.opusb.service.PaDashboardPreferenceQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.PaDashboardPreference}.
 */
@RestController
@RequestMapping("/api")
public class PaDashboardPreferenceResource {

    private final Logger log = LoggerFactory.getLogger(PaDashboardPreferenceResource.class);

    private static final String ENTITY_NAME = "paDashboardPreference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaDashboardPreferenceService paDashboardPreferenceService;

    private final PaDashboardPreferenceQueryService paDashboardPreferenceQueryService;

    public PaDashboardPreferenceResource(PaDashboardPreferenceService paDashboardPreferenceService, PaDashboardPreferenceQueryService paDashboardPreferenceQueryService) {
        this.paDashboardPreferenceService = paDashboardPreferenceService;
        this.paDashboardPreferenceQueryService = paDashboardPreferenceQueryService;
    }

    /**
     * {@code POST  /pa-dashboard-preferences} : Create a new paDashboardPreference.
     *
     * @param paDashboardPreferenceDTO the paDashboardPreferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paDashboardPreferenceDTO, or with status {@code 400 (Bad Request)} if the paDashboardPreference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pa-dashboard-preferences")
    public ResponseEntity<PaDashboardPreferenceDTO> createPaDashboardPreference(@Valid @RequestBody PaDashboardPreferenceDTO paDashboardPreferenceDTO) throws URISyntaxException {
        log.debug("REST request to save PaDashboardPreference : {}", paDashboardPreferenceDTO);
        if (paDashboardPreferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new paDashboardPreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaDashboardPreferenceDTO result = paDashboardPreferenceService.save(paDashboardPreferenceDTO);
        return ResponseEntity.created(new URI("/api/pa-dashboard-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pa-dashboard-preferences} : Updates an existing paDashboardPreference.
     *
     * @param paDashboardPreferenceDTO the paDashboardPreferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paDashboardPreferenceDTO,
     * or with status {@code 400 (Bad Request)} if the paDashboardPreferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paDashboardPreferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pa-dashboard-preferences")
    public ResponseEntity<PaDashboardPreferenceDTO> updatePaDashboardPreference(@Valid @RequestBody PaDashboardPreferenceDTO paDashboardPreferenceDTO) throws URISyntaxException {
        log.debug("REST request to update PaDashboardPreference : {}", paDashboardPreferenceDTO);
        if (paDashboardPreferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaDashboardPreferenceDTO result = paDashboardPreferenceService.save(paDashboardPreferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paDashboardPreferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pa-dashboard-preferences} : get all the paDashboardPreferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paDashboardPreferences in body.
     */
    @GetMapping("/pa-dashboard-preferences")
    public ResponseEntity<List<PaDashboardPreferenceDTO>> getAllPaDashboardPreferences(PaDashboardPreferenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaDashboardPreferences by criteria: {}", criteria);
        Page<PaDashboardPreferenceDTO> page = paDashboardPreferenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pa-dashboard-preferences/count} : count all the paDashboardPreferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pa-dashboard-preferences/count")
    public ResponseEntity<Long> countPaDashboardPreferences(PaDashboardPreferenceCriteria criteria) {
        log.debug("REST request to count PaDashboardPreferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(paDashboardPreferenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pa-dashboard-preferences/:id} : get the "id" paDashboardPreference.
     *
     * @param id the id of the paDashboardPreferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paDashboardPreferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pa-dashboard-preferences/{id}")
    public ResponseEntity<PaDashboardPreferenceDTO> getPaDashboardPreference(@PathVariable Long id) {
        log.debug("REST request to get PaDashboardPreference : {}", id);
        Optional<PaDashboardPreferenceDTO> paDashboardPreferenceDTO = paDashboardPreferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paDashboardPreferenceDTO);
    }

    /**
     * {@code DELETE  /pa-dashboard-preferences/:id} : delete the "id" paDashboardPreference.
     *
     * @param id the id of the paDashboardPreferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pa-dashboard-preferences/{id}")
    public ResponseEntity<Void> deletePaDashboardPreference(@PathVariable Long id) {
        log.debug("REST request to delete PaDashboardPreference : {}", id);
        paDashboardPreferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
