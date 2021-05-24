package com.bhp.opusb.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.bhp.opusb.service.MAuctionTeamQueryService;
import com.bhp.opusb.service.MAuctionTeamService;
import com.bhp.opusb.service.dto.MAuctionTeamCriteria;
import com.bhp.opusb.service.dto.MAuctionTeamDTO;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MAuctionTeam}.
 */
@RestController
@RequestMapping("/api")
public class MAuctionTeamResource {

    private final Logger log = LoggerFactory.getLogger(MAuctionTeamResource.class);

    private static final String ENTITY_NAME = "mAuctionTeam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MAuctionTeamService mAuctionTeamService;

    private final MAuctionTeamQueryService mAuctionTeamQueryService;

    public MAuctionTeamResource(MAuctionTeamService mAuctionTeamService, MAuctionTeamQueryService mAuctionTeamQueryService) {
        this.mAuctionTeamService = mAuctionTeamService;
        this.mAuctionTeamQueryService = mAuctionTeamQueryService;
    }

    /**
     * {@code POST  /m-auction-teams} : Create a new mAuctionTeam.
     *
     * @param mAuctionTeamDTO the mAuctionTeamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mAuctionTeamDTO, or with status {@code 400 (Bad Request)} if the mAuctionTeam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-auction-teams")
    public ResponseEntity<MAuctionTeamDTO> createMAuctionTeam(@Valid @RequestBody MAuctionTeamDTO mAuctionTeamDTO) throws URISyntaxException {
        log.debug("REST request to save MAuctionTeam : {}", mAuctionTeamDTO);
        if (mAuctionTeamDTO.getId() != null) {
            throw new BadRequestAlertException("A new mAuctionTeam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MAuctionTeamDTO result = mAuctionTeamService.save(mAuctionTeamDTO);
        return ResponseEntity.created(new URI("/api/m-auction-teams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-auction-teams} : Updates an existing mAuctionTeam.
     *
     * @param mAuctionTeamDTO the mAuctionTeamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mAuctionTeamDTO,
     * or with status {@code 400 (Bad Request)} if the mAuctionTeamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mAuctionTeamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-auction-teams")
    public ResponseEntity<MAuctionTeamDTO> updateMAuctionTeam(@Valid @RequestBody MAuctionTeamDTO mAuctionTeamDTO) throws URISyntaxException {
        log.debug("REST request to update MAuctionTeam : {}", mAuctionTeamDTO);
        if (mAuctionTeamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MAuctionTeamDTO result = mAuctionTeamService.save(mAuctionTeamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mAuctionTeamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-auction-teams} : get all the mAuctionTeams.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mAuctionTeams in body.
     */
    @GetMapping("/m-auction-teams")
    public ResponseEntity<List<MAuctionTeamDTO>> getAllMAuctionTeams(MAuctionTeamCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MAuctionTeams by criteria: {}", criteria);
        Page<MAuctionTeamDTO> page = mAuctionTeamQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-auction-teams/count} : count all the mAuctionTeams.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-auction-teams/count")
    public ResponseEntity<Long> countMAuctionTeams(MAuctionTeamCriteria criteria) {
        log.debug("REST request to count MAuctionTeams by criteria: {}", criteria);
        return ResponseEntity.ok().body(mAuctionTeamQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-auction-teams/:id} : get the "id" mAuctionTeam.
     *
     * @param id the id of the mAuctionTeamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mAuctionTeamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-auction-teams/{id}")
    public ResponseEntity<MAuctionTeamDTO> getMAuctionTeam(@PathVariable Long id) {
        log.debug("REST request to get MAuctionTeam : {}", id);
        Optional<MAuctionTeamDTO> mAuctionTeamDTO = mAuctionTeamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mAuctionTeamDTO);
    }

    /**
     * {@code DELETE  /m-auction-teams/:id} : delete the "id" mAuctionTeam.
     *
     * @param id the id of the mAuctionTeamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-auction-teams/{id}")
    public ResponseEntity<Void> deleteMAuctionTeam(@PathVariable Long id) {
        log.debug("REST request to delete MAuctionTeam : {}", id);
        mAuctionTeamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
