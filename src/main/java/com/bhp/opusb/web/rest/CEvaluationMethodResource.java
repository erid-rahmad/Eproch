package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CEvaluationMethodQueryService;
import com.bhp.opusb.service.CEvaluationMethodService;
import com.bhp.opusb.service.dto.CEvaluationMethodCriteria;
import com.bhp.opusb.service.dto.CEvaluationMethodDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CEvaluationMethod}.
 */
@RestController
@RequestMapping("/api")
public class CEvaluationMethodResource {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodResource.class);

    private static final String ENTITY_NAME = "cEvaluationMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEvaluationMethodService cEvaluationMethodService;

    private final CEvaluationMethodQueryService cEvaluationMethodQueryService;

    public CEvaluationMethodResource(CEvaluationMethodService cEvaluationMethodService, CEvaluationMethodQueryService cEvaluationMethodQueryService) {
        this.cEvaluationMethodService = cEvaluationMethodService;
        this.cEvaluationMethodQueryService = cEvaluationMethodQueryService;
    }

    /**
     * {@code POST  /c-evaluation-methods} : Create a new cEvaluationMethod.
     *
     * @param cEvaluationMethodDTO the cEvaluationMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEvaluationMethodDTO, or with status {@code 400 (Bad Request)} if the cEvaluationMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-evaluation-methods")
    public ResponseEntity<CEvaluationMethodDTO> createCEvaluationMethod(@Valid @RequestBody CEvaluationMethodDTO cEvaluationMethodDTO) throws URISyntaxException {
        log.debug("REST request to save CEvaluationMethod : {}", cEvaluationMethodDTO);
        if (cEvaluationMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEvaluationMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEvaluationMethodDTO result = cEvaluationMethodService.save(cEvaluationMethodDTO);
        return ResponseEntity.created(new URI("/api/c-evaluation-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-evaluation-methods} : Updates an existing cEvaluationMethod.
     *
     * @param cEvaluationMethodDTO the cEvaluationMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEvaluationMethodDTO,
     * or with status {@code 400 (Bad Request)} if the cEvaluationMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEvaluationMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-evaluation-methods")
    public ResponseEntity<CEvaluationMethodDTO> updateCEvaluationMethod(@Valid @RequestBody CEvaluationMethodDTO cEvaluationMethodDTO) throws URISyntaxException {
        log.debug("REST request to update CEvaluationMethod : {}", cEvaluationMethodDTO);
        if (cEvaluationMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEvaluationMethodDTO result = cEvaluationMethodService.save(cEvaluationMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEvaluationMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-evaluation-methods} : get all the cEvaluationMethods.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEvaluationMethods in body.
     */
    @GetMapping("/c-evaluation-methods")
    public ResponseEntity<List<CEvaluationMethodDTO>> getAllCEvaluationMethods(CEvaluationMethodCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEvaluationMethods by criteria: {}", criteria);
        Page<CEvaluationMethodDTO> page = cEvaluationMethodQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-evaluation-methods/count} : count all the cEvaluationMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-evaluation-methods/count")
    public ResponseEntity<Long> countCEvaluationMethods(CEvaluationMethodCriteria criteria) {
        log.debug("REST request to count CEvaluationMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEvaluationMethodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-evaluation-methods/:id} : get the "id" cEvaluationMethod.
     *
     * @param id the id of the cEvaluationMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEvaluationMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-evaluation-methods/{id}")
    public ResponseEntity<CEvaluationMethodDTO> getCEvaluationMethod(@PathVariable Long id) {
        log.debug("REST request to get CEvaluationMethod : {}", id);
        Optional<CEvaluationMethodDTO> cEvaluationMethodDTO = cEvaluationMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEvaluationMethodDTO);
    }

    /**
     * {@code DELETE  /c-evaluation-methods/:id} : delete the "id" cEvaluationMethod.
     *
     * @param id the id of the cEvaluationMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-evaluation-methods/{id}")
    public ResponseEntity<Void> deleteCEvaluationMethod(@PathVariable Long id) {
        log.debug("REST request to delete CEvaluationMethod : {}", id);
        cEvaluationMethodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
