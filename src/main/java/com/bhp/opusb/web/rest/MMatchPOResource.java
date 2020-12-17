package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MMatchPOQueryService;
import com.bhp.opusb.service.MMatchPOService;
import com.bhp.opusb.service.dto.MMatchPOCriteria;
import com.bhp.opusb.service.dto.MMatchPODTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.fasterxml.jackson.core.JsonProcessingException;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MMatchPO}.
 */
@RestController
@RequestMapping("/api")
public class MMatchPOResource {

    private final Logger log = LoggerFactory.getLogger(MMatchPOResource.class);

    private static final String ENTITY_NAME = "mMatchPO";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MMatchPOService mMatchPOService;

    private final MMatchPOQueryService mMatchPOQueryService;

    public MMatchPOResource(MMatchPOService mMatchPOService, MMatchPOQueryService mMatchPOQueryService) {
        this.mMatchPOService = mMatchPOService;
        this.mMatchPOQueryService = mMatchPOQueryService;
    }

    /**
     * {@code POST  /m-match-pos} : Create a new mMatchPO.
     *
     * @param mMatchPODTO the mMatchPODTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mMatchPODTO, or with status {@code 400 (Bad Request)} if the mMatchPO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-match-pos")
    public ResponseEntity<MMatchPODTO> createMMatchPO(@Valid @RequestBody MMatchPODTO mMatchPODTO) throws URISyntaxException {
        log.debug("REST request to save MMatchPO : {}", mMatchPODTO);
        if (mMatchPODTO.getId() != null) {
            throw new BadRequestAlertException("A new mMatchPO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MMatchPODTO result = mMatchPOService.save(mMatchPODTO);
        return ResponseEntity.created(new URI("/api/m-match-pos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-match-pos} : Create a new mMatchPO.
     *
     * @param mMatchPODTO the mMatchPODTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new mMatchPODTO, or with status {@code 400 (Bad Request)} if
     *         the mMatchPO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping(
        path = "/m-match-pos/synchronize",
        consumes = {
            "application/octet-stream;charset=UTF-8",
            "application/json;charset=UTF-8"
        },
        produces = {
            "application/json;charset=UTF-8"
        })
    public ResponseEntity<MMatchPODTO> syncReceiverFile(@RequestBody byte[] message) throws URISyntaxException, JsonProcessingException {
        final String input = new String(message);
        log.debug("REST request to synchronize MMatchPO : {}", input);

        MMatchPODTO result = mMatchPOService.synchronize(input);

        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-match-pos} : Updates an existing mMatchPO.
     *
     * @param mMatchPODTO the mMatchPODTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mMatchPODTO,
     * or with status {@code 400 (Bad Request)} if the mMatchPODTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mMatchPODTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-match-pos")
    public ResponseEntity<MMatchPODTO> updateMMatchPO(@Valid @RequestBody MMatchPODTO mMatchPODTO) throws URISyntaxException {
        log.debug("REST request to update MMatchPO : {}", mMatchPODTO);
        if (mMatchPODTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MMatchPODTO result = mMatchPOService.save(mMatchPODTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mMatchPODTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-match-pos} : get all the mMatchPOS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMatchPOS in body.
     */
    @GetMapping("/m-match-pos")
    public ResponseEntity<List<MMatchPODTO>> getAllMMatchPOS(MMatchPOCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MMatchPOS by criteria: {}", criteria);
        Page<MMatchPODTO> page = mMatchPOQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-match-pos} : get all the mMatchPOS that are not already invoiced.
     *
     * @param cVendorId
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mMatchPOS in body.
     */
    @GetMapping("/m-match-pos/not-invoiced")
    public ResponseEntity<List<MMatchPODTO>> getNotInvoicedMMatchPOS(MMatchPOCriteria criteria, Pageable pageable) {
        log.debug("REST request to get not-already-invoiced MMatchPOS by criteria: {}", criteria);
        Page<MMatchPODTO> page = mMatchPOQueryService.findNewReceivedItems(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-match-pos/count} : count all the mMatchPOS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-match-pos/count")
    public ResponseEntity<Long> countMMatchPOS(MMatchPOCriteria criteria) {
        log.debug("REST request to count MMatchPOS by criteria: {}", criteria);
        return ResponseEntity.ok().body(mMatchPOQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-match-pos/:id} : get the "id" mMatchPO.
     *
     * @param id the id of the mMatchPODTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mMatchPODTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-match-pos/{id}")
    public ResponseEntity<MMatchPODTO> getMMatchPO(@PathVariable Long id) {
        log.debug("REST request to get MMatchPO : {}", id);
        Optional<MMatchPODTO> mMatchPODTO = mMatchPOService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mMatchPODTO);
    }

    /**
     * {@code DELETE  /m-match-pos/:id} : delete the "id" mMatchPO.
     *
     * @param id the id of the mMatchPODTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-match-pos/{id}")
    public ResponseEntity<Void> deleteMMatchPO(@PathVariable Long id) {
        log.debug("REST request to delete MMatchPO : {}", id);
        mMatchPOService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
