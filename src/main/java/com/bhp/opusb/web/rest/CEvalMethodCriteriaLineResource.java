package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CEvalMethodCriteriaLineQueryService;
import com.bhp.opusb.service.CEvalMethodCriteriaLineService;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineCriteria;
import com.bhp.opusb.service.dto.CEvalMethodCriteriaLineDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CEvalMethodCriteriaLine}.
 */
@RestController
@RequestMapping("/api")
public class CEvalMethodCriteriaLineResource {

    private final Logger log = LoggerFactory.getLogger(CEvalMethodCriteriaLineResource.class);

    private static final String ENTITY_NAME = "cEvalMethodCriteriaLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEvalMethodCriteriaLineService cEvalMethodCriteriaLineService;

    private final CEvalMethodCriteriaLineQueryService cEvalMethodCriteriaLineQueryService;

    public CEvalMethodCriteriaLineResource(CEvalMethodCriteriaLineService cEvalMethodCriteriaLineService, CEvalMethodCriteriaLineQueryService cEvalMethodCriteriaLineQueryService) {
        this.cEvalMethodCriteriaLineService = cEvalMethodCriteriaLineService;
        this.cEvalMethodCriteriaLineQueryService = cEvalMethodCriteriaLineQueryService;
    }

    /**
     * {@code POST  /c-eval-method-criteria-lines} : Create a new cEvalMethodCriteriaLine.
     *
     * @param cEvalMethodCriteriaLineDTO the cEvalMethodCriteriaLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEvalMethodCriteriaLineDTO, or with status {@code 400 (Bad Request)} if the cEvalMethodCriteriaLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-eval-method-criteria-lines")
    public ResponseEntity<CEvalMethodCriteriaLineDTO> createCEvalMethodCriteriaLine(@Valid @RequestBody CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO) throws URISyntaxException {
        log.debug("REST request to save CEvalMethodCriteriaLine : {}", cEvalMethodCriteriaLineDTO);
        if (cEvalMethodCriteriaLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEvalMethodCriteriaLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEvalMethodCriteriaLineDTO result = cEvalMethodCriteriaLineService.save(cEvalMethodCriteriaLineDTO);
        return ResponseEntity.created(new URI("/api/c-eval-method-criteria-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-eval-method-criteria-lines} : Updates an existing cEvalMethodCriteriaLine.
     *
     * @param cEvalMethodCriteriaLineDTO the cEvalMethodCriteriaLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEvalMethodCriteriaLineDTO,
     * or with status {@code 400 (Bad Request)} if the cEvalMethodCriteriaLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEvalMethodCriteriaLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-eval-method-criteria-lines")
    public ResponseEntity<CEvalMethodCriteriaLineDTO> updateCEvalMethodCriteriaLine(@Valid @RequestBody CEvalMethodCriteriaLineDTO cEvalMethodCriteriaLineDTO) throws URISyntaxException {
        log.debug("REST request to update CEvalMethodCriteriaLine : {}", cEvalMethodCriteriaLineDTO);
        if (cEvalMethodCriteriaLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEvalMethodCriteriaLineDTO result = cEvalMethodCriteriaLineService.save(cEvalMethodCriteriaLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEvalMethodCriteriaLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-eval-method-criteria-lines} : get all the cEvalMethodCriteriaLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEvalMethodCriteriaLines in body.
     */
    @GetMapping("/c-eval-method-criteria-lines")
    public ResponseEntity<List<CEvalMethodCriteriaLineDTO>> getAllCEvalMethodCriteriaLines(CEvalMethodCriteriaLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEvalMethodCriteriaLines by criteria: {}", criteria);
        Page<CEvalMethodCriteriaLineDTO> page = cEvalMethodCriteriaLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-eval-method-criteria-lines/count} : count all the cEvalMethodCriteriaLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-eval-method-criteria-lines/count")
    public ResponseEntity<Long> countCEvalMethodCriteriaLines(CEvalMethodCriteriaLineCriteria criteria) {
        log.debug("REST request to count CEvalMethodCriteriaLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEvalMethodCriteriaLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-eval-method-criteria-lines/:id} : get the "id" cEvalMethodCriteriaLine.
     *
     * @param id the id of the cEvalMethodCriteriaLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEvalMethodCriteriaLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-eval-method-criteria-lines/{id}")
    public ResponseEntity<CEvalMethodCriteriaLineDTO> getCEvalMethodCriteriaLine(@PathVariable Long id) {
        log.debug("REST request to get CEvalMethodCriteriaLine : {}", id);
        Optional<CEvalMethodCriteriaLineDTO> cEvalMethodCriteriaLineDTO = cEvalMethodCriteriaLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEvalMethodCriteriaLineDTO);
    }

    /**
     * {@code DELETE  /c-eval-method-criteria-lines/:id} : delete the "id" cEvalMethodCriteriaLine.
     *
     * @param id the id of the cEvalMethodCriteriaLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-eval-method-criteria-lines/{id}")
    public ResponseEntity<Void> deleteCEvalMethodCriteriaLine(@PathVariable Long id) {
        log.debug("REST request to delete CEvalMethodCriteriaLine : {}", id);
        cEvalMethodCriteriaLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
