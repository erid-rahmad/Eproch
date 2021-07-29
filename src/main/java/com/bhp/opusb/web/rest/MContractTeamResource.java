package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MContractTeamService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MContractTeamDTO;
import com.bhp.opusb.service.dto.MContractTeamCriteria;
import com.bhp.opusb.service.MContractTeamQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MContractTeam}.
 */
@RestController
@RequestMapping("/api")
public class MContractTeamResource {

    private final Logger log = LoggerFactory.getLogger(MContractTeamResource.class);

    private static final String ENTITY_NAME = "mContractTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MContractTeamService mContractTeamService;

    private final MContractTeamQueryService mContractTeamQueryService;

    public MContractTeamResource(MContractTeamService mContractTeamService, MContractTeamQueryService mContractTeamQueryService) {
        this.mContractTeamService = mContractTeamService;
        this.mContractTeamQueryService = mContractTeamQueryService;
    }

    /**
     * {@code POST  /m-contract-teams} : Create a new mContractTeam.
     *
     * @param mContractTeamDTO the mContractTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mContractTeamDTO, or with status {@code 400 (Bad Request)} if the mContractTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-contract-teams")
    public ResponseEntity<MContractTeamDTO> createMContractTeam(@Valid @RequestBody MContractTeamDTO mContractTeamDTO) throws URISyntaxException {
        log.debug("REST request to save MContractTeam : {}", mContractTeamDTO);
        if (mContractTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new mContractTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MContractTeamDTO result = mContractTeamService.save(mContractTeamDTO);
        return ResponseEntity.created(new URI("/api/m-contract-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-contract-teams} : Updates an existing mContractTeam.
     *
     * @param mContractTeamDTO the mContractTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mContractTeamDTO,
     * or with status {@code 400 (Bad Request)} if the mContractTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mContractTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-contract-teams")
    public ResponseEntity<MContractTeamDTO> updateMContractTeam(@Valid @RequestBody MContractTeamDTO mContractTeamDTO) throws URISyntaxException {
        log.debug("REST request to update MContractTeam : {}", mContractTeamDTO);
        if (mContractTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MContractTeamDTO result = mContractTeamService.save(mContractTeamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mContractTeamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-contract-teams} : get all the mContractTeams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mContractTeams in body.
     */
    @GetMapping("/m-contract-teams")
    public ResponseEntity<List<MContractTeamDTO>> getAllMContractTeams(MContractTeamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MContractTeams by criteria: {}", criteria);
        Page<MContractTeamDTO> page = mContractTeamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-contract-teams/count} : count all the mContractTeams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-contract-teams/count")
    public ResponseEntity<Long> countMContractTeams(MContractTeamCriteria criteria) {
        log.debug("REST request to count MContractTeams by criteria: {}", criteria);
        return ResponseEntity.ok().body(mContractTeamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-contract-teams/:id} : get the "id" mContractTeam.
     *
     * @param id the id of the mContractTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mContractTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-contract-teams/{id}")
    public ResponseEntity<MContractTeamDTO> getMContractTeam(@PathVariable Long id) {
        log.debug("REST request to get MContractTeam : {}", id);
        Optional<MContractTeamDTO> mContractTeamDTO = mContractTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mContractTeamDTO);
    }

    /**
     * {@code DELETE  /m-contract-teams/:id} : delete the "id" mContractTeam.
     *
     * @param id the id of the mContractTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-contract-teams/{id}")
    public ResponseEntity<Void> deleteMContractTeam(@PathVariable Long id) {
        log.debug("REST request to delete MContractTeam : {}", id);
        mContractTeamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
