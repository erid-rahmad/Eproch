package com.bhp.opusb.web.rest;

import com.bhp.opusb.service.MRfqLineService;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;
import com.bhp.opusb.service.dto.MRfqLineDTO;
import com.bhp.opusb.service.dto.MRfqLineCriteria;
import com.bhp.opusb.service.MRfqLineQueryService;

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
 * REST controller for managing {@link com.bhp.opusb.domain.MRfqLine}.
 */
@RestController
@RequestMapping("/api")
public class MRfqLineResource {

    private final Logger log = LoggerFactory.getLogger(MRfqLineResource.class);

    private static final String ENTITY_NAME = "mRfqLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MRfqLineService mRfqLineService;

    private final MRfqLineQueryService mRfqLineQueryService;

    public MRfqLineResource(MRfqLineService mRfqLineService, MRfqLineQueryService mRfqLineQueryService) {
        this.mRfqLineService = mRfqLineService;
        this.mRfqLineQueryService = mRfqLineQueryService;
    }

    /**
     * {@code POST  /m-rfq-lines} : Create a new mRfqLine.
     *
     * @param mRfqLineDTO the mRfqLineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mRfqLineDTO, or with status {@code 400 (Bad Request)} if the mRfqLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-rfq-lines")
    public ResponseEntity<MRfqLineDTO> createMRfqLine(@Valid @RequestBody MRfqLineDTO mRfqLineDTO) throws URISyntaxException {
        log.debug("REST request to save MRfqLine : {}", mRfqLineDTO);
        if (mRfqLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new mRfqLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MRfqLineDTO result = mRfqLineService.save(mRfqLineDTO);
        return ResponseEntity.created(new URI("/api/m-rfq-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-rfq-lines} : Updates an existing mRfqLine.
     *
     * @param mRfqLineDTO the mRfqLineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mRfqLineDTO,
     * or with status {@code 400 (Bad Request)} if the mRfqLineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mRfqLineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-rfq-lines")
    public ResponseEntity<MRfqLineDTO> updateMRfqLine(@Valid @RequestBody MRfqLineDTO mRfqLineDTO) throws URISyntaxException {
        log.debug("REST request to update MRfqLine : {}", mRfqLineDTO);
        if (mRfqLineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MRfqLineDTO result = mRfqLineService.save(mRfqLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mRfqLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-rfq-lines} : get all the mRfqLines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mRfqLines in body.
     */
    @GetMapping("/m-rfq-lines")
    public ResponseEntity<List<MRfqLineDTO>> getAllMRfqLines(MRfqLineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MRfqLines by criteria: {}", criteria);
        Page<MRfqLineDTO> page = mRfqLineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-rfq-lines/count} : count all the mRfqLines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/m-rfq-lines/count")
    public ResponseEntity<Long> countMRfqLines(MRfqLineCriteria criteria) {
        log.debug("REST request to count MRfqLines by criteria: {}", criteria);
        return ResponseEntity.ok().body(mRfqLineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-rfq-lines/:id} : get the "id" mRfqLine.
     *
     * @param id the id of the mRfqLineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mRfqLineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-rfq-lines/{id}")
    public ResponseEntity<MRfqLineDTO> getMRfqLine(@PathVariable Long id) {
        log.debug("REST request to get MRfqLine : {}", id);
        Optional<MRfqLineDTO> mRfqLineDTO = mRfqLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mRfqLineDTO);
    }

    /**
     * {@code DELETE  /m-rfq-lines/:id} : delete the "id" mRfqLine.
     *
     * @param id the id of the mRfqLineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-rfq-lines/{id}")
    public ResponseEntity<Void> deleteMRfqLine(@PathVariable Long id) {
        log.debug("REST request to delete MRfqLine : {}", id);
        mRfqLineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
