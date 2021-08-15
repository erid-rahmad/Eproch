package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationCriteriaService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaDTO;
import com.bhp.opusb.service.dto.MPrequalificationCriteriaCriteria;
import com.bhp.opusb.service.MPrequalificationCriteriaQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationCriteria}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationCriteriaResource.class);

    private static final String ENTITY_NAME = "mPrequalificationCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationCriteriaService mPrequalificationCriteriaService;

    private final MPrequalificationCriteriaQueryService mPrequalificationCriteriaQueryService;

    public MPrequalificationCriteriaResource(MPrequalificationCriteriaService mPrequalificationCriteriaService, MPrequalificationCriteriaQueryService mPrequalificationCriteriaQueryService) {
        this.mPrequalificationCriteriaService = mPrequalificationCriteriaService;
        this.mPrequalificationCriteriaQueryService = mPrequalificationCriteriaQueryService;
    }

    /**
     * {@code POST  /m-prequalification-criteria} : Create a new mPrequalificationCriteria.
     *
     * @param mPrequalificationCriteriaDTO the mPrequalificationCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationCriteriaDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-criteria")
    public ResponseEntity<MPrequalificationCriteriaDTO> createMPrequalificationCriteria(@Valid @RequestBody MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationCriteria : {}", mPrequalificationCriteriaDTO);
        if (mPrequalificationCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationCriteriaDTO result = mPrequalificationCriteriaService.save(mPrequalificationCriteriaDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-prequalification-criteria/requirements} : Create or update mPrequalificationCriteriaDTOs.
     *
     * @param mPrequalificationCriteriaDTOs the list of mPrequalificationCriteriaDTOs to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationCriteriaDTOs.
     */
    @PostMapping("/m-prequalification-criteria/requirements")
    public ResponseEntity<List<MPrequalificationCriteriaDTO>> createMPrequalificationCriterias(@Valid @RequestBody List<MPrequalificationCriteriaDTO> mPrequalificationCriteriaDTOs) {
        log.debug("REST request to save mPrequalificationCriteriaDTOs. size : {}", mPrequalificationCriteriaDTOs.size());
        List<MPrequalificationCriteriaDTO> result = mPrequalificationCriteriaService.saveRequirements(mPrequalificationCriteriaDTOs);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-prequalification-criteria} : Updates an existing mPrequalificationCriteria.
     *
     * @param mPrequalificationCriteriaDTO the mPrequalificationCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-criteria")
    public ResponseEntity<MPrequalificationCriteriaDTO> updateMPrequalificationCriteria(@Valid @RequestBody MPrequalificationCriteriaDTO mPrequalificationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationCriteria : {}", mPrequalificationCriteriaDTO);
        if (mPrequalificationCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationCriteriaDTO result = mPrequalificationCriteriaService.save(mPrequalificationCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-criteria} : get all the mPrequalificationCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationCriteria in body.
     */
    @GetMapping("/m-prequalification-criteria")
    public ResponseEntity<List<MPrequalificationCriteriaDTO>> getAllMPrequalificationCriteria(MPrequalificationCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationCriteria by criteria: {}", criteria);
        Page<MPrequalificationCriteriaDTO> page = mPrequalificationCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-criteria/count} : count all the mPrequalificationCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-criteria/count")
    public ResponseEntity<Long> countMPrequalificationCriteria(MPrequalificationCriteriaCriteria criteria) {
        log.debug("REST request to count MPrequalificationCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-criteria/:id} : get the "id" mPrequalificationCriteria.
     *
     * @param id the id of the mPrequalificationCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-criteria/{id}")
    public ResponseEntity<MPrequalificationCriteriaDTO> getMPrequalificationCriteria(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationCriteria : {}", id);
        Optional<MPrequalificationCriteriaDTO> mPrequalificationCriteriaDTO = mPrequalificationCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationCriteriaDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-criteria/:id} : delete the "id" mPrequalificationCriteria.
     *
     * @param id the id of the mPrequalificationCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-criteria/{id}")
    public ResponseEntity<Void> deleteMPrequalificationCriteria(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationCriteria : {}", id);
        mPrequalificationCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
