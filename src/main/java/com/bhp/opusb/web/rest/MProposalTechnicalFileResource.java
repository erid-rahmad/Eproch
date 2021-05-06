package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MProposalTechnicalFileService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MProposalTechnicalFileDTO;
import com.bhp.opusb.service.dto.MProposalTechnicalFileCriteria;
import com.bhp.opusb.service.MProposalTechnicalFileQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MProposalTechnicalFile}.
 */
@RestController
@RequestMapping("/api")
public class MProposalTechnicalFileResource {

    private final Logger log = LoggerFactory.getLogger(MProposalTechnicalFileResource.class);

    private static final String ENTITY_NAME = "mProposalTechnicalFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MProposalTechnicalFileService mProposalTechnicalFileService;

    private final MProposalTechnicalFileQueryService mProposalTechnicalFileQueryService;

    public MProposalTechnicalFileResource(MProposalTechnicalFileService mProposalTechnicalFileService, MProposalTechnicalFileQueryService mProposalTechnicalFileQueryService) {
        this.mProposalTechnicalFileService = mProposalTechnicalFileService;
        this.mProposalTechnicalFileQueryService = mProposalTechnicalFileQueryService;
    }

    /**
     * {@code POST  /m-proposal-technical-files} : Create a new mProposalTechnicalFile.
     *
     * @param mProposalTechnicalFileDTO the mProposalTechnicalFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mProposalTechnicalFileDTO, or with status {@code 400 (Bad Request)} if the mProposalTechnicalFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-proposal-technical-files")
    public ResponseEntity<MProposalTechnicalFileDTO> createMProposalTechnicalFile(@Valid @RequestBody MProposalTechnicalFileDTO mProposalTechnicalFileDTO) throws URISyntaxException {
        log.debug("REST request to save MProposalTechnicalFile : {}", mProposalTechnicalFileDTO);
        if (mProposalTechnicalFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new mProposalTechnicalFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MProposalTechnicalFileDTO result = mProposalTechnicalFileService.save(mProposalTechnicalFileDTO);
        return ResponseEntity.created(new URI("/api/m-proposal-technical-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-proposal-technical-files} : Updates an existing mProposalTechnicalFile.
     *
     * @param mProposalTechnicalFileDTO the mProposalTechnicalFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mProposalTechnicalFileDTO,
     * or with status {@code 400 (Bad Request)} if the mProposalTechnicalFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mProposalTechnicalFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-proposal-technical-files")
    public ResponseEntity<MProposalTechnicalFileDTO> updateMProposalTechnicalFile(@Valid @RequestBody MProposalTechnicalFileDTO mProposalTechnicalFileDTO) throws URISyntaxException {
        log.debug("REST request to update MProposalTechnicalFile : {}", mProposalTechnicalFileDTO);
        if (mProposalTechnicalFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MProposalTechnicalFileDTO result = mProposalTechnicalFileService.save(mProposalTechnicalFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mProposalTechnicalFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-proposal-technical-files} : get all the mProposalTechnicalFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mProposalTechnicalFiles in body.
     */
    @GetMapping("/m-proposal-technical-files")
    public ResponseEntity<List<MProposalTechnicalFileDTO>> getAllMProposalTechnicalFiles(MProposalTechnicalFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MProposalTechnicalFiles by criteria: {}", criteria);
        Page<MProposalTechnicalFileDTO> page = mProposalTechnicalFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-proposal-technical-files/count} : count all the mProposalTechnicalFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-proposal-technical-files/count")
    public ResponseEntity<Long> countMProposalTechnicalFiles(MProposalTechnicalFileCriteria criteria) {
        log.debug("REST request to count MProposalTechnicalFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(mProposalTechnicalFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-proposal-technical-files/:id} : get the "id" mProposalTechnicalFile.
     *
     * @param id the id of the mProposalTechnicalFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mProposalTechnicalFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-proposal-technical-files/{id}")
    public ResponseEntity<MProposalTechnicalFileDTO> getMProposalTechnicalFile(@PathVariable Long id) {
        log.debug("REST request to get MProposalTechnicalFile : {}", id);
        Optional<MProposalTechnicalFileDTO> mProposalTechnicalFileDTO = mProposalTechnicalFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mProposalTechnicalFileDTO);
    }

    /**
     * {@code DELETE  /m-proposal-technical-files/:id} : delete the "id" mProposalTechnicalFile.
     *
     * @param id the id of the mProposalTechnicalFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-proposal-technical-files/{id}")
    public ResponseEntity<Void> deleteMProposalTechnicalFile(@PathVariable Long id) {
        log.debug("REST request to delete MProposalTechnicalFile : {}", id);
        mProposalTechnicalFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
