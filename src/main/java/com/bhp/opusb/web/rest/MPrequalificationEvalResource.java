package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationEvalService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationEvalDTO;
import com.bhp.opusb.service.dto.MPrequalificationEvalCriteria;
import com.bhp.opusb.service.MPrequalificationEvalQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationEval}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationEvalResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEvalResource.class);

    private static final String ENTITY_NAME = "mPrequalificationEval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationEvalService mPrequalificationEvalService;

    private final MPrequalificationEvalQueryService mPrequalificationEvalQueryService;

    public MPrequalificationEvalResource(MPrequalificationEvalService mPrequalificationEvalService, MPrequalificationEvalQueryService mPrequalificationEvalQueryService) {
        this.mPrequalificationEvalService = mPrequalificationEvalService;
        this.mPrequalificationEvalQueryService = mPrequalificationEvalQueryService;
    }

    /**
     * {@code POST  /m-prequalification-evals} : Create a new mPrequalificationEval.
     *
     * @param mPrequalificationEvalDTO the mPrequalificationEvalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationEvalDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationEval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-evals")
    public ResponseEntity<MPrequalificationEvalDTO> createMPrequalificationEval(@Valid @RequestBody MPrequalificationEvalDTO mPrequalificationEvalDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationEval : {}", mPrequalificationEvalDTO);
        if (mPrequalificationEvalDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationEval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationEvalDTO result = mPrequalificationEvalService.save(mPrequalificationEvalDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-evals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /m-prequalification-evals/requirements} : Create or update mProposalAdministrations.
     *
     * @param mProposalAdministrationDTOs the list of mProposalAdministrationDTOs to create.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalAdministrationDTOs.
     */
    @PostMapping("/m-prequalification-evals/requirements")
    public ResponseEntity<List<MPrequalificationEvalDTO>> createMProposalAdministrations(@Valid @RequestBody List<MPrequalificationEvalDTO> mPrequalificationEvalDTOs) {
        log.debug("REST request to save MPrequalificationEvals. size : {}", mPrequalificationEvalDTOs.size());
        List<MPrequalificationEvalDTO> result = mPrequalificationEvalService.saveRequirements(mPrequalificationEvalDTOs);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PUT  /m-prequalification-evals} : Updates an existing mPrequalificationEval.
     *
     * @param mPrequalificationEvalDTO the mPrequalificationEvalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationEvalDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationEvalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationEvalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-evals")
    public ResponseEntity<MPrequalificationEvalDTO> updateMPrequalificationEval(@Valid @RequestBody MPrequalificationEvalDTO mPrequalificationEvalDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationEval : {}", mPrequalificationEvalDTO);
        if (mPrequalificationEvalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationEvalDTO result = mPrequalificationEvalService.save(mPrequalificationEvalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationEvalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-evals} : get all the mPrequalificationEvals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationEvals in body.
     */
    @GetMapping("/m-prequalification-evals")
    public ResponseEntity<List<MPrequalificationEvalDTO>> getAllMPrequalificationEvals(MPrequalificationEvalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationEvals by criteria: {}", criteria);
        Page<MPrequalificationEvalDTO> page = mPrequalificationEvalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-evals/count} : count all the mPrequalificationEvals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-evals/count")
    public ResponseEntity<Long> countMPrequalificationEvals(MPrequalificationEvalCriteria criteria) {
        log.debug("REST request to count MPrequalificationEvals by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationEvalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-evals/:id} : get the "id" mPrequalificationEval.
     *
     * @param id the id of the mPrequalificationEvalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationEvalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-evals/{id}")
    public ResponseEntity<MPrequalificationEvalDTO> getMPrequalificationEval(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationEval : {}", id);
        Optional<MPrequalificationEvalDTO> mPrequalificationEvalDTO = mPrequalificationEvalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationEvalDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-evals/:id} : delete the "id" mPrequalificationEval.
     *
     * @param id the id of the mPrequalificationEvalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-evals/{id}")
    public ResponseEntity<Void> deleteMPrequalificationEval(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationEval : {}", id);
        mPrequalificationEvalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
