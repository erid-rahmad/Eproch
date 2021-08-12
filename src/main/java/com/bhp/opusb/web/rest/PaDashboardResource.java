package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.PaDashboardService;
import com.bhp.opusb.service.dto.DashboardChartDTO;
import com.bhp.opusb.service.dto.DashboardMyDocument;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.PaDashboardDTO;
import com.bhp.opusb.service.dto.PaDashboardCriteria;
import com.bhp.opusb.service.PaDashboardQueryService;

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
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bhp.opusb.domain.PaDashboard}.
 */
@RestController
@RequestMapping("/api")
public class PaDashboardResource {

    private final Logger log = LoggerFactory.getLogger(PaDashboardResource.class);

    private static final String ENTITY_NAME = "paDashboard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaDashboardService paDashboardService;

    private final PaDashboardQueryService paDashboardQueryService;

    public PaDashboardResource(PaDashboardService paDashboardService, PaDashboardQueryService paDashboardQueryService) {
        this.paDashboardService = paDashboardService;
        this.paDashboardQueryService = paDashboardQueryService;
    }

    /**
     * {@code POST  /pa-dashboards} : Create a new paDashboard.
     *
     * @param paDashboardDTO the paDashboardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paDashboardDTO, or with status {@code 400 (Bad Request)} if the paDashboard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pa-dashboards")
    public ResponseEntity<PaDashboardDTO> createPaDashboard(@Valid @RequestBody PaDashboardDTO paDashboardDTO) throws URISyntaxException {
        log.debug("REST request to save PaDashboard : {}", paDashboardDTO);
        if (paDashboardDTO.getId() != null) {
            throw new BadRequestAlertException("A new paDashboard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaDashboardDTO result = paDashboardService.save(paDashboardDTO);
        return ResponseEntity.created(new URI("/api/pa-dashboards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pa-dashboards} : Updates an existing paDashboard.
     *
     * @param paDashboardDTO the paDashboardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paDashboardDTO,
     * or with status {@code 400 (Bad Request)} if the paDashboardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paDashboardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pa-dashboards")
    public ResponseEntity<PaDashboardDTO> updatePaDashboard(@Valid @RequestBody PaDashboardDTO paDashboardDTO) throws URISyntaxException {
        log.debug("REST request to update PaDashboard : {}", paDashboardDTO);
        if (paDashboardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaDashboardDTO result = paDashboardService.save(paDashboardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paDashboardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pa-dashboards} : get all the paDashboards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paDashboards in body.
     */
    @GetMapping("/pa-dashboards")
    public ResponseEntity<List<PaDashboardDTO>> getAllPaDashboards(PaDashboardCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaDashboards by criteria: {}", criteria);
        Page<PaDashboardDTO> page = paDashboardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/pa-dashboards/mydocument")
    public ResponseEntity<List<DashboardMyDocument>> getAllPaDashboards() {
        return ResponseEntity.ok().body(paDashboardService.findAllMyDocument());
    }

    @GetMapping("/pa-dashboards/spendByCostCenter")
    public ResponseEntity<List<DashboardChartDTO>> spendByCostCenter() {
        //return paDashboardService.SpendByCostCenter();
        return ResponseEntity.ok().body(paDashboardQueryService.getSpendByCostCtr());
    }

    @GetMapping("/pa-dashboards/prodPurchaseAmount")
    public ResponseEntity<List<DashboardChartDTO>> prodPurchaseAmount() {
        return ResponseEntity.ok().body(paDashboardQueryService.getProdPurchaseAmount());
    }

    @GetMapping("/pa-dashboards/vendorConfirmation")
    public Map<String, Object> vendorConfirmation() {
        return paDashboardService.vendorConfirmation();

    }

    @GetMapping("/pa-dashboards/topVendorPurchase")
    public List<MPurchaseOrderDTO> topVendorPurchase() {
        return paDashboardService.topVendorPurchase();

    }

    /**
     * {@code GET  /pa-dashboards/count} : count all the paDashboards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pa-dashboards/count")
    public ResponseEntity<Long> countPaDashboards(PaDashboardCriteria criteria) {
        log.debug("REST request to count PaDashboards by criteria: {}", criteria);
        return ResponseEntity.ok().body(paDashboardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pa-dashboards/:id} : get the "id" paDashboard.
     *
     * @param id the id of the paDashboardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paDashboardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pa-dashboards/{id}")
    public ResponseEntity<PaDashboardDTO> getPaDashboard(@PathVariable Long id) {
        log.debug("REST request to get PaDashboard : {}", id);
        Optional<PaDashboardDTO> paDashboardDTO = paDashboardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paDashboardDTO);
    }

    /**
     * {@code DELETE  /pa-dashboards/:id} : delete the "id" paDashboard.
     *
     * @param id the id of the paDashboardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pa-dashboards/{id}")
    public ResponseEntity<Void> deletePaDashboard(@PathVariable Long id) {
        log.debug("REST request to delete PaDashboard : {}", id);
        paDashboardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
