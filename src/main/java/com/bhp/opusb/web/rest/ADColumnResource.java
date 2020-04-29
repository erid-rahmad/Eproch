package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADColumnQueryService;
import com.bhp.opusb.service.ADColumnService;
import com.bhp.opusb.service.dto.ADColumnCriteria;
import com.bhp.opusb.service.dto.ADColumnDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADColumn}.
 */
@RestController
@RequestMapping("/api")
public class ADColumnResource {

    private final Logger log = LoggerFactory.getLogger(ADColumnResource.class);

    private static final String ENTITY_NAME = "aDColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADColumnService aDColumnService;

    private final ADColumnQueryService aDColumnQueryService;

    public ADColumnResource(ADColumnService aDColumnService, ADColumnQueryService aDColumnQueryService) {
        this.aDColumnService = aDColumnService;
        this.aDColumnQueryService = aDColumnQueryService;
    }

    /**
     * {@code POST  /ad-columns} : Create a new aDColumn.
     *
     * @param aDColumnDTO the aDColumnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDColumnDTO, or with status {@code 400 (Bad Request)} if the aDColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-columns")
    public ResponseEntity<ADColumnDTO> createADColumn(@Valid @RequestBody ADColumnDTO aDColumnDTO) throws URISyntaxException {
        log.debug("REST request to save ADColumn : {}", aDColumnDTO);
        if (aDColumnDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADColumnDTO result = aDColumnService.save(aDColumnDTO);
        return ResponseEntity.created(new URI("/api/ad-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-columns} : Updates an existing aDColumn.
     *
     * @param aDColumnDTO the aDColumnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDColumnDTO,
     * or with status {@code 400 (Bad Request)} if the aDColumnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDColumnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-columns")
    public ResponseEntity<ADColumnDTO> updateADColumn(@Valid @RequestBody ADColumnDTO aDColumnDTO) throws URISyntaxException {
        log.debug("REST request to update ADColumn : {}", aDColumnDTO);
        if (aDColumnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADColumnDTO result = aDColumnService.save(aDColumnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDColumnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-columns} : get all the aDColumns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDColumns in body.
     */
    @GetMapping("/ad-columns")
    public ResponseEntity<List<ADColumnDTO>> getAllADColumns(ADColumnCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADColumns by criteria: {}", criteria);
        Page<ADColumnDTO> page = aDColumnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-columns/count} : count all the aDColumns.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-columns/count")
    public ResponseEntity<Long> countADColumns(ADColumnCriteria criteria) {
        log.debug("REST request to count ADColumns by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDColumnQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-columns/:id} : get the "id" aDColumn.
     *
     * @param id the id of the aDColumnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDColumnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-columns/{id}")
    public ResponseEntity<ADColumnDTO> getADColumn(@PathVariable Long id) {
        log.debug("REST request to get ADColumn : {}", id);
        Optional<ADColumnDTO> aDColumnDTO = aDColumnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDColumnDTO);
    }

    /**
     * {@code DELETE  /ad-columns/:id} : delete the "id" aDColumn.
     *
     * @param id the id of the aDColumnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-columns/{id}")
    public ResponseEntity<Void> deleteADColumn(@PathVariable Long id) {
        log.debug("REST request to delete ADColumn : {}", id);
        aDColumnService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
