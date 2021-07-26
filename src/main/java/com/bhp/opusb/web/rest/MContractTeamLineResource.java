package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractTeamLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractTeamLineDTO;
import com.bhp.opusb.service.dto.MContractTeamLineCriteria;
import com.bhp.opusb.service.MContractTeamLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractTeamLine}.
 */
@RestController
@RequestMapping("/api")
public class MContractTeamLineResource {

    private final Logger log = LoggerFactory.getLogger(MContractTeamLineResource.class);

    private static final String ENTITY_NAME = "mContractTeamLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractTeamLineService mContractTeamLineService;

    private final MContractTeamLineQueryService mContractTeamLineQueryService;

    public MContractTeamLineResource(MContractTeamLineService mContractTeamLineService, MContractTeamLineQueryService mContractTeamLineQueryService) {
        this.mContractTeamLineService = mContractTeamLineService;
        this.mContractTeamLineQueryService = mContractTeamLineQueryService;
    }

    /**
     * {@code POST  /m-contract-team-lines} : Create a new mContractTeamLine.
     *
     * @param mContractTeamLineDTO the mContractTeamLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractTeamLineDTO, or with status {@code 400 (Bad Request)} if the mContractTeamLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-team-lines")
    public ResponseEntity<MContractTeamLineDTO> createMContractTeamLine(@Valid @RequestBody MContractTeamLineDTO mContractTeamLineDTO) throws URISyntaxException {
        log.debug("REST request to save MContractTeamLine : {}", mContractTeamLineDTO);
        if (mContractTeamLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractTeamLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractTeamLineDTO result = mContractTeamLineService.save(mContractTeamLineDTO);
        return ResponseEntity.created(new URI("/api/m-contract-team-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-team-lines} : Updates an existing mContractTeamLine.
     *
     * @param mContractTeamLineDTO the mContractTeamLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractTeamLineDTO,
     * or with status {@code 400 (Bad Request)} if the mContractTeamLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractTeamLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-team-lines")
    public ResponseEntity<MContractTeamLineDTO> updateMContractTeamLine(@Valid @RequestBody MContractTeamLineDTO mContractTeamLineDTO) throws URISyntaxException {
        log.debug("REST request to update MContractTeamLine : {}", mContractTeamLineDTO);
        if (mContractTeamLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractTeamLineDTO result = mContractTeamLineService.save(mContractTeamLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractTeamLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-team-lines} : get all the mContractTeamLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractTeamLines in body.
     */
    @GetMapping("/m-contract-team-lines")
    public ResponseEntity<List<MContractTeamLineDTO>> getAllMContractTeamLines(MContractTeamLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractTeamLines by criteria: {}", criteria);
        Page<MContractTeamLineDTO> page = mContractTeamLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-team-lines/count} : count all the mContractTeamLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-team-lines/count")
    public ResponseEntity<Long> countMContractTeamLines(MContractTeamLineCriteria criteria) {
        log.debug("REST request to count MContractTeamLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractTeamLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-team-lines/:id} : get the "id" mContractTeamLine.
     *
     * @param id the id of the mContractTeamLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractTeamLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-team-lines/{id}")
    public ResponseEntity<MContractTeamLineDTO> getMContractTeamLine(@PathVariable Long id) {
        log.debug("REST request to get MContractTeamLine : {}", id);
        Optional<MContractTeamLineDTO> mContractTeamLineDTO = mContractTeamLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractTeamLineDTO);
    }

    /**
     * {@code DELETE  /m-contract-team-lines/:id} : delete the "id" mContractTeamLine.
     *
     * @param id the id of the mContractTeamLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-team-lines/{id}")
    public ResponseEntity<Void> deleteMContractTeamLine(@PathVariable Long id) {
        log.debug("REST request to delete MContractTeamLine : {}", id);
        mContractTeamLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
