package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CCityService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CCityDTO;
import com.bhp.opusb.service.dto.CCityCriteria;
import com.bhp.opusb.service.CCityQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CCity}.
 */
@RestController
@RequestMapping("/api")
public class CCityResource {

    private final Logger log = LoggerFactory.getLogger(CCityResource.class);

    private static final String ENTITY_NAME = "cCity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCityService cCityService;

    private final CCityQueryService cCityQueryService;

    public CCityResource(CCityService cCityService, CCityQueryService cCityQueryService) {
        this.cCityService = cCityService;
        this.cCityQueryService = cCityQueryService;
    }

    /**
     * {@code POST  /c-cities} : Create a new cCity.
     *
     * @param cCityDTO the cCityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCityDTO, or with status {@code 400 (Bad Request)} if the cCity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-cities")
    public ResponseEntity<CCityDTO> createCCity(@Valid @RequestBody CCityDTO cCityDTO) throws URISyntaxException {
        log.debug("REST request to save CCity : {}", cCityDTO);
        if (cCityDTO.getId() != null) {
            throw new BadRequestAlertException("A new cCity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCityDTO result = cCityService.save(cCityDTO);
        return ResponseEntity.created(new URI("/api/c-cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-cities} : Updates an existing cCity.
     *
     * @param cCityDTO the cCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCityDTO,
     * or with status {@code 400 (Bad Request)} if the cCityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-cities")
    public ResponseEntity<CCityDTO> updateCCity(@Valid @RequestBody CCityDTO cCityDTO) throws URISyntaxException {
        log.debug("REST request to update CCity : {}", cCityDTO);
        if (cCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCityDTO result = cCityService.save(cCityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-cities} : get all the cCities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCities in body.
     */
    @GetMapping("/c-cities")
    public ResponseEntity<List<CCityDTO>> getAllCCities(CCityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CCities by criteria: {}", criteria);
        Page<CCityDTO> page = cCityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-cities/count} : count all the cCities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-cities/count")
    public ResponseEntity<Long> countCCities(CCityCriteria criteria) {
        log.debug("REST request to count CCities by criteria: {}", criteria);
        return ResponseEntity.ok().body(cCityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-cities/:id} : get the "id" cCity.
     *
     * @param id the id of the cCityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-cities/{id}")
    public ResponseEntity<CCityDTO> getCCity(@PathVariable Long id) {
        log.debug("REST request to get CCity : {}", id);
        Optional<CCityDTO> cCityDTO = cCityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCityDTO);
    }

    /**
     * {@code DELETE  /c-cities/:id} : delete the "id" cCity.
     *
     * @param id the id of the cCityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-cities/{id}")
    public ResponseEntity<Void> deleteCCity(@PathVariable Long id) {
        log.debug("REST request to delete CCity : {}", id);
        cCityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
