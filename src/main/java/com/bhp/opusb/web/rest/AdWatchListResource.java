package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdWatchListService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdWatchListDTO;
import com.bhp.opusb.service.dto.AdWatchListCriteria;
import com.bhp.opusb.service.AdWatchListQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdWatchList}.
 */
@RestController
@RequestMapping("/api")
public class AdWatchListResource {

    private final Logger log = LoggerFactory.getLogger(AdWatchListResource.class);

    private static final String ENTITY_NAME = "adWatchList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdWatchListService adWatchListService;

    private final AdWatchListQueryService adWatchListQueryService;

    public AdWatchListResource(AdWatchListService adWatchListService, AdWatchListQueryService adWatchListQueryService) {
        this.adWatchListService = adWatchListService;
        this.adWatchListQueryService = adWatchListQueryService;
    }

    /**
     * {@code POST  /ad-watch-lists} : Create a new adWatchList.
     *
     * @param adWatchListDTO the adWatchListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adWatchListDTO, or with status {@code 400 (Bad Request)} if the adWatchList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-watch-lists")
    public ResponseEntity<AdWatchListDTO> createAdWatchList(@Valid @RequestBody AdWatchListDTO adWatchListDTO) throws URISyntaxException {
        log.debug("REST request to save AdWatchList : {}", adWatchListDTO);
        if (adWatchListDTO.getId() != null) {
            throw new BadRequestAlertException("A new adWatchList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdWatchListDTO result = adWatchListService.save(adWatchListDTO);
        return ResponseEntity.created(new URI("/api/ad-watch-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-watch-lists} : Updates an existing adWatchList.
     *
     * @param adWatchListDTO the adWatchListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adWatchListDTO,
     * or with status {@code 400 (Bad Request)} if the adWatchListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adWatchListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-watch-lists")
    public ResponseEntity<AdWatchListDTO> updateAdWatchList(@Valid @RequestBody AdWatchListDTO adWatchListDTO) throws URISyntaxException {
        log.debug("REST request to update AdWatchList : {}", adWatchListDTO);
        if (adWatchListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdWatchListDTO result = adWatchListService.save(adWatchListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adWatchListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-watch-lists} : get all the adWatchLists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adWatchLists in body.
     */
    @GetMapping("/ad-watch-lists")
    public ResponseEntity<List<AdWatchListDTO>> getAllAdWatchLists(AdWatchListCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdWatchLists by criteria: {}", criteria);
        Page<AdWatchListDTO> page = adWatchListQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-watch-lists/count} : count all the adWatchLists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-watch-lists/count")
    public ResponseEntity<Long> countAdWatchLists(AdWatchListCriteria criteria) {
        log.debug("REST request to count AdWatchLists by criteria: {}", criteria);
        return ResponseEntity.ok().body(adWatchListQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-watch-lists/:id} : get the "id" adWatchList.
     *
     * @param id the id of the adWatchListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adWatchListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-watch-lists/{id}")
    public ResponseEntity<AdWatchListDTO> getAdWatchList(@PathVariable Long id) {
        log.debug("REST request to get AdWatchList : {}", id);
        Optional<AdWatchListDTO> adWatchListDTO = adWatchListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adWatchListDTO);
    }

    /**
     * {@code DELETE  /ad-watch-lists/:id} : delete the "id" adWatchList.
     *
     * @param id the id of the adWatchListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-watch-lists/{id}")
    public ResponseEntity<Void> deleteAdWatchList(@PathVariable Long id) {
        log.debug("REST request to delete AdWatchList : {}", id);
        adWatchListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
