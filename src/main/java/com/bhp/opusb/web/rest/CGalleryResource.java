package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CGalleryQueryService;
import com.bhp.opusb.service.CGalleryService;
import com.bhp.opusb.service.dto.CGalleryCriteria;
import com.bhp.opusb.service.dto.CGalleryDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CGallery}.
 */
@RestController
@RequestMapping("/api")
public class CGalleryResource {

    private final Logger log = LoggerFactory.getLogger(CGalleryResource.class);

    private static final String ENTITY_NAME = "cGallery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CGalleryService cGalleryService;

    private final CGalleryQueryService cGalleryQueryService;

    public CGalleryResource(CGalleryService cGalleryService, CGalleryQueryService cGalleryQueryService) {
        this.cGalleryService = cGalleryService;
        this.cGalleryQueryService = cGalleryQueryService;
    }

    /**
     * {@code POST  /c-galleries} : Create a new cGallery.
     *
     * @param cGalleryDTO the cGalleryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cGalleryDTO, or with status {@code 400 (Bad Request)} if the cGallery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-galleries")
    public ResponseEntity<CGalleryDTO> createCGallery(@Valid @RequestBody CGalleryDTO cGalleryDTO) throws URISyntaxException {
        log.debug("REST request to save CGallery : {}", cGalleryDTO);
        if (cGalleryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cGallery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CGalleryDTO result = cGalleryService.save(cGalleryDTO);
        return ResponseEntity.created(new URI("/api/c-galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-galleries} : Updates an existing cGallery.
     *
     * @param cGalleryDTO the cGalleryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cGalleryDTO,
     * or with status {@code 400 (Bad Request)} if the cGalleryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cGalleryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-galleries")
    public ResponseEntity<CGalleryDTO> updateCGallery(@Valid @RequestBody CGalleryDTO cGalleryDTO) throws URISyntaxException {
        log.debug("REST request to update CGallery : {}", cGalleryDTO);
        if (cGalleryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CGalleryDTO result = cGalleryService.save(cGalleryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cGalleryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-galleries} : get all the cGalleries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cGalleries in body.
     */
    @GetMapping("/c-galleries")
    public ResponseEntity<List<CGalleryDTO>> getAllCGalleries(CGalleryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CGalleries by criteria: {}", criteria);
        Page<CGalleryDTO> page = cGalleryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-galleries/count} : count all the cGalleries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-galleries/count")
    public ResponseEntity<Long> countCGalleries(CGalleryCriteria criteria) {
        log.debug("REST request to count CGalleries by criteria: {}", criteria);
        return ResponseEntity.ok().body(cGalleryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-galleries/:id} : get the "id" cGallery.
     *
     * @param id the id of the cGalleryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cGalleryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-galleries/{id}")
    public ResponseEntity<CGalleryDTO> getCGallery(@PathVariable Long id) {
        log.debug("REST request to get CGallery : {}", id);
        Optional<CGalleryDTO> cGalleryDTO = cGalleryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cGalleryDTO);
    }

    /**
     * {@code DELETE  /c-galleries/:id} : delete the "id" cGallery.
     *
     * @param id the id of the cGalleryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-galleries/{id}")
    public ResponseEntity<Void> deleteCGallery(@PathVariable Long id) {
        log.debug("REST request to delete CGallery : {}", id);
        cGalleryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
