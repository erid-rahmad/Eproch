package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MBiddingEvaluationTeamService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamDTO;
import com.bhp.opusb.service.dto.MBiddingEvaluationTeamCriteria;
import com.bhp.opusb.service.MBiddingEvaluationTeamQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MBiddingEvaluationTeam}.
 */
@RestController
@RequestMapping("/api")
public class MBiddingEvaluationTeamResource {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvaluationTeamResource.class);

    private static final String ENTITY_NAME = "mBiddingEvaluationTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MBiddingEvaluationTeamService mBiddingEvaluationTeamService;

    private final MBiddingEvaluationTeamQueryService mBiddingEvaluationTeamQueryService;

    public MBiddingEvaluationTeamResource(MBiddingEvaluationTeamService mBiddingEvaluationTeamService, MBiddingEvaluationTeamQueryService mBiddingEvaluationTeamQueryService) {
        this.mBiddingEvaluationTeamService = mBiddingEvaluationTeamService;
        this.mBiddingEvaluationTeamQueryService = mBiddingEvaluationTeamQueryService;
    }

    /**
     * {@code POST  /m-bidding-evaluation-teams} : Create a new mBiddingEvaluationTeam.
     *
     * @param mBiddingEvaluationTeamDTO the mBiddingEvaluationTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mBiddingEvaluationTeamDTO, or with status {@code 400 (Bad Request)} if the mBiddingEvaluationTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-bidding-evaluation-teams")
    public ResponseEntity<MBiddingEvaluationTeamDTO> createMBiddingEvaluationTeam(@Valid @RequestBody MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO) throws URISyntaxException {
        log.debug("REST request to save MBiddingEvaluationTeam : {}", mBiddingEvaluationTeamDTO);
        if (mBiddingEvaluationTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new mBiddingEvaluationTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MBiddingEvaluationTeamDTO result = mBiddingEvaluationTeamService.save(mBiddingEvaluationTeamDTO);
        return ResponseEntity.created(new URI("/api/m-bidding-evaluation-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-bidding-evaluation-teams} : Updates an existing mBiddingEvaluationTeam.
     *
     * @param mBiddingEvaluationTeamDTO the mBiddingEvaluationTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mBiddingEvaluationTeamDTO,
     * or with status {@code 400 (Bad Request)} if the mBiddingEvaluationTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mBiddingEvaluationTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-bidding-evaluation-teams")
    public ResponseEntity<MBiddingEvaluationTeamDTO> updateMBiddingEvaluationTeam(@Valid @RequestBody MBiddingEvaluationTeamDTO mBiddingEvaluationTeamDTO) throws URISyntaxException {
        log.debug("REST request to update MBiddingEvaluationTeam : {}", mBiddingEvaluationTeamDTO);
        if (mBiddingEvaluationTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MBiddingEvaluationTeamDTO result = mBiddingEvaluationTeamService.save(mBiddingEvaluationTeamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mBiddingEvaluationTeamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-bidding-evaluation-teams} : get all the mBiddingEvaluationTeams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mBiddingEvaluationTeams in body.
     */
    @GetMapping("/m-bidding-evaluation-teams")
    public ResponseEntity<List<MBiddingEvaluationTeamDTO>> getAllMBiddingEvaluationTeams(MBiddingEvaluationTeamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MBiddingEvaluationTeams by criteria: {}", criteria);
        Page<MBiddingEvaluationTeamDTO> page = mBiddingEvaluationTeamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-bidding-evaluation-teams/count} : count all the mBiddingEvaluationTeams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-bidding-evaluation-teams/count")
    public ResponseEntity<Long> countMBiddingEvaluationTeams(MBiddingEvaluationTeamCriteria criteria) {
        log.debug("REST request to count MBiddingEvaluationTeams by criteria: {}", criteria);
        return ResponseEntity.ok().body(mBiddingEvaluationTeamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-bidding-evaluation-teams/:id} : get the "id" mBiddingEvaluationTeam.
     *
     * @param id the id of the mBiddingEvaluationTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mBiddingEvaluationTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-bidding-evaluation-teams/{id}")
    public ResponseEntity<MBiddingEvaluationTeamDTO> getMBiddingEvaluationTeam(@PathVariable Long id) {
        log.debug("REST request to get MBiddingEvaluationTeam : {}", id);
        Optional<MBiddingEvaluationTeamDTO> mBiddingEvaluationTeamDTO = mBiddingEvaluationTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mBiddingEvaluationTeamDTO);
    }

    /**
     * {@code DELETE  /m-bidding-evaluation-teams/:id} : delete the "id" mBiddingEvaluationTeam.
     *
     * @param id the id of the mBiddingEvaluationTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-bidding-evaluation-teams/{id}")
    public ResponseEntity<Void> deleteMBiddingEvaluationTeam(@PathVariable Long id) {
        log.debug("REST request to delete MBiddingEvaluationTeam : {}", id);
        mBiddingEvaluationTeamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
