package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CGalleryItemQueryService;
import com.bhp.opusb.service.CGalleryItemService;
import com.bhp.opusb.service.dto.CGalleryItemCriteria;
import com.bhp.opusb.service.dto.CGalleryItemDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CGalleryItem}.
 */
@RestController
@RequestMapping("/api")
public class CGalleryItemResource {

    private final Logger log = LoggerFactory.getLogger(CGalleryItemResource.class);

    private static final String ENTITY_NAME = "cGalleryItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CGalleryItemService cGalleryItemService;

    private final CGalleryItemQueryService cGalleryItemQueryService;

    public CGalleryItemResource(CGalleryItemService cGalleryItemService, CGalleryItemQueryService cGalleryItemQueryService) {
        this.cGalleryItemService = cGalleryItemService;
        this.cGalleryItemQueryService = cGalleryItemQueryService;
    }

    /**
     * {@code POST  /c-gallery-items} : Create a new cGalleryItem.
     *
     * @param cGalleryItemDTO the cGalleryItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cGalleryItemDTO, or with status {@code 400 (Bad Request)} if the cGalleryItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-gallery-items")
    public ResponseEntity<CGalleryItemDTO> createCGalleryItem(@Valid @RequestBody CGalleryItemDTO cGalleryItemDTO) throws URISyntaxException {
        log.debug("REST request to save CGalleryItem : {}", cGalleryItemDTO);
        if (cGalleryItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new cGalleryItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CGalleryItemDTO result = cGalleryItemService.save(cGalleryItemDTO);
        return ResponseEntity.created(new URI("/api/c-gallery-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-gallery-items} : Updates an existing cGalleryItem.
     *
     * @param cGalleryItemDTO the cGalleryItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cGalleryItemDTO,
     * or with status {@code 400 (Bad Request)} if the cGalleryItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cGalleryItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-gallery-items")
    public ResponseEntity<CGalleryItemDTO> updateCGalleryItem(@Valid @RequestBody CGalleryItemDTO cGalleryItemDTO) throws URISyntaxException {
        log.debug("REST request to update CGalleryItem : {}", cGalleryItemDTO);
        if (cGalleryItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CGalleryItemDTO result = cGalleryItemService.save(cGalleryItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cGalleryItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-gallery-items} : get all the cGalleryItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cGalleryItems in body.
     */
    @GetMapping("/c-gallery-items")
    public ResponseEntity<List<CGalleryItemDTO>> getAllCGalleryItems(CGalleryItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CGalleryItems by criteria: {}", criteria);
        Page<CGalleryItemDTO> page = cGalleryItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-gallery-items/count} : count all the cGalleryItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-gallery-items/count")
    public ResponseEntity<Long> countCGalleryItems(CGalleryItemCriteria criteria) {
        log.debug("REST request to count CGalleryItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(cGalleryItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-gallery-items/:id} : get the "id" cGalleryItem.
     *
     * @param id the id of the cGalleryItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cGalleryItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-gallery-items/{id}")
    public ResponseEntity<CGalleryItemDTO> getCGalleryItem(@PathVariable Long id) {
        log.debug("REST request to get CGalleryItem : {}", id);
        Optional<CGalleryItemDTO> cGalleryItemDTO = cGalleryItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cGalleryItemDTO);
    }

    /**
     * {@code DELETE  /c-gallery-items/:id} : delete the "id" cGalleryItem.
     *
     * @param id the id of the cGalleryItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-gallery-items/{id}")
    public ResponseEntity<Void> deleteCGalleryItem(@PathVariable Long id) {
        log.debug("REST request to delete CGalleryItem : {}", id);
        cGalleryItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
