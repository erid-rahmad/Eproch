package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CEvaluationMethodLineQueryService;
import com.bhp.opusb.service.CEvaluationMethodLineService;
import com.bhp.opusb.service.dto.CEvaluationMethodLineCriteria;
import com.bhp.opusb.service.dto.CEvaluationMethodLineDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CEvaluationMethodLine}.
 */
@RestController
@RequestMapping("/api")
public class CEvaluationMethodLineResource {

    private final Logger log = LoggerFactory.getLogger(CEvaluationMethodLineResource.class);

    private static final String ENTITY_NAME = "cEvaluationMethodLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEvaluationMethodLineService cEvaluationMethodLineService;

    private final CEvaluationMethodLineQueryService cEvaluationMethodLineQueryService;

    public CEvaluationMethodLineResource(CEvaluationMethodLineService cEvaluationMethodLineService, CEvaluationMethodLineQueryService cEvaluationMethodLineQueryService) {
        this.cEvaluationMethodLineService = cEvaluationMethodLineService;
        this.cEvaluationMethodLineQueryService = cEvaluationMethodLineQueryService;
    }

    /**
     * {@code POST  /c-evaluation-method-lines} : Create a new cEvaluationMethodLine.
     *
     * @param cEvaluationMethodLineDTO the cEvaluationMethodLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEvaluationMethodLineDTO, or with status {@code 400 (Bad Request)} if the cEvaluationMethodLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-evaluation-method-lines")
    public ResponseEntity<CEvaluationMethodLineDTO> createCEvaluationMethodLine(@Valid @RequestBody CEvaluationMethodLineDTO cEvaluationMethodLineDTO) throws URISyntaxException {
        log.debug("REST request to save CEvaluationMethodLine : {}", cEvaluationMethodLineDTO);
        if (cEvaluationMethodLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEvaluationMethodLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEvaluationMethodLineDTO result = cEvaluationMethodLineService.save(cEvaluationMethodLineDTO);
        return ResponseEntity.created(new URI("/api/c-evaluation-method-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-evaluation-method-lines} : Updates an existing cEvaluationMethodLine.
     *
     * @param cEvaluationMethodLineDTO the cEvaluationMethodLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEvaluationMethodLineDTO,
     * or with status {@code 400 (Bad Request)} if the cEvaluationMethodLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEvaluationMethodLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-evaluation-method-lines")
    public ResponseEntity<CEvaluationMethodLineDTO> updateCEvaluationMethodLine(@Valid @RequestBody CEvaluationMethodLineDTO cEvaluationMethodLineDTO) throws URISyntaxException {
        log.debug("REST request to update CEvaluationMethodLine : {}", cEvaluationMethodLineDTO);
        if (cEvaluationMethodLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEvaluationMethodLineDTO result = cEvaluationMethodLineService.save(cEvaluationMethodLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEvaluationMethodLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-evaluation-method-lines} : get all the cEvaluationMethodLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEvaluationMethodLines in body.
     */
    @GetMapping("/c-evaluation-method-lines")
    public ResponseEntity<List<CEvaluationMethodLineDTO>> getAllCEvaluationMethodLines(CEvaluationMethodLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEvaluationMethodLines by criteria: {}", criteria);
        Page<CEvaluationMethodLineDTO> page = cEvaluationMethodLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-evaluation-method-lines/count} : count all the cEvaluationMethodLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-evaluation-method-lines/count")
    public ResponseEntity<Long> countCEvaluationMethodLines(CEvaluationMethodLineCriteria criteria) {
        log.debug("REST request to count CEvaluationMethodLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEvaluationMethodLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-evaluation-method-lines/:id} : get the "id" cEvaluationMethodLine.
     *
     * @param id the id of the cEvaluationMethodLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEvaluationMethodLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-evaluation-method-lines/{id}")
    public ResponseEntity<CEvaluationMethodLineDTO> getCEvaluationMethodLine(@PathVariable Long id) {
        log.debug("REST request to get CEvaluationMethodLine : {}", id);
        Optional<CEvaluationMethodLineDTO> cEvaluationMethodLineDTO = cEvaluationMethodLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEvaluationMethodLineDTO);
    }

    /**
     * {@code DELETE  /c-evaluation-method-lines/:id} : delete the "id" cEvaluationMethodLine.
     *
     * @param id the id of the cEvaluationMethodLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-evaluation-method-lines/{id}")
    public ResponseEntity<Void> deleteCEvaluationMethodLine(@PathVariable Long id) {
        log.debug("REST request to delete CEvaluationMethodLine : {}", id);
        cEvaluationMethodLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
