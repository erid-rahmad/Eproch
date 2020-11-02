package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CTaxQueryService;
import com.bhp.opusb.service.CTaxService;
import com.bhp.opusb.service.dto.CTaxCriteria;
import com.bhp.opusb.service.dto.CTaxDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CTax}.
 */
@RestController
@RequestMapping("/api")
public class CTaxResource {

    private final Logger log = LoggerFactory.getLogger(CTaxResource.class);

    private static final String ENTITY_NAME = "cTax";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CTaxService cTaxService;

    private final CTaxQueryService cTaxQueryService;

    public CTaxResource(CTaxService cTaxService, CTaxQueryService cTaxQueryService) {
        this.cTaxService = cTaxService;
        this.cTaxQueryService = cTaxQueryService;
    }

    /**
     * {@code POST  /c-taxes} : Create a new cTax.
     *
     * @param cTaxDTO the cTaxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cTaxDTO, or with status {@code 400 (Bad Request)} if the cTax has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-taxes")
    public ResponseEntity<CTaxDTO> createCTax(@Valid @RequestBody CTaxDTO cTaxDTO) throws URISyntaxException {
        log.debug("REST request to save CTax : {}", cTaxDTO);
        if (cTaxDTO.getId() != null) {
            throw new BadRequestAlertException("A new cTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CTaxDTO result = cTaxService.save(cTaxDTO);
        return ResponseEntity.created(new URI("/api/c-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-taxes} : Updates an existing cTax.
     *
     * @param cTaxDTO the cTaxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cTaxDTO,
     * or with status {@code 400 (Bad Request)} if the cTaxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cTaxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-taxes")
    public ResponseEntity<CTaxDTO> updateCTax(@Valid @RequestBody CTaxDTO cTaxDTO) throws URISyntaxException {
        log.debug("REST request to update CTax : {}", cTaxDTO);
        if (cTaxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CTaxDTO result = cTaxService.save(cTaxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cTaxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-taxes} : get all the cTaxes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cTaxes in body.
     */
    @GetMapping("/c-taxes")
    public ResponseEntity<List<CTaxDTO>> getAllCTaxes(CTaxCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CTaxes by criteria: {}", criteria);
        Page<CTaxDTO> page = cTaxQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-taxes/count} : count all the cTaxes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-taxes/count")
    public ResponseEntity<Long> countCTaxes(CTaxCriteria criteria) {
        log.debug("REST request to count CTaxes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cTaxQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-taxes/:id} : get the "id" cTax.
     *
     * @param id the id of the cTaxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cTaxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-taxes/{id}")
    public ResponseEntity<CTaxDTO> getCTax(@PathVariable Long id) {
        log.debug("REST request to get CTax : {}", id);
        Optional<CTaxDTO> cTaxDTO = cTaxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cTaxDTO);
    }

    /**
     * {@code DELETE  /c-taxes/:id} : delete the "id" cTax.
     *
     * @param id the id of the cTaxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-taxes/{id}")
    public ResponseEntity<Void> deleteCTax(@PathVariable Long id) {
        log.debug("REST request to delete CTax : {}", id);
        cTaxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
