package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CEvaluationCriteriaQueryService;
import com.bhp.opusb.service.CEvaluationCriteriaService;
import com.bhp.opusb.service.dto.CEvaluationCriteriaCriteria;
import com.bhp.opusb.service.dto.CEvaluationCriteriaDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.CEvaluationCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CEvaluationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CEvaluationCriteriaResource.class);

    private static final String ENTITY_NAME = "cEvaluationCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEvaluationCriteriaService cEvaluationCriteriaService;

    private final CEvaluationCriteriaQueryService cEvaluationCriteriaQueryService;

    public CEvaluationCriteriaResource(CEvaluationCriteriaService cEvaluationCriteriaService, CEvaluationCriteriaQueryService cEvaluationCriteriaQueryService) {
        this.cEvaluationCriteriaService = cEvaluationCriteriaService;
        this.cEvaluationCriteriaQueryService = cEvaluationCriteriaQueryService;
    }

    /**
     * {@code POST  /c-evaluation-criteria} : Create a new cEvaluationCriteria.
     *
     * @param cEvaluationCriteriaDTO the cEvaluationCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEvaluationCriteriaDTO, or with status {@code 400 (Bad Request)} if the cEvaluationCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-evaluation-criteria")
    public ResponseEntity<CEvaluationCriteriaDTO> createCEvaluationCriteria(@Valid @RequestBody CEvaluationCriteriaDTO cEvaluationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CEvaluationCriteria : {}", cEvaluationCriteriaDTO);
        if (cEvaluationCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEvaluationCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEvaluationCriteriaDTO result = cEvaluationCriteriaService.save(cEvaluationCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-evaluation-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-evaluation-criteria} : Updates an existing cEvaluationCriteria.
     *
     * @param cEvaluationCriteriaDTO the cEvaluationCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEvaluationCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cEvaluationCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEvaluationCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-evaluation-criteria")
    public ResponseEntity<CEvaluationCriteriaDTO> updateCEvaluationCriteria(@Valid @RequestBody CEvaluationCriteriaDTO cEvaluationCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CEvaluationCriteria : {}", cEvaluationCriteriaDTO);
        if (cEvaluationCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEvaluationCriteriaDTO result = cEvaluationCriteriaService.save(cEvaluationCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEvaluationCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-evaluation-criteria} : get all the cEvaluationCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEvaluationCriteria in body.
     */
    @GetMapping("/c-evaluation-criteria")
    public ResponseEntity<List<CEvaluationCriteriaDTO>> getAllCEvaluationCriteria(CEvaluationCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEvaluationCriteria by criteria: {}", criteria);
        Page<CEvaluationCriteriaDTO> page = cEvaluationCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-evaluation-criteria/count} : count all the cEvaluationCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-evaluation-criteria/count")
    public ResponseEntity<Long> countCEvaluationCriteria(CEvaluationCriteriaCriteria criteria) {
        log.debug("REST request to count CEvaluationCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEvaluationCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-evaluation-criteria/:id} : get the "id" cEvaluationCriteria.
     *
     * @param id the id of the cEvaluationCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEvaluationCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-evaluation-criteria/{id}")
    public ResponseEntity<CEvaluationCriteriaDTO> getCEvaluationCriteria(@PathVariable Long id) {
        log.debug("REST request to get CEvaluationCriteria : {}", id);
        Optional<CEvaluationCriteriaDTO> cEvaluationCriteriaDTO = cEvaluationCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEvaluationCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-evaluation-criteria/:id} : delete the "id" cEvaluationCriteria.
     *
     * @param id the id of the cEvaluationCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-evaluation-criteria/{id}")
    public ResponseEntity<Void> deleteCEvaluationCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CEvaluationCriteria : {}", id);
        cEvaluationCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
