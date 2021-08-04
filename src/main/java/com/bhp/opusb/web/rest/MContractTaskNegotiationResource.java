package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractTaskNegotiationService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractTaskNegotiationDTO;
import com.bhp.opusb.service.dto.MContractTaskNegotiationCriteria;
import com.bhp.opusb.service.MContractTaskNegotiationQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractTaskNegotiation}.
 */
@RestController
@RequestMapping("/api")
public class MContractTaskNegotiationResource {

    private final Logger log = LoggerFactory.getLogger(MContractTaskNegotiationResource.class);

    private static final String ENTITY_NAME = "mContractTaskNegotiation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractTaskNegotiationService mContractTaskNegotiationService;

    private final MContractTaskNegotiationQueryService mContractTaskNegotiationQueryService;

    public MContractTaskNegotiationResource(MContractTaskNegotiationService mContractTaskNegotiationService, MContractTaskNegotiationQueryService mContractTaskNegotiationQueryService) {
        this.mContractTaskNegotiationService = mContractTaskNegotiationService;
        this.mContractTaskNegotiationQueryService = mContractTaskNegotiationQueryService;
    }

    /**
     * {@code POST  /m-contract-task-negotiations} : Create a new mContractTaskNegotiation.
     *
     * @param mContractTaskNegotiationDTO the mContractTaskNegotiationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractTaskNegotiationDTO, or with status {@code 400 (Bad Request)} if the mContractTaskNegotiation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-task-negotiations")
    public ResponseEntity<MContractTaskNegotiationDTO> createMContractTaskNegotiation(@Valid @RequestBody MContractTaskNegotiationDTO mContractTaskNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to save MContractTaskNegotiation : {}", mContractTaskNegotiationDTO);
        if (mContractTaskNegotiationDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractTaskNegotiation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractTaskNegotiationDTO result = mContractTaskNegotiationService.save(mContractTaskNegotiationDTO);
        return ResponseEntity.created(new URI("/api/m-contract-task-negotiations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-task-negotiations} : Updates an existing mContractTaskNegotiation.
     *
     * @param mContractTaskNegotiationDTO the mContractTaskNegotiationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractTaskNegotiationDTO,
     * or with status {@code 400 (Bad Request)} if the mContractTaskNegotiationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractTaskNegotiationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-task-negotiations")
    public ResponseEntity<MContractTaskNegotiationDTO> updateMContractTaskNegotiation(@Valid @RequestBody MContractTaskNegotiationDTO mContractTaskNegotiationDTO) throws URISyntaxException {
        log.debug("REST request to update MContractTaskNegotiation : {}", mContractTaskNegotiationDTO);
        if (mContractTaskNegotiationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractTaskNegotiationDTO result = mContractTaskNegotiationService.save(mContractTaskNegotiationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractTaskNegotiationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-task-negotiations} : get all the mContractTaskNegotiations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractTaskNegotiations in body.
     */
    @GetMapping("/m-contract-task-negotiations")
    public ResponseEntity<List<MContractTaskNegotiationDTO>> getAllMContractTaskNegotiations(MContractTaskNegotiationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractTaskNegotiations by criteria: {}", criteria);
        Page<MContractTaskNegotiationDTO> page = mContractTaskNegotiationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-task-negotiations/count} : count all the mContractTaskNegotiations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-task-negotiations/count")
    public ResponseEntity<Long> countMContractTaskNegotiations(MContractTaskNegotiationCriteria criteria) {
        log.debug("REST request to count MContractTaskNegotiations by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractTaskNegotiationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-task-negotiations/:id} : get the "id" mContractTaskNegotiation.
     *
     * @param id the id of the mContractTaskNegotiationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractTaskNegotiationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-task-negotiations/{id}")
    public ResponseEntity<MContractTaskNegotiationDTO> getMContractTaskNegotiation(@PathVariable Long id) {
        log.debug("REST request to get MContractTaskNegotiation : {}", id);
        Optional<MContractTaskNegotiationDTO> mContractTaskNegotiationDTO = mContractTaskNegotiationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractTaskNegotiationDTO);
    }

    /**
     * {@code DELETE  /m-contract-task-negotiations/:id} : delete the "id" mContractTaskNegotiation.
     *
     * @param id the id of the mContractTaskNegotiationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-task-negotiations/{id}")
    public ResponseEntity<Void> deleteMContractTaskNegotiation(@PathVariable Long id) {
        log.debug("REST request to delete MContractTaskNegotiation : {}", id);
        mContractTaskNegotiationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
