package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.CAnnouncementResultService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.CAnnouncementResultDTO;
import com.bhp.opusb.service.dto.CAnnouncementResultCriteria;
import com.bhp.opusb.service.CAnnouncementResultQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.CAnnouncementResult}.
 */
@RestController
@RequestMapping("/api")
public class CAnnouncementResultResource {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementResultResource.class);

    private static final String ENTITY_NAME = "cAnnouncementResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CAnnouncementResultService cAnnouncementResultService;

    private final CAnnouncementResultQueryService cAnnouncementResultQueryService;

    public CAnnouncementResultResource(CAnnouncementResultService cAnnouncementResultService, CAnnouncementResultQueryService cAnnouncementResultQueryService) {
        this.cAnnouncementResultService = cAnnouncementResultService;
        this.cAnnouncementResultQueryService = cAnnouncementResultQueryService;
    }

    /**
     * {@code POST  /c-announcement-results} : Create a new cAnnouncementResult.
     *
     * @param cAnnouncementResultDTO the cAnnouncementResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cAnnouncementResultDTO, or with status {@code 400 (Bad Request)} if the cAnnouncementResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/c-announcement-results")
    public ResponseEntity<CAnnouncementResultDTO> createCAnnouncementResult(@Valid @RequestBody CAnnouncementResultDTO cAnnouncementResultDTO) throws URISyntaxException {
        log.debug("REST request to save CAnnouncementResult : {}", cAnnouncementResultDTO);
        if (cAnnouncementResultDTO.getId() != null) {
            updateCAnnouncementResult(cAnnouncementResultDTO);
            return ResponseEntity.ok(cAnnouncementResultDTO);
        }
        CAnnouncementResultDTO result = cAnnouncementResultService.save(cAnnouncementResultDTO);
        return ResponseEntity.created(new URI("/api/c-announcement-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /c-announcement-results} : Updates an existing cAnnouncementResult.
     *
     * @param cAnnouncementResultDTO the cAnnouncementResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cAnnouncementResultDTO,
     * or with status {@code 400 (Bad Request)} if the cAnnouncementResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cAnnouncementResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/c-announcement-results")
    public ResponseEntity<CAnnouncementResultDTO> updateCAnnouncementResult(@Valid @RequestBody CAnnouncementResultDTO cAnnouncementResultDTO) throws URISyntaxException {
        log.debug("REST request to update CAnnouncementResult : {}", cAnnouncementResultDTO);
        if (cAnnouncementResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CAnnouncementResultDTO result = cAnnouncementResultService.save(cAnnouncementResultDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cAnnouncementResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /c-announcement-results} : get all the cAnnouncementResults.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cAnnouncementResults in body.
     */
    @GetMapping("/c-announcement-results")
    public ResponseEntity<List<CAnnouncementResultDTO>> getAllCAnnouncementResults(CAnnouncementResultCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CAnnouncementResults by criteria: {}", criteria);
        Page<CAnnouncementResultDTO> page = cAnnouncementResultQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /c-announcement-results/count} : count all the cAnnouncementResults.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/c-announcement-results/count")
    public ResponseEntity<Long> countCAnnouncementResults(CAnnouncementResultCriteria criteria) {
        log.debug("REST request to count CAnnouncementResults by criteria: {}", criteria);
        return ResponseEntity.ok().body(cAnnouncementResultQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /c-announcement-results/:id} : get the "id" cAnnouncementResult.
     *
     * @param id the id of the cAnnouncementResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cAnnouncementResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/c-announcement-results/{id}")
    public ResponseEntity<CAnnouncementResultDTO> getCAnnouncementResult(@PathVariable Long id) {
        log.debug("REST request to get CAnnouncementResult : {}", id);
        Optional<CAnnouncementResultDTO> cAnnouncementResultDTO = cAnnouncementResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cAnnouncementResultDTO);
    }

    /**
     * {@code DELETE  /c-announcement-results/:id} : delete the "id" cAnnouncementResult.
     *
     * @param id the id of the cAnnouncementResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/c-announcement-results/{id}")
    public ResponseEntity<Void> deleteCAnnouncementResult(@PathVariable Long id) {
        log.debug("REST request to delete CAnnouncementResult : {}", id);
        cAnnouncementResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
