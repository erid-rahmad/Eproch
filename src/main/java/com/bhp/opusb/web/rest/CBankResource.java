package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CBankService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CBankDTO;
import com.bhp.opusb.service.dto.CBankCriteria;
import com.bhp.opusb.service.CBankQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CBank}.
 */
@RestController
@RequestMapping("/api")
public class CBankResource {

    private final Logger log = LoggerFactory.getLogger(CBankResource.class);

    private static final String ENTITY_NAME = "cBank";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CBankService cBankService;

    private final CBankQueryService cBankQueryService;

    public CBankResource(CBankService cBankService, CBankQueryService cBankQueryService) {
        this.cBankService = cBankService;
        this.cBankQueryService = cBankQueryService;
    }

    /**
     * {@code POST  /c-banks} : Create a new cBank.
     *
     * @param cBankDTO the cBankDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cBankDTO, or with status {@code 400 (Bad Request)} if the cBank has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-banks")
    public ResponseEntity<CBankDTO> createCBank(@Valid @RequestBody CBankDTO cBankDTO) throws URISyntaxException {
        log.debug("REST request to save CBank : {}", cBankDTO);
        if (cBankDTO.getId() != null) {
            throw new BadRequestAlertException("A new cBank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CBankDTO result = cBankService.save(cBankDTO);
        return ResponseEntity.created(new URI("/api/c-banks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-banks} : Updates an existing cBank.
     *
     * @param cBankDTO the cBankDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cBankDTO,
     * or with status {@code 400 (Bad Request)} if the cBankDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cBankDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-banks")
    public ResponseEntity<CBankDTO> updateCBank(@Valid @RequestBody CBankDTO cBankDTO) throws URISyntaxException {
        log.debug("REST request to update CBank : {}", cBankDTO);
        if (cBankDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CBankDTO result = cBankService.save(cBankDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cBankDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-banks} : get all the cBanks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cBanks in body.
     */
    @GetMapping("/c-banks")
    public ResponseEntity<List<CBankDTO>> getAllCBanks(CBankCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CBanks by criteria: {}", criteria);
        Page<CBankDTO> page = cBankQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-banks/count} : count all the cBanks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-banks/count")
    public ResponseEntity<Long> countCBanks(CBankCriteria criteria) {
        log.debug("REST request to count CBanks by criteria: {}", criteria);
        return ResponseEntity.ok().body(cBankQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-banks/:id} : get the "id" cBank.
     *
     * @param id the id of the cBankDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cBankDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-banks/{id}")
    public ResponseEntity<CBankDTO> getCBank(@PathVariable Long id) {
        log.debug("REST request to get CBank : {}", id);
        Optional<CBankDTO> cBankDTO = cBankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cBankDTO);
    }

    /**
     * {@code DELETE  /c-banks/:id} : delete the "id" cBank.
     *
     * @param id the id of the cBankDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-banks/{id}")
    public ResponseEntity<Void> deleteCBank(@PathVariable Long id) {
        log.debug("REST request to delete CBank : {}", id);
        cBankService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
