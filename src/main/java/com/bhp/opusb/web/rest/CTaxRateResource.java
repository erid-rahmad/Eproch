package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CTaxRateService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CTaxRateDTO;
import com.bhp.opusb.service.dto.CTaxRateCriteria;
import com.bhp.opusb.service.CTaxRateQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CTaxRate}.
 */
@RestController
@RequestMapping("/api")
public class CTaxRateResource {

    private final Logger log = LoggerFactory.getLogger(CTaxRateResource.class);

    private static final String ENTITY_NAME = "cTaxRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CTaxRateService cTaxRateService;

    private final CTaxRateQueryService cTaxRateQueryService;

    public CTaxRateResource(CTaxRateService cTaxRateService, CTaxRateQueryService cTaxRateQueryService) {
        this.cTaxRateService = cTaxRateService;
        this.cTaxRateQueryService = cTaxRateQueryService;
    }

    /**
     * {@code POST  /c-tax-rates} : Create a new cTaxRate.
     *
     * @param cTaxRateDTO the cTaxRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cTaxRateDTO, or with status {@code 400 (Bad Request)} if the cTaxRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-tax-rates")
    public ResponseEntity<CTaxRateDTO> createCTaxRate(@Valid @RequestBody CTaxRateDTO cTaxRateDTO) throws URISyntaxException {
        log.debug("REST request to save CTaxRate : {}", cTaxRateDTO);
        if (cTaxRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new cTaxRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CTaxRateDTO result = cTaxRateService.save(cTaxRateDTO);
        return ResponseEntity.created(new URI("/api/c-tax-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-tax-rates} : Updates an existing cTaxRate.
     *
     * @param cTaxRateDTO the cTaxRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cTaxRateDTO,
     * or with status {@code 400 (Bad Request)} if the cTaxRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cTaxRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-tax-rates")
    public ResponseEntity<CTaxRateDTO> updateCTaxRate(@Valid @RequestBody CTaxRateDTO cTaxRateDTO) throws URISyntaxException {
        log.debug("REST request to update CTaxRate : {}", cTaxRateDTO);
        if (cTaxRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CTaxRateDTO result = cTaxRateService.save(cTaxRateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cTaxRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-tax-rates} : get all the cTaxRates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cTaxRates in body.
     */
    @GetMapping("/c-tax-rates")
    public ResponseEntity<List<CTaxRateDTO>> getAllCTaxRates(CTaxRateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CTaxRates by criteria: {}", criteria);
        Page<CTaxRateDTO> page = cTaxRateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-tax-rates/count} : count all the cTaxRates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-tax-rates/count")
    public ResponseEntity<Long> countCTaxRates(CTaxRateCriteria criteria) {
        log.debug("REST request to count CTaxRates by criteria: {}", criteria);
        return ResponseEntity.ok().body(cTaxRateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-tax-rates/:id} : get the "id" cTaxRate.
     *
     * @param id the id of the cTaxRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cTaxRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-tax-rates/{id}")
    public ResponseEntity<CTaxRateDTO> getCTaxRate(@PathVariable Long id) {
        log.debug("REST request to get CTaxRate : {}", id);
        Optional<CTaxRateDTO> cTaxRateDTO = cTaxRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cTaxRateDTO);
    }

    /**
     * {@code DELETE  /c-tax-rates/:id} : delete the "id" cTaxRate.
     *
     * @param id the id of the cTaxRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-tax-rates/{id}")
    public ResponseEntity<Void> deleteCTaxRate(@PathVariable Long id) {
        log.debug("REST request to delete CTaxRate : {}", id);
        cTaxRateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
