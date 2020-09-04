package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CNonBusinessDayService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CNonBusinessDayDTO;
import com.bhp.opusb.service.dto.CNonBusinessDayCriteria;
import com.bhp.opusb.service.CNonBusinessDayQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CNonBusinessDay}.
 */
@RestController
@RequestMapping("/api")
public class CNonBusinessDayResource {

    private final Logger log = LoggerFactory.getLogger(CNonBusinessDayResource.class);

    private static final String ENTITY_NAME = "cNonBusinessDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CNonBusinessDayService cNonBusinessDayService;

    private final CNonBusinessDayQueryService cNonBusinessDayQueryService;

    public CNonBusinessDayResource(CNonBusinessDayService cNonBusinessDayService, CNonBusinessDayQueryService cNonBusinessDayQueryService) {
        this.cNonBusinessDayService = cNonBusinessDayService;
        this.cNonBusinessDayQueryService = cNonBusinessDayQueryService;
    }

    /**
     * {@code POST  /c-non-business-days} : Create a new cNonBusinessDay.
     *
     * @param cNonBusinessDayDTO the cNonBusinessDayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cNonBusinessDayDTO, or with status {@code 400 (Bad Request)} if the cNonBusinessDay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-non-business-days")
    public ResponseEntity<CNonBusinessDayDTO> createCNonBusinessDay(@Valid @RequestBody CNonBusinessDayDTO cNonBusinessDayDTO) throws URISyntaxException {
        log.debug("REST request to save CNonBusinessDay : {}", cNonBusinessDayDTO);
        if (cNonBusinessDayDTO.getId() != null) {
            throw new BadRequestAlertException("A new cNonBusinessDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CNonBusinessDayDTO result = cNonBusinessDayService.save(cNonBusinessDayDTO);
        return ResponseEntity.created(new URI("/api/c-non-business-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-non-business-days} : Updates an existing cNonBusinessDay.
     *
     * @param cNonBusinessDayDTO the cNonBusinessDayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cNonBusinessDayDTO,
     * or with status {@code 400 (Bad Request)} if the cNonBusinessDayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cNonBusinessDayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-non-business-days")
    public ResponseEntity<CNonBusinessDayDTO> updateCNonBusinessDay(@Valid @RequestBody CNonBusinessDayDTO cNonBusinessDayDTO) throws URISyntaxException {
        log.debug("REST request to update CNonBusinessDay : {}", cNonBusinessDayDTO);
        if (cNonBusinessDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CNonBusinessDayDTO result = cNonBusinessDayService.save(cNonBusinessDayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cNonBusinessDayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-non-business-days} : get all the cNonBusinessDays.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cNonBusinessDays in body.
     */
    @GetMapping("/c-non-business-days")
    public ResponseEntity<List<CNonBusinessDayDTO>> getAllCNonBusinessDays(CNonBusinessDayCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CNonBusinessDays by criteria: {}", criteria);
        Page<CNonBusinessDayDTO> page = cNonBusinessDayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-non-business-days/count} : count all the cNonBusinessDays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-non-business-days/count")
    public ResponseEntity<Long> countCNonBusinessDays(CNonBusinessDayCriteria criteria) {
        log.debug("REST request to count CNonBusinessDays by criteria: {}", criteria);
        return ResponseEntity.ok().body(cNonBusinessDayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-non-business-days/:id} : get the "id" cNonBusinessDay.
     *
     * @param id the id of the cNonBusinessDayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cNonBusinessDayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-non-business-days/{id}")
    public ResponseEntity<CNonBusinessDayDTO> getCNonBusinessDay(@PathVariable Long id) {
        log.debug("REST request to get CNonBusinessDay : {}", id);
        Optional<CNonBusinessDayDTO> cNonBusinessDayDTO = cNonBusinessDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cNonBusinessDayDTO);
    }

    /**
     * {@code DELETE  /c-non-business-days/:id} : delete the "id" cNonBusinessDay.
     *
     * @param id the id of the cNonBusinessDayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-non-business-days/{id}")
    public ResponseEntity<Void> deleteCNonBusinessDay(@PathVariable Long id) {
        log.debug("REST request to delete CNonBusinessDay : {}", id);
        cNonBusinessDayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
