package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CompanyFunctionaryService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CompanyFunctionaryDTO;
import com.bhp.opusb.service.dto.CompanyFunctionaryCriteria;
import com.bhp.opusb.service.CompanyFunctionaryQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CompanyFunctionary}.
 */
@RestController
@RequestMapping("/api")
public class CompanyFunctionaryResource {

    private final Logger log = LoggerFactory.getLogger(CompanyFunctionaryResource.class);

    private static final String ENTITY_NAME = "companyFunctionary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyFunctionaryService companyFunctionaryService;

    private final CompanyFunctionaryQueryService companyFunctionaryQueryService;

    public CompanyFunctionaryResource(CompanyFunctionaryService companyFunctionaryService, CompanyFunctionaryQueryService companyFunctionaryQueryService) {
        this.companyFunctionaryService = companyFunctionaryService;
        this.companyFunctionaryQueryService = companyFunctionaryQueryService;
    }

    /**
     * {@code POST  /company-functionaries} : Create a new companyFunctionary.
     *
     * @param companyFunctionaryDTO the companyFunctionaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyFunctionaryDTO, or with status {@code 400 (Bad Request)} if the companyFunctionary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-functionaries")
    public ResponseEntity<CompanyFunctionaryDTO> createCompanyFunctionary(@Valid @RequestBody CompanyFunctionaryDTO companyFunctionaryDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyFunctionary : {}", companyFunctionaryDTO);
        if (companyFunctionaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyFunctionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyFunctionaryDTO result = companyFunctionaryService.save(companyFunctionaryDTO);
        return ResponseEntity.created(new URI("/api/company-functionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-functionaries} : Updates an existing companyFunctionary.
     *
     * @param companyFunctionaryDTO the companyFunctionaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyFunctionaryDTO,
     * or with status {@code 400 (Bad Request)} if the companyFunctionaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyFunctionaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-functionaries")
    public ResponseEntity<CompanyFunctionaryDTO> updateCompanyFunctionary(@Valid @RequestBody CompanyFunctionaryDTO companyFunctionaryDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyFunctionary : {}", companyFunctionaryDTO);
        if (companyFunctionaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyFunctionaryDTO result = companyFunctionaryService.save(companyFunctionaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyFunctionaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /company-functionaries} : get all the companyFunctionaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyFunctionaries in body.
     */
    @GetMapping("/company-functionaries")
    public ResponseEntity<List<CompanyFunctionaryDTO>> getAllCompanyFunctionaries(CompanyFunctionaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CompanyFunctionaries by criteria: {}", criteria);
        Page<CompanyFunctionaryDTO> page = companyFunctionaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-functionaries/count} : count all the companyFunctionaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/company-functionaries/count")
    public ResponseEntity<Long> countCompanyFunctionaries(CompanyFunctionaryCriteria criteria) {
        log.debug("REST request to count CompanyFunctionaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyFunctionaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /company-functionaries/:id} : get the "id" companyFunctionary.
     *
     * @param id the id of the companyFunctionaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyFunctionaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-functionaries/{id}")
    public ResponseEntity<CompanyFunctionaryDTO> getCompanyFunctionary(@PathVariable Long id) {
        log.debug("REST request to get CompanyFunctionary : {}", id);
        Optional<CompanyFunctionaryDTO> companyFunctionaryDTO = companyFunctionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyFunctionaryDTO);
    }

    /**
     * {@code DELETE  /company-functionaries/:id} : delete the "id" companyFunctionary.
     *
     * @param id the id of the companyFunctionaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-functionaries/{id}")
    public ResponseEntity<Void> deleteCompanyFunctionary(@PathVariable Long id) {
        log.debug("REST request to delete CompanyFunctionary : {}", id);
        companyFunctionaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
