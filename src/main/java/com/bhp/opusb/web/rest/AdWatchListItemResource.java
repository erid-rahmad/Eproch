package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdWatchListItemService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdWatchListItemDTO;
import com.bhp.opusb.service.dto.AdWatchListItemCriteria;
import com.bhp.opusb.service.AdWatchListItemQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdWatchListItem}.
 */
@RestController
@RequestMapping("/api")
public class AdWatchListItemResource {

    private final Logger log = LoggerFactory.getLogger(AdWatchListItemResource.class);

    private static final String ENTITY_NAME = "adWatchListItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdWatchListItemService adWatchListItemService;

    private final AdWatchListItemQueryService adWatchListItemQueryService;

    public AdWatchListItemResource(AdWatchListItemService adWatchListItemService, AdWatchListItemQueryService adWatchListItemQueryService) {
        this.adWatchListItemService = adWatchListItemService;
        this.adWatchListItemQueryService = adWatchListItemQueryService;
    }

    /**
     * {@code POST  /ad-watch-list-items} : Create a new adWatchListItem.
     *
     * @param adWatchListItemDTO the adWatchListItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adWatchListItemDTO, or with status {@code 400 (Bad Request)} if the adWatchListItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-watch-list-items")
    public ResponseEntity<AdWatchListItemDTO> createAdWatchListItem(@Valid @RequestBody AdWatchListItemDTO adWatchListItemDTO) throws URISyntaxException {
        log.debug("REST request to save AdWatchListItem : {}", adWatchListItemDTO);
        if (adWatchListItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new adWatchListItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdWatchListItemDTO result = adWatchListItemService.save(adWatchListItemDTO);
        return ResponseEntity.created(new URI("/api/ad-watch-list-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-watch-list-items} : Updates an existing adWatchListItem.
     *
     * @param adWatchListItemDTO the adWatchListItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adWatchListItemDTO,
     * or with status {@code 400 (Bad Request)} if the adWatchListItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adWatchListItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-watch-list-items")
    public ResponseEntity<AdWatchListItemDTO> updateAdWatchListItem(@Valid @RequestBody AdWatchListItemDTO adWatchListItemDTO) throws URISyntaxException {
        log.debug("REST request to update AdWatchListItem : {}", adWatchListItemDTO);
        if (adWatchListItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdWatchListItemDTO result = adWatchListItemService.save(adWatchListItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adWatchListItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-watch-list-items} : get all the adWatchListItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adWatchListItems in body.
     */
    @GetMapping("/ad-watch-list-items")
    public ResponseEntity<List<AdWatchListItemDTO>> getAllAdWatchListItems(AdWatchListItemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdWatchListItems by criteria: {}", criteria);
        Page<AdWatchListItemDTO> page = adWatchListItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-watch-list-items/count} : count all the adWatchListItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-watch-list-items/count")
    public ResponseEntity<Long> countAdWatchListItems(AdWatchListItemCriteria criteria) {
        log.debug("REST request to count AdWatchListItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(adWatchListItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-watch-list-items/:id} : get the "id" adWatchListItem.
     *
     * @param id the id of the adWatchListItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adWatchListItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-watch-list-items/{id}")
    public ResponseEntity<AdWatchListItemDTO> getAdWatchListItem(@PathVariable Long id) {
        log.debug("REST request to get AdWatchListItem : {}", id);
        Optional<AdWatchListItemDTO> adWatchListItemDTO = adWatchListItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adWatchListItemDTO);
    }

    /**
     * {@code DELETE  /ad-watch-list-items/:id} : delete the "id" adWatchListItem.
     *
     * @param id the id of the adWatchListItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-watch-list-items/{id}")
    public ResponseEntity<Void> deleteAdWatchListItem(@PathVariable Long id) {
        log.debug("REST request to delete AdWatchListItem : {}", id);
        adWatchListItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
