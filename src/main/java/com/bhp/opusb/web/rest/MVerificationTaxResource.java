package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVerificationTaxService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVerificationTaxDTO;
import com.bhp.opusb.service.dto.MVerificationTaxCriteria;
import com.bhp.opusb.service.MVerificationTaxQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVerificationTax}.
 */
@RestController
@RequestMapping("/api")
public class MVerificationTaxResource {

    private final Logger log = LoggerFactory.getLogger(MVerificationTaxResource.class);

    private static final String ENTITY_NAME = "mVerificationTax";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVerificationTaxService mVerificationTaxService;

    private final MVerificationTaxQueryService mVerificationTaxQueryService;

    public MVerificationTaxResource(MVerificationTaxService mVerificationTaxService, MVerificationTaxQueryService mVerificationTaxQueryService) {
        this.mVerificationTaxService = mVerificationTaxService;
        this.mVerificationTaxQueryService = mVerificationTaxQueryService;
    }

    /**
     * {@code POST  /m-verification-taxes} : Create a new mVerificationTax.
     *
     * @param mVerificationTaxDTO the mVerificationTaxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVerificationTaxDTO, or with status {@code 400 (Bad Request)} if the mVerificationTax has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-verification-taxes")
    public ResponseEntity<MVerificationTaxDTO> createMVerificationTax(@Valid @RequestBody MVerificationTaxDTO mVerificationTaxDTO) throws URISyntaxException {
        log.debug("REST request to save MVerificationTax : {}", mVerificationTaxDTO);
        if (mVerificationTaxDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVerificationTax cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVerificationTaxDTO result = mVerificationTaxService.save(mVerificationTaxDTO);
        return ResponseEntity.created(new URI("/api/m-verification-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-verification-taxes} : Updates an existing mVerificationTax.
     *
     * @param mVerificationTaxDTO the mVerificationTaxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVerificationTaxDTO,
     * or with status {@code 400 (Bad Request)} if the mVerificationTaxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVerificationTaxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-verification-taxes")
    public ResponseEntity<MVerificationTaxDTO> updateMVerificationTax(@Valid @RequestBody MVerificationTaxDTO mVerificationTaxDTO) throws URISyntaxException {
        log.debug("REST request to update MVerificationTax : {}", mVerificationTaxDTO);
        if (mVerificationTaxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVerificationTaxDTO result = mVerificationTaxService.save(mVerificationTaxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVerificationTaxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-verification-taxes} : get all the mVerificationTaxes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVerificationTaxes in body.
     */
    @GetMapping("/m-verification-taxes")
    public ResponseEntity<List<MVerificationTaxDTO>> getAllMVerificationTaxes(MVerificationTaxCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVerificationTaxes by criteria: {}", criteria);
        Page<MVerificationTaxDTO> page = mVerificationTaxQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-verification-taxes/count} : count all the mVerificationTaxes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-verification-taxes/count")
    public ResponseEntity<Long> countMVerificationTaxes(MVerificationTaxCriteria criteria) {
        log.debug("REST request to count MVerificationTaxes by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVerificationTaxQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-verification-taxes/:id} : get the "id" mVerificationTax.
     *
     * @param id the id of the mVerificationTaxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVerificationTaxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-verification-taxes/{id}")
    public ResponseEntity<MVerificationTaxDTO> getMVerificationTax(@PathVariable Long id) {
        log.debug("REST request to get MVerificationTax : {}", id);
        Optional<MVerificationTaxDTO> mVerificationTaxDTO = mVerificationTaxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVerificationTaxDTO);
    }

    /**
     * {@code DELETE  /m-verification-taxes/:id} : delete the "id" mVerificationTax.
     *
     * @param id the id of the mVerificationTaxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-verification-taxes/{id}")
    public ResponseEntity<Void> deleteMVerificationTax(@PathVariable Long id) {
        log.debug("REST request to delete MVerificationTax : {}", id);
        mVerificationTaxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
