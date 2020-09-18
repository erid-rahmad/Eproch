package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.AdFormService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.AdFormDTO;
import com.bhp.opusb.service.dto.AdFormCriteria;
import com.bhp.opusb.service.AdFormQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.AdForm}.
 */
@RestController
@RequestMapping("/api")
public class AdFormResource {

    private final Logger log = LoggerFactory.getLogger(AdFormResource.class);

    private static final String ENTITY_NAME = "adForm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdFormService adFormService;

    private final AdFormQueryService adFormQueryService;

    public AdFormResource(AdFormService adFormService, AdFormQueryService adFormQueryService) {
        this.adFormService = adFormService;
        this.adFormQueryService = adFormQueryService;
    }

    /**
     * {@code POST  /ad-forms} : Create a new adForm.
     *
     * @param adFormDTO the adFormDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adFormDTO, or with status {@code 400 (Bad Request)} if the adForm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-forms")
    public ResponseEntity<AdFormDTO> createAdForm(@Valid @RequestBody AdFormDTO adFormDTO) throws URISyntaxException {
        log.debug("REST request to save AdForm : {}", adFormDTO);
        if (adFormDTO.getId() != null) {
            throw new BadRequestAlertException("A new adForm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdFormDTO result = adFormService.save(adFormDTO);
        return ResponseEntity.created(new URI("/api/ad-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-forms} : Updates an existing adForm.
     *
     * @param adFormDTO the adFormDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adFormDTO,
     * or with status {@code 400 (Bad Request)} if the adFormDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adFormDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-forms")
    public ResponseEntity<AdFormDTO> updateAdForm(@Valid @RequestBody AdFormDTO adFormDTO) throws URISyntaxException {
        log.debug("REST request to update AdForm : {}", adFormDTO);
        if (adFormDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdFormDTO result = adFormService.save(adFormDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adFormDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-forms} : get all the adForms.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adForms in body.
     */
    @GetMapping("/ad-forms")
    public ResponseEntity<List<AdFormDTO>> getAllAdForms(AdFormCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdForms by criteria: {}", criteria);
        Page<AdFormDTO> page = adFormQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-forms/count} : count all the adForms.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-forms/count")
    public ResponseEntity<Long> countAdForms(AdFormCriteria criteria) {
        log.debug("REST request to count AdForms by criteria: {}", criteria);
        return ResponseEntity.ok().body(adFormQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-forms/:id} : get the "id" adForm.
     *
     * @param id the id of the adFormDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adFormDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-forms/{id}")
    public ResponseEntity<AdFormDTO> getAdForm(@PathVariable Long id) {
        log.debug("REST request to get AdForm : {}", id);
        Optional<AdFormDTO> adFormDTO = adFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adFormDTO);
    }

    /**
     * {@code DELETE  /ad-forms/:id} : delete the "id" adForm.
     *
     * @param id the id of the adFormDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-forms/{id}")
    public ResponseEntity<Void> deleteAdForm(@PathVariable Long id) {
        log.debug("REST request to delete AdForm : {}", id);
        adFormService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
