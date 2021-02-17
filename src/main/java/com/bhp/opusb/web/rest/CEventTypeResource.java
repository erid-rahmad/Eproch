package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CEventTypeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CEventTypeDTO;
import com.bhp.opusb.service.dto.CEventTypeCriteria;
import com.bhp.opusb.service.CEventTypeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CEventType}.
 */
@RestController
@RequestMapping("/api")
public class CEventTypeResource {

    private final Logger log = LoggerFactory.getLogger(CEventTypeResource.class);

    private static final String ENTITY_NAME = "cEventType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CEventTypeService cEventTypeService;

    private final CEventTypeQueryService cEventTypeQueryService;

    public CEventTypeResource(CEventTypeService cEventTypeService, CEventTypeQueryService cEventTypeQueryService) {
        this.cEventTypeService = cEventTypeService;
        this.cEventTypeQueryService = cEventTypeQueryService;
    }

    /**
     * {@code POST  /c-event-types} : Create a new cEventType.
     *
     * @param cEventTypeDTO the cEventTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cEventTypeDTO, or with status {@code 400 (Bad Request)} if the cEventType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-event-types")
    public ResponseEntity<CEventTypeDTO> createCEventType(@Valid @RequestBody CEventTypeDTO cEventTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CEventType : {}", cEventTypeDTO);
        if (cEventTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cEventType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CEventTypeDTO result = cEventTypeService.save(cEventTypeDTO);
        return ResponseEntity.created(new URI("/api/c-event-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-event-types} : Updates an existing cEventType.
     *
     * @param cEventTypeDTO the cEventTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cEventTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cEventTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cEventTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-event-types")
    public ResponseEntity<CEventTypeDTO> updateCEventType(@Valid @RequestBody CEventTypeDTO cEventTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CEventType : {}", cEventTypeDTO);
        if (cEventTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CEventTypeDTO result = cEventTypeService.save(cEventTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cEventTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-event-types} : get all the cEventTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cEventTypes in body.
     */
    @GetMapping("/c-event-types")
    public ResponseEntity<List<CEventTypeDTO>> getAllCEventTypes(CEventTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CEventTypes by criteria: {}", criteria);
        Page<CEventTypeDTO> page = cEventTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-event-types/count} : count all the cEventTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-event-types/count")
    public ResponseEntity<Long> countCEventTypes(CEventTypeCriteria criteria) {
        log.debug("REST request to count CEventTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cEventTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-event-types/:id} : get the "id" cEventType.
     *
     * @param id the id of the cEventTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cEventTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-event-types/{id}")
    public ResponseEntity<CEventTypeDTO> getCEventType(@PathVariable Long id) {
        log.debug("REST request to get CEventType : {}", id);
        Optional<CEventTypeDTO> cEventTypeDTO = cEventTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cEventTypeDTO);
    }

    /**
     * {@code DELETE  /c-event-types/:id} : delete the "id" cEventType.
     *
     * @param id the id of the cEventTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-event-types/{id}")
    public ResponseEntity<Void> deleteCEventType(@PathVariable Long id) {
        log.debug("REST request to delete CEventType : {}", id);
        cEventTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
