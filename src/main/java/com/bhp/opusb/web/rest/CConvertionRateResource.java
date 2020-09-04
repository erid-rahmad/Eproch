package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CConvertionRateService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CConvertionRateDTO;
import com.bhp.opusb.service.dto.CConvertionRateCriteria;
import com.bhp.opusb.service.CConvertionRateQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CConvertionRate}.
 */
@RestController
@RequestMapping("/api")
public class CConvertionRateResource {

    private final Logger log = LoggerFactory.getLogger(CConvertionRateResource.class);

    private static final String ENTITY_NAME = "cConvertionRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CConvertionRateService cConvertionRateService;

    private final CConvertionRateQueryService cConvertionRateQueryService;

    public CConvertionRateResource(CConvertionRateService cConvertionRateService, CConvertionRateQueryService cConvertionRateQueryService) {
        this.cConvertionRateService = cConvertionRateService;
        this.cConvertionRateQueryService = cConvertionRateQueryService;
    }

    /**
     * {@code POST  /c-convertion-rates} : Create a new cConvertionRate.
     *
     * @param cConvertionRateDTO the cConvertionRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cConvertionRateDTO, or with status {@code 400 (Bad Request)} if the cConvertionRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-convertion-rates")
    public ResponseEntity<CConvertionRateDTO> createCConvertionRate(@Valid @RequestBody CConvertionRateDTO cConvertionRateDTO) throws URISyntaxException {
        log.debug("REST request to save CConvertionRate : {}", cConvertionRateDTO);
        if (cConvertionRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new cConvertionRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CConvertionRateDTO result = cConvertionRateService.save(cConvertionRateDTO);
        return ResponseEntity.created(new URI("/api/c-convertion-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-convertion-rates} : Updates an existing cConvertionRate.
     *
     * @param cConvertionRateDTO the cConvertionRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cConvertionRateDTO,
     * or with status {@code 400 (Bad Request)} if the cConvertionRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cConvertionRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-convertion-rates")
    public ResponseEntity<CConvertionRateDTO> updateCConvertionRate(@Valid @RequestBody CConvertionRateDTO cConvertionRateDTO) throws URISyntaxException {
        log.debug("REST request to update CConvertionRate : {}", cConvertionRateDTO);
        if (cConvertionRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CConvertionRateDTO result = cConvertionRateService.save(cConvertionRateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cConvertionRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-convertion-rates} : get all the cConvertionRates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cConvertionRates in body.
     */
    @GetMapping("/c-convertion-rates")
    public ResponseEntity<List<CConvertionRateDTO>> getAllCConvertionRates(CConvertionRateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CConvertionRates by criteria: {}", criteria);
        Page<CConvertionRateDTO> page = cConvertionRateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-convertion-rates/count} : count all the cConvertionRates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-convertion-rates/count")
    public ResponseEntity<Long> countCConvertionRates(CConvertionRateCriteria criteria) {
        log.debug("REST request to count CConvertionRates by criteria: {}", criteria);
        return ResponseEntity.ok().body(cConvertionRateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-convertion-rates/:id} : get the "id" cConvertionRate.
     *
     * @param id the id of the cConvertionRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cConvertionRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-convertion-rates/{id}")
    public ResponseEntity<CConvertionRateDTO> getCConvertionRate(@PathVariable Long id) {
        log.debug("REST request to get CConvertionRate : {}", id);
        Optional<CConvertionRateDTO> cConvertionRateDTO = cConvertionRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cConvertionRateDTO);
    }

    /**
     * {@code DELETE  /c-convertion-rates/:id} : delete the "id" cConvertionRate.
     *
     * @param id the id of the cConvertionRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-convertion-rates/{id}")
    public ResponseEntity<Void> deleteCConvertionRate(@PathVariable Long id) {
        log.debug("REST request to delete CConvertionRate : {}", id);
        cConvertionRateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
