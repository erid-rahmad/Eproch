package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CEvaluationMethodCriteriaQueryService;
import com.bhp.opusb.service.CEvaluationMethodCriteriaService;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaCriteria;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteriaDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CEvaluationMethodCriteria}.
 */
@RestController
@RequestMapping("/api")
public class CEvaluationMethodCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodCriteriaResource.class);

    private static final String ENTITY_NAME = "cEvaluationMethodCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEvaluationMethodCriteriaService cEvaluationMethodCriteriaService;

    private final CEvaluationMethodCriteriaQueryService cEvaluationMethodCriteriaQueryService;

    public CEvaluationMethodCriteriaResource(CEvaluationMethodCriteriaService cEvaluationMethodCriteriaService, CEvaluationMethodCriteriaQueryService cEvaluationMethodCriteriaQueryService) {
        this.cEvaluationMethodCriteriaService = cEvaluationMethodCriteriaService;
        this.cEvaluationMethodCriteriaQueryService = cEvaluationMethodCriteriaQueryService;
    }

    /**
     * {@code POST  /c-evaluation-method-criteria} : Create a new cEvaluationMethodCriteria.
     *
     * @param cEvaluationMethodCriteriaDTO the cEvaluationMethodCriteriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEvaluationMethodCriteriaDTO, or with status {@code 400 (Bad Request)} if the cEvaluationMethodCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-evaluation-method-criteria")
    public ResponseEntity<CEvaluationMethodCriteriaDTO> createCEvaluationMethodCriteria(@Valid @RequestBody CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to save CEvaluationMethodCriteria : {}", cEvaluationMethodCriteriaDTO);
        if (cEvaluationMethodCriteriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEvaluationMethodCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEvaluationMethodCriteriaDTO result = cEvaluationMethodCriteriaService.save(cEvaluationMethodCriteriaDTO);
        return ResponseEntity.created(new URI("/api/c-evaluation-method-criteria/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-evaluation-method-criteria} : Updates an existing cEvaluationMethodCriteria.
     *
     * @param cEvaluationMethodCriteriaDTO the cEvaluationMethodCriteriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEvaluationMethodCriteriaDTO,
     * or with status {@code 400 (Bad Request)} if the cEvaluationMethodCriteriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEvaluationMethodCriteriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-evaluation-method-criteria")
    public ResponseEntity<CEvaluationMethodCriteriaDTO> updateCEvaluationMethodCriteria(@Valid @RequestBody CEvaluationMethodCriteriaDTO cEvaluationMethodCriteriaDTO) throws URISyntaxException {
        log.debug("REST request to update CEvaluationMethodCriteria : {}", cEvaluationMethodCriteriaDTO);
        if (cEvaluationMethodCriteriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEvaluationMethodCriteriaDTO result = cEvaluationMethodCriteriaService.save(cEvaluationMethodCriteriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEvaluationMethodCriteriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-evaluation-method-criteria} : get all the cEvaluationMethodCriteria.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEvaluationMethodCriteria in body.
     */
    @GetMapping("/c-evaluation-method-criteria")
    public ResponseEntity<List<CEvaluationMethodCriteriaDTO>> getAllCEvaluationMethodCriteria(CEvaluationMethodCriteriaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEvaluationMethodCriteria by criteria: {}", criteria);
        Page<CEvaluationMethodCriteriaDTO> page = cEvaluationMethodCriteriaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-evaluation-method-criteria/count} : count all the cEvaluationMethodCriteria.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-evaluation-method-criteria/count")
    public ResponseEntity<Long> countCEvaluationMethodCriteria(CEvaluationMethodCriteriaCriteria criteria) {
        log.debug("REST request to count CEvaluationMethodCriteria by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEvaluationMethodCriteriaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-evaluation-method-criteria/:id} : get the "id" cEvaluationMethodCriteria.
     *
     * @param id the id of the cEvaluationMethodCriteriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEvaluationMethodCriteriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-evaluation-method-criteria/{id}")
    public ResponseEntity<CEvaluationMethodCriteriaDTO> getCEvaluationMethodCriteria(@PathVariable Long id) {
        log.debug("REST request to get CEvaluationMethodCriteria : {}", id);
        Optional<CEvaluationMethodCriteriaDTO> cEvaluationMethodCriteriaDTO = cEvaluationMethodCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEvaluationMethodCriteriaDTO);
    }

    /**
     * {@code DELETE  /c-evaluation-method-criteria/:id} : delete the "id" cEvaluationMethodCriteria.
     *
     * @param id the id of the cEvaluationMethodCriteriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-evaluation-method-criteria/{id}")
    public ResponseEntity<Void> deleteCEvaluationMethodCriteria(@PathVariable Long id) {
        log.debug("REST request to delete CEvaluationMethodCriteria : {}", id);
        cEvaluationMethodCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
