package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdButtonService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdButtonDTO;
import com.bhp.opusb.service.dto.AdButtonCriteria;
import com.bhp.opusb.service.AdButtonQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdButton}.
 */
@RestController
@RequestMapping("/api")
public class AdButtonResource {

    private final Logger log = LoggerFactory.getLogger(AdButtonResource.class);

    private static final String ENTITY_NAME = "adButton";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdButtonService adButtonService;

    private final AdButtonQueryService adButtonQueryService;

    public AdButtonResource(AdButtonService adButtonService, AdButtonQueryService adButtonQueryService) {
        this.adButtonService = adButtonService;
        this.adButtonQueryService = adButtonQueryService;
    }

    /**
     * {@code POST  /ad-buttons} : Create a new adButton.
     *
     * @param adButtonDTO the adButtonDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adButtonDTO, or with status {@code 400 (Bad Request)} if the adButton has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-buttons")
    public ResponseEntity<AdButtonDTO> createAdButton(@Valid @RequestBody AdButtonDTO adButtonDTO) throws URISyntaxException {
        log.debug("REST request to save AdButton : {}", adButtonDTO);
        if (adButtonDTO.getId() != null) {
            throw new BadRequestAlertException("A new adButton cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdButtonDTO result = adButtonService.save(adButtonDTO);
        return ResponseEntity.created(new URI("/api/ad-buttons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-buttons} : Updates an existing adButton.
     *
     * @param adButtonDTO the adButtonDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adButtonDTO,
     * or with status {@code 400 (Bad Request)} if the adButtonDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adButtonDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-buttons")
    public ResponseEntity<AdButtonDTO> updateAdButton(@Valid @RequestBody AdButtonDTO adButtonDTO) throws URISyntaxException {
        log.debug("REST request to update AdButton : {}", adButtonDTO);
        if (adButtonDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdButtonDTO result = adButtonService.save(adButtonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adButtonDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-buttons} : get all the adButtons.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adButtons in body.
     */
    @GetMapping("/ad-buttons")
    public ResponseEntity<List<AdButtonDTO>> getAllAdButtons(AdButtonCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdButtons by criteria: {}", criteria);
        Page<AdButtonDTO> page = adButtonQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-buttons/count} : count all the adButtons.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-buttons/count")
    public ResponseEntity<Long> countAdButtons(AdButtonCriteria criteria) {
        log.debug("REST request to count AdButtons by criteria: {}", criteria);
        return ResponseEntity.ok().body(adButtonQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-buttons/:id} : get the "id" adButton.
     *
     * @param id the id of the adButtonDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adButtonDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-buttons/{id}")
    public ResponseEntity<AdButtonDTO> getAdButton(@PathVariable Long id) {
        log.debug("REST request to get AdButton : {}", id);
        Optional<AdButtonDTO> adButtonDTO = adButtonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adButtonDTO);
    }

    /**
     * {@code DELETE  /ad-buttons/:id} : delete the "id" adButton.
     *
     * @param id the id of the adButtonDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-buttons/{id}")
    public ResponseEntity<Void> deleteAdButton(@PathVariable Long id) {
        log.debug("REST request to delete AdButton : {}", id);
        adButtonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
