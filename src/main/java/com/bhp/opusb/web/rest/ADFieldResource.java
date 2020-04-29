package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.ADFieldQueryService;
import com.bhp.opusb.service.ADFieldService;
import com.bhp.opusb.service.dto.ADFieldCriteria;
import com.bhp.opusb.service.dto.ADFieldDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.ADField}.
 */
@RestController
@RequestMapping("/api")
public class ADFieldResource {

    private final Logger log = LoggerFactory.getLogger(ADFieldResource.class);

    private static final String ENTITY_NAME = "aDField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ADFieldService aDFieldService;

    private final ADFieldQueryService aDFieldQueryService;

    public ADFieldResource(ADFieldService aDFieldService, ADFieldQueryService aDFieldQueryService) {
        this.aDFieldService = aDFieldService;
        this.aDFieldQueryService = aDFieldQueryService;
    }

    /**
     * {@code POST  /ad-fields} : Create a new aDField.
     *
     * @param aDFieldDTO the aDFieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aDFieldDTO, or with status {@code 400 (Bad Request)} if the aDField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ad-fields")
    public ResponseEntity<ADFieldDTO> createADField(@Valid @RequestBody ADFieldDTO aDFieldDTO) throws URISyntaxException {
        log.debug("REST request to save ADField : {}", aDFieldDTO);
        if (aDFieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new aDField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ADFieldDTO result = aDFieldService.save(aDFieldDTO);
        return ResponseEntity.created(new URI("/api/ad-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ad-fields} : Updates an existing aDField.
     *
     * @param aDFieldDTO the aDFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aDFieldDTO,
     * or with status {@code 400 (Bad Request)} if the aDFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aDFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ad-fields")
    public ResponseEntity<ADFieldDTO> updateADField(@Valid @RequestBody ADFieldDTO aDFieldDTO) throws URISyntaxException {
        log.debug("REST request to update ADField : {}", aDFieldDTO);
        if (aDFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ADFieldDTO result = aDFieldService.save(aDFieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aDFieldDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ad-fields} : get all the aDFields.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aDFields in body.
     */
    @GetMapping("/ad-fields")
    public ResponseEntity<List<ADFieldDTO>> getAllADFields(ADFieldCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ADFields by criteria: {}", criteria);
        Page<ADFieldDTO> page = aDFieldQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ad-fields/count} : count all the aDFields.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ad-fields/count")
    public ResponseEntity<Long> countADFields(ADFieldCriteria criteria) {
        log.debug("REST request to count ADFields by criteria: {}", criteria);
        return ResponseEntity.ok().body(aDFieldQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ad-fields/:id} : get the "id" aDField.
     *
     * @param id the id of the aDFieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aDFieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ad-fields/{id}")
    public ResponseEntity<ADFieldDTO> getADField(@PathVariable Long id) {
        log.debug("REST request to get ADField : {}", id);
        Optional<ADFieldDTO> aDFieldDTO = aDFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(aDFieldDTO);
    }

    /**
     * {@code DELETE  /ad-fields/:id} : delete the "id" aDField.
     *
     * @param id the id of the aDFieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ad-fields/{id}")
    public ResponseEntity<Void> deleteADField(@PathVariable Long id) {
        log.debug("REST request to delete ADField : {}", id);
        aDFieldService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
