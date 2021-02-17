package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.PaDashboardItemService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.PaDashboardItemDTO;
import com.bhp.opusb.service.dto.PaDashboardItemCriteria;
import com.bhp.opusb.service.PaDashboardItemQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.PaDashboardItem}.
 */
@RestController
@RequestMapping("/api")
public class PaDashboardItemResource {

    private final Logger log = LoggerFactory.getLogger(PaDashboardItemResource.class);

    private static final String ENTITY_NAME = "paDashboardItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaDashboardItemService paDashboardItemService;

    private final PaDashboardItemQueryService paDashboardItemQueryService;

    public PaDashboardItemResource(PaDashboardItemService paDashboardItemService, PaDashboardItemQueryService paDashboardItemQueryService) {
        this.paDashboardItemService = paDashboardItemService;
        this.paDashboardItemQueryService = paDashboardItemQueryService;
    }

    /**
     * {@code POST  /pa-dashboard-items} : Create a new paDashboardItem.
     *
     * @param paDashboardItemDTO the paDashboardItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paDashboardItemDTO, or with status {@code 400 (Bad Request)} if the paDashboardItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pa-dashboard-items")
    public ResponseEntity<PaDashboardItemDTO> createPaDashboardItem(@Valid @RequestBody PaDashboardItemDTO paDashboardItemDTO) throws URISyntaxException {
        log.debug("REST request to save PaDashboardItem : {}", paDashboardItemDTO);
        if (paDashboardItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new paDashboardItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaDashboardItemDTO result = paDashboardItemService.save(paDashboardItemDTO);
        return ResponseEntity.created(new URI("/api/pa-dashboard-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pa-dashboard-items} : Updates an existing paDashboardItem.
     *
     * @param paDashboardItemDTO the paDashboardItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paDashboardItemDTO,
     * or with status {@code 400 (Bad Request)} if the paDashboardItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paDashboardItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pa-dashboard-items")
    public ResponseEntity<PaDashboardItemDTO> updatePaDashboardItem(@Valid @RequestBody PaDashboardItemDTO paDashboardItemDTO) throws URISyntaxException {
        log.debug("REST request to update PaDashboardItem : {}", paDashboardItemDTO);
        if (paDashboardItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaDashboardItemDTO result = paDashboardItemService.save(paDashboardItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paDashboardItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pa-dashboard-items} : get all the paDashboardItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paDashboardItems in body.
     */
    @GetMapping("/pa-dashboard-items")
    public ResponseEntity<List<PaDashboardItemDTO>> getAllPaDashboardItems(PaDashboardItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaDashboardItems by criteria: {}", criteria);
        Page<PaDashboardItemDTO> page = paDashboardItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pa-dashboard-items/count} : count all the paDashboardItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pa-dashboard-items/count")
    public ResponseEntity<Long> countPaDashboardItems(PaDashboardItemCriteria criteria) {
        log.debug("REST request to count PaDashboardItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(paDashboardItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pa-dashboard-items/:id} : get the "id" paDashboardItem.
     *
     * @param id the id of the paDashboardItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paDashboardItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pa-dashboard-items/{id}")
    public ResponseEntity<PaDashboardItemDTO> getPaDashboardItem(@PathVariable Long id) {
        log.debug("REST request to get PaDashboardItem : {}", id);
        Optional<PaDashboardItemDTO> paDashboardItemDTO = paDashboardItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paDashboardItemDTO);
    }

    /**
     * {@code DELETE  /pa-dashboard-items/:id} : delete the "id" paDashboardItem.
     *
     * @param id the id of the paDashboardItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pa-dashboard-items/{id}")
    public ResponseEntity<Void> deletePaDashboardItem(@PathVariable Long id) {
        log.debug("REST request to delete PaDashboardItem : {}", id);
        paDashboardItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
