package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.AdUserQueryService;
import com.bhp.opusb.service.AdUserService;
import com.bhp.opusb.service.dto.AdUserCriteria;
import com.bhp.opusb.service.dto.AdUserDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.AdUser}.
 */
@RestController
@RequestMapping("/api")
public class AdUserResource {

    private final Logger log = LoggerFactory.getLogger(AdUserResource.class);

    private static final String ENTITY_NAME = "adUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdUserService adUserService;

    private final AdUserQueryService adUserQueryService;

    public AdUserResource(AdUserService adUserService, AdUserQueryService adUserQueryService) {
        this.adUserService = adUserService;
        this.adUserQueryService = adUserQueryService;
    }

    /**
     * {@code POST  /ad-users} : Create a new adUser.
     *
     * @param adUserDTO the adUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adUserDTO, or with status {@code 400 (Bad Request)} if the adUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-users")
    public ResponseEntity<AdUserDTO> createAdUser(@Valid @RequestBody AdUserDTO adUserDTO) throws URISyntaxException {
        log.debug("REST request to save AdUser : {}", adUserDTO);
        if (adUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new adUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdUserDTO result = adUserService.save(adUserDTO);
        return ResponseEntity.created(new URI("/api/ad-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-users} : Updates an existing adUser.
     *
     * @param adUserDTO the adUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adUserDTO,
     * or with status {@code 400 (Bad Request)} if the adUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-users")
    public ResponseEntity<AdUserDTO> updateAdUser(@Valid @RequestBody AdUserDTO adUserDTO) throws URISyntaxException {
        log.debug("REST request to update AdUser : {}", adUserDTO);
        if (adUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdUserDTO result = adUserService.save(adUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-users} : get all the adUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adUsers in body.
     */
    @GetMapping("/ad-users")
    public ResponseEntity<List<AdUserDTO>> getAllAdUsers(AdUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdUsers by criteria: {}", criteria);
        Page<AdUserDTO> page = adUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-users/count} : count all the adUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-users/count")
    public ResponseEntity<Long> countAdUsers(AdUserCriteria criteria) {
        log.debug("REST request to count AdUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(adUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-users/:id} : get the "id" adUser.
     *
     * @param id the id of the adUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-users/{id}")
    public ResponseEntity<AdUserDTO> getAdUser(@PathVariable Long id) {
        log.debug("REST request to get AdUser : {}", id);
        Optional<AdUserDTO> adUserDTO = adUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adUserDTO);
    }

    /**
     * {@code DELETE  /ad-users/:id} : delete the "id" adUser.
     *
     * @param id the id of the adUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-users/{id}")
    public ResponseEntity<Void> deleteAdUser(@PathVariable Long id) {
        log.debug("REST request to delete AdUser : {}", id);
        adUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
