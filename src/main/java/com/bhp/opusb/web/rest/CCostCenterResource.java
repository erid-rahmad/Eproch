package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CCostCenterService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CCostCenterDTO;
import com.bhp.opusb.service.dto.CCostCenterCriteria;
import com.bhp.opusb.service.CCostCenterQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CCostCenter}.
 */
@RestController
@RequestMapping("/api")
public class CCostCenterResource {

    private final Logger log = LoggerFactory.getLogger(CCostCenterResource.class);

    private static final String ENTITY_NAME = "cCostCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCostCenterService cCostCenterService;

    private final CCostCenterQueryService cCostCenterQueryService;

    public CCostCenterResource(CCostCenterService cCostCenterService, CCostCenterQueryService cCostCenterQueryService) {
        this.cCostCenterService = cCostCenterService;
        this.cCostCenterQueryService = cCostCenterQueryService;
    }

    /**
     * {@code POST  /c-cost-centers} : Create a new cCostCenter.
     *
     * @param cCostCenterDTO the cCostCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCostCenterDTO, or with status {@code 400 (Bad Request)} if the cCostCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-cost-centers")
    public ResponseEntity<CCostCenterDTO> createCCostCenter(@Valid @RequestBody CCostCenterDTO cCostCenterDTO) throws URISyntaxException {
        log.debug("REST request to save CCostCenter : {}", cCostCenterDTO);
        if (cCostCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCostCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCostCenterDTO result = cCostCenterService.save(cCostCenterDTO);
        return ResponseEntity.created(new URI("/api/c-cost-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-cost-centers} : Updates an existing cCostCenter.
     *
     * @param cCostCenterDTO the cCostCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCostCenterDTO,
     * or with status {@code 400 (Bad Request)} if the cCostCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCostCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-cost-centers")
    public ResponseEntity<CCostCenterDTO> updateCCostCenter(@Valid @RequestBody CCostCenterDTO cCostCenterDTO) throws URISyntaxException {
        log.debug("REST request to update CCostCenter : {}", cCostCenterDTO);
        if (cCostCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCostCenterDTO result = cCostCenterService.save(cCostCenterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCostCenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-cost-centers} : get all the cCostCenters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCostCenters in body.
     */
    @GetMapping("/c-cost-centers")
    public ResponseEntity<List<CCostCenterDTO>> getAllCCostCenters(CCostCenterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CCostCenters by criteria: {}", criteria);
        Page<CCostCenterDTO> page = cCostCenterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-cost-centers/count} : count all the cCostCenters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-cost-centers/count")
    public ResponseEntity<Long> countCCostCenters(CCostCenterCriteria criteria) {
        log.debug("REST request to count CCostCenters by criteria: {}", criteria);
        return ResponseEntity.ok().body(cCostCenterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-cost-centers/:id} : get the "id" cCostCenter.
     *
     * @param id the id of the cCostCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCostCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-cost-centers/{id}")
    public ResponseEntity<CCostCenterDTO> getCCostCenter(@PathVariable Long id) {
        log.debug("REST request to get CCostCenter : {}", id);
        Optional<CCostCenterDTO> cCostCenterDTO = cCostCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCostCenterDTO);
    }

    /**
     * {@code DELETE  /c-cost-centers/:id} : delete the "id" cCostCenter.
     *
     * @param id the id of the cCostCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-cost-centers/{id}")
    public ResponseEntity<Void> deleteCCostCenter(@PathVariable Long id) {
        log.debug("REST request to delete CCostCenter : {}", id);
        cCostCenterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
