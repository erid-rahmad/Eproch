package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MWarningLetterService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MWarningLetterDTO;
import com.bhp.opusb.service.dto.MWarningLetterCriteria;
import com.bhp.opusb.service.MWarningLetterQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MWarningLetter}.
 */
@RestController
@RequestMapping("/api")
public class MWarningLetterResource {

    private final Logger log = LoggerFactory.getLogger(MWarningLetterResource.class);

    private static final String ENTITY_NAME = "mWarningLetter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MWarningLetterService mWarningLetterService;

    private final MWarningLetterQueryService mWarningLetterQueryService;

    public MWarningLetterResource(MWarningLetterService mWarningLetterService, MWarningLetterQueryService mWarningLetterQueryService) {
        this.mWarningLetterService = mWarningLetterService;
        this.mWarningLetterQueryService = mWarningLetterQueryService;
    }

    /**
     * {@code POST  /m-warning-letters} : Create a new mWarningLetter.
     *
     * @param mWarningLetterDTO the mWarningLetterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mWarningLetterDTO, or with status {@code 400 (Bad Request)} if the mWarningLetter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-warning-letters")
    public ResponseEntity<MWarningLetterDTO> createMWarningLetter(@Valid @RequestBody MWarningLetterDTO mWarningLetterDTO) throws URISyntaxException {
        log.debug("REST request to save MWarningLetter : {}", mWarningLetterDTO);
        if (mWarningLetterDTO.getId() != null) {
            throw new BadRequestAlertException("A new mWarningLetter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MWarningLetterDTO result = mWarningLetterService.save(mWarningLetterDTO);
        return ResponseEntity.created(new URI("/api/m-warning-letters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-warning-letters} : Updates an existing mWarningLetter.
     *
     * @param mWarningLetterDTO the mWarningLetterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mWarningLetterDTO,
     * or with status {@code 400 (Bad Request)} if the mWarningLetterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mWarningLetterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-warning-letters")
    public ResponseEntity<MWarningLetterDTO> updateMWarningLetter(@Valid @RequestBody MWarningLetterDTO mWarningLetterDTO) throws URISyntaxException {
        log.debug("REST request to update MWarningLetter : {}", mWarningLetterDTO);
        if (mWarningLetterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MWarningLetterDTO result = mWarningLetterService.save(mWarningLetterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mWarningLetterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-warning-letters} : get all the mWarningLetters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mWarningLetters in body.
     */
    @GetMapping("/m-warning-letters")
    public ResponseEntity<List<MWarningLetterDTO>> getAllMWarningLetters(MWarningLetterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MWarningLetters by criteria: {}", criteria);
        Page<MWarningLetterDTO> page = mWarningLetterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-warning-letters/count} : count all the mWarningLetters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-warning-letters/count")
    public ResponseEntity<Long> countMWarningLetters(MWarningLetterCriteria criteria) {
        log.debug("REST request to count MWarningLetters by criteria: {}", criteria);
        return ResponseEntity.ok().body(mWarningLetterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-warning-letters/:id} : get the "id" mWarningLetter.
     *
     * @param id the id of the mWarningLetterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mWarningLetterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-warning-letters/{id}")
    public ResponseEntity<MWarningLetterDTO> getMWarningLetter(@PathVariable Long id) {
        log.debug("REST request to get MWarningLetter : {}", id);
        Optional<MWarningLetterDTO> mWarningLetterDTO = mWarningLetterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mWarningLetterDTO);
    }

    /**
     * {@code DELETE  /m-warning-letters/:id} : delete the "id" mWarningLetter.
     *
     * @param id the id of the mWarningLetterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-warning-letters/{id}")
    public ResponseEntity<Void> deleteMWarningLetter(@PathVariable Long id) {
        log.debug("REST request to delete MWarningLetter : {}", id);
        mWarningLetterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
