package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBlacklistLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBlacklistLineDTO;
import com.bhp.opusb.service.dto.MBlacklistLineCriteria;
import com.bhp.opusb.service.MBlacklistLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBlacklistLine}.
 */
@RestController
@RequestMapping("/api")
public class MBlacklistLineResource {

    private final Logger log = LoggerFactory.getLogger(MBlacklistLineResource.class);

    private static final String ENTITY_NAME = "mBlacklistLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBlacklistLineService mBlacklistLineService;

    private final MBlacklistLineQueryService mBlacklistLineQueryService;

    public MBlacklistLineResource(MBlacklistLineService mBlacklistLineService, MBlacklistLineQueryService mBlacklistLineQueryService) {
        this.mBlacklistLineService = mBlacklistLineService;
        this.mBlacklistLineQueryService = mBlacklistLineQueryService;
    }

    /**
     * {@code POST  /m-blacklist-lines} : Create a new mBlacklistLine.
     *
     * @param mBlacklistLineDTO the mBlacklistLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBlacklistLineDTO, or with status {@code 400 (Bad Request)} if the mBlacklistLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-blacklist-lines")
    public ResponseEntity<MBlacklistLineDTO> createMBlacklistLine(@Valid @RequestBody MBlacklistLineDTO mBlacklistLineDTO) throws URISyntaxException {
        log.debug("REST request to save MBlacklistLine : {}", mBlacklistLineDTO);
        if (mBlacklistLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBlacklistLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBlacklistLineDTO result = mBlacklistLineService.save(mBlacklistLineDTO);
        return ResponseEntity.created(new URI("/api/m-blacklist-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-blacklist-lines} : Updates an existing mBlacklistLine.
     *
     * @param mBlacklistLineDTO the mBlacklistLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBlacklistLineDTO,
     * or with status {@code 400 (Bad Request)} if the mBlacklistLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBlacklistLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-blacklist-lines")
    public ResponseEntity<MBlacklistLineDTO> updateMBlacklistLine(@Valid @RequestBody MBlacklistLineDTO mBlacklistLineDTO) throws URISyntaxException {
        log.debug("REST request to update MBlacklistLine : {}", mBlacklistLineDTO);
        if (mBlacklistLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBlacklistLineDTO result = mBlacklistLineService.save(mBlacklistLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBlacklistLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-blacklist-lines} : get all the mBlacklistLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBlacklistLines in body.
     */
    @GetMapping("/m-blacklist-lines")
    public ResponseEntity<List<MBlacklistLineDTO>> getAllMBlacklistLines(MBlacklistLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBlacklistLines by criteria: {}", criteria);
        Page<MBlacklistLineDTO> page = mBlacklistLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-blacklist-lines/count} : count all the mBlacklistLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-blacklist-lines/count")
    public ResponseEntity<Long> countMBlacklistLines(MBlacklistLineCriteria criteria) {
        log.debug("REST request to count MBlacklistLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBlacklistLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-blacklist-lines/:id} : get the "id" mBlacklistLine.
     *
     * @param id the id of the mBlacklistLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBlacklistLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-blacklist-lines/{id}")
    public ResponseEntity<MBlacklistLineDTO> getMBlacklistLine(@PathVariable Long id) {
        log.debug("REST request to get MBlacklistLine : {}", id);
        Optional<MBlacklistLineDTO> mBlacklistLineDTO = mBlacklistLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBlacklistLineDTO);
    }

    /**
     * {@code DELETE  /m-blacklist-lines/:id} : delete the "id" mBlacklistLine.
     *
     * @param id the id of the mBlacklistLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-blacklist-lines/{id}")
    public ResponseEntity<Void> deleteMBlacklistLine(@PathVariable Long id) {
        log.debug("REST request to delete MBlacklistLine : {}", id);
        mBlacklistLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
