package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CBudgetPlanLineQueryService;
import com.bhp.opusb.service.CBudgetPlanLineService;
import com.bhp.opusb.service.dto.CBudgetPlanLineCriteria;
import com.bhp.opusb.service.dto.CBudgetPlanLineDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CBudgetPlanLine}.
 */
@RestController
@RequestMapping("/api")
public class CBudgetPlanLineResource {

    private final Logger log = LoggerFactory.getLogger(CBudgetPlanLineResource.class);

    private static final String ENTITY_NAME = "cBudgetPlanLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBudgetPlanLineService cBudgetPlanLineService;

    private final CBudgetPlanLineQueryService cBudgetPlanLineQueryService;

    public CBudgetPlanLineResource(CBudgetPlanLineService cBudgetPlanLineService, CBudgetPlanLineQueryService cBudgetPlanLineQueryService) {
        this.cBudgetPlanLineService = cBudgetPlanLineService;
        this.cBudgetPlanLineQueryService = cBudgetPlanLineQueryService;
    }

    /**
     * {@code POST  /c-budget-plan-lines} : Create a new cBudgetPlanLine.
     *
     * @param cBudgetPlanLineDTO the cBudgetPlanLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBudgetPlanLineDTO, or with status {@code 400 (Bad Request)} if the cBudgetPlanLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-budget-plan-lines")
    public ResponseEntity<CBudgetPlanLineDTO> createCBudgetPlanLine(@Valid @RequestBody CBudgetPlanLineDTO cBudgetPlanLineDTO) throws URISyntaxException {
        log.debug("REST request to save CBudgetPlanLine : {}", cBudgetPlanLineDTO);
        if (cBudgetPlanLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBudgetPlanLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBudgetPlanLineDTO result = cBudgetPlanLineService.save(cBudgetPlanLineDTO);
        return ResponseEntity.created(new URI("/api/c-budget-plan-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-budget-plan-lines} : Updates an existing cBudgetPlanLine.
     *
     * @param cBudgetPlanLineDTO the cBudgetPlanLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBudgetPlanLineDTO,
     * or with status {@code 400 (Bad Request)} if the cBudgetPlanLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBudgetPlanLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-budget-plan-lines")
    public ResponseEntity<CBudgetPlanLineDTO> updateCBudgetPlanLine(@Valid @RequestBody CBudgetPlanLineDTO cBudgetPlanLineDTO) throws URISyntaxException {
        log.debug("REST request to update CBudgetPlanLine : {}", cBudgetPlanLineDTO);
        if (cBudgetPlanLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBudgetPlanLineDTO result = cBudgetPlanLineService.save(cBudgetPlanLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBudgetPlanLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-budget-plan-lines} : get all the cBudgetPlanLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBudgetPlanLines in body.
     */
    @GetMapping("/c-budget-plan-lines")
    public ResponseEntity<List<CBudgetPlanLineDTO>> getAllCBudgetPlanLines(CBudgetPlanLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBudgetPlanLines by criteria: {}", criteria);
        Page<CBudgetPlanLineDTO> page = cBudgetPlanLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-budget-plan-lines/count} : count all the cBudgetPlanLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-budget-plan-lines/count")
    public ResponseEntity<Long> countCBudgetPlanLines(CBudgetPlanLineCriteria criteria) {
        log.debug("REST request to count CBudgetPlanLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBudgetPlanLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-budget-plan-lines/:id} : get the "id" cBudgetPlanLine.
     *
     * @param id the id of the cBudgetPlanLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBudgetPlanLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-budget-plan-lines/{id}")
    public ResponseEntity<CBudgetPlanLineDTO> getCBudgetPlanLine(@PathVariable Long id) {
        log.debug("REST request to get CBudgetPlanLine : {}", id);
        Optional<CBudgetPlanLineDTO> cBudgetPlanLineDTO = cBudgetPlanLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBudgetPlanLineDTO);
    }

    /**
     * {@code DELETE  /c-budget-plan-lines/:id} : delete the "id" cBudgetPlanLine.
     *
     * @param id the id of the cBudgetPlanLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-budget-plan-lines/{id}")
    public ResponseEntity<Void> deleteCBudgetPlanLine(@PathVariable Long id) {
        log.debug("REST request to delete CBudgetPlanLine : {}", id);
        cBudgetPlanLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
