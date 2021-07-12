package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBlacklistService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBlacklistDTO;
import com.bhp.opusb.service.dto.MBlacklistCriteria;
import com.bhp.opusb.service.MBlacklistQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBlacklist}.
 */
@RestController
@RequestMapping("/api")
public class MBlacklistResource {

    private final Logger log = LoggerFactory.getLogger(MBlacklistResource.class);

    private static final String ENTITY_NAME = "mBlacklist";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBlacklistService mBlacklistService;

    private final MBlacklistQueryService mBlacklistQueryService;

    public MBlacklistResource(MBlacklistService mBlacklistService, MBlacklistQueryService mBlacklistQueryService) {
        this.mBlacklistService = mBlacklistService;
        this.mBlacklistQueryService = mBlacklistQueryService;
    }

    /**
     * {@code POST  /m-blacklists} : Create a new mBlacklist.
     *
     * @param mBlacklistDTO the mBlacklistDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBlacklistDTO, or with status {@code 400 (Bad Request)} if the mBlacklist has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-blacklists")
    public ResponseEntity<MBlacklistDTO> createMBlacklist(@Valid @RequestBody MBlacklistDTO mBlacklistDTO) throws URISyntaxException {
        log.debug("REST request to save MBlacklist : {}", mBlacklistDTO);
        if (mBlacklistDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBlacklist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBlacklistDTO result = mBlacklistService.save(mBlacklistDTO);
        return ResponseEntity.created(new URI("/api/m-blacklists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-blacklists} : Updates an existing mBlacklist.
     *
     * @param mBlacklistDTO the mBlacklistDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBlacklistDTO,
     * or with status {@code 400 (Bad Request)} if the mBlacklistDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBlacklistDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-blacklists")
    public ResponseEntity<MBlacklistDTO> updateMBlacklist(@Valid @RequestBody MBlacklistDTO mBlacklistDTO) throws URISyntaxException {
        log.debug("REST request to update MBlacklist : {}", mBlacklistDTO);
        if (mBlacklistDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBlacklistDTO result = mBlacklistService.save(mBlacklistDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBlacklistDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-blacklists} : get all the mBlacklists.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBlacklists in body.
     */
    @GetMapping("/m-blacklists")
    public ResponseEntity<List<MBlacklistDTO>> getAllMBlacklists(MBlacklistCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBlacklists by criteria: {}", criteria);
        Page<MBlacklistDTO> page = mBlacklistQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-blacklists/count} : count all the mBlacklists.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-blacklists/count")
    public ResponseEntity<Long> countMBlacklists(MBlacklistCriteria criteria) {
        log.debug("REST request to count MBlacklists by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBlacklistQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-blacklists/:id} : get the "id" mBlacklist.
     *
     * @param id the id of the mBlacklistDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBlacklistDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-blacklists/{id}")
    public ResponseEntity<MBlacklistDTO> getMBlacklist(@PathVariable Long id) {
        log.debug("REST request to get MBlacklist : {}", id);
        Optional<MBlacklistDTO> mBlacklistDTO = mBlacklistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBlacklistDTO);
    }

    /**
     * {@code DELETE  /m-blacklists/:id} : delete the "id" mBlacklist.
     *
     * @param id the id of the mBlacklistDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-blacklists/{id}")
    public ResponseEntity<Void> deleteMBlacklist(@PathVariable Long id) {
        log.debug("REST request to delete MBlacklist : {}", id);
        mBlacklistService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
