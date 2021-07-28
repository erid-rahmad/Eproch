package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractTaskReviewersService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractTaskReviewersDTO;
import com.bhp.opusb.service.dto.MContractTaskReviewersCriteria;
import com.bhp.opusb.service.MContractTaskReviewersQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractTaskReviewers}.
 */
@RestController
@RequestMapping("/api")
public class MContractTaskReviewersResource {

    private final Logger log = LoggerFactory.getLogger(MContractTaskReviewersResource.class);

    private static final String ENTITY_NAME = "mContractTaskReviewers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractTaskReviewersService mContractTaskReviewersService;

    private final MContractTaskReviewersQueryService mContractTaskReviewersQueryService;

    public MContractTaskReviewersResource(MContractTaskReviewersService mContractTaskReviewersService, MContractTaskReviewersQueryService mContractTaskReviewersQueryService) {
        this.mContractTaskReviewersService = mContractTaskReviewersService;
        this.mContractTaskReviewersQueryService = mContractTaskReviewersQueryService;
    }

    /**
     * {@code POST  /m-contract-task-reviewers} : Create a new mContractTaskReviewers.
     *
     * @param mContractTaskReviewersDTO the mContractTaskReviewersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractTaskReviewersDTO, or with status {@code 400 (Bad Request)} if the mContractTaskReviewers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-task-reviewers")
    public ResponseEntity<MContractTaskReviewersDTO> createMContractTaskReviewers(@Valid @RequestBody MContractTaskReviewersDTO mContractTaskReviewersDTO) throws URISyntaxException {
        log.debug("REST request to save MContractTaskReviewers : {}", mContractTaskReviewersDTO);
        if (mContractTaskReviewersDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractTaskReviewers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractTaskReviewersDTO result = mContractTaskReviewersService.save(mContractTaskReviewersDTO);
        return ResponseEntity.created(new URI("/api/m-contract-task-reviewers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-task-reviewers} : Updates an existing mContractTaskReviewers.
     *
     * @param mContractTaskReviewersDTO the mContractTaskReviewersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractTaskReviewersDTO,
     * or with status {@code 400 (Bad Request)} if the mContractTaskReviewersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractTaskReviewersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-task-reviewers")
    public ResponseEntity<MContractTaskReviewersDTO> updateMContractTaskReviewers(@Valid @RequestBody MContractTaskReviewersDTO mContractTaskReviewersDTO) throws URISyntaxException {
        log.debug("REST request to update MContractTaskReviewers : {}", mContractTaskReviewersDTO);
        if (mContractTaskReviewersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractTaskReviewersDTO result = mContractTaskReviewersService.save(mContractTaskReviewersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractTaskReviewersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-task-reviewers} : get all the mContractTaskReviewers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractTaskReviewers in body.
     */
    @GetMapping("/m-contract-task-reviewers")
    public ResponseEntity<List<MContractTaskReviewersDTO>> getAllMContractTaskReviewers(MContractTaskReviewersCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractTaskReviewers by criteria: {}", criteria);
        Page<MContractTaskReviewersDTO> page = mContractTaskReviewersQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-task-reviewers/count} : count all the mContractTaskReviewers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-task-reviewers/count")
    public ResponseEntity<Long> countMContractTaskReviewers(MContractTaskReviewersCriteria criteria) {
        log.debug("REST request to count MContractTaskReviewers by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractTaskReviewersQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-task-reviewers/:id} : get the "id" mContractTaskReviewers.
     *
     * @param id the id of the mContractTaskReviewersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractTaskReviewersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-task-reviewers/{id}")
    public ResponseEntity<MContractTaskReviewersDTO> getMContractTaskReviewers(@PathVariable Long id) {
        log.debug("REST request to get MContractTaskReviewers : {}", id);
        Optional<MContractTaskReviewersDTO> mContractTaskReviewersDTO = mContractTaskReviewersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractTaskReviewersDTO);
    }

    /**
     * {@code DELETE  /m-contract-task-reviewers/:id} : delete the "id" mContractTaskReviewers.
     *
     * @param id the id of the mContractTaskReviewersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-task-reviewers/{id}")
    public ResponseEntity<Void> deleteMContractTaskReviewers(@PathVariable Long id) {
        log.debug("REST request to delete MContractTaskReviewers : {}", id);
        mContractTaskReviewersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
