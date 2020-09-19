package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ScAuthorityService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ScAuthorityDTO;
import com.bhp.opusb.service.dto.ScAuthorityCriteria;
import com.bhp.opusb.service.ScAuthorityQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.ScAuthority}.
 */
@RestController
@RequestMapping("/api")
public class ScAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(ScAuthorityResource.class);

    private static final String ENTITY_NAME = "scAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScAuthorityService scAuthorityService;

    private final ScAuthorityQueryService scAuthorityQueryService;

    public ScAuthorityResource(ScAuthorityService scAuthorityService, ScAuthorityQueryService scAuthorityQueryService) {
        this.scAuthorityService = scAuthorityService;
        this.scAuthorityQueryService = scAuthorityQueryService;
    }

    /**
     * {@code POST  /sc-authorities} : Create a new scAuthority.
     *
     * @param scAuthorityDTO the scAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scAuthorityDTO, or with status {@code 400 (Bad Request)} if the scAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-authorities")
    public ResponseEntity<ScAuthorityDTO> createScAuthority(@Valid @RequestBody ScAuthorityDTO scAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save ScAuthority : {}", scAuthorityDTO);
        if (scAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new scAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScAuthorityDTO result = scAuthorityService.save(scAuthorityDTO);
        return ResponseEntity.created(new URI("/api/sc-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-authorities} : Updates an existing scAuthority.
     *
     * @param scAuthorityDTO the scAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the scAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-authorities")
    public ResponseEntity<ScAuthorityDTO> updateScAuthority(@Valid @RequestBody ScAuthorityDTO scAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update ScAuthority : {}", scAuthorityDTO);
        if (scAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScAuthorityDTO result = scAuthorityService.save(scAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sc-authorities} : get all the scAuthorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scAuthorities in body.
     */
    @GetMapping("/sc-authorities")
    public ResponseEntity<List<ScAuthorityDTO>> getAllScAuthorities(ScAuthorityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ScAuthorities by criteria: {}", criteria);
        Page<ScAuthorityDTO> page = scAuthorityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sc-authorities/count} : count all the scAuthorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sc-authorities/count")
    public ResponseEntity<Long> countScAuthorities(ScAuthorityCriteria criteria) {
        log.debug("REST request to count ScAuthorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(scAuthorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sc-authorities/:id} : get the "id" scAuthority.
     *
     * @param id the id of the scAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-authorities/{id}")
    public ResponseEntity<ScAuthorityDTO> getScAuthority(@PathVariable Long id) {
        log.debug("REST request to get ScAuthority : {}", id);
        Optional<ScAuthorityDTO> scAuthorityDTO = scAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scAuthorityDTO);
    }

    /**
     * {@code DELETE  /sc-authorities/:id} : delete the "id" scAuthority.
     *
     * @param id the id of the scAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-authorities/{id}")
    public ResponseEntity<Void> deleteScAuthority(@PathVariable Long id) {
        log.debug("REST request to delete ScAuthority : {}", id);
        scAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
