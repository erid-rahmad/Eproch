package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CElementValueService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CElementValueDTO;
import com.bhp.opusb.service.dto.CElementValueCriteria;
import com.bhp.opusb.service.CElementValueQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CElementValue}.
 */
@RestController
@RequestMapping("/api")
public class CElementValueResource {

    private final Logger log = LoggerFactory.getLogger(CElementValueResource.class);

    private static final String ENTITY_NAME = "cElementValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CElementValueService cElementValueService;

    private final CElementValueQueryService cElementValueQueryService;

    public CElementValueResource(CElementValueService cElementValueService, CElementValueQueryService cElementValueQueryService) {
        this.cElementValueService = cElementValueService;
        this.cElementValueQueryService = cElementValueQueryService;
    }

    /**
     * {@code POST  /c-element-values} : Create a new cElementValue.
     *
     * @param cElementValueDTO the cElementValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cElementValueDTO, or with status {@code 400 (Bad Request)} if the cElementValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-element-values")
    public ResponseEntity<CElementValueDTO> createCElementValue(@Valid @RequestBody CElementValueDTO cElementValueDTO) throws URISyntaxException {
        log.debug("REST request to save CElementValue : {}", cElementValueDTO);
        if (cElementValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new cElementValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CElementValueDTO result = cElementValueService.save(cElementValueDTO);
        return ResponseEntity.created(new URI("/api/c-element-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-element-values} : Updates an existing cElementValue.
     *
     * @param cElementValueDTO the cElementValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cElementValueDTO,
     * or with status {@code 400 (Bad Request)} if the cElementValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cElementValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-element-values")
    public ResponseEntity<CElementValueDTO> updateCElementValue(@Valid @RequestBody CElementValueDTO cElementValueDTO) throws URISyntaxException {
        log.debug("REST request to update CElementValue : {}", cElementValueDTO);
        if (cElementValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CElementValueDTO result = cElementValueService.save(cElementValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cElementValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-element-values} : get all the cElementValues.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cElementValues in body.
     */
    @GetMapping("/c-element-values")
    public ResponseEntity<List<CElementValueDTO>> getAllCElementValues(CElementValueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CElementValues by criteria: {}", criteria);
        Page<CElementValueDTO> page = cElementValueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-element-values/count} : count all the cElementValues.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-element-values/count")
    public ResponseEntity<Long> countCElementValues(CElementValueCriteria criteria) {
        log.debug("REST request to count CElementValues by criteria: {}", criteria);
        return ResponseEntity.ok().body(cElementValueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-element-values/:id} : get the "id" cElementValue.
     *
     * @param id the id of the cElementValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cElementValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-element-values/{id}")
    public ResponseEntity<CElementValueDTO> getCElementValue(@PathVariable Long id) {
        log.debug("REST request to get CElementValue : {}", id);
        Optional<CElementValueDTO> cElementValueDTO = cElementValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cElementValueDTO);
    }

    /**
     * {@code DELETE  /c-element-values/:id} : delete the "id" cElementValue.
     *
     * @param id the id of the cElementValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-element-values/{id}")
    public ResponseEntity<Void> deleteCElementValue(@PathVariable Long id) {
        log.debug("REST request to delete CElementValue : {}", id);
        cElementValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
