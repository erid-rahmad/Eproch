package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADReferenceQueryService;
import com.bhp.opusb.service.ADReferenceService;
import com.bhp.opusb.service.dto.ADReferenceCriteria;
import com.bhp.opusb.service.dto.ADReferenceDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADReference}.
 */
@RestController
@RequestMapping("/api")
public class ADReferenceResource {

    private final Logger log = LoggerFactory.getLogger(ADReferenceResource.class);

    private static final String ENTITY_NAME = "aDReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADReferenceService aDReferenceService;

    private final ADReferenceQueryService aDReferenceQueryService;

    public ADReferenceResource(ADReferenceService aDReferenceService, ADReferenceQueryService aDReferenceQueryService) {
        this.aDReferenceService = aDReferenceService;
        this.aDReferenceQueryService = aDReferenceQueryService;
    }

    /**
     * {@code POST  /ad-references} : Create a new aDReference.
     *
     * @param aDReferenceDTO the aDReferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDReferenceDTO, or with status {@code 400 (Bad Request)} if the aDReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-references")
    public ResponseEntity<ADReferenceDTO> createADReference(@Valid @RequestBody ADReferenceDTO aDReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save ADReference : {}", aDReferenceDTO);
        if (aDReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADReferenceDTO result = aDReferenceService.save(aDReferenceDTO);
        return ResponseEntity.created(new URI("/api/ad-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-references} : Updates an existing aDReference.
     *
     * @param aDReferenceDTO the aDReferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDReferenceDTO,
     * or with status {@code 400 (Bad Request)} if the aDReferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDReferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-references")
    public ResponseEntity<ADReferenceDTO> updateADReference(@Valid @RequestBody ADReferenceDTO aDReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update ADReference : {}", aDReferenceDTO);
        if (aDReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADReferenceDTO result = aDReferenceService.save(aDReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-references} : get all the aDReferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDReferences in body.
     */
    @GetMapping("/ad-references")
    public ResponseEntity<List<ADReferenceDTO>> getAllADReferences(ADReferenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADReferences by criteria: {}", criteria);
        Page<ADReferenceDTO> page = aDReferenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-references/count} : count all the aDReferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-references/count")
    public ResponseEntity<Long> countADReferences(ADReferenceCriteria criteria) {
        log.debug("REST request to count ADReferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDReferenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-references/:id} : get the "id" aDReference.
     *
     * @param id the id of the aDReferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDReferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-references/{id}")
    public ResponseEntity<ADReferenceDTO> getADReference(@PathVariable Long id) {
        log.debug("REST request to get ADReference : {}", id);
        Optional<ADReferenceDTO> aDReferenceDTO = aDReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDReferenceDTO);
    }

    /**
     * {@code DELETE  /ad-references/:id} : delete the "id" aDReference.
     *
     * @param id the id of the aDReferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-references/{id}")
    public ResponseEntity<Void> deleteADReference(@PathVariable Long id) {
        log.debug("REST request to delete ADReference : {}", id);
        aDReferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
