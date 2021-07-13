package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalMethodLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalMethodLineDTO;
import com.bhp.opusb.service.dto.CPrequalMethodLineCriteria;
import com.bhp.opusb.service.CPrequalMethodLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalMethodLine}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalMethodLineResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalMethodLineResource.class);

    private static final String ENTITY_NAME = "cPrequalMethodLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalMethodLineService cPrequalMethodLineService;

    private final CPrequalMethodLineQueryService cPrequalMethodLineQueryService;

    public CPrequalMethodLineResource(CPrequalMethodLineService cPrequalMethodLineService, CPrequalMethodLineQueryService cPrequalMethodLineQueryService) {
        this.cPrequalMethodLineService = cPrequalMethodLineService;
        this.cPrequalMethodLineQueryService = cPrequalMethodLineQueryService;
    }

    /**
     * {@code POST  /c-prequal-method-lines} : Create a new cPrequalMethodLine.
     *
     * @param cPrequalMethodLineDTO the cPrequalMethodLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalMethodLineDTO, or with status {@code 400 (Bad Request)} if the cPrequalMethodLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequal-method-lines")
    public ResponseEntity<CPrequalMethodLineDTO> createCPrequalMethodLine(@Valid @RequestBody CPrequalMethodLineDTO cPrequalMethodLineDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalMethodLine : {}", cPrequalMethodLineDTO);
        if (cPrequalMethodLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalMethodLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalMethodLineDTO result = cPrequalMethodLineService.save(cPrequalMethodLineDTO);
        return ResponseEntity.created(new URI("/api/c-prequal-method-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequal-method-lines} : Updates an existing cPrequalMethodLine.
     *
     * @param cPrequalMethodLineDTO the cPrequalMethodLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalMethodLineDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalMethodLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalMethodLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequal-method-lines")
    public ResponseEntity<CPrequalMethodLineDTO> updateCPrequalMethodLine(@Valid @RequestBody CPrequalMethodLineDTO cPrequalMethodLineDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalMethodLine : {}", cPrequalMethodLineDTO);
        if (cPrequalMethodLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalMethodLineDTO result = cPrequalMethodLineService.save(cPrequalMethodLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalMethodLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequal-method-lines} : get all the cPrequalMethodLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalMethodLines in body.
     */
    @GetMapping("/c-prequal-method-lines")
    public ResponseEntity<List<CPrequalMethodLineDTO>> getAllCPrequalMethodLines(CPrequalMethodLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalMethodLines by criteria: {}", criteria);
        Page<CPrequalMethodLineDTO> page = cPrequalMethodLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequal-method-lines/count} : count all the cPrequalMethodLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequal-method-lines/count")
    public ResponseEntity<Long> countCPrequalMethodLines(CPrequalMethodLineCriteria criteria) {
        log.debug("REST request to count CPrequalMethodLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalMethodLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequal-method-lines/:id} : get the "id" cPrequalMethodLine.
     *
     * @param id the id of the cPrequalMethodLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalMethodLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequal-method-lines/{id}")
    public ResponseEntity<CPrequalMethodLineDTO> getCPrequalMethodLine(@PathVariable Long id) {
        log.debug("REST request to get CPrequalMethodLine : {}", id);
        Optional<CPrequalMethodLineDTO> cPrequalMethodLineDTO = cPrequalMethodLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalMethodLineDTO);
    }

    /**
     * {@code DELETE  /c-prequal-method-lines/:id} : delete the "id" cPrequalMethodLine.
     *
     * @param id the id of the cPrequalMethodLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequal-method-lines/{id}")
    public ResponseEntity<Void> deleteCPrequalMethodLine(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalMethodLine : {}", id);
        cPrequalMethodLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
