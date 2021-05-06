package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalAdministrationFileService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalAdministrationFileDTO;
import com.bhp.opusb.service.dto.MProposalAdministrationFileCriteria;
import com.bhp.opusb.service.MProposalAdministrationFileQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalAdministrationFile}.
 */
@RestController
@RequestMapping("/api")
public class MProposalAdministrationFileResource {

    private final Logger log = LoggerFactory.getLogger(MProposalAdministrationFileResource.class);

    private static final String ENTITY_NAME = "mProposalAdministrationFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalAdministrationFileService mProposalAdministrationFileService;

    private final MProposalAdministrationFileQueryService mProposalAdministrationFileQueryService;

    public MProposalAdministrationFileResource(MProposalAdministrationFileService mProposalAdministrationFileService, MProposalAdministrationFileQueryService mProposalAdministrationFileQueryService) {
        this.mProposalAdministrationFileService = mProposalAdministrationFileService;
        this.mProposalAdministrationFileQueryService = mProposalAdministrationFileQueryService;
    }

    /**
     * {@code POST  /m-proposal-administration-files} : Create a new mProposalAdministrationFile.
     *
     * @param mProposalAdministrationFileDTO the mProposalAdministrationFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalAdministrationFileDTO, or with status {@code 400 (Bad Request)} if the mProposalAdministrationFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-administration-files")
    public ResponseEntity<MProposalAdministrationFileDTO> createMProposalAdministrationFile(@Valid @RequestBody MProposalAdministrationFileDTO mProposalAdministrationFileDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalAdministrationFile : {}", mProposalAdministrationFileDTO);
        if (mProposalAdministrationFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalAdministrationFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalAdministrationFileDTO result = mProposalAdministrationFileService.save(mProposalAdministrationFileDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-administration-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-proposal-administration-files} : Updates an existing mProposalAdministrationFile.
     *
     * @param mProposalAdministrationFileDTO the mProposalAdministrationFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalAdministrationFileDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalAdministrationFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalAdministrationFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-administration-files")
    public ResponseEntity<MProposalAdministrationFileDTO> updateMProposalAdministrationFile(@Valid @RequestBody MProposalAdministrationFileDTO mProposalAdministrationFileDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalAdministrationFile : {}", mProposalAdministrationFileDTO);
        if (mProposalAdministrationFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalAdministrationFileDTO result = mProposalAdministrationFileService.save(mProposalAdministrationFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalAdministrationFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-administration-files} : get all the mProposalAdministrationFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalAdministrationFiles in body.
     */
    @GetMapping("/m-proposal-administration-files")
    public ResponseEntity<List<MProposalAdministrationFileDTO>> getAllMProposalAdministrationFiles(MProposalAdministrationFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalAdministrationFiles by criteria: {}", criteria);
        Page<MProposalAdministrationFileDTO> page = mProposalAdministrationFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-administration-files/count} : count all the mProposalAdministrationFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-administration-files/count")
    public ResponseEntity<Long> countMProposalAdministrationFiles(MProposalAdministrationFileCriteria criteria) {
        log.debug("REST request to count MProposalAdministrationFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalAdministrationFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-administration-files/:id} : get the "id" mProposalAdministrationFile.
     *
     * @param id the id of the mProposalAdministrationFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalAdministrationFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-administration-files/{id}")
    public ResponseEntity<MProposalAdministrationFileDTO> getMProposalAdministrationFile(@PathVariable Long id) {
        log.debug("REST request to get MProposalAdministrationFile : {}", id);
        Optional<MProposalAdministrationFileDTO> mProposalAdministrationFileDTO = mProposalAdministrationFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalAdministrationFileDTO);
    }

    /**
     * {@code DELETE  /m-proposal-administration-files/:id} : delete the "id" mProposalAdministrationFile.
     *
     * @param id the id of the mProposalAdministrationFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-administration-files/{id}")
    public ResponseEntity<Void> deleteMProposalAdministrationFile(@PathVariable Long id) {
        log.debug("REST request to delete MProposalAdministrationFile : {}", id);
        mProposalAdministrationFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
