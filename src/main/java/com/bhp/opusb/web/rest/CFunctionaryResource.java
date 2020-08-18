package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CFunctionaryQueryService;
import com.bhp.opusb.service.CFunctionaryService;
import com.bhp.opusb.service.dto.CFunctionaryCriteria;
import com.bhp.opusb.service.dto.CFunctionaryDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CFunctionary}.
 */
@RestController
@RequestMapping("/api")
public class CFunctionaryResource {

    private final Logger log = LoggerFactory.getLogger(CFunctionaryResource.class);

    private static final String ENTITY_NAME = "cFunctionary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CFunctionaryService cFunctionaryService;

    private final CFunctionaryQueryService cFunctionaryQueryService;

    public CFunctionaryResource(CFunctionaryService cFunctionaryService, CFunctionaryQueryService cFunctionaryQueryService) {
        this.cFunctionaryService = cFunctionaryService;
        this.cFunctionaryQueryService = cFunctionaryQueryService;
    }

    /**
     * {@code POST  /c-functionaries} : Create a new cFunctionary.
     *
     * @param cFunctionaryDTO the cFunctionaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cFunctionaryDTO, or with status {@code 400 (Bad Request)} if the cFunctionary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-functionaries")
    public ResponseEntity<CFunctionaryDTO> createCFunctionary(@Valid @RequestBody CFunctionaryDTO cFunctionaryDTO) throws URISyntaxException {
        log.debug("REST request to save CFunctionary : {}", cFunctionaryDTO);
        if (cFunctionaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cFunctionary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CFunctionaryDTO result = cFunctionaryService.save(cFunctionaryDTO);
        return ResponseEntity.created(new URI("/api/c-functionaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-functionaries} : Updates an existing cFunctionary.
     *
     * @param cFunctionaryDTO the cFunctionaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cFunctionaryDTO,
     * or with status {@code 400 (Bad Request)} if the cFunctionaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cFunctionaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-functionaries")
    public ResponseEntity<CFunctionaryDTO> updateCFunctionary(@Valid @RequestBody CFunctionaryDTO cFunctionaryDTO) throws URISyntaxException {
        log.debug("REST request to update CFunctionary : {}", cFunctionaryDTO);
        if (cFunctionaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CFunctionaryDTO result = cFunctionaryService.save(cFunctionaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cFunctionaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-functionaries} : get all the cFunctionaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cFunctionaries in body.
     */
    @GetMapping("/c-functionaries")
    public ResponseEntity<List<CFunctionaryDTO>> getAllCFunctionaries(CFunctionaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CFunctionaries by criteria: {}", criteria);
        Page<CFunctionaryDTO> page = cFunctionaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-functionaries/count} : count all the cFunctionaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-functionaries/count")
    public ResponseEntity<Long> countCFunctionaries(CFunctionaryCriteria criteria) {
        log.debug("REST request to count CFunctionaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(cFunctionaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-functionaries/:id} : get the "id" cFunctionary.
     *
     * @param id the id of the cFunctionaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cFunctionaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-functionaries/{id}")
    public ResponseEntity<CFunctionaryDTO> getCFunctionary(@PathVariable Long id) {
        log.debug("REST request to get CFunctionary : {}", id);
        Optional<CFunctionaryDTO> cFunctionaryDTO = cFunctionaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cFunctionaryDTO);
    }

    /**
     * {@code DELETE  /c-functionaries/:id} : delete the "id" cFunctionary.
     *
     * @param id the id of the cFunctionaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-functionaries/{id}")
    public ResponseEntity<Void> deleteCFunctionary(@PathVariable Long id) {
        log.debug("REST request to delete CFunctionary : {}", id);
        cFunctionaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
