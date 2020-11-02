package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CProductCategoryAccountService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CProductCategoryAccountDTO;
import com.bhp.opusb.service.dto.CProductCategoryAccountCriteria;
import com.bhp.opusb.service.CProductCategoryAccountQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CProductCategoryAccount}.
 */
@RestController
@RequestMapping("/api")
public class CProductCategoryAccountResource {

    private final Logger log = LoggerFactory.getLogger(CProductCategoryAccountResource.class);

    private static final String ENTITY_NAME = "cProductCategoryAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CProductCategoryAccountService cProductCategoryAccountService;

    private final CProductCategoryAccountQueryService cProductCategoryAccountQueryService;

    public CProductCategoryAccountResource(CProductCategoryAccountService cProductCategoryAccountService, CProductCategoryAccountQueryService cProductCategoryAccountQueryService) {
        this.cProductCategoryAccountService = cProductCategoryAccountService;
        this.cProductCategoryAccountQueryService = cProductCategoryAccountQueryService;
    }

    /**
     * {@code POST  /c-product-category-accounts} : Create a new cProductCategoryAccount.
     *
     * @param cProductCategoryAccountDTO the cProductCategoryAccountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cProductCategoryAccountDTO, or with status {@code 400 (Bad Request)} if the cProductCategoryAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-product-category-accounts")
    public ResponseEntity<CProductCategoryAccountDTO> createCProductCategoryAccount(@Valid @RequestBody CProductCategoryAccountDTO cProductCategoryAccountDTO) throws URISyntaxException {
        log.debug("REST request to save CProductCategoryAccount : {}", cProductCategoryAccountDTO);
        if (cProductCategoryAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new cProductCategoryAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CProductCategoryAccountDTO result = cProductCategoryAccountService.save(cProductCategoryAccountDTO);
        return ResponseEntity.created(new URI("/api/c-product-category-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-product-category-accounts} : Updates an existing cProductCategoryAccount.
     *
     * @param cProductCategoryAccountDTO the cProductCategoryAccountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cProductCategoryAccountDTO,
     * or with status {@code 400 (Bad Request)} if the cProductCategoryAccountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cProductCategoryAccountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-product-category-accounts")
    public ResponseEntity<CProductCategoryAccountDTO> updateCProductCategoryAccount(@Valid @RequestBody CProductCategoryAccountDTO cProductCategoryAccountDTO) throws URISyntaxException {
        log.debug("REST request to update CProductCategoryAccount : {}", cProductCategoryAccountDTO);
        if (cProductCategoryAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CProductCategoryAccountDTO result = cProductCategoryAccountService.save(cProductCategoryAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cProductCategoryAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-product-category-accounts} : get all the cProductCategoryAccounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cProductCategoryAccounts in body.
     */
    @GetMapping("/c-product-category-accounts")
    public ResponseEntity<List<CProductCategoryAccountDTO>> getAllCProductCategoryAccounts(CProductCategoryAccountCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CProductCategoryAccounts by criteria: {}", criteria);
        Page<CProductCategoryAccountDTO> page = cProductCategoryAccountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-product-category-accounts/count} : count all the cProductCategoryAccounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-product-category-accounts/count")
    public ResponseEntity<Long> countCProductCategoryAccounts(CProductCategoryAccountCriteria criteria) {
        log.debug("REST request to count CProductCategoryAccounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(cProductCategoryAccountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-product-category-accounts/:id} : get the "id" cProductCategoryAccount.
     *
     * @param id the id of the cProductCategoryAccountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cProductCategoryAccountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-product-category-accounts/{id}")
    public ResponseEntity<CProductCategoryAccountDTO> getCProductCategoryAccount(@PathVariable Long id) {
        log.debug("REST request to get CProductCategoryAccount : {}", id);
        Optional<CProductCategoryAccountDTO> cProductCategoryAccountDTO = cProductCategoryAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cProductCategoryAccountDTO);
    }

    /**
     * {@code DELETE  /c-product-category-accounts/:id} : delete the "id" cProductCategoryAccount.
     *
     * @param id the id of the cProductCategoryAccountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-product-category-accounts/{id}")
    public ResponseEntity<Void> deleteCProductCategoryAccount(@PathVariable Long id) {
        log.debug("REST request to delete CProductCategoryAccount : {}", id);
        cProductCategoryAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
