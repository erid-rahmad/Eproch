package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalRegistrationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalRegistrationDTO;
import com.bhp.opusb.service.dto.MPrequalRegistrationCriteria;
import com.bhp.opusb.service.MPrequalRegistrationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalRegistration}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalRegistrationResource.class);

    private static final String ENTITY_NAME = "mPrequalRegistration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalRegistrationService mPrequalRegistrationService;

    private final MPrequalRegistrationQueryService mPrequalRegistrationQueryService;

    public MPrequalRegistrationResource(MPrequalRegistrationService mPrequalRegistrationService, MPrequalRegistrationQueryService mPrequalRegistrationQueryService) {
        this.mPrequalRegistrationService = mPrequalRegistrationService;
        this.mPrequalRegistrationQueryService = mPrequalRegistrationQueryService;
    }

    /**
     * {@code POST  /m-prequal-registrations} : Create a new mPrequalRegistration.
     *
     * @param mPrequalRegistrationDTO the mPrequalRegistrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalRegistrationDTO, or with status {@code 400 (Bad Request)} if the mPrequalRegistration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequal-registrations")
    public ResponseEntity<MPrequalRegistrationDTO> createMPrequalRegistration(@Valid @RequestBody MPrequalRegistrationDTO mPrequalRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalRegistration : {}", mPrequalRegistrationDTO);
        if (mPrequalRegistrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalRegistrationDTO result = mPrequalRegistrationService.save(mPrequalRegistrationDTO);
        return ResponseEntity.created(new URI("/api/m-prequal-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequal-registrations} : Updates an existing mPrequalRegistration.
     *
     * @param mPrequalRegistrationDTO the mPrequalRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalRegistrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequal-registrations")
    public ResponseEntity<MPrequalRegistrationDTO> updateMPrequalRegistration(@Valid @RequestBody MPrequalRegistrationDTO mPrequalRegistrationDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalRegistration : {}", mPrequalRegistrationDTO);
        if (mPrequalRegistrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalRegistrationDTO result = mPrequalRegistrationService.save(mPrequalRegistrationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalRegistrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequal-registrations} : get all the mPrequalRegistrations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalRegistrations in body.
     */
    @GetMapping("/m-prequal-registrations")
    public ResponseEntity<List<MPrequalRegistrationDTO>> getAllMPrequalRegistrations(MPrequalRegistrationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalRegistrations by criteria: {}", criteria);
        Page<MPrequalRegistrationDTO> page = mPrequalRegistrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequal-registrations/count} : count all the mPrequalRegistrations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequal-registrations/count")
    public ResponseEntity<Long> countMPrequalRegistrations(MPrequalRegistrationCriteria criteria) {
        log.debug("REST request to count MPrequalRegistrations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalRegistrationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequal-registrations/:id} : get the "id" mPrequalRegistration.
     *
     * @param id the id of the mPrequalRegistrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalRegistrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequal-registrations/{id}")
    public ResponseEntity<MPrequalRegistrationDTO> getMPrequalRegistration(@PathVariable Long id) {
        log.debug("REST request to get MPrequalRegistration : {}", id);
        Optional<MPrequalRegistrationDTO> mPrequalRegistrationDTO = mPrequalRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalRegistrationDTO);
    }

    /**
     * {@code DELETE  /m-prequal-registrations/:id} : delete the "id" mPrequalRegistration.
     *
     * @param id the id of the mPrequalRegistrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequal-registrations/{id}")
    public ResponseEntity<Void> deleteMPrequalRegistration(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalRegistration : {}", id);
        mPrequalRegistrationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
