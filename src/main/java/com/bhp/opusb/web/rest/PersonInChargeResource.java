package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.PersonInChargeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.PersonInChargeDTO;
import com.bhp.opusb.service.dto.PersonInChargeCriteria;
import com.bhp.opusb.service.PersonInChargeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.PersonInCharge}.
 */
@RestController
@RequestMapping("/api")
public class PersonInChargeResource {

    private final Logger log = LoggerFactory.getLogger(PersonInChargeResource.class);

    private static final String ENTITY_NAME = "personInCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonInChargeService personInChargeService;

    private final PersonInChargeQueryService personInChargeQueryService;

    public PersonInChargeResource(PersonInChargeService personInChargeService, PersonInChargeQueryService personInChargeQueryService) {
        this.personInChargeService = personInChargeService;
        this.personInChargeQueryService = personInChargeQueryService;
    }

    /**
     * {@code POST  /person-in-charges} : Create a new personInCharge.
     *
     * @param personInChargeDTO the personInChargeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personInChargeDTO, or with status {@code 400 (Bad Request)} if the personInCharge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-in-charges")
    public ResponseEntity<PersonInChargeDTO> createPersonInCharge(@Valid @RequestBody PersonInChargeDTO personInChargeDTO) throws URISyntaxException {
        log.debug("REST request to save PersonInCharge : {}", personInChargeDTO);
        if (personInChargeDTO.getId() != null) {
            throw new BadRequestAlertException("A new personInCharge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonInChargeDTO result = personInChargeService.save(personInChargeDTO);
        return ResponseEntity.created(new URI("/api/person-in-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-in-charges} : Updates an existing personInCharge.
     *
     * @param personInChargeDTO the personInChargeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personInChargeDTO,
     * or with status {@code 400 (Bad Request)} if the personInChargeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personInChargeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-in-charges")
    public ResponseEntity<PersonInChargeDTO> updatePersonInCharge(@Valid @RequestBody PersonInChargeDTO personInChargeDTO) throws URISyntaxException {
        log.debug("REST request to update PersonInCharge : {}", personInChargeDTO);
        if (personInChargeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PersonInChargeDTO result = personInChargeService.save(personInChargeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personInChargeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /person-in-charges} : get all the personInCharges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personInCharges in body.
     */
    @GetMapping("/person-in-charges")
    public ResponseEntity<List<PersonInChargeDTO>> getAllPersonInCharges(PersonInChargeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PersonInCharges by criteria: {}", criteria);
        Page<PersonInChargeDTO> page = personInChargeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /person-in-charges/count} : count all the personInCharges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/person-in-charges/count")
    public ResponseEntity<Long> countPersonInCharges(PersonInChargeCriteria criteria) {
        log.debug("REST request to count PersonInCharges by criteria: {}", criteria);
        return ResponseEntity.ok().body(personInChargeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /person-in-charges/:id} : get the "id" personInCharge.
     *
     * @param id the id of the personInChargeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personInChargeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-in-charges/{id}")
    public ResponseEntity<PersonInChargeDTO> getPersonInCharge(@PathVariable Long id) {
        log.debug("REST request to get PersonInCharge : {}", id);
        Optional<PersonInChargeDTO> personInChargeDTO = personInChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personInChargeDTO);
    }

    /**
     * {@code DELETE  /person-in-charges/:id} : delete the "id" personInCharge.
     *
     * @param id the id of the personInChargeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-in-charges/{id}")
    public ResponseEntity<Void> deletePersonInCharge(@PathVariable Long id) {
        log.debug("REST request to delete PersonInCharge : {}", id);
        personInChargeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
