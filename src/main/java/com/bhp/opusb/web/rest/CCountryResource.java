package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CCountryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CCountryDTO;
import com.bhp.opusb.service.dto.CCountryCriteria;
import com.bhp.opusb.service.CCountryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CCountry}.
 */
@RestController
@RequestMapping("/api")
public class CCountryResource {

    private final Logger log = LoggerFactory.getLogger(CCountryResource.class);

    private static final String ENTITY_NAME = "cCountry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCountryService cCountryService;

    private final CCountryQueryService cCountryQueryService;

    public CCountryResource(CCountryService cCountryService, CCountryQueryService cCountryQueryService) {
        this.cCountryService = cCountryService;
        this.cCountryQueryService = cCountryQueryService;
    }

    /**
     * {@code POST  /c-countries} : Create a new cCountry.
     *
     * @param cCountryDTO the cCountryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCountryDTO, or with status {@code 400 (Bad Request)} if the cCountry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-countries")
    public ResponseEntity<CCountryDTO> createCCountry(@Valid @RequestBody CCountryDTO cCountryDTO) throws URISyntaxException {
        log.debug("REST request to save CCountry : {}", cCountryDTO);
        if (cCountryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCountry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCountryDTO result = cCountryService.save(cCountryDTO);
        return ResponseEntity.created(new URI("/api/c-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-countries} : Updates an existing cCountry.
     *
     * @param cCountryDTO the cCountryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCountryDTO,
     * or with status {@code 400 (Bad Request)} if the cCountryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCountryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-countries")
    public ResponseEntity<CCountryDTO> updateCCountry(@Valid @RequestBody CCountryDTO cCountryDTO) throws URISyntaxException {
        log.debug("REST request to update CCountry : {}", cCountryDTO);
        if (cCountryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCountryDTO result = cCountryService.save(cCountryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCountryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-countries} : get all the cCountries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCountries in body.
     */
    @GetMapping("/c-countries")
    public ResponseEntity<List<CCountryDTO>> getAllCCountries(CCountryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CCountries by criteria: {}", criteria);
        Page<CCountryDTO> page = cCountryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-countries/count} : count all the cCountries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-countries/count")
    public ResponseEntity<Long> countCCountries(CCountryCriteria criteria) {
        log.debug("REST request to count CCountries by criteria: {}", criteria);
        return ResponseEntity.ok().body(cCountryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-countries/:id} : get the "id" cCountry.
     *
     * @param id the id of the cCountryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCountryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-countries/{id}")
    public ResponseEntity<CCountryDTO> getCCountry(@PathVariable Long id) {
        log.debug("REST request to get CCountry : {}", id);
        Optional<CCountryDTO> cCountryDTO = cCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCountryDTO);
    }

    /**
     * {@code DELETE  /c-countries/:id} : delete the "id" cCountry.
     *
     * @param id the id of the cCountryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-countries/{id}")
    public ResponseEntity<Void> deleteCCountry(@PathVariable Long id) {
        log.debug("REST request to delete CCountry : {}", id);
        cCountryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
