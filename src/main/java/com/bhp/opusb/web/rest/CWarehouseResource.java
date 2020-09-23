package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CWarehouseService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CWarehouseDTO;
import com.bhp.opusb.service.dto.CWarehouseCriteria;
import com.bhp.opusb.service.CWarehouseQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CWarehouse}.
 */
@RestController
@RequestMapping("/api")
public class CWarehouseResource {

    private final Logger log = LoggerFactory.getLogger(CWarehouseResource.class);

    private static final String ENTITY_NAME = "cWarehouse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CWarehouseService cWarehouseService;

    private final CWarehouseQueryService cWarehouseQueryService;

    public CWarehouseResource(CWarehouseService cWarehouseService, CWarehouseQueryService cWarehouseQueryService) {
        this.cWarehouseService = cWarehouseService;
        this.cWarehouseQueryService = cWarehouseQueryService;
    }

    /**
     * {@code POST  /c-warehouses} : Create a new cWarehouse.
     *
     * @param cWarehouseDTO the cWarehouseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cWarehouseDTO, or with status {@code 400 (Bad Request)} if the cWarehouse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-warehouses")
    public ResponseEntity<CWarehouseDTO> createCWarehouse(@Valid @RequestBody CWarehouseDTO cWarehouseDTO) throws URISyntaxException {
        log.debug("REST request to save CWarehouse : {}", cWarehouseDTO);
        if (cWarehouseDTO.getId() != null) {
            throw new BadRequestAlertException("A new cWarehouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CWarehouseDTO result = cWarehouseService.save(cWarehouseDTO);
        return ResponseEntity.created(new URI("/api/c-warehouses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-warehouses} : Updates an existing cWarehouse.
     *
     * @param cWarehouseDTO the cWarehouseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cWarehouseDTO,
     * or with status {@code 400 (Bad Request)} if the cWarehouseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cWarehouseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-warehouses")
    public ResponseEntity<CWarehouseDTO> updateCWarehouse(@Valid @RequestBody CWarehouseDTO cWarehouseDTO) throws URISyntaxException {
        log.debug("REST request to update CWarehouse : {}", cWarehouseDTO);
        if (cWarehouseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CWarehouseDTO result = cWarehouseService.save(cWarehouseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cWarehouseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-warehouses} : get all the cWarehouses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cWarehouses in body.
     */
    @GetMapping("/c-warehouses")
    public ResponseEntity<List<CWarehouseDTO>> getAllCWarehouses(CWarehouseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CWarehouses by criteria: {}", criteria);
        Page<CWarehouseDTO> page = cWarehouseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-warehouses/count} : count all the cWarehouses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-warehouses/count")
    public ResponseEntity<Long> countCWarehouses(CWarehouseCriteria criteria) {
        log.debug("REST request to count CWarehouses by criteria: {}", criteria);
        return ResponseEntity.ok().body(cWarehouseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-warehouses/:id} : get the "id" cWarehouse.
     *
     * @param id the id of the cWarehouseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cWarehouseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-warehouses/{id}")
    public ResponseEntity<CWarehouseDTO> getCWarehouse(@PathVariable Long id) {
        log.debug("REST request to get CWarehouse : {}", id);
        Optional<CWarehouseDTO> cWarehouseDTO = cWarehouseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cWarehouseDTO);
    }

    /**
     * {@code DELETE  /c-warehouses/:id} : delete the "id" cWarehouse.
     *
     * @param id the id of the cWarehouseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-warehouses/{id}")
    public ResponseEntity<Void> deleteCWarehouse(@PathVariable Long id) {
        log.debug("REST request to delete CWarehouse : {}", id);
        cWarehouseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
