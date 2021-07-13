package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPrequalificationMethodService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPrequalificationMethodDTO;
import com.bhp.opusb.service.dto.CPrequalificationMethodCriteria;
import com.bhp.opusb.service.CPrequalificationMethodQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPrequalificationMethod}.
 */
@RestController
@RequestMapping("/api")
public class CPrequalificationMethodResource {

    private final Logger log = LoggerFactory.getLogger(CPrequalificationMethodResource.class);

    private static final String ENTITY_NAME = "cPrequalificationMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPrequalificationMethodService cPrequalificationMethodService;

    private final CPrequalificationMethodQueryService cPrequalificationMethodQueryService;

    public CPrequalificationMethodResource(CPrequalificationMethodService cPrequalificationMethodService, CPrequalificationMethodQueryService cPrequalificationMethodQueryService) {
        this.cPrequalificationMethodService = cPrequalificationMethodService;
        this.cPrequalificationMethodQueryService = cPrequalificationMethodQueryService;
    }

    /**
     * {@code POST  /c-prequalification-methods} : Create a new cPrequalificationMethod.
     *
     * @param cPrequalificationMethodDTO the cPrequalificationMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPrequalificationMethodDTO, or with status {@code 400 (Bad Request)} if the cPrequalificationMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-prequalification-methods")
    public ResponseEntity<CPrequalificationMethodDTO> createCPrequalificationMethod(@Valid @RequestBody CPrequalificationMethodDTO cPrequalificationMethodDTO) throws URISyntaxException {
        log.debug("REST request to save CPrequalificationMethod : {}", cPrequalificationMethodDTO);
        if (cPrequalificationMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPrequalificationMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPrequalificationMethodDTO result = cPrequalificationMethodService.save(cPrequalificationMethodDTO);
        return ResponseEntity.created(new URI("/api/c-prequalification-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-prequalification-methods} : Updates an existing cPrequalificationMethod.
     *
     * @param cPrequalificationMethodDTO the cPrequalificationMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPrequalificationMethodDTO,
     * or with status {@code 400 (Bad Request)} if the cPrequalificationMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPrequalificationMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-prequalification-methods")
    public ResponseEntity<CPrequalificationMethodDTO> updateCPrequalificationMethod(@Valid @RequestBody CPrequalificationMethodDTO cPrequalificationMethodDTO) throws URISyntaxException {
        log.debug("REST request to update CPrequalificationMethod : {}", cPrequalificationMethodDTO);
        if (cPrequalificationMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPrequalificationMethodDTO result = cPrequalificationMethodService.save(cPrequalificationMethodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPrequalificationMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-prequalification-methods} : get all the cPrequalificationMethods.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPrequalificationMethods in body.
     */
    @GetMapping("/c-prequalification-methods")
    public ResponseEntity<List<CPrequalificationMethodDTO>> getAllCPrequalificationMethods(CPrequalificationMethodCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPrequalificationMethods by criteria: {}", criteria);
        Page<CPrequalificationMethodDTO> page = cPrequalificationMethodQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-prequalification-methods/count} : count all the cPrequalificationMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-prequalification-methods/count")
    public ResponseEntity<Long> countCPrequalificationMethods(CPrequalificationMethodCriteria criteria) {
        log.debug("REST request to count CPrequalificationMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPrequalificationMethodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-prequalification-methods/:id} : get the "id" cPrequalificationMethod.
     *
     * @param id the id of the cPrequalificationMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPrequalificationMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-prequalification-methods/{id}")
    public ResponseEntity<CPrequalificationMethodDTO> getCPrequalificationMethod(@PathVariable Long id) {
        log.debug("REST request to get CPrequalificationMethod : {}", id);
        Optional<CPrequalificationMethodDTO> cPrequalificationMethodDTO = cPrequalificationMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPrequalificationMethodDTO);
    }

    /**
     * {@code DELETE  /c-prequalification-methods/:id} : delete the "id" cPrequalificationMethod.
     *
     * @param id the id of the cPrequalificationMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-prequalification-methods/{id}")
    public ResponseEntity<Void> deleteCPrequalificationMethod(@PathVariable Long id) {
        log.debug("REST request to delete CPrequalificationMethod : {}", id);
        cPrequalificationMethodService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
