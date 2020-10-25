package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.AdUserAuthorityQueryService;
import com.bhp.opusb.service.AdUserAuthorityService;
import com.bhp.opusb.service.dto.AdUserAuthorityCriteria;
import com.bhp.opusb.service.dto.AdUserAuthorityDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.AdUserAuthority}.
 */
@RestController
@RequestMapping("/api")
public class AdUserAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AdUserAuthorityResource.class);

    private static final String ENTITY_NAME = "adUserAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdUserAuthorityService adUserAuthorityService;

    private final AdUserAuthorityQueryService adUserAuthorityQueryService;

    public AdUserAuthorityResource(AdUserAuthorityService adUserAuthorityService, AdUserAuthorityQueryService adUserAuthorityQueryService) {
        this.adUserAuthorityService = adUserAuthorityService;
        this.adUserAuthorityQueryService = adUserAuthorityQueryService;
    }

    /**
     * {@code POST  /ad-user-authorities} : Create a new adUserAuthority.
     *
     * @param adUserAuthorityDTO the adUserAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adUserAuthorityDTO, or with status {@code 400 (Bad Request)} if the adUserAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-user-authorities")
    public ResponseEntity<AdUserAuthorityDTO> createAdUserAuthority(@Valid @RequestBody AdUserAuthorityDTO adUserAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save AdUserAuthority : {}", adUserAuthorityDTO);
        if (adUserAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new adUserAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdUserAuthorityDTO result = adUserAuthorityService.save(adUserAuthorityDTO);
        return ResponseEntity.created(new URI("/api/ad-user-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-user-authorities} : Updates an existing adUserAuthority.
     *
     * @param adUserAuthorityDTO the adUserAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adUserAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the adUserAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adUserAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-user-authorities")
    public ResponseEntity<AdUserAuthorityDTO> updateAdUserAuthority(@Valid @RequestBody AdUserAuthorityDTO adUserAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update AdUserAuthority : {}", adUserAuthorityDTO);
        if (adUserAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdUserAuthorityDTO result = adUserAuthorityService.save(adUserAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adUserAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-user-authorities} : get all the adUserAuthorities.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adUserAuthorities in body.
     */
    @GetMapping("/ad-user-authorities")
    public ResponseEntity<List<AdUserAuthorityDTO>> getAllAdUserAuthorities(AdUserAuthorityCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdUserAuthorities by criteria: {}", criteria);
        Page<AdUserAuthorityDTO> page = adUserAuthorityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-user-authorities/count} : count all the adUserAuthorities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-user-authorities/count")
    public ResponseEntity<Long> countAdUserAuthorities(AdUserAuthorityCriteria criteria) {
        log.debug("REST request to count AdUserAuthorities by criteria: {}", criteria);
        return ResponseEntity.ok().body(adUserAuthorityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-user-authorities/:id} : get the "id" adUserAuthority.
     *
     * @param id the id of the adUserAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adUserAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-user-authorities/{id}")
    public ResponseEntity<AdUserAuthorityDTO> getAdUserAuthority(@PathVariable Long id) {
        log.debug("REST request to get AdUserAuthority : {}", id);
        Optional<AdUserAuthorityDTO> adUserAuthorityDTO = adUserAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adUserAuthorityDTO);
    }

    /**
     * {@code DELETE  /ad-user-authorities/:id} : delete the "id" adUserAuthority.
     *
     * @param id the id of the adUserAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-user-authorities/{id}")
    public ResponseEntity<Void> deleteAdUserAuthority(@PathVariable Long id) {
        log.debug("REST request to delete AdUserAuthority : {}", id);
        adUserAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
