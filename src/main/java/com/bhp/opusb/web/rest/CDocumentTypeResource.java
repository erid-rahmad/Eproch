package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.CDocumentTypeQueryService;
import com.bhp.opusb.service.CDocumentTypeService;
import com.bhp.opusb.service.dto.CDocumentTypeCriteria;
import com.bhp.opusb.service.dto.CDocumentTypeDTO;
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
 * REST controller for managing {@link com.bhp.opusb.domain.CDocumentType}.
 */
@RestController
@RequestMapping("/api")
public class CDocumentTypeResource {

    private final Logger log = LoggerFactory.getLogger(CDocumentTypeResource.class);

    private static final String ENTITY_NAME = "cDocumentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CDocumentTypeService cDocumentTypeService;

    private final CDocumentTypeQueryService cDocumentTypeQueryService;

    public CDocumentTypeResource(CDocumentTypeService cDocumentTypeService, CDocumentTypeQueryService cDocumentTypeQueryService) {
        this.cDocumentTypeService = cDocumentTypeService;
        this.cDocumentTypeQueryService = cDocumentTypeQueryService;
    }

    /**
     * {@code POST  /c-document-types} : Create a new cDocumentType.
     *
     * @param cDocumentTypeDTO the cDocumentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cDocumentTypeDTO, or with status {@code 400 (Bad Request)} if the cDocumentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-document-types")
    public ResponseEntity<CDocumentTypeDTO> createCDocumentType(@Valid @RequestBody CDocumentTypeDTO cDocumentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CDocumentType : {}", cDocumentTypeDTO);
        if (cDocumentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cDocumentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CDocumentTypeDTO result = cDocumentTypeService.save(cDocumentTypeDTO);
        return ResponseEntity.created(new URI("/api/c-document-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-document-types} : Updates an existing cDocumentType.
     *
     * @param cDocumentTypeDTO the cDocumentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cDocumentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cDocumentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cDocumentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-document-types")
    public ResponseEntity<CDocumentTypeDTO> updateCDocumentType(@Valid @RequestBody CDocumentTypeDTO cDocumentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CDocumentType : {}", cDocumentTypeDTO);
        if (cDocumentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CDocumentTypeDTO result = cDocumentTypeService.save(cDocumentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cDocumentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-document-types} : get all the cDocumentTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cDocumentTypes in body.
     */
    @GetMapping("/c-document-types")
    public ResponseEntity<List<CDocumentTypeDTO>> getAllCDocumentTypes(CDocumentTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CDocumentTypes by criteria: {}", criteria);
        Page<CDocumentTypeDTO> page = cDocumentTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-document-types/count} : count all the cDocumentTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-document-types/count")
    public ResponseEntity<Long> countCDocumentTypes(CDocumentTypeCriteria criteria) {
        log.debug("REST request to count CDocumentTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cDocumentTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-document-types/:id} : get the "id" cDocumentType.
     *
     * @param id the id of the cDocumentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cDocumentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-document-types/{id}")
    public ResponseEntity<CDocumentTypeDTO> getCDocumentType(@PathVariable Long id) {
        log.debug("REST request to get CDocumentType : {}", id);
        Optional<CDocumentTypeDTO> cDocumentTypeDTO = cDocumentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cDocumentTypeDTO);
    }

    /**
     * {@code DELETE  /c-document-types/:id} : delete the "id" cDocumentType.
     *
     * @param id the id of the cDocumentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-document-types/{id}")
    public ResponseEntity<Void> deleteCDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete CDocumentType : {}", id);
        cDocumentTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
