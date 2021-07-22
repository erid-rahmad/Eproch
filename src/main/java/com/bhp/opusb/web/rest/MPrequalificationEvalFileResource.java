package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MPrequalificationEvalFileService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileDTO;
import com.bhp.opusb.service.dto.MPrequalificationEvalFileCriteria;
import com.bhp.opusb.service.MPrequalificationEvalFileQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MPrequalificationEvalFile}.
 */
@RestController
@RequestMapping("/api")
public class MPrequalificationEvalFileResource {

    private final Logger log = LoggerFactory.getLogger(MPrequalificationEvalFileResource.class);

    private static final String ENTITY_NAME = "mPrequalificationEvalFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MPrequalificationEvalFileService mPrequalificationEvalFileService;

    private final MPrequalificationEvalFileQueryService mPrequalificationEvalFileQueryService;

    public MPrequalificationEvalFileResource(MPrequalificationEvalFileService mPrequalificationEvalFileService, MPrequalificationEvalFileQueryService mPrequalificationEvalFileQueryService) {
        this.mPrequalificationEvalFileService = mPrequalificationEvalFileService;
        this.mPrequalificationEvalFileQueryService = mPrequalificationEvalFileQueryService;
    }

    /**
     * {@code POST  /m-prequalification-eval-files} : Create a new mPrequalificationEvalFile.
     *
     * @param mPrequalificationEvalFileDTO the mPrequalificationEvalFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mPrequalificationEvalFileDTO, or with status {@code 400 (Bad Request)} if the mPrequalificationEvalFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-prequalification-eval-files")
    public ResponseEntity<MPrequalificationEvalFileDTO> createMPrequalificationEvalFile(@Valid @RequestBody MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO) throws URISyntaxException {
        log.debug("REST request to save MPrequalificationEvalFile : {}", mPrequalificationEvalFileDTO);
        if (mPrequalificationEvalFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new mPrequalificationEvalFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MPrequalificationEvalFileDTO result = mPrequalificationEvalFileService.save(mPrequalificationEvalFileDTO);
        return ResponseEntity.created(new URI("/api/m-prequalification-eval-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-prequalification-eval-files} : Updates an existing mPrequalificationEvalFile.
     *
     * @param mPrequalificationEvalFileDTO the mPrequalificationEvalFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mPrequalificationEvalFileDTO,
     * or with status {@code 400 (Bad Request)} if the mPrequalificationEvalFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mPrequalificationEvalFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-prequalification-eval-files")
    public ResponseEntity<MPrequalificationEvalFileDTO> updateMPrequalificationEvalFile(@Valid @RequestBody MPrequalificationEvalFileDTO mPrequalificationEvalFileDTO) throws URISyntaxException {
        log.debug("REST request to update MPrequalificationEvalFile : {}", mPrequalificationEvalFileDTO);
        if (mPrequalificationEvalFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MPrequalificationEvalFileDTO result = mPrequalificationEvalFileService.save(mPrequalificationEvalFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mPrequalificationEvalFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-prequalification-eval-files} : get all the mPrequalificationEvalFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mPrequalificationEvalFiles in body.
     */
    @GetMapping("/m-prequalification-eval-files")
    public ResponseEntity<List<MPrequalificationEvalFileDTO>> getAllMPrequalificationEvalFiles(MPrequalificationEvalFileCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MPrequalificationEvalFiles by criteria: {}", criteria);
        Page<MPrequalificationEvalFileDTO> page = mPrequalificationEvalFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-prequalification-eval-files/count} : count all the mPrequalificationEvalFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-prequalification-eval-files/count")
    public ResponseEntity<Long> countMPrequalificationEvalFiles(MPrequalificationEvalFileCriteria criteria) {
        log.debug("REST request to count MPrequalificationEvalFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(mPrequalificationEvalFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-prequalification-eval-files/:id} : get the "id" mPrequalificationEvalFile.
     *
     * @param id the id of the mPrequalificationEvalFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mPrequalificationEvalFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-prequalification-eval-files/{id}")
    public ResponseEntity<MPrequalificationEvalFileDTO> getMPrequalificationEvalFile(@PathVariable Long id) {
        log.debug("REST request to get MPrequalificationEvalFile : {}", id);
        Optional<MPrequalificationEvalFileDTO> mPrequalificationEvalFileDTO = mPrequalificationEvalFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mPrequalificationEvalFileDTO);
    }

    /**
     * {@code DELETE  /m-prequalification-eval-files/:id} : delete the "id" mPrequalificationEvalFile.
     *
     * @param id the id of the mPrequalificationEvalFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-prequalification-eval-files/{id}")
    public ResponseEntity<Void> deleteMPrequalificationEvalFile(@PathVariable Long id) {
        log.debug("REST request to delete MPrequalificationEvalFile : {}", id);
        mPrequalificationEvalFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
