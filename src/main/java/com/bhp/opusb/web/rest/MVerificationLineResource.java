package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MVerificationLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MVerificationLineDTO;
import com.bhp.opusb.service.dto.MVerificationLineCriteria;
import com.bhp.opusb.service.MVerificationLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MVerificationLine}.
 */
@RestController
@RequestMapping("/api")
public class MVerificationLineResource {

    private final Logger log = LoggerFactory.getLogger(MVerificationLineResource.class);

    private static final String ENTITY_NAME = "mVerificationLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MVerificationLineService mVerificationLineService;

    private final MVerificationLineQueryService mVerificationLineQueryService;

    public MVerificationLineResource(MVerificationLineService mVerificationLineService, MVerificationLineQueryService mVerificationLineQueryService) {
        this.mVerificationLineService = mVerificationLineService;
        this.mVerificationLineQueryService = mVerificationLineQueryService;
    }

    /**
     * {@code POST  /m-verification-lines} : Create a new mVerificationLine.
     *
     * @param mVerificationLineDTO the mVerificationLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mVerificationLineDTO, or with status {@code 400 (Bad Request)} if the mVerificationLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-verification-lines")
    public ResponseEntity<MVerificationLineDTO> createMVerificationLine(@Valid @RequestBody MVerificationLineDTO mVerificationLineDTO) throws URISyntaxException {
        log.debug("REST request to save MVerificationLine : {}", mVerificationLineDTO);
        if (mVerificationLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mVerificationLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MVerificationLineDTO result = mVerificationLineService.save(mVerificationLineDTO);
        return ResponseEntity.created(new URI("/api/m-verification-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-verification-lines} : Updates an existing mVerificationLine.
     *
     * @param mVerificationLineDTO the mVerificationLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mVerificationLineDTO,
     * or with status {@code 400 (Bad Request)} if the mVerificationLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mVerificationLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-verification-lines")
    public ResponseEntity<MVerificationLineDTO> updateMVerificationLine(@Valid @RequestBody MVerificationLineDTO mVerificationLineDTO) throws URISyntaxException {
        log.debug("REST request to update MVerificationLine : {}", mVerificationLineDTO);
        if (mVerificationLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MVerificationLineDTO result = mVerificationLineService.save(mVerificationLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mVerificationLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-verification-lines} : get all the mVerificationLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mVerificationLines in body.
     */
    @GetMapping("/m-verification-lines")
    public ResponseEntity<List<MVerificationLineDTO>> getAllMVerificationLines(MVerificationLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MVerificationLines by criteria: {}", criteria);
        Page<MVerificationLineDTO> page = mVerificationLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-verification-lines/count} : count all the mVerificationLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-verification-lines/count")
    public ResponseEntity<Long> countMVerificationLines(MVerificationLineCriteria criteria) {
        log.debug("REST request to count MVerificationLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mVerificationLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-verification-lines/:id} : get the "id" mVerificationLine.
     *
     * @param id the id of the mVerificationLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mVerificationLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-verification-lines/{id}")
    public ResponseEntity<MVerificationLineDTO> getMVerificationLine(@PathVariable Long id) {
        log.debug("REST request to get MVerificationLine : {}", id);
        Optional<MVerificationLineDTO> mVerificationLineDTO = mVerificationLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mVerificationLineDTO);
    }

    /**
     * {@code DELETE  /m-verification-lines/:id} : delete the "id" mVerificationLine.
     *
     * @param id the id of the mVerificationLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-verification-lines/{id}")
    public ResponseEntity<Void> deleteMVerificationLine(@PathVariable Long id) {
        log.debug("REST request to delete MVerificationLine : {}", id);
        mVerificationLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
