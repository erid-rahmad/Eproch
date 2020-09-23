package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CProductClassificationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CProductClassificationDTO;
import com.bhp.opusb.service.dto.CProductClassificationCriteria;
import com.bhp.opusb.service.CProductClassificationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CProductClassification}.
 */
@RestController
@RequestMapping("/api")
public class CProductClassificationResource {

    private final Logger log = LoggerFactory.getLogger(CProductClassificationResource.class);

    private static final String ENTITY_NAME = "cProductClassification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CProductClassificationService cProductClassificationService;

    private final CProductClassificationQueryService cProductClassificationQueryService;

    public CProductClassificationResource(CProductClassificationService cProductClassificationService, CProductClassificationQueryService cProductClassificationQueryService) {
        this.cProductClassificationService = cProductClassificationService;
        this.cProductClassificationQueryService = cProductClassificationQueryService;
    }

    /**
     * {@code POST  /c-product-classifications} : Create a new cProductClassification.
     *
     * @param cProductClassificationDTO the cProductClassificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductClassificationDTO, or with status {@code 400 (Bad Request)} if the cProductClassification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-product-classifications")
    public ResponseEntity<CProductClassificationDTO> createCProductClassification(@Valid @RequestBody CProductClassificationDTO cProductClassificationDTO) throws URISyntaxException {
        log.debug("REST request to save CProductClassification : {}", cProductClassificationDTO);
        if (cProductClassificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new cProductClassification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CProductClassificationDTO result = cProductClassificationService.save(cProductClassificationDTO);
        return ResponseEntity.created(new URI("/api/c-product-classifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-product-classifications} : Updates an existing cProductClassification.
     *
     * @param cProductClassificationDTO the cProductClassificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cProductClassificationDTO,
     * or with status {@code 400 (Bad Request)} if the cProductClassificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cProductClassificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-product-classifications")
    public ResponseEntity<CProductClassificationDTO> updateCProductClassification(@Valid @RequestBody CProductClassificationDTO cProductClassificationDTO) throws URISyntaxException {
        log.debug("REST request to update CProductClassification : {}", cProductClassificationDTO);
        if (cProductClassificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CProductClassificationDTO result = cProductClassificationService.save(cProductClassificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cProductClassificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-product-classifications} : get all the cProductClassifications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cProductClassifications in body.
     */
    @GetMapping("/c-product-classifications")
    public ResponseEntity<List<CProductClassificationDTO>> getAllCProductClassifications(CProductClassificationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CProductClassifications by criteria: {}", criteria);
        Page<CProductClassificationDTO> page = cProductClassificationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-product-classifications/count} : count all the cProductClassifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-product-classifications/count")
    public ResponseEntity<Long> countCProductClassifications(CProductClassificationCriteria criteria) {
        log.debug("REST request to count CProductClassifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(cProductClassificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-product-classifications/:id} : get the "id" cProductClassification.
     *
     * @param id the id of the cProductClassificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cProductClassificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-product-classifications/{id}")
    public ResponseEntity<CProductClassificationDTO> getCProductClassification(@PathVariable Long id) {
        log.debug("REST request to get CProductClassification : {}", id);
        Optional<CProductClassificationDTO> cProductClassificationDTO = cProductClassificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cProductClassificationDTO);
    }

    /**
     * {@code DELETE  /c-product-classifications/:id} : delete the "id" cProductClassification.
     *
     * @param id the id of the cProductClassificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-product-classifications/{id}")
    public ResponseEntity<Void> deleteCProductClassification(@PathVariable Long id) {
        log.debug("REST request to delete CProductClassification : {}", id);
        cProductClassificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
