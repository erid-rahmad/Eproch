package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADReferenceListQueryService;
import com.bhp.opusb.service.ADReferenceListService;
import com.bhp.opusb.service.dto.ADReferenceListCriteria;
import com.bhp.opusb.service.dto.ADReferenceListDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADReferenceList}.
 */
@RestController
@RequestMapping("/api")
public class ADReferenceListResource {

    private final Logger log = LoggerFactory.getLogger(ADReferenceListResource.class);

    private static final String ENTITY_NAME = "aDReferenceList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADReferenceListService aDReferenceListService;

    private final ADReferenceListQueryService aDReferenceListQueryService;

    public ADReferenceListResource(ADReferenceListService aDReferenceListService, ADReferenceListQueryService aDReferenceListQueryService) {
        this.aDReferenceListService = aDReferenceListService;
        this.aDReferenceListQueryService = aDReferenceListQueryService;
    }

    /**
     * {@code POST  /ad-reference-lists} : Create a new aDReferenceList.
     *
     * @param aDReferenceListDTO the aDReferenceListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDReferenceListDTO, or with status {@code 400 (Bad Request)} if the aDReferenceList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-reference-lists")
    public ResponseEntity<ADReferenceListDTO> createADReferenceList(@Valid @RequestBody ADReferenceListDTO aDReferenceListDTO) throws URISyntaxException {
        log.debug("REST request to save ADReferenceList : {}", aDReferenceListDTO);
        if (aDReferenceListDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDReferenceList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADReferenceListDTO result = aDReferenceListService.save(aDReferenceListDTO);
        return ResponseEntity.created(new URI("/api/ad-reference-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-reference-lists} : Updates an existing aDReferenceList.
     *
     * @param aDReferenceListDTO the aDReferenceListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDReferenceListDTO,
     * or with status {@code 400 (Bad Request)} if the aDReferenceListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDReferenceListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-reference-lists")
    public ResponseEntity<ADReferenceListDTO> updateADReferenceList(@Valid @RequestBody ADReferenceListDTO aDReferenceListDTO) throws URISyntaxException {
        log.debug("REST request to update ADReferenceList : {}", aDReferenceListDTO);
        if (aDReferenceListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADReferenceListDTO result = aDReferenceListService.save(aDReferenceListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDReferenceListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-reference-lists} : get all the aDReferenceLists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDReferenceLists in body.
     */
    @GetMapping("/ad-reference-lists")
    public ResponseEntity<List<ADReferenceListDTO>> getAllADReferenceLists(ADReferenceListCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADReferenceLists by criteria: {}", criteria);
        Page<ADReferenceListDTO> page = aDReferenceListQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-reference-lists/count} : count all the aDReferenceLists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-reference-lists/count")
    public ResponseEntity<Long> countADReferenceLists(ADReferenceListCriteria criteria) {
        log.debug("REST request to count ADReferenceLists by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDReferenceListQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-reference-lists/:id} : get the "id" aDReferenceList.
     *
     * @param id the id of the aDReferenceListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDReferenceListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-reference-lists/{id}")
    public ResponseEntity<ADReferenceListDTO> getADReferenceList(@PathVariable Long id) {
        log.debug("REST request to get ADReferenceList : {}", id);
        Optional<ADReferenceListDTO> aDReferenceListDTO = aDReferenceListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDReferenceListDTO);
    }

    /**
     * {@code DELETE  /ad-reference-lists/:id} : delete the "id" aDReferenceList.
     *
     * @param id the id of the aDReferenceListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-reference-lists/{id}")
    public ResponseEntity<Void> deleteADReferenceList(@PathVariable Long id) {
        log.debug("REST request to delete ADReferenceList : {}", id);
        aDReferenceListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
