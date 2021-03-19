package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CPaymentTermQueryService;
import com.bhp.opusb.service.CPaymentTermService;
import com.bhp.opusb.service.dto.CPaymentTermCriteria;
import com.bhp.opusb.service.dto.CPaymentTermDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CPaymentTerm}.
 */
@RestController
@RequestMapping("/api")
public class CPaymentTermResource {

    private final Logger log = LoggerFactory.getLogger(CPaymentTermResource.class);

    private static final String ENTITY_NAME = "cPaymentTerm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPaymentTermService cPaymentTermService;

    private final CPaymentTermQueryService cPaymentTermQueryService;

    public CPaymentTermResource(CPaymentTermService cPaymentTermService, CPaymentTermQueryService cPaymentTermQueryService) {
        this.cPaymentTermService = cPaymentTermService;
        this.cPaymentTermQueryService = cPaymentTermQueryService;
    }

    /**
     * {@code POST  /c-payment-terms} : Create a new cPaymentTerm.
     *
     * @param cPaymentTermDTO the cPaymentTermDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPaymentTermDTO, or with status {@code 400 (Bad Request)} if the cPaymentTerm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-payment-terms")
    public ResponseEntity<CPaymentTermDTO> createCPaymentTerm(@Valid @RequestBody CPaymentTermDTO cPaymentTermDTO) throws URISyntaxException {
        log.debug("REST request to save CPaymentTerm : {}", cPaymentTermDTO);
        if (cPaymentTermDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPaymentTerm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPaymentTermDTO result = cPaymentTermService.save(cPaymentTermDTO);
        return ResponseEntity.created(new URI("/api/c-payment-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-payment-terms} : Updates an existing cPaymentTerm.
     *
     * @param cPaymentTermDTO the cPaymentTermDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPaymentTermDTO,
     * or with status {@code 400 (Bad Request)} if the cPaymentTermDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPaymentTermDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-payment-terms")
    public ResponseEntity<CPaymentTermDTO> updateCPaymentTerm(@Valid @RequestBody CPaymentTermDTO cPaymentTermDTO) throws URISyntaxException {
        log.debug("REST request to update CPaymentTerm : {}", cPaymentTermDTO);
        if (cPaymentTermDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPaymentTermDTO result = cPaymentTermService.save(cPaymentTermDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPaymentTermDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-payment-terms} : get all the cPaymentTerms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPaymentTerms in body.
     */
    @GetMapping("/c-payment-terms")
    public ResponseEntity<List<CPaymentTermDTO>> getAllCPaymentTerms(CPaymentTermCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPaymentTerms by criteria: {}", criteria);
        Page<CPaymentTermDTO> page = cPaymentTermQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-payment-terms/count} : count all the cPaymentTerms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-payment-terms/count")
    public ResponseEntity<Long> countCPaymentTerms(CPaymentTermCriteria criteria) {
        log.debug("REST request to count CPaymentTerms by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPaymentTermQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-payment-terms/:id} : get the "id" cPaymentTerm.
     *
     * @param id the id of the cPaymentTermDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPaymentTermDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-payment-terms/{id}")
    public ResponseEntity<CPaymentTermDTO> getCPaymentTerm(@PathVariable Long id) {
        log.debug("REST request to get CPaymentTerm : {}", id);
        Optional<CPaymentTermDTO> cPaymentTermDTO = cPaymentTermService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPaymentTermDTO);
    }

    /**
     * {@code DELETE  /c-payment-terms/:id} : delete the "id" cPaymentTerm.
     *
     * @param id the id of the cPaymentTermDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-payment-terms/{id}")
    public ResponseEntity<Void> deleteCPaymentTerm(@PathVariable Long id) {
        log.debug("REST request to delete CPaymentTerm : {}", id);
        cPaymentTermService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
