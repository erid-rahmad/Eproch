package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CConvertionTypeService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CConvertionTypeDTO;
import com.bhp.opusb.service.dto.CConvertionTypeCriteria;
import com.bhp.opusb.service.CConvertionTypeQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CConvertionType}.
 */
@RestController
@RequestMapping("/api")
public class CConvertionTypeResource {

    private final Logger log = LoggerFactory.getLogger(CConvertionTypeResource.class);

    private static final String ENTITY_NAME = "cConvertionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CConvertionTypeService cConvertionTypeService;

    private final CConvertionTypeQueryService cConvertionTypeQueryService;

    public CConvertionTypeResource(CConvertionTypeService cConvertionTypeService, CConvertionTypeQueryService cConvertionTypeQueryService) {
        this.cConvertionTypeService = cConvertionTypeService;
        this.cConvertionTypeQueryService = cConvertionTypeQueryService;
    }

    /**
     * {@code POST  /c-convertion-types} : Create a new cConvertionType.
     *
     * @param cConvertionTypeDTO the cConvertionTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cConvertionTypeDTO, or with status {@code 400 (Bad Request)} if the cConvertionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-convertion-types")
    public ResponseEntity<CConvertionTypeDTO> createCConvertionType(@Valid @RequestBody CConvertionTypeDTO cConvertionTypeDTO) throws URISyntaxException {
        log.debug("REST request to save CConvertionType : {}", cConvertionTypeDTO);
        if (cConvertionTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cConvertionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CConvertionTypeDTO result = cConvertionTypeService.save(cConvertionTypeDTO);
        return ResponseEntity.created(new URI("/api/c-convertion-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-convertion-types} : Updates an existing cConvertionType.
     *
     * @param cConvertionTypeDTO the cConvertionTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cConvertionTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cConvertionTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cConvertionTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-convertion-types")
    public ResponseEntity<CConvertionTypeDTO> updateCConvertionType(@Valid @RequestBody CConvertionTypeDTO cConvertionTypeDTO) throws URISyntaxException {
        log.debug("REST request to update CConvertionType : {}", cConvertionTypeDTO);
        if (cConvertionTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CConvertionTypeDTO result = cConvertionTypeService.save(cConvertionTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cConvertionTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-convertion-types} : get all the cConvertionTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cConvertionTypes in body.
     */
    @GetMapping("/c-convertion-types")
    public ResponseEntity<List<CConvertionTypeDTO>> getAllCConvertionTypes(CConvertionTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CConvertionTypes by criteria: {}", criteria);
        Page<CConvertionTypeDTO> page = cConvertionTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-convertion-types/count} : count all the cConvertionTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-convertion-types/count")
    public ResponseEntity<Long> countCConvertionTypes(CConvertionTypeCriteria criteria) {
        log.debug("REST request to count CConvertionTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(cConvertionTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-convertion-types/:id} : get the "id" cConvertionType.
     *
     * @param id the id of the cConvertionTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cConvertionTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-convertion-types/{id}")
    public ResponseEntity<CConvertionTypeDTO> getCConvertionType(@PathVariable Long id) {
        log.debug("REST request to get CConvertionType : {}", id);
        Optional<CConvertionTypeDTO> cConvertionTypeDTO = cConvertionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cConvertionTypeDTO);
    }

    /**
     * {@code DELETE  /c-convertion-types/:id} : delete the "id" cConvertionType.
     *
     * @param id the id of the cConvertionTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-convertion-types/{id}")
    public ResponseEntity<Void> deleteCConvertionType(@PathVariable Long id) {
        log.debug("REST request to delete CConvertionType : {}", id);
        cConvertionTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
