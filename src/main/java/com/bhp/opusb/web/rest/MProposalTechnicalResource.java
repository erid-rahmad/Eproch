package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalTechnicalService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalTechnicalDTO;
import com.bhp.opusb.service.dto.MProposalTechnicalCriteria;
import com.bhp.opusb.service.MProposalTechnicalQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalTechnical}.
 */
@RestController
@RequestMapping("/api")
public class MProposalTechnicalResource {

    private final Logger log = LoggerFactory.getLogger(MProposalTechnicalResource.class);

    private static final String ENTITY_NAME = "mProposalTechnical";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalTechnicalService mProposalTechnicalService;

    private final MProposalTechnicalQueryService mProposalTechnicalQueryService;

    public MProposalTechnicalResource(MProposalTechnicalService mProposalTechnicalService, MProposalTechnicalQueryService mProposalTechnicalQueryService) {
        this.mProposalTechnicalService = mProposalTechnicalService;
        this.mProposalTechnicalQueryService = mProposalTechnicalQueryService;
    }

    /**
     * {@code POST  /m-proposal-technicals} : Create a new mProposalTechnical.
     *
     * @param mProposalTechnicalDTO the mProposalTechnicalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalTechnicalDTO, or with status {@code 400 (Bad Request)} if the mProposalTechnical has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-technicals")
    public ResponseEntity<MProposalTechnicalDTO> createMProposalTechnical(@Valid @RequestBody MProposalTechnicalDTO mProposalTechnicalDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalTechnical : {}", mProposalTechnicalDTO);
        if (mProposalTechnicalDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalTechnical cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalTechnicalDTO result = mProposalTechnicalService.save(mProposalTechnicalDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-technicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-proposal-technicals} : Updates an existing mProposalTechnical.
     *
     * @param mProposalTechnicalDTO the mProposalTechnicalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalTechnicalDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalTechnicalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalTechnicalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-technicals")
    public ResponseEntity<MProposalTechnicalDTO> updateMProposalTechnical(@Valid @RequestBody MProposalTechnicalDTO mProposalTechnicalDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalTechnical : {}", mProposalTechnicalDTO);
        if (mProposalTechnicalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalTechnicalDTO result = mProposalTechnicalService.save(mProposalTechnicalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalTechnicalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-technicals} : get all the mProposalTechnicals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalTechnicals in body.
     */
    @GetMapping("/m-proposal-technicals")
    public ResponseEntity<List<MProposalTechnicalDTO>> getAllMProposalTechnicals(MProposalTechnicalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalTechnicals by criteria: {}", criteria);
        Page<MProposalTechnicalDTO> page = mProposalTechnicalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-technicals/count} : count all the mProposalTechnicals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-technicals/count")
    public ResponseEntity<Long> countMProposalTechnicals(MProposalTechnicalCriteria criteria) {
        log.debug("REST request to count MProposalTechnicals by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalTechnicalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-technicals/:id} : get the "id" mProposalTechnical.
     *
     * @param id the id of the mProposalTechnicalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalTechnicalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-technicals/{id}")
    public ResponseEntity<MProposalTechnicalDTO> getMProposalTechnical(@PathVariable Long id) {
        log.debug("REST request to get MProposalTechnical : {}", id);
        Optional<MProposalTechnicalDTO> mProposalTechnicalDTO = mProposalTechnicalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalTechnicalDTO);
    }

    /**
     * {@code DELETE  /m-proposal-technicals/:id} : delete the "id" mProposalTechnical.
     *
     * @param id the id of the mProposalTechnicalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-technicals/{id}")
    public ResponseEntity<Void> deleteMProposalTechnical(@PathVariable Long id) {
        log.debug("REST request to delete MProposalTechnical : {}", id);
        mProposalTechnicalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
