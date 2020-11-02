package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CProductGroupAccountService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CProductGroupAccountDTO;
import com.bhp.opusb.service.dto.CProductGroupAccountCriteria;
import com.bhp.opusb.service.CProductGroupAccountQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CProductGroupAccount}.
 */
@RestController
@RequestMapping("/api")
public class CProductGroupAccountResource {

    private final Logger log = LoggerFactory.getLogger(CProductGroupAccountResource.class);

    private static final String ENTITY_NAME = "cProductGroupAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CProductGroupAccountService cProductGroupAccountService;

    private final CProductGroupAccountQueryService cProductGroupAccountQueryService;

    public CProductGroupAccountResource(CProductGroupAccountService cProductGroupAccountService, CProductGroupAccountQueryService cProductGroupAccountQueryService) {
        this.cProductGroupAccountService = cProductGroupAccountService;
        this.cProductGroupAccountQueryService = cProductGroupAccountQueryService;
    }

    /**
     * {@code POST  /c-product-group-accounts} : Create a new cProductGroupAccount.
     *
     * @param cProductGroupAccountDTO the cProductGroupAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductGroupAccountDTO, or with status {@code 400 (Bad Request)} if the cProductGroupAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-product-group-accounts")
    public ResponseEntity<CProductGroupAccountDTO> createCProductGroupAccount(@Valid @RequestBody CProductGroupAccountDTO cProductGroupAccountDTO) throws URISyntaxException {
        log.debug("REST request to save CProductGroupAccount : {}", cProductGroupAccountDTO);
        if (cProductGroupAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new cProductGroupAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CProductGroupAccountDTO result = cProductGroupAccountService.save(cProductGroupAccountDTO);
        return ResponseEntity.created(new URI("/api/c-product-group-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-product-group-accounts} : Updates an existing cProductGroupAccount.
     *
     * @param cProductGroupAccountDTO the cProductGroupAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cProductGroupAccountDTO,
     * or with status {@code 400 (Bad Request)} if the cProductGroupAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cProductGroupAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-product-group-accounts")
    public ResponseEntity<CProductGroupAccountDTO> updateCProductGroupAccount(@Valid @RequestBody CProductGroupAccountDTO cProductGroupAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CProductGroupAccount : {}", cProductGroupAccountDTO);
        if (cProductGroupAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CProductGroupAccountDTO result = cProductGroupAccountService.save(cProductGroupAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cProductGroupAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-product-group-accounts} : get all the cProductGroupAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cProductGroupAccounts in body.
     */
    @GetMapping("/c-product-group-accounts")
    public ResponseEntity<List<CProductGroupAccountDTO>> getAllCProductGroupAccounts(CProductGroupAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CProductGroupAccounts by criteria: {}", criteria);
        Page<CProductGroupAccountDTO> page = cProductGroupAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-product-group-accounts/count} : count all the cProductGroupAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-product-group-accounts/count")
    public ResponseEntity<Long> countCProductGroupAccounts(CProductGroupAccountCriteria criteria) {
        log.debug("REST request to count CProductGroupAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(cProductGroupAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-product-group-accounts/:id} : get the "id" cProductGroupAccount.
     *
     * @param id the id of the cProductGroupAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cProductGroupAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-product-group-accounts/{id}")
    public ResponseEntity<CProductGroupAccountDTO> getCProductGroupAccount(@PathVariable Long id) {
        log.debug("REST request to get CProductGroupAccount : {}", id);
        Optional<CProductGroupAccountDTO> cProductGroupAccountDTO = cProductGroupAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cProductGroupAccountDTO);
    }

    /**
     * {@code DELETE  /c-product-group-accounts/:id} : delete the "id" cProductGroupAccount.
     *
     * @param id the id of the cProductGroupAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-product-group-accounts/{id}")
    public ResponseEntity<Void> deleteCProductGroupAccount(@PathVariable Long id) {
        log.debug("REST request to delete CProductGroupAccount : {}", id);
        cProductGroupAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
