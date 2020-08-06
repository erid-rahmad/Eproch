package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CPicBusinessCatService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CPicBusinessCatDTO;
import com.bhp.opusb.service.dto.CPicBusinessCatCriteria;
import com.bhp.opusb.service.CPicBusinessCatQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CPicBusinessCat}.
 */
@RestController
@RequestMapping("/api")
public class CPicBusinessCatResource {

    private final Logger log = LoggerFactory.getLogger(CPicBusinessCatResource.class);

    private static final String ENTITY_NAME = "cPicBusinessCat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CPicBusinessCatService cPicBusinessCatService;

    private final CPicBusinessCatQueryService cPicBusinessCatQueryService;

    public CPicBusinessCatResource(CPicBusinessCatService cPicBusinessCatService, CPicBusinessCatQueryService cPicBusinessCatQueryService) {
        this.cPicBusinessCatService = cPicBusinessCatService;
        this.cPicBusinessCatQueryService = cPicBusinessCatQueryService;
    }

    /**
     * {@code POST  /c-pic-business-cats} : Create a new cPicBusinessCat.
     *
     * @param cPicBusinessCatDTO the cPicBusinessCatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cPicBusinessCatDTO, or with status {@code 400 (Bad Request)} if the cPicBusinessCat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-pic-business-cats")
    public ResponseEntity<CPicBusinessCatDTO> createCPicBusinessCat(@Valid @RequestBody CPicBusinessCatDTO cPicBusinessCatDTO) throws URISyntaxException {
        log.debug("REST request to save CPicBusinessCat : {}", cPicBusinessCatDTO);
        if (cPicBusinessCatDTO.getId() != null) {
            throw new BadRequestAlertException("A new cPicBusinessCat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CPicBusinessCatDTO result = cPicBusinessCatService.save(cPicBusinessCatDTO);
        return ResponseEntity.created(new URI("/api/c-pic-business-cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-pic-business-cats} : Updates an existing cPicBusinessCat.
     *
     * @param cPicBusinessCatDTO the cPicBusinessCatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cPicBusinessCatDTO,
     * or with status {@code 400 (Bad Request)} if the cPicBusinessCatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cPicBusinessCatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-pic-business-cats")
    public ResponseEntity<CPicBusinessCatDTO> updateCPicBusinessCat(@Valid @RequestBody CPicBusinessCatDTO cPicBusinessCatDTO) throws URISyntaxException {
        log.debug("REST request to update CPicBusinessCat : {}", cPicBusinessCatDTO);
        if (cPicBusinessCatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CPicBusinessCatDTO result = cPicBusinessCatService.save(cPicBusinessCatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cPicBusinessCatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-pic-business-cats} : get all the cPicBusinessCats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cPicBusinessCats in body.
     */
    @GetMapping("/c-pic-business-cats")
    public ResponseEntity<List<CPicBusinessCatDTO>> getAllCPicBusinessCats(CPicBusinessCatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CPicBusinessCats by criteria: {}", criteria);
        Page<CPicBusinessCatDTO> page = cPicBusinessCatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-pic-business-cats/count} : count all the cPicBusinessCats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-pic-business-cats/count")
    public ResponseEntity<Long> countCPicBusinessCats(CPicBusinessCatCriteria criteria) {
        log.debug("REST request to count CPicBusinessCats by criteria: {}", criteria);
        return ResponseEntity.ok().body(cPicBusinessCatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-pic-business-cats/:id} : get the "id" cPicBusinessCat.
     *
     * @param id the id of the cPicBusinessCatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cPicBusinessCatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-pic-business-cats/{id}")
    public ResponseEntity<CPicBusinessCatDTO> getCPicBusinessCat(@PathVariable Long id) {
        log.debug("REST request to get CPicBusinessCat : {}", id);
        Optional<CPicBusinessCatDTO> cPicBusinessCatDTO = cPicBusinessCatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cPicBusinessCatDTO);
    }

    /**
     * {@code DELETE  /c-pic-business-cats/:id} : delete the "id" cPicBusinessCat.
     *
     * @param id the id of the cPicBusinessCatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-pic-business-cats/{id}")
    public ResponseEntity<Void> deleteCPicBusinessCat(@PathVariable Long id) {
        log.debug("REST request to delete CPicBusinessCat : {}", id);
        cPicBusinessCatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
