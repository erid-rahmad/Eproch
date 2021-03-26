package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CBudgetPlanQueryService;
import com.bhp.opusb.service.CBudgetPlanService;
import com.bhp.opusb.service.dto.CBudgetPlanCriteria;
import com.bhp.opusb.service.dto.CBudgetPlanDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CBudgetPlan}.
 */
@RestController
@RequestMapping("/api")
public class CBudgetPlanResource {

    private final Logger log = LoggerFactory.getLogger(CBudgetPlanResource.class);

    private static final String ENTITY_NAME = "cBudgetPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBudgetPlanService cBudgetPlanService;

    private final CBudgetPlanQueryService cBudgetPlanQueryService;

    public CBudgetPlanResource(CBudgetPlanService cBudgetPlanService, CBudgetPlanQueryService cBudgetPlanQueryService) {
        this.cBudgetPlanService = cBudgetPlanService;
        this.cBudgetPlanQueryService = cBudgetPlanQueryService;
    }

    /**
     * {@code POST  /c-budget-plans} : Create a new cBudgetPlan.
     *
     * @param cBudgetPlanDTO the cBudgetPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBudgetPlanDTO, or with status {@code 400 (Bad Request)} if the cBudgetPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-budget-plans")
    public ResponseEntity<CBudgetPlanDTO> createCBudgetPlan(@Valid @RequestBody CBudgetPlanDTO cBudgetPlanDTO) throws URISyntaxException {
        log.debug("REST request to save CBudgetPlan : {}", cBudgetPlanDTO);
        if (cBudgetPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBudgetPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBudgetPlanDTO result = cBudgetPlanService.save(cBudgetPlanDTO);
        return ResponseEntity.created(new URI("/api/c-budget-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-budget-plans} : Updates an existing cBudgetPlan.
     *
     * @param cBudgetPlanDTO the cBudgetPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBudgetPlanDTO,
     * or with status {@code 400 (Bad Request)} if the cBudgetPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBudgetPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-budget-plans")
    public ResponseEntity<CBudgetPlanDTO> updateCBudgetPlan(@Valid @RequestBody CBudgetPlanDTO cBudgetPlanDTO) throws URISyntaxException {
        log.debug("REST request to update CBudgetPlan : {}", cBudgetPlanDTO);
        if (cBudgetPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBudgetPlanDTO result = cBudgetPlanService.save(cBudgetPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBudgetPlanDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-budget-plans} : get all the cBudgetPlans.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBudgetPlans in body.
     */
    @GetMapping("/c-budget-plans")
    public ResponseEntity<List<CBudgetPlanDTO>> getAllCBudgetPlans(CBudgetPlanCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBudgetPlans by criteria: {}", criteria);
        Page<CBudgetPlanDTO> page = cBudgetPlanQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-budget-plans/count} : count all the cBudgetPlans.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-budget-plans/count")
    public ResponseEntity<Long> countCBudgetPlans(CBudgetPlanCriteria criteria) {
        log.debug("REST request to count CBudgetPlans by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBudgetPlanQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-budget-plans/:id} : get the "id" cBudgetPlan.
     *
     * @param id the id of the cBudgetPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBudgetPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-budget-plans/{id}")
    public ResponseEntity<CBudgetPlanDTO> getCBudgetPlan(@PathVariable Long id) {
        log.debug("REST request to get CBudgetPlan : {}", id);
        Optional<CBudgetPlanDTO> cBudgetPlanDTO = cBudgetPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBudgetPlanDTO);
    }

    /**
     * {@code DELETE  /c-budget-plans/:id} : delete the "id" cBudgetPlan.
     *
     * @param id the id of the cBudgetPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-budget-plans/{id}")
    public ResponseEntity<Void> deleteCBudgetPlan(@PathVariable Long id) {
        log.debug("REST request to delete CBudgetPlan : {}", id);
        cBudgetPlanService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
