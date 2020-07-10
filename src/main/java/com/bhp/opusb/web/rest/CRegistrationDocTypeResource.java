package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CRegistrationDocTypeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CRegistrationDocTypeDTO;
import com.bhp.opusb.service.dto.CRegistrationDocTypeCriteria;
import com.bhp.opusb.service.CRegistrationDocTypeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CRegistrationDocType}.
 */
@RestController
@RequestMapping("/api")
public class CRegistrationDocTypeResource {

    private final Logger log = LoggerFactory.getLogger(CRegistrationDocTypeResource.class);

    private static final String ENTITY_NAME = "cRegistrationDocType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CRegistrationDocTypeService cRegistrationDocTypeService;

    private final CRegistrationDocTypeQueryService cRegistrationDocTypeQueryService;

    public CRegistrationDocTypeResource(CRegistrationDocTypeService cRegistrationDocTypeService, CRegistrationDocTypeQueryService cRegistrationDocTypeQueryService) {
        this.cRegistrationDocTypeService = cRegistrationDocTypeService;
        this.cRegistrationDocTypeQueryService = cRegistrationDocTypeQueryService;
    }

    /**
     * {@code POST  /c-registration-doc-types} : Create a new cRegistrationDocType.
     *
     * @param cRegistrationDocTypeDTO the cRegistrationDocTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cRegistrationDocTypeDTO, or with status {@code 400 (Bad Request)} if the cRegistrationDocType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-registration-doc-types")
    public ResponseEntity<CRegistrationDocTypeDTO> createCRegistrationDocType(@Valid @RequestBody CRegistrationDocTypeDTO cRegistrationDocTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CRegistrationDocType : {}", cRegistrationDocTypeDTO);
        if (cRegistrationDocTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cRegistrationDocType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CRegistrationDocTypeDTO result = cRegistrationDocTypeService.save(cRegistrationDocTypeDTO);
        return ResponseEntity.created(new URI("/api/c-registration-doc-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-registration-doc-types} : Updates an existing cRegistrationDocType.
     *
     * @param cRegistrationDocTypeDTO the cRegistrationDocTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cRegistrationDocTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cRegistrationDocTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cRegistrationDocTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-registration-doc-types")
    public ResponseEntity<CRegistrationDocTypeDTO> updateCRegistrationDocType(@Valid @RequestBody CRegistrationDocTypeDTO cRegistrationDocTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CRegistrationDocType : {}", cRegistrationDocTypeDTO);
        if (cRegistrationDocTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CRegistrationDocTypeDTO result = cRegistrationDocTypeService.save(cRegistrationDocTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cRegistrationDocTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-registration-doc-types} : get all the cRegistrationDocTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cRegistrationDocTypes in body.
     */
    @GetMapping("/c-registration-doc-types")
    public ResponseEntity<List<CRegistrationDocTypeDTO>> getAllCRegistrationDocTypes(CRegistrationDocTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CRegistrationDocTypes by criteria: {}", criteria);
        Page<CRegistrationDocTypeDTO> page = cRegistrationDocTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-registration-doc-types/count} : count all the cRegistrationDocTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-registration-doc-types/count")
    public ResponseEntity<Long> countCRegistrationDocTypes(CRegistrationDocTypeCriteria criteria) {
        log.debug("REST request to count CRegistrationDocTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cRegistrationDocTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-registration-doc-types/:id} : get the "id" cRegistrationDocType.
     *
     * @param id the id of the cRegistrationDocTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cRegistrationDocTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-registration-doc-types/{id}")
    public ResponseEntity<CRegistrationDocTypeDTO> getCRegistrationDocType(@PathVariable Long id) {
        log.debug("REST request to get CRegistrationDocType : {}", id);
        Optional<CRegistrationDocTypeDTO> cRegistrationDocTypeDTO = cRegistrationDocTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cRegistrationDocTypeDTO);
    }

    /**
     * {@code DELETE  /c-registration-doc-types/:id} : delete the "id" cRegistrationDocType.
     *
     * @param id the id of the cRegistrationDocTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-registration-doc-types/{id}")
    public ResponseEntity<Void> deleteCRegistrationDocType(@PathVariable Long id) {
        log.debug("REST request to delete CRegistrationDocType : {}", id);
        cRegistrationDocTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
