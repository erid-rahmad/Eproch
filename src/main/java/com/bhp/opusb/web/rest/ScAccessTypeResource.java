package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.ScAccessTypeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.ScAccessTypeDTO;
import com.bhp.opusb.service.dto.ScAccessTypeCriteria;
import com.bhp.opusb.service.ScAccessTypeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.ScAccessType}.
 */
@RestController
@RequestMapping("/api")
public class ScAccessTypeResource {

    private final Logger log = LoggerFactory.getLogger(ScAccessTypeResource.class);

    private static final String ENTITY_NAME = "scAccessType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScAccessTypeService scAccessTypeService;

    private final ScAccessTypeQueryService scAccessTypeQueryService;

    public ScAccessTypeResource(ScAccessTypeService scAccessTypeService, ScAccessTypeQueryService scAccessTypeQueryService) {
        this.scAccessTypeService = scAccessTypeService;
        this.scAccessTypeQueryService = scAccessTypeQueryService;
    }

    /**
     * {@code POST  /sc-access-types} : Create a new scAccessType.
     *
     * @param scAccessTypeDTO the scAccessTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scAccessTypeDTO, or with status {@code 400 (Bad Request)} if the scAccessType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sc-access-types")
    public ResponseEntity<ScAccessTypeDTO> createScAccessType(@Valid @RequestBody ScAccessTypeDTO scAccessTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ScAccessType : {}", scAccessTypeDTO);
        if (scAccessTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new scAccessType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScAccessTypeDTO result = scAccessTypeService.save(scAccessTypeDTO);
        return ResponseEntity.created(new URI("/api/sc-access-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sc-access-types} : Updates an existing scAccessType.
     *
     * @param scAccessTypeDTO the scAccessTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scAccessTypeDTO,
     * or with status {@code 400 (Bad Request)} if the scAccessTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scAccessTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sc-access-types")
    public ResponseEntity<ScAccessTypeDTO> updateScAccessType(@Valid @RequestBody ScAccessTypeDTO scAccessTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ScAccessType : {}", scAccessTypeDTO);
        if (scAccessTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScAccessTypeDTO result = scAccessTypeService.save(scAccessTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scAccessTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sc-access-types} : get all the scAccessTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scAccessTypes in body.
     */
    @GetMapping("/sc-access-types")
    public ResponseEntity<List<ScAccessTypeDTO>> getAllScAccessTypes(ScAccessTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ScAccessTypes by criteria: {}", criteria);
        Page<ScAccessTypeDTO> page = scAccessTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sc-access-types/count} : count all the scAccessTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sc-access-types/count")
    public ResponseEntity<Long> countScAccessTypes(ScAccessTypeCriteria criteria) {
        log.debug("REST request to count ScAccessTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(scAccessTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sc-access-types/:id} : get the "id" scAccessType.
     *
     * @param id the id of the scAccessTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scAccessTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sc-access-types/{id}")
    public ResponseEntity<ScAccessTypeDTO> getScAccessType(@PathVariable Long id) {
        log.debug("REST request to get ScAccessType : {}", id);
        Optional<ScAccessTypeDTO> scAccessTypeDTO = scAccessTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scAccessTypeDTO);
    }

    /**
     * {@code DELETE  /sc-access-types/:id} : delete the "id" scAccessType.
     *
     * @param id the id of the scAccessTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sc-access-types/{id}")
    public ResponseEntity<Void> deleteScAccessType(@PathVariable Long id) {
        log.debug("REST request to delete ScAccessType : {}", id);
        scAccessTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
